package com.github.imaman.inheritance;

public class MountainBike extends Bicycle {

  private String frontShock;
  private String rearShock;

  public MountainBike(Record record) {
    super(record, new TireSize() {
      @Override public String calculate() { return "2.1"; } 
    });
    this.frontShock = record.get("front_shock");
    this.rearShock = record.get("rear_shock");
  }

  public MountainBike(String json) {
    this(new Record(json));
  }

  @Override
  public String customSpares() {
    return "rear_shock: '" + rearShock + "'";      
  }
  
  public String getFrontShock() { 
    return frontShock;
  }
  
  // additional method are here..
  
}
