# Microservices Orchestrator Saga Pattern - Credit Banking example

# Running the services

Before running the services, startup the mysql *database* and *kafka* containers. 

Go to `/docker` and run `docker-compose -f docker-compose.kafka.yml up --build`. 

Currently, both order service and credit service use the same db, which you can access via phpmyadmin at `http://localhost:8085`.

Next, head to kafka manager at `http://localhost:9000` and 

- create a cluster Click 'Add Cluster' with any name.
- Under Cluster Zookeeper Hosts enter `zoo:2181`
- Add topics:
  - order-customer
  - customer-order
  - order-credit
  - credit-order
  - order-rollback

## Run the Order service

Go to `/order-service` and run `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

## Credit service

Go to `/credit` and run `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

## Customer service

Go to `/customer-service` and see the instructions in the README.md.

## Build the project

Run `.\gradlew build` to build a project and create an executable jar-file. 

## How to test

1. First post balance into customers account using Credit Service endpoint.
2. Then post a new credit order with Order Service endpoint.
3. Get the credit order status (and other details) with customerId using Order Service endpoint.

Endpoints are here below.

## Endpoints 

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
