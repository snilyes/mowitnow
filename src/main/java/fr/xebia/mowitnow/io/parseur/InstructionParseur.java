package fr.xebia.mowitnow.io.parseur;

import java.util.LinkedList;
import java.util.Queue;

import fr.xebia.mowitnow.tonte.Instruction;

public class InstructionParseur implements Parseur<String, Queue<Instruction>> {


  @Override
  public Queue<Instruction> parse(final String source) {
    Queue<Instruction> instructions = new LinkedList<Instruction>();
    for (char instruction : source.toCharArray()) {
      switch (instruction) {
        case 'G':
          instructions.add(Instruction.PIVOTER_GAUCHE);
          break;
        case 'D':
          instructions.add(Instruction.PIVOTER_DROITE);
          break;
        case 'A':
          instructions.add(Instruction.AVANCER);
          break;
        default:
          throw new IllegalArgumentException(
              "Erreur de parse des instructions [attendue: (G|D|A) n fois'; actuelle: '" + source
                  + "']");
      }
    }
    return instructions;
  }

}
