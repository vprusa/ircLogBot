#!/bin/bash

#[ "$#" -ne 3 ] &&  {
#  echo "Usage: logger.sh <input_raw_file> <output_file> <channel_name>"
#  exit 1
#}
_INPUT_FILE="/tmp/ircLog.log"
_LOG_TEMPLATE_HTML_FILE="/var/www/html/logs/logTemplate.html"
_LOG_HTML_FILE="/var/www/html/logs/latest.html"
_LOG_FILE="/var/www/html/logs/latestIrcLog.json"
_CHANNEL=${IRC_CHANNEL:="##foo"}
#_STYLES="<style>.name {color: red;}</style>"

# this works for the original raw pircbotx log output
#####################################################
# initial copy
#echo "<html><h1>$_CHANNEL</h1><table>"`cat $_INPUT_FILE | grep "PRIVMSG $_CHANNEL" | grep -v "SAFELIST" | grep -v "TARGMAX" | grep -v ">>>PRIVMSG" | awk '{$2=substr($2, 5, index($2, "!") - 5); $3=""; $4="";  print strftime("%b %d %H:%M:%S  %Y", substr($1, 0, 10))$0;}' | awk '{$1="<nobr>"$1; $3=$3"</nobr>"; $4=""; $5="</td><td><span style=\"color:red;\">"$5"</span></td><td>"; $6=substr($6, 2); print "<tr><td>"$0"</td></tr>"}' | sed 's/\(https\?:\/\/[^ <]*\)/<a href="\1">\1<\/a>/'` > $_LOG_FILE

# listen and add new changes
#tail -f $1 | grep --line-buffered "PRIVMSG $_CHANNEL" | grep --line-buffered -v "SAFELIST" | grep --line-buffered -v "TARGMAX" | grep --line-buffered -v ">>>PRIVMSG" | awk '{$2=substr($2, 5, index($2, "!") - 5); $3=""; $4="";  print strftime("%b %d %H:%M:%S  %Y", substr($1, 0, 10))$0;system("")}' | awk '{$1="<nobr>"$1; $3=$3"</nobr>"; $4=""; $5="</td><td><span style=\"color:red;\">"$5"</span></td><td>"; $6=substr($6, 2); print "<tr><td>"$0"</td></tr>";system("")}' | sed -u 's/\(https\?:\/\/[^ <]*\)/<a href="\1">\1<\/a>/' >> $_LOG_FILE

# this creates simple static html pages
#######################################
# initial copy
#echo "<html><head><title>IRC LOG for $_CHANNEL</title>$_STYLES</head><body><h1>$_CHANNEL</h1><table>"`cat $_INPUT_FILE | awk '{$1="<nobr>"$1; $3=$3"</nobr>"; $4=""; $5="</td><td><span class=\"name\">"$5"</span></td><td>"; $6; print "<tr><td>"$0"</td></tr>"}' | sed 's/\(https\?:\/\/[^ <]*\)/<a href="\1">\1<\/a>/'` > $_LOG_FILE

# listen and add new changes
#tail -f $_INPUT_FILE | awk '{$1="<nobr>"$1; $3=$3"</nobr>"; $4=""; $5="</td><td><span class=\"name\">"$5"</span></td><td>"; $6; print "<tr><td>"$0"</td></tr>";system("")}' | sed -u 's/\(https\?:\/\/[^ <]*\)/<a href="\1">\1<\/a>/' >> $_LOG_FILE

# and this creates the dynamic single page app
##############################################
echo "{\"entries\" :[" > $_LOG_FILE

# listen and add new changes
tail -f $_INPUT_FILE | awk '{$1="{\"time\":\""$1; $3=$3"\","; $4=""; $5="\"name\":\""$5"\",\"message\":\""; print $0"\"},";system("")}' | sed -u 's/\(https\?:\/\/[^ <]*\)/<a href=\1>\1<\/a>/' >> $_LOG_FILE

