package org.dsaw.javacup.render;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Esta clase se encarga de dibujar el campo de juego, los arcos y el entorno, uso interno
 */
public final class FieldRender {

  private Image arcoSup, arcoInf, estadio, cancha, entorno;
  private int wm, hm;
  private Position esqSupEntorno = new Position(-450, -479);
  private Position esqSupEstadio = new Position(-166, -169);
  private Position esqSupCancha = Constants.esqSupIzqCampoJuego.movePosition(-8.4, -10);
  private int estadioIdx = 0;

  public FieldRender(int wm, int hm, int estadioIdx) throws SlickException {
    this.wm = wm;
    this.hm = hm;
    this.estadioIdx = estadioIdx;
    if (this.estadioIdx == 0) {
      entorno = new Image("imagenes/noucamp/noucamp_entorno.jpg");
      estadio = new Image("imagenes/noucamp/noucamp_estadio.png");
    } else {
      entorno = new Image("imagenes/bernabeu/entorno.bernabeu.jpg");
      estadio = new Image("imagenes/bernabeu/bernabeu.png");
    }
    cancha = new Image("imagenes/cancha.png", Color.black);
    arcoSup = new Image("imagenes/arco_inferior.1.2.png");
    arcoInf = new Image("imagenes/arco_superior.1.2.png");
  }

  public void pintaEstadio(Graphics g, Position p, double escala) throws SlickException {
    if (estadioIdx == 0) {
      int inf[] = Transformer.transform(esqSupEstadio, p, wm, hm, escala);
      float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
      g.drawImage(estadio.getScaledCopy(factor * 6.66666f), inf[0], inf[1]);
    } else {
      int inf[] = Transformer
          .transform(esqSupEstadio, p.movePosition(-44, 13.5), wm, hm, escala);
      float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
      g.drawImage(estadio.getScaledCopy(factor * 6.56614f), inf[0], inf[1]);
    }
  }

  public void pintaEntorno(Graphics g, Position p, double escala) throws SlickException {
    if (estadioIdx == 0) {
      int inf[] = Transformer.transform(esqSupEntorno, p, wm, hm, escala);
      float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
      g.drawImage(entorno.getScaledCopy(factor * 18.8f), inf[0], inf[1]);
    } else {
      int inf[] = Transformer
          .transform(esqSupEntorno, p.movePosition(-236, -145), wm, hm, escala);
      float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
      g.drawImage(entorno.getScaledCopy(factor * 11.8f), inf[0], inf[1]);
    }
  }

  public void pintaCancha(Graphics g, Position p, double escala) {
    int inf[] = Transformer.transform(esqSupCancha, p, wm, hm, escala);
    float factor = (float) (escala / 15d);
    g.drawImage(cancha.getScaledCopy(factor), inf[0], inf[1]);
  }

  public void pintaArcos(Graphics g, Position p, double escala) {
    int inf[] = Transformer.transform(Constants.posteIzqArcoInf, p, wm, hm, escala);
    float factor = (float) (escala * Constants.LARGO_ARCO / 165);
    double dy = 0.25 * Constants.LARGO_ARCO * Constants.AMP_ARCO;
    double dx = 0.023 * Constants.LARGO_ARCO;
    g.drawImage(arcoInf.getScaledCopy(factor), inf[0] - Transformer.transform(dx, escala),
                inf[1] - Transformer
                    .transform(dy, escala));
    inf = Transformer.transform(Constants.posteIzqArcoSup, p, wm, hm, escala);
    g.drawImage(arcoSup.getScaledCopy(factor), inf[0] - Transformer.transform(dx, escala), inf[1]);
  }

}
