FROM jkremser/jdk-mvn
MAINTAINER jkremser@redhat.com

RUN git clone --depth=1 https://github.com/Jiri-Kremser/ircLogBot &&\
    cd ircLogBot/ircLogBot &&\
    mvn install
    

ENTRYPOINT ["java" "-jar", "target/ircLogBot-*-jar-with-dependencies.jar", $IRC_SERVER $IRC_CHANNEL]
CMD []
