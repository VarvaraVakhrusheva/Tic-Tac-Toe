#!/bin/bash

# Start instance 1 on port 8080, passing own and opponent URLs as arguments
nohup mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080 --own.url=http://localhost:8080 --opponent.url=http://localhost:8081" > player1.log 2>&1 &

# Start instance 2 on port 8081, passing own and opponent URLs as arguments
nohup mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --own.url=http://localhost:8081 --opponent.url=http://localhost:8080" > player2.log 2>&1 &

echo "Players are running in the background. Logs are being written to player1.log and player2.log."
