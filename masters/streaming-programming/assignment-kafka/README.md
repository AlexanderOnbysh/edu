# Kafka assignment

## Spinup stack

```bash
export DOCKER_HOST_IP=127.0.0.1
docker-compose up -d
```

All data will be saved to `kafka-data`

## Commands

```bash
# Create topic
bin/kafka-topics --zookeeper :2181 --create --topic t1 --partitions 3 --replication-factor 2

# List topics
bin/kafka-topics --zookeeper :2181 --list

# Describe topic
bin/kafka-topics --zookeeper :2181 --describe --topic t1

# Run consumer
bin/kafka-console-consumer --bootstrap-server :9092 --topic output --property print.key=true

# Run producer
bin/kafka-console-producer --broker-list 127.0.0.1:9092 --topic input
```

---

## Task 1

```bash
$ unzip confluent-5.3.1-2.12.zip

Archive:  confluent-5.3.1-2.12.zip
   creating: confluent-5.3.1/
   creating: confluent-5.3.1/src/
...
Completed
------------------------------------------------------------

$ export PATH=$PWD/bin:$PATH
```

## Task 2

```bash
docker-compose up -d
```

## Task 3

```bash
$ bin/zookeeper-shell :2181
Connecting to :2181
Welcome to ZooKeeper!
JLine support is enabled

WATCHER::

WatchedEvent state:SyncConnected type:None path:null
[zk: :2181(CONNECTED) 0] ls
[zk: :2181(CONNECTED) 1] ls /brokers/ids
[1, 2, 3]
[zk: :2181(CONNECTED) 2] get /brokers/ids/0
Node does not exist: /brokers/ids/0
[zk: :2181(CONNECTED) 3] get /brokers/ids/1
{"listener_security_protocol_map":{"PLAINTEXT":"PLAINTEXT"},"endpoints":["PLAINTEXT://kafka-1:19092"],"jmx_port":-1,"host":"kafka-1","timestamp":"1575552287912","port":19092,"version":4}
cZxid = 0x10000003c
ctime = Thu Dec 05 15:24:47 EET 2019
mZxid = 0x10000003c
mtime = Thu Dec 05 15:24:47 EET 2019
pZxid = 0x10000003c
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0x300003f0bb10002
dataLength = 186
numChildren = 0
[zk: :2181(CONNECTED) 4] get /controller
{"version":1,"brokerid":1,"timestamp":"1575552288082"}
cZxid = 0x10000003e
ctime = Thu Dec 05 15:24:48 EET 2019
mZxid = 0x10000003e
mtime = Thu Dec 05 15:24:48 EET 2019
pZxid = 0x10000003e
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x300003f0bb10002
dataLength = 54
numChildren = 0
[zk: :2181(CONNECTED) 5]
```

## Task 4

```bash
$ bin/kafka-topics --zookeeper :2181 --create --topic t1 --partitions 3 --replication-factor 2
Created topic t1.
------------------------------------------------------------
$ bin/kafka-topics --zookeeper :2181 --list
__confluent.support.metrics
__consumer_offsets
input
output
t1
------------------------------------------------------------
$ bin/kafka-topics --zookeeper :2181 --describe --topic t1
Topic:t1	PartitionCount:3	ReplicationFactor:2	Configs:
	Topic: t1	Partition: 0	Leader: 2	Replicas: 2,3	Isr: 2,3
	Topic: t1	Partition: 1	Leader: 3	Replicas: 3,1	Isr: 3,1
	Topic: t1	Partition: 2	Leader: 1	Replicas: 1,2	Isr: 1,2
```

## Task 5

### Create topic

```bash
$ bin/kafka-topics --zookeeper :2181 --create --topic t2 --partitions 3 --replication-factor 2
Error while executing topic command : Topic 't2' already exists.
[2019-12-06 12:01:57,452] ERROR org.apache.kafka.common.errors.TopicExistsException: Topic 't2' already exists.
 (kafka.admin.TopicCommand$)
 ```

### I producer I consumer
```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
1
2
3
4
```

### I producer II consumer

#### Consumer I

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
1
2
3
4
7
```

#### Consumer II

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
5
6
8
9
```

### I producer III consumer

#### Consumer I

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
1
2
3
4
7
10
13
16
19
```

#### Consumer II

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
5
6
8
9
11
14
17
20
```

#### Consumer III

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
12
15
18
```

### I producer II consumer (shutdown first)

#### Consumer II

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
5
6
8
9
11
14
17
20
22
25
28
```

#### Consumer III

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
12
15
18
21
23
24
26
27
```

### Kill a leader

View leader
```bash
$ bin/kafka-topics --zookeeper :2181 --describe --topic t2
Topic:t2    PartitionCount:3    ReplicationFactor:2 Configs:
Topic: t2   Partition: 0    Leader: 1   Replicas: 1,3   Isr: 1,3
Topic: t2   Partition: 1    Leader: 2   Replicas: 2,1   Isr: 1,2
Topic: t2   Partition: 2    Leader: 3   Replicas: 3,2   Isr: 2,3
```

View container id of a leader
```
$ docker ps
CONTAINER ID        IMAGE                          COMMAND                  CREATED             STATUS              PORTS                                                  NAMES
69e1c3824aa4        confluentinc/cp-kafka:latest   "/etc/confluent/dock…"   17 minutes ago      Up 17 minutes       0.0.0.0:9092->9092/tcp                                 assignment-kafka_kafka-1_1
a58f71a19a0d        confluentinc/cp-kafka:latest   "/etc/confluent/dock…"   17 minutes ago      Up 17 minutes       9092/tcp, 0.0.0.0:9093->9093/tcp                       assignment-kafka_kafka-2_1
14a4c76044ef        confluentinc/cp-kafka:latest   "/etc/confluent/dock…"   17 minutes ago      Up 17 minutes       9092/tcp, 0.0.0.0:9094->9094/tcp                       assignment-kafka_kafka-3_1
12f238be6550        zookeeper:3.4.9                "/docker-entrypoint.…"   23 minutes ago      Up 17 minutes       2181/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2182->2182/tcp   assignment-kafka_zookeeper-2_1
2bde8b43c3ac        zookeeper:3.4.9                "/docker-entrypoint.…"   23 minutes ago      Up 17 minutes       2181/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2183->2183/tcp   assignment-kafka_zookeeper-3_1
20106962040d        zookeeper:3.4.9                "/docker-entrypoint.…"   23 minutes ago      Up 17 minutes       2888/tcp, 0.0.0.0:2181->2181/tcp, 3888/tcp             assignment-kafka_zookeeper-1_1
------------------------------------------------------------
$ docker kill 69e1c3824aa4
69e1c3824aa4
------------------------------------------------------------
```

Consumer's log

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic t2 --group CG1
12
15
18
21
23
24
26
27
[2019-12-06 18:18:50,130] WARN [Consumer clientId=consumer-1, groupId=CG1] Connection to node 1 (/127.0.0.1:9092) could not be established. Broker may not be available. (org.apache.kafka.clients.NetworkClient)
[2019-12-06 18:18:51,171] WARN [Consumer clientId=consumer-1, groupId=CG1] Connection to node 1 (/127.0.0.1:9092) could not be established. Broker may not be available. (org.apache.kafka.clients.NetworkClient)
2
1
4
8
```

## Task 6

### Logs from consumer

```text
Start: 2019-12-06T18:54:59.001
Message 2019-12-06T18:54:59.225
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
Message 2019-12-06T18:54:59.226
End: 2019-12-06T18:54:59.226
```

### What does Callback give when you send a message to an existing topic?

Callback is called and message is printed.

### What happens when sending a message to an unexisting topic?

The same as for an existed topic

### Mind the automatic topic creation feature

It's enabled by default

## Task 7

### SubTask 3

#### Consumer I

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Message 2019-12-06T21:28:14.129
Message 2019-12-06T21:28:14.182
Message 2019-12-06T21:28:14.237
Message 2019-12-06T21:28:14.292
Message 2019-12-06T21:28:14.346
Message 2019-12-06T21:28:14.400
Message 2019-12-06T21:28:14.451
Message 2019-12-06T21:28:14.502
Message 2019-12-06T21:28:14.556
Message 2019-12-06T21:28:14.606
Message 2019-12-06T21:28:14.661
Message 2019-12-06T21:28:14.716
Message 2019-12-06T21:28:14.770
Message 2019-12-06T21:28:14.825
Message 2019-12-06T21:28:14.878
Message 2019-12-06T21:28:14.932
Message 2019-12-06T21:28:14.987
Message 2019-12-06T21:28:15.038
Message 2019-12-06T21:28:15.089
Message 2019-12-06T21:28:15.142
```

#### Consumer II

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Start: 2019-12-06T21:28:13.904
End: 2019-12-06T21:28:15.142
```

### SubTask 4

#### Consumer I

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Message 2019-12-06T21:33:32.113
Message 2019-12-06T21:33:32.164
Message 2019-12-06T21:33:32.215
Message 2019-12-06T21:33:32.265
Message 2019-12-06T21:33:32.320
Message 2019-12-06T21:33:32.370
Message 2019-12-06T21:33:32.425
Message 2019-12-06T21:33:32.478
Message 2019-12-06T21:33:32.532
Message 2019-12-06T21:33:32.582
Message 2019-12-06T21:33:32.633
Message 2019-12-06T21:33:32.683
Message 2019-12-06T21:33:32.734
Message 2019-12-06T21:33:32.786
Message 2019-12-06T21:33:32.840
Message 2019-12-06T21:33:32.892
Message 2019-12-06T21:33:32.945
Message 2019-12-06T21:33:32.999
Message 2019-12-06T21:33:33.053
Message 2019-12-06T21:33:33.108
Hardcoded partition
```

#### Consumer II

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Start: 2019-12-06T21:33:31.893
End: 2019-12-06T21:33:33.108
Hardcoded partition
```

### SubTask 5

#### Consumer I

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Message 2019-12-06T21:46:01.508
Message 2019-12-06T21:46:01.666
Message 2019-12-06T21:46:01.826
Message 2019-12-06T21:46:01.985
Message 2019-12-06T21:46:02.146
Message 2019-12-06T21:46:02.300
Hardcoded partition
```

#### Consumer II

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Start: 2019-12-06T21:46:01.189
End: 2019-12-06T21:46:02.406
```

#### Consumer III

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task7 --group CG1
Message 2019-12-06T21:46:01.454
Message 2019-12-06T21:46:01.614
Message 2019-12-06T21:46:01.771
Message 2019-12-06T21:46:01.934
Message 2019-12-06T21:46:02.091
Message 2019-12-06T21:46:02.249
Message 2019-12-06T21:46:02.406
Hardcoded partition
```

## Task 8

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic task8 --group CG1
Message InstEmp(1,2)
Message InstEmp(2,4)
Message InstEmp(3,6)
Message InstEmp(4,8)
Message InstEmp(5,10)
Message InstEmp(6,12)
Message InstEmp(7,14)
Message InstEmp(8,16)
Message InstEmp(9,18)
Message InstEmp(10,20)
Message InstEmp(11,22)
Message InstEmp(12,24)
Message InstEmp(13,26)
Message InstEmp(14,28)
Message InstEmp(15,30)
Message InstEmp(16,32)
Message InstEmp(17,34)
Message InstEmp(18,36)
Message InstEmp(19,38)
Message InstEmp(20,40)
```

## Task 9

```text
Topic: task9,Key: null,Value: test, Offset: 0, Partition: 0
Topic: task9,Key: null,Value: hello, Offset: 1, Partition: 0
Topic: task9,Key: null,Value: one, Offset: 2, Partition: 0
Topic: task9,Key: null,Value: two, Offset: 3, Partition: 0
Topic: task9,Key: null,Value: here here, Offset: 4, Partition: 0
```

```bash
$ bin/kafka-console-producer --broker-list 127.0.0.1:9092 --topic task9
>test
>hello
>one
>two
>here here
>
```

## Task 10

#### Producer

```bash
$ bin/kafka-console-producer --broker-list 127.0.0.1:9092 --topic input
>one one two
>hello hello world
>test one test two test three one two three three three one test one three
>
```

#### Consumer

```bash
$ bin/kafka-console-consumer --bootstrap-server :9092 --topic output --property print.key=true
one 2
two	1
world   1
hello   2
test    4
one 4
two 2
three   5
```

#### Logs for application

```text
2019-12-06 23:37:02 INFO  Application$:23 - Got message: <one one two>
2019-12-06 23:37:02 INFO  Application$:31 - Send message: < one -> 2>
2019-12-06 23:37:02 INFO  Application$:31 - Send message: < two -> 1>
2019-12-06 23:39:11 INFO  Application$:23 - Got message: <one one two>
2019-12-06 23:39:11 INFO  Application$:31 - Send message: < one -> 2>
2019-12-06 23:39:11 INFO  Application$:31 - Send message: < two -> 1>
2019-12-06 23:39:32 INFO  Application$:23 - Got message: <one one two>
2019-12-06 23:39:32 INFO  Application$:31 - Send message: < one -> 2>
2019-12-06 23:39:32 INFO  Application$:31 - Send message: < two -> 1>
2019-12-06 23:40:06 INFO  Application$:23 - Got message: <one one two>
2019-12-06 23:40:06 INFO  Application$:31 - Send message: < one -> 2>
2019-12-06 23:40:06 INFO  Application$:31 - Send message: < two -> 1>
2019-12-06 23:40:11 INFO  Application$:23 - Got message: <hello hello world>
2019-12-06 23:40:11 INFO  Application$:31 - Send message: < world -> 1>
2019-12-06 23:40:11 INFO  Application$:31 - Send message: < hello -> 2>
2019-12-06 23:41:09 INFO  Application$:23 - Got message: <test one test two test three one two three three three one test one three>
2019-12-06 23:41:09 INFO  Application$:31 - Send message: < test -> 4>
2019-12-06 23:41:09 INFO  Application$:31 - Send message: < one -> 4>
2019-12-06 23:41:09 INFO  Application$:31 - Send message: < two -> 2>
2019-12-06 23:41:09 INFO  Application$:31 - Send message: < three -> 5>
```
