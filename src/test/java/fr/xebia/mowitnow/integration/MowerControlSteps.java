package fr.xebia.mowitnow.integration;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import fr.xebia.mowitnow.base.Orientation;
import fr.xebia.mowitnow.base.Position;
import fr.xebia.mowitnow.io.parseur.InstructionParseur;
import fr.xebia.mowitnow.tonte.Pelouse;
import fr.xebia.mowitnow.tonte.Tondeuse;

import static org.junit.Assert.assertEquals;

public class MowerControlSteps {

	private Pelouse pelouse;
	
	private Tondeuse tondeuse;
	
	@Given("une pelouse de $largeur sur $longueur")
	public void pelouse(@Named("largeur") final int largeur, @Named("longueur") final int longueur) {
		pelouse = new Pelouse(largeur, longueur);
	}

	@Given("une tondeuse coordonnee au ($x1, $y1) et orientee vers $o1")
	public void tondeuse(@Named("x1") final int x, @Named("y1") final int y, @Named("o1") final Orientation orientation) {
		tondeuse = new Tondeuse(pelouse.cellule(new Position(x, y)), orientation);
	}
	
	@When("la tondeuse execute les instructions suivantes : $instructions")
	public void execute(@Named("instructions")final String instructions) {
		tondeuse.setInstructions(new InstructionParseur().parse(instructions));
		tondeuse.demarrer();
	}
	
	@Then("la tondeuse doit etre postionnee au ($x2, $y2) et orientee vers $o2")
	public void verifier(@Named("x2") final int x,@Named("y2") final int y,@Named("o2") final Orientation orientation) {
		assertEquals(x, tondeuse.position().getX());
		assertEquals(y, tondeuse.position().getY());
		assertEquals(orientation, tondeuse.getOrientation());
	}
}
