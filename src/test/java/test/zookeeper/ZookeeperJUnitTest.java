package test.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.ConfigLoader;

import java.io.IOException;

public abstract class ZookeeperJUnitTest extends ConfigLoader implements Watcher {

    protected static final Logger LOG = LoggerFactory.getLogger(ZookeeperJUnitTest.class);

    protected ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(getServerList(), SESSION_TIMEOUT, this);
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info(event.toString());
    }

}
