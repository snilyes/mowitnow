package com.mowitnow.model;

/**
 * Cardinal directions enum.
 * @author mblanc
 */
public enum Orientation {

	EAST("E", 1, 0) {
		@Override public Orientation left() { return NORTH;}
		@Override public Orientation right() { return SOUTH;}
	}, 
	NORTH("N", 0, 1) {
		@Override public Orientation left() { return WEST;}
		@Override public Orientation right() { return EAST;}
	}, 
	WEST("W", -1, 0) {
		@Override public Orientation left() { return SOUTH;}
		@Override public Orientation right() { return NORTH;}
	}, 
	SOUTH("S", 0, -1)  {
		@Override public Orientation left() { return EAST;}
		@Override public Orientation right() { return WEST;}
	};

	/** Code representation of the orientation. */
	private String code;
	
	/** The abcissa component of the orientation vector. */
	private int x;
	
	/** The ordinate component of the orientation vector. */
	private int y;

	private Orientation(String code, int x, int y) {
		this.code = code;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The orientation at the left of this orientation.
	 */
	public abstract Orientation left();
	
	/**
	 * @return The orientation at the right of this orientation.
	 */
	public abstract Orientation right();

	/**
	 * Return the orientation represented by a string code.
	 * @param code the code.
	 * @return the orientation represented by the code.
	 */
	public static Orientation fromCode(String code) {
		for (Orientation orientation : Orientation.values()) {
			if (orientation.code.equals(code)) {
				return orientation;
			}
		}
		throw new IllegalArgumentException("No orientation with code " + code + " found");
	}
	
	//getters

	public String getCode() {
		return code;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
