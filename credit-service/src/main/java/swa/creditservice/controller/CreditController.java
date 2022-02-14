package swa.creditservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import swa.creditservice.domain.CreditRepository;
import swa.creditservice.domain.Loan;
import swa.creditservice.messagebroker.CreditProducer;
import swa.creditservice.service.CreditCalculationService;
import swa.creditservice.web.CreateLoanRequest;
import swa.creditservice.web.CreateLoanResponse;
import swa.creditservice.web.LoanResponse;

@RestController
public class CreditController {
    private CreditCalculationService creditService;
    private CreditRepository creditRepository;
    private CreditProducer producer;
  
    @Autowired
    public CreditController(CreditCalculationService creditService, CreditRepository creditRepository, CreditProducer producer) {
      this.creditRepository = creditRepository;
      this.creditService = creditService;
      this.producer = producer;
    }

    @RequestMapping(value = "/calculateCredit", method = RequestMethod.POST)
    public CreateLoanResponse createVerdict(@RequestBody CreateLoanRequest createLoanRequest) {
      Loan verdict = creditService.reserveCredit(createLoanRequest.getCustomerId(), createLoanRequest.getCustomerMoney(), createLoanRequest.getCreditAmount());
      creditService.createVerdict(verdict.getCustomerId(), verdict.getCreditAmount(), verdict.getValid());
      if (verdict.getValid()) {
        this.producer.sendMessage("GRANTED");
      } else {
        this.producer.sendMessage("REJECTED");
      }
      
      return new CreateLoanResponse(verdict.getId());
    }

  
    @RequestMapping(value="/loans/{loanId}", method= RequestMethod.GET)
    public ResponseEntity<LoanResponse> getCustomer(@PathVariable Long loanId) {
      return creditRepository
              .findById(loanId)
              .map(c -> new ResponseEntity<>(new LoanResponse(c.getCustomerId(), c.getCreditAmount(), c.getValid()), HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
