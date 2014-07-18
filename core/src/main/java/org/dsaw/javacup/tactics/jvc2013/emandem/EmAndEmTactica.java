package org.dsaw.javacup.tactics.jvc2013.emandem;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.emandem.alineaciones.Alineaciones;
import org.dsaw.javacup.tactics.jvc2013.emandem.configuracion.TacticaDetalleImpl;
import org.dsaw.javacup.tactics.jvc2013.emandem.controladores.CPU;
import org.dsaw.javacup.tactics.jvc2013.emandem.controladores.ControladorAlineaciones;

import java.util.List;


public class EmAndEmTactica implements Tactic {

  private Alineaciones alineaciones = new Alineaciones();
  private Team detalle = new TacticaDetalleImpl();
  private CPU cpu = new CPU();

  @Override
  public Team getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(final GameSituations situacionPartido) {
    ControladorAlineaciones.setProximaIteracionCambioAlineacion(0);
    return this.alineaciones.getAlineacion5();
  }

  @Override
  public Position[] getNoStartPositions(final GameSituations situacionPartido) {
    ControladorAlineaciones.setProximaIteracionCambioAlineacion(0);
    return this.alineaciones.getAlineacion6();
  }

  //Lista de comandos a ejecutar.
  @Override
  public List<Command> execute(final GameSituations situacionPartido) {
    return this.cpu.getComandosEjecutar(situacionPartido);
  }
}