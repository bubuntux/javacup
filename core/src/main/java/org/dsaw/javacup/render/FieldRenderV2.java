package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_1;
import static org.dsaw.javacup.model.util.ConstantsV2.BIG_AREA_2;
import static org.dsaw.javacup.model.util.ConstantsV2.CENTRAL_CIRCLE_AREA;
import static org.dsaw.javacup.model.util.ConstantsV2.CENTRAL_CIRCLE_MARK;
import static org.dsaw.javacup.model.util.ConstantsV2.CORNER_AREA_1;
import static org.dsaw.javacup.model.util.ConstantsV2.CORNER_AREA_2;
import static org.dsaw.javacup.model.util.ConstantsV2.CORNER_AREA_3;
import static org.dsaw.javacup.model.util.ConstantsV2.CORNER_AREA_4;
import static org.dsaw.javacup.model.util.ConstantsV2.FIELD;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_1;
import static org.dsaw.javacup.model.util.ConstantsV2.GAME_FIELD_2;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_1;
import static org.dsaw.javacup.model.util.ConstantsV2.GOAL_AREA_2;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_AREA_1;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_AREA_2;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_MARK_1;
import static org.dsaw.javacup.model.util.ConstantsV2.PENALTY_MARK_2;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_1;
import static org.dsaw.javacup.model.util.ConstantsV2.SMALL_AREA_2;

/**
 * Created by Julio Gutierrez on 6/9/14.
 */
public class FieldRenderV2 {

  public static final Color GRASS = new Color(0.3f, 0.7f, 0.3f, 1);

  public void draw(ShapeRenderer shapeRenderer) { //TODO different green grass
    //Render all the grass
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(GRASS);
    shapeRenderer
        .rect(FIELD.getX(), FIELD.getY(), FIELD.getWidth(), FIELD.getHeight());
    shapeRenderer.end();

    //Render game field
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.WHITE);
    shapeRenderer
        .rect(GAME_FIELD_1.getX(), GAME_FIELD_1.getY(), GAME_FIELD_1.getWidth(),
              GAME_FIELD_1.getHeight());
    shapeRenderer
        .rect(GAME_FIELD_2.getX(), GAME_FIELD_2.getY(), GAME_FIELD_2.getWidth(),
              GAME_FIELD_2.getHeight());

    //Penalty circle
    shapeRenderer.circle(PENALTY_AREA_1.getX(), PENALTY_AREA_1.getY(), PENALTY_AREA_1.getRadius());
    shapeRenderer.circle(PENALTY_AREA_2.getX(), PENALTY_AREA_2.getY(), PENALTY_AREA_2.getRadius());
    shapeRenderer.end();

    //Filled big area (so remove inside penalty circle) //TODO improve
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(GRASS);

    shapeRenderer
        .rect(BIG_AREA_1.getX(), BIG_AREA_1.getY(), BIG_AREA_1.getWidth(), BIG_AREA_1.getHeight());
    shapeRenderer
        .rect(BIG_AREA_2.getX(), BIG_AREA_2.getY(), BIG_AREA_2.getWidth(), BIG_AREA_2.getHeight());

    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.WHITE);

    //Small area
    shapeRenderer.rect(SMALL_AREA_1.getX(), SMALL_AREA_1.getY(),
                       SMALL_AREA_1.getWidth(), SMALL_AREA_1.getHeight());
    shapeRenderer.rect(SMALL_AREA_2.getX(), SMALL_AREA_2.getY(),
                       SMALL_AREA_2.getWidth(), SMALL_AREA_2.getHeight());

    //Big area
    shapeRenderer
        .rect(BIG_AREA_1.getX(), BIG_AREA_1.getY(), BIG_AREA_1.getWidth(), BIG_AREA_1.getHeight());
    shapeRenderer
        .rect(BIG_AREA_2.getX(), BIG_AREA_2.getY(), BIG_AREA_2.getWidth(), BIG_AREA_2.getHeight());

    //Central circle
    shapeRenderer.circle(CENTRAL_CIRCLE_AREA.getX(), CENTRAL_CIRCLE_AREA.getY(),
                         CENTRAL_CIRCLE_AREA.getRadius());

    //Goals
    shapeRenderer.rect(GOAL_AREA_1.getX(), GOAL_AREA_1.getY(), GOAL_AREA_1.getWidth(),
                       GOAL_AREA_1.getHeight());
    shapeRenderer.rect(GOAL_AREA_2.getX(), GOAL_AREA_2.getY(), GOAL_AREA_2.getWidth(),
                       GOAL_AREA_2.getHeight());

    //Corner arcs
    shapeRenderer
        .arc(CORNER_AREA_1.getX(), CORNER_AREA_1.getY(), CORNER_AREA_1.getRadius(), 270f, 90f);
    shapeRenderer
        .arc(CORNER_AREA_2.getX(), CORNER_AREA_2.getY(), CORNER_AREA_2.getRadius(), 0f, 90f);
    shapeRenderer
        .arc(CORNER_AREA_3.getX(), CORNER_AREA_3.getY(), CORNER_AREA_3.getRadius(), 90f, 90f);
    shapeRenderer
        .arc(CORNER_AREA_4.getX(), CORNER_AREA_4.getY(), CORNER_AREA_4.getRadius(), 180f, 90f);

    shapeRenderer.end();
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.circle(CENTRAL_CIRCLE_MARK.getX(), CENTRAL_CIRCLE_MARK.getY(),
                         CENTRAL_CIRCLE_MARK.getRadius());
    shapeRenderer.circle(PENALTY_MARK_1.getX(), PENALTY_MARK_1.getY(), PENALTY_MARK_1.getRadius());
    shapeRenderer.circle(PENALTY_MARK_2.getX(), PENALTY_MARK_2.getY(), PENALTY_MARK_2.getRadius());
    shapeRenderer.end();
  }
}
