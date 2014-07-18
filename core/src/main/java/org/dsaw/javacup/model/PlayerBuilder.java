package org.dsaw.javacup.model;

/**
 * Created by Julio Gutierrez on 7/18/14.
 */
public class PlayerBuilder { //TODO elaborate

  private String name;
  private int number;
  private boolean goalKeeper;
  private float speed, power, precision;

  public PlayerBuilder() {
    reset();
  }

  public String getName() {
    return name;
  }

  public PlayerBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public int getNumber() {
    return number;
  }

  public PlayerBuilder setNumber(int number) {
    this.number = number;
    return this;
  }

  public boolean isGoalKeeper() {
    return goalKeeper;
  }

  public PlayerBuilder setGoalKeeper(boolean goalKeeper) {
    this.goalKeeper = goalKeeper;
    return this;
  }

  public float getSpeed() {
    return speed;
  }

  public PlayerBuilder setSpeed(float speed) {
    this.speed = speed;
    return this;
  }

  public float getPower() {
    return power;
  }

  public PlayerBuilder setPower(float power) {
    this.power = power;
    return this;
  }

  public float getPrecision() {
    return precision;
  }

  public PlayerBuilder setPrecision(float precision) {
    this.precision = precision;
    return this;
  }

  public void reset() {
    //TODO
  }

  public Player buildRest() {
    Player player = build();
    reset();
    return player;
  }

  public Player build() {
    return new Player(name, number, goalKeeper, null, null, speed, power, precision);
  }
}
