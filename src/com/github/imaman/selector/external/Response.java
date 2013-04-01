package com.github.imaman.selector.external;

import com.github.imaman.selector.Label;

/**
 * Encapsulates the result of a single calculation carried out by a single Generator. 
 */
public interface Response {

  /**
   * When was the data (on which this response is based) last updated.
   * @return milliseconds since midnight, January 1, 1970 UTC.
   */
  public long lastUpdatedAt(); 
  
  /**
   * The {@link Label} best describing the payload.
   * @return a {@link Label} object 
   */
  public Label label();
  
  public enum PayloadFormat {
    JSON,
    XML
    ;
  }
  
  /**
   * Obtains the data held by this response object in the specified format.
   * @param format determines the format of the return value.
   * @return the paylod, formatted.
   */
  public String payload(PayloadFormat format);
}
