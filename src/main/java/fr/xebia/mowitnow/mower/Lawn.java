package fr.xebia.mowitnow.mower;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import fr.xebia.mowitnow.base.Cell;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Reprsente une pelouse
 * 
 * @author ilyes
 *
 */
@EqualsAndHashCode(exclude = "grid")
@ToString(exclude = "grid")
public class Lawn {

  /**
   * La grille representant la surface de la peouse
   */
  private final Cell[][] grid;

  /**
   * La largeur de la pelouse
   */
  @Getter
  private final int width;

  /**
   * La longueur de la pelouse
   */
  @Getter
  private final int height;

  public Lawn(final int width, final int height) {
    checkArgument(width > 0, " La largeur doit être > 0 ");
    checkArgument(height > 0, " La longueur doit être > 0");

    this.width = width;
    this.height = height;
    grid = new Cell[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        grid[x][y] = new Cell(Position.at(x, y));
      }
    }

    for (int y = 0; y < height; y++) {
      setNext(0, y, Orientation.EAST);
      setNext(width - 1, y, Orientation.WEST);
    }

    for (int x = 0; x < width; x++) {
      setNext(x, 0, Orientation.NORTH);
      setNext(x, height - 1, Orientation.SOUTH);
    }
  }

  private void setNext(final int x, final int y, final Orientation orientation) {
    int nextX = x + orientation.getX();
    int nextY = y + orientation.getY();
    if (in(nextX, nextY)) {
      grid[x][y].next(orientation, grid[nextX][nextY]);
      setNext(nextX, nextY, orientation);
    }
  }

  private boolean in(final int x, final int y) {
    return (x < width && x >= 0) && (y < height && y >= 0);
  }

  public Cell cellAt(final int x, final int y) {
    checkArgument(in(x, y), "la position (" + (x + 1) + ',' + (y + 1)
        + ") est en dehors de la pelouse");
    return grid[x][y];
  }

  public Cell cellAt(@NonNull final Position position) {
    return grid[position.getX()][position.getY()];
  }
}
