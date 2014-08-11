package org.dsaw.javacup.model.engine;


import org.dsaw.javacup.model.ITeam;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.util.Position;

import java.util.List;

/**
 * Implementacion de una mascara de otra tactica, usada intenamente
 */
final class TacticImpl implements Tactic {

  final Tactic tac;
  final TacticaDetalleImpl detalle;

  public TacticImpl(Tactic tac) {
    this.tac = tac;
    detalle = new TacticaDetalleImpl(tac.getDetail());
  }

  @Override
  public ITeam getDetail() {
    return detalle;
  }

  @Override
  public List<Command> execute(GameSituations sp) {
    return tac.execute(sp);
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return tac.getStartPositions(sp);
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return tac.getNoStartPositions(sp);
  }
}
