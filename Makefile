build:
	docker build -t upraised/jobportal:1.0 ./application
	docker build -t mypostgres:10.12 ./database

run:
	docker stack deploy --compose-file docker-compose.yml upraised

stop:
	docker stack rm upraised

bootstrap-data:
	docker-compose -f ./database/docker-compose.yml up -d
	sleep 30
	docker exec -t my-postgres-container pg_restore -d job_portal /tmp/job_portal_db_dump.dump -U test
	docker stop my-postgres-container
	docker swarm init
