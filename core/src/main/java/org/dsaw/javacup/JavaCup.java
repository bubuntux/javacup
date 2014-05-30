package org.dsaw.javacup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.screen.TeamSelectionScreen;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class JavaCup extends Game {

  //TODO public?
  public SpriteBatch batch;
  public BitmapFont font;

  @Override
  public void create() {
    Reflections reflections = new Reflections("org.dsaw.javacup.tactics");
    Set<Class<? extends Tactic>> tactics = reflections.getSubTypesOf(Tactic.class);
    Class<? extends Tactic> next = tactics.iterator().next();
    try {
      Tactic tactic = next.newInstance();
      tactic.getDetail();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

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
    batch.dispose();
    font.dispose();
  }
}