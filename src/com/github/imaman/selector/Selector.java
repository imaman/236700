package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.imaman.selector.external.Response;

public class Selector {

  private int maxAllowedAge = -1;

  public List<Response> select(Request request, List<Response> responses) {
    List<Response> selected = new ArrayList<Response>();
    for (Response current : responses) {
      long age = request.time() - current.lastUpdatedAt();
      if (maxAllowedAge < 0 || age <= maxAllowedAge) 
        selected.add(current);
    }
    
    return selected;
  }

  /**
   * Sets the upper limit on the age of a selected response.
   */
  public void setMaxAllowedAge(int ageInMilliseconds) {
    this.maxAllowedAge = ageInMilliseconds;
  }
}
