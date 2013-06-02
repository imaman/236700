package com.github.imaman.inheritance;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public abstract class Bicycle {

  private String size;
  private TireSize tireSize;
  private CustomSpares customSpares;
  
  public interface TireSize {
    public String calculate();
  }
  
  public interface CustomSpares {
    public void populate(Map<String, String> output);
  }
  
  public Bicycle(String json, TireSize tireSize, CustomSpares customSpares) {
    this(new Record(json), tireSize, customSpares);
  }
  public Bicycle(Record record, TireSize tireSize, CustomSpares customSpares) {
    this.size = record.get("size");
    this.tireSize = tireSize;
    this.customSpares = customSpares;
  }
  
  public Record spares() {
    // Delegate tire-size-calculation to another object.
    return new Record("chain: '10-speed', tire_size: '" + tireSize.calculate() + "', " 
        + customSpares());      
  }

  private final String customSpares() {
    Map<String, String> map = new HashMap<>();
    customSpares.populate(map);
    StringBuilder sb = new StringBuilder();
    for (Entry<String, String> entry : map.entrySet()) {
      sb.append(sb.length() > 0 ? ", " : "");
      sb.append(entry.getKey() + ": " + entry.getValue());
    }
    
    return sb.toString();
  }
  
  public String getSize() {
    return size;
  }
  
  
  // additional method are here..
}
