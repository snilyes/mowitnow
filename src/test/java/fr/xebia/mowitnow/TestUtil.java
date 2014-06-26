package fr.xebia.mowitnow;

final class TestUtil {
	
	static Orientation est() {
		return Orientation.EST;
	}

	static Orientation nord() {
		return Orientation.NORD;
	}

	static Orientation sud() {
		return Orientation.SUD;
	}

	static Orientation west() {
		return Orientation.WEST;
	}
	
	static Instruction droite() {
		return Instruction.PIVOTER_DROITE;
	}

	static Instruction gauche() {
		return Instruction.PIVOTER_GAUCHE;
	}

	static Instruction avancer() {
		return Instruction.AVANCER;
	}
}
