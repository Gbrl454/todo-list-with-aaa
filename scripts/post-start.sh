#!/bin/bash

apk add --no-cache curl jq bash


KEYCLOAK_URL=http://keycloak:8080
REALM=TodoAAA
GROUP_NAME=TodoGroup

echo '⏳ Aguardando Keycloak subir...'
until curl -s $KEYCLOAK_URL/realms/master; do
  sleep 2
done

TOKEN=$(curl -s \
  -d 'client_id=admin-cli' \
  -d 'username=admin' \
  -d 'password=admin' \
  -d 'grant_type=password' \
  "$KEYCLOAK_URL/realms/master/protocol/openid-connect/token" \
  | jq -r .access_token)

curl --request POST \
  --url "$KEYCLOAK_URL/admin/realms" \
  --header "Authorization: Bearer $TOKEN" \
  --header 'Content-Type: application/json' \
  --data "@scripts/realm.json"

curl --request POST \
    --url "$KEYCLOAK_URL/admin/realms/$REALM/groups" \
    --header "Authorization: Bearer $TOKEN" \
    --header "Content-Type: application/json" \
    --data "{\"name\": \"$GROUP_NAME\"}"

CLIENT_ID=$(curl --request GET \
  --url "$KEYCLOAK_URL/admin/realms/$REALM/clients?clientId=todo-client" \
  --header "Authorization: Bearer $TOKEN" \
  --header 'Content-Type: application/json' \
  | jq -r .[0].id)

for SCOPE in GET POST PUT DELETE; do
  curl --request POST \
    --url "$KEYCLOAK_URL/admin/realms/$REALM/clients/$CLIENT_ID/authz/resource-server/scope" \
    --header "Authorization: Bearer $TOKEN" \
    --header "Content-Type: application/json" \
    --data "{\"name\": \"$SCOPE\"}"

  echo ""
  echo "Scope $SCOPE criado com sucesso."
done

curl --request POST \
  --url "$KEYCLOAK_URL/admin/realms/$REALM/clients/$CLIENT_ID/authz/resource-server/resource" \
  --header "Authorization: Bearer $TOKEN" \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Tasks",
	"type": "task",
	"displayName": "CRUD Task",
	"uris": ["/todo-list/task/*"],
	"ownerManagedAccess": false,
	"scopes": [
		{"name": "GET"},
		{"name": "POST"},
		{"name": "PUT"},
		{"name": "DELETE"}
	]
}'

GROUP_ID=$(curl --request GET \
  --url "$KEYCLOAK_URL/admin/realms/$REALM/groups" \
  --header "Authorization: Bearer $TOKEN" \
  --header 'Content-Type: application/json' \
  | jq -r .[0].id)

NAMES="get-task-list get-task-by-hash post-task-create put-task-edit delete-task-remove"
DESCRIPTIONS="listTasks getTaskByHashTask createTask editTask removeTask"

i=1
for NAME in $NAMES; do
  DESCRIPTION=$(echo $DESCRIPTIONS | cut -d' ' -f $i)
  i=$((i + 1))

  METHOD_HTTP=$(echo "$NAME" | cut -d'-' -f1 | tr '[:lower:]' '[:upper:]')
  POLICY_NAME="$NAME-policy"
  PERMISSION_NAME="$NAME-permission"

  JSON_PAYLOAD=$(cat <<EOF
{
  "name": "$POLICY_NAME",
  "description": "$DESCRIPTION",
  "type": "group",
  "logic": "POSITIVE",
  "groupsClaim": "groups",
  "groups": [{"id": "$GROUP_ID"}]
}
EOF
)

  POLICY_RESPONSE=$(curl --silent \
    --request POST \
    --url "$KEYCLOAK_URL/admin/realms/$REALM/clients/$CLIENT_ID/authz/resource-server/policy/group" \
    --header "Authorization: Bearer $TOKEN" \
    --header "Content-Type: application/json" \
    --data "$JSON_PAYLOAD")

  POLICY_ID=$(echo "$POLICY_RESPONSE" | jq -r '.id')

  if [ "$POLICY_ID" != "null" ]; then
    echo "Policy $POLICY_NAME criada com ID $POLICY_ID"

    # Criação da permission
    PERMISSION_PAYLOAD=$(cat <<EOF
{
  "name": "$PERMISSION_NAME",
  "description": "$DESCRIPTION",
  "type": "scope",
  "logic": "POSITIVE",
  "scopes": ["$METHOD_HTTP"],
  "resources": [],
  "policies": ["$POLICY_NAME"]
}
EOF
)

    curl --request POST \
      --url "$KEYCLOAK_URL/admin/realms/$REALM/clients/$CLIENT_ID/authz/resource-server/permission/scope" \
      --header "Authorization: Bearer $TOKEN" \
      --header "Content-Type: application/json" \
      --data "$PERMISSION_PAYLOAD"
  else
    echo "Falha ao criar policy $POLICY_NAME"
  fi

  echo
done

ADMIN_CLIENT_SECRET=$(curl --request GET \
  --url "$KEYCLOAK_URL/admin/realms/$REALM/clients/?clientId=admin-client" \
  --header "Authorization: Bearer $TOKEN" \
  | jq -r '.[0].secret')

TODO_CLIENT_SECRET=$(curl --request GET \
  --url "$KEYCLOAK_URL/admin/realms/$REALM/clients/?clientId=todo-client" \
  --header "Authorization: Bearer $TOKEN" \
  | jq -r '.[0].secret')

echo "Senha Admin Client -> $ADMIN_CLIENT_SECRET"
echo "Senha Todo Client -> $TODO_CLIENT_SECRET"
