package swa.credit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swa.credit.model.Credit;

@Repository
public interface CustomerRepository extends JpaRepository<Credit, Integer> {
}
