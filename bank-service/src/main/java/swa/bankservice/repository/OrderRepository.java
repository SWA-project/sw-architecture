package swa.bankservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import swa.bankservice.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
  List<Order> findAllByOrderDetailsCustomerId(Long customerId);
}