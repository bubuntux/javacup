package org.dsaw.javacup.model;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

/**
 * Team information
 */
public interface Team {

  public String getName();

  public CountryCode getCountryCode();

  public String getCoachName();

  public PlayerI[] getPlayers(); //TODO array or collection???

  //// TODO Merge into a class  *******************
  public Color getShirtColor();

  public Color getShortsColor();

  public Color getShirtLineColor();

  public Color getSocksColor();

  public Color getGoalKeeper();

  public UniformStyle getStyle();

  public Color getShirtColor2();

  public Color getShortsColor2();

  public Color getShirtLineColor2();

  public Color getSocksColor2();

  public Color getGoalKeeper2();

  public UniformStyle getStyle2();
//// TODO  Merge into a class  *******************

}
