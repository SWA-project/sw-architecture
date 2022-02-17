package swa.credit.handler;

import static swa.credit.enums.VerdictStatus.APPROVED;
import static swa.credit.enums.VerdictStatus.DECLINED;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swa.credit.dto.CreditOrderEvent;
import swa.credit.dto.CreditVerdictEvent;
import swa.credit.model.Customer;
import swa.credit.repository.CreditOrderRepository;

@Component
public class CreditOrderEventHandler implements EventHandler<CreditOrderEvent, CreditVerdictEvent> {

    private final CreditOrderRepository customerRepository;

    @Autowired
    public CreditOrderEventHandler(CreditOrderRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CreditVerdictEvent handleEvent(CreditOrderEvent event) {
    	if (event.getCustomerId() == null) {
    		System.out.println("Received a credit order event without a valid customer id, returning declined to order service");
    		return new CreditVerdictEvent().status(DECLINED).setRejectionReason("Customer not found");
    	}
    	System.out.println("Handling credit check request from order service with order id: " + event.getOrderId() + ", customer id: " + event.getCustomerId() + " and credit amount: " + event.getCreditAmount());
        Integer customerId = event.getCustomerId();
        CreditVerdictEvent creditVerdictEvent = new CreditVerdictEvent()
                .orderId(event.getOrderId());
        customerRepository
                .findById(customerId)
                .ifPresent(customer -> deductUserBalance(event.getCreditAmount(), creditVerdictEvent, customer));
        System.out.println("Returning credit verdict to order service for order " + creditVerdictEvent.getOrderId() + " and status " + creditVerdictEvent.getStatus() + "\n\n");
        return creditVerdictEvent;
    }

    private void deductUserBalance(int creditAmount, CreditVerdictEvent creditVerdictEvent, Customer customer) {
        double userBalance = customer.getBalance();
        if (userBalance >= creditAmount) {
            customer.setBalance(userBalance - creditAmount);
            customerRepository.save(customer);
            creditVerdictEvent.status(APPROVED);
        } else {
        	creditVerdictEvent
        		.setRejectionReason("Insufficient credit")
        		.status(DECLINED);;
        }
    }

}
