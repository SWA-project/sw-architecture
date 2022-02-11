package swa.creditservice.domain;

import org.springframework.data.repository.CrudRepository;

public interface CreditRepository extends CrudRepository<Loan, Long> {
}
