package fr.xebia.mowitnow.tonte;

import java.util.Observable;
import java.util.Queue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import fr.xebia.mowitnow.base.Cellule;
import fr.xebia.mowitnow.base.Mobile;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class Tondeuse extends Observable implements Mobile {

  @Getter
  private Cellule cellule;

  @Getter
  private Orientation orientation;

  public Tondeuse(final Cellule cellule, final Orientation orientation) {
    super();
    this.cellule = cellule;
    this.orientation = orientation;
    this.cellule.occuper();
  }

  @Getter
  @Setter
  @NonNull
  private Queue<Instruction> instructions;

  @Override
  public void pivoterDroite() {
    this.orientation = this.orientation.aDroite();
  }

  @Override
  public void pivoterGauche() {
    this.orientation = this.orientation.aGauche();
  }

  @Override
  public void avancer() {
    Cellule next = cellule.getVoisin(orientation);
    if (next != null && !next.isOccupe()) {
      this.cellule.liberer();
      this.cellule = next;
      this.cellule.occuper();
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
    if (instructions != null && !instructions.isEmpty()) {
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
  public String toString() {
    return "Tondeuse (position=" + position() + ", orientation=" + orientation + ").";
  }
}
