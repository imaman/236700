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
    
    private final Checker checker;
    
    public Alerter(Checker checker) {
      this.checker = checker;
    }

    void alert() {
      Date now = new Date();
      System.out.println("Alert fired at " + now + " - please check!");
    }

    public void check(String address) throws Exception {
      if (!checker.check(address)) {
        alert();
      }
    }
  }
  
  public static class Checker {

    private int minLength;
    private boolean checkContent;
    private boolean checkLength;
    private final Fetcher fetcher = new Fetcher();


    public Checker(boolean checkContent, boolean checkLength) {
      this.checkContent = checkContent;
      this.checkLength = checkLength;
    }

    public void setMinLength(int newValue) {
      this.minLength = newValue;
    }

    public boolean check(String address) throws Exception {
      String html = fetcher.fetch(address);
      if (checkLength && html.length() < minLength) {
        return false;
      }
      if (checkContent && !html.contains("body")) {
        return false;
      }
      return true;
    }
  }

  private final Alerter alerter;
    
  public Watchdog(Alerter alerter) {
    this.alerter = alerter;
  }
  
  public void run(String address) throws Exception {
    alerter.check(address);
  }
  
  public static void main(String[] args) throws Exception {    
    Checker checker = new Checker(true, true);
    checker.setMinLength(5000);
    new Watchdog(new Alerter(checker)).run("http://www.jquery.org");   
  }
}
