# spring-boot

## Documentation:
[Documentation with OpenAPI Specification](http://localhost:8080/swagger-ui/index.html#/)

## Technical:

1. Spring Boot 3.2.4
2. Java 21
3. Thymeleaf
4. Bootstrap v.5.3.3


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config
