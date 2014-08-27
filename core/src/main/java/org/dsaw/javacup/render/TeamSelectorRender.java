package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


/**
 * @author Julio Gutierrez (30/05/2014)
 */
public final class TeamSelectorRender {/*


  private final TeamLoader teamLoader;

  private float x;
  private float y;
  private float width;
  private float height;

  //TODO crate wrapper
  private final Rectangle prevCountryBounds;
  private final Rectangle nextCountryBounds;
  private float countryTextX;
  private float countryTextY;

  private final Rectangle prevTacticBounds;
  private final Rectangle nextTacticBounds;
  private float tacticTextX;
  private float tacticTextY;


  public TeamSelectorRender(TeamLoader teamLoader, float x, float y,
                            float width, float height) {
    this.teamLoader = teamLoader;

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    prevCountryBounds = new Rectangle(x + 10, y + 50, 20, 20);
    nextCountryBounds = new Rectangle(x + width - 30, y + 50, 20, 20);
    countryTextX = prevCountryBounds.x + prevCountryBounds.width + 10;
    countryTextY = y + 65;

    prevTacticBounds = new Rectangle(x + 10, y + 20, 20, 20);
    nextTacticBounds = new Rectangle(x + width - 30, y + 20, 20, 20);
    tacticTextX = prevTacticBounds.x + prevTacticBounds.width + 10;
    tacticTextY = y + 35;
  }

  public void draw(CompositeRenderer cRenderer) {
    ShapeRenderer shape = cRenderer.shape;

    shape.begin(ShapeRenderer.ShapeType.Filled);

    shape.setColor(new Color(0, 0, 1, 1));
    shape.rect(x, y, width, height);

    shape.setColor(new Color(1, 1, 1, 1)); //country buttons
    shape.rect(nextCountryBounds.x, nextCountryBounds.y, nextCountryBounds.width,
               nextCountryBounds.height);
    shape
        .rect(prevCountryBounds.x, prevCountryBounds.y, prevCountryBounds.width,
              prevCountryBounds.height);

    shape.rect(nextTacticBounds.x, nextTacticBounds.y, nextTacticBounds.width,
               nextTacticBounds.height);
    shape
        .rect(prevTacticBounds.x, prevTacticBounds.y, prevTacticBounds.width,
              prevTacticBounds.height);
    shape.end();

    shape.begin(ShapeRenderer.ShapeType.Line);
    shape.setColor(new Color(0, 0, 0, 1)); //country lines inside "buttons"
    shape.line(nextCountryBounds.x, nextCountryBounds.y,
               nextCountryBounds.x + nextCountryBounds.width,
               nextCountryBounds.y + nextCountryBounds.height / 2);
    shape.line(nextCountryBounds.x, nextCountryBounds.y + nextCountryBounds.height,
               nextCountryBounds.x + nextCountryBounds.width,
               nextCountryBounds.y + nextCountryBounds.height / 2);
    shape.line(prevCountryBounds.x + prevCountryBounds.width, prevCountryBounds.y,
               prevCountryBounds.x,
               prevCountryBounds.y + prevCountryBounds.height / 2);
    shape.line(prevCountryBounds.x + prevCountryBounds.width,
               prevCountryBounds.y + prevCountryBounds.height,
               prevCountryBounds.x,
               prevCountryBounds.y + prevCountryBounds.height / 2);

    shape.line(nextTacticBounds.x, nextTacticBounds.y,
               nextTacticBounds.x + nextTacticBounds.width,
               nextTacticBounds.y + nextTacticBounds.height / 2);
    shape.line(nextTacticBounds.x, nextTacticBounds.y + nextTacticBounds.height,
               nextTacticBounds.x + nextTacticBounds.width,
               nextTacticBounds.y + nextTacticBounds.height / 2);
    shape.line(prevTacticBounds.x + prevTacticBounds.width, prevTacticBounds.y,
               prevTacticBounds.x,
               prevTacticBounds.y + prevTacticBounds.height / 2);
    shape.line(prevTacticBounds.x + prevTacticBounds.width,
               prevTacticBounds.y + prevTacticBounds.height,
               prevTacticBounds.x,
               prevTacticBounds.y + prevTacticBounds.height / 2);
    shape.end();

    SpriteBatch batch = cRenderer.batch;
    BitmapFont font = cRenderer.font;
    batch.begin();
    font.draw(batch, teamLoader.getCountry().getName(), countryTextX, countryTextY);
    font.draw(batch, teamLoader.getTeam().getName(), tacticTextX,
              tacticTextY);
    batch.end();
  }

  public void touch(float x, float y) {
    if (prevCountryBounds.contains(x, y)) {
      teamLoader.prevCountry();
    }

    if (nextCountryBounds.contains(x, y)) {
      teamLoader.nextCountry();
    }

    if (prevTacticBounds.contains(x, y)) {
      teamLoader.prevTeam();
    }

    if (nextTacticBounds.contains(x, y)) {
      teamLoader.nextTeam();
    }
  }
*/
}