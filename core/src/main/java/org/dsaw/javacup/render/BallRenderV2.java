package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.dsaw.javacup.model.util.ConstantsV2;

/**
 * Created by Julio Gutierrez on 6/10/14.
 */
public class BallRenderV2 {

  public void draw(ShapeRenderer shapeRenderer, float x, float y) {
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.ORANGE);
    shapeRenderer.circle(x, y, ConstantsV2.BALL_RADIUS);
    shapeRenderer.end();
  }
}