package watchdog;

import java.net.URL;
import java.util.Date;
import java.util.Scanner;

public class Watchdog {

  private final boolean checkContent;
  private final int minLength;

  public Watchdog(boolean checkContent, int minLength) {
    this.checkContent = checkContent;
    this.minLength = minLength;
  }

  public void check(String address) throws Exception {
    URL url = new URL(address);
    StringBuilder result = new StringBuilder();
    for (Scanner sc = new Scanner(url.openStream()); sc.hasNext(); ) {
      result.append(sc.nextLine()).append("\n");
    }
    String html = result.toString();
    if ((minLength >= 0) && html.length() < minLength || 
        checkContent && !html.contains("body")) { 
      Date now = new Date();
      System.out.println("Alert fired at " + now + " - please check!");
    }
  }

  public static void main(String[] args) throws Exception {    
    Watchdog watchdog = new Watchdog(true, 5000);
    watchdog.check("http://www.jquery.org");   
  }
}
