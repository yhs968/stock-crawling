FROM openjdk:15-jdk-alpine
MAINTAINER suagramm@gmail.com

# setup batch-app
USER root
WORKDIR /opt
ARG APP_NAME=stock-crawling

RUN mkdir ${APP_NAME}
COPY stock-batch-0.0.1-SNAPSHOT.jar ${APP_NAME}

RUN mkdir $APP_NAME/data
COPY example.csv $APP_NAME/data/

# copy & inject run_batch into the container
COPY run_batch.sh .
RUN chmod +x run_batch.sh

# setup jenkins
# ARG user=jenkins
# ARG group=jenkins
# ARG uid=10000
# ARG gid=10000
# ARG JENKINS_AGENT_HOME=/home/${user}

# ENV JENKINS_AGENT_HOME ${JENKINS_AGENT_HOME}

# RUN addgroup -g ${gid} ${group} \
#     && adduser -u "${uid}" -g "${gid}" -s /bin/bash "${user}"

RUN apk update && apk update && apk add bash

ARG user=jenkins
ARG group=jenkins
ARG JENKINS_AGENT_HOME=/home/${user}
ENV JENKINS_AGENT_HOME ${JENKINS_AGENT_HOME}
RUN addgroup "${group}"

RUN adduser -s /bin/bash --disabled-password "${user}" -G "${group}"
# RUN adduser -s /bin/bash -D "${user}" -G "${group}"
# RUN adduser -s /bin/bash "${user}" -G "${group}"

RUN echo 'jenkins:dummy_passwd'|chpasswd

# setup SSH server
RUN apk add --no-cache openssh \
  && sed -i /etc/ssh/sshd_config \
        -e 's/#PermitRootLogin.*/PermitRootLogin no/' \
        -e 's/#RSAAuthentication.*/RSAAuthentication yes/'  \
        -e 's/#PasswordAuthentication.*/PasswordAuthentication no/' \
        -e 's/#SyslogFacility.*/SyslogFacility AUTH/' \
        -e 's/#LogLevel.*/LogLevel INFO/' && \
    mkdir /var/run/sshd

# setup jenkins_2
VOLUME "${JENKINS_AGENT_HOME}" "/tmp" "/run" "/var/run"
WORKDIR "${JENKINS_AGENT_HOME}"

# setup SSH server_2
COPY setup-sshd /usr/local/bin/setup-sshd
EXPOSE 22
ENTRYPOINT ["bash", "/usr/local/bin/setup-sshd"]

# initialization
# WORKDIR /root
# ENTRYPOINT ["/bin/sh", "-c"]
# CMD ["sh"]
