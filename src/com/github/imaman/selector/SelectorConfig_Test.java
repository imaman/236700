package com.github.imaman.selector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SelectorConfig_Test {

  @Test
  public void translatesInputIntoLabelIdToRevisionMap() {
    SelectorConfig config = new SelectorConfig();
    config.LoadFromString("{" +
        "generators: [" +
        "  { name: 'G_G', default_labels: [['A', 100]] }]}");

    assertEquals(100, config.defaultRevisionOf("G_G/A").intValue());
  }

  @Test
  public void aGeneratorCanHaveSeveralDefaultLabels() {
    SelectorConfig config = new SelectorConfig();
    config.LoadFromString("{" +
        "generators: [" +
        "  { name: 'G_G', default_labels: [['A', 100], ['B', 200]] }]}");

    assertEquals(100, config.defaultRevisionOf("G_G/A").intValue());
    assertEquals(200, config.defaultRevisionOf("G_G/B").intValue());
  }

  @Test
  public void canAccomodateMultipleGenerators() {
    SelectorConfig config = new SelectorConfig();
    config.LoadFromString("{" +
        "generators: [" +
        "  { name: 'First', default_labels: [['L1', 501]] }," +
        "  { name: 'Second', default_labels: [['L2', 502]] }" +
        "]}");

    assertEquals(501, config.defaultRevisionOf("First/L1").intValue());
    assertEquals(502, config.defaultRevisionOf("Second/L2").intValue());
  }
}
