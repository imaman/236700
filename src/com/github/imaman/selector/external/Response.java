package com.github.imaman.selector.external;

public interface Response {

  /**
   * How old is the data on which this response is based.
   * @return minutes. 0 means "now", 10 means "10 minutes ago".
   */
  public long age(); 
}
