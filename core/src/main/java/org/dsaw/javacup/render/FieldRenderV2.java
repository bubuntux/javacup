package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_X_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_Y_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.CENTRAL_CIRCLE_MARK_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.CENTRAL_CIRCLE_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.CORNER_MARK_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.FIELD_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.FIELD_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_X_END;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_X_HALF;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_X_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_Y_END;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_Y_HALF;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_Y_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_X_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_Y2_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_Y_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_MARK_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_MARK_Y1;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_MARK_Y2;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_RADIUS;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_HEIGHT;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_WIDTH;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_X_INIT;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_Y_INIT;

/**
 * Created by Julio Gutierrez on 6/9/14.
 */
public class FieldRenderV2 {

  public static final Color GRASS = new Color(0.3f, 0.7f, 0.3f, 1);

  public void draw(ShapeRenderer shapeRenderer) { //TODO different green grass
    //Render all the grass
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(GRASS);
    shapeRenderer.rect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);
    shapeRenderer.end();

    //Render game field
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.WHITE);
    shapeRenderer.rect(GAME_FIELD_X_INIT, GAME_FIELD_Y_INIT, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);

    //Penalty circle
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y1, PENALTY_RADIUS);
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y2, PENALTY_RADIUS);
    shapeRenderer.end();

    //Filled big area (so remove inside penalty circle)
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(GRASS);

    shapeRenderer.rect(BIG_AREA_X_INIT, GAME_FIELD_Y_INIT, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);
    shapeRenderer.rect(BIG_AREA_X_INIT, BIG_AREA_Y_INIT, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);

    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.WHITE);

    //Render small area
    shapeRenderer.rect(SMALL_AREA_X_INIT, GAME_FIELD_Y_INIT, SMALL_AREA_WIDTH, SMALL_AREA_HEIGHT);
    shapeRenderer.rect(SMALL_AREA_X_INIT, SMALL_AREA_Y_INIT, SMALL_AREA_WIDTH, SMALL_AREA_HEIGHT);

    //Render big area
    shapeRenderer.rect(BIG_AREA_X_INIT, GAME_FIELD_Y_INIT, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);
    shapeRenderer.rect(BIG_AREA_X_INIT, BIG_AREA_Y_INIT, BIG_AREA_WIDTH, BIG_AREA_HEIGHT);

    //Half field
    shapeRenderer.line(GAME_FIELD_X_INIT, GAME_FIELD_Y_HALF, GAME_FIELD_X_END, GAME_FIELD_Y_HALF);
    shapeRenderer.circle(GAME_FIELD_X_HALF, GAME_FIELD_Y_HALF, CENTRAL_CIRCLE_RADIUS);

    //render Goals
    shapeRenderer.rect(GOAL_AREA_X_INIT, GOAL_AREA_Y_INIT, GOAL_AREA_WIDTH, GOAL_AREA_HEIGHT);
    shapeRenderer.rect(GOAL_AREA_X_INIT, GOAL_AREA_Y2_INIT, GOAL_AREA_WIDTH, GOAL_AREA_HEIGHT);

    //Corners
    shapeRenderer.arc(GAME_FIELD_X_INIT, GAME_FIELD_Y_INIT, CORNER_MARK_RADIUS, 0f, 90f);
    shapeRenderer.arc(GAME_FIELD_X_END, GAME_FIELD_Y_INIT, CORNER_MARK_RADIUS, 90f, 90f);
    shapeRenderer.arc(GAME_FIELD_X_END, GAME_FIELD_Y_END, CORNER_MARK_RADIUS, 180f, 90f);
    shapeRenderer.arc(GAME_FIELD_X_INIT, GAME_FIELD_Y_END, CORNER_MARK_RADIUS, 270f, 90f);
    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.circle(GAME_FIELD_X_HALF, GAME_FIELD_Y_HALF, CENTRAL_CIRCLE_MARK_RADIUS);
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y1, PENALTY_MARK_RADIUS);
    shapeRenderer.circle(GAME_FIELD_X_HALF, PENALTY_MARK_Y2, PENALTY_MARK_RADIUS);
    shapeRenderer.end();
  }
}
