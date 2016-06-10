package test.thread.junit4;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(ConcurrentThreadJUnitRunner.class)
public class ConcurrentThreadJUnitTest extends Assert {

    protected static final Logger LOG = LoggerFactory.getLogger(ConcurrentThreadJUnitTest.class);

    private RunnableCallable callable;

    public RunnableCallable getCallable() {
        return callable;
    }

    public void setCallable(RunnableCallable callable) {
        this.callable = callable;
    }

}
