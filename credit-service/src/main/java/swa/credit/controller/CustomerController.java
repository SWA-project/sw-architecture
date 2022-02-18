package swa.credit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import swa.credit.dto.CustomerDTO;
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
    public CustomerDTO createOrder(@RequestBody Customer customer) {
    	Customer updatedCustomer = new Customer();
    	Optional<Customer> match = this.customerRepository.findByCustomerId(customer.getCustomerId());
    	if (match.isPresent()) {
    		updatedCustomer = match.get();
    		updatedCustomer.setBalance(customer.getBalance());
			this.customerRepository.save(updatedCustomer);
			System.out.print("Updated customer: " + updatedCustomer.getCustomerId() + ", balance: " + updatedCustomer.getBalance() + "\n");
    	} else {
    		updatedCustomer = this.customerRepository.save(customer);
			System.out.print("New customer added ID: " + updatedCustomer.getCustomerId() + ", balance: " + updatedCustomer.getBalance() + "\n");
    	}
    	return new CustomerDTO()
    			.setBalance(updatedCustomer.getBalance())
    			.setCustomerId(updatedCustomer.getCustomerId())
    			.setTotalCredits(this.calculateCustomerTotalCredit(updatedCustomer));
    }

    @GetMapping("customers")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> response = new ArrayList<>();
        for (Customer customer : customers) {
        	response.add(new CustomerDTO()
        			.setBalance(customer.getBalance())
        			.setCustomerId(customer.getCustomerId())
        			.setTotalCredits(this.calculateCustomerTotalCredit(customer))	
        			);
        }
        return response;
    }

    private int calculateCustomerTotalCredit(Customer customer) {
    	return customer.getCustomerCredits() != null 
				? customer.getCustomerCredits()
						.stream()
						.mapToInt(x -> x.getAmount())
						.sum() 
				: 0;
    }
}
