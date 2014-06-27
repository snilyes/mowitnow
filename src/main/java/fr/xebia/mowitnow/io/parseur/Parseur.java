package fr.xebia.mowitnow.io.parseur;

public interface Parseur<I, O> {

  O parse(final I source);
}
