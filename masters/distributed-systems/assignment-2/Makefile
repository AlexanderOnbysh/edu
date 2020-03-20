mongo-up:
	docker run --rm -d --name mongo \
			-p 27017:27017 \
			-e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
			-e MONGO_INITDB_ROOT_PASSWORD=secret \
			-e MONGO_INITDB_DATABASE=db \
			mongo:latest

mongo-down:
	docker ps | grep mongo | awk '{print $1}' | xargs docker stop


