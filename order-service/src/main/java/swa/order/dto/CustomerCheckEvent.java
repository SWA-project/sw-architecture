package swa.order.dto;

import lombok.Getter;
import lombok.ToString;
import swa.order.enums.CustomerStatus;

@ToString
@Getter
public class CustomerCheckEvent implements Event {

    private static final String EVENT = "CustomerCheck";

    private Integer orderId;
    private CustomerStatus status;

    public CustomerCheckEvent() {
    }

    public CustomerCheckEvent orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CustomerCheckEvent status(CustomerStatus status) {
        this.status = status;
        return this;
    }

    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	@Override
    public String getEvent() {
        return EVENT;
    }

}
