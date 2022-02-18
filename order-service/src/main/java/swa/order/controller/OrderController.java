package swa.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import swa.order.dto.OrderRequest;
import swa.order.model.CreditOrder;
import swa.order.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @PostMapping("orders")
    public CreditOrder createOrder(@RequestBody OrderRequest order) {
        return orderService.createOrder(order);
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @GetMapping("orders")
    public List<CreditOrder> getAllOrders() {
        return orderService.getAll();
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @GetMapping("orders/{id}")
    public CreditOrder getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

}
