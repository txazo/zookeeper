package test.zookeeper.node;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;
import test.zookeeper.junit4.ZooConfig;
import test.zookeeper.junit4.ZooKeeperJUnitTest;

public class NodeTest extends ZooKeeperJUnitTest {

    // 创建节点
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testCreateNode() throws Exception {
        assertEquals("/node/child", zooKeeper.create("/node/child", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
    }

    // 节点已存在
    @Test(expected = KeeperException.NodeExistsException.class)
    @ZooConfig(initNodes = {"/node"})
    public void testCreateExistsNode() throws Exception {
        zooKeeper.create("/node", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 父节点不存在
    @Test(expected = KeeperException.NoNodeException.class)
    @ZooConfig(initNodes = {"/node"})
    public void testCreateNodeWithParentNotExists() throws Exception {
        zooKeeper.create("/node/child/subchild", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 节点是否存在
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testNodeExists() throws Exception {
        assertNotNull(zooKeeper.exists("/node", false));
        assertNull(zooKeeper.exists("/node/child", false));
    }

    // 删除节点
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testDeleteNode() throws Exception {
        zooKeeper.delete("/node", -1);
    }

    // 节点不存在
    @Test(expected = KeeperException.NoNodeException.class)
    @ZooConfig(initNodes = {"/node"})
    public void testDeleteNoNode() throws Exception {
        zooKeeper.delete("/node/child", -1);
    }

    // version不匹配
    @Test(expected = KeeperException.BadVersionException.class)
    @ZooConfig(initNodes = {"/node"})
    public void testDeleteNodeWithBadVersion() throws Exception {
        zooKeeper.delete("/node", 100);
    }

    // 节点存在子节点
    @Test(expected = KeeperException.NotEmptyException.class)
    @ZooConfig(initNodes = {"/node/child"})
    public void testDeleteNodeWithChild() throws Exception {
        zooKeeper.delete("/node", -1);
    }

}
