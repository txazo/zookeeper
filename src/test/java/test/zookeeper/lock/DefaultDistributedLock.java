package test.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class DefaultDistributedLock implements DistributedLock {

    private static final byte[] data = {0x1A, 0x2B};
    private static final String lockPath = "/lock";

    private String path;
    private ZooKeeper zooKeeper;

    public DefaultDistributedLock(ZooKeeper zooKeeper, String path) {
        this.zooKeeper = zooKeeper;
        this.path = path;
    }

    @Override
    public void lock() throws InterruptedException, KeeperException {
        String realPath = zooKeeper.create(lockPath + path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public void unlock() throws KeeperException {

    }

}
