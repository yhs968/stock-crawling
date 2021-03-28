#!/bin/bash

IMAGE_NAME=stock-batch
CONTAINER_NAME=batch-server
NETWORK_NAME=docker_stock-network
SSH_HOME=/Users/sua/.ssh
SSH_PUBKEY=$(cat $SSH_HOME/id_rsa_stock_crawling.pub)

docker build -t $IMAGE_NAME:latest .

# CONTAINER_ID=$(docker ps -a -q --filter ancestor=$IMAGE_NAME:latest)
# docker rm -f $CONTAINER_ID
docker rm -f $CONTAINER_NAME

docker run -it -d --name $CONTAINER_NAME $IMAGE_NAME:latest "$SSH_PUBKEY"
NEW_CONTAINER_ID=$(docker ps -a -q --filter ancestor=$IMAGE_NAME:latest)

# connect to network
docker network connect $NETWORK_NAME $NEW_CONTAINER_ID

# get ip
# NEW_CONTAINER_IP=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $NEW_CONTAINER_ID)

# GO template이 '-' 지우는 걸 지원 안하는듯? '-' 에러로 인해 docker_stock-network를 인식하는데 실패
# NEW_CONTAINER_IP=$(docker inspect --format "{{ .NetworkSettings.Networks.$NETWORK_NAME.IPAddress }}" $NEW_CONTAINER_ID)
# echo "Container IP: $NEW_CONTAINER_IP"

# check docker container name for connecting to jenkins
echo "Container Name: $CONTAINER_NAME"