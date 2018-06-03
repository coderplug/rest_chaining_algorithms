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
    
### Database configuration for test cases (PostgreSQL without `dblink` extension)
1. Create DB tables from `db/single_db/single_db_tables`
2. Execute chosen test case from `db/single_db/Test Values`
3. Create Multi DB tables from `db/multi_db_tables/multi_db_test_case_example.txt`
    * Example uses `F5` value which identifies as "Forward 5th test case": 
        * Letter represents chaining algorithm (`F` or `B`)
        * Number represents test case number

#### Note: Each test case represents a database, in order to add more than one, these steps must be taken:
1. First test case use steps 1-3 from DB configuration section
2. Delete values from `rule` and `rule_antecedent` tables (`antecedent` is optional as in all test cases remains unchanged).
3. Execute chosen test case from `db/single_db/Test Values`
4. Insert values into Multi DB tables from `db/multi_db_tables/multi_db_test_case_insert_example.txt` with `F5` 
as an example test case (same as 3rd step)
5. Repeat 2-4 if more test cases are needed

### Database configuration for test cases (PostgreSQL with `dblink` extension)
1. Create DB tables from `db/single_db/single_db_tables` on separate DB
2. Execute chosen test case from `db/single_db/Test Values` or `db/single_db/values[2-4].txt` ([2-4] represents one of 4 files)
3. Repeat for all databases
4. Import dblink extension by executing `CREATE EXTENSION dblink;`
4. Create Multi DB tables by changing and executing `db/multi_db_tables/multi_db_tables_4_tables.txt`
    * Example uses 4 databases (2 local, 2 external), where dblink function parameters are: 
        * dbname - DB name
        * port - used port
        * host - DB host IP
        * user - DB username
        * pass - DB password
    * Server values are identified with names `local1`, `local2`, `remote1`, `remote2` which can be changed    
    