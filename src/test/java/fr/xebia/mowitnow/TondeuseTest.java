package fr.xebia.mowitnow;

import java.util.Arrays;
import java.util.Collection;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class TondeuseTest {

	Pelouse pelouse = new Pelouse(5, 5);
	
	@Test
    @Parameters
	public void demarrerTest(final Tondeuse tondeuse, final Cellule celluleAttendue, final Orientation orientationAttendue, final Collection<Instruction> instructions) {
		if (instructions != null) {
			tondeuse.setInstructions(Lists.newLinkedList(instructions));
		}
		tondeuse.demarrer();  
		assertEquals(tondeuse.getOrientation(), orientationAttendue);
		Assert.assertEquals(tondeuse.getCellule(), celluleAttendue);
	}
	
    public Object[][] parametersForDemarrerTest() {
    	return new Object[][] {
    			{new Tondeuse(on(0, 0), est()), on(0, 0), est(), null},
    			{new Tondeuse(on(0, 0), est()), on(0, 0), sud(), Arrays.asList(d())}
    	};
    }
    
    private Cellule on (final int x, final int y) {
    	return pelouse.cellule(x, y);
    }
    
    private Orientation est() {
    	return Orientation.EST;
    }
    private Orientation west() {
    	return Orientation.WEST;
    }
    private Orientation sud() {
    	return Orientation.SUD;
    }
    private Orientation nord() {
    	return Orientation.NORD;
    }
    
    private Instruction d() {
    	return Instruction.PIVOTER_DROITE;
    }
    
    private Instruction g() {
    	return Instruction.PIVOTER_GAUCHE;
    }

    private Instruction a() {
    	return Instruction.AVANCER;
    }
}
