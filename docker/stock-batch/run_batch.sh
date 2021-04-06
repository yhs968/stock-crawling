#!/bin/bash

JAVA_BIN_DIR=/opt/openjdk-15/bin
APP_HOME=/opt/stock-crawling
JAR_NAME=stock-batch-0.0.1-SNAPSHOT.jar
JOB_NAME=pgWriterJob
TIME=$(date '+%Y%m%d%H%M%S')
SPRING_BOOT_PROFILE=docker

# (주의) spring.jpa.hibernate.ddl-auto를 none으로 해놨기 때문에, 최초 init시 create로 설정해서 실행해줘야 db table이 생성되어서 문제 없음
# RUN /opt/openjdk-15/bin/java -jar /opt/stock-crawling/stock-batch-0.0.1-SNAPSHOT.jar --job.name=pgWriterJob verion=99999999 --spring.profiles.active=docker --spring.jpa.hibernate.ddl-auto=create
DDL_AUTO="${SPRING_JPA_HIBERNATE_DDL_AUTO:-none}"

$JAVA_BIN_DIR/java -jar $APP_HOME/$JAR_NAME --job.name=$JOB_NAME version=$TIME --spring.profiles.active=$SPRING_BOOT_PROFILE --spring.jpa.hibernate.ddl-auto=$DDL_AUTO


