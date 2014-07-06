package fr.xebia.mowitnow.unit;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.mower.Instruction;


interface Constant {

  static final Orientation EAST = Orientation.EAST;
  static final Orientation NORTH = Orientation.NORTH;
  static final Orientation WEST = Orientation.WEST;
  static final Orientation SOUTH = Orientation.SOUTH;

  static final Instruction D = Instruction.ROTATE_RIGHT;
  static final Instruction G = Instruction.ROTATE_LEFT;
  static final Instruction A = Instruction.FORWARD;

}
