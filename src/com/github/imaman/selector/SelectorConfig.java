package com.github.imaman.selector;

import java.util.HashMap;
import java.util.Map;

import com.github.imaman.selector.external.Config;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SelectorConfig extends Config {

  private JsonObject data;

  public void LoadFromString(String json) {
    JsonParser jp = new JsonParser();
    this.data = (JsonObject) jp.parse(json);
  }

  public Map<String, Integer> map() {
    Map<String, Integer> result = new HashMap<>();
    result.put("G_G/A", 1);
    return result;
  }

  @Override
  public String toString() {
    return data.toString();
  }
}
