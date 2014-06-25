package fr.xebia.mowitnow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.google.common.base.Preconditions;

@RequiredArgsConstructor
public class Pelouse {

  @Getter
  private final Cellule[][] grille;
  @Getter
  private final int largeur;
  @Getter
  private final int longueur;

  public Pelouse(final int largeur, final int longueur) {
    Preconditions.checkArgument(largeur > 0, " La largeur doit être > 0 ");
    Preconditions.checkArgument(longueur > 0, " La longueur doit être > 0");

    this.largeur = largeur;
    this.longueur = longueur;
    grille = new Cellule[largeur][longueur];
    for (int x = 0; x < largeur; x++) {
      for (int y = 0; y < longueur; y++) {
        grille[x][y] = new Cellule(new Position(x, y));
      }
    }

    for (int y = 0; y < longueur; y++) {
      setVoisins(0, y, Orientation.EST);
      setVoisins(largeur - 1, y, Orientation.WEST);
    }

    for (int x = 0; x < largeur; x++) {
      setVoisins(x, 0, Orientation.NORD);
      setVoisins(x, longueur - 1, Orientation.SUD);
    }
  }

  private void setVoisins(final int x, final int y, final Orientation orientation) {
    int voisinX = x + orientation.x;
    int voisinY = y + orientation.y;
    if (in(voisinX, voisinY)) {
      grille[x][y].addVoisin(orientation, grille[voisinX][voisinY]);
      setVoisins(voisinX, voisinY, orientation);
    }
  }

  private boolean in(final int x, final int y) {
    return (x < largeur && x >= 0) && (y < longueur && y >= 0);
  }

  public Cellule cellule(final int x, final int y) {
    Preconditions.checkArgument(x < largeur, x + " doit être entre [0," + largeur + "[");
    Preconditions.checkArgument(x < longueur, x + " doit être entre [0," + longueur + "[");
    return grille[x][y];
  }
}
