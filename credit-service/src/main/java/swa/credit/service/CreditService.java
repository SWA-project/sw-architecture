package swa.credit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import swa.credit.model.Credit;
import swa.credit.repository.CreditRepository;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    @Autowired
    public CreditService(CreditRepository transactionRepository) {
        this.creditRepository = transactionRepository;
    }

    public List<Credit> getAll() {
        return creditRepository.findAll();
    }

    public Credit getOrderById(Integer id) {
        return creditRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Credit id: " + id + " does not exist"));
    }

}
