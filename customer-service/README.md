# Customer service

## DEV env using docker


### Customer-service

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


