# Cloud services assignment

## Assignments
### Exercise 1:
Create your AWS Free Tier account and setup a Billing Alarm using tutorial from AWS documentation:
https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/monitor_estimated_charges_with_cloudwatch.html

### Exercise 2:
Use your AWS Free Tier account to configure, launch and access your Amazon EC2 instance
https://aws.amazon.com/ec2/getting-started/

### Exercise 3:
Amazon Simple Storage Service (Amazon S3) is storage for the Internet. In this exercise you need to create S3 bucket and publish any open dataset available online
https://docs.aws.amazon.com/AmazonS3/latest/gsg/GetStartedWithS3.html

### Exercise 4:
Deploy any REST web app to EC2 instance created in Exercise 2 and verify it from local env using curl. In addition to SSH port in Security Group you need to open a port for your web app
Web app example: https://github.com/dropwizard/dropwizard/tree/master/dropwizard-example

### Exercise 5:
You need to use the Amazon RDS Free Tier to gain hands-on experience with Amazon RDS. Create PostgreSQL database using the following tutorial, configure web app created in Exercise 4 to use it and query application data with psql 
https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/CHAP_GettingStarted.CreatingConnecting.PostgreSQL.html

### Exercise 6:
Delete all resources created in previous exercises and provide your Free Tier usage details https://console.aws.amazon.com/billing/home?#/freetier 

## Solution

### Create all resources
```sh
make run
```

### Clean all resources
```sh
make clean
```

### Run Python web-server 
```sh
python3 -m venv .venv
. ./.venv/bin/activate
pip install flask  psycopg2-binary

FLASK_APP=app.py python3 -m flask run -h 0.0.0.0 -p 8080
```

### curl test
```sh
curl http://ec2-52-57-232-150.eu-central-1.compute.amazonaws.com:8080/table

curl http://ec2-52-57-232-150.eu-central-1.compute.amazonaws.com:8080/table
```
