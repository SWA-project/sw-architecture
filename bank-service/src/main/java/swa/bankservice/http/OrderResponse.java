package swa.bankservice.http;

import swa.bankservice.model.OrderState;
import swa.bankservice.model.RejectionReason;

public class OrderResponse {
	private long orderId;
	private OrderState orderState;
	private int interestRate;
	private RejectionReason rejectionReason;

	public OrderResponse() {
	}

	public OrderResponse(long orderId) {
		this.orderId = orderId;
	}
  
  	public OrderResponse(Long orderId, OrderState orderState, int interestRate, RejectionReason rejectionReason) {
  		this.orderId = orderId;
  		this.orderState = orderState;
  		this.interestRate = interestRate;
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
  
	public int getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}

	public RejectionReason getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(RejectionReason rejectionReason) {
	    this.rejectionReason = rejectionReason;
	}
}
