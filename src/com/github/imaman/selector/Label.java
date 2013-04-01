package com.github.imaman.selector;

import java.util.Objects;

public class Label {

  public final String primaryName;
  public final String secondaryName;
  public final int revision;
  
  public Label(String primaryName, String secndaryName, int revision) {
    this.primaryName = primaryName;
    this.secondaryName = secndaryName;
    this.revision = revision;
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryName, secondaryName, revision);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!getClass().equals(obj.getClass()))
      return false;
    Label that = (Label) obj;
    return Objects.equals(primaryName, that.primaryName)
        && Objects.equals(secondaryName, that.secondaryName)
        && Objects.equals(revision, that.revision);
  }

  @Override
  public String toString() {
    return "Label [" + primaryName + ", " + secondaryName + ", " + revision + "]";
  }
  
  
  
  
  
  
  
}
