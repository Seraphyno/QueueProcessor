#!/bin/bash

if ! compgen -G "../target/QueueProcessor-*.jar" > /dev/null; then
    echo "Application jar not found in target. Running 'mvn package'"
    mvn -f pom.xml package -DskipTests
fi

echo "Copying jar to build directory..."
cp ../target/QueueProcessor-*.jar app.jar

echo "Building image"
docker build . -t queueprocessor:latest

echo "Cleaning up (removing jar from build directory)..."
rm app.jar
