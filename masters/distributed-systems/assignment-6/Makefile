clean:
	docker-compose down

drop:
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "drop keyspace \"space1\";"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "drop keyspace \"space2\";"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "drop keyspace \"space3\";"

task1-init:
	docker-compose up -d

task2-status:
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool status"

task3-keyspaces:
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "CREATE KEYSPACE \"space1\" WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "CREATE KEYSPACE \"space2\" WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 2 };"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "CREATE KEYSPACE \"space3\" WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };"

task4-tables:
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "CREATE TABLE space1.table1 (id UUID, field text, PRIMARY KEY(id, field));"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "DESCRIBE space1.table1;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "CREATE TABLE space2.table2 (id UUID, field text, PRIMARY KEY(id, field));"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "DESCRIBE space2.table2;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "CREATE TABLE space3.table3 (id UUID, field text, PRIMARY KEY(id, field));"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "DESCRIBE space3.table3;"

task5-read-write:
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra \
		-e "INSERT INTO space1.table1 (id, field) VALUES (0ab09bec-e68e-48d9-a5f8-97e6fb4c9b47, 'space1');"
	docker exec -it assignment-6_cassandra2_1 cqlsh --password cassandra --username cassandra \
		-e "INSERT INTO space2.table2 (id, field) VALUES (1ab09bec-e68e-48d9-a5f8-97e6fb4c9b47, 'space2');"
	docker exec -it assignment-6_cassandra3_1 cqlsh --password cassandra --username cassandra \
		-e "INSERT INTO space3.table3 (id, field) VALUES (2ab09bec-e68e-48d9-a5f8-97e6fb4c9b47, 'space3');"
	docker exec -it assignment-6_cassandra3_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space1.table1;"
	docker exec -it assignment-6_cassandra2_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space2.table2;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"

task6-data-distribution:
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool status space1"
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool status space2"
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool status space3"

task7-record-location:
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool getendpoints space1 table1 '0ab09bec-e68e-48d9-a5f8-97e6fb4c9b47'"
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool getendpoints space2 table2 '1ab09bec-e68e-48d9-a5f8-97e6fb4c9b47'"
	docker exec -it assignment-6_cassandra_1 bash -c "nodetool getendpoints space3 table3 '2ab09bec-e68e-48d9-a5f8-97e6fb4c9b47'"

task8-disconnected-node:
	docker network disconnect assignment-6_default assignment-6_cassandra3_1
	echo 'CONSISTENCY ONE'
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY ONE;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space1.table1 (id, field) VALUES (now(), 'space1');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space1.table1;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space2.table2 (id, field) VALUES (now(), 'space2');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space2.table2;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'space3');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	echo 'CONSISTENCY TWO'
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY TWO;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space1.table1 (id, field) VALUES (now(), 'space1');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space1.table1;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space2.table2 (id, field) VALUES (now(), 'space2');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space2.table2;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'space3');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	echo 'CONSISTENCY QUORUM'
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY QUORUM;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space1.table1 (id, field) VALUES (now(), 'space1');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space1.table1;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space2.table2 (id, field) VALUES (now(), 'space2');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space2.table2;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'space3');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	echo 'CONSISTENCY SERIAL'
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY SERIAL;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space1.table1 (id, field) VALUES (now(), 'space1');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space1.table1;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space2.table2 (id, field) VALUES (now(), 'space2');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space2.table2;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'space3');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	echo 'CONSISTENCY ALL'
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY ALL;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space1.table1 (id, field) VALUES (now(), 'space1');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space1.table1;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space2.table2 (id, field) VALUES (now(), 'space2');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space2.table2;"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'space3');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	docker network connect assignment-6_default assignment-6_cassandra3_1


task9-disconnect:
	docker network disconnect assignment-6_default assignment-6_cassandra_1
	docker network disconnect assignment-6_default assignment-6_cassandra2_1
	docker network disconnect assignment-6_default assignment-6_cassandra3_1

task10-write-to-nodes:
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY ONE;"
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'node 1');"; exit 0
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	docker exec -it assignment-6_cassandra2_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY ONE;"
	docker exec -it assignment-6_cassandra2_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'node 2');"; exit 0
	docker exec -it assignment-6_cassandra2_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	docker exec -it assignment-6_cassandra3_1 cqlsh --password cassandra --username cassandra -e "CONSISTENCY ONE;"
	docker exec -it assignment-6_cassandra3_1 cqlsh --password cassandra --username cassandra -e "INSERT INTO space3.table3 (id, field) VALUES (now(), 'node 3');"; exit 0
	docker exec -it assignment-6_cassandra3_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0

task11-enable-on:
	docker network connect assignment-6_default assignment-6_cassandra_1
	docker network connect assignment-6_default assignment-6_cassandra2_1
	docker network connect assignment-6_default assignment-6_cassandra3_1
	sleep 30
	docker exec -it assignment-6_cassandra_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	docker exec -it assignment-6_cassandra2_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0
	docker exec -it assignment-6_cassandra3_1 cqlsh --password cassandra --username cassandra -e "SELECT * FROM space3.table3;"; exit 0




