package swa.order.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderRequest {

	@Valid
	@NotNull(message = "Customer id is required.")
    private Integer customerId;
	
	@Valid
	@NotNull(message = "Credit amount is required.")
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
