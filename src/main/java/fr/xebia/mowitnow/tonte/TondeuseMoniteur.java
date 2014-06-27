package fr.xebia.mowitnow.tonte;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import fr.xebia.mowitnow.io.AsciiGrid;

@Slf4j
public class TondeuseMoniteur implements Observer {

  public TondeuseMoniteur(Pelouse pelouse, List<Tondeuse> tondeuses) {
    super();
    this.pelouse = pelouse;
    this.tondeuses = tondeuses;
  }

  @Getter
  @NonNull
  private final Pelouse pelouse;

  @Getter
  @NonNull
  final List<Tondeuse> tondeuses;

  public void tondre() {
    for (Tondeuse tondeuse : tondeuses) {
      tondeuse.addObserver(this);
      tondre(tondeuse);
    }
  }

  private void tondre(final Tondeuse tondeuse) {
    tondeuse.demarrer();
  }

  @Override
  public void update(final Observable o, final Object arg) {
    log.debug(AsciiGrid.dessiner(pelouse, tondeuses.toArray(new Tondeuse[] {})));

  }
}
