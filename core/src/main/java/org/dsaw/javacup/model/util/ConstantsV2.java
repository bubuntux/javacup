package org.dsaw.javacup.model.util;

/**
 * Created by jgutierrez on 6/9/14.
 */
public final class ConstantsV2 extends Constants {

  public final static int SCALE = 100; //100 cm = 1 meter

  //TODO just space between game_field and advertise_field
  public final static float FIELD_WIDTH = (float) (ANCHO_CAMPO * SCALE);
  public final static float FIELD_WIDTH_HALF = FIELD_WIDTH / 2;
  public final static float FIELD_HEIGHT = (float) (LARGO_CAMPO * SCALE);
  public final static float FIELD_HEIGHT_HALF = FIELD_HEIGHT / 2;

  public final static float GAME_FIELD_WIDTH = (float) (ANCHO_CAMPO_JUEGO * SCALE);
  public final static float GAME_FIELD_WIDTH_HALF = GAME_FIELD_WIDTH / 2;
  public final static float GAME_FIELD_HEIGHT = (float) (LARGO_CAMPO_JUEGO * SCALE);
  public final static float GAME_FIELD_HEIGHT_HALF = GAME_FIELD_HEIGHT / 2;

  public final static float PENALTY_DISTANCE = (float) (DISTANCIA_PENAL * SCALE);
  public final static float PENALTY_MARK_RADIUS = 0.15f * SCALE; //TODO research
  public final static float PENALTY_RADIUS = (float) (RADIO_PENAL * SCALE);

  public final static float CENTRAL_CIRCLE_RADIUS = PENALTY_RADIUS;
  public final static float CENTRAL_CIRCLE_MARK_RADIUS = PENALTY_MARK_RADIUS; //TODO research

  public final static float CORNER_MARK_RADIUS = SCALE;

  public final static float GOAL_AREA_WIDTH = 7.32f * SCALE;
  public final static float GOAL_AREA_WIDTH_HALF = GOAL_AREA_WIDTH / 2;
  public final static float GOAL_AREA_HEIGHT = 2.44f * SCALE;
  public final static float GOAL_AREA_HEIGHT_HALF = GOAL_AREA_HEIGHT / 2;

  public final static float SMALL_AREA_WIDTH = (float) (LARGO_AREA_CHICA * SCALE);
  public final static float SMALL_AREA_WIDTH_HALF = SMALL_AREA_WIDTH / 2;
  public final static float SMALL_AREA_HEIGHT = (float) (ANCHO_AREA_CHICA * SCALE);
  public final static float SMALL_AREA_HEIGHT_HALF = SMALL_AREA_HEIGHT / 2;


  public final static float BIG_AREA_WIDTH = (float) (LARGO_AREA_GRANDE * SCALE);
  public final static float BIG_AREA_WIDTH_HALF = BIG_AREA_WIDTH / 2;
  public final static float BIG_AREA_HEIGHT = (float) (ANCHO_AREA_GRANDE * SCALE);
  public final static float BIG_AREA_HEIGHT_HALF = BIG_AREA_HEIGHT / 2;
}