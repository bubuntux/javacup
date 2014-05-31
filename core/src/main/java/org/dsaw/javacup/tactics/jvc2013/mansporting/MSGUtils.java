/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Circulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Rectangulo;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Segmento;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author MaN
 */
public final class MSGUtils {

  private MSGUtils() {
  }

  public static <E extends MSGJugadorDetalle> E[] convertJugadores(Collection<E> jugadores) {
    E[] result = (E[]) new MSGJugadorDetalle[jugadores.size()];
    for (E detalle : jugadores) {
      result[detalle.getIndex()] = detalle;
    }
    return result;
  }

  public static Position[] convertPosicion(Map<Integer, MSGAlineacionPosicion> posiciones) {
    Position[] result = new Position[posiciones.size()];
    for (Map.Entry<Integer, MSGAlineacionPosicion> entry : posiciones.entrySet()) {
      result[entry.getKey()] = new Position(
          entry.getValue().getPosicion().getX(),
          entry.getValue().getPosicion().getY());
    }
    return result;
  }

  public static boolean getAguienPuedeRematar(
      Collection<? extends MSGJugadorDetalle> situacionJugadores) {
    for (MSGJugadorDetalle situacion : situacionJugadores) {
      if (situacion.getPuedeRematar()) {
        return true;
      }
    }
    return false;
  }

  public static boolean getAccesible(MSGPuntoTrayectoria posicionBalon, int iteraciones,
                                     MSGJugadorDetalle jugador) {
    if (!jugador.getPuedeRematar(iteraciones)) {
      return false;
    }

    Circulo areaCubierta = jugador.getAreaCubierta(iteraciones);
    if (posicionBalon instanceof MSGPuntoTrayectoria.Punto3DErrorTrayectoria) {
      MSGPuntoTrayectoria.Punto3DErrorTrayectoria
          tray = (MSGPuntoTrayectoria.Punto3DErrorTrayectoria) posicionBalon;
      boolean accesible = false;
      if (getAccesible(tray.getPunto(), areaCubierta, jugador)) {
        accesible = true;
      } else if (getAccesible(tray.getPuntoMax(), areaCubierta, jugador)) {
        accesible = true;
      } else if (getAccesible(tray.getPuntoMin(), areaCubierta, jugador)) {
        accesible = true;
      }
      return accesible;
    } else {
      return getAccesible(posicionBalon.getPunto(), areaCubierta, jugador);
    }
  }

  public static <E> List<E> filter(List<E> list, Comparator<E> comparator, double percentaje) {
    if (list.isEmpty()) {
      return list;
    }

    List<E> bufferList = new ArrayList<>(list);
    Collections.sort(bufferList, comparator);

    int maxIndex = Math.round((float) (percentaje * bufferList.size()));
    maxIndex = maxIndex <= 0 ? 1 : maxIndex;
    return bufferList.subList(0, maxIndex);
  }

  public static boolean getAccesible(Punto3D posicionBalon, Circulo areaCubierta,
                                     MSGJugadorDetalle jugador) {
    double distancia = TrigonometriaUtils
        .getDistancia(areaCubierta.getCentro(), posicionBalon.getPuntoXY());
    if (distancia <= areaCubierta.getRadio() + Constants.RADIO_BALON) {
      if (posicionBalon.getZ() <= jugador.getAltoCubierto()) {
        return true;
      }
    }
    return false;
  }

  public static List<Punto> getPunto(MSGJugadorDetalle jugador, MSGBalon balon, Punto destino) {
    List<Punto> puntos = new LinkedList<>();
    double distancia = TrigonometriaUtils.getDistancia(balon.getPosicion().getPuntoXY(), destino);
    double
        anguloHorizontal =
        TrigonometriaUtils.getAngulo(balon.getPosicion().getPuntoXY(), destino);
    double e = Constants.getErrorAngular(jugador.getEstadisticas().getPrecision());
    double anguloHorizontalMaximo = anguloHorizontal + (e / 2) * Math.PI;
    double anguloHorizontalMinimo = anguloHorizontal - (e / 2) * Math.PI;

    puntos.add(
        new Punto(balon.getPosicion().getPuntoXY().getX() + distancia * Math.cos(anguloHorizontal),
                  destino.getY()));
    puntos.add(new Punto(
        balon.getPosicion().getPuntoXY().getX() + distancia * Math.cos(anguloHorizontalMaximo),
        destino.getY()));
    puntos.add(new Punto(
        balon.getPosicion().getPuntoXY().getX() + distancia * Math.cos(anguloHorizontalMinimo),
        destino.getY()));
    return puntos;
  }

  public static Punto getPuntoMasCecano(Segmento segmento, Punto balon, Rectangulo rectangulo) {
    Punto
        punto1 =
        new Punto(rectangulo.getCentro().getX() - rectangulo.getAncho() / 2,
                  rectangulo.getCentro().getY() + rectangulo.getAlto() / 2);
    Punto
        punto2 =
        new Punto(rectangulo.getCentro().getX() + rectangulo.getAncho() / 2,
                  rectangulo.getCentro().getY() + rectangulo.getAlto() / 2);
    Punto
        punto3 =
        new Punto(rectangulo.getCentro().getX() + rectangulo.getAncho() / 2,
                  rectangulo.getCentro().getY() - rectangulo.getAlto() / 2);
    Punto
        punto4 =
        new Punto(rectangulo.getCentro().getX() - rectangulo.getAncho() / 2,
                  rectangulo.getCentro().getY() - rectangulo.getAlto() / 2);

    Punto result = null;
    List<Segmento>
        segmentos =
        Arrays.asList(new Segmento(punto1, punto2), new Segmento(punto2, punto3),
                      new Segmento(punto3, punto4), new Segmento(punto4, punto1));
    for (Segmento segmentoRectangulo : segmentos) {
      Punto i = TrigonometriaUtils.getInterseccion(segmento, segmentoRectangulo);
      if (i != null) {
        if (result == null) {
          result = i;
        } else {
          double d1 = TrigonometriaUtils.getDistancia(i, balon);
          double d2 = TrigonometriaUtils.getDistancia(result, balon);
          if (d1 < d2) {
            result = i;
          }

        }
      }
    }
    return result;
  }

  public static boolean isEnTerrenoJuego(Punto punto) {
    return punto.getX() > -Constants.ANCHO_CAMPO_JUEGO / 2
           && punto.getX() < Constants.ANCHO_CAMPO_JUEGO / 2
           && punto.getY() > -Constants.LARGO_CAMPO_JUEGO / 2
           && punto.getY() < Constants.LARGO_CAMPO_JUEGO / 2;
  }

  public static Punto situaEnTerrenoJuego(Punto punto) {
    double
        px =
        Math.max(-Constants.ANCHO_CAMPO_JUEGO / 2,
                 Math.min(Constants.ANCHO_CAMPO_JUEGO / 2, punto.getX()));
    double
        py =
        Math.max(-Constants.LARGO_CAMPO_JUEGO / 2,
                 Math.min(Constants.LARGO_CAMPO_JUEGO / 2, punto.getY()));
    return new Punto(px, py);
  }

  public static boolean isPaseValido(Punto punto) {
    return punto.getX() > -Constants.ANCHO_CAMPO_JUEGO / 2 + Constants.ANCHO_AREA_CHICA
           && punto.getX() < Constants.ANCHO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA
           && punto.getY() > -Constants.LARGO_CAMPO_JUEGO / 2 + Constants.ANCHO_AREA_CHICA
           && punto.getY() < Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA;
  }

  /**
   * Deuelve los jugadores a los que se peude pasar
   *
   * @param jugadorPasador jugador que va a realizar el pase
   * @return lista d ejugadores que se tienen que desmarcar.
   */
  public static List<MSGJugadorPropio> getJugadoresADesmarcar(MSGJugadorPropio jugadorPasador,
                                                              Map<Integer, MSGJugadorPropio> jugadoresPropios) {
    List<MSGJugadorPropio> jugadoresADesmarcar = new LinkedList<>();
    switch (jugadorPasador.getIndex()) {
      case 0:
        jugadoresADesmarcar.add(jugadoresPropios.get(1));
        jugadoresADesmarcar.add(jugadoresPropios.get(2));
        jugadoresADesmarcar.add(jugadoresPropios.get(3));
        break;
      case 1:
        jugadoresADesmarcar.add(jugadoresPropios.get(2));
        jugadoresADesmarcar.add(jugadoresPropios.get(4));
        jugadoresADesmarcar.add(jugadoresPropios.get(5));
        break;
      case 2:
        jugadoresADesmarcar.add(jugadoresPropios.get(1));
        jugadoresADesmarcar.add(jugadoresPropios.get(3));
        jugadoresADesmarcar.add(jugadoresPropios.get(4));
        jugadoresADesmarcar.add(jugadoresPropios.get(5));
        jugadoresADesmarcar.add(jugadoresPropios.get(6));
        break;
      case 3:
        jugadoresADesmarcar.add(jugadoresPropios.get(2));
        jugadoresADesmarcar.add(jugadoresPropios.get(5));
        jugadoresADesmarcar.add(jugadoresPropios.get(6));
        break;
      case 4:
        jugadoresADesmarcar.add(jugadoresPropios.get(5));
        jugadoresADesmarcar.add(jugadoresPropios.get(7));
        jugadoresADesmarcar.add(jugadoresPropios.get(8));
        break;
      case 5:
        jugadoresADesmarcar.add(jugadoresPropios.get(4));
        jugadoresADesmarcar.add(jugadoresPropios.get(6));
        jugadoresADesmarcar.add(jugadoresPropios.get(7));
        jugadoresADesmarcar.add(jugadoresPropios.get(8));
        jugadoresADesmarcar.add(jugadoresPropios.get(9));
        break;
      case 6:
        jugadoresADesmarcar.add(jugadoresPropios.get(5));
        jugadoresADesmarcar.add(jugadoresPropios.get(8));
        jugadoresADesmarcar.add(jugadoresPropios.get(9));
        break;
      case 7:
        jugadoresADesmarcar.add(jugadoresPropios.get(8));
        jugadoresADesmarcar.add(jugadoresPropios.get(10));
        break;
      case 8:
        jugadoresADesmarcar.add(jugadoresPropios.get(7));
        jugadoresADesmarcar.add(jugadoresPropios.get(9));
        jugadoresADesmarcar.add(jugadoresPropios.get(10));
        break;
      case 9:
        jugadoresADesmarcar.add(jugadoresPropios.get(8));
        jugadoresADesmarcar.add(jugadoresPropios.get(10));
        break;
      case 10:
        jugadoresADesmarcar.add(jugadoresPropios.get(7));
        jugadoresADesmarcar.add(jugadoresPropios.get(8));
        jugadoresADesmarcar.add(jugadoresPropios.get(9));
        break;
    }
    jugadoresADesmarcar.remove(jugadorPasador);
    return jugadoresADesmarcar;
  }

  /**
   * Deuelve los jugadores a los que se peude pasar
   *
   * @param jugadorPasador jugador que va a realizar el pase
   * @return lista d ejugadores que se tienen que desmarcar.
   */
  public static List<MSGJugadorPropio> getJugadorADespejar(MSGJugadorPropio jugadorPasador,
                                                           Map<Integer, MSGJugadorPropio> jugadoresPropios) {
    List<MSGJugadorPropio> puntosDespejar = new LinkedList<>();
    switch (jugadorPasador.getIndex()) {
      case 0:
        puntosDespejar.add(jugadoresPropios.get(4));
        puntosDespejar.add(jugadoresPropios.get(5));
        puntosDespejar.add(jugadoresPropios.get(6));
        break;
      case 1:
      case 2:
      case 3:
        puntosDespejar.add(jugadoresPropios.get(7));
        puntosDespejar.add(jugadoresPropios.get(8));
        puntosDespejar.add(jugadoresPropios.get(9));
        break;
      case 4:
      case 5:
      case 6:
        puntosDespejar.add(jugadoresPropios.get(10));
        break;
      default:
        break;
    }
    return puntosDespejar;
  }

  /**
   * Devuelve los puntos donde se puede desmarcar un juegador.
   *
   * @return mapa con los jugadores y los puntos a tener en cuenta
   * @
   */
  public static Map<MSGJugadorPropio, List<Punto3D>> getPuntosDesmarque(
      List<MSGJugadorPropio> jugadores) {
    if (jugadores.isEmpty()) {
      return Collections.emptyMap();
    } else {
      double resolucion = MSGConstants.DESMARQUE_RESOLUCION.get(jugadores.size());
      Map<MSGJugadorPropio, List<Punto3D>>
          jugadoresADesmarcar =
          new HashMap<>();
      for (MSGJugadorPropio jugador : jugadores) {
        Rectangulo areaCubierta = jugador.getAlineacion().getArea();
        List<Punto3D> puntosAComprobar = new LinkedList<>();

        for (double x = areaCubierta.getCentro().getX() - areaCubierta.getAncho() / 2;
             x <= areaCubierta.getCentro().getX() + areaCubierta.getAncho() / 2; x += resolucion) {
          for (double y = areaCubierta.getCentro().getY() - areaCubierta.getAlto() / 2;
               y <= areaCubierta.getCentro().getY() + areaCubierta.getAlto() / 2; y += resolucion) {
            puntosAComprobar.add(new Punto3D(x, y, 0));
          }
        }
        jugadoresADesmarcar.put(jugador, puntosAComprobar);
      }
      return jugadoresADesmarcar;
    }
  }

  /**
   * Devuelve la probabilidad de control.
   *
   * @param punto  punto.
   * @param punto0 punto anterior.
   * @return probabilidad de control,
   */
  public static double getProbabilidadControl(Punto3D punto, Punto3D punto0) {
    if (punto0 == null) {
      return 1;
    }

    double dx = punto.getX() - punto0.getX();
    double dy = punto.getY() - punto0.getY();

    double velocidadH = TrigonometriaUtils.getModuloVector(new Punto(dx, dy));
    double velocidadV = punto.getZ() - punto0.getZ();

    double vel = Math.sqrt(velocidadH * velocidadH + velocidadV * velocidadV);
    return (7d - vel) / 7d;
  }

  public static int getIteracionesMaximas(int iteracionesInicio, int iteracionesFin,
                                          GameSituations situacionPartido) {
    for (int i = iteracionesInicio; i < iteracionesFin - 1; i++) {
      Punto3D punto0 = new Punto3D(situacionPartido.getTrajectory(i));
      Punto3D punto1 = new Punto3D(situacionPartido.getTrajectory(i + 1));

      if (punto0.equals(punto1)) {
        return i;
      }

    }
    return iteracionesFin;
  }
}
