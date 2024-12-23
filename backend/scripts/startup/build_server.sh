#!/bin/bash

mvn clean package
java -jar ./target/eduhub_server-1.0-SNAPSHOT.jar