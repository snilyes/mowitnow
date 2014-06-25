package fr.xebia.mowitnow;

import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Strings;

@Slf4j
public class AsciiGrid {

  private static final String SEPARATOR_VER = "|";
  private static final String SEPARATOR_HOR = "-";

  private static String dessiner(Pelouse pelouse) {
    int largeur = pelouse.getLargeur(), longeur = pelouse.getLongueur();
    StringBuffer buffer = new StringBuffer();
    buffer.append("   ").append(SEPARATOR_VER);
    for (int i = 0; i < largeur; i++) {
      buffer.append(' ').append(i + 1).append(' ').append(SEPARATOR_VER);
    }

    buffer.append('\n').append(Strings.repeat(SEPARATOR_HOR, largeur * 5));
    buffer.append('\n');

    for (int i = 0; i < longeur; i++) {
      for (int j = 0; j < largeur + 1; j++) {
        if (j > 0) {
          buffer.append(' ').append(' ').append(' ').append(SEPARATOR_VER);
        } else {
          buffer.append(' ').append(i + 1).append(' ').append(SEPARATOR_VER);
        }
      }
      buffer.append('\n').append(Strings.repeat(SEPARATOR_HOR, largeur * 5)).append('\n');
    }
    return buffer.toString();
  }

  public static void main(String... strings) {
    System.out.print(dessiner(new Pelouse(7, 5)));
  }
}
