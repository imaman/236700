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
        Tracker tracker, SelectorConfig config) {
      return responses;
    }
  },
  SELECT_BY_LABEL {
    @Override
    public List<Response> select(Request request, List<Response> responses, Tracker tracker,
        SelectorConfig config) {
      Map<String, Integer> revisionByLabelName = new HashMap<String, Integer>();
      for (int i = 0; i < request.numLabels(); ++i)
        revisionByLabelName.put(labelId(request.label(i)),  request.label(i).revision);

      List<Response> selected = new ArrayList<Response>();
      for (Response current : responses) {
        boolean decided = findReasonToDiscard(current, revisionByLabelName, "Requested", selected, tracker);;
        if (decided)
          continue;

        if (config == null) {
          tracker.discardedResponse(current, "Label [" + labelId(current.label()) + "] was not requested");
          continue;
        }

        decided = findReasonToDiscard(current, config.map(), "Default", selected, tracker);
        if (decided)
          continue;

        tracker.discardedResponse(current, "Label [" + labelId(current.label()) + "] was not requested and is not the default");
      }

      return selected;
    }

    private String labelId(Label label) {
      return label.generatorId + "/" + label.name;
    }

    private boolean findReasonToDiscard(Response current, Map<String, Integer> revisionByLabelName,
        String prefix, List<Response> selected, Tracker tracker) {
      Label label = current.label();
      if (label == null) {
        selected.add(current);
        return true;
      }

      Integer requestedRevision = revisionByLabelName.get(labelId(label));
      if (requestedRevision != null) {
        if (Objects.equals(requestedRevision, label.revision)) {
          selected.add(current);
          return true;
        }
        String reason = String.format("%s revision [%s] does not match the response's revision [%d]",
            prefix, requestedRevision, label.revision);
        tracker.discardedResponse(current, reason);
        return true;
      }

      return false;
    }
  }
  ;

  public abstract List<Response> select(Request request, List<Response> responses, Tracker tracker,
      SelectorConfig config);

  public static SelectionPolicy lookup(String policyName) {
    return policyName == null ? SELECT_BY_LABEL : valueOf(policyName);
  }
}
