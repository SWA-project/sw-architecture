package swa.order.model;

import lombok.Data;
import lombok.ToString;
import swa.order.enums.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@ToString
public class CreditOrder {

    public CreditOrder() {
    }

    @Id
    @GeneratedValue
    private Integer id;
    private Integer customerId;
    private OrderStatus status;
    private int creditAmount;

    public CreditOrder setCustomerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreditOrder setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public CreditOrder setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
		return this;
	}
    
	public Integer getId() {
		return id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public int getCreditAmount() {
		return creditAmount;
	}
	
}
