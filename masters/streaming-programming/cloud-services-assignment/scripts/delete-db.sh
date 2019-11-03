#!/bin/bash

echo "Deleting RDS instance"
aws rds delete-db-instance --db-instance-identifier ucu-test-instance --skip-final-snapshot --delete-automated-backups
