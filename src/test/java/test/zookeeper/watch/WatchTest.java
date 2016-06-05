package test.zookeeper.watch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;
import test.zookeeper.ZookeeperJUnitTest;

public class WatchTest extends ZookeeperJUnitTest {

    @Test
    public void test() throws Exception {
        if (zooKeeper.exists("/node", this) != null) {
            zooKeeper.delete("/node", -1);
        }
        assertEquals("/node", zooKeeper.create("/node", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
        assertEquals("data", new String(zooKeeper.getData("/node", this, null)));
        assertNotNull(zooKeeper.setData("/node", "data_new".getBytes(), -1));
        assertEquals("data_new", new String(zooKeeper.getData("/node", this, null)));
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info(event.toString());
    }

}
