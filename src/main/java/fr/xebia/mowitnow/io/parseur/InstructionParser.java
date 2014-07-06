package fr.xebia.mowitnow.io.parseur;

import java.util.LinkedList;
import java.util.Queue;

import fr.xebia.mowitnow.mower.Instruction;

/**
 * Renvoie une file instruction à partir d'un string
 * 
 * @author ilyes
 *
 */
public class InstructionParser implements Parser<String, Queue<Instruction>> {


  @Override
  public Queue<Instruction> parse(final String source) {
    Queue<Instruction> instructions = new LinkedList<Instruction>();
    for (char instruction : source.toCharArray()) {
      switch (instruction) {
        case 'G':
          instructions.add(Instruction.ROTATE_LEFT);
          break;
        case 'D':
          instructions.add(Instruction.ROTATE_RIGHT);
          break;
        case 'A':
          instructions.add(Instruction.FORWARD);
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
