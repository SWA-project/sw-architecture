package swa.creditservice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.SourceType;

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
        int creditLimit = 50000;

        Iterator<Verdict> it = creditRepository.findAll().iterator();

        while (it.hasNext()) {
            Verdict v = it.next();
            if (!v.getVerdict() && v.getCustomerId().compareTo(customerId) == 0) {
                creditLimit-=1000;
            }

            if (creditLimit < 45000) {
                break;
            }
        }
        if (creditAmount > creditLimit) {
            return false;
        }

        return true;
    }
}
