package fr.xebia.mowitnow.io.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe permettant la configuration spring et le demarrage d'un serveur Spring Boot embarqué
 * 
 * @author ilyes
 * 
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

  private static final String CLOUD_BEES = "app.port";
  private static final String SPRING_BOOT_PORT = "server.port";

  public static void main(final String[] args) {
    System.setProperty(SPRING_BOOT_PORT, System.getProperty(CLOUD_BEES, "8080"));
    SpringApplication.run(Application.class, args);
  }
}
