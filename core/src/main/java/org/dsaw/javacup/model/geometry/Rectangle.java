package org.dsaw.javacup.model.geometry;

/**
 * Immutable rectangle
 *
 * Created by Julio Gutierrez on 7/17/14.
 */
public class Rectangle {

  private final com.badlogic.gdx.math.Rectangle rectangle;

  public Rectangle(float x, float y, float width, float height) {
    rectangle = new com.badlogic.gdx.math.Rectangle(x, y, width, height);
  }

  public Rectangle(float y, float width, float height) {
    this(width / -2f, y, width, height);
  }

  public Rectangle(float width, float height) {
    this(height / -2f, width, height);
  }

  public float getX() {
    return rectangle.x;
  }

  public float getY() {
    return rectangle.y;
  }

  public float getWidth() {
    return rectangle.width;
  }

  public float getHeight() {
    return rectangle.height;
  }

}
