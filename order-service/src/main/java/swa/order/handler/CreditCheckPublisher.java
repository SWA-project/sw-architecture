package swa.order.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Sinks;
import swa.order.dto.CreditCheckEventDTO;

@Component
public class CreditCheckPublisher {

    private final Sinks.Many<CreditCheckEventDTO> creditCheckSink;

    @Autowired
    public CreditCheckPublisher(Sinks.Many<CreditCheckEventDTO> creditCheckSink) {
        this.creditCheckSink = creditCheckSink;
    }

    public void publish(CreditCheckEventDTO event) {
    	creditCheckSink.emitNext(event, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
