package swa.creditservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import swa.creditservice.domain.CreditRepository;
import swa.creditservice.domain.Verdict;
import swa.creditservice.service.CreditCalculationService;
import swa.creditservice.web.CreateVerdictRequest;
import swa.creditservice.web.CreateVerdictResponse;
import swa.creditservice.web.VerdictResponse;

@RestController
public class CreditController {
    private CreditCalculationService creditService;
    private CreditRepository creditRepository;
  
    @Autowired
    public CreditController(CreditCalculationService creditService, CreditRepository creditRepository) {
      this.creditRepository = creditRepository;
      this.creditService = creditService;
    }

    @RequestMapping(value = "/calculateCredit", method = RequestMethod.POST)
    public CreateVerdictResponse createVerdict(@RequestBody CreateVerdictRequest createVerdictRequest) {
      boolean verdictCalculation = creditService.calculateVerdict(createVerdictRequest.getCustomerId(), createVerdictRequest.getCreditAmount());
      Verdict verdict = creditService.createVerdict(createVerdictRequest.getCustomerId(), createVerdictRequest.getCreditAmount(), verdictCalculation);
      return new CreateVerdictResponse(verdict.getId());
    }

  
    @RequestMapping(value="/creditVerdict/{verdictId}", method= RequestMethod.GET)
    public ResponseEntity<VerdictResponse> getCustomer(@PathVariable Long verdictId) {
      return creditRepository
              .findById(verdictId)
              .map(c -> new ResponseEntity<>(new VerdictResponse(c.getCustomerId(), c.getCreditAmount(), c.getVerdict()), HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
