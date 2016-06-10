package test.zookeeper.lock;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.recipes.lock.LockListener;
import org.apache.zookeeper.recipes.lock.WriteLock;
import org.junit.Test;
import test.zookeeper.ZooKeeperGetter;

public class DistributedLockTest {

    @Test
    public void test() throws Exception {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            final int j = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        final WriteLock lock = new WriteLock(ZooKeeperGetter.getZooKeeper(), "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE);
                        lock.setLockListener(new LockListener() {

                            @Override
                            public void lockAcquired() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("lock " + j);
                                lock.unlock();
                            }

                            @Override
                            public void lockReleased() {
                            }

                        });
                        boolean res = lock.lock();
                        if (res) {
                            Thread.sleep(1000);
                            System.out.println("lock " + j);
                            lock.unlock();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }

        for (int i = 0; i < 100; i++) {
            threads[i].start();
        }

        Thread.sleep(1000000);
    }

}
