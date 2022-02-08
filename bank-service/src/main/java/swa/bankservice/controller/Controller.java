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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import swa.bankservice.http.OrderRequest;
import swa.bankservice.http.OrderResponse;
import swa.bankservice.model.CreditCardOrder;
import swa.bankservice.model.OrderDetails;
import swa.bankservice.repository.CreditCardOrderRepository;

@RestController
public class Controller {

  private CreditCardOrderRepository orderRepository;

  @Autowired
  public Controller(CreditCardOrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @RequestMapping(value = "/orders", method = RequestMethod.POST)
  public OrderResponse createOrder(@RequestBody OrderRequest request) {
	OrderDetails orderDetails = new OrderDetails(request.getCustomerId(), request.getCreditAmount());
	CreditCardOrder order = this.orderRepository.save(CreditCardOrder.createOrder(orderDetails));
	return new OrderResponse(order.getId());
  }
  
  @RequestMapping(value="/orders/{orderId}", method = RequestMethod.GET)
  public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
    return orderRepository
            .findById(orderId)
            .map(o -> new ResponseEntity<>(new OrderResponse(o.getId(), o.getState(), o.getRejectionReason()), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @RequestMapping(value="/orders/customer/{customerId}", method = RequestMethod.GET)
  public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@PathVariable Long customerId) {
    return new ResponseEntity<List<OrderResponse>>(orderRepository
            .findAllByOrderDetailsCustomerId(customerId)
            .stream()
            .map(o -> new OrderResponse(o.getId(), o.getState(), o.getRejectionReason()))
            .collect(Collectors.toList()), HttpStatus.OK);
  }
}
