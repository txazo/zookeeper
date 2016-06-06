package test.zookeeper.junit4;

public class ZooKeeperJUnitException extends RuntimeException {

    private static final long serialVersionUID = 124922327533991267L;

    public ZooKeeperJUnitException() {
    }

    public ZooKeeperJUnitException(String message) {
        super(message);
    }

    public ZooKeeperJUnitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZooKeeperJUnitException(Throwable cause) {
        super(cause);
    }

}
