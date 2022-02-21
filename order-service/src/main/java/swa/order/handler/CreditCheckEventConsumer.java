package swa.order.handler;

import static swa.order.enums.CreditCheckStatus.APPROVED;
import static swa.order.enums.CreditCheckStatus.DECLINED;
import static swa.order.enums.OrderStatus.FAILED;
import static swa.order.enums.OrderStatus.COMPLETED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import swa.order.dto.CreditCheckResponseDTO;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

@Component
public class CreditCheckEventConsumer implements EventConsumer<CreditCheckResponseDTO> {

    private final CreditOrderRepository creditOrderRepository;
    private final OrderRollbackPublisher rollbackPublisher;

    @Lazy
    @Autowired
    public CreditCheckEventConsumer(
            CreditOrderRepository creditOrderRepository,
            OrderRollbackPublisher rollbackPublisher) {
        this.creditOrderRepository = creditOrderRepository;
        this.rollbackPublisher = rollbackPublisher;
    }

    public void consumeEvent(CreditCheckResponseDTO event) {
    	System.out.println("Handling credit service response for order " + event.getOrderId() + ". Response status: " + event.getStatus());
    	if (DECLINED.equals(event.getStatus())) {
    		System.out.println("Credit rejected, reason: " + event.getRejectionReason() + "\n");
    	}
    	if (event.getOrderId() == null) {
    		System.out.println("Not proceeding with an event without a valid order id \n");
    		return;
    	}
    	
    	creditOrderRepository.findById(event.getOrderId())
        .ifPresent(order -> {
            setStatus(event, order);
            creditOrderRepository.save(order);
            initRollbackIfCheckFailed(order, event); 
        });
    }

    private void setStatus(CreditCheckResponseDTO event, CreditOrder order) {
        order
        	.setStatus(APPROVED.equals(event.getStatus())
                ? COMPLETED
                : FAILED)
        	.setRejectionReason(event.getRejectionReason());
    }
    
    private void initRollbackIfCheckFailed(CreditOrder order, CreditCheckResponseDTO event) {
    	if (DECLINED.equals(event.getStatus())) {
    		System.out.println("Credit check failed, Sending rollback request to customer service for order " + order.getId() + "\n");
        	this.rollbackPublisher.publish(order);
        }
    }

}
