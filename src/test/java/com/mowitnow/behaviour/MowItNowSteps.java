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

public class MowItNowSteps {

	private Lawn lawn;
	private Mower mower;
	private Throwable throwable;

	@Given("a $width by $height lawn")
	public void lawn(int width, int height) {
		try {
			lawn = new Lawn(width, height);
		} catch (Exception exception) {
			throwable = exception;
		}
	}

	@Given("a mower at position $x $y facing $orientation")
	public void mowerAtPosition(int x, int y, Orientation orientation) {
		try {
			mower = new Mower(1, lawn, x, y, orientation);
		} catch (Exception exception) {
			throwable = exception;
		}
	}

	@When("the mower executes instructions $instructions")
	public void mowerExecutesInstructions(String instructions) {
		try {
			parseInstructions(instructions);
			mower.executeInstructions();
		} catch (Exception exception) {
			throwable = exception;
		}
	}

	private void parseInstructions(String instructions) {
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
	}

	@Then("the mower should be at position $x $y facing $orientation")
	public void mowerShouldBeAt(int x, int y, Orientation orientation) {
		Assert.assertEquals(x, mower.getX());
		Assert.assertEquals(y, mower.getY());
		Assert.assertEquals(orientation, mower.getOrientation());
	}

	@Then("the lawn should be mowed")
	public void lawnShouldBeMowed() {
		Assert.assertTrue(lawn.isMowed());
	}

	@Then("an error message should appear which says: $message")
	public void exceptionShouldHaveBeenThrown(String message) {
		Assert.assertNotNull(throwable);
		Assert.assertEquals(message, throwable.getMessage());
	}

}