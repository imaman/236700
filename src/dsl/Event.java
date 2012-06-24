package dsl;

public class Event {

  public String kind;
  boolean rejected = false;
  
  public Event(String kind) {
    this.kind = kind;
  }

  public boolean isPayment() {
    return kind.equals("payment");
  }

  public boolean isLastPayment() {
    return kind.equals("last-payment");
  }

  public boolean isBalanceEvent() {
    return kind.startsWith("+") || kind.startsWith("-");
  }

  public boolean isDelayedPayment() {
    return kind.equals("delayed");
  }

  public void reject() {
    rejected = true;
  }

  public int getAmount() {
    if (!isBalanceEvent())
      throw new AssertionError();
    
    return Integer.parseInt(kind);
  }

  public boolean isFineEvent() {
    return kind.equals("fine");
  }

  public int getBalance() {
    // TODO Auto-generated method stub
    return 0;
  }

}
