package org.dsaw.javacup.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.render.TacticSelector;
import org.dsaw.javacup.model.engine.Match;
import org.dsaw.javacup.render.TeamSelectorRender;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class TeamSelectionScreen implements Screen {

  private final JavaCup game;
  private final TeamSelectorRender localTeamSelectorRender;
  private final TeamSelectorRender visitorTeamSelectorRender;

  private Rectangle button;
  public TacticSelector localTacticSelector;
  public TacticSelector visitorTacticSelector;

  public TeamSelectionScreen(JavaCup game) {
    this.game = game;
    localTacticSelector = new TacticSelector();
    visitorTacticSelector = new TacticSelector();

    localTeamSelectorRender =
        new TeamSelectorRender(game, localTacticSelector, 50, 200, 200, 200);// TODO generalize
    visitorTeamSelectorRender =
        new TeamSelectorRender(game, visitorTacticSelector, 400, 200, 200, 200);

    button = new Rectangle(300, 50, 50, 50);
  }

  private void update(float delta) {
    if (Gdx.input.justTouched()) {
      int x = Gdx.input.getX();
      int y = Gdx.graphics.getHeight() - Gdx.input.getY();
      Gdx.app.log("some", "X:" + x + "  Y:" + y);
      localTeamSelectorRender.touch(x, y);
      visitorTeamSelectorRender.touch(x, y);
      if (button.contains(x, y)) {

        Match
            match = null;
        try {
          match =
              new Match(localTacticSelector.currentTeam(), visitorTacticSelector.currentTeam(),
                        false);
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

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    //TODO background

    localTeamSelectorRender.draw();
    visitorTeamSelectorRender.draw();

    ShapeRenderer shapeRenderer = game.shapeRenderer;
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(new Color(0, 0, 1, 1));
    shapeRenderer.rect(button.x, button.y, button.width, button.height);
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
