package fr.xebia.mowitnow.tonte;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import fr.xebia.mowitnow.jardin.Pelouse;

@RequiredArgsConstructor
public class TondeuseMoniteur implements Observer {

  @NonNull
  private final Pelouse pelouse;

  @NonNull
  final List<Tondeuse> tondeuses;

  public void tondre() {
    for (Tondeuse tondeuse : tondeuses) {
      tondre(tondeuse);
    }
  }

  private void tondre(final Tondeuse tondeuse) {
    tondeuse.demarrer();
  }

  @Override
  public void update(final Observable o, final Object arg) {
    pelouse.afficher();
  }
}
