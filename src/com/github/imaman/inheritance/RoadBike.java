package com.github.imaman.inheritance;

public class RoadBike extends Bicycle {

  private String tapeColor;

  public RoadBike(Record record) {
    super(record, new TireSize() {
      @Override public String calculate() { return "23"; }
    });
    this.tapeColor = record.get("tape_color");
  }
  
  public RoadBike(String json) {
    this(new Record(json));
  }
  
  @Override
  public String customSpares() {
    return "tape_color: '" + tapeColor + "'";
  }
  
  public String getTapeColor() {
    return tapeColor;
  }
  
  public boolean isTapeColor(String otherTapeColor) {
    return tapeColor.compareToIgnoreCase(otherTapeColor) == 0;
  }
}
