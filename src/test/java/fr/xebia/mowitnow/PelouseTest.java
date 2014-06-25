package fr.xebia.mowitnow;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RequiredArgsConstructor
@RunWith(Parameterized.class)
public class PelouseTest {

  private final int largeur;

  private final int longueur;

  private final Class<Throwable> erreur;

  @Parameters
  public static Iterable<?> testValidPelouse() {
    return Arrays.asList(new Object[][] { {5, 5, null}, {3, 6, null}, {1, 1, null},
        {0, 0, IllegalArgumentException.class}, {0, 3, IllegalArgumentException.class},
        {2, 0, IllegalArgumentException.class}});
  }

  @Test
  public void pelouse() {
    try {
      // When
      Pelouse pelouse = new Pelouse(largeur, longueur);

      // Then
      for (int x = 0; x < largeur; x++) {
        for (int y = 0; y < longueur; y++) {
          Cellule cellule = pelouse.cellule(x, y);
          Assert.assertEquals(new Position(x, y), cellule.getPosition());
          Assert.assertFalse(cellule.isOccupe());
          Assert.assertFalse(cellule.isTondu());

          Cellule droite = cellule.getVoisin(Orientation.EST);
          if (x < largeur - 1) {
            Assert.assertEquals(pelouse.cellule(x + 1, y), droite);
          } else {
            Assert.assertNull(droite);
          }

          Cellule gauche = cellule.getVoisin(Orientation.WEST);
          if (x > 0) {
            Assert.assertEquals(pelouse.cellule(x - 1, y), gauche);
          } else {
            Assert.assertNull(gauche);
          }

          Cellule haut = cellule.getVoisin(Orientation.NORD);
          if (y < longueur - 1) {
            Assert.assertEquals(pelouse.cellule(x, y + 1), haut);
          } else {
            Assert.assertNull(haut);
          }

          Cellule bas = cellule.getVoisin(Orientation.SUD);
          if (y > 0) {
            Assert.assertEquals(pelouse.cellule(x, y - 1), bas);
          } else {
            Assert.assertNull(bas);
          }
        }
      }
    } catch (Throwable t) {
      Assert.assertEquals(t.getClass(), erreur);
    }
  }
}
