package fr.xebia.mowitnow;

import static fr.xebia.mowitnow.TestUtil.A;
import static fr.xebia.mowitnow.TestUtil.D;
import static fr.xebia.mowitnow.TestUtil.EST;
import static fr.xebia.mowitnow.TestUtil.G;
import static fr.xebia.mowitnow.TestUtil.NORD;
import static fr.xebia.mowitnow.TestUtil.SUD;
import static fr.xebia.mowitnow.TestUtil.WEST;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;

import fr.xebia.mowitnow.TondeuseTest.Data.DataBuilder;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.jardin.Cellule;
import fr.xebia.mowitnow.jardin.Pelouse;
import fr.xebia.mowitnow.tonte.Instruction;
import fr.xebia.mowitnow.tonte.Tondeuse;

@RunWith(JUnitParamsRunner.class)
public class TondeuseTest {

  @Test
  @Parameters
  public void demarrerTest(final Data data) {
    data.tondeuse.demarrer();
    data.pelouse.afficher();
    assertEquals(data.orientationAttendue, data.tondeuse.getOrientation());
    assertEquals(data.celluleAttendue, data.tondeuse.getCellule());
  }

  public Object[][] parametersForDemarrerTest() {
    return new Object[][] {
        {DataBuilder.pelouse(2, 2).tondeuse(0, 0, EST).attendu(0, 0, EST)},
        {DataBuilder.pelouse(1, 1).tondeuse(0, 0, EST).faire(D).attendu(0, 0, SUD)},
        {DataBuilder.pelouse(1, 1).tondeuse(0, 0, EST).faire(G).attendu(0, 0, NORD)},
        {DataBuilder.pelouse(2, 2).tondeuse(0, 0, WEST).faire(G, G, G, G).attendu(0, 0, WEST)},
        {DataBuilder.pelouse(5, 5).tondeuse(2, 2, WEST).faire(D, A, A, G, A, A, G, A, G, A)
            .attendu(1, 3, EST)},};
  }

  @lombok.Data
  static class Data {

    private Pelouse pelouse;
    private Tondeuse tondeuse;
    private Cellule celluleAttendue;
    private Orientation orientationAttendue;

    Cellule on(final int x, final int y) {
      return pelouse.cellule(x, y);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static class DataBuilder {

      final Data data = new Data();

      static DataBuilder pelouse(final int largeur, final int longueur) {
        DataBuilder builder = new DataBuilder();
        builder.data.pelouse = new Pelouse(largeur, longueur);
        return builder;
      }

      DataBuilder tondeuse(final int x, final int y, final Orientation o) {
        data.tondeuse = new Tondeuse(data.on(x, y), o);
        return this;
      }

      DataBuilder faire(final Instruction... instructions) {
        data.tondeuse.setInstructions(Lists.newLinkedList(Arrays.asList(instructions)));
        return this;
      }

      Data attendu(final int x, final int y, final Orientation o) {
        data.celluleAttendue = data.on(x, y);
        data.orientationAttendue = o;
        return data;
      }
    }
  }
}
