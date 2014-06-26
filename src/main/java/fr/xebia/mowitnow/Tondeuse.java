package fr.xebia.mowitnow;

import java.util.Observable;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tondeuse extends Observable {

	@Getter
	private Cellule cellule;

	@Getter
	private Orientation orientation;

	public Tondeuse(final Cellule cellule, final Orientation orientation) {
		super();
		this.cellule = cellule;
		this.orientation = orientation;
		this.cellule.verouiller();
	}

	@Getter
	@Setter
	private Queue<Instruction> instructions;

	public void pivoterDroite() {
		this.orientation = this.orientation.aDroite();
	}

	public void pivoterGauche() {
		this.orientation = this.orientation.aGauche();
	}

	public void avancer() {
		Cellule next = cellule.getVoisin(orientation);
		if (next != null && !next.isOccupe()) {
			this.cellule.deverouiller();
			this.cellule = next;
			this.cellule.verouiller();
			tondre();
		}
	}

	public void tondre() {
		this.cellule.setTondu(true);
	}

	public void demarrer() {
		log.debug("Demarrage " + toString() + " ...");
		tondre();
		int index = 1;
		if (instructions != null) {
			while (!instructions.isEmpty()) {
				Instruction instruction = instructions.poll();
				instruction.executer(this);
				log.debug("Execution instruction N° " + (index++) + " (" + instruction + ").");
				this.setChanged();
				notifyObservers();
			}
		} else {
			log.warn("Tondeuse n'est pas programmée !!! ");
		}
		log.debug("Arrêt " + toString());
	}

	public Position position() {
		return cellule.getPosition();
	}
	
	@Override
	public String toString () {
		return "Tondeuse (position=" + position() + ", orientation=" + orientation + ").";
	}
}
