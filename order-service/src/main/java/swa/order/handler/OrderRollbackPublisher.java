package swa.order.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Sinks;
import swa.order.dto.RollbackEvent;
import swa.order.model.CreditOrder;

@Component
public class OrderRollbackPublisher {

    private final Sinks.Many<RollbackEvent> rollbackSink;

    @Autowired
    public OrderRollbackPublisher(Sinks.Many<RollbackEvent> rollbackSink) {
        this.rollbackSink = rollbackSink;
    }

    public void publish(CreditOrder order) {
        RollbackEvent rollbackEvent = new RollbackEvent().setOrderId(order.getId());
        rollbackSink.emitNext(rollbackEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}