package swa.order.model;

import lombok.Data;

@Data
public class OrderRequest {

    private Integer customerId;
    private int creditAmount;
    
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer userId) {
		this.customerId = userId;
	}
	
	public int getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	

}
