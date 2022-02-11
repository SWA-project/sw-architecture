package swa.bankservice.model;

import javax.persistence.Embeddable;

@Embeddable
public class OrderDetails {
	private Long customerId;
	private int creditAmount;
	private int interestRate;
	
	public OrderDetails() {
	}
	
	public OrderDetails(Long customerId, int creditAmount) {
		this.customerId = customerId;
		this.creditAmount = creditAmount;
		this.interestRate = 10;
	}
	
	public OrderDetails(Long customerId, int creditAmount, int interestRate) {
		this.customerId = customerId;
		this.creditAmount = creditAmount;
		this.interestRate = interestRate;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public int getCreditAmount() {
		return creditAmount;
	}

	public int getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}    
}