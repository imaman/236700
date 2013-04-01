package com.github.imaman.selector.external;

public interface Response {

  /**
   * When was the data (on which this response is based) last updated.
   * @return milliseconds since midnight, January 1, 1970 UTC.
   */
  public long lastUpdatedAt(); 
}
