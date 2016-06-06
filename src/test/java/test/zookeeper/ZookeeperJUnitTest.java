package test.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.ConfigLoader;
import test.FieldCallback;

import java.io.IOException;

public abstract class ZookeeperJUnitTest extends ConfigLoader implements Watcher {

    protected static final Logger LOG = LoggerFactory.getLogger(ZookeeperJUnitTest.class);

    protected ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(getServerList(), SESSION_TIMEOUT, this);
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info(event.toString());
    }

    protected void showStat(Stat stat) throws IllegalAccessException {
        if (stat != null) {
            traverseField(stat, new FieldCallback() {

                @Override
                public void doField(String fieldName, Object fieldValue) {
                    System.out.println(fieldName + "\t" + fieldValue.toString());
                }

            });
        }
    }

}
