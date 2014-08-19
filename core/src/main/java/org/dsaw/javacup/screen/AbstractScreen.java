package org.dsaw.javacup.screen;

import com.badlogic.gdx.Screen;

/**
 * Created by Julio Gutierrez on 8/18/14.
 */
public abstract class AbstractScreen implements Screen {

  @Override
  public void render(float delta) {
    update(delta);
    render();
  }

  public abstract void update(float delta);

  public abstract void render();

}
