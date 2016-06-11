package test.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import test.zookeeper.junit4.ZooConfig;
import test.zookeeper.junit4.ZooKeeperJUnitTest;

import java.util.Arrays;
import java.util.List;

public class ACLTest extends ZooKeeperJUnitTest {

    /**
     * get acl
     * <p>
     * 1) 节点不存在, 抛出KeeperException.NoNode
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testGetACL() throws Exception {
        List<ACL> acls = zooKeeper.getACL("/node", stat);
        assertTrue(acls.equals(ZooDefs.Ids.OPEN_ACL_UNSAFE));
        showStat(stat);
    }

    /**
     * get acl async
     */
    @Test
    @ZooConfig(initNodes = {"/node"}, async = true)
    public void testGetACLAsync() throws Exception {
        zooKeeper.getACL("/node", null, new AsyncCallback.ACLCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, List<ACL> acl, Stat stat) {
                assertTrue(acl.equals(ZooDefs.Ids.OPEN_ACL_UNSAFE));
                assertResult(rc, "/node", path, ctx, stat);
            }

        }, null);
    }

    /**
     * set acl
     */
    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testSetACL() throws Exception {
        zooKeeper.setACL("/node", Arrays.asList(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone"))), -1);
    }

    /**
     * set acl sync
     */
    @Test
    @ZooConfig(initNodes = {"/node"}, async = true)
    public void testSetACLAsync() throws Exception {
        zooKeeper.setACL("/node", Arrays.asList(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone"))), -1, new AsyncCallback.StatCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                assertResult(rc, "/node", path, ctx, null);
            }

        }, null);
    }

}
