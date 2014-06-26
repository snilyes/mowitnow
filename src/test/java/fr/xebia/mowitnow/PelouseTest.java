package fr.xebia.mowitnow;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
				assertEquals(new Position(x, y), cellule.getPosition());
				assertFalse(cellule.isOccupe());
				assertFalse(cellule.isTondu());
				assertVoisin(pelouse, Orientation.EST, cellule.getPosition(), new Position(x + 1, y));
				assertVoisin(pelouse, Orientation.WEST, cellule.getPosition(), new Position(x - 1, y));
				assertVoisin(pelouse, Orientation.NORD, cellule.getPosition(), new Position(x, y + 1));
				assertVoisin(pelouse, Orientation.SUD, cellule.getPosition(), new Position(x, y - 1));
			}
		}
	}
	
	public void assertVoisin(final Pelouse pelouse, final Orientation orientation, final Position position, final Position positionAttendue) {
		Cellule cellule = pelouse.cellule(position);
		Cellule actuelle = cellule.getVoisin(orientation);
		Cellule attendue = null;
		if (entre(positionAttendue.getX(), 0, pelouse.getLargeur()) && entre(positionAttendue.getY(), 0, pelouse.getLongueur())){
			attendue = pelouse.cellule(positionAttendue);
		}
		assertEquals(attendue, actuelle);
	}
	
	public boolean entre(final int position, final int min, final int max) {
		return position >= min && position < max; 
	}
}
