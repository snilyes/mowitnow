package fr.xebia.mowitnow.mower;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import fr.xebia.mowitnow.util.AsciiGrid;

/**
 * 
 * Monietur permetant la synchronisation et la surveillance des executions des instructions de
 * plusieurs tondeuses sur une pelouse
 * 
 * @author ilyes
 * 
 */
@Slf4j
public class Monitor implements Observer {

  @Getter
  @NonNull
  private final Lawn lawn;

  @Getter
  @NonNull
  final List<Mower> mowers;

  public Monitor(final Lawn lawn, final List<Mower> mowers) {
    super();
    this.lawn = lawn;
    this.mowers = mowers;
  }

  /**
   * Lancement des tondeuses
   */
  public void mow() {
    for (Mower mower : mowers) {
      mower.addObserver(this);
      mow(mower);
    }
  }

  private void mow(final Mower mower) {
    mower.start();
  }

  @Override
  public void update(final Observable o, final Object arg) {
    log.debug(AsciiGrid.draw(lawn, mowers.toArray(new Mower[] {})));
  }
}
