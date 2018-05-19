# Global Events Demo

This project is designed for testing the CUBA [Global Events add-on](https://github.com/cuba-platform/global-events-addon).

## System Requirements

* Linux or macOS
* Locally installed PostgreSQL 9.5+
* 16GB RAM and a modern multi-core CPU 
* Free port ranges: 7770..7776, 7801, 7802, 8050..8056, 8080..8086, 8090..8096, 8780..8786

## Manual Testing

First, create local PostgreSQL database which will be used by all configurations below:

        ./gradlew createDb
        
### Local System
         
* Start Tomcat with `core`, `web` and `portal` blocks:

        ./gradlew setupTomcat deploy start
        
* Open `http://localhost:8080/app` in two different browsers. 

* Open _Application > Screen1_ in both browsers. Click _Send test event_ in one browser and see how the number of received events is increased in both.  

* Open `app-core.glevtdemo:type=CoreTester` JMX bean in one browser. Invoke `sendUiNotificationEvent` operation and see how the number of received events is increased in both browsers. 

* Invoke `sendBeanNotificationEvent` operation, then open _Entity Inspector_ for the _Event Registration_ entity and make sure there are three instances with _Receiver_ fields indicating that the event was received by beans of all three blocks: `core`, `web` and `portal`.            

### Desktop Client

* Start Tomcat as described above.

* Start desktop client from IntelliJ or using the `startDesktop` Gradle task. In the latter case, the client will automatically connect to the middleware with the `admin/admin` credentials.

* Send events from the same screen and watch the number of received events in the desktop client and the browsers.

* Invoke the `sendBeanNotificationEvent` JMX operation and make sure there is a record of _Event Registration_ with the _Receiver_ field showing a bean from the `desktop` block. 

### Cluster

* Start cluster of six Tomcat servers and wait for all servers to load:

        ./gradlew setupClusterEnv startClusterEnv
        
    In this setup, `web3` and `portal6` are connected to `core1`, `web4` and `web5` are connected to `core2`.

* Open `http://localhost:8083/app` (web3), `http://localhost:8084/app` (web4), `http://localhost:8085/app` (web5) in different browsers and send UI events using the screen.

* Open JMX console and select `localhost:7771` or `localhost:7772` JMX connections (`core1` and `core2` respectively). Send UI events and watch the number of received events in all browsers.

* Invoke the `sendBeanNotificationEvent` JMX operation and ensure _Event Registration_ contains a record with the _Receiver_ field showing a bean from the `portal` block.

* Stop cluster:

         ./gradlew stopClusterEnv

## Automatic Tests

* Download [Chrome WebDriver](http://chromedriver.chromium.org/downloads) to a local directory. 

* Open terminal in the project root and execute:

        ./run-system-test.sh /some_path/chromedriver
