package org.dsaw.javacup.model.engine;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Acceleration {

  //  Aceleraci�n en el eje X e Y, valor entre 0 y 1

  private double accelerationX = 0;
  private double accelerationY = 0;

  //Ultima posicion del jugador donde se calculo la aceleracion
  private Position posJugador = new Position();

  //Direcci�n en el eje X e Y. Valores -1, 0 (DETENIDO) y 1

  private double direccionX = 0;
  private double direccionY = 0;

  protected Acceleration() {
    accelerationX = 1;
    accelerationY = 1;
  }

  protected Acceleration(float x, float y) {
    accelerationX = x;
    accelerationY = y;

  }

  public void update(Position posActual) {

    //Guardamos las direcciones actuales
    double anteriorDireccionX = direccionX;
    double anteriorDireccionY = direccionY;

    //Calculamos las direcciones nuevas
    double Dx = posActual.getX() - posJugador.getX();
    direccionX = (Dx == 0) ? 0 : (Math.signum(Dx));

    double Dy = posActual.getY() - posJugador.getY();
    direccionY = (Dy == 0) ? 0 : (Math.signum(Dy));

    //Si ha habido algun cambio en alguna de ellas bajamos su aceleracion sino la incrementamos
    if (anteriorDireccionX != direccionX) {
      accelerationX = Constants.ACELERACION_MINIMA_X;
    } else {
      accelerationX += Constants.ACELERACION_INCR;
      if (accelerationX > 1) {
        accelerationX = 1; //M�ximo de la acelaracion
      }
    }

    if (anteriorDireccionY != direccionY) {
      accelerationY = Constants.ACELERACION_MINIMA_Y;
    } else {
      accelerationY += Constants.ACELERACION_INCR;
      if (accelerationY > 1) {
        accelerationY = 1; //M�ximo de la acelaracion
      }
    }

    //Actualizamos la posicion del jugador
    posJugador = new Position(posActual.getX(), posActual.getY());
  }

  public double getAccelerationX() {
    return accelerationX;
  }

  public double getAccelerationY() {
    return accelerationY;
  }

  /**
   * Devuelve la aceleracion global compuesta por la de ambos ejes*
   */
  public double getAccelerationGlobal() {
    return accelerationX * accelerationY;
  }

}
