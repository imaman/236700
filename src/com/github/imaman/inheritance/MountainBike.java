package com.github.imaman.inheritance;

import java.util.Map;

public class MountainBike extends Bicycle {

  private String frontShock;

  public MountainBike(final Record record) {
    super(record, new TireSize() {
      @Override public String calculate() { return "2.1"; } 
    }, new CustomSpares() {      
      @Override public void populate(Map<String, String> output) {
        output.put("rear_shock",  record.get("rear_shock"));
      }
    });
    this.frontShock = record.get("front_shock");
  }

  public MountainBike(String json) {
    this(new Record(json));
  }

  public String getFrontShock() { 
    return frontShock;
  }
  
  // additional method are here..
  
}
