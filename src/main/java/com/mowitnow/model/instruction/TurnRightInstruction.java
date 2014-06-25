package com.mowitnow.model.instruction;

import com.mowitnow.model.Mower;


/**
 * Makes a mower turn right.
 * 
 * @author mblanc
 */
public class TurnRightInstruction implements Instruction {

	private Mower mower;
	
	public TurnRightInstruction(Mower mower) {
		this.mower = mower;
	}
	
	@Override
	public void execute() {
		mower.turnRight();
	}

}
