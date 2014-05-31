package org.dsaw.javacup.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import org.dsaw.javacup.JavaCup;

/**
 * @author Julio Gutierrez (30/05/2014)
 */
public final class TeamSelector {

  private final JavaCup game;
  private final float x;
  private final float y;
  private final float width;
  private final float height;
  private final ShapeRenderer shapeRenderer;
  private final Rectangle nextCountryBounds;
  private final Rectangle prevCountryBounds;

  public TeamSelector(JavaCup game, float x, float y, float width, float height) {
    this.game = game;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    shapeRenderer = new ShapeRenderer();

    prevCountryBounds = new Rectangle(x + 10, y + 50, 20, 20);
    nextCountryBounds = new Rectangle(x + width - 30, y + 50, 20, 20);
  }

  public void draw() {
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    shapeRenderer.setColor(new Color(0, 0, 1, 1));
    shapeRenderer.rect(x, y, width, height);

    shapeRenderer.setColor(new Color(1, 1, 1, 1)); //country buttons
    shapeRenderer.rect(nextCountryBounds.x, nextCountryBounds.y, nextCountryBounds.width,
                       nextCountryBounds.height);
    shapeRenderer
        .rect(prevCountryBounds.x, prevCountryBounds.y, prevCountryBounds.width,
              prevCountryBounds.height);
    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(new Color(0, 0, 0, 1)); //country lines inside "buttons"
    shapeRenderer.line(nextCountryBounds.x, nextCountryBounds.y,
                       nextCountryBounds.x + nextCountryBounds.width,
                       nextCountryBounds.y + nextCountryBounds.height / 2);
    shapeRenderer.line(nextCountryBounds.x, nextCountryBounds.y + nextCountryBounds.height,
                       nextCountryBounds.x + nextCountryBounds.width,
                       nextCountryBounds.y + nextCountryBounds.height / 2);
    shapeRenderer.line(prevCountryBounds.x + prevCountryBounds.width, prevCountryBounds.y,
                       prevCountryBounds.x,
                       prevCountryBounds.y + prevCountryBounds.height / 2);
    shapeRenderer.line(prevCountryBounds.x + prevCountryBounds.width,
                       prevCountryBounds.y + prevCountryBounds.height,
                       prevCountryBounds.x,
                       prevCountryBounds.y + prevCountryBounds.height / 2);
    shapeRenderer.end();
  }

  public void touch(float x, float y) {
    if (nextCountryBounds.contains(x, y)) {
      Gdx.app.log("some", "next");
    }

    if (prevCountryBounds.contains(x, y)) {
      Gdx.app.log("some", "prev");
    }
  }

}