# Customer service

## DEV env using docker


### Customer-service

Make sure kafka is running

Set environment:

```
export DOCKER_HOST_IP=<LOCAL_MACHINE_IP>
```

Run:

```
docker compose -f docker-compose.dev.yml up
```

Stop:

```
docker compose -f docker-compose.dev.yml stop 
```

Rebuild and start after e.g. installing deps:

```
docker compose -f docker-compose.dev.yml up --build
```

Reset all, including db:

```
docker compose -f docker-compose.dev.yml down --volumes
```


