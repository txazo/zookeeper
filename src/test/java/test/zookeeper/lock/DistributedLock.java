package test.zookeeper.lock;

import org.apache.zookeeper.KeeperException;

public interface DistributedLock {

    public void lock() throws InterruptedException, KeeperException;

    public void unlock() throws KeeperException;

}
