package org.dsaw.javacup.screen;

import com.badlogic.gdx.Screen;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.model.engine.StoredMatch;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class MatchScreen implements Screen {


  private final JavaCup game;
  private final StoredMatch match;
  private final int width;
  private final int height;

  public MatchScreen(JavaCup game, StoredMatch match, int width, int height) {
    this.game = game;
    this.match = match;
    this.width = width;
    this.height = height;


  }

  private void update(float delta) {

  }

  private void draw() {

  }

  @Override
  public void render(float delta) {
    update(delta);
    draw();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void show() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }
}
