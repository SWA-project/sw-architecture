package swa.creditservice.web;

public class CreateLoanResponse {
    private Long loanId;

    public CreateLoanResponse() {
    }
  
    public CreateLoanResponse(Long loanId) {
      this.loanId = loanId;
    }
  
    public Long getloanId() {
      return loanId;
    }

    public void setVerdictrId(Long loanId) {
      this.loanId = loanId;
    }
  }