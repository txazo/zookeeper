package test.zookeeper.recipes;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.recipes.lock.LockListener;
import org.apache.zookeeper.recipes.lock.WriteLock;
import org.junit.Test;
import test.thread.junit4.*;
import test.zookeeper.ZooKeeperGetter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class WriteLockTest extends ConcurrentThreadJUnitTest {

    @Test
    @Concurrency(count = 100, pooled = true, poolCount = 20)
    public void test() {
        setCallable(new RunnableCallable() {

            @Override
            public RunnableFuture run(final int i) throws Exception {
                final AtomicBoolean atomic = new AtomicBoolean();
                final RunnableFuture future = new DefaultRunnableFuture();
                final WriteLock lock = new WriteLock(ZooKeeperGetter.getZooKeeper(), "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE);
                lock.setLockListener(new LockListener() {

                    @Override
                    public void lockAcquired() {
                        if (atomic.compareAndSet(false, true)) {
                            execute(i);
                            lock.unlock();
                            future.setDone();
                        }
                    }

                    @Override
                    public void lockReleased() {
                    }

                });
                if (lock.lock()) {
                    if (atomic.compareAndSet(false, true)) {
                        execute(i);
                        lock.unlock();
                        future.setDone();
                    }
                }
                return future;
            }

            private void execute(int i) {
                try {
                    Thread.sleep(new Random().nextInt(10) * 100);
                } catch (InterruptedException e) {
                    LOG.info(e.getMessage());
                }
                LOG.info("lock {}", i);
            }

        });
    }

}
