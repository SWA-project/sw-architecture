package swa.order.handler;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swa.order.dto.OrderCreatedEvent;
import swa.order.dto.CustomerCheckEvent;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

import static swa.order.enums.CustomerStatus.CUSTOMERNOTFOUND;

@Component
public class CustomerCheckEventHandler implements EventHandler<CustomerCheckEvent, OrderCreatedEvent> {

    private final CreditOrderRepository creditOrderRepository;

    @Autowired
    public CustomerCheckEventHandler(CreditOrderRepository creditOrderRepository) {
        this.creditOrderRepository = creditOrderRepository;
    }

    @Transactional
    public OrderCreatedEvent handleEvent(CustomerCheckEvent event) {
                
    	OrderCreatedEvent orderEvent = new OrderCreatedEvent();
    	
    	if (event.getStatus().equals(CUSTOMERNOTFOUND)) {
    		return orderEvent;
    	}
    	
		try {
			CreditOrder creditOrder = this.getCreditOrder(event.getOrderId());
			orderEvent
        		.setOrderId(creditOrder.getId())
        		.setCustomerId(creditOrder.getCustomerId())
        		.setCreditAmount(creditOrder.getCreditAmount());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return orderEvent;
    }
    
    private CreditOrder getCreditOrder(Integer orderId) throws Exception {
    	return this.creditOrderRepository
				.findById(orderId)
				.orElseThrow(() -> new Exception("Order not found by id: " + orderId));
    }

}

