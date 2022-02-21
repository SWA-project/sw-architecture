package swa.order.handler;

import static swa.order.enums.CustomerStatus.CUSTOMERNOTFOUND;
import static swa.order.enums.OrderStatus.FAILED;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import swa.order.dto.CreditCheckEventDTO;
import swa.order.dto.CustomerCheckResponseDTO;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

@Component
public class CustomerCheckEventConsumer implements EventConsumer<CustomerCheckResponseDTO> {

	private final CreditCheckPublisher creditCheckPublisher;
    private final CreditOrderRepository creditOrderRepository;

    @Lazy
    @Autowired
    public CustomerCheckEventConsumer(
    		CreditOrderRepository creditOrderRepository, 
    		CreditCheckPublisher creditCheckPublisher) {
        this.creditOrderRepository = creditOrderRepository;
        this.creditCheckPublisher = creditCheckPublisher;
    }

    @Transactional
    public void consumeEvent(CustomerCheckResponseDTO event) {
    	System.out.println("Handling response from customer service, response: " + event.getStatus());
    
    	if (event.getStatus().equals(CUSTOMERNOTFOUND)) {
    		this.updateOrderStatusOnFail(event);
    		return;
    	}
    	
		try {
			CreditOrder creditOrder = this.getCreditOrder(event.getOrderId());
			CreditCheckEventDTO creditCheckEvent = new CreditCheckEventDTO()
				.setOrderId(event.getOrderId())
        		.setCustomerId(creditOrder.getCustomerId())
        		.setCreditAmount(creditOrder.getCreditAmount());
			
			System.out.println("Requesting credit service to check customer id " + creditOrder.getCustomerId() + " and credit amount of " + creditOrder.getCreditAmount() + "\n");
			this.creditCheckPublisher.publish(creditCheckEvent);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
    }
    
    private void updateOrderStatusOnFail(CustomerCheckResponseDTO event) {
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

