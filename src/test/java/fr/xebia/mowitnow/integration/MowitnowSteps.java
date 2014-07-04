package fr.xebia.mowitnow.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.util.ResourceUtils;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.io.TondeuseMonitorLoader;
import fr.xebia.mowitnow.tonte.Tondeuse;
import fr.xebia.mowitnow.tonte.TondeuseMoniteur;

import static org.junit.Assert.assertEquals;

public class MowitnowSteps {

	private File file;
	private List<Tondeuse> tondeuses;

	@Given("le fichier \"$path\"")
	public void file(final String path) throws FileNotFoundException {
		file = ResourceUtils.getFile(path);
	}

	@When("tondre la pelouse")
	public void tondre() {
		TondeuseMoniteur monitor = new TondeuseMonitorLoader().fromFile(file);
		tondeuses = monitor.getTondeuses();
		monitor.tondre();
	}
	
	@Then("la tondeuse $index doit etre postionnee au ($x, $y) et orientee vers $orientation")
	public void verifier(final int index, final int x, final int y, final Orientation orientation) {
		Tondeuse tondeuse = tondeuses.get(index - 1);
		assertEquals(x, tondeuse.position().getX());
		assertEquals(y, tondeuse.position().getY());
		assertEquals(orientation, tondeuse.getOrientation());
	}
}