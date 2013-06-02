package com.github.imaman.inheritance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MountainBike_Test {
  @Test
  public void providesListOfSpares() {
    MountainBike bike = new MountainBike("size: 'S', front_shock: 'Manitou', rear_shock: 'Fox'");
    assertEquals("S", bike.getSize());
    
    // This assertion fails! 
    // Actual value we get is: 
    // { chain: '10-speed', tire_size: '2.1', tape_color: null, rear_shock: 'Fox' }
    assertTrue("spares=" + bike.spares(), 
        bike.spares().isEquivalentTo("tire_size: '2.1', chain: '10-speed', rear_shock: 'Fox'"));
  }

}
