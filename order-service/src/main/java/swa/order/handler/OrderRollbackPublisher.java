package swa.order.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Sinks;
import swa.order.dto.RollbackEventDTO;
import swa.order.model.CreditOrder;

@Component
public class OrderRollbackPublisher {

    private final Sinks.Many<RollbackEventDTO> rollbackSink;

    @Autowired
    public OrderRollbackPublisher(Sinks.Many<RollbackEventDTO> rollbackSink) {
        this.rollbackSink = rollbackSink;
    }

    public void publish(CreditOrder order) {
        RollbackEventDTO rollbackEvent = new RollbackEventDTO().setOrderId(order.getId());
        rollbackSink.emitNext(rollbackEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}