package allocations;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class Urls {

  private static final String TARGET = "target";
  private static final String SOURCE = "source";
  private static final String TYPE = "Type";
  private static final String ID = "Id";
  private static final String SEP = "/";
  private static final String TRANSACTIONS = "transactions" + SEP;
  private static final String TARGET_TYPE_AND_ID = SEP + var(TARGET + TYPE) + SEP + var(TARGET + ID) + SEP;
  private static final String SOURCE_ID = var(SOURCE + ID);
  private static final String SOURCE_TYPE = var(SOURCE + TYPE);
  private static final String SOURCE_TYPE_AND_ID = SOURCE_TYPE + SEP + SOURCE_ID;
  private static final String WHEN = var("year") + SEP + var("month");
  private static final String SOURCE_TARGET_WHEN = SOURCE_TYPE_AND_ID + TARGET_TYPE_AND_ID + WHEN;
  
  public final String transactionsOnDate;
  public final String createTransaction;
  public final String deleteTransaction;
  
  public Urls(Map<String, Object> params) {
    transactionsOnDate = populate(TRANSACTIONS + SOURCE_TYPE + SEP + "on" + SEP + WHEN, params); 
    createTransaction = populate(TRANSACTIONS + SOURCE_TARGET_WHEN + SEP + var("percentage"), params);
    deleteTransaction = populate(TRANSACTIONS + SOURCE_TARGET_WHEN, params);
  }

  private static String var(String variableName) {
    return "{" + variableName + "}";
  }

  private String populate(String template, Map<String, Object> params) {
    StringBuilder result = new StringBuilder();
    while (true) {
      int openAt = template.indexOf("{");
      if (openAt < 0) 
        return result.append(template).toString();
      result.append(template.substring(0, openAt));
      template = template.substring(openAt + 1);
      int closeAt = template.indexOf("}");
      if (closeAt < 0)
        throw new IllegalArgumentException("Missing }");
      
      String key = template.substring(0, closeAt);
      System.out.println(key);
      result.append(params.get(key).toString());
      template = template.substring(closeAt + 1);
    }
  }
  
  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<String, Object>();
    
    map.put("sourceType", "sT");
    map.put("sourceId", "sID");
    map.put("targetType", "tT");
    map.put("targetId", "tID");
    map.put("year", "YY");
    map.put("month", "MM");
    map.put("percentage", "%%");
    
    Urls urls = new Urls(map);
    
    System.out.println(urls.createTransaction);
    System.out.println(urls.deleteTransaction);
    System.out.println(urls.transactionsOnDate);

    assertEquals("transactions/sT/sID/tT/tID/YY/MM/%%", urls.createTransaction);
    assertEquals("transactions/sT/sID/tT/tID/YY/MM", urls.deleteTransaction);
    assertEquals("transactions/sT/on/YY/MM", urls.transactionsOnDate);
  }
}
