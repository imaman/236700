package com.github.imaman.inheritance;


public class MountainBike extends Bicycle {

  private String frontShock;
  
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
