package org.dsaw.javacup.model.util;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Julio Gutierrez on 6/9/14.
 */
public final class ConstantsV2 extends Constants { //Everything is on centimeters

  public final static int METER_TO_CENTIMETER = 100; // 1 meter = 100 cm


  public final static Rect FIELD = new Rect((float) (ANCHO_CAMPO * METER_TO_CENTIMETER),
                                            (float) (LARGO_CAMPO * METER_TO_CENTIMETER));

  public final static Rect GAME_FIELD = new Rect((float) (ANCHO_CAMPO_JUEGO * METER_TO_CENTIMETER),
                                                 (float) (LARGO_CAMPO_JUEGO * METER_TO_CENTIMETER));

  public final static Rect GAME_FIELD_1 =
      new Rect(GAME_FIELD.getX(), GAME_FIELD.getY(), GAME_FIELD.getWidth(),
               GAME_FIELD.getHeight() / 2
      );
  public final static Rect GAME_FIELD_2 =
      new Rect(GAME_FIELD_1.getX(), GAME_FIELD_1.getY() + GAME_FIELD_1.getHeight(),
               GAME_FIELD_1.getWidth(), GAME_FIELD_1.getHeight()
      );

  public final static Rect GOAL_AREA_1 =
      new Rect(GAME_FIELD.getY() - 2.44f * METER_TO_CENTIMETER, 7.32f * METER_TO_CENTIMETER,
               2.44f * METER_TO_CENTIMETER
      );
  public final static Rect GOAL_AREA_2 =
      new Rect(GOAL_AREA_1.getX(),
               GOAL_AREA_1.getY() + GAME_FIELD.getHeight() + GOAL_AREA_1.getHeight(),
               GOAL_AREA_1.getWidth(), GOAL_AREA_1.getHeight()
      );

  public final static Rect SMALL_AREA_1 =
      new Rect(GAME_FIELD.getY(), (float) (LARGO_AREA_CHICA * METER_TO_CENTIMETER),
               (float) (ANCHO_AREA_CHICA * METER_TO_CENTIMETER)
      );
  public final static Rect SMALL_AREA_2 =
      new Rect(SMALL_AREA_1.getX(),
               SMALL_AREA_1.getY() + GAME_FIELD.getHeight() - SMALL_AREA_1.getHeight(),
               SMALL_AREA_1.getWidth(), SMALL_AREA_1.getHeight()
      );

  public final static Rect BIG_AREA_1 =
      new Rect(GAME_FIELD.getY(), (float) (LARGO_AREA_GRANDE * METER_TO_CENTIMETER),
               (float) (ANCHO_AREA_GRANDE * METER_TO_CENTIMETER)
      );
  public final static Rect BIG_AREA_2 =
      new Rect(BIG_AREA_1.getX(),
               BIG_AREA_1.getY() + GAME_FIELD.getHeight() - BIG_AREA_1.getHeight(),
               BIG_AREA_1.getWidth(), BIG_AREA_1.getHeight()
      );

  public final static float BALL_RADIUS = 11f;


  public final static Circ PENALTY_MARK_1 =
      new Circ(GAME_FIELD.getY() + (float) (DISTANCIA_PENAL * METER_TO_CENTIMETER), BALL_RADIUS);
  public final static Circ PENALTY_MARK_2 =
      new Circ(PENALTY_MARK_1.getX(),
               GAME_FIELD.getY() + GAME_FIELD.getHeight() - (float) (DISTANCIA_PENAL
                                                                     * METER_TO_CENTIMETER),
               PENALTY_MARK_1.getRadius());

  public final static Circ PENALTY_AREA_1 =
      new Circ(PENALTY_MARK_1.getX(), PENALTY_MARK_1.getY(),
               (float) (RADIO_PENAL * METER_TO_CENTIMETER));
  public final static Circ PENALTY_AREA_2 =
      new Circ(PENALTY_MARK_2.getX(), PENALTY_MARK_2.getY(),
               PENALTY_AREA_1.getRadius());

  public final static Circ CENTRAL_CIRCLE_MARK = new Circ(BALL_RADIUS);
  public final static Circ CENTRAL_CIRCLE_AREA =
      new Circ((float) (RADIO_CIRCULO_CENTRAL * METER_TO_CENTIMETER));

  public final static Circ CORNER_AREA_1 =
      new Circ(GAME_FIELD.getX(), GAME_FIELD.getY() + GAME_FIELD.getHeight(),
               METER_TO_CENTIMETER);
  public final static Circ CORNER_AREA_2 =
      new Circ(GAME_FIELD.getX(), GAME_FIELD.getY(), CORNER_AREA_1.getRadius());
  public final static Circ CORNER_AREA_3 =
      new Circ(GAME_FIELD.getX() + GAME_FIELD.getWidth(),
               GAME_FIELD.getY(), CORNER_AREA_1.getRadius());
  public final static Circ CORNER_AREA_4 =
      new Circ(GAME_FIELD.getX() + GAME_FIELD.getWidth(),
               GAME_FIELD.getY() + GAME_FIELD.getHeight(), CORNER_AREA_1.getRadius());

  public final static class Rect { //TODO relocate?

    private final Rectangle rectangle;

    private Rect(float x, float y, float width, float height) {
      rectangle = new Rectangle(x, y, width, height);
    }

    private Rect(float y, float width, float height) {
      this(width / -2f, y, width, height);
    }

    private Rect(float width, float height) {
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

  public final static class Circ {

    private final Circle circle;

    private Circ(float x, float y, float radius) {
      circle = new Circle(x, y, radius);
    }

    private Circ(float y, float radius) {
      this(0f, y, radius);
    }

    private Circ(float radius) {
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

}