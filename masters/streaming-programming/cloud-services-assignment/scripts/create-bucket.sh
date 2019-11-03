#!/bin/bash

name="ucu-test-bucket"
echo "Creating $name bucket"
aws s3api create-bucket --bucket $name --region eu-central-1 --create-bucket-configuration LocationConstraint=eu-central-1
aws s3api wait bucket-exists --bucket ucu-test-bucket

echo "Uploading datasets"
wget http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz
aws s3 cp train-images-idx3-ubyte.gz s3://$name/

rm train-images-idx3-ubyte.gz 
