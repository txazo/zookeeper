package test.zookeeper.lock;

import org.apache.zookeeper.ZooKeeper;

public class DistributedLock {

    private static final byte[] data = {0x1A, 0x2B};
    private static final String lockPath = "/lock";

    private String path;
    private ZooKeeper zooKeeper;

    public DistributedLock(ZooKeeper zooKeeper, String path) {
        this.zooKeeper = zooKeeper;
        this.path = path;
    }

    public void lock() {

    }

    public void unlock() {

    }

}
