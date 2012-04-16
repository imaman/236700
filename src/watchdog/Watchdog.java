package watchdog;

import java.net.URL;
import java.util.Date;
import java.util.Scanner;

public class Watchdog {

  public static class Fetcher {
    String fetch(String address) throws Exception {
      URL url = new URL(address);
      StringBuilder result = new StringBuilder();
      for (Scanner sc = new Scanner(url.openStream()); sc.hasNext(); ) {
        result.append(sc.nextLine()).append("\n");
      }
      
      return result.toString();
    }
  }
  
  public static class Alerter {  
    void alert() {
      Date now = new Date();
      System.out.println("Alert fired at " + now + " - please check!");
    }
  }
  
  public static class Checker {

    private int minLength;
    private boolean checkContent;
    private boolean checkLength;

    public Checker(boolean checkContent, boolean checkLength) {
      this.checkContent = checkContent;
      this.checkLength = checkLength;
    }

    public void setMinLength(int newValue) {
      this.minLength = newValue;
    }

    public boolean check(String html) {
      if (checkLength && html.length() < minLength) {
        return false;
      }
      if (checkContent && !html.contains("body")) {
        return false;
      }
      return true;
    }
  }

  private final Checker checker;
  private final Fetcher fetcher;
  private final Alerter alerter;
    
  public Watchdog(Checker checker, Fetcher fetcher, Alerter alerter) {
    this.checker = checker;
    this.fetcher = fetcher;
    this.alerter = alerter;
  }
  
  public void run(String address) throws Exception {
    if (!checker.check(fetcher.fetch(address))) {
      alerter.alert();
    }
  }
  
  public static void main(String[] args) throws Exception {    
    Checker checker = new Checker(true, true);
    checker.setMinLength(5000);
    new Watchdog(checker, new Fetcher(), new Alerter()).run("http://www.jquery.org");   
  }
}