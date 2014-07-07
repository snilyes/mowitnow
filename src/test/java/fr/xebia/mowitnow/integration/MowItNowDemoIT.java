package fr.xebia.mowitnow.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import fr.xebia.mowitnow.io.web.Application;

import static org.junit.Assert.assertTrue;

public class MowItNowDemoIT {

	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		// Démarrage serveur
		Application.main(new String[]{});
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/");
	}
		
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
	public void demo() throws InterruptedException {
    	driver.findElement(By.id("start")).click();
    	Thread.sleep(5000);
    	String result = driver.findElement(By.id("demo-result")).getText();
		assertTrue(result.contains("La tondeuse 1 => (0,1,WEST)"));
		assertTrue(result.contains("La tondeuse 2 => (3,2,EAST)"));		
		//assertThat(result, Matchers.contains("La tondeuse 1 => (0,1,WEST)", "La tondeuse 2 => (3,2,EAST)"));
	}
}
