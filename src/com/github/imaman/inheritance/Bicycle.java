package com.github.imaman.inheritance;


public abstract class Bicycle {

  private String size;
  
  public Bicycle(String json) {
    this(new Record(json));
  }

  public Bicycle(Record record) {
    this.size = record.get("size");
  }
  
  public Record spares() {
    // Let's make it a template method! x2
    return new Record("chain: '10-speed', tire_size: '" + tireSize() + "', " + customSpares());      
  }

  protected abstract String customSpares();
  protected abstract String tireSize();

  public String getSize() {
    return size;
  }
  
  
  // additional method are here..
}
