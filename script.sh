!/bin/bash

JAVA_HOME=/path/to/jvmdir
WORKDIR=/path/to/app/workdir
JAVA_OPTIONS=" -Xms256m -Xmx512m -server "
#APP_OPTIONS=" -c /path/to/app.config -d /path/to/datadir "

cd $WORKDIR
"${JAVA_HOME}/bin/java" $JAVA_OPTIONS -jar app.jar 
