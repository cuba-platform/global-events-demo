#!/usr/bin/env bash

if [ -z $1 ] ; then
  echo "Specify path to Chrome webdriver as a parameter of this script"
  exit 1
fi

wd_path_prop="-Dwebdriver.chrome.driver=$1"

function exitIfError {
    if [ $1 -ne 0 ]; then
        exit $1
    fi
}

echo ">>> Creating database"
./gradlew clean createDb
exitIfError $?

echo ">>> Testing local interaction"
./gradlew startLocalEnv
sleep 10
./gradlew testLocal ${wd_path_prop}
exitIfError $?

echo ">>> Testing desktop client"
./gradlew startDesktop
sleep 10
./gradlew testDesktop ${wd_path_prop}
status=$?
./gradlew stopDesktop stopTomcat
exitIfError ${status}

echo ">>> Testing cluster"
./gradlew setupClusterEnv startClusterEnv
sleep 30
./gradlew testCluster ${wd_path_prop}
./gradlew stopClusterEnv
