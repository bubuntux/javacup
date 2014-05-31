package org.dsaw.javacup.tactics.jvc2013.emandem.cancha;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.emandem.enums.CUADRANTE_CANCHA;

public class CuadrantesCancha {

  private static CuadrantesCancha instance;

  //SUPERIOR
  //X1,Y1,X2,Y2,X3,Y3,X4,Y4
  private static final double[] CUADRANTE_1_SUP = new double[8];
  private static final double[] CUADRANTE_2_SUP = new double[8];
  private static final double[] CUADRANTE_3_SUP = new double[8];
  private static final double[] CUADRANTE_4_SUP = new double[8];
  //INFERIOR
  //X1,Y1,X2,Y2,X3,Y3,X4,Y4
  private static final double[] CUADRANTE_1_INF = new double[8];
  private static final double[] CUADRANTE_2_INF = new double[8];
  private static final double[] CUADRANTE_3_INF = new double[8];
  private static final double[] CUADRANTE_4_INF = new double[8];

  private static int X1 = 0;
  private static int Y1 = 1;
  private static int X2 = 2;
  private static int Y2 = 3;
  private static int X3 = 4;
  private static int Y3 = 5;
  private static int X4 = 6;
  private static int Y4 = 7;

  static {
    instance = new CuadrantesCancha();

    CUADRANTE_1_SUP[X1] = 0;
    CUADRANTE_1_SUP[Y1] = -Constants.LARGO_CAMPO_JUEGO / 2;
    CUADRANTE_1_SUP[X3] = -Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_1_SUP[Y3] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2) * -1;

    CUADRANTE_2_SUP[X1] = 0;
    CUADRANTE_2_SUP[Y1] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2) * -1;
    CUADRANTE_2_SUP[X3] = -Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_2_SUP[Y3] = 0;

    CUADRANTE_3_SUP[X1] = Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_3_SUP[Y1] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2) * -1;
    CUADRANTE_3_SUP[X3] = 0;
    CUADRANTE_3_SUP[Y3] = 0;

    CUADRANTE_4_SUP[X1] = Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_4_SUP[Y1] = -Constants.LARGO_CAMPO_JUEGO / 2;
    CUADRANTE_4_SUP[X3] = 0;
    CUADRANTE_4_SUP[Y3] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2) * -1;

    CUADRANTE_1_INF[X1] = 0;
    CUADRANTE_1_INF[Y1] = Constants.LARGO_CAMPO_JUEGO / 2;
    CUADRANTE_1_INF[X3] = -Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_1_INF[Y3] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2);

    CUADRANTE_2_INF[X1] = 0;
    CUADRANTE_2_INF[Y1] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2);
    CUADRANTE_2_INF[X3] = -Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_2_INF[Y3] = 0;

    CUADRANTE_3_INF[X1] = Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_3_INF[Y1] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2);
    CUADRANTE_3_INF[X3] = 0;
    CUADRANTE_3_INF[Y3] = 0;

    CUADRANTE_4_INF[X1] = Constants.ANCHO_CAMPO_JUEGO / 2;
    CUADRANTE_4_INF[Y1] = Constants.LARGO_CAMPO_JUEGO / 2;
    CUADRANTE_4_INF[X3] = 0;
    CUADRANTE_4_INF[Y3] = ((Constants.LARGO_CAMPO_JUEGO / 2) / 2);
  }

  private CuadrantesCancha() {

  }

  public static CuadrantesCancha getInstance() {
    return instance;
  }

  //coordenadas x,y,z
  public CUADRANTE_CANCHA getCuadrantePorPosicion(final Position posicion) {

    if (posicion.getX() <= CUADRANTE_1_SUP[X1] && posicion.getX() >= CUADRANTE_1_SUP[X3]
        && posicion.getY() >= CUADRANTE_1_SUP[Y1] && posicion.getY() <= CUADRANTE_1_SUP[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_1_SUP;
    } else if (posicion.getX() <= CUADRANTE_2_SUP[X1] && posicion.getX() >= CUADRANTE_2_SUP[X3]
               && posicion.getY() >= CUADRANTE_2_SUP[Y1]
               && posicion.getY() <= CUADRANTE_2_SUP[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_2_SUP;
    } else if (posicion.getX() <= CUADRANTE_3_SUP[X1] && posicion.getX() >= CUADRANTE_3_SUP[X3]
               && posicion.getY() >= CUADRANTE_3_SUP[Y1]
               && posicion.getY() <= CUADRANTE_3_SUP[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_3_SUP;
    } else if (posicion.getX() <= CUADRANTE_4_SUP[X1] && posicion.getX() >= CUADRANTE_4_SUP[X3]
               && posicion.getY() >= CUADRANTE_4_SUP[Y1]
               && posicion.getY() <= CUADRANTE_4_SUP[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_4_SUP;
    }

    if (posicion.getX() <= CUADRANTE_1_INF[X1] && posicion.getX() >= CUADRANTE_1_INF[X3]
        && posicion.getY() <= CUADRANTE_1_INF[Y1] && posicion.getY() >= CUADRANTE_1_INF[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_1_INF;
    } else if (posicion.getX() <= CUADRANTE_2_INF[X1] && posicion.getX() >= CUADRANTE_2_INF[X3]
               && posicion.getY() <= CUADRANTE_2_INF[Y1]
               && posicion.getY() >= CUADRANTE_2_INF[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_2_INF;
    } else if (posicion.getX() <= CUADRANTE_3_INF[X1] && posicion.getX() >= CUADRANTE_3_INF[X3]
               && posicion.getY() <= CUADRANTE_3_INF[Y1]
               && posicion.getY() >= CUADRANTE_3_INF[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_3_INF;
    } else if (posicion.getX() <= CUADRANTE_4_INF[X1] && posicion.getX() >= CUADRANTE_4_INF[X3]
               && posicion.getY() <= CUADRANTE_4_INF[Y1]
               && posicion.getY() >= CUADRANTE_4_INF[Y3]) {
      return CUADRANTE_CANCHA.CUADRANTE_4_INF;
    }

    return null;
  }


  public boolean estaEnElCuadrante1Sup(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_1_SUP[X1] && posicion.getX() >= CUADRANTE_1_SUP[X3] &&
        posicion.getY() >= CUADRANTE_1_SUP[Y1] && posicion.getY() <= CUADRANTE_1_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante2Sup(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_2_SUP[X1] && posicion.getX() >= CUADRANTE_2_SUP[X3] &&
        posicion.getY() >= CUADRANTE_2_SUP[Y1] && posicion.getY() <= CUADRANTE_2_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante3Sup(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_3_SUP[X1] && posicion.getX() >= CUADRANTE_3_SUP[X3] &&
        posicion.getY() >= CUADRANTE_3_SUP[Y1] && posicion.getY() <= CUADRANTE_3_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante4Sup(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_4_SUP[X1] && posicion.getX() >= CUADRANTE_4_SUP[X3] &&
        posicion.getY() >= CUADRANTE_4_SUP[Y1] && posicion.getY() <= CUADRANTE_4_SUP[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante1Inf(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_1_INF[X1] && posicion.getX() >= CUADRANTE_1_INF[X3] &&
        posicion.getY() <= CUADRANTE_1_INF[Y1] && posicion.getY() >= CUADRANTE_1_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante2Inf(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_2_INF[X1] && posicion.getX() >= CUADRANTE_2_INF[X3] &&
        posicion.getY() <= CUADRANTE_2_INF[Y1] && posicion.getY() >= CUADRANTE_2_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante3Inf(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_3_INF[X1] && posicion.getX() >= CUADRANTE_3_INF[X3] &&
        posicion.getY() <= CUADRANTE_3_INF[Y1] && posicion.getY() >= CUADRANTE_3_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public boolean estaEnElCuadrante4Inf(final Position posicion) {
    if (posicion.getX() <= CUADRANTE_4_INF[X1] && posicion.getX() >= CUADRANTE_4_INF[X3] &&
        posicion.getY() <= CUADRANTE_4_INF[Y1] && posicion.getY() >= CUADRANTE_4_INF[Y3]) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

}
