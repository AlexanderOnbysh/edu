#!/bin/bash

echo "Deleting bucket ucu-test-bucket"
aws s3 rb s3://ucu-test-bucket --force
