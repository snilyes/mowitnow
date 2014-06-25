package fr.xebia.mowitnow;

import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

import com.google.common.base.Preconditions;

public class Tondeuse {

  @Getter
  private Cellule cellule;

  @Getter
  private Orientation orientation;

  @Getter
  @Setter
  private Queue<Instruction> instructions;

  public void orienter(final Orientation orientation) {
    this.orientation = orientation;
  }

  public void pivoterDroite() {
    this.orientation = this.orientation.aDroite();
  }

  public void pivoterGauche() {
    this.orientation = this.orientation.aGauche();
  }

  public void avancer() {
    Cellule next = cellule.getVoisin(orientation);
    if (next != null && !next.isOccupe()) {
      next.verouiller();
      this.cellule.deverouiller();
      this.cellule = next;
    }
  }


  public void demarrer() {
    Preconditions.checkNotNull(instructions, "Aucune instruction programmée");
    while (!instructions.isEmpty()) {
      Instruction instruction = instructions.poll();
      instruction.executer(this);
    }
  }
}
