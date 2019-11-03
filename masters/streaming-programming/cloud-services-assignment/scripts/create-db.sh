#!/bin/bash

echo "Creating DB"
config=$(aws rds create-db-instance \
	    --allocated-storage 20 --db-instance-class db.t2.micro \
	    --db-instance-identifier ucu-test-instance \
	    --engine postgres \
	    --master-username master \
	    --master-user-password Re69m3RT8SYdxcwg)

instance_id=$(jq -n "$config" | jq -r ".DBInstance.DBInstanceIdentifier")

echo "Waiting for DB $instance_id"
aws rds wait db-instance-available --db-instance-identifier $instance_id
