package swa.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import swa.order.model.CreditOrder;
import swa.order.model.OrderRequest;
import swa.order.service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders")
    public Mono<CreditOrder> createOrder(@RequestBody OrderRequest order) {
        return orderService.createOrder(order);
    }

    @GetMapping("orders")
    public Flux<CreditOrder> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping(path = "orders/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<CreditOrder>> getAllOrdersStream() {
        return orderService.reactiveGetAll();
    }

    @GetMapping("orders/{id}")
    public Mono<CreditOrder> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

}
