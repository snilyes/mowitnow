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

  public static void main(final String[] args) {
    System.setProperty("server.port", System.getProperty("app.port", "8080"));
    SpringApplication.run(Application.class, args);
  }
}
