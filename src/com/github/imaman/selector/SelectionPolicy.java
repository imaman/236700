package com.github.imaman.selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.imaman.selector.external.Response;
import com.github.imaman.selector.external.Tracker;

public enum SelectionPolicy {

  SELECT_ALL {
    @Override
    public List<Response> select(Request request, List<Response> responses,
        Tracker tracker) {
      return responses;
    }
  },
  SELECT_BY_LABEL {
    public List<Response> select(Request request, List<Response> responses, Tracker tracker) {
      Map<String, Integer> revisionByLabelName = new HashMap<String, Integer>();
      for (int i = 0; i < request.numLabels(); ++i)
        revisionByLabelName.put(labelId(request.label(i)),  request.label(i).revision);

      List<Response> selected = new ArrayList<Response>();
      for (Response current : responses) {
        String reason = findReasonToDiscard(current, revisionByLabelName);
        if (reason == null)
          selected.add(current);
        else
          tracker.discardedResponse(current, reason);
      }

      return selected;
    }

    private String labelId(Label label) {
      return label.generatorId + "/" + label.name;
    }

    private String findReasonToDiscard(Response current, Map<String, Integer> revisionByLabelName) {
      Label label = current.label();
      if (label == null)
        return null;

      Integer requestedRevision = revisionByLabelName.get(labelId(label));
      if (requestedRevision != null) {
        if (Objects.equals(requestedRevision, label.revision))
          return null;
        return String.format("Requested revision [%s] does not match the response's revision [%d]",
            requestedRevision, label.revision);
      }

      return "Label [" + labelId(label) + "] was not requested";
    }
  }
  ;
  
  public abstract List<Response> select(Request request, List<Response> responses, Tracker tracker);

  public static SelectionPolicy lookup(String policyName) {
    return policyName == null ? SELECT_BY_LABEL : valueOf(policyName);
  }
}
