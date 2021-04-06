#!/bin/bash

JAVA_BIN_DIR=/opt/openjdk-15/bin
APP_HOME=/opt/stock-crawling
JAR_NAME=stock-web-0.0.1-SNAPSHOT.jar
SPRING_BOOT_PROFILE=docker

$JAVA_BIN_DIR/java -jar $APP_HOME/$JAR_NAME --spring.profiles.active=$SPRING_BOOT_PROFILE