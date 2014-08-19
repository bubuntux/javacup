package org.dsaw.javacup.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.model.engine.Match;
import org.dsaw.javacup.render.TeamSelector;
import org.dsaw.javacup.render.TeamSelectorRender;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class VersusScreen extends AbstractScreen {

  private final JavaCup game;
  public final TeamSelector localTS;
  public final TeamSelector visitorTS;

  private final TeamSelectorRender localTSR;
  private final TeamSelectorRender visitorTSR;
  private final Rectangle button;

  public VersusScreen(JavaCup game) {
    this.game = game;
    localTS = new TeamSelector();
    visitorTS = new TeamSelector();

    localTSR = new TeamSelectorRender(localTS, 50, 200, 200, 200);// TODO generalize
    visitorTSR = new TeamSelectorRender(visitorTS, 400, 200, 200, 200);

    button = new Rectangle(300, 50, 50, 50);
  }

  @Override
  public void update(float delta) {
    if (Gdx.input.justTouched()) {
      int x = Gdx.input.getX();
      int y = Gdx.input.getY();
      Gdx.app.debug("justTouched", "X:" + x + "  Y:" + y);
      localTSR.touch(x, y);
      visitorTSR.touch(x, y);
      if (button.contains(x, y)) {

        Match match = null;
        try {
          match = new Match(localTS.getTeam(), visitorTS.getTeam(), false);
        } catch (Exception e) {
          e.printStackTrace();
        }

        //TODO start match!!
        /*URL resource = getClass().getResource("/savedGame.zip");
        new StoredMatch(resource);*/
        try {
          game.setScreen(new MatchScreen(game, match));
        } catch (Exception e) {
          Gdx.app.error("rendering", e.getMessage(), e);
        }
      }
    }

  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    //TODO background

    localTSR.draw(game.cRenderer);
    visitorTSR.draw(game.cRenderer);

    ShapeRenderer shapeRenderer = game.cRenderer.shape;
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(new Color(0, 0, 1, 1));
    shapeRenderer.rect(button.x, button.y, button.width, button.height);
    shapeRenderer.end();
  }

  @Override
  public void resize(int width, int height) {
    Gdx.app.debug("resize", "resize");
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
  }
}
