package com.github.imaman.inheritance;

import java.util.Map;

public class RoadBike extends Bicycle {

  private String tapeColor;

  public RoadBike(final Record record) {
    super(record, new TireSize() {
      @Override public String calculate() { return "23"; }
    }, new CustomSpares() {      
      @Override public void populate(Record record, Map<String, String> output) {
        output.put("tape_color", record.get("tape_color"));
      }
    });
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
