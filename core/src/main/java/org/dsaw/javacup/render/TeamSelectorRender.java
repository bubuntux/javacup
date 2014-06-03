package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.model.TacticSelector;

/**
 * @author Julio Gutierrez (30/05/2014)
 */
public final class TeamSelectorRender {

  private final JavaCup game;
  private final TacticSelector tacticSelector;

  private final float x;
  private final float y;
  private final float width;
  private final float height;

  private final Rectangle prevCountryBounds;
  private final Rectangle nextCountryBounds;
  private float countryTextX;
  private float countryTextY;


  public TeamSelectorRender(JavaCup game, TacticSelector tacticSelector, float x, float y,
                            float width, float height) {
    this.game = game;
    this.tacticSelector = tacticSelector;

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    prevCountryBounds = new Rectangle(x + 10, y + 50, 20, 20);
    nextCountryBounds = new Rectangle(x + width - 30, y + 50, 20, 20);

    countryTextX = prevCountryBounds.x + prevCountryBounds.width + 10;
    countryTextY = y + 65;
  }

  public void draw() {
    ShapeRenderer shapeRenderer = game.shapeRenderer;
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

    SpriteBatch batch = game.batch;
    batch.begin();
    game.font.draw(batch, tacticSelector.currentCountry().getName(), countryTextX, countryTextY);
    batch.end();
  }

  public void touch(float x, float y) {
    if (prevCountryBounds.contains(x, y)) {
      tacticSelector.prevCountry();
    }

    if (nextCountryBounds.contains(x, y)) {
      tacticSelector.nextCountry();
    }
  }

}