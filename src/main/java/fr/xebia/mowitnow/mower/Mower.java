package fr.xebia.mowitnow.mower;

import java.util.Observable;
import java.util.Queue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import fr.xebia.mowitnow.base.Cell;
import fr.xebia.mowitnow.base.Movable;
import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class Mower extends Observable implements Movable {

  @Getter
  private Cell cell;

  @Getter
  private Orientation orientation;

  @Getter
  @Setter
  @NonNull
  private Queue<Instruction> instructions;

  @Getter
  private final int id;

  public Mower(final int id, final Cell cell, final Orientation orientation) {
    super();
    this.cell = cell;
    this.orientation = orientation;
    this.cell.lock();
    this.id = id;
  }

  public Mower(final Cell cell, final Orientation orientation) {
    this(0, cell, orientation);
  }

  @Override
  public void rotateRight() {
    this.orientation = this.orientation.right();
    changeAndNotify();
  }

  @Override
  public void rotateLeft() {
    this.orientation = this.orientation.left();
    changeAndNotify();
  }

  @Override
  @Synchronized
  public void advance() {
    Cell next = cell.next(orientation);
    if (next != null && !next.isLock()) {
      this.cell.unlock();
      this.cell = next;
      this.cell.lock();
      mow();
      changeAndNotify();
    }
  }

  public void mow() {
    this.cell.setMowed(true);
  }

  public void start() {
    log.debug("Demarrage " + toString() + " ...");
    mow();
    int index = 1;
    if (instructions != null && !instructions.isEmpty()) {
      while (!instructions.isEmpty()) {
        Instruction instruction = instructions.poll();
        instruction.executer(this);
        log.debug("Execution instruction N° " + (index++) + " (" + instruction + ").");
      }
    } else {
      log.warn("Tondeuse n'est pas programmée !!! ");
    }
    log.debug("Arrêt " + toString());
  }

  private void changeAndNotify() {
    setChanged();
    notifyObservers();
  }

  public Position position() {
    return cell.getPosition();
  }

  @Override
  public String toString() {
    return "Tondeuse (id=" + id + ", position=" + position() + ", orientation=" + orientation
        + ").";
  }
}
