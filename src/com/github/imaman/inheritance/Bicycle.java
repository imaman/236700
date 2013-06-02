package com.github.imaman.inheritance;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public abstract class Bicycle {

  public interface TireSize {
    public String calculate();
  }
  
  public interface CustomSpares {
    public void populate(Record record, Map<String, String> map);
  }

  protected static final CustomSpares REAR_SHOCK_SPARES = new CustomSpares() {      
    @Override public void populate(Record record, Map<String, String> output) {
      output.put("rear_shock",  record.get("rear_shock"));
    }
  };
  
  protected static final TireSize MOUNTAIN_BIKE_TIRE_SIZE = new TireSize() {
    @Override public String calculate() { return "2.1"; } 
  };
  
  protected static CustomSpares TAPE_COLOR_SPARES = new CustomSpares() {      
    @Override public void populate(Record record, Map<String, String> output) {
      output.put("tape_color", record.get("tape_color"));
    }
  };
  
  protected static TireSize ROAD_BIKE_TIRE_SIZE = new TireSize() {
    @Override public String calculate() { return "23"; }
  };
    
  private String size;
  private TireSize tireSize;
  private CustomSpares customSpares;
  private Record record;
  
  public Bicycle(String json, TireSize tireSize, CustomSpares customSpares) {
    this(new Record(json), tireSize, customSpares);
  }
  public Bicycle(Record record, TireSize tireSize, CustomSpares customSpares) {
    this.size = record.get("size");
    this.tireSize = tireSize;
    this.customSpares = customSpares;
    this.record = record;
  }
  
  public Record spares() {
    // Delegate tire-size-calculation to another object.
    return new Record("chain: '10-speed', tire_size: '" + tireSize.calculate() + "', " 
        + customSpares());      
  }

  private final String customSpares() {
    Map<String, String> map = new HashMap<>();
    customSpares.populate(record, map);
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
