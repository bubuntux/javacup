package org.dsaw.javacup.tactics.jvc2013.emandem.utils;

import org.dsaw.javacup.model.util.Position;

public class Recta {

  Position punto1;
  Position punto2;

  public Recta() {

  }

  public Recta(final Position punto1, final Position punto2) {
    this.punto1 = punto1;
    this.punto2 = punto2;
  }

  public Position getPunto1() {
    return punto1;
  }


  public void setPunto1(Position punto1) {
    this.punto1 = punto1;
  }


  public Position getPunto2() {
    return punto2;
  }


  public void setPunto2(Position punto2) {
    this.punto2 = punto2;
  }

}