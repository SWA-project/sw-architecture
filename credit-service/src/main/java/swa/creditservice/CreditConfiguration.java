package swa.creditservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import swa.creditservice.domain.CreditRepository;
import swa.creditservice.service.CreditCalculationService;




@Configuration
@Import({
  TramMessageProducerJdbcConfiguration.class,
  EventuateTramKafkaMessageConsumerConfiguration.class
})
@EnableJpaRepositories
@EnableAutoConfiguration
public class CreditConfiguration {

  @Bean
  public CreditCalculationService creditService(CreditRepository customerRepository) {
    return new CreditCalculationService(customerRepository);
  }

}
