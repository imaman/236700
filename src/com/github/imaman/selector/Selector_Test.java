package com.github.imaman.selector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.github.imaman.selector.external.Response;
import com.github.imaman.selector.external.Tracker;

public class Selector_Test {

  private final Tracker tracker = mock(Tracker.class);

  private Response newResponse(String name, long age) {
    Response response = mock(Response.class, name);
    when(response.lastUpdatedAt()).thenReturn(age);
    return response;
  }
  
  @Test
  public void filtersNothingByDefault() {
    Selector selector = new Selector();
    List<Response> responses = new ArrayList<Response>();
    responses.add(newResponse("old", 900));
    responses.add(newResponse("older", 1));
    List<Response> selected = selector.select(new Request(1500L, ""), responses, tracker);
    assertEquals(responses, selected);
  }

  @Test
  public void rejectsResponsesWhoseAgeExceedsTheThreshold() {
    Response young = newResponse("young", 70);   
    Response tooOld = newResponse("too old", 60);    
    Response younger = newResponse("younger", 75);
    
    List<Response> responses = new ArrayList<Response>();
    responses.add(young);
    responses.add(tooOld);
    responses.add(younger);
    
    Selector selector = new Selector();
    selector.setMaxAllowedAge(35);
    List<Response> selected = selector.select(new Request(100L,  ""), responses, tracker);
    assertEquals(Arrays.asList(young, younger), selected);
    Mockito.verify(tracker).discardedResponse(tooOld, "Dropped due to age [40]");
  }
}
