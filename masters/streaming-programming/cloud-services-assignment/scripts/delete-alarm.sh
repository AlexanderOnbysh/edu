#!/bin/bash

echo "Delete billing alarm"

topic=$(aws sns list-topics | jq -r '.Topics[] | select(.TopicArn|test("BillingAlert")).TopicArn')
if test -n "$topic"; then
	echo "Deleting sns topic: $topic"
	aws sns delete-topic --topic-arn $topic
fi


echo "Deleting cloudwatch alarm"
aws cloudwatch delete-alarms --alarm-names "billing-alarm"
