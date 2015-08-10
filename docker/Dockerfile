FROM jkremser/jdk-mvn
MAINTAINER jkremser@redhat.com

RUN yum --quiet -y install git && rm -rf /var/cache/yum \
    && git clone --depth=1 https://github.com/Jiri-Kremser/ircLogBot \
    && yum --quiet -y remove git && rm -rf /var/cache/yum \
    && cd ircLogBot/ircLogBot && mvn install && rm -rf ~/.m2/repository
    
CMD java -jar /opt/ircLogBot/ircLogBot/target/ircLogBot-*-jar-with-dependencies.jar $IRC_SERVER $IRC_CHANNEL
