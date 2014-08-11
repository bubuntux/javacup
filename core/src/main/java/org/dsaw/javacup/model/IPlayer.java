package org.dsaw.javacup.model;

import java.awt.*;

/**
 * Player information
 */
public interface IPlayer {

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

}
