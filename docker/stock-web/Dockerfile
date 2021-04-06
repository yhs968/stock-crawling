FROM openjdk:15-jdk-alpine
MAINTAINER suagramm@gmail.com

USER root
WORKDIR /opt
ARG APP_NAME=stock-crawling

RUN mkdir ${APP_NAME}
COPY stock-web-0.0.1-SNAPSHOT.jar ${APP_NAME}

# copy & inject run_web into the container
COPY run_web.sh .
RUN chmod +x run_web.sh

EXPOSE 9999
ENTRYPOINT ["sh", "/opt/run_web.sh"]
