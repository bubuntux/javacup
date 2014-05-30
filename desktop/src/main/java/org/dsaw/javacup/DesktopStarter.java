package org.dsaw.javacup;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class DesktopStarter {

  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = "JavaCup";
    config.useGL30 = true;
    new LwjglApplication(new JavaCup(), config);
  }
}