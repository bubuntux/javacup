/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.ifox.estado;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Estado;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Mensaje;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Mensajero;
import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Jugador;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Parametros;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Vector2d;

/**
 * @author Usuario
 */
public class Esperando extends Estado<Jugador> {

  private Esperando() {
  }

  public static Esperando getInstance() {
    return EsperandoHolder.INSTANCE;
  }

  @Override
  public void ejecutar(Jugador jugador) {
    jugador.irAObjetivo(jugador.getEquipo().getAlineacion()
                            .getPosicion(jugador.getId()));

    if (jugador.isJugadorAyudante()) {
      jugador.irAObjetivo(jugador.getEquipo()
                              .getMejorPosicionDeAyuda());
    }

    if (jugador.getRol() == Jugador.Rol.PORTERO) {
      Vector2d posicionBalon = new Vector2d(jugador.getEquipo().getSituacion().ballPosition());
      Vector2d centroArco = new Vector2d(Constants.centroArcoInf);
      Vector2d haciaBalon = posicionBalon.resta(centroArco);
      haciaBalon = haciaBalon.normal().productoEscalar(6.0);
      jugador.irAObjetivo(haciaBalon.suma(centroArco).toPosicion());
    }

    jugador.recuperarBalon();

    if (jugador.isBalonEnRangoDeControl()) {
      jugador.getEquipo().setJugadorControlador(jugador);
      jugador.getEquipo().setJugadorAAyudar(jugador);
      jugador.buscarAyuda();

      jugador.golpearBalon(Constants.centroArcoSup, false);

      jugador.regatear();

      Position posicionPase = jugador.getEquipo().buscarPase(jugador,
                                                             Parametros.Jugador.DISTANCIA_MINIMA_PASE);
      if (jugador.isAmenazado(Parametros.Jugador.AREA_SEGURA)
          && posicionPase != null
          && posicionPase.isInsideGameField(0)) {
        jugador.golpearBalon(posicionPase, false);
        Jugador receptor = jugador.getEquipo().jugadorMasCercano(posicionPase);
        Mensajero.getInstancia()
            .entregarMensaje(Mensajero.INMEDIATO, jugador, receptor, Mensaje.RECIBIR_BALON,
                             posicionPase);
      }

      if (jugador.getRol() == Jugador.Rol.DEFENSOR
          && jugador.isAmenazado(Parametros.Jugador.AREA_SEGURA_DEFENSA)) {
        posicionPase = jugador.getEquipo().buscarPase(jugador,
                                                      Constants.LARGO_CAMPO_JUEGO / 2.0);
        if (posicionPase != null && posicionPase.isInsideGameField(0)) {
          jugador.golpearBalon(posicionPase, true);
        } else {
          jugador.golpearBalon(Constants.centroArcoSup, true);
        }
      }

      if (jugador.getRol() == Jugador.Rol.PORTERO
          && jugador.isAmenazado(Parametros.Jugador.AREA_SEGURA_PORTERO)) {
        posicionPase = jugador.getEquipo().buscarPase(jugador,
                                                      Constants.LARGO_CAMPO_JUEGO / 2.0);
        if (posicionPase != null && posicionPase.isInsideGameField(0)) {
          jugador.golpearBalon(posicionPase, true);
        } else {
          jugador.golpearBalon(Constants.centroArcoSup, true);
        }
      }

      Position
          posicionDisparo =
          jugador.getEquipo()
              .puedeRematar(jugador, 1.0, Parametros.Jugador.DISTANCIA_REMATE_CUADRADA);
      if (posicionDisparo != null) {
        jugador.golpearBalon(posicionDisparo, false);
      }

      if (jugador.getRol() == Jugador.Rol.ATACANTE) {
        posicionDisparo =
            jugador.getEquipo()
                .puedeRematar(jugador, 1.0, Parametros.Jugador.DISTANCIA_REMATE_CUADRADA * 1.5);
        if (posicionDisparo != null) {
          jugador.golpearBalon(posicionDisparo, false);
        }
      }
    } else {
      jugador.getEquipo().setJugadorAAyudar(jugador);
      jugador.buscarAyuda();

      Position cubrir = jugador.cubrir();
      if (jugador.isEnRegionAlineacion(cubrir)
          && jugador.getRol() != Jugador.Rol.PORTERO) {
        jugador.irAObjetivo(cubrir);
      }
    }

    if (jugador.isJugadorAyudante()) {
      jugador.getEquipo().pedirPase(jugador);
    }

    if (jugador.getEquipo().isEnControl()) {
      Vector2d direccion = new Vector2d(jugador.evitarOponente());
      Vector2d miPosicion = new Vector2d(jugador.getPosicion());
      Vector2d posicion = miPosicion.suma(direccion);
      if (posicion.toPosicion().isInsideGameField(-10)) {
        jugador.irAObjetivo(posicion.toPosicion());
      }
    }

    if (jugador.getPosicionPase() != null) {
      jugador.irAObjetivo(jugador.getPosicionPase());
      Vector2d posicionJugador = new Vector2d(jugador.getPosicion());
      Vector2d posicionPase = new Vector2d(jugador.getPosicionPase());
      if (posicionJugador.distanciaCuadrada(posicionPase) < 0.4) {
        jugador.setPosicionPase(null);
      }
    }
  }

  private static class EsperandoHolder {

    private static final Esperando INSTANCE = new Esperando();
  }
}
