package test.server;

import org.apache.zookeeper.server.quorum.QuorumPeerMain;

public class SingleServerTest {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("");
        }
        int i = Integer.valueOf(args[0]);
        QuorumPeerMain.main(new String[]{SingleServerTest.class.getResource("/zoo" + i + ".cfg").getPath()});
        System.in.read();
    }

}
