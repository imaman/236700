package com.github.imaman.inheritance;


public class RoadBike extends Bicycle {

  private String tapeColor;

  public RoadBike(final Record record) {
    super(record, ROAD_BIKE_TIRE_SIZE, TAPE_COLOR_SPARES);
    this.tapeColor = record.get("tape_color");
  }

  public RoadBike(String json) {
    this(new Record(json));
  }
  
  public String getTapeColor() {
    return tapeColor;
  }
  
  public boolean isTapeColor(String otherTapeColor) {
    return tapeColor.compareToIgnoreCase(otherTapeColor) == 0;
  }
}
