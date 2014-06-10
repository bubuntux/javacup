package org.dsaw.javacup.model.util;

/**
 * Created by Julio Gutierrez on 6/9/14.
 */
public final class ConstantsV2 extends Constants { //Everything is on centimeters

  public final static int METER_TO_CENTIMETER = 100; // 1 meter = 100 cm

  //TODO just space between game_field and advertise_field
  public final static float FIELD_WIDTH = (float) (ANCHO_CAMPO * METER_TO_CENTIMETER);
  public final static float FIELD_WIDTH_HALF = FIELD_WIDTH / 2;
  public final static float FIELD_HEIGHT = (float) (LARGO_CAMPO * METER_TO_CENTIMETER);
  public final static float FIELD_HEIGHT_HALF = FIELD_HEIGHT / 2;

  public final static float GAME_FIELD_WIDTH = (float) (ANCHO_CAMPO_JUEGO * METER_TO_CENTIMETER);
  public final static float GAME_FIELD_WIDTH_HALF = GAME_FIELD_WIDTH / 2;
  public final static float GAME_FIELD_HEIGHT = (float) (LARGO_CAMPO_JUEGO * METER_TO_CENTIMETER);
  public final static float GAME_FIELD_HEIGHT_HALF = GAME_FIELD_HEIGHT / 2;

  public final static float PENALTY_DISTANCE = (float) (DISTANCIA_PENAL * METER_TO_CENTIMETER);
  public final static float PENALTY_MARK_RADIUS = 15f; //TODO research
  public final static float PENALTY_RADIUS = (float) (RADIO_PENAL * METER_TO_CENTIMETER);

  public final static float CENTRAL_CIRCLE_MARK_RADIUS = PENALTY_MARK_RADIUS; //TODO research
  public final static float CENTRAL_CIRCLE_RADIUS = PENALTY_RADIUS;

  public final static float CORNER_MARK_RADIUS = METER_TO_CENTIMETER;

  public final static float GOAL_AREA_WIDTH = 7.32f * METER_TO_CENTIMETER;
  public final static float GOAL_AREA_WIDTH_HALF = GOAL_AREA_WIDTH / 2;
  public final static float GOAL_AREA_HEIGHT = 2.44f * METER_TO_CENTIMETER;
  public final static float GOAL_AREA_HEIGHT_HALF = GOAL_AREA_HEIGHT / 2;

  public final static float SMALL_AREA_WIDTH = (float) (LARGO_AREA_CHICA * METER_TO_CENTIMETER);
  public final static float SMALL_AREA_WIDTH_HALF = SMALL_AREA_WIDTH / 2;
  public final static float SMALL_AREA_HEIGHT = (float) (ANCHO_AREA_CHICA * METER_TO_CENTIMETER);
  public final static float SMALL_AREA_HEIGHT_HALF = SMALL_AREA_HEIGHT / 2;

  public final static float BIG_AREA_WIDTH = (float) (LARGO_AREA_GRANDE * METER_TO_CENTIMETER);
  public final static float BIG_AREA_WIDTH_HALF = BIG_AREA_WIDTH / 2;
  public final static float BIG_AREA_HEIGHT = (float) (ANCHO_AREA_GRANDE * METER_TO_CENTIMETER);
  public final static float BIG_AREA_HEIGHT_HALF = BIG_AREA_HEIGHT / 2;

  public final static float BALL_RADIUS = 11f;
}