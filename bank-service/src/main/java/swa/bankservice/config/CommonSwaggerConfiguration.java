package swa.bankservice.config;

import io.eventuate.util.spring.swagger.EventuateSwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonSwaggerConfiguration {

  @Bean
  public EventuateSwaggerConfig eventuateSwaggerConfig() {
    return () -> "swa.bankservice";
  }
}
