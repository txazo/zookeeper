package test.thread.junit4;

public class ConcurrentThreadJUnitException extends RuntimeException {

    private static final long serialVersionUID = -8135032048591878967L;

    public ConcurrentThreadJUnitException() {
        super();
    }

    public ConcurrentThreadJUnitException(String message) {
        super(message);
    }

    public ConcurrentThreadJUnitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrentThreadJUnitException(Throwable cause) {
        super(cause);
    }

}
