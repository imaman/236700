package watchdog;

import org.junit.Test;
import org.mockito.Mockito;

import watchdog.Watchdog.Alerter;
import watchdog.Watchdog.Checker;
import watchdog.Watchdog.Fetcher;

public class Watchdog_Test {

  private final Alerter alerter = Mockito.mock(Alerter.class);    
  private final Fetcher fetcher = Mockito.mock(Fetcher.class);

  @Test
  public void shouldFireIfHtmlDoesNotContainBody() throws Exception {
    Mockito.when(fetcher.fetch("some-address")).thenReturn("CONTENT");    
    Checker checker = new Checker(true, false);
    
    Watchdog watchdog = new Watchdog(checker, fetcher, alerter);
    watchdog.run("some-address");
    
    Mockito.verify(alerter).alert();
  }
  
  @Test
  public void shouldNotFireIfHtmlContainBody() throws Exception {
    Mockito.when(fetcher.fetch("some-address")).thenReturn("body");    
    Checker checker = new Checker(true, false);
    
    Watchdog watchdog = new Watchdog(checker, fetcher, alerter);
    watchdog.run("some-address");
    
    Mockito.verify(alerter, Mockito.never()).alert();
  }

  @Test
  public void shouldFireIfHtmlIsTooShort() throws Exception {
    Mockito.when(fetcher.fetch("some-address")).thenReturn("123456789");    
    Checker checker = new Checker(false, true);
    checker.setMinLength(10);
    
    Watchdog watchdog = new Watchdog(checker, fetcher, alerter);
    watchdog.run("some-address");
    
    Mockito.verify(alerter).alert();
  }

  @Test
  public void shouldNotFireIfHtmlIsNotTooShort() throws Exception {
    Mockito.when(fetcher.fetch("some-address")).thenReturn("123456789");    
    Checker checker = new Checker(false, true);
    checker.setMinLength(9);
    
    Watchdog watchdog = new Watchdog(checker, fetcher, alerter);
    watchdog.run("some-address");
    
    Mockito.verify(alerter, Mockito.never()).alert();
  }
}
