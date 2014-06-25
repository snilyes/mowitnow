package fr.xebia.mowitnow;

import lombok.RequiredArgsConstructor;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;


@RequiredArgsConstructor
public enum Orientation {
  EST(1, 0), NORD(0, 1), WEST(-1, 0), SUD(0, -1);

  final int x;
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
	}).orNull();
  }
}
