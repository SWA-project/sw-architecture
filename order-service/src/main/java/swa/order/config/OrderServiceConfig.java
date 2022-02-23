package swa.order.config;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import swa.order.dto.CreditCheckResponseDTO;
import swa.order.dto.CreditCheckEventDTO;
import swa.order.dto.CustomerCheckResponseDTO;
import swa.order.dto.CustomerCheckEventDTO;
import swa.order.dto.RollbackEventDTO;
import swa.order.handler.EventConsumer;

@Configuration
public class OrderServiceConfig {

    private final EventConsumer<CreditCheckResponseDTO> creditCheckEventConsumer;
    private final EventConsumer<CustomerCheckResponseDTO> customerCheckEventConsumer;

    @Autowired
    public OrderServiceConfig(
    		EventConsumer<CreditCheckResponseDTO> creditCheckEventConsumer,
    		EventConsumer<CustomerCheckResponseDTO> customerCheckEventConsumer
    		) {
        this.creditCheckEventConsumer = creditCheckEventConsumer;
        this.customerCheckEventConsumer = customerCheckEventConsumer;

    }

    @Bean
    public Sinks.Many<CustomerCheckEventDTO> sink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }
    
    @Bean
    public Sinks.Many<CreditCheckEventDTO> creditCheckSink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }
       
    @Bean
    public Sinks.Many<RollbackEventDTO> rollbackSink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }
    
    
    @Bean
    public Supplier<Flux<CustomerCheckEventDTO>> customerCheckEventPublisher(
            Sinks.Many<CustomerCheckEventDTO> publisher) {
        return publisher::asFlux;
    }
    
    @Bean
    public Supplier<Flux<CreditCheckEventDTO>> creditCheckEventPublisher(
            Sinks.Many<CreditCheckEventDTO> publisher) {
        return publisher::asFlux;
    }
    
    @Bean
    public Supplier<Flux<RollbackEventDTO>> orderRollbackEventPublisher(
            Sinks.Many<RollbackEventDTO> publisher) {
        return publisher::asFlux;
    }
    
    @Bean
    public Consumer<CustomerCheckResponseDTO> customerCheckEventProcessor() {
        return customerCheckEventConsumer::consumeEvent;
    }
    
    @Bean
    public Consumer<CreditCheckResponseDTO> creditCheckEventProcessor() {
        return creditCheckEventConsumer::consumeEvent;
    }

}
