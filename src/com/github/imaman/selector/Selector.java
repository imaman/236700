package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.List;

import com.github.imaman.selector.external.Response;

public class Selector {

  private int maxAllowedAge = -1;

  public List<Response> select(Request request, List<Response> responses) {
    List<Response> selected = new ArrayList<Response>();
    for (Response response : responses) {
      long age = request.time() - response.lastUpdatedAt();
      if (maxAllowedAge < 0 || age <= maxAllowedAge) 
        selected.add(response);
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
