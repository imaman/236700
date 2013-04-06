package com.github.imaman.selector;

import com.github.imaman.selector.external.Config;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SelectorConfig extends Config {

  private JsonObject data;

  public void LoadFromString(String json) {
    JsonParser jp = new JsonParser();
    this.data = (JsonObject) jp.parse(json);
  }

  @Override
  public String toString() {
    return data.toString();
  }
}
