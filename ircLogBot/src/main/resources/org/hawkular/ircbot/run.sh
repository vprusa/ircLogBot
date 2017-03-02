#!/bin/bash

# starts httpd, the irc log bot and logger script that generates the html

#/usr/sbin/httpd \
#&& java -jar /opt/ircLogBot/ircLogBot/target/ircLogBot-*-jar-with-dependencies.jar ${IRC_SERVER:="irc.freenode.net"} ${IRC_CHANNEL:="##foo"}& \
#sleep 10 && /root/logger.sh&

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Dir: ${DIR}"

echo "Stop previous"

ps -fe | grep java | grep ircLogBot | awk '{print $2}' | kill -9

COMPILE=false

#Dir: /home/vprusa/workspace/school/bc/1/ircLogBot/ircLogBot/src/main/resources/org/hawkular/ircbot

cd ${DIR}
cd ../../../../../../

BASE_DIR=`pwd`

echo "Base dir: ${BASE_DIR}"


if [ "$COMPILE" = true ] ; then

rm -rf target

mvn --quiet package && rm -rf ~/.m2/repository \
&& mkdir -p /var/www/html/logs/ && touch /tmp/ircLog.log \

fi
cd ${BASE_DIR}

echo "Path:"
echo `pwd`
   
cp -r src/main/resources/org/hawkular/ircbot/web/* /var/www/html/logs/ \
  && echo "14 3 1 * * root /root/ircLogRotation.sh" > /etc/cron.d/ircLogRotation \
  && cp src/main/resources/org/hawkular/ircbot/*.sh /root && chmod +x /root/*.sh

/usr/sbin/httpd \
 && java -jar ./target/ircLogBot-*-jar-with-dependencies.jar ${IRC_SERVER:="localhost"} ${IRC_CHANNEL:="#TheName"}& \
 sleep 10 && /root/logger.sh &

