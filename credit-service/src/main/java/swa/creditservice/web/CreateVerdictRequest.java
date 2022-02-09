package swa.creditservice.web;

public class CreateVerdictRequest {
  private Long customerId;
  private int creditAmount;

  public CreateVerdictRequest() {
  }

  public CreateVerdictRequest(Long customerId, int creditAmount) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
  }

  public Long getCustomerId() {
      return customerId;
  }

  public int getCreditAmount() {
    return creditAmount;
  }
}
