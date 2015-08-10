FROM jkremser/jdk-mvn
MAINTAINER jkremser@redhat.com

RUN yum -y install git httpd && rm -rf /var/cache/yum \
    && git clone --depth=1 https://github.com/Jiri-Kremser/ircLogBot \
    && yum --quiet -y remove git && rm -rf /var/cache/yum \
    && cd ircLogBot/ircLogBot && mvn package && rm -rf ~/.m2/repository \
    && mkdir -p /var/www/html/logs/ \
    && echo "14 3 1 * * root /root/ircLogRotation.sh" > /etc/cron.d/ircLogRotation

EXPOSE 80
CMD /usr/sbin/httpd && java -jar /opt/ircLogBot/ircLogBot/target/ircLogBot-*-jar-with-dependencies.jar ${IRC_SERVER:="irc.freenode.net"} ${IRC_CHANNEL:="##foo"}
