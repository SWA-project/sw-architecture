package swa.bankservice.model;

import javax.persistence.Embeddable;

@Embeddable
public class OrderDetails {
	private Long customerId;
	private int creditAmount;
	private int interestRate;
	private int customerMoney;
	
	public OrderDetails() {
	}
	
	public OrderDetails(Long customerId, int creditAmount, int customerMoney) {
		this.customerId = customerId;
		this.creditAmount = creditAmount;
		this.customerMoney = customerMoney;
		this.interestRate = 10;
	}

	public OrderDetails(Long customerId, int creditAmount, int customerMoney, int interestRate) {
		this.customerId = customerId;
		this.creditAmount = creditAmount;
		this.customerMoney = customerMoney;
		this.interestRate = interestRate;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public int getCreditAmount() {
		return creditAmount;
	}

	public int getCustomerMoney() {
		return customerMoney;
	}

	public int getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}    
}