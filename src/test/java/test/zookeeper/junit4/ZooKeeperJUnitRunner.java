package test.zookeeper.junit4;

import org.apache.zookeeper.*;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperJUnitRunner extends BlockJUnit4ClassRunner implements Watcher {

    private static final String SERVER_LIST = "zookeeper.server.list";

    private boolean isZooKeeperTest = false;
    private ZooKeeper zooKeeper;
    private CountDownLatch connectionLatch = new CountDownLatch(1);

    public ZooKeeperJUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        initRunner(clazz);
    }

    private void initRunner(Class<?> clazz) {
        isZooKeeperTest = ZooKeeperJUnitTest.class.isAssignableFrom(clazz);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        initBeforeInvoker(method, test);
        return super.methodInvoker(method, test);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectionLatch.countDown();
        }
    }

    private void initBeforeInvoker(FrameworkMethod method, Object test) {
        if (isZooKeeperTest) {
            ZooConfig zooConfig = method.getAnnotation(ZooConfig.class);
            if (zooConfig != null) {
                try {
                    String serverList = loadConfig(zooConfig.config()).getProperty(SERVER_LIST);
                    zooKeeper = new ZooKeeper(serverList, zooConfig.timeout(), this);
                    waitUtilConnected();
                    initNodes(zooConfig.initNodes());
                    test.getClass().getMethod("setZooKeeper", ZooKeeper.class).invoke(test, zooKeeper);
                } catch (Exception e) {
                    throw new ZooKeeperJUnitException("initBeforeInvoker failed", e);
                }
            }
        }
    }

    private void waitUtilConnected() throws InterruptedException {
        if (zooKeeper.getState() == ZooKeeper.States.CONNECTING) {
            connectionLatch.await();
        }
    }

    private Properties loadConfig(String config) throws IOException {
        Properties prop = new Properties();
        prop.load(getClass().getResourceAsStream(config));
        return prop;
    }

    private void initNodes(String[] nodes) throws Exception {
        if (nodes != null && nodes.length > 0) {
            for (String node : nodes) {
                initNode(node);
            }
        }
    }

    private void initNode(String node) throws Exception {
        if (node == null || "".equals(node = node.trim()) || !node.startsWith("/") || "/".equals(node)) {
            return;
        }

        clearChildNode(node);
        createNode(node);
    }

    private void clearChildNode(String node) throws Exception {
        if (existsNode(node)) {
            List<String> childrens = zooKeeper.getChildren(node, false);
            if (childrens != null && childrens.size() > 0) {
                for (String childNode : childrens) {
                    clearChildNode(node + "/" + childNode);
                }
            }
            zooKeeper.delete(node, -1);
        }
    }

    private void createNode(String node) throws Exception {
        createParentNode(node);
        zooKeeper.create(node, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    private void createParentNode(String node) throws Exception {
        String parentNode = node.substring(0, node.lastIndexOf("/"));
        if (!isBlank(parentNode)) {
            createParentNode(parentNode);
            if (!existsNode(parentNode)) {
                zooKeeper.create(parentNode, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
    }

    private boolean existsNode(String node) throws Exception {
        return zooKeeper.exists(node, false) != null;
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

}
