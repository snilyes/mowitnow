package fr.xebia.mowitnow.unit;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.mower.Instruction;

/**
 * Grouper les enums en constante pour alleger le code
 * 
 * @author ilyes
 * 
 */
interface Constant {

	// Orientations
	static final Orientation EAST = Orientation.EAST;
	static final Orientation NORTH = Orientation.NORTH;
	static final Orientation WEST = Orientation.WEST;
	static final Orientation SOUTH = Orientation.SOUTH;

	// Instructions
	static final Instruction D = Instruction.ROTATE_RIGHT;
	static final Instruction G = Instruction.ROTATE_LEFT;
	static final Instruction A = Instruction.FORWARD;
}
