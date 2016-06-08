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

    private ZooKeeper zooKeeper;
    private boolean isZooKeeperTest = false;
    private boolean async = false;
    private CountDownLatch connectedLatch = new CountDownLatch(1);

    public ZooKeeperJUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        initRunner(clazz);
    }

    private void initRunner(Class<?> clazz) {
        isZooKeeperTest = ZooKeeperJUnitTest.class.isAssignableFrom(clazz);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedLatch.countDown();
        }
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        beforeMethodInvoker(method, test);
        final Object target = test;
        final Statement statement = super.methodInvoker(method, test);
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                statement.evaluate();
                syncMethod(target);
            }

        };
    }

    private void beforeMethodInvoker(FrameworkMethod method, Object test) {
        if (isZooKeeperTest) {
            ZooConfig zooConfig = method.getAnnotation(ZooConfig.class);
            if (zooConfig != null) {
                try {
                    async = zooConfig.async();
                    String serverList = loadConfig(zooConfig.config()).getProperty(SERVER_LIST);
                    zooKeeper = new ZooKeeper(serverList, zooConfig.timeout(), this);
                    waitUtilConnected();
                    injectZooKeeper(test);
                    initNodes(zooConfig.initNodes());
                } catch (Exception e) {
                    throw new ZooKeeperJUnitException("beforeMethodInvoker failed", e);
                }
            }
        }
    }

    private void syncMethod(Object test) {
        try {
            test.getClass().getMethod("asyncAwait", boolean.class).invoke(test, async);
        } catch (Exception e) {
            throw new ZooKeeperJUnitException("syncMethod failed", e);
        }
    }

    private Properties loadConfig(String config) throws IOException {
        Properties prop = new Properties();
        prop.load(getClass().getResourceAsStream(config));
        return prop;
    }

    private void waitUtilConnected() throws InterruptedException {
        if (zooKeeper.getState() == ZooKeeper.States.CONNECTING) {
            connectedLatch.await();
        }
    }

    private void injectZooKeeper(Object test) throws Exception {
        test.getClass().getMethod("setZooKeeper", ZooKeeper.class).invoke(test, zooKeeper);
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

        removeChildNodes(node);
        createNodeWithParent(node);
    }

    private void removeChildNodes(String node) throws Exception {
        if (existsNode(node)) {
            List<String> childNodes = zooKeeper.getChildren(node, false);
            if (childNodes != null && childNodes.size() > 0) {
                for (String childNode : childNodes) {
                    removeChildNodes(node + "/" + childNode);
                }
            }
            zooKeeper.delete(node, -1);
        }
    }

    private boolean existsNode(String node) throws Exception {
        return zooKeeper.exists(node, false) != null;
    }

    private void createNodeWithParent(String node) throws Exception {
        String parentNode = node.substring(0, node.lastIndexOf("/"));
        if (isNotBlank(parentNode)) {
            createNodeWithParent(parentNode);
        }

        createNode(node);
    }

    private void createNode(String node) throws Exception {
        if (!existsNode(node)) {
            zooKeeper.create(node, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    private boolean isNotBlank(String str) {
        return str != null && !"".equals(str.trim());
    }

}
