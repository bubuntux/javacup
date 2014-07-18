package org.dsaw.javacup.model;

import java.awt.*;

/**
 * Created by Julio Gutierrez on 7/18/14.
 */
public class Player implements PlayerI {

  private final String name;
  private final int number;
  private final boolean goalKeeper;
  private final Color skin, hair;
  private final float speed, power, precision;

  public Player(String name, int number, boolean goalKeeper, Color skin, Color hair, float speed,
                float power, float precision) {
    this.name = name;
    this.number = number;
    this.goalKeeper = goalKeeper;
    this.skin = skin;
    this.hair = hair;
    this.speed = speed;
    this.power = power;
    this.precision = precision;
  }

  public final String getName() {
    return name;
  }

  public final int getNumber() {
    return number;
  }

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
}
