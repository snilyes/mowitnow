package com.mowitnow.model.instruction;

import com.mowitnow.model.Mower;


/**
 * Makes a mower turn left.
 * 
 * @author mblanc
 */
public class TurnLeftInstruction implements Instruction {

	private Mower mower;
	
	public TurnLeftInstruction(Mower mower) {
		this.mower = mower;
	}
	
	@Override
	public void execute() {
		mower.turnLeft();
	}

}
