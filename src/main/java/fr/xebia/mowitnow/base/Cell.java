package fr.xebia.mowitnow.base;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represente une cellule d'une grille chainée
 * 
 * @author ilyes
 * 
 */
@ToString(exclude = {"next"})
@EqualsAndHashCode(exclude = {"next"})
@RequiredArgsConstructor
public class Cell {

  /**
   * Position de la cellule
   */
  @NonNull
  @Getter
  private final Position position;

  /**
   * Flag pour verouiller/deverouille la cellule
   */
  @Getter
  private boolean lock;

  /**
   * Vrai si la cellule à été tondue
   */
  @Getter
  @Setter
  private boolean mowed;

  /**
   * Represente les 4 cellules voisines
   */
  private final Map<Orientation, Cell> next = new HashMap<Orientation, Cell>(
      Orientation.values().length);

  /**
   * Vérouiller la cellule
   */
  public void lock() {
    lock = true;
  }

  /**
   * Dévérouiller la cellule
   */
  public void unlock() {
    lock = false;
  }

  /**
   * Ajoute un voisin de la cellule dans un sens donné
   * 
   * @param orientation
   * @param cellule
   */
  public void next(final Orientation orientation, final Cell cellule) {
    this.next.put(orientation, cellule);
  }

  /**
   * Renvoie le voisin de la cellule dans un sens donné
   * 
   * @param orientation
   * @return
   */
  public Cell next(final Orientation orientation) {
    return next.get(orientation);
  }
}
