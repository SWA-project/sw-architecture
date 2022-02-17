package swa.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Credit> getAllOrders() {
        return creditService.getAll();
    }

    @GetMapping("transactions/{id}")
    public Credit getOrderById(@PathVariable Integer id) {
        return creditService.getOrderById(id);
    }

}
