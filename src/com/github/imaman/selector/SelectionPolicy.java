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
        String reason = findReasonToDiscard(current, revisionByLabelName, "Requested");
        if (reason == null)
          selected.add(current);
        else if (!reason.isEmpty())
          tracker.discardedResponse(current, reason);
        else if (config != null) {
          reason = findReasonToDiscard(current, config.map(), "Default");
          if (reason == null)
            selected.add(current);
          else if (!reason.isEmpty())
            tracker.discardedResponse(current, reason);
          else
            tracker.discardedResponse(current, "Label [" + labelId(current.label()) + "] was not requested and is not the default version");
        } else
          tracker.discardedResponse(current, "Label [" + labelId(current.label()) + "] was not requested");

      }

      return selected;
    }

    private String labelId(Label label) {
      return label.generatorId + "/" + label.name;
    }

    private String findReasonToDiscard(Response current, Map<String, Integer> revisionByLabelName,
        String prefix) {
      Label label = current.label();
      if (label == null)
        return null;

      Integer requestedRevision = revisionByLabelName.get(labelId(label));
      if (requestedRevision != null) {
        if (Objects.equals(requestedRevision, label.revision))
          return null;
        return String.format("%s revision [%s] does not match the response's revision [%d]",
            prefix, requestedRevision, label.revision);
      }

      return "";
    }
  }
  ;

  public abstract List<Response> select(Request request, List<Response> responses, Tracker tracker,
      SelectorConfig config);

  public static SelectionPolicy lookup(String policyName) {
    return policyName == null ? SELECT_BY_LABEL : valueOf(policyName);
  }
}
