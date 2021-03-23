FROM openjdk:15-jdk-alpine
MAINTAINER suagramm@gmail.com

USER root
WORKDIR /opt
ARG APP_NAME=stock-crawling

RUN mkdir ${APP_NAME}
COPY stock-web-0.0.1-SNAPSHOT.jar ${APP_NAME}

WORKDIR /root
EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c"]
CMD ["sh"]
