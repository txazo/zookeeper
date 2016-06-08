echo "1" > /var/lib/zookeeper/data/server1/myid
echo "2" > /var/lib/zookeeper/data/server2/myid
echo "3" > /var/lib/zookeeper/data/server3/myid

bin/zkServer.sh start conf/zoo-server1.cfg
bin/zkServer.sh start conf/zoo-server2.cfg
bin/zkServer.sh start conf/zoo-server3.cfg

bin/zkServer.sh status conf/zoo-server1.cfg
bin/zkServer.sh status conf/zoo-server2.cfg
bin/zkServer.sh status conf/zoo-server3.cfg
