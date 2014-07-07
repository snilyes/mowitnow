package fr.xebia.mowitnow.integration;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.io.parseur.InstructionParser;
import fr.xebia.mowitnow.mower.Lawn;
import fr.xebia.mowitnow.mower.Mower;
import static fr.xebia.mowitnow.base.Position.at;

import static org.junit.Assert.assertEquals;

public class MowerControlSteps {

	private Lawn lawn;
	
	private Mower mower;
	
	@Given("une pelouse de $largeur sur $longueur")
	public void lawn(@Named("largeur") final int width, @Named("longueur") final int height) {
		lawn = new Lawn(width, height);
	}

	@Given("une tondeuse coordonnee au ($x1, $y1) et orientee vers $o1")
	public void mower(@Named("x1") final int x, @Named("y1") final int y, @Named("o1") final Orientation orientation) {
		mower = new Mower(lawn.cellAt(at(x, y)), orientation);
	}
	
	@When("la tondeuse execute les instructions suivantes : $instructions")
	public void execute(@Named("instructions")final String instructions) {
		mower.setInstructions(new InstructionParser().parse(instructions));
		mower.start();
	}
	
	@Then("la tondeuse doit etre postionnee au ($x2, $y2) et orientee vers $o2")
	public void verify(@Named("x2") final int x,@Named("y2") final int y,@Named("o2") final Orientation orientation) {
		assertEquals(x, mower.position().getX());
		assertEquals(y, mower.position().getY());
		assertEquals(orientation, mower.getOrientation());
	}
}
