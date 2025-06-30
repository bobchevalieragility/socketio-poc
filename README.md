# socketio-poc

## Running locally in Docker Compose
1. cd be
2. ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=be-poc
3. cd ..
4. docker compose build
5. docker compose up
6. open http://localhost:3000

The "Streaming Data from SocketIO value should be 0"

7. Open terminal and run: curl -X POST -H "Content-Type: application/json" -d '51' http://localhost:8080/availability

The "Streaming Data from SocketIO value should be 51%"
