package com.github.imaman.inheritance;

import java.util.Map;

public class MountainBike extends Bicycle {

  private String frontShock;
  
  private static final CustomSpares REAR_SHOCK_SPARES = new CustomSpares() {      
    @Override public void populate(Record record, Map<String, String> output) {
      output.put("rear_shock",  record.get("rear_shock"));
    }
  };
  
  private static final TireSize MOUNTAIN_BIKE_TIRE_SIZE = new TireSize() {
    @Override public String calculate() { return "2.1"; } 
  };

  public MountainBike(final Record record) {
    super(record, MOUNTAIN_BIKE_TIRE_SIZE, REAR_SHOCK_SPARES);
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
