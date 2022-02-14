package swa.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swa.order.model.CreditOrder;

@Repository
public interface CreditOrderRepository extends JpaRepository<CreditOrder, Integer> {
}
