FROM openjdk:15-jdk-alpine
MAINTAINER suagramm@gmail.com

USER root
WORKDIR /opt
ARG APP_NAME=stock-crawling

RUN mkdir ${APP_NAME}
COPY stock-batch-0.0.1-SNAPSHOT.jar ${APP_NAME}

RUN mkdir $APP_NAME/data
COPY example.csv $APP_NAME/data/

WORKDIR /root
ENTRYPOINT ["/bin/sh", "-c"]
CMD ["sh"]
