package swa.order.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RollbackEvent implements Event {
	private static final String EVENT = "Rollback";

    private Integer orderId;

    public RollbackEvent() {
    }
    
    public RollbackEvent setOrderId(Integer orderId) {
		this.orderId = orderId;
		return this;
	}
    
	public Integer getOrderId() {
		return orderId;
	}

	@Override
	public String getEvent() {
		return EVENT;
	}
    
    

}
