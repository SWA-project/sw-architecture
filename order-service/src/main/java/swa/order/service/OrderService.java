package swa.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import swa.order.dto.OrderRequestDTO;
import swa.order.enums.OrderStatus;
import swa.order.handler.CustomerCheckPublisher;
import swa.order.model.CreditOrder;
import swa.order.repository.CreditOrderRepository;

@Service
public class OrderService {

    private final CreditOrderRepository creditOrderRepository;
    private final CustomerCheckPublisher creditOrderPublisher;

    @Autowired
    public OrderService(
            CreditOrderRepository creditOrderRepository,
            CustomerCheckPublisher creditOrderPublisher) {
        this.creditOrderRepository = creditOrderRepository;
        this.creditOrderPublisher = creditOrderPublisher;
    }

    public CreditOrder createOrder(OrderRequestDTO order) {
        CreditOrder creditOrder = getCreditOrder(order);
        CreditOrder savedCreditOrder = creditOrderRepository.save(creditOrder);
        creditOrderPublisher.publish(creditOrder);
        return savedCreditOrder;
    }

    public List<CreditOrder> getAll() {
        return creditOrderRepository.findAll();
    }

    public CreditOrder getOrderById(Integer id) {
        return creditOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order id: " + id + " does not exist"));
    }

    private CreditOrder getCreditOrder(final OrderRequestDTO order) {
        return new CreditOrder()
                .setCustomerId(order.getCustomerId())
                .setCreditAmount(order.getCreditAmount())
                .setStatus(OrderStatus.PENDING);
    }

}
