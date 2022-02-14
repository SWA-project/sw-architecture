package swa.creditservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import swa.creditservice.domain.CreditRepository;
import swa.creditservice.service.CreditCalculationService;




@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
public class CreditConfiguration {

  @Bean
  public CreditCalculationService creditService(CreditRepository customerRepository) {
    return new CreditCalculationService(customerRepository);
  }

}
