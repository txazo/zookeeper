package test.zookeeper.junit4;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.FieldCallback;
import test.FieldTraverser;

import java.util.concurrent.CountDownLatch;

@RunWith(ZooKeeperJUnitRunner.class)
public class ZooKeeperJUnitTest extends FieldTraverser {

    protected static final Logger LOG = LoggerFactory.getLogger(ZooKeeperJUnitTest.class);

    protected ZooKeeper zooKeeper;
    protected Stat stat = new Stat();
    private CountDownLatch asyncLatch;

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void asyncAwait(boolean async) throws InterruptedException {
        if (async) {
            asyncLatch = new CountDownLatch(1);
            asyncLatch.await();
        } else {
            asyncLatch = null;
        }
    }

    protected void asyncFinished() {
        if (asyncLatch != null) {
            asyncLatch.countDown();
        }
    }

    protected void assertResult(int rc, String expectedPath, String path, Object ctx, Stat stat) {
        assertEquals(KeeperException.Code.OK.intValue(), rc);
        assertEquals(expectedPath, path);
        assertNull(ctx);
        showStat(stat);
        asyncFinished();
    }

    public void showStat(Stat stat) {
        traverseField(stat, new FieldCallback() {

            @Override
            public void doField(String fieldName, Object fieldValue) {
                System.out.println(fieldName + "\t" + fieldValue.toString());
            }

        });
    }

}
