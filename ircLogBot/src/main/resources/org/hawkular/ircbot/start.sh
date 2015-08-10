#!/bin/bash

# starts httpd, the irc log bot and logger script that generates the html

/usr/sbin/httpd \
&& java -jar /opt/ircLogBot/ircLogBot/target/ircLogBot-*-jar-with-dependencies.jar ${IRC_SERVER:="irc.freenode.net"} ${IRC_CHANNEL:="##foo"}& \
sleep 10 && /root/logger.sh&
