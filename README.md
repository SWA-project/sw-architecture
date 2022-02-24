# Microservices Orchestrator Saga Pattern - Credit Banking example

# Running the services

Before running the services, startup the *kafka* containers. 

Set DOCKER_HOST_IP environment variable (IMPORTANT!): `export DOCKER_HOST_IP=<LOCAL_MACHINE_IP>`

Go to `/docker` and run `docker-compose -f docker-compose.kafka.yml up --build`. (Might take a while, at least at first time if no image layers present on cache)

Next, head to kafka manager at `http://localhost:9000` and 

- create a cluster Click 'Add Cluster' with any name.
- Under Cluster Zookeeper Hosts enter `zoo:2181`
- Add topics:
  - order-customer
  - customer-order
  - order-credit
  - credit-order
  - order-rollback

Go to `/order-service` and run:

`./gradlew build`

Go to `/credit-service` 

Run `docker-compose -f docker-compose.services.yml up --build` (Might take a while, at least at first time if no image layers present on cache)

All services should now be running.

## Developing a service

### Order service

Make sure all containers of docker-compose.kafka.yml are running!

Run all other services and the databases in `/docker`: `docker-compose -f docker-compose.services.yml up --scale order-service=0`

Go to `/order-service`

Now you can run the service locally:  `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

### Credit service

Make sure all containers of docker-compose.kafka.yml are running!

Run all other services and the databases in `/docker`: `docker-compose -f docker-compose.services.yml up --scale credit-service=0`

Go to `/credit-service`

Set environment variables:


Now you can run the service locally:  `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

### Customer service

Make sure all containers of docker-compose.kafka.yml are running!

Run all other services and databases in `/docker`: `docker-compose -f docker-compose.services.yml up --scale customer-service=0 --scale postgres-db=0`

Go to `/customer-service` and see the instructions in the README.md.

# How to test

First start the entire backend with above instructions.

## From user interface

Go to `/front` folder and install dependencies with `npm install` and start the user interface with `npm start`. You are able to send new orders here and start/stop request stream to test capabilites of the system.

## Manually sending requests to the system

1. First post balance into customers account using Credit Service endpoint.
2. Then post a new credit order with Order Service endpoint.
3. Get the credit order status (and other details) with customerId using Order Service endpoint.

Endpoints are here below.

## Endpoints 

### Customer service

**Create a customer**

Send Send a *POST* request to `localhost:3000/customers` with the body

```
{
    "firstName": "Ossi",
    "lastName": "Tester",
    "ssn": "010101-1111"
}
```

### Credit service

**Add customer data**:

Send Send a *POST* request to `localhost:9191/customers` with the body 

```
{
  "customerId": 1,
  "balance": 1000
}
```
Response:

```
{
    "customerId": 1,
    "balance": 1000.0,
    "totalCredits": 0
}
```
The field totalCredits is the sum of all existing approved credit requests.

### Order Service

**Create a new credit order**:

Send a *POST* request to `localhost:9192/orders` with the body 

```
{
  "customerId": 1,
  "creditAmount": 50
}
```

And you will get a response with order state set to `PENDING`:

```
{
    "id": 1,
    "customerId": 1,
    "status": "PENDING",
    "creditAmount": 50,
    "rejectionReason": null
}
```

To check the order data after the saga has finished, send a *GET* request to `http://localhost:9192/orders/1`. For a successfully completed order the response would be 

```
{
    "id": 1,
    "customerId": 1,
    "status": "COMPLETED",
    "creditAmount": 50,
    "rejectionReason": null
}
```

Note that if there is no customer with id 1 in the credit service database, the credit request will be rejected:


```
{
    "id": 1,
    "customerId": 1,
    "status": "FAILED",
    "creditAmount": 50,
    "rejectionReason": "Customer not found"
}
```

**Get all orders**:

Send a *GET* request to `http://localhost:9191/orders`, and you get a list of all orders.


**Get all customer data**

Sending a *GET* request to `http://localhost:9191/customers` will return data of all customers in credit service.

Sending a *GET* request to `http://localhost:3000/customers` will return data of all customers in customer service.
