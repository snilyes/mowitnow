package fr.xebia.mowitnow.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;


/**
 * Les 4 directions possibles representées par un vecteur (x, y)
 * 
 * @author ilyes
 *
 */
@RequiredArgsConstructor
public enum Orientation {
  EAST("E", 1, 0), NORTH("N", 0, 1), WEST("W", -1, 0), SOUTH("S", 0, -1);

  /**
   * Identifiant de l'orientation
   */
  @Getter
  final String code;

  /**
   * Abscisse du vecteur
   */
  @Getter
  final int x;

  /**
   * Ordonné du vecteur
   */
  @Getter
  final int y;

  /**
   * 
   * @return L'orientation de droite
   */
  public Orientation right() {
    return byVector(y, -x);
  }

  /**
   * 
   * @return L'orientation de gauche
   */
  public Orientation left() {
    return byVector(-y, x);
  }

  /**
   * 
   * @param a abscisse du vecteur
   * @param b ordonnée du vecteur
   * @return Retrouve une orientation à partir de son vecteur
   */
  public static Orientation byVector(final int a, final int b) {

    return Iterators.tryFind(Iterators.forArray(values()), new Predicate<Orientation>() {
      @Override
      public boolean apply(final Orientation input) {
        return input.x == a && input.y == b;
      }
    }).get();
  }

  /**
   * 
   * @param code
   * @return Retrouve une orientation à partir de son code
   */
  public static Orientation byCode(final String code) {

    return Iterators.tryFind(Iterators.forArray(values()), new Predicate<Orientation>() {
      @Override
      public boolean apply(final Orientation input) {
        return input != null && code.equals(input.code);
      }
    }).get();
  }
}
