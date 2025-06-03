sudo docker-compose down
sudo docker network prune -f
sudo docker container prune -f
sudo rm keycloak_data/ postgres_data/ -rf
sudo docker-compose up --build