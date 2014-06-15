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
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.BallRenderV2;
import org.dsaw.javacup.render.FieldRenderV2;
import org.dsaw.javacup.render.ScoreboardRenderV2;

import static org.dsaw.javacup.model.util.ConstantsV2.METER_TO_CENTIMETER;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class MatchScreen implements Screen {

  private static final float CAMERA_ZOOM_MAX = 10f;
  private static final float CAMERA_ZOOM_MIN = 3f;
  private static final float CAMERA_ZOOM_SPEED = 0.1f;

  private final JavaCup game;
  private final StoredMatch match;

  private final OrthographicCamera camera;
  private final FieldRenderV2 fieldRender;
  private final BallRenderV2 ballRender;
  private final ScoreboardRenderV2 scoreboardRender;

  private boolean cameraFollowing = true;


  public MatchScreen(JavaCup game, StoredMatch match) { //TODO no nulls
    this.game = game;
    this.match = match;

    camera = new OrthographicCamera();
    camera.zoom = CAMERA_ZOOM_MAX;
    camera.setToOrtho(true);

    fieldRender = new FieldRenderV2();
    ballRender = new BallRenderV2();
    scoreboardRender = new ScoreboardRenderV2();
  }

  private void update(float delta) {
    try {
      match.iterar();
    } catch (Exception e) {
      Gdx.app.error("rendering", e.getMessage(), e);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
      camera.position.y -= 25f;
      cameraFollowing = false;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
      camera.position.y += 25f;
      cameraFollowing = false;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
      camera.position.x -= 25f;
      cameraFollowing = false;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
      camera.position.x += 25f;
      cameraFollowing = false;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.PLUS) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
      camera.zoom -= CAMERA_ZOOM_SPEED;
      if (camera.zoom < CAMERA_ZOOM_MIN) {
        camera.zoom = CAMERA_ZOOM_MIN;
      }
    }
    if (Gdx.input.isKeyPressed(Input.Keys.MINUS) || Gdx.input.isKeyPressed(Input.Keys.E)) {
      camera.zoom += CAMERA_ZOOM_SPEED;
      if (camera.zoom > CAMERA_ZOOM_MAX) {
        camera.zoom = CAMERA_ZOOM_MAX;
      }
    }
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      cameraFollowing = true;
    }
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    ShapeRenderer shapeRenderer = game.shapeRenderer;
    shapeRenderer.setProjectionMatrix(camera.combined);
    fieldRender.draw(shapeRenderer);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    for (int i = 0; i < match.getPosiciones().length; i++) {
      switch (i) {
        case 0:
          shapeRenderer.setColor(Color.BLUE);
          break;
        case 1:
          shapeRenderer.setColor(Color.RED);
          break;
        case 2:
          continue;
      }

      Position[] positions = match.getPosiciones()[i]; //TODO WTF!!!!
      for (Position position : positions) {
        if (position != null) {
          shapeRenderer.circle((float) (position.getX() * METER_TO_CENTIMETER),
                               (float) (position.getY() * METER_TO_CENTIMETER),
                               METER_TO_CENTIMETER / 2);
        }
      }
    }
    shapeRenderer.end();

    Position ballPosition = match.getPosVisibleBalon();
    float ballX = (float) (ballPosition.getX() * METER_TO_CENTIMETER);
    float ballY = (float) (ballPosition.getY() * METER_TO_CENTIMETER);

    ballRender.draw(shapeRenderer, ballX, ballY);
    if (cameraFollowing) { //TODO
      camera.position.x = ballX;
      camera.position.y = ballY;
    }

    scoreboardRender
        .draw(shapeRenderer, game.batch, game.font, match.getDetalleLocal().getTacticName(),
              match.getGolesLocal(), match.getDetalleVisita().getTacticName(),
              match.getGolesVisita(), match.getTiempo());
  }

  @Override
  public void render(float delta) {
    update(delta);
    draw();
    camera.update(); //TODO

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
