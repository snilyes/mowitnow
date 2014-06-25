package com.mowitnow.model.instruction;

import com.mowitnow.model.Mower;

/**
 * Makes a mower move forward.
 * 
 * @author mblanc
 */
public class ForwardInstruction implements Instruction {
	
	private Mower mower;
	
	public ForwardInstruction(Mower mower) {
		this.mower = mower;
	}

	@Override
	public void execute() {
		mower.forward();
	}

}
