package test.curator.node;

import org.junit.Test;
import test.curator.CuratorJUnitTest;

public class NodeTest extends CuratorJUnitTest {

    @Test
    public void testCreate() throws Exception {
        client.create().forPath("/node", "data".getBytes());
    }

    @Test
    public void testMultiCreate() throws Exception {
        long start = System.currentTimeMillis();
        long id = 0;
        for (long i = 0; i < 100000; i++) {
            id = start + i;
            client.create().forPath("/node/" + id, String.valueOf(id).getBytes());
            Thread.sleep(100);
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
