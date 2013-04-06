package com.github.imaman.selector;

import java.util.Objects;

public class Label {

  public final String generatorId;
  public final String name;
  public final int revision;

  public Label(String generatorId, String primaryName, int revision) {
    this.generatorId = generatorId;
    this.name = primaryName;
    this.revision = revision;
  }

  @Override
  public int hashCode() {
    return Objects.hash(generatorId, name, revision);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!getClass().equals(obj.getClass()))
      return false;
    Label that = (Label) obj;
    return Objects.equals(generatorId, that.generatorId)
        && Objects.equals(name, that.name)
        && Objects.equals(revision, that.revision);
  }

  @Override
  public String toString() {
    return "Label [" + generatorId + ", " + name + ", " + revision + "]";
  }

  public String id() {
    return generatorId + "/" + name;
  }
}
