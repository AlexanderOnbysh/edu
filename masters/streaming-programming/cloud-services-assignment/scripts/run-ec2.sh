#!/bin/bash

keypair_path=file://~/.ssh/id_rsa.pub
key_exist=$(aws ec2 describe-key-pairs --filters "Name=key-name,Values=alexanderonbysh" | jq ".KeyPairs[0]")
if test -z "$key_exist"; then
	echo "Uploading key pair"
	aws ec2 import-key-pair --key-name "alexanderonbysh" --public-key-material $keypair_path
fi

echo "Running EC2 instance"
config=$(aws ec2 run-instances --image-id ami-0f3a43fbf2d3899f7 --count 1 --instance-type t2.micro --key-name alexanderonbysh)
instance_id=$(jq -n "$config" | jq -r ".Instances[0].InstanceId")
security_group=$(jq -n "$VAR" | jq -r ".Instances[0].NetworkInterfaces[0].Groups[0].GroupId")

echo "Tagging $instance_id with assignment tag"
aws ec2 create-tags --resources "$instance_id" --tags "Key=env,Value=assignment"

Echo "Waiting for instance to start $instance_id"
aws ec2 wait instance-status-ok --instance-ids $instance_id
