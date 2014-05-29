package org.dsaw.javacup;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopStarter {

  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.useGL30 = true;
    //new LwjglApplication(new JavaCup(), config); TODO
  }
}
