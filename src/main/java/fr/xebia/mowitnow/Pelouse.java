package fr.xebia.mowitnow;

import java.util.Observable;
import java.util.Observer;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Preconditions;

@Slf4j
public class Pelouse implements Observer {

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
		
		afficher();
	}

	private void setVoisins(final int x, final int y, final Orientation orientation) {
		int voisinX = x + orientation.x;
		int voisinY = y + orientation.y;
		if (dedans(voisinX, voisinY)) {
			grille[x][y].addVoisin(orientation, grille[voisinX][voisinY]);
			setVoisins(voisinX, voisinY, orientation);
		}
	}

	private boolean dedans(final int x, final int y) {
		return (x < largeur && x >= 0) && (y < longueur && y >= 0);
	}

	public Cellule cellule(final int x, final int y) {
		Preconditions.checkArgument(dedans(x, y), "la position (" + x + ',' + y + ") est en dehors de la pelouse");
		return grille[x][y];
	}

	public Cellule cellule(@NonNull final Position position) {
		return grille[position.getX()][position.getY()];
	}

	@Override
	public void update(final Observable o, final Object arg) {
		afficher();
	}
	
	public void afficher() {
		log.debug(AsciiGrid.dessiner(this));
	}
}
