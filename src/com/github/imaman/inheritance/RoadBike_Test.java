package com.github.imaman.inheritance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RoadBike_Test {

  @Test
  public void canBeInitializedFromJsonm() {
    RoadBike bike = new RoadBike("size: 'M', tape_color: 'red'");
    assertEquals("M", bike.getSize());
    assertEquals("red", bike.getTapeColor());
  }

  @Test
  public void canDetermineEqualityOfTapeColor() {
    RoadBike bike = new RoadBike("size: 'M', tape_color: 'yellow'");
    assertTrue(bike.isTapeColor("yellow"));
    assertFalse(bike.isTapeColor("red"));
  }
  @Test
  public void providesSpares() {
    RoadBike bike = new RoadBike("size: 'M', tape_color: 'red'");
    assertTrue("bike.spares=" + bike.spares(), 
        bike.spares().isEquivalentTo("tire_size: '23', chain: '10-speed', tape_color: 'red'"));
  }
}
