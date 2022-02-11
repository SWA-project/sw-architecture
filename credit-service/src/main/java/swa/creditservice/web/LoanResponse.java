package swa.creditservice.web;

public class LoanResponse {
  private Long customerId;
  private int creditAmount;
  private boolean valid;

  public LoanResponse() {
  }

  public LoanResponse(Long customerId, int creditAmount, boolean valid) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
    this.valid = valid;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public int getCreditAmount() {
    return creditAmount;
  }

  public void setCreditAmount(int creditAmount) {
    this.creditAmount = creditAmount;
  }

  public boolean getValid() {
    return valid;
  }

  public void setCreditLimit(boolean valid) {
    this.valid = valid;
  }
}
