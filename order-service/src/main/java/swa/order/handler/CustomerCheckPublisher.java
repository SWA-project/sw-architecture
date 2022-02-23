package swa.order.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Sinks;
import swa.order.dto.CustomerCheckEventDTO;
import swa.order.model.CreditOrder;

@Component
public class CustomerCheckPublisher {

    private final Sinks.Many<CustomerCheckEventDTO> sink;

    @Autowired
    public CustomerCheckPublisher(Sinks.Many<CustomerCheckEventDTO> sink) {
        this.sink = sink;
    }

    public void publish(CreditOrder order) {
    	System.out.println("\nPublishing order " + order.getId() + " with creditAmount " + order.getCreditAmount());
        CustomerCheckEventDTO creditOrderEvent = new CustomerCheckEventDTO()
                .setCustomerId(order.getCustomerId())
                .setOrderId(order.getId())
                .setCreditAmount(order.getCreditAmount());
        sink.emitNext(creditOrderEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
