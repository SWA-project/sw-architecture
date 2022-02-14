package payment.saga.order.model;

import lombok.Data;

@Data
public class CreditOrderEvent implements Event {

    private static final String EVENT = "CreditRequest";

    private Integer orderId;
    private Integer customerId;
    private int creditAmount;

    public CreditOrderEvent setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CreditOrderEvent setCustomerId(Integer userId) {
        this.customerId = userId;
        return this;
    }

    public CreditOrderEvent setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}
    
	@Override
    public String getEvent() {
        return EVENT;
    }
}
