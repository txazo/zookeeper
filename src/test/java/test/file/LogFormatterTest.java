package test.file;

import org.apache.zookeeper.server.LogFormatter;

public class LogFormatterTest {

    public static void main(String[] args) throws Exception {
        String logFile = "/var/lib/zookeeper/datalog/server1/version-2/log.3000109f9";
        LogFormatter.main(new String[]{logFile});
    }

}
