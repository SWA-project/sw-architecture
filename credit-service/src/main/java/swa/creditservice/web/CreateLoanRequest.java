package swa.creditservice.web;

public class CreateLoanRequest {
  private Long customerId;
  private int creditAmount;
  private int customerMoney;

  public CreateLoanRequest() {
  }

  public CreateLoanRequest(Long customerId, int customerMoney, int creditAmount) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
  }

  public Long getCustomerId() {
      return customerId;
  }

  public int getCreditAmount() {
    return creditAmount;
  }

  public int getCustomerMoney() {
    return customerMoney;
  }
}
