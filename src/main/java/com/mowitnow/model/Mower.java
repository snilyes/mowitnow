package com.mowitnow.model;

import java.util.List;
import java.util.Observable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mowitnow.model.instruction.Instruction;

/**
 * Represents a mower.
 * 
 * @author mblanc
 */
public class Mower extends Observable {

	/** Id of the plot (used by the HTML UI of the application to know which mower to move). */
	private int id;

	/** Abcissa of the mower. */
	private int x;

	/** Ordinate of the mower. */
	private int y;

	/** Orientation of the mower. */
	private Orientation orientation;

	/** Lawn on which the mower executes its instructions. */
	private Lawn lawn;

	/** Instructions that the mower will executes. */
	private List<Instruction> instructions = Lists.newArrayList();

	public Mower(int id, Lawn lawn, int x, int y, Orientation orientation) {
		Preconditions.checkNotNull(lawn, "mower lawn must not be null");
		Preconditions.checkNotNull(orientation, "mower orientation must not be null");

		Preconditions.checkArgument(lawn.isIn(x, y), "mower %s should not be placed outside the lawn", id);

		this.id = id;
		this.lawn = lawn;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		mow();
	}

	/** Make the mower executes its instructions. */
	public void executeInstructions() {
		for (Instruction instruction : instructions) {
			instruction.execute();
			setChanged();
			notifyObservers();
		}
	}

	/** Mow the plot at the mower position. */
	public void mow() {
		lawn.getPlots()[this.x][y].mow();
	}

	/** 
	 * Move the mower.
	 * If the position after movement is out of the lawn, the mower does not move.
	 * Beyond the scope of the exercise : If the position after movement is already occupied by a mower, the mower does not move.
	 * */
	public void forward() {
		int nextX = x + orientation.getX();
		int nextY = y + orientation.getY();
		if (lawn.isIn(nextX, nextY) && !lawn.isMowerPosition(nextX, nextY)) {
			x = nextX;
			y = nextY;
			mow();
		}
	}

	/** Turn the mower to the right. */
	public void turnRight() {
		orientation = orientation.right();
	}

	/** Turn the mower to the left. */
	public void turnLeft() {
		orientation = orientation.left();
	}

	/**
	 * Add an instruction to the mower.
	 * @param instruction the instruction.
	 */
	public void addInstruction(Instruction instruction) {
		instructions.add(instruction);
	}

	// getters

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Orientation getOrientation() {
		return orientation;
	}
}
