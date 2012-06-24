package dsl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanRulesExplicitStates {

  private static final int SUPER_POSITIVE_BALANCE = 10000;
  private static final int SUPER_NEGATIVE_BALANCE = -5000;
  private final List<Event> events = new ArrayList<Event>();
  
  public static class Transition {
    public final Condition condition;
    public final State from;
    public State to;
    
    public Transition(Condition condition, State from) {
      this.condition = condition;
      this.from = from;
    }

    public Transition loanIs(State to) {
      this.to = to;
      return this;      
    }

    public Transition butWhen(Condition c) {
      return from.when(c);
    }

    public Transition andThenWhen(Condition c) {
      return to.when(c);
    }
  }
  
  public static class State {    
    public void run(Event e) {}
      
    private List<Transition> transitions = new ArrayList<Transition>();
    
    public State processEvent(Event e) {
      for (Transition t : transitions)
        if (t.condition.check(e))
          return t.to;
      return null;
    }

    public Transition when(Condition c) {
      Transition t = new Transition(c, this);
      transitions.add(t);
      return t;
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
    // Behavior of each state is also pretty much  localized.
        
    idle.when(isPayment).loanIs(active);
    
    active.when(isDelayedPayment).loanIs(restricted)
      .butWhen(isLastPayment).loanIs(waitForPositive)
      .andThenWhen(isPositiveBalance).loanIs(done);
    
    restricted.when(isDelayedPayment).loanIs(superRestricted)
      .butWhen(isPayment).loanIs(active);
    
    waitForFine.when(isFinePaid).loanIs(active);
    
    superRestricted.when(isSuperPositiveBalance).loanIs(waitForFine);
    
    done.when(always).loanIs(idle);
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
