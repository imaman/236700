package com.github.imaman.inheritance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Bicycle_Test {

  @Test
  public void canBeInitializedFromJsonm() {
    Bicycle bike = new Bicycle("size: 'M', tape_color: 'red'");
    assertEquals("M", bike.getSize());
    assertEquals("red", bike.getTapeColor());
  }

  @Test
  public void canDetermineEqualityOfTapeColor() {
    Bicycle bike = new Bicycle("size: 'M', tape_color: 'yellow'");
    assertTrue(bike.isTapeColor("yellow"));
    assertFalse(bike.isTapeColor("red"));
  }
  @Test
  public void providesSpares() {
    Bicycle bike = new Bicycle("size: 'M', tape_color: 'red'");
    assertTrue(bike.spares().isEquivalentTo("tire_size: '23', chain: '10-speed', tape_color: 'red'"));
  }
  
  @Test
  public void canBeMountainBike() {
    Bicycle bike = new Bicycle("style: 'Mountain', size: 'S', front_shock: 'Manitou', rear_shock: 'Fox'");
    assertTrue("spares=" + bike.spares(), 
        bike.spares().isEquivalentTo("tire_size: '2.1', chain: '10-speed', rear_shock: 'Fox'"));
  }
}
