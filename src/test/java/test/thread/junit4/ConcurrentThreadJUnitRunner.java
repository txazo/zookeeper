package test.thread.junit4;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentThreadJUnitRunner extends BlockJUnit4ClassRunner {

    public ConcurrentThreadJUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement statement = super.methodInvoker(method, test);

        if (!ConcurrentThreadJUnitTest.class.isAssignableFrom(test.getClass())) {
            return statement;
        }

        Concurrency concurrency = method.getAnnotation(Concurrency.class);
        if (concurrency == null) {
            return statement;
        }

        return new ConcurrentThreadStatement(method, test, statement, concurrency);
    }

    private class ConcurrentThreadStatement extends Statement {

        private FrameworkMethod method;
        private Object test;
        private Statement statement;
        private Concurrency concurrency;
        private RunnableCallable callable;

        public ConcurrentThreadStatement(FrameworkMethod method, Object test, Statement statement, Concurrency concurrency) {
            this.method = method;
            this.test = test;
            this.statement = statement;
            this.concurrency = concurrency;
        }

        @Override
        public void evaluate() throws Throwable {
            statement.evaluate();
            execute();
        }

        private void execute() throws Throwable {
            if (existsCallable()) {
                if (concurrency.pooled()) {
                    executeThreadWithPool();
                } else {
                    executeThread();
                }
            }
        }

        private boolean existsCallable() throws Exception {
            callable = (RunnableCallable) test.getClass().getMethod("getCallable").invoke(test);
            return callable != null;
        }

        private void executeThread() throws Throwable {
            final CyclicBarrier begin = new CyclicBarrier(concurrency.count());
            final CountDownLatch end = new CountDownLatch(concurrency.count());
            for (int i = 0; i < concurrency.count(); i++) {
                final int j = i;
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            begin.await();
                            callable.run(j).waitDone();
                        } catch (Exception e) {
                            throw new ConcurrentThreadJUnitException("callable run error", e);
                        } finally {
                            end.countDown();
                        }
                    }

                }).start();
            }
            end.await();
        }

        private void executeThreadWithPool() throws Throwable {
            final CountDownLatch end = new CountDownLatch(concurrency.count());
            ExecutorService threadPool = Executors.newFixedThreadPool(concurrency.poolCount());
            for (int i = 0; i < concurrency.count(); i++) {
                final int j = i;
                threadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            callable.run(j).waitDone();
                        } catch (Exception e) {
                            throw new ConcurrentThreadJUnitException("callable run error", e);
                        } finally {
                            end.countDown();
                        }
                    }

                });
            }
            end.await();
        }

    }

}
