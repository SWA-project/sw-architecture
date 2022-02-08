package swa.bankservice.http;

import swa.bankservice.model.OrderState;
import swa.bankservice.model.RejectionReason;

public class OrderResponse {
  private long orderId;
  private OrderState orderState;
  private RejectionReason rejectionReason;

  public OrderResponse() {
  }

  public OrderResponse(long orderId) {
    this.orderId = orderId;
  }
  
  public OrderResponse(Long orderId, OrderState orderState, RejectionReason rejectionReason) {
    this.orderId = orderId;
    this.orderState = orderState;
    this.rejectionReason = rejectionReason;
  }

  public long getOrderId() {
    return orderId;
  }
  
  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public OrderState getOrderState() {
    return orderState;
  }

  public void setOrderState(OrderState orderState) {
    this.orderState = orderState;
  }

  public RejectionReason getRejectionReason() {
    return rejectionReason;
  }

  public void setRejectionReason(RejectionReason rejectionReason) {
    this.rejectionReason = rejectionReason;
  }
}
