package fr.xebia.mowitnow.mower;

/**
 * Represente l'ensemble des instructions prévues
 * 
 * @author ilyes
 *
 */
public enum Instruction {

  ROTATE_RIGHT {
    @Override
    public void executer(final Mower tondeuse) {
      tondeuse.rotateRight();
    }
  },

  ROTATE_LEFT {
    @Override
    public void executer(final Mower tondeuse) {
      tondeuse.rotateLeft();
    }
  },

  FORWARD {
    @Override
    public void executer(final Mower tondeuse) {
      tondeuse.advance();
    }
  };

  abstract void executer(final Mower tondeuse);
}
