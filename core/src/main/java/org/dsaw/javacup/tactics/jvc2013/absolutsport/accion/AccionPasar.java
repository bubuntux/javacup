package org.dsaw.javacup.tactics.jvc2013.absolutsport.accion;


import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades.Jugador;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de pase del jugador.
 *
 * @author Christian Onwuzor Mart�n (chr -> airchris01@yahoo.es)
 */
public class AccionPasar implements Accion {

  private Command comando;
  private Jugador jugadorDestino;
  private Position posJugadorDestino;


  public AccionPasar(Command comando, Jugador jugadorDestino, Position posJugadorDestino) {

    this.comando = comando;
    this.jugadorDestino = jugadorDestino;
    this.posJugadorDestino = posJugadorDestino;
  }


  @Override
  public Command comando() {
    return comando;
  }

  public Jugador jugadorDestino() {
    return jugadorDestino;
  }

  public Position posJugadorDestino() {
    return posJugadorDestino;
  }

}
