package test.zookeeper.node;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;
import test.zookeeper.ZookeeperJUnitTest;

public class NodeTest extends ZookeeperJUnitTest {

    @Test
    public void testCreate() throws Exception {
        if (zooKeeper.exists("/node", false) != null) {
            zooKeeper.delete("/node", -1);
        }
        assertEquals("/node", zooKeeper.create("/node", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
        assertEquals("data", new String(zooKeeper.getData("/node", false, null)));
        assertNotNull(zooKeeper.setData("/node", "data_new".getBytes(), -1));
        assertEquals("data_new", new String(zooKeeper.getData("/node", false, null)));
    }

}
