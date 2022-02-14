package payment.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.payment.model.Credit;

@Repository
public interface CustomerRepository extends JpaRepository<Credit, Integer> {
}
