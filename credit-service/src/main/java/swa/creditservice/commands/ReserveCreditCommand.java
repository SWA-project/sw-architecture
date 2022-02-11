package swa.creditservice.commands;

import io.eventuate.tram.commands.common.Command;

public class ReserveCreditCommand implements Command {
    private Long orderId;
    private Long customerId;
    private int creditAmount;
    private int customerMoney;

    public ReserveCreditCommand() {
    }

    public ReserveCreditCommand(Long customerId, Long orderId, int creditAmount, int customerMoney) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.creditAmount = creditAmount;
        this.customerMoney = customerMoney;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public int getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(int customerMoney) {
        this.customerMoney = customerMoney;
    }
}
