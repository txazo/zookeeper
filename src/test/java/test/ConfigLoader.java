package test;

import java.util.Properties;

public abstract class ConfigLoader extends FieldTraverser {

    private static final String CONFIG = "/zookeeper.properties";
    private static final String SERVER_LIST = "zookeeper.server.list";
    protected static final int SESSION_TIMEOUT = 3000;

    protected String getServerList() {
        return loadConfig().getProperty(SERVER_LIST);
    }

    private Properties loadConfig() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream(CONFIG));
        } catch (Exception e) {
            throw new RuntimeException("config load failed");
        }
        return prop;
    }

}
