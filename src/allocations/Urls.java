package allocations;

import java.util.HashMap;
import java.util.Map;

public class Urls {

  public final String transactionsOnDate;
  public final String createTransaction;
  public final String deleteTransaction;
  
  public Urls(Map<String, Object> params) {
    transactionsOnDate = populate("transactions/{sourceType}/on/{year}/{month}", params); 
    createTransaction = populate("transactions/{sourceType}/{sourceId}/{targetType}/{targetId}/{year}/{month}/{percentage}", params);
    deleteTransaction = populate("transactions/{sourceType}/{sourceId}/{targetType}/{targetId}/{year}/{month}", params);
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
  }
}
