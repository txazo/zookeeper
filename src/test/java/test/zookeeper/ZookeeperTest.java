package test.zookeeper;

import org.apache.commons.codec.binary.Hex;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import test.zookeeper.junit4.ZooKeeperJUnitTest;

import java.util.concurrent.CountDownLatch;

public class ZookeeperTest extends ZooKeeperJUnitTest {

    @Test
    public void testZooKeeper() throws Exception {
        String connectString = "127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";
        int sessionTimeout = 5000;
        final CountDownLatch connectedLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                LOG.info(event.toString());
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    connectedLatch.countDown();
                }
            }

        });
        connectedLatch.await();

        LOG.info("Client SessionId: " + zooKeeper.getSessionId());
        LOG.info("Client SessionPasswd: " + Hex.encodeHexString(zooKeeper.getSessionPasswd()));
    }

}
