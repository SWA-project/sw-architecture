package swa.bankservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swa.bankservice.model.CreditCardOrder;
import swa.bankservice.model.OrderDetails;
import swa.bankservice.model.RejectionReason;
import swa.bankservice.repository.CreditCardOrderRepository;

@Service
public class CreditCardOrderService {

  @Autowired
  private CreditCardOrderRepository orderRepository;

  public CreditCardOrderService(CreditCardOrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public CreditCardOrder createOrder(OrderDetails orderDetails) {
	CreditCardOrder order = CreditCardOrder.createOrder(orderDetails);
    orderRepository.save(order);
    return order;
  }

  public void approveOrder(Long orderId) {
    orderRepository.findById(orderId).get().approve();
  }

  public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
    orderRepository.findById(orderId).get().reject(rejectionReason);
  }
}