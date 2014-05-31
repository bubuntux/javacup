package org.dsaw.javacup.tactics.jvc2013.mansporting.ui;


import java.awt.*;

/**
 * <br> Drawable abstracto. <br> <br>Copyright Fi2net S.A. 10-ene-2011
 */
public abstract class AbstractDrawable<E> implements Drawable<E> {

  /**
   * Elemento a pintar.
   */
  protected final E element;
  /**
   * Color con que pintar.
   */
  protected final Color color;

  /**
   * Constructor.
   *
   * @param canvas  canvas.
   * @param element elemento.
   */
  public AbstractDrawable(E element, Color color) {
    this.element = element;
    this.color = color;
  }
}
