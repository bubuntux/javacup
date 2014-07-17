package org.dsaw.javacup.model.util;

import org.dsaw.javacup.model.geometry.Circle;
import org.dsaw.javacup.model.geometry.Rectangle;

/**
 * Created by Julio Gutierrez on 6/9/14.
 */
public final class ConstantsV2 extends Constants { //Everything is on centimeters

  public final static int METER_TO_CENTIMETER = 100; // 1 meter = 100 cm


  public final static Rectangle FIELD = new Rectangle((float) (ANCHO_CAMPO * METER_TO_CENTIMETER),
                                                      (float) (LARGO_CAMPO * METER_TO_CENTIMETER));

  public final static Rectangle GAME_FIELD =
      new Rectangle((float) (ANCHO_CAMPO_JUEGO * METER_TO_CENTIMETER),
                    (float) (LARGO_CAMPO_JUEGO * METER_TO_CENTIMETER));

  public final static Rectangle GAME_FIELD_1 =
      new Rectangle(GAME_FIELD.getX(), GAME_FIELD.getY(), GAME_FIELD.getWidth(),
                    GAME_FIELD.getHeight() / 2
      );
  public final static Rectangle GAME_FIELD_2 =
      new Rectangle(GAME_FIELD_1.getX(), GAME_FIELD_1.getY() + GAME_FIELD_1.getHeight(),
                    GAME_FIELD_1.getWidth(), GAME_FIELD_1.getHeight()
      );

  public final static Rectangle GOAL_AREA_1 =
      new Rectangle(GAME_FIELD.getY() - 2.44f * METER_TO_CENTIMETER, 7.32f * METER_TO_CENTIMETER,
                    2.44f * METER_TO_CENTIMETER
      );
  public final static Rectangle GOAL_AREA_2 =
      new Rectangle(GOAL_AREA_1.getX(),
                    GOAL_AREA_1.getY() + GAME_FIELD.getHeight() + GOAL_AREA_1.getHeight(),
                    GOAL_AREA_1.getWidth(), GOAL_AREA_1.getHeight()
      );

  public final static Rectangle SMALL_AREA_1 =
      new Rectangle(GAME_FIELD.getY(), (float) (LARGO_AREA_CHICA * METER_TO_CENTIMETER),
                    (float) (ANCHO_AREA_CHICA * METER_TO_CENTIMETER)
      );
  public final static Rectangle SMALL_AREA_2 =
      new Rectangle(SMALL_AREA_1.getX(),
                    SMALL_AREA_1.getY() + GAME_FIELD.getHeight() - SMALL_AREA_1.getHeight(),
                    SMALL_AREA_1.getWidth(), SMALL_AREA_1.getHeight()
      );

  public final static Rectangle BIG_AREA_1 =
      new Rectangle(GAME_FIELD.getY(), (float) (LARGO_AREA_GRANDE * METER_TO_CENTIMETER),
                    (float) (ANCHO_AREA_GRANDE * METER_TO_CENTIMETER)
      );
  public final static Rectangle BIG_AREA_2 =
      new Rectangle(BIG_AREA_1.getX(),
                    BIG_AREA_1.getY() + GAME_FIELD.getHeight() - BIG_AREA_1.getHeight(),
                    BIG_AREA_1.getWidth(), BIG_AREA_1.getHeight()
      );

  public final static float BALL_RADIUS = 11f;


  public final static Circle PENALTY_MARK_1 =
      new Circle(GAME_FIELD.getY() + (float) (DISTANCIA_PENAL * METER_TO_CENTIMETER), BALL_RADIUS);
  public final static Circle PENALTY_MARK_2 =
      new Circle(PENALTY_MARK_1.getX(),
                 GAME_FIELD.getY() + GAME_FIELD.getHeight() - (float) (DISTANCIA_PENAL
                                                                       * METER_TO_CENTIMETER),
                 PENALTY_MARK_1.getRadius());

  public final static Circle PENALTY_AREA_1 =
      new Circle(PENALTY_MARK_1.getX(), PENALTY_MARK_1.getY(),
                 (float) (RADIO_PENAL * METER_TO_CENTIMETER));
  public final static Circle PENALTY_AREA_2 =
      new Circle(PENALTY_MARK_2.getX(), PENALTY_MARK_2.getY(),
                 PENALTY_AREA_1.getRadius());

  public final static Circle CENTRAL_CIRCLE_MARK = new Circle(BALL_RADIUS);
  public final static Circle CENTRAL_CIRCLE_AREA =
      new Circle((float) (RADIO_CIRCULO_CENTRAL * METER_TO_CENTIMETER));

  public final static Circle CORNER_AREA_1 =
      new Circle(GAME_FIELD.getX(), GAME_FIELD.getY() + GAME_FIELD.getHeight(),
                 METER_TO_CENTIMETER);
  public final static Circle CORNER_AREA_2 =
      new Circle(GAME_FIELD.getX(), GAME_FIELD.getY(), CORNER_AREA_1.getRadius());
  public final static Circle CORNER_AREA_3 =
      new Circle(GAME_FIELD.getX() + GAME_FIELD.getWidth(),
                 GAME_FIELD.getY(), CORNER_AREA_1.getRadius());
  public final static Circle CORNER_AREA_4 =
      new Circle(GAME_FIELD.getX() + GAME_FIELD.getWidth(),
                 GAME_FIELD.getY() + GAME_FIELD.getHeight(), CORNER_AREA_1.getRadius());


}