mkdir -p /var/lib/zookeeper/data/server1
mkdir -p /var/lib/zookeeper/data/server2
mkdir -p /var/lib/zookeeper/data/server3
mkdir -p /var/lib/zookeeper/data/server4
mkdir -p /var/lib/zookeeper/data/server5

mkdir -p /var/lib/zookeeper/datalog/server1
mkdir -p /var/lib/zookeeper/datalog/server2
mkdir -p /var/lib/zookeeper/datalog/server3
mkdir -p /var/lib/zookeeper/datalog/server4
mkdir -p /var/lib/zookeeper/datalog/server5

echo "1" > /var/lib/zookeeper/data/server1/myid
echo "2" > /var/lib/zookeeper/data/server2/myid
echo "3" > /var/lib/zookeeper/data/server3/myid
echo "4" > /var/lib/zookeeper/data/server4/myid
echo "5" > /var/lib/zookeeper/data/server5/myid
