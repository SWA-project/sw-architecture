package swa.creditservice.web;

public class VerdictResponse {
  private Long customerId;
  private int creditAmount;
  private boolean verdict;

  public VerdictResponse() {
  }

  public VerdictResponse(Long customerId, int creditAmount, boolean verdict) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
    this.verdict = verdict;
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

  public boolean getVerdict() {
    return verdict;
  }

  public void setCreditLimit(boolean verdict) {
    this.verdict = verdict;
  }
}
