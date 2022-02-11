package swa.bankservice.http;

public class OrderRequest {
  private int creditAmount;
  private Long customerId;
  private int customerMoney;

  public OrderRequest() {
  }

  public OrderRequest(Long customerId, int creditAmount, int customerMoney) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
    this.customerMoney = customerMoney;
  }

  public int getCreditAmount() {
    return creditAmount;
  }

  public int getCustomerMoney() {
    return customerMoney;
  }

  public Long getCustomerId() {
    return customerId;
  }
}
