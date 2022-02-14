package swa.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import swa.credit.model.Customer;
import swa.credit.repository.CustomerRepository;

@RestController
public class CustomerController {
	
	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
    @PostMapping("customers")
    public Customer createOrder(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

}