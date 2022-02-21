package swa.order.dto;

import lombok.Data;

@Data
public class CustomerCheckEventDTO implements Event {

    private static final String EVENT = "CreditRequest";

    private Integer orderId;
    private Integer customerId;
    private int creditAmount;

    public CustomerCheckEventDTO setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CustomerCheckEventDTO setCustomerId(Integer userId) {
        this.customerId = userId;
        return this;
    }

    public CustomerCheckEventDTO setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}
    
	@Override
    public String getEvent() {
        return EVENT;
    }
}
