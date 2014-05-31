package org.dsaw.javacup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.dsaw.javacup.screen.TeamSelectionScreen;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class JavaCup extends Game {

  //TODO public?
  public SpriteBatch batch;
  public BitmapFont font;
  //public Map<Version, Map<CountryCode, Tactic>> tactics;

  @Override
  public void create() {
    //tactics = loadTactics();

    batch = new SpriteBatch();
    font = new BitmapFont();
    setScreen(new TeamSelectionScreen(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    super.dispose();
    // tactics.clear();
    batch.dispose();
    font.dispose();
  }
}