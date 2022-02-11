package swa.bankservice.dto;

import io.eventuate.tram.commands.common.Command;

public class ValidateUserCommand implements Command {
  private Long orderId;
  private int creditAmount;
  private long customerId;

  public ValidateUserCommand() {
  }

  public ValidateUserCommand(Long customerId, Long orderId, int creditAmount) {
    this.customerId = customerId;
    this.orderId = orderId;
    this.creditAmount = creditAmount;
  }

  public ValidateUserCommand(Long customerId, long orderId, int creditAmount2, int customerMoney) {
}

public int getCreditAmount() {
	return creditAmount;
  }

  public void setCreditAmount(int creditAmount) {
	this.creditAmount = creditAmount;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }
}