package fr.xebia.mowitnow.io;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang.text.StrBuilder;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

import fr.xebia.mowitnow.base.Cellule;
import fr.xebia.mowitnow.tonte.Pelouse;
import fr.xebia.mowitnow.tonte.Tondeuse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AsciiGrid {


  public static String dessiner(final Pelouse pelouse, final Tondeuse... tondeuses) {
    int largeur = pelouse.getLargeur(), longeur = pelouse.getLongueur();
    boolean withTondeuse = tondeuses.length > 0;
    GridBuilder builder = new GridBuilder();
    builder.append("\n\n");
    for (int y = longeur - 1; y >= 0; y--) {
      builder.cell(y);
      for (int x = 0; x < largeur; x++) {
        Cellule cell = pelouse.cellule(x, y);

        if (cell.isOccupe()) {
          builder.cell(withTondeuse ? orientation(cell, tondeuses) : 'x');
        } else if (cell.isTondu()) {
          builder.cell('-');
        } else {
          builder.cell(' ');
        }
      }
      builder.line(largeur);
    }

    builder.cell();
    for (int i = 0; i < largeur; i++) {
      builder.cell(i);
    }
    builder.append("\n\n");
    return builder.toString();
  }

  private static char orientation(final Cellule cell, final Tondeuse... tondeuses) {
    return Iterables.find(Arrays.asList(tondeuses), new Predicate<Tondeuse>() {
      @Override
      public boolean apply(Tondeuse input) {
        return input.getCellule().equals(cell);
      }
    }).getOrientation().getCode().charAt(0);
  }

  static class GridBuilder extends StrBuilder {

    private static final String SEPARATOR_VER = "|";
    private static final String SEPARATOR_HOR = "-";

    public GridBuilder() {
      super();
    }

    GridBuilder line(final int largeur) {
      return (GridBuilder) this.append('\n').append(Strings.repeat(SEPARATOR_HOR, largeur * 5))
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
