package swa.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import swa.order.handler.EventConsumer;
import swa.order.model.CreditOrderEvent;
import swa.order.model.TransactionEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderServiceConfig {

    private final EventConsumer<TransactionEvent> transactionEventConsumer;

    @Autowired
    public OrderServiceConfig(EventConsumer<TransactionEvent> transactionEventConsumer) {
        this.transactionEventConsumer = transactionEventConsumer;
    }

    @Bean
    public Sinks.Many<CreditOrderEvent> sink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }

    @Bean
    public Supplier<Flux<CreditOrderEvent>> creditOrderEventPublisher(
            Sinks.Many<CreditOrderEvent> publisher) {
        return publisher::asFlux;
    }

    @Bean
    public Consumer<TransactionEvent> transactionEventProcessor() {
        return transactionEventConsumer::consumeEvent;
    }

}
