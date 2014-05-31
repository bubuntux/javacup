package org.dsaw.javacup.tactics.jvc2012.chr2012.accion;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.tactics.jvc2012.chr2012.tactica.utilidades.Jugador;


/**
 * Clase que implementa el interfaz Action y que representa la acci�n de pase al area del jugador.
 *
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class AccionPasarAlArea implements Accion {

  private Command comando;
  private Jugador jugadorDestino;


  public AccionPasarAlArea(Command comando, Jugador jugadorDestino) {

    this.comando = comando;
    this.jugadorDestino = jugadorDestino;
  }


  @Override
  public Command comando() {
    return comando;
  }

  public Jugador jugadorDestino() {
    return jugadorDestino;
  }
}
