package fr.xebia.mowitnow.unit;

import java.util.Queue;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.io.parseur.InstructionParser;
import fr.xebia.mowitnow.io.parseur.LawnParser;
import fr.xebia.mowitnow.io.parseur.MowerParser;
import fr.xebia.mowitnow.mower.Instruction;
import fr.xebia.mowitnow.mower.Lawn;
import fr.xebia.mowitnow.mower.Mower;
import static fr.xebia.mowitnow.unit.Constant.A;
import static fr.xebia.mowitnow.unit.Constant.D;
import static fr.xebia.mowitnow.unit.Constant.G;

import static org.junit.Assert.assertEquals;

/**
 * Test de parseurs
 * 
 * @author ilyes
 *
 */
@RunWith(JUnitParamsRunner.class)
public class ParserTest {

  @Test
  @Parameters(value = {"5 5, 5, 5", "1 1, 1, 1", "3 6, 3, 6"})
  public void lawnParserTest(final String line, final int width, final int height) {
    LawnParser parser = new LawnParser();
    Lawn actual = parser.parse(line);
    Lawn expected = new Lawn(width, height);
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters(value = {"", "b l", "1", " 1 2", "1 2 ", "1 2 3"})
  public void lawnParserKoTest(final String line) {
    new LawnParser().parse(line);
  }

  @Test
  @Parameters(value = {"2 1 W, 2, 1, WEST", "1 3 N, 1, 3, NORTH", "2 2 S, 2, 2, SOUTH",
      "1 2 E, 1, 2, EAST"})
  public void mowerParserTest(final String line, final int x, final int y,
      final Orientation orientation) {
    Lawn lawn = new Lawn(4, 4);
    MowerParser parser = new MowerParser(lawn);
    Mower actual = parser.parse(line);
    Mower expected = new Mower(lawn.cellAt(x - 1, y - 1), orientation);
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters(value = {"5 5 W", "2 2 B", "", "bla", "1 2 S 2"})
  public void mowerParserKoTest(final String line) {
    new MowerParser(new Lawn(4, 4)).parse(line);
  }


  @Test
  @Parameters(method = "instructionParserParam")
  public void instructionsParserTest(final String line, final Instruction[] expected) {
    InstructionParser parser = new InstructionParser();
    Queue<Instruction> actual = parser.parse(line);
    Assert.assertArrayEquals(expected, actual.toArray());
  }

  public Object[] instructionParserParam() {
    return new Object[] {"ADGAD", new Instruction[] {A, D, G, A, D}};
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters(value = {" A", "DD ", "AZA"})
  public void instructionsParserKoTest(final String line) {
    new InstructionParser().parse(line);
  }

}
