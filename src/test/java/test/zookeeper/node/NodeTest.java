package test.zookeeper.node;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import test.zookeeper.junit4.ZooConfig;
import test.zookeeper.junit4.ZooKeeperJUnitTest;

import java.util.List;

public class NodeTest extends ZooKeeperJUnitTest {

    /**
     * create node
     * <p>
     * 1) 节点已存在, 抛出KeeperException.NodeExistsException
     * 2) 父节点不存在, 抛出KeeperException.NoNodeException
     * 3) data大小不可超过1M, 抛出KeeperException
     * 4) 父节点是临时节点, 抛出KeeperException.NoChildrenForEphemeralsException
     * 5) 创建成功, 返回节点的实际路径
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testCreateNode() throws Exception {
        assertEquals("/node/child", zooKeeper.create("/node/child", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
    }

    /**
     * node exists
     * <p>
     * 1) 节点存在, 返回节点的Stat
     * 2) 节点不存在, 返回null
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testNodeExists() throws Exception {
        assertNotNull(zooKeeper.exists("/node", false));
        assertNull(zooKeeper.exists("/node/child", false));
    }

    /**
     * delete node
     * <p>
     * 1) 节点不存在, 抛出KeeperException.NoNodeException
     * 2) version不匹配, 抛出KeeperException.BadVersionException
     * 3) 节点存在子节点, 抛出KeeperException.NotEmptyException
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testDeleteNode() throws Exception {
        zooKeeper.delete("/node", -1);
    }

    /**
     * set node data
     * <p>
     * 1) 节点必须存在, 否则抛出KeeperException.NoNodeException
     * 2) version必须匹配, 否则抛出KeeperException.BadVersionException
     * 3) data大小不超过1M, 否则抛出KeeperException
     * 4) 执行成功, 返回节点的Stat
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testSetNodeData() throws Exception {
        showStat(zooKeeper.setData("/node", "data".getBytes(), -1));
    }

    /**
     * get node data
     * <p>
     * 1) 节点必须存在, 否则抛出KeeperException.NoNodeException
     * 2) 参数Stat代表返回的节点的Stat
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testGetNodeData() throws Exception {
        zooKeeper.setData("/node", "data".getBytes(), -1);
        Stat stat = new Stat();
        assertEquals("data", new String(zooKeeper.getData("/node", false, stat)));
        showStat(stat);
    }

    @Test
    @ZooConfig(initNodes = {"/node/child"})
    public void testGetChildren() throws Exception {
        Stat stat = new Stat();
        List<String> childrens = zooKeeper.getChildren("/node", false, stat);
        assertEquals(1, childrens.size());
        assertEquals("child", childrens.get(0));
        showStat(stat);
    }

}
