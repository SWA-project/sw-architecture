package swa.credit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import swa.credit.handler.EventHandler;
import swa.credit.model.CreditOrderEvent;
import swa.credit.model.CreditVerdictEvent;
import swa.credit.model.TransactionEvent;

import java.util.function.Function;

@Configuration
public class PaymentServiceConfig {

    private final EventHandler<CreditVerdictEvent, TransactionEvent> paymentEventHandler;
    private final EventHandler<CreditOrderEvent, CreditVerdictEvent> orderPurchaseEventHandler;

    @Autowired
    public PaymentServiceConfig(
            EventHandler<CreditVerdictEvent, TransactionEvent> paymentEventHandler,
            EventHandler<CreditOrderEvent, CreditVerdictEvent> orderPurchaseEventHandler) {
        this.paymentEventHandler = paymentEventHandler;
        this.orderPurchaseEventHandler = orderPurchaseEventHandler;
    }

    @Bean
    public Function<CreditOrderEvent, CreditVerdictEvent> orderPurchaseEventProcessor() {
        return orderPurchaseEventHandler::handleEvent;
    }

    @Bean
    public Function<CreditVerdictEvent, TransactionEvent> paymentEventSubscriber() {
        return paymentEventHandler::handleEvent;
    }

}
