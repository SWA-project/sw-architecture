package swa.order.dto;

import lombok.Getter;
import lombok.ToString;
import swa.order.enums.CreditCheckStatus;

@ToString
@Getter
public class CreditCheckResponseDTO implements Event {

    private static final String EVENT = "CreditCheck";

    private Integer orderId;
    private CreditCheckStatus status;
    private String rejectionReason;

    public CreditCheckResponseDTO() {
    }

    public CreditCheckResponseDTO orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CreditCheckResponseDTO status(CreditCheckStatus status) {
        this.status = status;
        return this;
    }
    
    public CreditCheckResponseDTO setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
		return this;
	}

    public String getRejectionReason() {
		return rejectionReason;
	}
    
    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public CreditCheckStatus getStatus() {
		return status;
	}

	public void setStatus(CreditCheckStatus status) {
		this.status = status;
	}

	@Override
    public String getEvent() {
        return EVENT;
    }

}
