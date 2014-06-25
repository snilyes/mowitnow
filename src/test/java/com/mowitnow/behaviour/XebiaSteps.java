package com.mowitnow.behaviour;

import junit.framework.Assert;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.mowitnow.model.Lawn;
import com.mowitnow.model.Mower;
import com.mowitnow.model.Orientation;
import com.mowitnow.model.instruction.ForwardInstruction;
import com.mowitnow.model.instruction.TurnLeftInstruction;
import com.mowitnow.model.instruction.TurnRightInstruction;

public class XebiaSteps {

	private Lawn lawn;

	@Given("a $width by $height lawn")
	public void lawn(int width, int height) {
		lawn = new Lawn(width, height);
	}

	@Given("a mower at position $x $y facing $orientation with instructions $instructions")
	public void mowerAtPositionWithInstructions(int x, int y, Orientation orientation, String instructions) {
		Mower mower = new Mower(lawn.getMowers().size() + 1, lawn, x, y, orientation);
		for (char instruction : instructions.toCharArray()) {
			switch (instruction) {
			case 'G':
				mower.addInstruction(new TurnLeftInstruction(mower));
				break;
			case 'D':
				mower.addInstruction(new TurnRightInstruction(mower));
				break;
			case 'A':
				mower.addInstruction(new ForwardInstruction(mower));
				break;
			default:
				throw new IllegalArgumentException("Unknow instruction : " + instruction);
			}
		}
		lawn.addMower(mower);
	}

	@When("the mowers execute their instructions")
	public void mowersExecuteTheirInstructions() {
		lawn.mow();
	}
	
	@Then("the $index{st|nd|rd} mower should be at position $x $y facing $orientation")
	public void mowerShouldBeAtPositionFacing(int index, int x, int y, Orientation orientation) {
		Mower mower = lawn.getMowers().get(index-1);
		Assert.assertEquals(x, mower.getX());
		Assert.assertEquals(y, mower.getY());
		Assert.assertEquals(orientation, mower.getOrientation());
	}

}