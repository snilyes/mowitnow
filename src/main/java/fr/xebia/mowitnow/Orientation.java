package fr.xebia.mowitnow;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Orientation {
  EST(1, 0), NORD(0, 1), WEST(-1, 0), SUD(0, -1);


  final int x;
  final int y;

  public Orientation aDroite() {
    return null;
  }

  public Orientation aGauche() {
    return null;
  }

}
