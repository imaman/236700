package dsl;

import java.util.ArrayList;
import java.util.List;

public class LoanRulesExplicitStates {

  private static final int SUPER_POSITIVE_BALANCE = 10000;
  private static final int SUPER_NEGATIVE_BALANCE = -5000;
  private final List<Event> events = new ArrayList<Event>();
  
  public abstract static class State {    
    public abstract State run(Event e);
  }
  

  State idle = new State() {

    @Override
    public State run(Event e) {
      if (e.isPayment())
        return active;
      return null;
    }
  };
  
  State active = new State() {
    @Override
    public State run(Event e) {
      if (e.isDelayedPayment()) 
        return restricted;
      else if (e.isLastPayment())
        return waitForPositive;
      else if (e.isPayment()) {
        // Process the payment ...
      }
      
      return null;
    }
  };
    
  State restricted = new State() {
      @Override
      public State run(Event e) {
      if (e.isDelayedPayment())
        return superRestricted;
      else if (e.isBalanceEvent() && e.getBalance() < 0)
        e.reject();
      else if (e.isPayment())
        return active;
      
      return null;
    }
  };

  State waitForFine = new State() {
    @Override
    public State run(Event e) {
      if (e.isFineEvent())
        return active;
      
      return null;
    }
  };

  State superRestricted = new State() {
    @Override
    public State run(Event e) {
      if (e.isBalanceEvent() && e.getAmount() < 0)
        e.reject();    
      else if (e.isBalanceEvent() && e.getBalance() > SUPER_POSITIVE_BALANCE)
        return waitForFine;
      
      return null;
    }
  };

  State waitForPositive = new State() {
    @Override
    public State run(Event e) {
      if (e.isBalanceEvent() && e.getBalance() > 0)
        return done;
      return null;
    }
  };
  

  State done = new State() {
    @Override
    public State run(Event e) {
      System.out.println("-- loan returned --");
      return idle;
    }
  };

  public void run() {
    State state = idle;
    for(Event e : events) {
      State next = null;
      if (e.isBalanceEvent() && e.getBalance() < SUPER_NEGATIVE_BALANCE)
        next = restricted; 
      else 
        next = state.run(e);
      
      state = next == null ? state : next;
    }
  }
}
