## ZooKeeper

Apache ZooKeeper

## 说明

* ZooKeeper源码版本: release-3.4.8

## IDEA中ZooKeeper启动配置

* Main class: org.apache.zookeeper.server.quorum.QuorumPeerMain
* VM options: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false
* Program arguments: /Users/txazo/Txazo/zookeeper/src/main/resources/zoo.cfg

## 附录

[ZooKeeper Java API](http://zookeeper.apache.org/doc/current/api/index.html)

```
org.apache.zookeeper.server.SyncRequestProcessor
org.apache.zookeeper.server.quorum.FollowerRequestProcessor
org.apache.zookeeper.server.quorum.CommitProcessor
org.apache.zookeeper.server.quorum.QuorumCnxManager$SendWorker
org.apache.zookeeper.server.quorum.QuorumCnxManager$RecvWorker
org.apache.zookeeper.server.quorum.QuorumCnxManager$Listener
org.apache.zookeeper.server.quorum.QuorumPeer
org.apache.zookeeper.server.quorum.FastLeaderElection$Messenger$WorkerSender
org.apache.zookeeper.server.quorum.FastLeaderElection$Messenger$WorkerReceiver
org.apache.zookeeper.server.NIOServerCnxnFactory
org.apache.zookeeper.server.DatadirCleanupManager$PurgeTask
```

```
org.apache.zookeeper.ClientCnxn$EventThread
org.apache.zookeeper.ClientCnxn$SendThread
```
