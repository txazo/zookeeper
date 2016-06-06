## Zookeeper

#### ZNode

/node/node_child
    node_child_data
    Stat
    ACL

#### Stat

* czxid = 0x19, 节点创建时的zxid
* mzxid = 0x19, 节点最后修改时的zxid
* ctime = Sat Jun 04 20:49:33 CST 2016, 节点创建的时间
* mtime = Sat Jun 04 20:49:33 CST 2016, 节点最后修改的时间
* version = 0, 节点的数据改变的次数
* cversion = 0, 子节点改变的次数, 包括子节点的新增和删除
* aversion = 0, 节点的ACL改变的次数
* ephemeralOwner = 0x0, 临时节点所有者的session id
* dataLength = 4, 节点的数据长度
* numChildren = 0, 子节点的数量

#### Session

* session id
* password
* client
* session timeout: 2 tickTime ~ 20 tickTime
* heartbeat

#### Watch

#### ACL

* scheme:id,  perms
* scheme: world, auth, digest, ip
* id:
* perms: CREATE, READ, WRITE, DELETE, ADMIN

* world:anyone
* ip:19.22.0.0/16
