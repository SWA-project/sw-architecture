package swa.credit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
	private Integer customerId;
    private double balance;
    private int totalCredits;
	
	public CustomerDTO setCustomerId(Integer customerId) {
		this.customerId = customerId;
		return this;
	}
	
	public CustomerDTO setBalance(double balance) {
		this.balance = balance;
		return this;
	}

	public CustomerDTO setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
		return this;
	}
	
	
    
    
}
