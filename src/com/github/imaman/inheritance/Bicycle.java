package com.github.imaman.inheritance;

import java.util.Objects;

import com.google.gson.JsonObject;

public class Bicycle {

  private String size;
  private String tapeColor;
  private String style;
  private String frontShock;
  private String rearShock;
  
  public Bicycle(String json) {
    this(new Record(json));
  }

  public Bicycle(Record record) {
    this.size = record.get("size");
    this.tapeColor = record.get("tape_color");
    this.style = record.get("style");
    this.frontShock = record.get("front_shock");
    this.rearShock = record.get("rear_shock");
  }
    
  public Record spares() {
    JsonObject json = new JsonObject();
    if (!Objects.equals("Mountain", style)) {
      json.addProperty("chain", "10-speed");
      json.addProperty("tire_size", "23");
      json.addProperty("tape_color", tapeColor);
    } else {
      json.addProperty("chain", "10-speed");
      json.addProperty("tire_size", "2.1");
      json.addProperty("rear_shock", rearShock);      
    }
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
  
  public String getFrontShock() { 
    return frontShock;
  }
  
  // additional method are here..
}
