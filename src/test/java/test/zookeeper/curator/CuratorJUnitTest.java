package test.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.Properties;

public abstract class CuratorJUnitTest extends Assert {

    private static final String config = "/zookeeper.properties";

    protected static CuratorFramework client;

    @BeforeClass
    public static void init() {
        String serverList = loadConfig().getProperty("zookeeper.server.list");
        client = CuratorFrameworkFactory.newClient(serverList, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    private static Properties loadConfig() {
        Properties prop = new Properties();
        try {
            prop.load(CuratorJUnitTest.class.getResourceAsStream(config));
        } catch (Exception e) {
            throw new RuntimeException("config load failed");
        }
        return prop;
    }

}
