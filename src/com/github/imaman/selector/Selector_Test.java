package com.github.imaman.selector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.imaman.selector.external.Response;

public class Selector_Test {

  private Response newResponse(String name, long age) {
    Response young = mock(Response.class, name);
    when(young.age()).thenReturn(age);
    return young;
  }
  
  @Test
  public void filtersNothingByDefault() {
    Selector selector = new Selector();
    List<Response> responses = new ArrayList<Response>();
    responses.add(newResponse("old", 900));
    responses.add(newResponse("young", 1));
    List<Response> selected = selector.select(responses);
    assertEquals(responses, selected);
  }

  @Test
  public void rejectsResponsesWhoseAgeExceedsTheThreshold() {
    Response young = newResponse("young", 30L);   
    Response older = newResponse("older", 40L);    
    Response younger = newResponse("younger", 25L);
    
    List<Response> responses = new ArrayList<Response>();
    responses.add(young);
    responses.add(older);
    responses.add(younger);
    
    Selector selector = new Selector();
    selector.setMaxAllowedAge(35);
    List<Response> selected = selector.select(responses);
    assertEquals(Arrays.asList(young, younger), selected);
  }
}
