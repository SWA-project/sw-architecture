# Customer service

## DEV env

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

## Eventuate tram

Run:

```
export DOCKER_HOST_IP=LOCAL_MACHINE_IP
```

```
docker compose -f docker-compose.eventuate-tram.yml up --build
```

Reset all:

```
docker compose -f docker-compose.eventuate-tram.yml down --volumes
```

