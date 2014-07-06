package fr.xebia.mowitnow.integration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.util.ResourceUtils;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.io.Loader;
import fr.xebia.mowitnow.mower.Monitor;
import fr.xebia.mowitnow.mower.Mower;

public class MowitnowSteps {

  private File file;
  private List<Mower> mowers;

  @Given("le fichier \"$path\"")
  public void file(final String path) throws FileNotFoundException {
    file = ResourceUtils.getFile(path);
  }

  @When("tondre la pelouse")
  public void mow() {
    Monitor monitor = new Loader().fromFile(file);
    mowers = monitor.getMowers();
    monitor.mow();
  }

  @Then("la tondeuse $index doit etre postionnee au ($x, $y) et orientee vers $orientation")
  public void verify(final int index, final int x, final int y, final Orientation orientation) {
    Mower mower = mowers.get(index - 1);
    assertEquals(x, mower.position().getX());
    assertEquals(y, mower.position().getY());
    assertEquals(orientation, mower.getOrientation());
  }
}
