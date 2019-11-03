#!/bin/bash

echo "Creating topic with billing alerts"
topic=$(aws sns create-topic \
  --name "BillingAlert" \
  --output "text" \
  --tags "Key=env,Value=assignment" \
  --query 'TopicArn'
)

echo "Subscribing to $topic"
aws sns subscribe \
--topic-arn "$topic" \
--protocol email \
--notification-endpoint "alexander.onbysh@gmail.com"

echo "Setting up billing alarm"
aws cloudwatch put-metric-alarm \
--alarm-name "billing-alarm" \
--namespace AWS/Billing \
--metric-name EstimatedCharges \
--evaluation-periods 1 \
--period 21600 \
--statistic Maximum \
--comparison-operator GreaterThanThreshold \
--dimensions "Name=Currency,Value=USD" \
--threshold "0" \
--actions-enabled \
--alarm-actions "$topic"
