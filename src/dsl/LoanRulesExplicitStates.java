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
  
  public static class Transition {
    public final Condition condition;
    public final State to;
    
    public Transition(Condition condition, State to) {
      this.condition = condition;
      this.to = to;
    }
  }
  
  public static class State {    
    public void run(Event e) {}
      
    private List<Transition> transitions = new ArrayList<Transition>();
    
    public void addTransition(Condition condition, State to) {
      transitions.add(new Transition(condition, to));
    }

    public State processEvent(Event e) {
      for (Transition t : transitions)
        if (t.condition.check(e))
          return t.to;
      return null;
    }
  }

  
  public abstract static class Condition {
    public abstract boolean check(Event e);
  }
  
  State idle = new State(); 
  State active = new State();
  State waitForFine = new State();  
  State waitForPositive = new State();

  State restricted = new State() {
    @Override
    public void run(Event e) {        
      if (e.isBalanceEvent() && e.getBalance() < 0)
        e.reject();
    }
  };
    
  State superRestricted = new State() {
    @Override
    public void run(Event e) {
      if (e.isBalanceEvent() && e.getAmount() < 0)
        e.reject();    
    }
  };
    
  State done = new State() {
    @Override
    public void run(Event e) {
      System.out.println("-- loan returned --");
    }
  };  
  
  public static Condition isPayment = new Condition() {
    @Override
    public boolean check(Event e) {
      return e.isPayment();
    }
  };

  public static Condition isDelayedPayment = new Condition() {
    @Override
    public boolean check(Event e) {
      return e.isDelayedPayment();
    }
  };
  
  public static Condition isLastPayment = new Condition() {
    @Override
    public boolean check(Event e) {
      return e.isLastPayment();
    }
  };
  
  public static Condition isFinePaid = new Condition() {
    @Override
    public boolean check(Event e) {
      return e.isFineEvent();
    }
  };

  public static Condition isSuperPositiveBalance = new Condition() {
    @Override
    public boolean check(Event e) {
      return e.isBalanceEvent() && e.getBalance() > SUPER_POSITIVE_BALANCE;
    }
  };

  public static Condition isPositiveBalance = new Condition() {
    @Override
    public boolean check(Event e) {
      return e.isBalanceEvent() && e.getBalance() > 0;
    }
  };

  public static Condition always = new Condition() {
    @Override
    public boolean check(Event e) {
      return true;
    }
  };
  
  public LoanRulesExplicitStates() {
    // Overall structure of the machine is now well localized
    // (although each state is now less localized).
    
    idle.addTransition(isPayment, active);
    active.addTransition(isDelayedPayment, restricted);
    active.addTransition(isLastPayment, waitForPositive);    
    restricted.addTransition(isDelayedPayment, superRestricted);
    restricted.addTransition(isPayment, active);    
    waitForFine.addTransition(isFinePaid, active);   
    superRestricted.addTransition(isSuperPositiveBalance, waitForFine);    
    waitForPositive.addTransition(isPositiveBalance, done);
    done.addTransition(always, idle);
  }
  
  public void run() {
    State state = idle;
    for(Event e : events) {
      State next = null;
      if (e.isBalanceEvent() && e.getBalance() < SUPER_NEGATIVE_BALANCE)
        next = restricted; 
      else  
        next = state.processEvent(e);
      
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
    for (Transition t : state.transitions) 
      traverse(t.to, visited);
  }
  
  public static void main(String[] args) {
    LoanRulesExplicitStates machine = new LoanRulesExplicitStates();
    System.out.println(machine.isReachable(machine.superRestricted, machine.done));
  }   
}
