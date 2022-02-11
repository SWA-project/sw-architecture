package swa.creditservice.service;

import java.util.Iterator;

import javax.transaction.Transactional;


import swa.creditservice.domain.CreditRepository;
import swa.creditservice.domain.Loan;

public class CreditCalculationService {
    
    private CreditRepository creditRepository;

    public CreditCalculationService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Transactional
    public Loan createVerdict(Long customerId, int creditAmount, boolean verdict) {
        Loan newVerdict  = new Loan(customerId, creditAmount, verdict);
        return creditRepository.save(newVerdict);
    }

    public boolean reserveCredit(Long customerId, int customerMoney, int creditAmount) {
        Iterator<Loan> it = creditRepository.findAll().iterator();
        int totalLoans = 0;
        while (it.hasNext()) {
            Loan loan = it.next();
            if (loan.getValid() && loan.getCustomerId().compareTo(customerId) == 0) {
                totalLoans += loan.getCreditAmount();
            }
        }
        
        if ((customerMoney-totalLoans)/2 > creditAmount) {
            return true;
        }

        return false;
    }
}
