package fr.xebia.mowitnow.base;

/**
 * Represente un objet mobile
 * 
 * @author ilyes
 * 
 */
public interface Movable {

  /**
   * Avancer
   */
  void advance();

  /**
   * Pivoter à droite
   */
  void rotateRight();

  /**
   * Pivoter à gauche
   */
  void rotateLeft();
}
