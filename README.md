# sw-architecture

# Running the services

## Bank & Credit services

Go to the root of chosen service f.ex `/bank-service` and run `./gradlew bootRun` or `./gradlew clean bootRun` if necessary.

### Endpoints 

## Bank service

*Create a new credit card order*: 

Send a *POST* request to `localhost:8080/orders with the body 

```
{
  "customerId": 2,
  "creditAmount": 4000
}
```

*Get an order by id*:

Send a *GET* request to `http://localhost:8080/orders/{orderId}`, and you should get a response such as:

```
{
    "orderId": 1,
    "orderState": "PENDING",
    "rejectionReason": null
}
```

## Credit service

*Calculate a new verdict for applied credit*: 

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
