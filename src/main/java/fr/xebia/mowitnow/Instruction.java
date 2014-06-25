package fr.xebia.mowitnow;

public enum Instruction {

  PIVOTER_DROITE {
    @Override
    public void executer(final Tondeuse tondeuse) {
      tondeuse.pivoterDroite();
    }
  },

  PIVOTER_GAUCHE {
    @Override
    public void executer(final Tondeuse tondeuse) {
      tondeuse.pivoterGauche();
    }
  },

  AVANCER {
    @Override
    public void executer(final Tondeuse tondeuse) {
      tondeuse.avancer();
    }
  };

  abstract void executer(final Tondeuse tondeuse);
}
