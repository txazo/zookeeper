package test.zookeeper.curator;

import org.junit.Test;

public class NodeTest extends CuratorJUnitTest {

    @Test
    public void testCreate() throws Exception {
        client.create().forPath("/node", "data".getBytes());
    }

    @Test
    public void testGetData() throws Exception {
        byte[] data = client.getData().forPath("/node");
        assertEquals("data", new String(data));
    }

    @Test
    public void testSetData() throws Exception {
        client.setData().forPath("/node", "data_new".getBytes());
    }

    @Test
    public void testDelete() throws Exception {
        client.delete().forPath("/node");
    }

}
