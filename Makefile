build:
	docker build -t upraised/jobportal:1.0 ./application
	docker build -t postgres:10.12 ./database

run:
	#docker swarm init
	docker stack deploy --compose-file docker-compose.yml upraised

stop:
	docker stack rm upraised
