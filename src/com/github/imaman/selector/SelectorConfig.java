package com.github.imaman.selector;

import java.util.HashMap;
import java.util.Map;

import com.github.imaman.selector.external.Config;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SelectorConfig extends Config {

  private JsonObject data;
  private Map<String, Integer> defaultRevisionByLabel;

  public void LoadFromString(String json) {
    JsonParser jp = new JsonParser();
    this.data = (JsonObject) jp.parse(json);
    this.defaultRevisionByLabel = computeDefaultRevisionMap();
  }

  public Map<String, Integer> defaultRevisionByLabel() {
    return defaultRevisionByLabel;
  }

  private Map<String, Integer> computeDefaultRevisionMap() {
    Map<String, Integer> result = new HashMap<>();
    JsonArray array = data.getAsJsonArray("generators");
    for (JsonElement current : array) {
      JsonObject obj = current.getAsJsonObject();
      String name = obj.get("name").getAsString();
      JsonArray labels = obj.getAsJsonArray("default_labels");
      for (JsonElement label : labels) {
        String labelName = label.getAsJsonArray().get(0).getAsString();
        int revision = label.getAsJsonArray().get(1).getAsInt();

        result.put(name + "/" + labelName, revision); // <-- DRY violation w.r.t Label.id()
      }
    }
    return result;
  }

  @Override
  public String toString() {
    return data.toString();
  }
}
