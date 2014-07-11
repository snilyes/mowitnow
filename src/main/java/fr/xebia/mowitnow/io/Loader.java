package fr.xebia.mowitnow.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import lombok.NonNull;
import lombok.SneakyThrows;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import fr.xebia.mowitnow.io.parseur.InstructionParser;
import fr.xebia.mowitnow.io.parseur.LawnParser;
import fr.xebia.mowitnow.io.parseur.MowerParser;
import fr.xebia.mowitnow.mower.Instruction;
import fr.xebia.mowitnow.mower.Lawn;
import fr.xebia.mowitnow.mower.Monitor;
import fr.xebia.mowitnow.mower.Mower;

/**
 * Classe permettant le chargement d'un moniteur
 * 
 * @author ilyes
 *
 */
public class Loader {

  private static final String LINE_SEPRATOR = "\n";

  @SneakyThrows(IOException.class)
  public Monitor fromFile(final File file) {
    return fromLines(Files.readLines(file, Charsets.UTF_8));
  }

  public Monitor fromText(final String text) {
    return fromLines(Splitter.on(LINE_SEPRATOR).omitEmptyStrings().splitToList(text));
  }

  public Monitor fromLines(@NonNull final List<String> pLines) {
    Preconditions.checkArgument(pLines.size() > 2, "Erreur de chargement du moniteur: ficher incomplet");
    Iterator<String> lines = pLines.iterator();
    LawnParser lawnParser = new LawnParser();
    InstructionParser instructionParser = new InstructionParser();
    Lawn lawn = lawnParser.parse(lines.next());
    MowerParser mowerParser = new MowerParser(lawn);
    List<Mower> mowers = Lists.newArrayList();
    while (lines.hasNext()) {
      Mower mower = mowerParser.parse(lines.next());
      Queue<Instruction> instructions = instructionParser.parse(lines.next());
      mower.setInstructions(instructions);
      mowers.add(mower);
    }
    return new Monitor(lawn, mowers);
  }

}
