#!/bin/sh
mvn clean package -DskipTests=true
java -jar ./target/pizza-store-backend-0.0.1-SNAPSHOT.jar