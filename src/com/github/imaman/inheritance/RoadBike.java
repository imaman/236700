package com.github.imaman.inheritance;

import java.util.Map;

public class RoadBike extends Bicycle {

  private String tapeColor;

  private static CustomSpares TAPE_COLOR_SPARES = new CustomSpares() {      
    @Override public void populate(Record record, Map<String, String> output) {
      output.put("tape_color", record.get("tape_color"));
    }
  };
  
  private static TireSize ROAD_BIKE_TIRE_SIZE = new TireSize() {
    @Override public String calculate() { return "23"; }
  };
  
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
