package fr.xebia.mowitnow.io.parseur;

/**
 * Represente un parseur d'une entrée <I> vers une sortie <O>
 * 
 * @author ilyes
 * 
 * @param <I> Input du parseur
 * @param <O> Output du parseur
 */
public interface Parser<I, O> {

  O parse(final I source);
}
