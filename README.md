# socketio-poc

## Running locally in Docker Compose
1. `docker compose build`
2. `docker compose up`
3. Open http://localhost:30000
- The "Simple HTTP query" value should be "Monkey Pants"
- The "Streaming Data from SocketIO" value should be "0"
4. `curl -X POST -H "Content-Type: application/json" -d '51' http://localhost:30001/availability`
- The "Streaming Data from SocketIO" value should change to "51%"
5. `CTRL-C` and then `docker compose down`

## Running locally in Kubernetes
1. `docker compose build`
2. Ensure that a local Kubernetes cluster is running (i.e. in Docker Desktop)
3. `./k8-apply.sh`
4. Open http://localhost:30000
- The "Simple HTTP query" value should be "Monkey Pants"
- The "Streaming Data from SocketIO" value should be "0"
5. `curl -X POST -H "Content-Type: application/json" -d '51' http://localhost:30001/availability`
- The "Streaming Data from SocketIO" value should change to "51%"
6. `./k8-delete.sh`
