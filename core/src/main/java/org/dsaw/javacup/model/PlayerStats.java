package org.dsaw.javacup.model;

/**
 * Created by Julio Gutierrez on 8/10/14.
 */
public class PlayerStats {

  public static final float MAX = 10;
  public static final float AVERAGE = MAX / 2;
  public static final float MIN = 0;

  public static final PlayerStats DEFAULT = new PlayerStats(AVERAGE, AVERAGE, AVERAGE);

  //TODO float or double?
  private final float speed;
  private final float power;
  private final float precision;

  public PlayerStats(float speed, float power, float precision) { //TODO error message!
    if (speed < MIN || MAX < speed) {
      throw new IllegalArgumentException();
    }
    this.speed = speed;
    if (power < MIN || MAX < power) {
      throw new IllegalArgumentException();
    }
    this.power = power;
    if (precision < MIN || MAX < precision) {
      throw new IllegalArgumentException();
    }
    this.precision = precision;
  }

  public final float getSpeed() {
    return speed;
  }

  public final float getPower() {
    return power;
  }

  public final float getPrecision() {
    return precision;
  }

}
