# Assignment 5: MongoDB

## Logs

Logs are located in `logs` direcotry

## Code

```makefile
CMD = 'var cfg = {\
	"_id": "mongo-network",\
	"protocolVersion": 1,\
	"members": [\
		{\
			"_id": 0,\
			"host": "mongo1:27017"\
		},\
		{\
			"_id": 1,\
			"host": "mongo2:27017"\
		},\
		{\
			"_id": 2,\
			"host": "mongo3:27017"\
		}\
	]\
};\
rs.initiate(cfg, { force: true });\
rs.reconfig(cfg, { force: true });\
'

clean:
		docker kill mongo1 mongo2 mongo3; docker rm mongo1 mongo2 mongo3
		docker network rm mongo-network
		rm -rf data

test:
		docker exec -it mongo1 mongo --eval 'db.isMaster();'
		docker exec -it mongo2 mongo --eval 'db.isMaster();'
		docker exec -it mongo3 mongo --eval 'db.isMaster();'

task1-init:
		docker network create mongo-network
		# primary node
		docker run -d \
				-v data:/root/data \
				-p 10000:27017 \
				--name mongo1 \
				--net mongo-network \
				mongo mongod --replSet mongo-network
		# secondary
		docker run -d \
				-p 10001:27017 \
				--name mongo2 \
				--net mongo-network \
				mongo mongod --replSet mongo-network
		docker run -d \
				-p 10002:27017 \
				--name mongo3 \
				--net mongo-network \
				mongo mongod --replSet mongo-network
		# wait for DB to initialize
		sleep 3
		docker exec -it mongo1 mongo --eval ${CMD}
		sleep 1
		# set mongo1 to master
		docker exec -it mongo1 mongo --quiet --eval 'cfg = rs.conf(); cfg.members[0].priority = 1; cfg.members[1].priority = 0.5; cfg.members[2].priority = 0.5; rs.reconfig(cfg);'
		docker exec -it mongo2 mongo --quiet --eval 'cfg = rs.conf(); cfg.members[0].priority = 1; cfg.members[1].priority = 0.5; cfg.members[2].priority = 0.5; rs.reconfig(cfg);'
		docker exec -it mongo3 mongo --quiet --eval 'cfg = rs.conf(); cfg.members[0].priority = 1; cfg.members[1].priority = 0.5; cfg.members[2].priority = 0.5; rs.reconfig(cfg);'
		sleep 10
		docker exec -it mongo1 mongo --eval 'db.isMaster();'
		docker exec -it mongo2 mongo --eval 'db.isMaster();'
		docker exec -it mongo3 mongo --eval 'db.isMaster();'


task2-write-concerns:
		docker exec -it mongo1 mongo --eval "\
		db.coll.insert({command: 'acknowledged: off; jornaling: off'}, { writeConcern: { w: 0, j: false } });\
		db.coll.find({command : 'acknowledged: off; jornaling: off'});\
		"
		docker exec -it mongo1 mongo --eval "\
		db.coll.insert({command: 'acknowledged: on; jornaling: off'}, { writeConcern: { w: 1, j: false } });\
		db.coll.find({command : 'acknowledged: on; jornaling: off'});\
		"
		docker exec -it mongo1 mongo --eval "\
		db.coll.insert({command: 'acknowledged: on; jornaling: on'}, { writeConcern: { w: 1, j: true } });\
		db.coll.find({command : 'acknowledged: on; jornaling: on'});\
		"
		docker exec -it mongo1 mongo --eval "\
		db.coll.insert({command: 'acknowledged: on(replica); jornaling: on'}, { writeConcern: { w: 3, j: true } });\
		db.coll.find({command : 'acknowledged: on; jornaling: on'});\
		"
task3-read-modes:
		docker exec -it mongo1 mongo --eval "\
				db.coll.insert({command: 'Task2-a: acknowledged: off; jornaling: off'}, { writeConcern: { w: 0, j: false } });\
				db.coll.find({command : 'Task2-a: acknowledged: off; jornaling: off'}).readPref('primary');\
		"
		docker exec -it mongo1 mongo --eval "\
				db.coll.insert({command: 'Task2-b: acknowledged: off; jornaling: off'}, { writeConcern: { w: 0, j: false } });\
				db.coll.find({command : 'Task2-b: acknowledged: off; jornaling: off'}).readPref('secondary');\
		"
		
task4-disable-node:
		docker stop mongo3
		sleep 1
		docker exec -i mongo1 mongo --eval "\
		db.coll.insert({command: 'acknowledged: on(replica); jornaling: on'}, { writeConcern: { w: 3, j: true } });\
		db.coll.find({command : 'acknowledged: on; jornaling: on'});\
		" &
		echo "Start node"
		docker start mongo3
		sleep 3

task5-election:
		docker exec -it mongo1 mongo --eval "rs.status()"
		docker stop mongo1
		sleep 5
		docker exec -it mongo2 mongo --eval "rs.status()"
		docker exec -it mongo2 mongo --eval "db.coll.insert({command: 'WRITE NODE2'}, { writeConcern: { w: 1}}); db.coll.find({command : 'WRITE NODE2'});"
		docker start mongo1
		sleep 5
		docker exec -it mongo1 mongo --eval "rs.status()"
		docker exec -it mongo1 mongo --eval "rs.slaveOk(); db.coll.find({command : 'WRITE NODE2'});"

task6-inconsistency:
		docker stop mongo3
		docker exec -it mongo1 mongo --eval "db.coll.insert({command: 'WRITE TO MASTER'}, { writeConcern: { w: 0}}); db.coll.find({command : 'WRITE TO MASTER'});"
		sleep 3
		docker stop mongo1
		docker start mongo3
		sleep 3
		docker exec -it mongo3 mongo --eval "rs.slaveOk(); db.coll.find({command : 'WRITE TO MASTER'});"
		docker start mongo1
		sleep 3
		docker exec -it mongo2 mongo --eval "rs.slaveOk(); db.coll.find({command : 'WRITE TO MASTER'});"

task7-read-concern:
		docker exec -it mongo1 mongo --eval "db.coll.insert({command: 'local'}, { writeConcern: { w: 1}});"
		docker exec -it mongo2 mongo --eval "rs.slaveOk(); db.coll.find({command : 'local'}).readConcern('local');"
		docker exec -it mongo1 mongo --eval "db.coll.insert({command: 'majority'}, { writeConcern: { w: 1}});"
		docker exec -it mongo2 mongo --eval "rs.slaveOk(); db.coll.find({command : 'local'}).readConcern('majority');"
		docker exec -it mongo1 mongo --eval "db.coll.insert({command: 'linearizable'}, { writeConcern: { w: 1}});"
		docker exec -it mongo1 mongo --eval "rs.slaveOk(); db.coll.find({command : 'local'}).readConcern('linearizable');"
		
task8-eventual-consistency:
		docker exec -it mongo1 mongo --eval "db.coll.insert({command: 'command before delay'}, { writeConcern: { w: 1}});"
		docker exec -it mongo1 mongo --quiet --eval 'cfg = rs.conf(); cfg.members[2].priority = 0; cfg.members[2].hidden=true; cfg.members[2].slaveDelay=100; rs.reconfig(cfg);'
		docker exec -it mongo1 mongo --eval "db.coll.insert({command: 'command after delay'}, { writeConcern: { w: 1}});"
		sleep 5
		docker exec -it mongo2 mongo --eval "rs.slaveOk(); db.coll.find({command : /delay/});"
		docker exec -it mongo3 mongo --eval "rs.slaveOk(); db.coll.find({command : /delay/});"
```
