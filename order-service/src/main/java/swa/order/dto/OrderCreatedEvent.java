package swa.order.dto;

import lombok.Data;

@Data
public class OrderCreatedEvent implements Event {

    private static final String EVENT = "CreditRequest";

    private Integer orderId;
    private Integer customerId;
    private int creditAmount;

    public OrderCreatedEvent setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderCreatedEvent setCustomerId(Integer userId) {
        this.customerId = userId;
        return this;
    }

    public OrderCreatedEvent setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}
    
	@Override
    public String getEvent() {
        return EVENT;
    }
}
