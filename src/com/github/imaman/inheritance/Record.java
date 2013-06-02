package com.github.imaman.inheritance;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Record {

  private JsonObject data;
  
  public Record(String json) {
    this(jsonFromString(json));
  }

  private static JsonObject jsonFromString(String json) {
    return (JsonObject) new JsonParser().parse("{ " + json + " }");
  }

  public Record(JsonObject data) {
    this.data = data;
  }
  
  public String get(String key) {
    JsonElement jsonElement = data.get(key);
    return jsonElement == null ? null : jsonElement.getAsString();
  }
  
  public boolean isEquivalentTo(String json) {
    return data.equals(jsonFromString(json));
  }
  
  public Record merge(String json) {
    for (Entry<String, JsonElement>  entry : jsonFromString(json).entrySet()) {
      if (!data.has(entry.getKey()))
        data.add(entry.getKey(), entry.getValue());
    }
    return this;
  }
  
  @Override
  public String toString() {
    return data.toString();
  }
}
