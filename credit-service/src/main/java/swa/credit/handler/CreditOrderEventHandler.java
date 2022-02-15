package swa.credit.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swa.credit.model.Customer;
import swa.credit.model.CreditOrderEvent;
import swa.credit.model.CreditVerdictEvent;
import swa.credit.repository.CreditOrderRepository;

import static swa.credit.enums.VerdictStatus.APPROVED;
import static swa.credit.enums.VerdictStatus.DECLINED;

import javax.transaction.Transactional;

@Component
public class CreditOrderEventHandler implements EventHandler<CreditOrderEvent, CreditVerdictEvent> {

    private final CreditOrderRepository customerRepository;

    @Autowired
    public CreditOrderEventHandler(CreditOrderRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CreditVerdictEvent handleEvent(CreditOrderEvent event) {
        Integer customerId = event.getCustomerId();
        int creditAmount = event.getCreditAmount();
        CreditVerdictEvent creditVerdictEvent = new CreditVerdictEvent()
                .orderId(event.getOrderId())
                .status(DECLINED);
        customerRepository
                .findById(customerId)
                .ifPresent(customer -> deductUserBalance(creditAmount, creditVerdictEvent, customer));
        return creditVerdictEvent;
    }

    private void deductUserBalance(int creditAmount, CreditVerdictEvent creditVerdictEvent, Customer customer) {
        double userBalance = customer.getBalance();
        if (userBalance >= creditAmount) {
            customer.setBalance(userBalance - creditAmount);
            customerRepository.save(customer);
            creditVerdictEvent.status(APPROVED);
        }
    }

}
