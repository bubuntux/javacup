package org.dsaw.javacup.tactics.jvc2013.Mou_team;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.List;

public class Mou_Team implements Tactic {

  public Mou_Team() {
    this.laTactica = new Tactica_Mou_Team();
    this.detalle = new TacticaDetalleImpl();
  }

  public Mou_Team(double prob, double prob2) {
    this.laTactica = new Tactica_Mou_Team(prob, prob2);
    this.detalle = new TacticaDetalleImpl();
  }

  private Tactica_Mou_Team laTactica;
  private TacticDetail detalle;

  @Override
  public TacticDetail getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return laTactica.getStartPositions(sp);
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return laTactica.getNoStartPositions(sp);
  }

  @Override
  public List<Command> execute(GameSituations sp) {
    return laTactica.generarTactica(sp);
  }

  public int getGolesCulpaDistancia() {
    return laTactica.golCulpaMiaDistancia;
  }

  public int getGolesCulpaAltura() {
    return laTactica.golCulpaMiaAltura;

  }
}
