package fr.xebia.mowitnow.io.parseur;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.google.common.base.Splitter;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.tonte.Pelouse;
import fr.xebia.mowitnow.tonte.Tondeuse;

@RequiredArgsConstructor
public class TondeuseParseur implements Parseur<String, Tondeuse> {
  private static final String SEPARATOR = " ";
  private static final String PATTERN = "^\\d+ \\d+ [N|E|W|S]$";

  private final Pelouse pelouse;


  @Override
  public Tondeuse parse(final String source) {
    checkArgument(source.matches(PATTERN),
        "Erreur de parse des infos de la tondeuse [attendue: 'x y Orientaion'; actuelle: '"
            + source + "']");
    List<String> champs = Splitter.on(SEPARATOR).splitToList(source);
    int x = Integer.valueOf(champs.get(0));
    int y = Integer.valueOf(champs.get(1));
    Orientation orientation = Orientation.parCode(champs.get(2));
    return new Tondeuse(pelouse.cellule(x, y), orientation);
  }

}
