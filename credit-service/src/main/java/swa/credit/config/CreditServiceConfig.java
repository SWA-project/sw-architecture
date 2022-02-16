package swa.credit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import swa.credit.dto.CreditOrderEvent;
import swa.credit.dto.CreditVerdictEvent;
import swa.credit.dto.CreditVerdictDoneEvent;
import swa.credit.handler.EventHandler;

import java.util.function.Function;

@Configuration
public class CreditServiceConfig {

    private final EventHandler<CreditVerdictEvent, CreditVerdictDoneEvent> verdictEventHandler;
    private final EventHandler<CreditOrderEvent, CreditVerdictEvent> creditOrderEventHandler;

    @Autowired
    public CreditServiceConfig(
            EventHandler<CreditVerdictEvent, CreditVerdictDoneEvent> verdictEventHandler,
            EventHandler<CreditOrderEvent, CreditVerdictEvent> creditOrderEventHandler) {
        this.verdictEventHandler = verdictEventHandler;
        this.creditOrderEventHandler = creditOrderEventHandler;
    }

    @Bean
    public Function<CreditOrderEvent, CreditVerdictEvent> creditOrderEventProcessor() {
        return creditOrderEventHandler::handleEvent;
    }

    @Bean
    public Function<CreditVerdictEvent, CreditVerdictDoneEvent> verdictEventSubscriber() {
        return verdictEventHandler::handleEvent;
    }

}
