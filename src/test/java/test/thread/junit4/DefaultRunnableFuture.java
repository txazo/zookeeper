package test.thread.junit4;

public class DefaultRunnableFuture implements RunnableFuture {

    private volatile boolean done;

    @Override
    public synchronized void waitDone() {
        while (!done) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public synchronized void setDone() {
        done = true;
        notify();
    }

}
