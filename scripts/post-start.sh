#!/bin/sh

apk add --no-cache curl jq

echo '⏳ Aguardando Keycloak subir...'
until curl -s http://keycloak:8080/realms/master; do
  sleep 2
done

TOKEN=$(curl -s \
  -d 'client_id=admin-cli' \
  -d 'username=admin' \
  -d 'password=admin' \
  -d 'grant_type=password' \
  http://keycloak:8080/realms/master/protocol/openid-connect/token \
  | jq -r .access_token)

curl --request POST \
  --url http://keycloak:8080/admin/realms \
  --header "Authorization: Bearer $TOKEN" \
  --header 'Content-Type: application/json' \
  --data "@scripts/realm.json"

ADMIN_CLIENT_SECRET=$(curl --request GET \
  --url 'http://keycloak:8080/admin/realms/TodoAAA/clients/?clientId=admin-client' \
  --header "Authorization: Bearer $TOKEN" \
  | jq -r '.[0].secret')

TODO_CLIENT_SECRET=$(curl --request GET \
  --url 'http://keycloak:8080/admin/realms/TodoAAA/clients/?clientId=todo-client' \
  --header "Authorization: Bearer $TOKEN" \
  | jq -r '.[0].secret')

echo "Senha Admin Client -> $ADMIN_CLIENT_SECRET"
echo "Senha Todo Client -> $TODO_CLIENT_SECRET"

