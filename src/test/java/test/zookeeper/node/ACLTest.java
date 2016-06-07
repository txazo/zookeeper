package test.zookeeper.node;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.Test;
import test.zookeeper.junit4.ZooConfig;
import test.zookeeper.junit4.ZooKeeperJUnitTest;

import java.util.Arrays;

public class ACLTest extends ZooKeeperJUnitTest {

    @Test
    @ZooConfig(initNodes = {"/node"})
    public void testSetACL() throws Exception {
        zooKeeper.setACL("/node", Arrays.asList(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone"))), -1);
    }

}
