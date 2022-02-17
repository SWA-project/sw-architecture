package swa.credit.handler;

import static swa.credit.enums.VerdictStatus.APPROVED;
import static swa.credit.enums.VerdictStatus.DECLINED;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swa.credit.dto.CreditOrderEvent;
import swa.credit.dto.CreditVerdictEvent;
import swa.credit.model.Credit;
import swa.credit.model.Customer;
import swa.credit.repository.CreditRepository;
import swa.credit.repository.CustomerRepository;

@Component
public class CreditOrderEventHandler implements EventHandler<CreditOrderEvent, CreditVerdictEvent> {

    private final CustomerRepository customerRepository;
    private final CreditRepository creditRepository;

    @Autowired
    public CreditOrderEventHandler(CustomerRepository customerRepository, CreditRepository creditRepository) {
        this.customerRepository = customerRepository;
        this.creditRepository = creditRepository;
    }

    @Transactional
    public CreditVerdictEvent handleEvent(CreditOrderEvent event) {
    	if (event.getCustomerId() == null) {
    		System.out.println("Received a credit order event without a valid customer id, returning declined to order service");
    		return new CreditVerdictEvent().status(DECLINED).setRejectionReason("Customer not found");
    	}
    	System.out.println("Handling credit check request from order service with order id: " + event.getOrderId() + ", customer id: " + event.getCustomerId() + " and credit amount: " + event.getCreditAmount());
        
    	CreditVerdictEvent creditVerdictEvent = new CreditVerdictEvent()
    												.orderId(event.getOrderId())
    												.status(DECLINED);

        this.customerRepository
	      .findByCustomerId(event.getCustomerId())
	      .ifPresent(customer -> {
	      	this.makeCreditVerdict(event, creditVerdictEvent, customer);
	      });
        
        System.out.println("Returning credit verdict to order service for order " + creditVerdictEvent.getOrderId() + " and status " + creditVerdictEvent.getStatus() + "\n\n");
        return creditVerdictEvent;
    }
    
    private void makeCreditVerdict(CreditOrderEvent event, CreditVerdictEvent creditVerdictEvent, Customer customer) {
    	int customerCreditTotal = getCustomerCreditTotal(customer);
    	
        if (customer.getBalance() >= event.getCreditAmount() + customerCreditTotal) {
            creditVerdictEvent.status(APPROVED);
            this.saveAsNewCredit(customer, event.getOrderId(), event.getCreditAmount());
        } else {
        	creditVerdictEvent
        		.setRejectionReason("Insufficient credit")
        		.status(DECLINED);
        }
    }
    
    private int getCustomerCreditTotal(Customer customer) {
    	int customerCreditTotal = 0;
    	
		List<Credit> credits = customer.getCustomerCredits();	
    	
		for (Credit credit : credits) {
			customerCreditTotal = customerCreditTotal + credit.getAmount();
		}
    	
    	return customerCreditTotal;
    }
    
    private void saveAsNewCredit(Customer customer, Integer orderId, int amount) {
        creditRepository.save(new Credit()
    			.setCustomer(customer)
    			.setOrderId(orderId)
    			.setAmount(amount));
    }
}
