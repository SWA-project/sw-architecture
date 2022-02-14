package swa.credit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swa.credit.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}