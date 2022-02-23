package swa.order.dto;

import lombok.Getter;
import lombok.ToString;
import swa.order.enums.CustomerStatus;

@ToString
@Getter
public class CustomerCheckResponseDTO implements Event {

    private static final String EVENT = "CustomerCheck";

    private Integer orderId;
    private CustomerStatus status;

    public CustomerCheckResponseDTO() {
    }

    public CustomerCheckResponseDTO orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CustomerCheckResponseDTO status(CustomerStatus status) {
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
