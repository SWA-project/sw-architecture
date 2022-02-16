package swa.order.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Sinks;
import swa.order.dto.OrderCreatedEvent;
import swa.order.model.CreditOrder;

@Component
public class CreditOrderPublisher {

    private final Sinks.Many<OrderCreatedEvent> sink;

    @Autowired
    public CreditOrderPublisher(Sinks.Many<OrderCreatedEvent> sink) {
        this.sink = sink;
    }

    public void publish(CreditOrder order) {
    	System.out.println("Publishing order " + order.getId() + " with creditAmount " + order.getCreditAmount());
        OrderCreatedEvent creditOrderEvent = new OrderCreatedEvent()
                .setCustomerId(order.getCustomerId())
                .setOrderId(order.getId())
                .setCreditAmount(order.getCreditAmount());
        sink.emitNext(creditOrderEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
