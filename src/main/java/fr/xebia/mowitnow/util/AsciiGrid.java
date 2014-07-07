package fr.xebia.mowitnow.util;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang.text.StrBuilder;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

import fr.xebia.mowitnow.base.Cell;
import fr.xebia.mowitnow.mower.Lawn;
import fr.xebia.mowitnow.mower.Mower;

/**
 * Classe utilitaire permettant de dessiner une grille representant une pelouse contenant des
 * tondeuses orientées
 * 
 * @author ilyes
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AsciiGrid {


  public static String draw(final Lawn lawn, final Mower... mowers) {
    int width = lawn.getWidth(), height = lawn.getHeight();
    boolean withMower = mowers.length > 0;
    GridBuilder builder = new GridBuilder();
    builder.append("\n\n");
    for (int y = height - 1; y >= 0; y--) {
      builder.cell(y);
      for (int x = 0; x < width; x++) {
        Cell cell = lawn.cellAt(x, y);

        if (cell.isLock()) {
          builder.cell(withMower ? orientation(cell, mowers) : 'x');
        } else if (cell.isMowed()) {
          builder.cell('-');
        } else {
          builder.cell(' ');
        }
      }
      builder.line(width);
    }

    builder.cell();
    for (int i = 0; i < width; i++) {
      builder.cell(i);
    }
    builder.append("\n\n");
    return builder.toString();
  }

  private static char orientation(final Cell cell, final Mower... mowers) {
    return Iterables.find(Arrays.asList(mowers), new Predicate<Mower>() {
      @Override
      public boolean apply(final Mower input) {
        return input.getCell().equals(cell);
      }
    }).getOrientation().getCode().charAt(0);
  }

  static class GridBuilder extends StrBuilder {

    private static final String SEPARATOR_VER = "|";
    private static final String SEPARATOR_HOR = "-";

    public GridBuilder() {
      super();
    }

    GridBuilder line(final int width) {
      return (GridBuilder) this.append('\n').append(Strings.repeat(SEPARATOR_HOR, width * 5))
          .append('\n');
    }

    GridBuilder cell(final int item) {
      return (GridBuilder) this.append(' ').append(item).append(' ').append(SEPARATOR_VER);
    }

    GridBuilder cell(final char item) {
      return (GridBuilder) this.append(' ').append(item).append(' ').append(SEPARATOR_VER);
    }


    GridBuilder cell() {
      return (GridBuilder) this.append(' ').append(' ').append(' ').append(SEPARATOR_VER);
    }
  }
}
