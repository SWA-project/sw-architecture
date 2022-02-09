package swa.creditservice.domain;

import javax.persistence.*;

@Entity
@Table(name="Verdict")
@Access(AccessType.FIELD)
public class Verdict {
    
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long customerId; 
  private int creditAmount;
  private boolean verdict;

  @Version
  private Long version;

  public Verdict() {
  }

  public Verdict(Long customerId, int creditAmount, boolean verdict) {
    this.customerId = customerId;
    this.creditAmount = creditAmount;
    this.verdict = verdict;
  }

  public Long getCustomerId() {
      return customerId;
  }

  public boolean getVerdict() {
    return verdict;
}

  public Long getId() {
    return id;
  }

  public int getCreditAmount() {
    return creditAmount;
  }
}
