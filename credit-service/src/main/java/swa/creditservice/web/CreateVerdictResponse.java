package swa.creditservice.web;

public class CreateVerdictResponse {
    private Long verdictId;

    public CreateVerdictResponse() {
    }
  
    public CreateVerdictResponse(Long verdictId) {
      this.verdictId = verdictId;
    }
  
    public Long getVerdictId() {
      return verdictId;
    }

    public void setVerdictrId(Long verdictId) {
      this.verdictId = verdictId;
    }
  }