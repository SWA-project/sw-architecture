package swa.creditservice.service;

import javax.transaction.Transactional;

import swa.creditservice.domain.CreditRepository;
import swa.creditservice.domain.Verdict;

public class CreditCalculationService {
    
    private CreditRepository creditRepository;

    public CreditCalculationService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Transactional
    public Verdict createVerdict(Long customerId, int creditAmount, boolean verdict) {
        Verdict newVerdict  = new Verdict(customerId, creditAmount, verdict);
        return creditRepository.save(newVerdict);
    }

    public boolean calculateVerdict(Long customerId, int creditAmount) {
        if (creditAmount > 10000) {
            return false;
        }
        return true;
    }
}
