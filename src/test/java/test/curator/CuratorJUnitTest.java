package test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import test.ConfigLoader;

public abstract class CuratorJUnitTest extends ConfigLoader {

    protected static CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(getServerList(), new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

}
