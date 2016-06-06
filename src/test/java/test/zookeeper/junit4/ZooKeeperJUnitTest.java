package test.zookeeper.junit4;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.FieldCallback;
import test.FieldTraverser;

@RunWith(ZooKeeperJUnitRunner.class)
public class ZooKeeperJUnitTest extends FieldTraverser {

    protected static final Logger LOG = LoggerFactory.getLogger(ZooKeeperJUnitTest.class);

    protected ZooKeeper zooKeeper;

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    protected void showStat(Stat stat) throws IllegalAccessException {
        traverseField(stat, new FieldCallback() {

            @Override
            public void doField(String fieldName, Object fieldValue) {
                System.out.println(fieldName + "\t" + fieldValue.toString());
            }

        });
    }

}
