FROM centos:7

USER root

RUN yum install -y https://repo.ius.io/ius-release-el7.rpm && yum update -y
RUN yum install -y python36u python36u-libs python36u-devel python36u-setuptools python36u-pip

#################################
# Install the app
#################################
ENV APP_HOME /opt/stock-crawling
RUN mkdir $APP_HOME
RUN mkdir $APP_HOME/data
WORKDIR $APP_HOME
COPY run_crawl.py .
COPY requirements.txt .
RUN pip3 install -r requirements.txt
WORKDIR /opt
COPY run_crawl.sh .
RUN chmod +x run_crawl.sh

#################################
# SSH setup for Jenkins control #
#################################
ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG JENKINS_AGENT_HOME=/home/${user}
ENV JENKINS_AGENT_HOME ${JENKINS_AGENT_HOME}
RUN groupadd -g ${gid} ${group}
RUN useradd -d "${JENKINS_AGENT_HOME}" -u "${uid}" -g "${gid}" -m -s /bin/bash "${user}"

RUN echo 'jenkins:dummy_passwd'|chpasswd

# setup SSH server
RUN yum install -y openssh-server openssh-clients \
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