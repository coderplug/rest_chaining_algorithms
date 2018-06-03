# README

This repository is used for demo Forward and backward chaining web service application. 

This is only server part of the web service, client source code can be reached[here](https://github.com/coderplug/client_chaining_algorithms).
## Chaining Application system requirements

* JDK 1.7
* Maven 3
* Internet connection

## Setup (using IntelliJ IDEA)

* Clone git repo
* Project is based on *Maven*, thus import project to IntelliJ IDEA by:
  * File -> Open... -> pick `pom.xml` file.
* Change database configuration at `web/WEB-INF/resources.xml`
* Setup application server (Apache TomEE):
    1. Download WebProfile, ZIP from: [http://tomee.apache.org/downloads.html](http://tomee.apache.org/downloads.html)
    2. Unzip
    4. In IntelliJ IDEA: register "TomEE Server" -> local:
        * Press "Fix", choose "exploded war" as artifact
        * Set page: `http://localhost:8080/rest/get/forward/A/B`
        * Set port: `8080` (should be default)
    5. Run the server, project should start successfully.
    
