package fr.xebia.mowitnow.unit;

import static com.google.common.collect.Lists.newLinkedList;
import static fr.xebia.mowitnow.unit.Constant.A;
import static fr.xebia.mowitnow.unit.Constant.D;
import static fr.xebia.mowitnow.unit.Constant.EAST;
import static fr.xebia.mowitnow.unit.Constant.G;
import static fr.xebia.mowitnow.unit.Constant.NORTH;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;

import java.io.File;

import lombok.SneakyThrows;

import org.junit.Test;

import com.google.common.io.Resources;

import fr.xebia.mowitnow.base.Position;
import fr.xebia.mowitnow.io.Loader;
import fr.xebia.mowitnow.mower.Lawn;
import fr.xebia.mowitnow.mower.Monitor;
import fr.xebia.mowitnow.mower.Mower;

public class MonitorTest {

  private final Lawn p = new Lawn(5, 5);


  @Test
  @SneakyThrows
  public void loaderTest() {
    File file = new File(Resources.getResource("data.txt").toURI());
    Monitor monitor = new Loader().fromFile(file);
    Lawn p = new Lawn(5, 5);
    assertEquals(p, monitor.getLawn());
    assertThat(monitor.getMowers(), contains(mowers()));
  }

  @Test
  public void monitorTest() {
    Monitor monitor = new Monitor(p, asList(mowers()));
    monitor.mow();
    assertEquals(new Position(1, 2), monitor.getMowers().get(0).position());
    assertEquals(new Position(4, 0), monitor.getMowers().get(1).position());
  }

  private Mower[] mowers() {
    Mower t1 = new Mower(p.cellAt(0, 1), NORTH);
    t1.setInstructions(newLinkedList(asList(G, A, G, A, G, A, G, A, A)));
    Mower t2 = new Mower(p.cellAt(2, 2), EAST);
    t2.setInstructions(newLinkedList(asList(A, A, D, A, A, D, A, D, D, A)));
    return new Mower[] {t1, t2};
  }
}
