#!/bin/bash

SSH_HOME=/Users/sua/.ssh
export SSH_PUBKEY=$(cat $SSH_HOME/id_rsa_stock_crawling.pub)
docker-compose up -d

