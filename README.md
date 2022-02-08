# sw-architecture

# Running the services

## Bank service

Go to `/bank-service` and run `./gradlew bootRun` or `./gradlew clean bootRun` if necessary. 

### Endpoints 

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
