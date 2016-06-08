package test.zookeeper.lock;

import org.junit.Test;
import test.zookeeper.ZooKeeperGetter;

public class DistributedLockTest {

    @Test
    public void test() throws Exception {
        DistributedLock lock = new DefaultDistributedLock(ZooKeeperGetter.getZooKeeper(), "/node");
        lock.lock();
    }

}
