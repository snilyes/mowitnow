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

import fr.xebia.mowitnow.io.parseur.InstructionParseur;
import fr.xebia.mowitnow.io.parseur.PelouseParseur;
import fr.xebia.mowitnow.io.parseur.TondeuseParseur;
import fr.xebia.mowitnow.tonte.Instruction;
import fr.xebia.mowitnow.tonte.Pelouse;
import fr.xebia.mowitnow.tonte.Tondeuse;
import fr.xebia.mowitnow.tonte.TondeuseMoniteur;

public class TondeuseMonitorLoader {

  private static final String LINE_SEPRATOR = "\n";

  @SneakyThrows(IOException.class)
  public TondeuseMoniteur fromFile(final File file) {
    return fromLines(Files.readLines(file, Charsets.UTF_8));
  }

  public TondeuseMoniteur fromText(final String text) {
    return fromLines(Splitter.on(LINE_SEPRATOR).omitEmptyStrings().splitToList(text));
  }

  public TondeuseMoniteur fromLines(@NonNull final List<String> pLines) {
    Preconditions.checkArgument(pLines.size() > 3,
        "Erreur de chargement du moniteur: ficher incomplet");
    Iterator<String> lines = pLines.iterator();
    PelouseParseur pelouseParser = new PelouseParseur();
    InstructionParseur instructionParser = new InstructionParseur();
    Pelouse pelouse = pelouseParser.parse(lines.next());
    TondeuseParseur tondeuseParser = new TondeuseParseur(pelouse);
    List<Tondeuse> tondeuses = Lists.newArrayList();
    while (lines.hasNext()) {
      Tondeuse tondeuse = tondeuseParser.parse(lines.next());
      Queue<Instruction> instructions = instructionParser.parse(lines.next());
      tondeuse.setInstructions(instructions);
      tondeuses.add(tondeuse);
    }
    return new TondeuseMoniteur(pelouse, tondeuses);
  }

}
