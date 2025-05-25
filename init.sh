docker-compose down
docker network prune -f
docker container prune -f
sudo rm keycloak_data/ postgres_data/ -rf
docker-compose up --build