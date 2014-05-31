package org.dsaw.javacup.tactics.jvc2013.emandem.controladores;

import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.emandem.utils.Recta;

import java.util.Random;

public class ControladorUtils {

  private static ControladorUtils instance;
  public static final double DISTANCIA_MARCAJE = 5;

  static {
    instance = new ControladorUtils();
  }

  private ControladorUtils() {

  }

  public static ControladorUtils getInstance() {
    return instance;
  }

  public int generaNumeroEnteroAleatorioEntre(int numLimiteIni,
                                              int numLimiteFin) {
    return (int) Math.floor(Math.random()
                            * (numLimiteFin - numLimiteIni + 1) + numLimiteIni);
  }

  public double generaNumeroDobleAleatorioEntre(int numLimiteIni,
                                                int numLimiteFin) {
    return Math.floor(Math.random() * (numLimiteFin - numLimiteIni + 1)
                      + numLimiteIni);
  }

  public double generaNumeroDobleAleatorioEntre(double numLimiteIni,
                                                double numLimiteFin) {
    return Math.floor(Math.random() * (numLimiteFin - numLimiteIni + 1)
                      + numLimiteIni);
  }

  public boolean generaBoleanoAleatorioEntre() {
    return new Random().nextBoolean();
  }

  public boolean esPosiblePasarBalon(final Position[] rivales,
                                     final Position jugadorConBalon,
                                     final Position jugadorDestino) {
    boolean result = Boolean.TRUE;

    for (Position rival : rivales) {
      Position interseccion = Position.Intersection(jugadorConBalon,
                                                    jugadorDestino, rival, rival);

      if (interseccion != null) {
        // Hay un jugador que impide el pase
        result = Boolean.FALSE;
        break;
      }
    }
    return result;
  }

  public double distancia2Puntos(final Position punto1, final Position punto2) {
    double distancia = Math.sqrt(Math.pow(punto1.getX() - punto2.getX(), 2)
                                 + Math.pow(punto1.getY() - punto2.getY(), 2));
    return distancia;
  }

  public boolean tieneRivalCerca(final Position[] rivales,
                                 final Position jugador) {
    boolean result = false;
    int rivalMasCercano = jugador.nearestIndex(rivales);

    if (jugador.distance(rivales[rivalMasCercano]) <= DISTANCIA_MARCAJE) {
      result = true;
    }
    return result;
  }

  public double getAnguloDe2Rectas(final Recta recta1,
                                   final Recta recta2) {
    double pendienteRecta1 = getPendienteUnaRecta(recta1.getPunto1(),
                                                  recta1.getPunto2());
    double pendienteRecta2 = getPendienteUnaRecta(recta2.getPunto1(),
                                                  recta2.getPunto2());
    double tanAngulo = (pendienteRecta2 - pendienteRecta1)
                       / ((pendienteRecta2 * pendienteRecta1) + 1);

    double anguloRadianes = Math.atan(tanAngulo);
    double angulo = Math.toDegrees(anguloRadianes);

    if (Math.tan(tanAngulo) >= 0) {
      angulo += 180;
    }
    return angulo;
  }

  public double getPendienteUnaRecta(final Position punto1, final Position punto2) {
    return ((punto2.getY() - punto1.getY()) / (punto2.getX() - punto1
        .getX()));
  }

}
