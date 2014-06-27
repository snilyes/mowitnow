package fr.xebia.mowitnow;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.xebia.mowitnow.base.Orientation;

@RunWith(JUnitParamsRunner.class)
public class OrientationTest {

  @Test
  @Parameters({"EST, SUD", "SUD, WEST", "WEST, NORD", "NORD, EST"})
  public void aDroiteTest(final Orientation initiale, final Orientation finale) {
    Assert.assertEquals(initiale.aDroite(), finale);
  }

  @Test
  @Parameters({"EST, NORD", "NORD, WEST", "WEST, SUD", "SUD, EST"})
  public void aGaucheTest(final Orientation initiale, final Orientation finale) {
    Assert.assertEquals(initiale.aGauche(), finale);
  }
}
