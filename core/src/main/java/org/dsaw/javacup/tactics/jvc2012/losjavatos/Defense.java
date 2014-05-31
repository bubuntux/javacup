package org.dsaw.javacup.tactics.jvc2012.losjavatos;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.LinkedList;

public class Defense {

  GameSituations sp;
  LinkedList<Command> comandos = new LinkedList<>();

  public Defense(GameSituations sp) {
    this.sp = sp;
  }

  public void tactic1() {
    Common c = new Common(sp);
    c.pCA();
    if (c.pum()) {
    }
    this.b1();
    c.mP();
    c.bB();
    c.aPB();
    c.mH();
    comandos.addAll(c.comCom);
  }

  private void b1() {
    comandos.add(new CommandMoveTo(5, new Position(-10, -20)));
    comandos.add(new CommandMoveTo(6, new Position(10, -20)));
    comandos.add(new CommandMoveTo(10, new Position(-13, 12)));
    comandos.add(new CommandMoveTo(9, new Position(13, 12)));
    comandos.add(new CommandMoveTo(8, new Position(-15, 0)));
    comandos.add(new CommandMoveTo(7, new Position(15, 0)));
  }
}
