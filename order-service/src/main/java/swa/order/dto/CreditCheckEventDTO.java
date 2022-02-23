package swa.order.dto;

import lombok.Data;

@Data
public class CreditCheckEventDTO implements Event {

    private static final String EVENT = "CreditCheck";

    private Integer orderId;
    private Integer customerId;
    private int creditAmount;

    public CreditCheckEventDTO setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CreditCheckEventDTO setCustomerId(Integer userId) {
        this.customerId = userId;
        return this;
    }

    public CreditCheckEventDTO setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}
    
	@Override
    public String getEvent() {
        return EVENT;
    }
}
