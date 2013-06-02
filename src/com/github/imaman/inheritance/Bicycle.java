package com.github.imaman.inheritance;


public abstract class Bicycle {

  private String size;
  
  public Bicycle(String json) {
    this(new Record(json));
  }

  public Bicycle(Record record) {
    this.size = record.get("size");
  }
    
  public abstract Record spares();
  
  public String getSize() {
    return size;
  }
  
  
  // additional method are here..
}
