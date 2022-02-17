package swa.order.handler;

import static swa.order.enums.CreditCheckStatus.APPROVED;
import static swa.order.enums.CreditCheckStatus.DECLINED;
import static swa.order.enums.OrderStatus.FAILED;
import static swa.order.enums.OrderStatus.COMPLETED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import swa.order.dto.CreditCheckEvent;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

@Component
public class CreditCheckEventConsumer implements EventConsumer<CreditCheckEvent> {

    private final CreditOrderRepository creditOrderRepository;
    private final OrderRollbackPublisher rollbackPublisher;
    private final Scheduler jdbcScheduler;

    @Lazy
    @Autowired
    public CreditCheckEventConsumer(
            CreditOrderRepository creditOrderRepository,
            OrderRollbackPublisher rollbackPublisher,
            Scheduler jdbcScheduler) {
        this.creditOrderRepository = creditOrderRepository;
        this.jdbcScheduler = jdbcScheduler;
        this.rollbackPublisher = rollbackPublisher;
    }

    public void consumeEvent(CreditCheckEvent event) {
    	System.out.println("Handling credit service response for order " + event.getOrderId() + ". Response status: " + event.getStatus());
    	if (DECLINED.equals(event.getStatus())) {
    		System.out.println("Credit rejected, reason: " + event.getRejectionReason() + "\n");
    	}
    	if (event.getOrderId() == null) {
    		System.out.println("Not proceeding with an event without a valid order id \n");
    		return;
    	}
        Mono.fromRunnable(
                () -> creditOrderRepository.findById(event.getOrderId())
                        .ifPresent(order -> {
                            setStatus(event, order);
                            creditOrderRepository.save(order);
                            initRollbackIfCheckFailed(order, event); 
                        }))
                .subscribeOn(jdbcScheduler)
                .subscribe();
    }

    private void setStatus(CreditCheckEvent event, CreditOrder order) {
        order
        	.setStatus(APPROVED.equals(event.getStatus())
                ? COMPLETED
                : FAILED)
        	.setRejectionReason(event.getRejectionReason());
    }
    
    private void initRollbackIfCheckFailed(CreditOrder order, CreditCheckEvent event) {
    	if (DECLINED.equals(event.getStatus())) {
    		System.out.println("Credit check failed, Sending rollback request to customer service for order " + order.getId() + "\n");
        	this.rollbackPublisher.publish(order);
        }
    }

}
