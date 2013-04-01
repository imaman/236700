package com.github.imaman.selector.external;

import java.util.ArrayList;
import java.util.List;

import com.github.imaman.selector.Request;
import com.github.imaman.selector.Selector;

public class TopLevel {

  public final List<Generator> generators = new ArrayList<Generator>();
  private final Selector selector;
  
  public TopLevel(Selector selector) {
    this.selector = selector;
  }
  
  public List<Response> process(Request request) {
    List<Response> responses = new ArrayList<Response>();
    
    for (Generator current : generators) {
      Response response = current.process(request);
      if (response != null)
        responses.add(response);
    }
    
    return selector.select(request, responses);
  }
}
