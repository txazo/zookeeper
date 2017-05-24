package test.server;

import org.apache.zookeeper.server.quorum.QuorumPeerMain;

public class QuorumPeerMainTest {

    public static void main(String[] args) throws Exception {
        int count = 5;
        for (int i = 1; i <= count; i++) {
            new Thread(new ZookeeperServer("/zoo" + i + ".cfg")).start();
        }
        System.in.read();
    }

    private static class ZookeeperServer implements Runnable {

        private String config;

        public ZookeeperServer(String config) {
            this.config = config;
        }

        @Override
        public void run() {
            QuorumPeerMain.main(new String[]{ZookeeperServer.class.getResource(config).getPath()});
        }

    }

}
