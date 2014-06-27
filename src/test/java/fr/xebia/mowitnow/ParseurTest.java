package fr.xebia.mowitnow;

import static fr.xebia.mowitnow.TestUtil.A;
import static fr.xebia.mowitnow.TestUtil.D;
import static fr.xebia.mowitnow.TestUtil.G;
import static org.junit.Assert.assertEquals;

import java.util.Queue;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.io.parseur.InstructionParseur;
import fr.xebia.mowitnow.io.parseur.PelouseParseur;
import fr.xebia.mowitnow.io.parseur.TondeuseParseur;
import fr.xebia.mowitnow.tonte.Instruction;
import fr.xebia.mowitnow.tonte.Pelouse;
import fr.xebia.mowitnow.tonte.Tondeuse;

@RunWith(JUnitParamsRunner.class)
public class ParseurTest {

  @Test
  @Parameters(value = {"5 5, 5, 5", "1 1, 1, 1", "3 6, 3, 6"})
  public void pelouseParseurTest(final String line, final int largeur, final int longueur) {
    PelouseParseur parseur = new PelouseParseur();
    Pelouse actuelle = parseur.parse(line);
    Pelouse attendue = new Pelouse(largeur, longueur);
    assertEquals(attendue, actuelle);
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters(value = {"", "b l", "1", " 1 2", "1 2 ", "1 2 3"})
  public void pelouseParseurKoTest(final String line) {
    new PelouseParseur().parse(line);
  }

  @Test
  @Parameters(value = {"2 1 W, 2, 1, WEST", "1 3 N, 1, 3, NORD", "2 2 S, 2, 2, SUD",
      "1 2 E, 1, 2, EST"})
  public void tondeuseParseurTest(final String line, final int x, final int y,
      final Orientation orientation) {
    Pelouse pelouse = new Pelouse(4, 4);
    TondeuseParseur parseur = new TondeuseParseur(pelouse);
    Tondeuse actuelle = parseur.parse(line);
    Tondeuse attendue = new Tondeuse(pelouse.cellule(x, y), orientation);
    assertEquals(attendue, actuelle);
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters(value = {"4 4 W", "2 2 B", "", "bla", "1 2 S 2"})
  public void tondeuseParseurKoTest(final String line) {
    new TondeuseParseur(new Pelouse(4, 4)).parse(line);
  }


  @Test
  @Parameters(method = "instructionParserParam")
  public void instructionsParseurTest(final String line, final Instruction[] attendue) {
    InstructionParseur parseur = new InstructionParseur();
    Queue<Instruction> actuelle = parseur.parse(line);
    Assert.assertArrayEquals(attendue, actuelle.toArray());
  }

  public Object[] instructionParserParam() {
    return new Object[] {"ADGAD", new Instruction[] {A, D, G, A, D}};
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters(value = {" A", "DD ", "AZA"})
  public void instructionsParseurKoTest(final String line) {
    new InstructionParseur().parse(line);
  }

}
