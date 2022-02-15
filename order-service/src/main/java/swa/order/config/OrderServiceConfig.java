package swa.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import swa.order.dto.OrderCreatedEvent;
import swa.order.dto.CreditCheckEvent;
import swa.order.handler.EventConsumer;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderServiceConfig {

    private final EventConsumer<CreditCheckEvent> transactionEventConsumer;

    @Autowired
    public OrderServiceConfig(EventConsumer<CreditCheckEvent> transactionEventConsumer) {
        this.transactionEventConsumer = transactionEventConsumer;
    }

    @Bean
    public Sinks.Many<OrderCreatedEvent> sink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }

    @Bean
    public Supplier<Flux<OrderCreatedEvent>> orderCreditEventPublisher(
            Sinks.Many<OrderCreatedEvent> publisher) {
        return publisher::asFlux;
    }

    @Bean
    public Consumer<CreditCheckEvent> transactionEventProcessor() {
        return transactionEventConsumer::consumeEvent;
    }

}
