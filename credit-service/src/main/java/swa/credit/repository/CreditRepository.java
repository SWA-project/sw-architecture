package swa.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swa.credit.model.Credit;


@Repository
public interface CreditRepository extends JpaRepository<Credit, Integer> {
	List<Credit> findByCustomerId(Integer customerId);
}