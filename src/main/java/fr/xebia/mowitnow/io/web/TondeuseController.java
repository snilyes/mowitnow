package fr.xebia.mowitnow.io.web;

import java.util.Observable;
import java.util.Observer;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import fr.xebia.mowitnow.io.TondeuseMonitorLoader;
import fr.xebia.mowitnow.tonte.Tondeuse;
import fr.xebia.mowitnow.tonte.TondeuseMoniteur;

@Controller
@Slf4j
public class TondeuseController implements Observer {

  @Autowired
  private MessageSendingOperations<String> messagingTemplate;

  private TondeuseMoniteur moniteur;


  @MessageMapping("/execute")
  public void execute(final String message) throws Exception {
    moniteur = new TondeuseMonitorLoader().fromText(message);
    for (Tondeuse tondeuse : moniteur.getTondeuses()) {
      tondeuse.addObserver(this);
    }
    this.messagingTemplate.convertAndSend("/mowers/init", moniteur);
    moniteur.tondre();
  }

  @Override
  public void update(final Observable o, final Object arg) {
    this.messagingTemplate.convertAndSend("/mowers/update", moniteur);
  }
  
  @MessageExceptionHandler(Exception.class)
  public void erreur(final Exception e) {
	this.messagingTemplate.convertAndSend("/mowers/error", ExceptionUtils.getRootCauseMessage(e));
	log.warn("Erreur!!! ", e);
  }
}
