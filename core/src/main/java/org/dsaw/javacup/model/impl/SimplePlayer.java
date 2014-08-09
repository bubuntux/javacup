package org.dsaw.javacup.model.impl;

import org.dsaw.javacup.model.Player;

import java.awt.*;

/**
 * Created by Julio Gutierrez on 7/18/14.
 */
public class SimplePlayer implements Player {

  private static final long serialVersionUID = 8958536353628670286L;

  private String name;
  private int number;
  private boolean goalKeeper;
  private Color skin, hair;
  private float speed, power, precision;

  public void setName(String name) {
    this.name = name;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public void setGoalKeeper(boolean goalKeeper) {
    this.goalKeeper = goalKeeper;
  }

  public Color getSkin() {
    return skin;
  }

  public void setSkin(Color skin) {
    this.skin = skin;
  }

  public Color getHair() {
    return hair;
  }

  public void setHair(Color hair) {
    this.hair = hair;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public void setPower(float power) {
    this.power = power;
  }

  public void setPrecision(float precision) {
    this.precision = precision;
  }

  @Override
  public final String getName() {
    return name;
  }

  @Override
  public final int getNumber() {
    return number;
  }

  @Override
  public final boolean isGoalKeeper() {
    return goalKeeper;
  }

  @Override
  public final Color getSkinColor() {
    return skin;
  }

  @Override
  public final Color getHairColor() {
    return hair;
  }

  @Override
  public final double getSpeed() {
    return speed;
  }

  @Override
  public final double getPower() {
    return power;
  }

  @Override
  public final double getPrecision() {
    return precision;
  }

  @Override
  public Player clone() throws CloneNotSupportedException {
    return null;
  }
}
