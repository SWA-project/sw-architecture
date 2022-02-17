package swa.credit.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "credits")
public class Credit {

	@Id
    @GeneratedValue
    private Integer id;
	private Integer orderId;
	private int amount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customer_id", nullable=false)
	private Customer customer;

	public Integer getOrderId() {
		return orderId;
	}

	public Credit setOrderId(Integer orderId) {
		this.orderId = orderId;
		return this;
	}

	public int getAmount() {
		return amount;
	}

	public Credit setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Credit setCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}

}
