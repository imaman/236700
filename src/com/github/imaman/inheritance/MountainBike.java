package com.github.imaman.inheritance;

public class MountainBike extends Bicycle {

  private String frontShock;
  private String rearShock;

  
  public MountainBike(Record record) {
    super(record);
    this.frontShock = record.get("front_shock");
    this.rearShock = record.get("rear_shock");
  }

  public MountainBike(String json) {
    this(new Record(json));
  }

  @Override
  public Record spares() {
    return super.spares().merge(
        "chain: '10-speed', tire_size: '2.1', rear_shock: '" + rearShock + "'");      
  }
  
  public String getFrontShock() { 
    return frontShock;
  }
  
  // additional method are here..
  
}
