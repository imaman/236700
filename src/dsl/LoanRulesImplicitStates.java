package dsl;

import java.util.ArrayList;
import java.util.List;

public class LoanRulesImplicitStates {

  private static final int SUPER_POSITIVE_BALANCE = 10000;
  private int index = 0;
  private final List<Event> events = new ArrayList<Event>();

  //idle -> [payment] -> active -> [payment] -> active -> [paid 100%] -> waitForPositive -> [positive balance] -> done
  //
  //    active -> [missed payment] -> restricted -> [payment] -> restricted -> [paid 100%] -> waitForFine -> [fine-paid] -> waitForPositive
  //
  //    restricted -> [missed payment] -> super-restricted (withdrawls are limited) -> [super-positive-balance] -> waitForFine
  //
  //    active -> [early-exit-requested] -> wait-for-100%-payment -> [paid 100%] -> wait-for-exit-fine -> [exit-fine-payed] -> done
  //
  //    wait-for-exit-fine -> [super-positive-balance] -> done
  //
  //    wait-for-100%-payment -> [payment] -> active
  //
  //    done -> [loan requested] -> idle

  public void idle() {
    Event e = next();
    if (e.isPayment()) 
      active();
    
    idle();
  }

  private void active() {
    Event e = next();
    
    if (e.isDelayedPayment()) 
      restricted();
    else if (e.isLastPayment())
      waitForPositive();
    else if (e.isPayment()) {
      // Process the payment ...
      active();
    }
    
    active();
  }
    
  private void restricted() {
    Event e = next();
    
    if (e.isDelayedPayment())
      superRestricted();
    else if (e.isBalanceEvent() && e.getBalance() < 0)
      e.reject();
    else if (e.isPayment())
      active();
    
    restricted();
  }

  private void waitForFine() {
    Event e = next();
    if (e.isFineEvent())
      active();
    
    waitForFine();
  }

  private void superRestricted() {
    Event e = next();
    
    if (e.isBalanceEvent() && e.getAmount() < 0)
      e.reject();    
    else if (e.isBalanceEvent() && e.getBalance() > SUPER_POSITIVE_BALANCE)
      waitForFine();
    
    superRestricted();
  }

  private void waitForPositive() {
    Event e = next();
    if (e.isBalanceEvent() && e.getBalance() > 0)
      done();
    
    waitForPositive();
  }

  private void done() {
    System.out.println("-- loan returned --");
    idle();
  }

  private Event next() {
    return events.get(index++);
  }
  
}
