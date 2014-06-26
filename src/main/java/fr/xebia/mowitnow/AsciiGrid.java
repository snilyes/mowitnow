package fr.xebia.mowitnow;

import org.apache.commons.lang.text.StrBuilder;

import com.google.common.base.Strings;

public final class AsciiGrid {


	public static String dessiner(final Pelouse pelouse) {
		int largeur = pelouse.getLargeur(), longeur = pelouse.getLongueur();
		GridBuilder builder = new GridBuilder();
		builder.append("\n\n");
		for (int y = longeur - 1; y >= 0; y--) {
			for (int x = -1; x < largeur ; x++) {
				if (x < 0) {
					builder.cell(y);
//				} else if (x == largeur) {
//					builder.cell(' ');
				} else if (pelouse.cellule(x, y).isOccupe()) {
					builder.cell('x');
				} else if (pelouse.cellule(x, y).isTondu()) {
					builder.cell('-');
				} else {
					builder.cell(' ');
				}
			}
			builder.line(largeur);
		}

		builder.cell();
		for (int i = 0; i < largeur; i++) {
			builder.cell(i);
		}
		builder.append("\n\n");
		return builder.toString();
	}

	public static void main(final String... strings) {
		new Pelouse(7, 5);
	}
	
	static class GridBuilder extends StrBuilder {
		
		private static final String SEPARATOR_VER = "|";
		private static final String SEPARATOR_HOR = "-";

		public GridBuilder() {
			super();
		}

		GridBuilder line (final int largeur) {
			return (GridBuilder) this.append('\n').append(Strings.repeat(SEPARATOR_HOR, largeur * 5)).append('\n');
		}
		
		GridBuilder cell (final int item) {
			return (GridBuilder) this.append(' ').append(item).append(' ').append(SEPARATOR_VER);
		}
		
		GridBuilder cell (final char item) {
			return (GridBuilder) this.append(' ').append(item).append(' ').append(SEPARATOR_VER);
		}

		
		GridBuilder cell () {
			return (GridBuilder) this.append(' ').append(' ').append(' ').append(SEPARATOR_VER);
		}
	}
}
