package test.thread.junit4;

public interface RunnableFuture {

    public static final RunnableFuture DONE = new RunnableFuture() {

        @Override
        public void waitDone() {
        }

        @Override
        public void setDone() {
        }

    };

    public void waitDone();

    public void setDone();

}
