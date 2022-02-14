package swa.order.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Sinks;
import swa.order.model.CreditOrder;
import swa.order.model.CreditOrderEvent;

@Component
public class CreditOrderPublisher {

    private final Sinks.Many<CreditOrderEvent> sink;

    @Autowired
    public CreditOrderPublisher(Sinks.Many<CreditOrderEvent> sink) {
        this.sink = sink;
    }

    public void process(CreditOrder order) {
        CreditOrderEvent creditOrderEvent = new CreditOrderEvent()
                .setCustomerId(order.getCustomerId())
                .setOrderId(order.getId())
                .setCreditAmount(order.getCreditAmount());
        sink.emitNext(creditOrderEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
