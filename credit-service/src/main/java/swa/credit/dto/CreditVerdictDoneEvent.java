package swa.credit.dto;

import lombok.Getter;
import lombok.ToString;
import swa.credit.enums.VerdictStatus;

@ToString
@Getter
public class CreditVerdictDoneEvent implements Event {

    private static final String EVENT = "CreditCheck";

    private Integer orderId;
    private VerdictStatus status;

    public CreditVerdictDoneEvent() {
    }

    public CreditVerdictDoneEvent orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public CreditVerdictDoneEvent status(VerdictStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String getEvent() {
        return EVENT;
    }

}