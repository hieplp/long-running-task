#!/bin/bash
./gradlew clean
./gradlew shadowJar
java -Djava.net.useSystemProxies=true -jar build/libs/be-1.0.* -conf build/resources/main/config.json
