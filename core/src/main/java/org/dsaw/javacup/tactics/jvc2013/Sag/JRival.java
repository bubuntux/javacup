/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.Sag;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.util.Position;

/**
 * @author pdsanchez
 */
public class JRival extends Jugador {

  private boolean portero = false;

  public JRival(int idx, PlayerDetail detalle, Position posRef) {
    super(idx, detalle, posRef);
    this.setTipo(Jugador.TIPO_RIVAL);
  }

  public JRival(int idx, PlayerDetail detalle) {
    super(idx, detalle);
    this.setTipo(Jugador.TIPO_RIVAL);
  }

  public JRival(int idx, PlayerDetail detalle, boolean portero) {
    super(idx, detalle);
    this.setTipo(Jugador.TIPO_RIVAL);
    this.portero = portero;
  }

  @Override
  public boolean isPortero() {
    return portero;
  }

  @Override
  public Command irAPosicionDestino() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Command GolpearBalon() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
