package com.github.imaman.selector.external;

public interface Tracker {

  public void discardedResponse(Response response, String reason);
}
