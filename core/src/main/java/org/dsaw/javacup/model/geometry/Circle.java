package org.dsaw.javacup.model.geometry;

/**
 * Immutable Circle
 *
 * Created by Julio Gutierrez on 7/17/14.
 */
public class Circle {

  private final com.badlogic.gdx.math.Circle circle;

  public Circle(float x, float y, float radius) {
    circle = new com.badlogic.gdx.math.Circle(x, y, radius);
  }

  public Circle(float y, float radius) {
    this(0f, y, radius);
  }

  public Circle(float radius) {
    this(0f, radius);
  }

  public float getX() {
    return circle.x;
  }

  public float getY() {
    return circle.y;
  }

  public float getRadius() {
    return circle.radius;
  }

}
