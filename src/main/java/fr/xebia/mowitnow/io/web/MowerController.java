package fr.xebia.mowitnow.io.web;

import java.util.Observable;
import java.util.Observer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import fr.xebia.mowitnow.io.Loader;
import fr.xebia.mowitnow.mower.Monitor;
import fr.xebia.mowitnow.mower.Mower;

/**
 * Contolleur pour les échanges websocket surveillant les déplacements des tondeuses
 * 
 * @author ilyes
 * 
 */
@Controller
@Slf4j
public class MowerController implements Observer {

  @Autowired
  private MessageSendingOperations<String> messagingTemplate;

  private Monitor monitor;

  /**
   * Charge un moniteur et execute les instructions
   * 
   * @param message
   * @throws Exception
   */
  @MessageMapping("/execute")
  public void execute(final String message) throws Exception {

    // Charger le moniteur
    monitor = new Loader().fromText(message);

    // Communiquer les positions initiales (avant executions)
    notifyAndWait("/mowers/init", monitor);

    // Surveiller les tondeuses
    for (Mower mower : monitor.getMowers()) {
      mower.addObserver(this);
    }

    // Executer les instructions
    monitor.mow();
  }

  /**
   * Notifier un changement de position d'une tondeuse
   */
  @Override
  public void update(final Observable o, final Object arg) {
    notifyAndWait("/mowers/update", o);
  }

  /**
   * Communiquer une erreur survenue
   * 
   * @param e
   */
  @MessageExceptionHandler(Exception.class)
  public void error(final Exception e) {
    notifyAndWait("/mowers/error", ExceptionUtils.getRootCauseMessage(e));
    log.warn("Erreur!!! ", e);
  }


  @SneakyThrows
  private synchronized void notifyAndWait(String subscription, Object o) {
    this.messagingTemplate.convertAndSend(subscription, o);
    wait();
  }

  @MessageMapping("/receive")
  public synchronized void receive() {
    notify();
  }

}
