setup:
	python3 -m venv .env
	. ./.env/bin/activate
	pip install -r requirements.txt

run:
	docker-compose up -d
	python db.py
	docker-compose down


