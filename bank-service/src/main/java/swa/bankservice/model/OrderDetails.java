package swa.bankservice.model;

import javax.persistence.Embeddable;

@Embeddable
public class OrderDetails {
  private Long customerId;

  private int creditAmount;

  public OrderDetails() {
  }

  public OrderDetails(Long customerId, int creditAmount) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public int getOrderTotal() {
    return creditAmount;
  }
}