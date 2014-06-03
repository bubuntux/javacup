package org.dsaw.javacup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.dsaw.javacup.model.TacticSelector;
import org.dsaw.javacup.screen.TeamSelectionScreen;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class JavaCup extends Game {

  //TODO public?
  public SpriteBatch batch;
  public ShapeRenderer shapeRenderer;
  public BitmapFont font;

  public TacticSelector localTacticSelector;
  public TacticSelector visitorTacticSelector;

  @Override
  public void create() {
    localTacticSelector = new TacticSelector();
    visitorTacticSelector = new TacticSelector();

    batch = new SpriteBatch();
    shapeRenderer = new ShapeRenderer();
    font = new BitmapFont();
    setScreen(new TeamSelectionScreen(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    super.dispose();

    shapeRenderer.dispose();
    batch.dispose();
    font.dispose();
  }
}