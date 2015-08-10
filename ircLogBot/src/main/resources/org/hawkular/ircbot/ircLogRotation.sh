#!/bin/bash
LOG_FILE_RAW="/tmp/ircLog.log"
LOG_FILE_LATEST="/var/www/html/logs/latestIrcLog"

mv $LOG_FILE_LATEST /var/www/html/logs/ircLog-$( date +%F ) && echo "copying old log file.." && \
kill $(ps aux | grep 'logger.sh' | awk '{print $2}') && echo "killing the logging process.." && \
cp -f /dev/null $LOG_FILE_RAW && echo "" && echo "erasing the old log.."

/bin/sh /root/logger.sh "$LOG_FILE_RAW" "$LOG_FILE_LATEST" "$IRC_CHANNEL"&
