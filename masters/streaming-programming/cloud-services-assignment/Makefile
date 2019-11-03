create-alarm:
	scripts/create-alarm.sh

delete-alarm:
	scripts/delete-alarm.sh

run-ec2:
	scripts/run-ec2.sh

terminate-ec2:
	scripts/terminate-ec2.sh

create-bucket:
	scripts/create-bucket.sh

delete-bucket:
	scripts/delete-bucket.sh

create-db:
	scripts/create-db.sh

delete-db:
	scripts/delete-db.sh


run: create-alarm run-ec2 create-bucket create-db
clean: delete-alarm terminate-ec2 delete-bucket delete-db
