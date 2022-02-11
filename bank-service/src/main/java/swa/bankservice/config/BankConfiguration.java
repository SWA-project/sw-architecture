package swa.bankservice.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import swa.bankservice.repository.OrderRepository;
import swa.bankservice.service.CreateOrderSaga;
import swa.bankservice.service.OrderService;
import swa.bankservice.service.OrderSagaService;

@Configuration
@EnableJpaRepositories(basePackages = {"swa.bankservice.repository"})
@EnableAutoConfiguration
@Import(OptimisticLockingDecoratorConfiguration.class)
public class BankConfiguration {

  @Bean
  public OrderSagaService orderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
    return new OrderSagaService(orderRepository, sagaInstanceFactory, createOrderSaga);
  }

  @Bean
  public OrderService orderService(OrderRepository orderRepository) {
    return new OrderService(orderRepository);
  }
  
  @Bean
  public CreateOrderSaga createOrderSaga(OrderService orderService) {
    return new CreateOrderSaga(orderService);
  }
}