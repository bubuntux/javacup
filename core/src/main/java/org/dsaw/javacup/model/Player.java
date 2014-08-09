package org.dsaw.javacup.model;

import java.awt.*;
import java.io.Serializable;

/**
 * Player information
 */
public interface Player extends Cloneable, Serializable {

  public String getName();

  public int getNumber();

  public boolean isGoalKeeper();

  //// TODO Merge into a class  *******************
  public Color getSkinColor();

  public Color getHairColor();
  //// TODO Merge into a class  *******************


  //// TODO Merge into a class  *******************
  public double getSpeed();

  public double getPower();

  public double getPrecision();
  //// TODO Merge into a class  *******************

  public Player clone() throws CloneNotSupportedException;
}
