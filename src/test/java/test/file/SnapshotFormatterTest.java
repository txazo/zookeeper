package test.file;

import org.apache.zookeeper.server.SnapshotFormatter;

public class SnapshotFormatterTest {

    public static void main(String[] args) throws Exception {
        String snapshotFile = "/var/lib/zookeeper/data/server1/version-2/snapshot.3000109f7";
        SnapshotFormatter.main(new String[]{snapshotFile});
    }

}
