package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.List;

import com.github.imaman.selector.external.Response;
import com.github.imaman.selector.external.Tracker;

public class Selector {

  static final String SELECTION_POLICY_KEY = "selection-policy";
  private int maxAllowedAge = -1;
  private final SelectorConfig config;

  public Selector() {
    this(null);
  }

  public Selector(SelectorConfig config) {
    this.config = config;
  }

  public List<Response> select(Request request, List<Response> responses, Tracker tracker) {
    List<Response> selected = new ArrayList<Response>();
    for (Response current : responses) {
      long age = request.time() - current.lastUpdatedAt();
      if (maxAllowedAge < 0 || age <= maxAllowedAge)
        selected.add(current);
      else
        tracker.discardedResponse(current, "Dropped due to age [" + age + "]");
    }

    SelectionPolicy policy = SelectionPolicy.lookup(request.valueOf(SELECTION_POLICY_KEY));
    return policy.select(request, selected, tracker,
        config == null ? null : config.defaultRevisionByLabel());
  }

  /**
   * Sets the upper limit on the age of a selected response.
   */
  public void setMaxAllowedAge(int ageInMilliseconds) {
    this.maxAllowedAge = ageInMilliseconds;
  }
}
