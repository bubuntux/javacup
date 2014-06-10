package org.dsaw.javacup.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.model.engine.StoredMatch;
import org.dsaw.javacup.model.util.ConstantsV2;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.FieldRenderV2;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class MatchScreen implements Screen {


  private final JavaCup game;
  private final StoredMatch match;

  private final FieldRenderV2 fieldRender;
  private final OrthographicCamera camera;

  public MatchScreen(JavaCup game, StoredMatch match) { //TODO no nulls
    this.game = game;
    this.match = match;

    fieldRender = new FieldRenderV2();
    camera = new OrthographicCamera();
    camera.zoom = 10f;
    camera.setToOrtho(true);
  }

  private void update(float delta) {
    try {
      match.iterar();
    } catch (Exception e) {
      Gdx.app.error("rendering", e.getMessage(), e);
    }
    camera.update();

    if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
      camera.position.y -= 25f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
      camera.position.y += 25f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
      camera.position.x -= 25f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
      camera.position.x += 25f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.PLUS) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
      camera.zoom -= 0.1f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.MINUS) || Gdx.input.isKeyPressed(Input.Keys.E)) {
      camera.zoom += 0.1f;
    }
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    ShapeRenderer shapeRenderer = game.shapeRenderer;
    shapeRenderer.setProjectionMatrix(camera.combined);
    fieldRender.draw(shapeRenderer);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.ORANGE);
    Position ball = match.getPosVisibleBalon();
    shapeRenderer
        .circle((float) ball.getX() * 100, (float) ball.getY() * 100, ConstantsV2.BALL_RADIUS * 5);
    shapeRenderer.end();
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
