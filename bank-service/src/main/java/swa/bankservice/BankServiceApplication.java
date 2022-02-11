package swa.bankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import swa.bankservice.config.BankConfiguration;
import swa.bankservice.config.OrderWebConfiguration;

@SpringBootApplication
@Configuration
@Import({BankConfiguration.class, OrderWebConfiguration.class})
public class BankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServiceApplication.class, args);
	}

}
