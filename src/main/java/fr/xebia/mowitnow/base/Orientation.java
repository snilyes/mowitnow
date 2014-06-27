package fr.xebia.mowitnow.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

@RequiredArgsConstructor
public enum Orientation {
  EST("E", 1, 0), NORD("N", 0, 1), WEST("W", -1, 0), SUD("S", 0, -1);

  @Getter
  final String code;

  @Getter
  final int x;
  @Getter
  final int y;

  public Orientation aDroite() {
    return parVecteur(y, -x);
  }

  public Orientation aGauche() {
    return parVecteur(-y, x);
  }

  public Orientation parVecteur(final int a, final int b) {

    return Iterators.tryFind(Iterators.forArray(values()), new Predicate<Orientation>() {
      @Override
      public boolean apply(final Orientation input) {
        return input.x == a && input.y == b;
      }
    }).get();
  }

  public static Orientation parCode(final String code) {

    return Iterators.tryFind(Iterators.forArray(values()), new Predicate<Orientation>() {
      @Override
      public boolean apply(final Orientation input) {
        return code.equals(input.code);
      }
    }).get();
  }

}
