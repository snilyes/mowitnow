package com.mowitnow.model;

/**
 * Represents a lawn plot. (Class added to check if a lawn has been entirely
 * mowed - beyond the scope of the exercice)
 * 
 * @author mblanc
 */
public class LawnPlot {

	/** Abcissa of the plot. */
	private int x;

	/** Ordinate of the plot. */
	private int y;

	/** true if the plot has been mowed. */
	private boolean mowed;

	public LawnPlot(int x, int y, boolean mowed) {
		this.x = x;
		this.y = y;
		this.mowed = mowed;
	}

	/**
	 * Mow the plot.
	 */
	public void mow() {
		this.mowed = true;
	}

	// getters

	public boolean isMowed() {
		return mowed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
