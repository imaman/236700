package com.github.imaman.inheritance;

import com.google.gson.JsonObject;

public class Bicycle {

  private String size;
  private String tapeColor;
  
  public Bicycle(String json) {
    this(new Record(json));
  }

  public Bicycle(Record record) {
    this.size = record.get("size");
    this.tapeColor = record.get("tape_color");
  }
    
  public Record spares() {
    JsonObject json = new JsonObject();
    json.addProperty("chain", "10-speed");
    json.addProperty("tire_size", "23");
    json.addProperty("tape_color", tapeColor);
    return new Record(json);
  }
  
  public String getSize() {
    return size;
  }
  
  public String getTapeColor() {
    return tapeColor;
  }
  
  public boolean isTapeColor(String otherTapeColor) {
    return tapeColor.compareToIgnoreCase(otherTapeColor) == 0;
  }
  
  // additional method are here..
}
