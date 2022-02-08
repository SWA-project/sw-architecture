package swa.bankservice.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import swa.bankservice.model.CreditCardOrder;

public interface CreditCardOrderRepository extends CrudRepository<CreditCardOrder, Long> {
  List<CreditCardOrder> findAllByOrderDetailsCustomerId(Long customerId);
}