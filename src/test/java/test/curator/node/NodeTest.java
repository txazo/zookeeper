package test.curator.node;

import org.junit.Test;
import test.curator.CuratorJUnitTest;

import java.util.UUID;

public class NodeTest extends CuratorJUnitTest {

    @Test
    public void testCreate() throws Exception {
        client.create().forPath("/node", "data".getBytes());
    }

    @Test
    public void testMultiCreate() throws Exception {
        String id = null;
        for (; ; ) {
            id = UUID.randomUUID().toString();
            client.create().forPath("/node/" + id, id.getBytes());
//            client.getData().forPath("/node/" + id);
            Thread.sleep(5000);
        }
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
