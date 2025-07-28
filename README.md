# socketio-poc

## Running locally in Docker Compose
1. docker compose build
2. docker compose up
3. open http://localhost:3000

The "Simple HTTP query" value should be "Monkey Pants"
The "Streaming Data from SocketIO" value should be "0"

7. Open terminal and run: curl -X POST -H "Content-Type: application/json" -d '51' http://localhost:8080/availability

The "Streaming Data from SocketIO" value should change to "51%"
