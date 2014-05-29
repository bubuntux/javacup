package org.dsaw.javacup;

import com.badlogic.gdx.Game;

import org.dsaw.javacup.screen.TeamSelectionScreen;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class JavaCup extends Game {

  @Override
  public void create() {
    setScreen(new TeamSelectionScreen());
  }
}
