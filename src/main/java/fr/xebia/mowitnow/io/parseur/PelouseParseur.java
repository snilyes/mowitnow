package fr.xebia.mowitnow.io.parseur;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import lombok.NonNull;

import com.google.common.base.Splitter;

import fr.xebia.mowitnow.tonte.Pelouse;

public class PelouseParseur implements Parseur<String, Pelouse> {

  private static final String SEPARATOR = " ";
  private static final String PATTERN = "^\\d+ \\d+$";

  @Override
  public Pelouse parse(@NonNull final String source) {
    checkArgument(source.matches(PATTERN),
        "Erreur de parse des infos de la tondeuse [attendue: 'largeur longueur'; actuelle: '"
            + source + "']");
    List<String> champs = Splitter.on(SEPARATOR).splitToList(source);
    return new Pelouse(Integer.valueOf(champs.get(0)), Integer.valueOf(champs.get(1)));
  }

}
