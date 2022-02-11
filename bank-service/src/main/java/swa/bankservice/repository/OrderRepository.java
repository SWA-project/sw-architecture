package swa.bankservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import swa.bankservice.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
  List<Order> findAllByOrderDetailsCustomerId(Long customerId);
}