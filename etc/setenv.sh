CATALINA_OPTS="-Xmx256m -Dfile.encoding=UTF-8 -Dapp.home=\"$CATALINA_BASE/work/app_home\""

CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote"

CATALINA_OPTS="$CATALINA_OPTS \
 -Djava.rmi.server.hostname=localhost \
 -Dcom.sun.management.jmxremote.port=777{n} \
 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false"

JPDA_OPTS="-Xrunjdwp:transport=dt_socket,address=878{n},server=y,suspend=n"

CATALINA_OPTS="$CATALINA_OPTS -DcubaBlock=b{n}"