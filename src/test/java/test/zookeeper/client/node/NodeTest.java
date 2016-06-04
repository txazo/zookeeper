package test.zookeeper.client.node;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class NodeTest {

    public void test() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1", 2000, new NodeWatcher());

    }

    private static class NodeWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {

        }

    }

}
