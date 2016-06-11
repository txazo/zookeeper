package test.zookeeper.recipes;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.recipes.queue.DistributedQueue;
import org.junit.Test;
import test.thread.junit4.Concurrency;
import test.thread.junit4.ConcurrentThreadJUnitTest;
import test.thread.junit4.RunnableCallable;
import test.thread.junit4.RunnableFuture;
import test.zookeeper.ZooKeeperGetter;

import java.util.Random;

public class DistributedQueueTest extends ConcurrentThreadJUnitTest {

    @Test
    @Concurrency(count = 100)
    public void test() {
        setCallable(new RunnableCallable() {

            @Override
            public RunnableFuture run(int i) throws Exception {
                DistributedQueue queue = new DistributedQueue(ZooKeeperGetter.getZooKeeper(), "/queue", ZooDefs.Ids.OPEN_ACL_UNSAFE);
                String data = "data " + i;
                queue.offer(data.getBytes());
                LOG.info("queue offer: " + data);
                Thread.sleep(new Random().nextInt(10) * 1000);
                LOG.info("queue poll: " + new String(queue.poll()));
                return RunnableFuture.DONE;
            }

        });
    }

}
