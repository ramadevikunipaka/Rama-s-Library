#!/bin/sh
set -e
PORT_VALUE="${PORT:-10000}"
sed -i "s/port=\"8080\"/port=\"${PORT_VALUE}\"/" /usr/local/tomcat/conf/server.xml
exec catalina.sh run
