package com.mowitnow.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Represents a lawn.
 * @author mblanc
 */
public class Lawn {

	private int height;

	private int width;

	private LawnPlot[][] plots;

	private List<Mower> mowers = Lists.newArrayList();

	public Lawn(int width, int height) {
		Preconditions.checkArgument(width >= 0, "mower width should be >= 0");
		Preconditions.checkArgument(height >= 0, "mower height should be >= 0");

		this.width = width;
		this.height = height;
		this.plots = new LawnPlot[width + 1][height + 1];
		for (int i = 0; i <= width; i++) {
			for (int j = 0; j <= height; j++) {
				plots[i][j] = new LawnPlot(i, j, false);
			}
		}
	}

	/**
	 * Tell all the mower of the lawn to executes their instructions.
	 */
	public void mow() {
		for (Mower mower : mowers) {
			mower.executeInstructions();
		}
	}

	/**
	 * Add a mower.
	 * @param mower a mower.
	 */
	public void addMower(Mower mower) {
		mowers.add(mower);
	}

	/**
	 * @return true if all the plots of the lawn are mowed.
	 */
	public boolean isMowed() {
		boolean isMowed = true;
		for (int i = 0; i <= width; i++) {
			for (int j = 0; j <= height; j++) {
				isMowed = isMowed && plots[i][j].isMowed();
			}
		}
		return isMowed;
	}
	
	/**
	 * @param x abcissa of the tested position.
	 * @param y ordinate of the tested position.
	 * @return true if the position is in the lawn.
	 */
	public boolean isIn(int x, int y) {
		return x >= 0 && x <= width && y >= 0 && y <= height;
	}
	
	/**
	 * @param x abcissa of the tested position.
	 * @param y ordinate of the tested position.
	 * @return true if the position is a mower position.
	 */
	public boolean isMowerPosition(int x, int y) {
		for (Mower mower : mowers) {
			if (x == mower.getX() && y == mower.getY()) {
				return true;
			}
		}
		return false;
	}

	// getters

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	@JsonIgnore
	public LawnPlot[][] getPlots() {
		return plots;
	}

	@JsonIgnore
	public List<Mower> getMowers() {
		return ImmutableList.copyOf(mowers);
	}
}
