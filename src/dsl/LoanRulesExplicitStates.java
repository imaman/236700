package dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoanRulesExplicitStates {

  private static final int SUPER_POSITIVE_BALANCE = 10000;
  private static final int SUPER_NEGATIVE_BALANCE = -5000;
  private final List<Event> events = new ArrayList<Event>();
  
  public abstract static class State {    
    public abstract State run(Event e);
    
    private Map<String, State> nextStateByName = new HashMap<String, State>();
    
    void addTransition(String name, State to) {
      nextStateByName.put(name, to);
    }
    
    protected State transition(String transitionName) {
      return nextStateByName.get(transitionName); 
    }
  }

  State idle = new State() {

    @Override
    public State run(Event e) {
      if (e.isPayment())
        return transition("payment");
      return null;
    }
  };

  
  State active = new State() {
    @Override
    public State run(Event e) {
      if (e.isDelayedPayment()) 
        return transition("delyed-payment");
      else if (e.isLastPayment())
        return transition("last-payment");
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
        return transition("delyed-payment");
      else if (e.isBalanceEvent() && e.getBalance() < 0)
        e.reject();
      else if (e.isPayment())
        return transition("payment");
      
      return null;
    }
  };
  
  State waitForFine = new State() {
    @Override
    public State run(Event e) {
      if (e.isFineEvent())
        return transition("fine-paid");
      
      return null;
    }
  };

  
  State superRestricted = new State() {
    @Override
    public State run(Event e) {
      if (e.isBalanceEvent() && e.getAmount() < 0)
        e.reject();    
      else if (e.isBalanceEvent() && e.getBalance() > SUPER_POSITIVE_BALANCE)
        return transition("super-positive");
      
      return null;
    }
  };
  
  State waitForPositive = new State() {
    @Override
    public State run(Event e) {
      if (e.isBalanceEvent() && e.getBalance() > 0)
        return transition("positive");
      return null;
    }
  };
  
  State done = new State() {
    @Override
    public State run(Event e) {
      System.out.println("-- loan returned --");
      return transition("back-to-idle");
    }
  };
  
  public LoanRulesExplicitStates() {
    // Overall structure of the machine is now well localized
    // (although each state is now less localized).
    
    idle.addTransition("payment", active);
    active.addTransition("delayed-payment", restricted);
    active.addTransition("last-payment", waitForPositive);    
    restricted.addTransition("delayed-payment", superRestricted);
    restricted.addTransition("payment", active);    
    waitForFine.addTransition("fine-paid", active);   
    superRestricted.addTransition("super-positive", waitForFine);    
    waitForPositive.addTransition("positive", done);
    done.addTransition("back-to-idle", idle);
  }
  
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
  
  public boolean isReachable(State from, State to) {    
    Set<State> visited = new HashSet<State>();
    traverse(from, visited);
    
    return visited.contains(to);    
  }

  private void traverse(State state, Set<State> visited) {
    if (visited.contains(state))
      return;
    
    visited.add(state);
    for (State s : state.nextStateByName.values())
      traverse(s, visited);
  }
  
  public static void main(String[] args) {
    LoanRulesExplicitStates machine = new LoanRulesExplicitStates();
    System.out.println(machine.isReachable(machine.superRestricted, machine.done));
  }   
}
