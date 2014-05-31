package org.dsaw.javacup.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.render.TeamSelector;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class TeamSelectionScreen implements Screen {

  private final JavaCup game;
  private final TeamSelector localTeamSelector;
  private final TeamSelector visitorTeamSelector;
  private final OrthographicCamera camera;

  public TeamSelectionScreen(JavaCup game) {
    this.game = game;

    localTeamSelector = new TeamSelector(game, 50, 200, 200, 200);// TODO generalize
    visitorTeamSelector = new TeamSelector(game, 400, 200, 200, 200);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  private void update() {
    if (Gdx.input.justTouched()) {
      int x = Gdx.input.getX();
      int y = Gdx.graphics.getHeight() - Gdx.input.getY();
      Gdx.app.log("some", "X:" + x + "  Y:" + y);
      localTeamSelector.touch(x, y);
      visitorTeamSelector.touch(x, y);
    }

  /*  if (game.getScreen() != this) { //TODO?
      dispose();
    }*/
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    camera.update();

    //TODO background

    localTeamSelector.draw();
    visitorTeamSelector.draw();

    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();
    game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
    game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
    game.batch.end();
  }

  @Override
  public void render(float delta) {
    update();
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
