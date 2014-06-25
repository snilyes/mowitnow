package fr.xebia.mowitnow;

import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

public class Tondeuse {

  @Getter
  private Cellule cellule;

  @Getter
  private Orientation orientation;

  public Tondeuse(final Cellule cellule, final Orientation orientation) {
	super();
	this.cellule = cellule;
	this.orientation = orientation;
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
//    Preconditions.checkNotNull(instructions, "Aucune instruction programmée");
    tondre();
    if (instructions != null) {
	    while (!instructions.isEmpty()) {
	      Instruction instruction = instructions.poll();
	      instruction.executer(this);
	    }
    }
  }
}
