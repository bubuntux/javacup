package org.dsaw.javacup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.dsaw.javacup.render.CompositeRenderer;
import org.dsaw.javacup.screen.VersusScreen;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class JavaCup extends Game {

  //TODO public?
  public CompositeRenderer cRenderer;
  //TODO configs or something...


  @Override
  public void create() {
    SpriteBatch spriteBatch = new SpriteBatch();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    BitmapFont bitmapFont = new BitmapFont();
    cRenderer = new CompositeRenderer(spriteBatch, shapeRenderer, bitmapFont);

    setScreen(new VersusScreen(this));
  }

  @Override
  public void dispose() {
    super.dispose();

    cRenderer.dispose();
  }
}