package com.github.imaman.inheritance;



public abstract class Bicycle {

  private String size;
  private TireSize tireSize;
  
  public interface TireSize {
    public String calculate();
  }
  
  public Bicycle(String json, TireSize tireSize) {
    this(new Record(json), tireSize);
  }

  public Bicycle(Record record, TireSize tireSize) {
    this.size = record.get("size");
    this.tireSize = tireSize;
  }
  
  public Record spares() {
    // Delegate tire-size-calculation to another object.
    return new Record("chain: '10-speed', tire_size: '" + tireSize.calculate() + "', " 
        + customSpares());      
  }

  protected abstract String customSpares();
  
  public String getSize() {
    return size;
  }
  
  
  // additional method are here..
}
