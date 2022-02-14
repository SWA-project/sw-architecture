package payment.saga.payment.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.payment.model.CreditOrderEvent;
import payment.saga.payment.model.CreditVerdictEvent;
import payment.saga.payment.model.Credit;
import payment.saga.payment.repository.CustomerRepository;

import javax.transaction.Transactional;

import static payment.saga.payment.enums.VerdictStatus.APPROVED;
import static payment.saga.payment.enums.VerdictStatus.DECLINED;

@Component
public class CreditOrderEventHandler implements EventHandler<CreditOrderEvent, CreditVerdictEvent> {

    private final CustomerRepository customerRepository;

    @Autowired
    public CreditOrderEventHandler(CustomerRepository customerRepository) {
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

    private void deductUserBalance(int creditAmount, CreditVerdictEvent creditVerdictEvent, Credit customer) {
        double userBalance = customer.getBalance();
        if (userBalance >= creditAmount) {
            customer.setBalance(userBalance - creditAmount);
            customerRepository.save(customer);
            creditVerdictEvent.status(APPROVED);
        }
    }

}
