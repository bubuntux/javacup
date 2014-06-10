package org.dsaw.javacup.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.model.engine.StoredMatch;
import org.dsaw.javacup.render.FieldRenderV2;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class MatchScreen implements Screen {


  private final JavaCup game;
  private final StoredMatch match;
  private final int width;
  private final int height;

  private final FieldRenderV2 fieldRender;
  private final OrthographicCamera camera;

  public MatchScreen(JavaCup game, StoredMatch match, int width, int height) {
    this.game = game;
    this.match = match;
    this.width = width;
    this.height = height;

    fieldRender = new FieldRenderV2();
    camera = new OrthographicCamera();
    camera.zoom = 10f;
    camera.setToOrtho(true);
  }

  private void update(float delta) {
    camera.update();
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    ShapeRenderer shapeRenderer = game.shapeRenderer;
    shapeRenderer.setProjectionMatrix(camera.combined);
    fieldRender.draw(shapeRenderer);
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
