# Simple bookstore application (Work In Progress) 
The following was discovered as part of building this project:
This project was bootstrapped from https://start.spring.io/
## Tech Stack

**Client:**
**Server:** Java, SprintBoot
**Database:** MySQL
## Installation

**1.** MYSQL Installation and Running
* Install MySQL -using Brew
```bash
brew install mysql
```
* Start SQL 
```bash
$ brew services start mysql 
```
* or 
```bash
/usr/local/Cellar/mysql/8.0.32/bin/mysql.server start
```
MySQL runs in localhost::3306 or override in application.properties in src/main/resources/application.properties

* Set root MySQL password
```bash
  $ mysqladmin -u root password 'secretpassword'
```
You can also follow
* https://formulae.brew.sh/formula/mysql

**2.** INSTALL Maven
```bash

brew install maven
```
You can also follow
* https://formulae.brew.sh/formula/maven

**3.** Compile, Build and Run Java Backend
Execute one command at a time
```bash

./mvnw compile
./mvnw install
cd target
java -jar url-shortener-0.0.1-SNAPSHOT.jar
```
Server Runs runs in localhost::9001 or override in application.properties in src/main/resources/application.properties

**4.** Compile, Build and Run FrontEnd
Follow  frontend/README.md

**5.** Run 
* Option 1 - from React Front end  from localhost:3000

* Option 2 - from Swagger Docs from http://localhost:9001/swagger-ui/index.html








