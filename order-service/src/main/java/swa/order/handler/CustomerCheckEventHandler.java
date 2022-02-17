package swa.order.handler;

import static swa.order.enums.CustomerStatus.CUSTOMERNOTFOUND;
import static swa.order.enums.OrderStatus.FAILED;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.scheduler.Scheduler;
import swa.order.dto.CustomerCheckEvent;
import swa.order.dto.OrderCreatedEvent;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

@Component
public class CustomerCheckEventHandler implements EventHandler<CustomerCheckEvent, OrderCreatedEvent> {

    private final CreditOrderRepository creditOrderRepository;

    @Autowired
    public CustomerCheckEventHandler(CreditOrderRepository creditOrderRepository) {
        this.creditOrderRepository = creditOrderRepository;
    }

    @Transactional
    public OrderCreatedEvent handleEvent(CustomerCheckEvent event) {
    	System.out.println("Handling response from customer service, response: " + event.getStatus());
    	OrderCreatedEvent orderEvent = new OrderCreatedEvent().setOrderId(event.getOrderId());
    	
    	if (event.getStatus().equals(CUSTOMERNOTFOUND)) {
    		this.updateOrderStatusOnFail(event);
    		return orderEvent;
    	}
    	
		try {
			CreditOrder creditOrder = this.getCreditOrder(event.getOrderId());
			System.out.println("Requesting credit service to check customer id " + creditOrder.getCustomerId() + " and credit amount of " + creditOrder.getCreditAmount() + "\n");
			orderEvent
        		.setCustomerId(creditOrder.getCustomerId())
        		.setCreditAmount(creditOrder.getCreditAmount());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		return orderEvent;
    }
    
    private void updateOrderStatusOnFail(CustomerCheckEvent event) {
    	System.out.println("Customer was not found, setting order state to FAILED");
    	creditOrderRepository.findById(event.getOrderId())
        .ifPresent(order -> {
            order.setStatus(FAILED).setRejectionReason("Customer not found");
            creditOrderRepository.save(order);
        });
    }
   
    
    private CreditOrder getCreditOrder(Integer orderId) throws Exception {
    	return this.creditOrderRepository
				.findById(orderId)
				.orElseThrow(() -> new Exception("Order not found by id: " + orderId));
    }

}

