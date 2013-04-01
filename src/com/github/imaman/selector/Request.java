package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

  private final List<Label> labels = new ArrayList<Label>();
  private final Map<String, String> valueByKey = new HashMap<String, String>();
  private final long time;
  private final String category;
  
  public Request(Long time, String requestedCategory, Label... labels) {
    this(time, requestedCategory, new HashMap<String, String>(), labels);
  }

  public Request(Long time, String requestedCategory, Map<String, String> valueByKey, 
      Label... labels) {
    this.time = time;
    this.category = requestedCategory;
    this.valueByKey.entrySet().addAll(valueByKey.entrySet());
    for (Label label : labels) {
      this.labels.add(label);
    }
  }
  
  public String category() {
    return category;
  }
  
  public String valueOf(String key) {
    return valueByKey.get(key);
  }
  
  public long time() {
    return time;
  }

  public int numLabels() {
    return labels.size();
  }
  
  public Label label(int index) {
    return labels.get(index);
  }
}
