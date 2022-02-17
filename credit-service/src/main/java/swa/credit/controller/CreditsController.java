package swa.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import swa.credit.model.Credit;
import swa.credit.service.CreditService;

@RestController
public class CreditsController {

    private final CreditService creditService;

    @Autowired
    public CreditsController(CreditService transactionService) {
        this.creditService = transactionService;
    }

    @GetMapping("transactions")
    public Flux<Credit> getAllOrders() {
        return creditService.getAll();
    }

    @GetMapping(path = "transactions/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<Credit>> getAllOrdersStream() {
        return creditService.reactiveGetAll();
    }

    @GetMapping("transactions/{id}")
    public Mono<Credit> getOrderById(@PathVariable Integer id) {
        return creditService.getOrderById(id);
    }

}
