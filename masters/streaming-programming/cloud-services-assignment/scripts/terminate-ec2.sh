#!/bin/bash

instance_id=$(aws ec2 describe-tags --filters "Name=resource-type,Values=instance,Name=value,Values=assignment" | jq -r ".Tags[].ResourceId")

if test -n "$instance_id"; then
	echo "Terminating $instance_id"
	aws ec2 terminate-instances --instance-ids $instance_id
fi
