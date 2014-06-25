package fr.xebia.mowitnow;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PelouseTest {

	@Test
	@Parameters({ "5,5", "3,6", "1,1" })
	public void pelouseValide(final int largeur, final int longueur) {
		pelouse(largeur, longueur);
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters({ "0,0", "0,3", "2,0" })
	public void pelouseInvalide(final int largeur, final int longueur) {
		pelouse(largeur, longueur);
	}

	public void pelouse(final int largeur, final int longueur) {
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
	}

}
