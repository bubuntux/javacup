package org.dsaw.javacup.tactics.jvc2013.emandem.cancha;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class AreasCancha {

  private static AreasCancha instance;

  private static double AREA_CHICA_SUP[] = new double[8];
  private static double AREA_GRANDE_SUP[] = new double[8];
  private static double AREA_CHICA_INF[] = new double[8];
  private static double AREA_GRANDE_INF[] = new double[8];

  private static int X1 = 0;
  private static int Y1 = 1;
  private static int X2 = 2;
  private static int Y2 = 3;
  private static int X3 = 4;
  private static int Y3 = 5;
  private static int X4 = 6;
  private static int Y4 = 7;

  static {
    instance = new AreasCancha();

    AREA_CHICA_SUP[X1] = Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_SUP[Y1] = -Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_CHICA_SUP[X2] = -Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_SUP[Y2] = -Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_CHICA_SUP[X3] = -Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_SUP[Y3] = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_CHICA) * -1;
    AREA_CHICA_SUP[X4] = Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_SUP[Y4] = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_CHICA) * -1;

    AREA_GRANDE_SUP[X1] = Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_SUP[Y1] = -Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_GRANDE_SUP[X2] = -Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_SUP[Y2] = -Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_GRANDE_SUP[X3] = -Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_SUP[Y3] = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE) * -1;
    AREA_GRANDE_SUP[X4] = Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_SUP[Y4] = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE) * -1;

    AREA_CHICA_INF[X1] = Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_INF[Y1] = Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_CHICA_INF[X2] = -Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_INF[Y2] = Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_CHICA_INF[X3] = -Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_INF[Y3] = (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_CHICA;
    AREA_CHICA_INF[X4] = Constants.LARGO_AREA_CHICA / 2;
    AREA_CHICA_INF[Y4] = (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_CHICA;

    AREA_GRANDE_INF[X1] = Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_INF[Y1] = Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_GRANDE_INF[X2] = -Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_INF[Y2] = Constants.LARGO_CAMPO_JUEGO / 2;
    AREA_GRANDE_INF[X3] = -Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_INF[Y3] = (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE;
    AREA_GRANDE_INF[X4] = Constants.LARGO_AREA_GRANDE / 2;
    AREA_GRANDE_INF[Y4] = (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE;
  }

  private AreasCancha() {

  }

  public static AreasCancha getInstance() {
    return instance;
  }

  public boolean estaEnElAreaChicaSup(final Position posicion) {
    if (posicion.getX() <= AREA_CHICA_SUP[X1] && posicion.getX() >= AREA_CHICA_SUP[X3] &&
        posicion.getY() >= AREA_CHICA_SUP[Y1] && posicion.getY() <= AREA_CHICA_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElAreaGrandeSup(final Position posicion) {
    if (posicion.getX() <= AREA_GRANDE_SUP[X1] && posicion.getX() >= AREA_GRANDE_SUP[X3] &&
        posicion.getY() >= AREA_GRANDE_SUP[Y1] && posicion.getY() <= AREA_GRANDE_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElAreaChicaInf(final Position posicion) {
    if (posicion.getX() <= AREA_CHICA_INF[X1] && posicion.getX() >= AREA_CHICA_INF[X3] &&
        posicion.getY() <= AREA_CHICA_INF[Y1] && posicion.getY() >= AREA_CHICA_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElAreaGrandeInf(final Position posicion) {
    if (posicion.getX() <= AREA_GRANDE_INF[X1] && posicion.getX() >= AREA_GRANDE_INF[X3] &&
        posicion.getY() <= AREA_GRANDE_INF[Y1] && posicion.getY() >= AREA_GRANDE_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElLimiteDelAreaChicaSup(final Position posicion) {
    if (posicion.getX() == AREA_CHICA_SUP[X1] || posicion.getX() == AREA_CHICA_SUP[X3] ||
        posicion.getY() == AREA_CHICA_SUP[Y1] || posicion.getY() == AREA_CHICA_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElLimiteDelAreaGrandeSup(final Position posicion) {
    if (posicion.getX() == AREA_GRANDE_SUP[X1] || posicion.getX() == AREA_GRANDE_SUP[X3] ||
        posicion.getY() == AREA_GRANDE_SUP[Y1] || posicion.getY() == AREA_GRANDE_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElLimiteDelAreaChicaInf(final Position posicion) {
    if (posicion.getX() == AREA_CHICA_INF[X1] || posicion.getX() == AREA_CHICA_INF[X3] ||
        posicion.getY() == AREA_CHICA_INF[Y1] || posicion.getY() == AREA_CHICA_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElLimiteDelAreaGrandeInf(final Position posicion) {
    if (posicion.getX() == AREA_GRANDE_INF[X1] || posicion.getX() == AREA_GRANDE_INF[X3] ||
        posicion.getY() == AREA_GRANDE_INF[Y1] || posicion.getY() == AREA_GRANDE_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

}