@echo off
docker-compose down
docker network prune -f
docker container prune -f

rmdir /s /q keycloak_data
rmdir /s /q postgres_data

docker-compose up --build
pause
