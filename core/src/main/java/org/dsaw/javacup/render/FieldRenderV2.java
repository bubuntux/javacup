package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_WIDTH_HALF;
import static org.dsaw.javacup.model.util.ConstantsV2.CENTRAL_CIRCLE_MARK_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.CENTRAL_CIRCLE_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.CORNER_MARK_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.FIELD_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.FIELD_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_HEIGHT_HALF;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_WIDTH_HALF;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_WIDTH_HALF;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_DISTANCE;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_MARK_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_WIDTH_HALF;


/**
 * Created by jgutierrez on 6/9/14.
 */
public class FieldRenderV2 {

  private static final float GAME_FIELD_X = (FIELD_WIDTH - GAME_FIELD_WIDTH) / 2;
  private static final float GAME_FIELD_X2 = GAME_FIELD_X + GAME_FIELD_WIDTH;
  private static final float GAME_FIELD_X_HALF = GAME_FIELD_X + GAME_FIELD_WIDTH_HALF;
  private static final float BIG_AREA_X = GAME_FIELD_X_HALF - BIG_AREA_WIDTH_HALF;
  private static final float SMALL_AREA_X = GAME_FIELD_X_HALF - SMALL_AREA_WIDTH_HALF;
  private static final float GOAL_AREA_X = GAME_FIELD_X_HALF - GOAL_AREA_WIDTH_HALF;
  private static final float GAME_FIELD_Y = (FIELD_HEIGHT - GAME_FIELD_HEIGHT) / 2;
  private static final float GAME_FIELD_Y2 = GAME_FIELD_Y + GAME_FIELD_HEIGHT;
  private static final float GAME_FIELD_Y_HALF = GAME_FIELD_Y + GAME_FIELD_HEIGHT_HALF;
  private static final float PENALTY_MARK_Y1 = GAME_FIELD_Y + PENALTY_DISTANCE;
  private static final float PENALTY_MARK_Y2 = GAME_FIELD_Y + GAME_FIELD_HEIGHT - PENALTY_DISTANCE;
  private static final float BIG_AREA_Y = GAME_FIELD_Y + GAME_FIELD_HEIGHT - BIG_AREA_HEIGHT;
  private static final float SMALL_AREA_Y = GAME_FIELD_Y + GAME_FIELD_HEIGHT - SMALL_AREA_HEIGHT;
  private static final float GOAL_AREA_Y = GAME_FIELD_Y + GAME_FIELD_HEIGHT;
  private static final float GOAL_AREA_Y2 = GAME_FIELD_Y - GOAL_AREA_HEIGHT;

  public FieldRenderV2() {

  }

  public void draw(ShapeRenderer shapeRenderer) {
    //Render all the grass
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(0, 1, 0, 1);
    shapeRenderer.rect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);
    shapeRenderer.end();

    //Render game field
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(1, 1, 1, 1);
    shapeRenderer.rect(GAME_FIELD_X, GAME_FIELD_Y, GAME_FIELD_WIDTH,
                       GAME_FIELD_HEIGHT);

    //Penalty circle
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y1, PENALTY_RADIUS);
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y2, PENALTY_RADIUS);
    shapeRenderer.end();

    //Filled big area (so remove inside penalty circle)
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(0, 1, 0, 1);

    shapeRenderer.rect(BIG_AREA_X, GAME_FIELD_Y, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);
    shapeRenderer.rect(BIG_AREA_X, BIG_AREA_Y, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);

    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(1, 1, 1, 1);

    //Render small area
    shapeRenderer.rect(SMALL_AREA_X, GAME_FIELD_Y, SMALL_AREA_WIDTH, SMALL_AREA_HEIGHT);
    shapeRenderer.rect(SMALL_AREA_X, SMALL_AREA_Y, SMALL_AREA_WIDTH, SMALL_AREA_HEIGHT);

    //Render big area
    shapeRenderer.rect(BIG_AREA_X, GAME_FIELD_Y, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);
    shapeRenderer.rect(BIG_AREA_X, BIG_AREA_Y, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);

    //Half field
    shapeRenderer.line(GAME_FIELD_X, GAME_FIELD_Y_HALF, GAME_FIELD_X2, GAME_FIELD_Y_HALF);
    shapeRenderer.circle(GAME_FIELD_X_HALF, GAME_FIELD_Y_HALF, CENTRAL_CIRCLE_RADIUS);

    //render Goals
    shapeRenderer.rect(GOAL_AREA_X, GOAL_AREA_Y, GOAL_AREA_WIDTH, GOAL_AREA_HEIGHT);
    shapeRenderer.rect(GOAL_AREA_X, GOAL_AREA_Y2, GOAL_AREA_WIDTH, GOAL_AREA_HEIGHT);

    //Corners
    shapeRenderer.arc(GAME_FIELD_X, GAME_FIELD_Y, CORNER_MARK_RADIUS, 0f, 90f);
    shapeRenderer.arc(GAME_FIELD_X2, GAME_FIELD_Y, CORNER_MARK_RADIUS, 90f, 90f);
    shapeRenderer.arc(GAME_FIELD_X2, GAME_FIELD_Y2, CORNER_MARK_RADIUS, 180f, 90f);
    shapeRenderer.arc(GAME_FIELD_X, GAME_FIELD_Y2, CORNER_MARK_RADIUS, 270f, 90f);
    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.circle(GAME_FIELD_X_HALF, GAME_FIELD_Y_HALF, CENTRAL_CIRCLE_MARK_RADIUS);
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y1, PENALTY_MARK_RADIUS);
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y2, PENALTY_MARK_RADIUS);
    shapeRenderer.end();
  }
}
