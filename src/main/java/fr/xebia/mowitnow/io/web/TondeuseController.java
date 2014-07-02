package fr.xebia.mowitnow.io.web;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import fr.xebia.mowitnow.io.TondeuseMonitorLoader;
import fr.xebia.mowitnow.tonte.Tondeuse;
import fr.xebia.mowitnow.tonte.TondeuseMoniteur;

@Controller
public class TondeuseController implements Observer {

  @Autowired
  private MessageSendingOperations<String> messagingTemplate;

  private TondeuseMoniteur moniteur;


  @MessageMapping("/hello")
  public void greeting(final String message) throws Exception {
    moniteur = new TondeuseMonitorLoader().fromText(message);
    for (Tondeuse tondeuse : moniteur.getTondeuses()) {
      tondeuse.addObserver(this);
    }
    this.messagingTemplate.convertAndSend("/topic/load", moniteur);
    moniteur.tondre();
  }

  @Override
  public void update(final Observable o, final Object arg) {
    this.messagingTemplate.convertAndSend("/topic/greetings", moniteur);
  }
}
