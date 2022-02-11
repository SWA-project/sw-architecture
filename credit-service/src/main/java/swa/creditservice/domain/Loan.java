package swa.creditservice.domain;

import javax.persistence.*;

@Entity
@Table(name="Loan")
@Access(AccessType.FIELD)
public class Loan {
    
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long customerId; 
  private int creditAmount;
  private boolean valid;

  @Version
  private Long version;

  public Loan() {
  }

  public Loan(Long customerId, int creditAmount, boolean valid) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
    this.valid = valid;
  }

  public Long getCustomerId() {
      return customerId;
  }

  public boolean getValid() {
    return valid;
}

  public Long getId() {
    return id;
  }

  public int getCreditAmount() {
    return creditAmount;
  }
}
