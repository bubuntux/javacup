package org.dsaw.javacup.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.render.TeamSelectorRender;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class TeamSelectionScreen implements Screen {

  private final JavaCup game;
  private final TeamSelectorRender localTeamSelectorRender;
  private final TeamSelectorRender visitorTeamSelectorRender;
  private final OrthographicCamera camera;

  public TeamSelectionScreen(JavaCup game) {
    this.game = game;

    localTeamSelectorRender =
        new TeamSelectorRender(game, game.localTacticSelector, 50, 200, 200, 200);// TODO generalize
    visitorTeamSelectorRender =
        new TeamSelectorRender(game, game.visitorTacticSelector, 400, 200, 200, 200);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  private void update(float delta) {
    if (Gdx.input.justTouched()) {
      int x = Gdx.input.getX();
      int y = Gdx.graphics.getHeight() - Gdx.input.getY();
      Gdx.app.log("some", "X:" + x + "  Y:" + y);
      localTeamSelectorRender.touch(x, y);
      visitorTeamSelectorRender.touch(x, y);
    }

    camera.update();
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    //TODO background

    localTeamSelectorRender.draw();
    visitorTeamSelectorRender.draw();
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
