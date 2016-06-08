package test.zookeeper.node;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import test.zookeeper.junit4.ZooConfig;
import test.zookeeper.junit4.ZooKeeperJUnitTest;

import java.util.Arrays;
import java.util.List;

public class NodeTest extends ZooKeeperJUnitTest {

    /**
     * create node
     * <p>
     * 1) 节点已存在, 抛出KeeperException.NodeExistsException
     * 2) 父节点不存在, 抛出KeeperException.NoNodeException
     * 3) 父节点是临时节点, 抛出KeeperException.NoChildrenForEphemeralsException
     * 4) data大小超过1M, 抛出KeeperException
     * 5) 创建成功, 返回节点的实际路径
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testCreateNode() throws Exception {
        assertEquals("/node/child", zooKeeper.create("/node/child", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
    }

    @Test
    @ZooConfig(initNodes = {"/node"}, async = true)
    public void testCreateNodeAsync() {
        zooKeeper.create("/node/child", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                assertEquals("/node/child", name);
                assertResult(rc, "/node/child", path, ctx, null);
            }

        }, null);
    }

    /**
     * delete node
     * <p>
     * 1) 节点不存在, 抛出KeeperException.NoNodeException
     * 2) 节点存在子节点, 抛出KeeperException.NotEmptyException
     * 3) version不匹配, 抛出KeeperException.BadVersionException
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testDeleteNode() throws Exception {
        zooKeeper.delete("/node", -1);
    }

    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testDeleteNodeAsync() throws Exception {
        zooKeeper.delete("/node", -1, new AsyncCallback.VoidCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx) {
                assertResult(rc, "/node", path, ctx, null);
            }

        }, null);
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

    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testNodeExistsAsync() throws Exception {
        zooKeeper.exists("/node", false, new AsyncCallback.StatCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                assertResult(rc, "/node", path, ctx, stat);
            }

        }, null);
    }

    /**
     * get node data
     * <p>
     * 1) 节点不存在, 抛出KeeperException.NoNodeException
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
    @ZooConfig(initNodes = {"/node"})
    public void testGetNodeDataAsync() throws Exception {
        zooKeeper.setData("/node", "data".getBytes(), -1);
        zooKeeper.getData("/node", false, new AsyncCallback.DataCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                assertEquals("data", new String(data));
                assertResult(rc, "/node", path, ctx, stat);
            }

        }, null);
    }

    /**
     * set node data
     * <p>
     * 1) 节点不存在, 抛出KeeperException.NoNodeException
     * 2) data大小超过1M, 抛出KeeperException
     * 3) version不匹配, 抛出KeeperException.BadVersionException
     * 4) 执行成功, 返回节点的Stat
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testSetNodeData() throws Exception {
        showStat(zooKeeper.setData("/node", "data".getBytes(), -1));
    }

    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testSetNodeDataAsync() throws Exception {
        zooKeeper.setData("/node", "data".getBytes(), -1, new AsyncCallback.StatCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                assertResult(rc, "/node", path, ctx, stat);
            }

        }, null);
    }

    /**
     * get node children
     * <p>
     * 1) 节点不存在, 抛出KeeperException.NoNodeException
     * 2) 参数Stat代表返回的节点的Stat
     */
    @Test
    @ZooConfig(initNodes = {"/node/child"})
    public void testGetChildren() throws Exception {
        Stat stat = new Stat();
        List<String> childrens = zooKeeper.getChildren("/node", false, stat);
        assertTrue(childrens.equals(Arrays.asList("child")));
        showStat(stat);
    }

    @Test
    @ZooConfig(initNodes = {"/node/child"})
    public void testGetChildrenAsync() throws Exception {
        zooKeeper.getChildren("/node", false, new AsyncCallback.ChildrenCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children) {
                assertTrue(children.equals(Arrays.asList("child")));
                assertResult(rc, "/node", path, ctx, stat);
            }

        }, null);
    }

}
