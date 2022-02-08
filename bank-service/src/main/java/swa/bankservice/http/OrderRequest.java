package swa.bankservice.http;

public class OrderRequest {
  private int creditAmount;
  private Long customerId;

  public OrderRequest() {
  }

  public OrderRequest(Long customerId, int creditAmount) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
  }

  public int getCreditAmount() {
    return creditAmount;
  }

  public Long getCustomerId() {
    return customerId;
  }
}
