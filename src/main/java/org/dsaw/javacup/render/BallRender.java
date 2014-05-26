package org.dsaw.javacup.render;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Esta clase se encarga de dibujar el ball, uso interno
 */
public final class BallRender {

  private Image ball[] = new Image[6];
  private Image shadow;

  public BallRender() throws SlickException {
    for (int i = 0; i < 6; i++) {
      ball[i] = new Image("imagenes/balon/ball" + i + ".png");
    }
    shadow = new Image("imagenes/balon/sombra.png");
  }

  //TODO merge methods?
  public void renderBall(int iter, double angulo, double escala, double x, double y, double z,
                         Graphics g) {
    escala = escala / 10;
    double esc = 0.225 * escala * (1 + z / 16);
    double esc1 = 0.225 * escala * (1 + z / 8);
    double factor = 14 * esc1;
    x = x - factor;
    y = y - factor;
    g.rotate((float) (x + factor), (float) (y + factor),
             (float) -((angulo - Math.PI / 2) * 180 / Math.PI));
    g.drawImage(ball[iter % 6].getScaledCopy((float) esc), (int) x, (int) y);
    g.rotate((float) (x + factor), (float) (y + factor),
             (float) ((angulo - Math.PI / 2) * 180 / Math.PI));
  }

  public void renderShadow(double escala, double x, double y, double z, Graphics g) {
    escala = escala / 10;
    double esc = 0.225 * escala;
    x = x + escala / 3;
    y = y + escala / 3;
    g.drawImage(shadow.getScaledCopy((float) esc), (int) (x + escala * z * 3),
                (int) (y + escala * z * 3));
  }
}
