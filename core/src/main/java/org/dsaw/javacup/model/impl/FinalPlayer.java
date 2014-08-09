package org.dsaw.javacup.model.impl;

import org.dsaw.javacup.model.Player;

import java.awt.*;

/**
 * Created by Julio Gutierrez on 8/9/14.
 */
public final class FinalPlayer implements Player {

  private static final long serialVersionUID = -1600231280881041418L;

  private final Player player;

  public FinalPlayer(Player player) throws CloneNotSupportedException {
    this.player = player.clone();
  }

  @Override
  public String getName() {
    return player.getName();
  }

  @Override
  public int getNumber() {
    return player.getNumber();
  }

  @Override
  public boolean isGoalKeeper() {
    return player.isGoalKeeper();
  }

  @Override
  public Color getSkinColor() {
    return player.getSkinColor();
  }

  @Override
  public Color getHairColor() {
    return player.getHairColor();
  }

  @Override
  public double getSpeed() {
    return player.getSpeed();
  }

  @Override
  public double getPower() {
    return player.getPower();
  }

  @Override
  public double getPrecision() {
    return player.getPrecision();
  }

  @Override
  public Player clone() throws CloneNotSupportedException {
    return player.clone();
  }
}
