package com.github.imaman.inheritance;

public class RoadBike extends Bicycle {

  private String tapeColor;

  public RoadBike(Record record) {
    super(record);
    this.tapeColor = record.get("tape_color");
  }
  
  public RoadBike(String json) {
    this(new Record(json));
  }
  
  @Override
  public String customSpares() {
    return "tape_color: '" + tapeColor + "'";
  }
  
  @Override
  protected String tireSize() {
    return "23";
  }

  public String getTapeColor() {
    return tapeColor;
  }
  
  public boolean isTapeColor(String otherTapeColor) {
    return tapeColor.compareToIgnoreCase(otherTapeColor) == 0;
  }
}
