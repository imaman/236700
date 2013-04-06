package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.imaman.selector.external.Response;
import com.github.imaman.selector.external.Tracker;

public enum SelectionPolicy {

  SELECT_ALL {
    @Override
    public List<Response> select(Map<String, Integer> requestedRevisionByLabelId,
        List<Response> responses, Tracker tracker, Map<String, Integer> defaultRevisionByLabelId) {
      return responses;
    }
  },
  SELECT_BY_LABEL {
    @Override
    public List<Response> select(Map<String, Integer> requestedRevisionByLabelId,
        List<Response> responses, Tracker tracker, Map<String, Integer> defaultRevisionByLabelId) {

      List<Response> selected = new ArrayList<Response>();
      for (Response current : responses) {
        select(current, requestedRevisionByLabelId, defaultRevisionByLabelId, selected, tracker);
      }
      return selected;
    }

    private void select(Response response, Map<String, Integer> revisionByLabelId,
        Map<String, Integer> defaultRevisionByLabelId, List<Response> selected,
        Tracker tracker) {
      if (addOrDiscard(response, revisionByLabelId, "Requested", selected, tracker))
        return;

      if (defaultRevisionByLabelId == null) {
        tracker.discardedResponse(response, "Label [" + response.label().id()
            + "] was not requested");
        return;
      }

      if (addOrDiscard(response, defaultRevisionByLabelId, "Default", selected, tracker))
        return;

      tracker.discardedResponse(response, "Label [" + response.label().id()
          + "] was not requested and is not the default");
    }

    private boolean addOrDiscard(Response response, Map<String, Integer> revisionByLabelId,
        String prefix, List<Response> selected, Tracker tracker) {
      Label label = response.label();
      if (label == null) {
        selected.add(response);
        return true;
      }

      Integer requestedRevision = revisionByLabelId.get(label.id());
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
      List<Response> responses, Tracker tracker, Map<String, Integer> defaultRevisionByLabelId);

  public static SelectionPolicy lookup(String policyName) {
    return policyName == null ? SELECT_BY_LABEL : valueOf(policyName);
  }
}
