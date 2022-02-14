package payment.saga.payment.model;

import lombok.Getter;
import lombok.ToString;
import payment.saga.payment.enums.VerdictStatus;

@ToString
@Getter
public class CreditVerdictEvent implements Event {

    private static final String EVENT = "Verdict";

    private Integer orderId;
    private VerdictStatus status;

    public CreditVerdictEvent() {
    }

    public CreditVerdictEvent orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CreditVerdictEvent status(VerdictStatus status) {
        this.status = status;
        return this;
    }

    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public VerdictStatus getStatus() {
		return status;
	}

	public void setStatus(VerdictStatus status) {
		this.status = status;
	}

	@Override
    public String getEvent() {
        return EVENT;
    }

}
