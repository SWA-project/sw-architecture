package payment.saga.payment.model;

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

    public CreditOrderEvent setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreditOrderEvent setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}
   
    public int getCreditAmount() {
		return creditAmount;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	@Override
    public String getEvent() {
        return EVENT;
    }
}
