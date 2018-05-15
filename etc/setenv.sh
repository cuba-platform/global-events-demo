CATALINA_OPTS="-Xmx256m -Dfile.encoding=UTF-8"

CATALINA_OPTS="$CATALINA_OPTS -Dlogback.configurationFile=\"$CATALINA_BASE/conf/logback.xml\""

CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote"

CATALINA_OPTS="$CATALINA_OPTS \
 -Djava.rmi.server.hostname=localhost \
 -Dcom.sun.management.jmxremote.port={n}7777 \
 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false"

JPDA_OPTS="-Xrunjdwp:transport=dt_socket,address={n}8787,server=y,suspend=n"

CATALINA_OPTS="$CATALINA_OPTS -DcubaBlock=b{n}"