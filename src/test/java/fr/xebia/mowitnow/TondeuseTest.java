package fr.xebia.mowitnow;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;

import fr.xebia.mowitnow.TondeuseTest.Data.DataBuilder;
import static fr.xebia.mowitnow.TestUtil.avancer;
import static fr.xebia.mowitnow.TestUtil.droite;
import static fr.xebia.mowitnow.TestUtil.est;
import static fr.xebia.mowitnow.TestUtil.gauche;
import static fr.xebia.mowitnow.TestUtil.nord;
import static fr.xebia.mowitnow.TestUtil.sud;
import static fr.xebia.mowitnow.TestUtil.west;
import static java.util.Arrays.asList;

import static org.junit.Assert.assertEquals;


@RunWith(JUnitParamsRunner.class)
public class TondeuseTest {

	
	@Test
    @Parameters
	public void demarrerTest( final Data data ) {
		data.tondeuse.addObserver(data.pelouse);
		data.tondeuse.demarrer();  
		assertEquals(data.orientationAttendue, data.tondeuse.getOrientation());
		assertEquals(data.celluleAttendue, data.tondeuse.getCellule());
	}
	
    public Object[][] parametersForDemarrerTest() {
    	return new Object[][] {
			{DataBuilder.pelouse(2, 2).tondeuse(0, 0, est()).attendu(0, 0, est())},
			{DataBuilder.pelouse(2, 2).tondeuse(0, 0, est()).faire(droite()).attendu(0, 0, sud())},
			{DataBuilder.pelouse(2, 2).tondeuse(0, 0, est()).faire(gauche()).attendu(0, 0, nord())},
			{DataBuilder.pelouse(2, 2).tondeuse(0, 0, west()).faire(gauche(), gauche(), gauche(), gauche()).attendu(0, 0, west())},
			{DataBuilder.pelouse(5, 5).tondeuse(2, 2, west()).faire(droite(), avancer(), avancer(),  gauche(), avancer(), avancer(), gauche(), avancer(), gauche(), avancer()).attendu(1, 3, est())},
			
    	};
    }
    
    
	@lombok.Data
	static class Data {

    	private Pelouse pelouse;
    	private Tondeuse tondeuse;
    	private Cellule celluleAttendue;
    	private Orientation orientationAttendue;
    	
        Cellule on (final int x, final int y) {
        	return pelouse.cellule(x, y);
        }
        
	    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	    static class DataBuilder {
	    	
	    	final Data data = new Data();
	    	
	    	static DataBuilder pelouse (final int largeur, final int longueur) {
	    		DataBuilder builder =  new DataBuilder();
	    		builder.data.pelouse = new Pelouse(largeur, longueur);
	    		return builder;
	    	}
	    	
	    	DataBuilder tondeuse(final int x, final int y, final Orientation o) {
	    		data.tondeuse = new Tondeuse(data.on(x, y), o);
	    		return this;
	    	}
	
	    	DataBuilder faire(final Instruction ... instructions) {
	    		data.tondeuse.setInstructions(Lists.newLinkedList(asList(instructions)));
	    		return this;
	    	}
	    	
	    	Data attendu(final int x, final int y, final Orientation o) {
	    		data.celluleAttendue = data.on(x, y);
	    		data.orientationAttendue = o;
	    		return data;
	    	}
	    }
	}
}
