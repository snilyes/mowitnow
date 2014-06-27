package fr.xebia.mowitnow.tonte;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import fr.xebia.mowitnow.base.Cellule;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;
import fr.xebia.mowitnow.io.AsciiGrid;
import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@EqualsAndHashCode(exclude = "grille")
@ToString(exclude = "grille")
public class Pelouse {

  private final Cellule[][] grille;
  @Getter
  private final int largeur;
  @Getter
  private final int longueur;

  public Pelouse(final int largeur, final int longueur) {
    checkArgument(largeur > 0, " La largeur doit être > 0 ");
    checkArgument(longueur > 0, " La longueur doit être > 0");

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

    afficher();
  }

  private void setVoisins(final int x, final int y, final Orientation orientation) {
    int voisinX = x + orientation.getX();
    int voisinY = y + orientation.getY();
    if (dedans(voisinX, voisinY)) {
      grille[x][y].addVoisin(orientation, grille[voisinX][voisinY]);
      setVoisins(voisinX, voisinY, orientation);
    }
  }

  private boolean dedans(final int x, final int y) {
    return (x < largeur && x >= 0) && (y < longueur && y >= 0);
  }

  public Cellule cellule(final int x, final int y) {
    checkArgument(dedans(x, y), "la position (" + x + ',' + y + ") est en dehors de la pelouse");
    return grille[x][y];
  }

  public Cellule cellule(@NonNull final Position position) {
    return grille[position.getX()][position.getY()];
  }

  public void afficher() {
    log.debug(AsciiGrid.dessiner(this));
  }
}
