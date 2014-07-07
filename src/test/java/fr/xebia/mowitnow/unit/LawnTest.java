package fr.xebia.mowitnow.unit;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import fr.xebia.mowitnow.base.Cell;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;
import fr.xebia.mowitnow.mower.Lawn;
import static fr.xebia.mowitnow.base.Position.at;
import static fr.xebia.mowitnow.unit.Constant.EAST;
import static fr.xebia.mowitnow.unit.Constant.NORTH;
import static fr.xebia.mowitnow.unit.Constant.SOUTH;
import static fr.xebia.mowitnow.unit.Constant.WEST;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test de construcion d'une pelouse
 * 
 * @author ilyes
 *
 */
@RunWith(JUnitParamsRunner.class)
public class LawnTest {

  @Test
  @Parameters({"5,5", "3,6", "1,1"})
  public void lawnValid(final int width, final int height) {
    lawn(width, height);
  }

  @Test(expected = IllegalArgumentException.class)
  @Parameters({"0,0", "0,3", "2,0"})
  public void lawnInvalid(final int width, final int height) {
    lawn(width, height);
  }

  private void lawn(final int width, final int height) {
    // When
    Lawn lawn = new Lawn(width, height);

    // Then
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Cell cell = lawn.cellAt(x, y);
        assertEquals(Position.at(x, y), cell.getPosition());
        assertFalse(cell.isLock());
        assertFalse(cell.isMowed());
        assertNext(lawn, EAST, cell.getPosition(), at(x + 1, y));
        assertNext(lawn, WEST, cell.getPosition(), at(x - 1, y));
        assertNext(lawn, NORTH, cell.getPosition(), at(x, y + 1));
        assertNext(lawn, SOUTH, cell.getPosition(), at(x, y - 1));
      }
    }
  }

  private void assertNext(final Lawn lawn, final Orientation orientation, final Position position,
      final Position expectedPosition) {
    Cell cell = lawn.cellAt(position);
    Cell actual = cell.next(orientation);
    Cell expected = null;
    if (in(expectedPosition.getX(), 0, lawn.getWidth())
        && in(expectedPosition.getY(), 0, lawn.getHeight())) {
      expected = lawn.cellAt(expectedPosition);
    }
    assertEquals(expected, actual);
  }

  private boolean in(final int position, final int min, final int max) {
    return position >= min && position < max;
  }
}
