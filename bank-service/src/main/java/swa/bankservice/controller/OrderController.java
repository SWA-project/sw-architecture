package swa.bankservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import swa.bankservice.http.OrderRequest;
import swa.bankservice.model.Order;
import swa.bankservice.model.OrderDetails;
import swa.bankservice.repository.OrderRepository;
import swa.bankservice.service.OrderSagaService;

@RestController
public class OrderController {

  private OrderRepository orderRepository;
  private OrderSagaService orderSagaService;

  @Autowired
  public OrderController(OrderSagaService orderSagaService, OrderRepository orderRepository) {
    this.orderSagaService = orderSagaService;
    this.orderRepository = orderRepository;
  }

  @RequestMapping(value = "/orders", method = RequestMethod.POST)
  public Order createOrder(@RequestBody OrderRequest request) {
	Order order = orderSagaService.createOrder(new OrderDetails(request.getCustomerId(), request.getCreditAmount()));
	return order;
  }
  
  @RequestMapping(value="/orders/{orderId}", method = RequestMethod.GET)
  public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
    return orderRepository
            .findById(orderId)
            .map(o -> new ResponseEntity<>(o, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  
  @RequestMapping(value="/orders/customer/{customerId}", method = RequestMethod.GET)
  public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
    return new ResponseEntity<List<Order>>(orderRepository
            .findAllByOrderDetailsCustomerId(customerId)
            .stream()
            .collect(Collectors.toList()), HttpStatus.OK);
  }
}
