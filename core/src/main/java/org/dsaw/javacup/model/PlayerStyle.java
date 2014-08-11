package org.dsaw.javacup.model;

import java.awt.*;

/**
 * Created by Julio Gutierrez on 8/10/14.
 */
public class PlayerStyle {

  private final Color skinColor;
  private final Color hairColor;

  public PlayerStyle(Color skinColor, Color hairColor) {
    this.skinColor = skinColor;
    this.hairColor = hairColor;
  }

  public Color getSkinColor() {
    return skinColor;
  }

  public Color getHairColor() {
    return hairColor;
  }
}
