package org.dsaw.javacup.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.ui.actor.widget.TeamSelector;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class VersusScreen implements Screen {

  private final JavaCup game;
  private final Stage stage;

  public VersusScreen(JavaCup game) {
    this.game = game;
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    Button b = new TextButton("Match", game.skin);
    b.setCenterPosition(Gdx.graphics.getWidth() / 2, 50);
    b.addListener(new ClickListener() {

      @Override
      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        Gdx.app.debug("event", "touchUp");
      }
    });

    stage.addActor(b);

    TeamSelector local = new TeamSelector(game.teams, game.skin);
    local.setX(150);
    local.setY(200);
    stage.addActor(local);

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    Gdx.app.debug("resize", "resize");
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void show() {
    Gdx.app.debug("show", "show");
  }

  @Override
  public void hide() {
    Gdx.app.debug("hide", "hide");
  }

  @Override
  public void pause() {
    Gdx.app.debug("pause", "pause");
  }

  @Override
  public void resume() {
    Gdx.app.debug("resume", "resume");
  }

  @Override
  public void dispose() {
    Gdx.app.debug("dispose", "dispose");
    stage.dispose();
  }
}
