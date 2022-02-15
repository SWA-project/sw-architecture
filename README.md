# Credit Banking Saga Pattern

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
  - verdicts

## Run the Order service

Go to `/order-service` and run `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

## Credit service

Go to `/credit` and run `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

## Customer service

Go to `/customer-service` and see the instructions in the README.md .

## Endpoints 

### Order Service

**Create a new credit order**:

Send a *POST* request to `localhost:9192/orders` with the body 

```
{
  "customerId": 1,
  "creditAmount": 50
}
```

Note, if there is no customerId 1 in the credit service database table yet, the credit request will be rejected.

**Get all orders**:

Send a *GET* request to `http://localhost:8080/orders`, and you should get a list of all orders.


### REVISE: Credit servic

** Add customer data**:

Send Send a *POST* request to `localhost:8080/customers with the body 

```
{
  "balance": 1000
}
```

**Calculate a new verdict for applied credit**: 

Send a *POST* request to `localhost:8080/calculateCredit with the body 

```
{
  "customerId": 1234,
  "creditAmount": 43000
}
```

The service will response the verdict id, that can be used as below:

*Get the verdict with verdictId*:

Send a *GET* request to `http://localhost:8080/creditVerdict/{verdictId}`, and you should get a response such as:

```
{
    "customerId": 1234,
    "creditAmount": 43000,
    "verdict": true
}
```
