package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.imaman.selector.external.Response;
import com.github.imaman.selector.external.Tracker;

public enum SelectionPolicy {

  SELECT_ALL {
    @Override
    public List<Response> select(Map<String, Integer> requestedRevisionByLabelId,
        List<Response> responses, Tracker tracker, SelectorConfig config) {
      return responses;
    }
  },
  SELECT_BY_LABEL {
    @Override
    public List<Response> select(Map<String, Integer> requestedRevisionByLabelId,
        List<Response> responses, Tracker tracker, SelectorConfig config) {

      List<Response> selected = new ArrayList<Response>();
      for (Response current : responses) {
        select(current, requestedRevisionByLabelId, config, selected, tracker);
      }
      return selected;
    }

    private void select(Response response, Map<String, Integer> revisionByLabelId,
        SelectorConfig config, List<Response> selected, Tracker tracker) {
      if (response.label() == null) {
        selected.add(response);
        return;
      }

      if (addOrDiscard(response, revisionByLabelId, "Requested", selected, tracker))
        return;

      if (config == null) {
        tracker.discardedResponse(response, "Label [" + response.label().id()
            + "] was not requested");
        return;
      }

      if (addOrDiscard(response, config, "Default", selected, tracker))
        return;

      tracker.discardedResponse(response, "Label [" + response.label().id()
          + "] was not requested and is not the default");
    }

    private boolean addOrDiscard(Response response, Map<String, Integer> revisionByLabelId,
        String prefix, List<Response> selected, Tracker tracker) {
      for (Label label : labelsToCheck(response)) {
        Integer requestedRevision = revisionByLabelId.get(label.id());
        if (check(prefix, label, response, requestedRevision, selected, tracker))
          return true;
      }

      return false;
    }

    private boolean addOrDiscard(Response response, SelectorConfig config,
        String prefix, List<Response> selected, Tracker tracker) {
      for (Label label : labelsToCheck(response)) {
        Integer requestedRevision = config.defaultRevisionOf(label);
        if (check(prefix, label, response, requestedRevision, selected, tracker))
          return true;
      }

      return false;
    }

    private List<Label> labelsToCheck(Response response) {
      Label originalLabel = response.label();
      Label wildcardLabel = new Label(originalLabel.generatorId, "*", originalLabel.revision);      
      return Arrays.asList(originalLabel, wildcardLabel);
    }
    
    private boolean check(String prefix, Label label, Response response, 
        Integer requestedRevision, List<Response> selected, Tracker tracker) {
      if (requestedRevision != null) {
        if (Objects.equals(requestedRevision, label.revision)) {
          selected.add(response);
          return true;
        }
        String reason = String.format("%s revision [%s] does not match the response's revision [%d]",
            prefix, requestedRevision, label.revision);
        tracker.discardedResponse(response, reason);
        return true;
      }   
      
      return false;
    }
  }
  ;

  public abstract List<Response> select(Map<String, Integer> requestedRevisionByLabelId,
      List<Response> responses, Tracker tracker, SelectorConfig config);

  public static SelectionPolicy lookup(String policyName) {
    return policyName == null ? SELECT_BY_LABEL : valueOf(policyName);
  }
}
