neo-up:
	docker run --rm -d --name neo \
			-p 7474:7474 \
			-p 7687:7687 \
			--env=NEO4J_AUTH=none \
			neo4j:3.5.3

neo-down:
	docker ps | grep neo | awk '{print $1}' | xargs docker stop


