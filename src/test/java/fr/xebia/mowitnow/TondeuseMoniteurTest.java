package fr.xebia.mowitnow;

import static com.google.common.collect.Lists.newLinkedList;
import static fr.xebia.mowitnow.TestUtil.A;
import static fr.xebia.mowitnow.TestUtil.D;
import static fr.xebia.mowitnow.TestUtil.EST;
import static fr.xebia.mowitnow.TestUtil.G;
import static fr.xebia.mowitnow.TestUtil.NORD;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;

import java.io.File;

import lombok.SneakyThrows;

import org.junit.Test;

import com.google.common.io.Resources;

import fr.xebia.mowitnow.base.Position;
import fr.xebia.mowitnow.io.TondeuseMonitorLoader;
import fr.xebia.mowitnow.tonte.Pelouse;
import fr.xebia.mowitnow.tonte.Tondeuse;
import fr.xebia.mowitnow.tonte.TondeuseMoniteur;

public class TondeuseMoniteurTest {

  private final Pelouse p = new Pelouse(5, 5);


  @Test
  @SneakyThrows
  public void loaderTest() {
    File file = new File(Resources.getResource("data.txt").toURI());
    TondeuseMoniteur moniteur = new TondeuseMonitorLoader().fromFile(file);
    Pelouse p = new Pelouse(5, 5);
    Tondeuse t1 = new Tondeuse(p.cellule(1, 2), NORD);
    t1.setInstructions(newLinkedList(asList(G, A, G, A, G, A, G, A, A)));
    Tondeuse t2 = new Tondeuse(p.cellule(3, 3), EST);
    t2.setInstructions(newLinkedList(asList(A, A, D, A, A, D, A, D, D, A)));
    assertEquals(p, moniteur.getPelouse());
    assertThat(moniteur.getTondeuses(), contains(tondeuses()));
  }

  @Test
  public void monitorTest() {
    TondeuseMoniteur moniteur = new TondeuseMoniteur(p, asList(tondeuses()));
    moniteur.tondre();
    assertEquals(new Position(1, 3), moniteur.getTondeuses().get(0).position());
    assertEquals(new Position(4, 1), moniteur.getTondeuses().get(1).position());
  }

  private Tondeuse[] tondeuses() {
    Tondeuse t1 = new Tondeuse(p.cellule(1, 2), NORD);
    t1.setInstructions(newLinkedList(asList(G, A, G, A, G, A, G, A, A)));
    Tondeuse t2 = new Tondeuse(p.cellule(3, 3), EST);
    t2.setInstructions(newLinkedList(asList(A, A, D, A, A, D, A, D, D, A)));
    return new Tondeuse[] {t1, t2};
  }
}
