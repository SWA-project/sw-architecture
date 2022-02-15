package swa.order.config;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import swa.order.dto.CreditCheckEvent;
import swa.order.dto.CustomerCheckEvent;
import swa.order.dto.OrderCreatedEvent;
import swa.order.handler.EventConsumer;
import swa.order.handler.EventHandler;

@Configuration
public class OrderServiceConfig {

    private final EventConsumer<CreditCheckEvent> transactionEventConsumer;
    private final EventHandler<CustomerCheckEvent, OrderCreatedEvent> customerCheckEventHandler;

    @Autowired
    public OrderServiceConfig(
    		EventConsumer<CreditCheckEvent> transactionEventConsumer,
    		EventHandler<CustomerCheckEvent, OrderCreatedEvent> customerCheckEventHandler
    		) {
        this.transactionEventConsumer = transactionEventConsumer;
        this.customerCheckEventHandler = customerCheckEventHandler;

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
    public Function<CustomerCheckEvent, OrderCreatedEvent> customerCheckEventProcessor() {
        return customerCheckEventHandler::handleEvent;
    }
    
    @Bean
    public Consumer<CreditCheckEvent> transactionEventProcessor() {
        return transactionEventConsumer::consumeEvent;
    }

}
