package org.dsaw.javacup.tacticas.jvc2012.losjavatos;

import java.util.LinkedList;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

public class Attack {
    GameSituations atSp;
    LinkedList <Command> comandos = new LinkedList<>();
    static int porContra = -1;
    public Attack(GameSituations sp) {
        atSp = sp;
    }
    public void ataque1() {
        Common c = new Common(atSp);
        c.pC();
        if (c.paC()) {}
        if (c.paNC()) c.pCA();
        if (c.tBT()) { c.tAP();}
        this.s1();
        c.bB();
        c.aPB();
        c.mP();
        comandos.addAll(c.comCom);
    }
    private void s1() {
        comandos.add(new CommandMoveTo(1,new Position(20,this.delAde()-1)));
        comandos.add(new CommandMoveTo(2,new Position(7,this.delAde()-1)));
        comandos.add(new CommandMoveTo(3,new Position(-7,this.delAde()-1)));
        comandos.add(new CommandMoveTo(4,new Position(-20,this.delAde()-1)));

        comandos.add(new CommandMoveTo(5, new Position(0,10)));
        comandos.add(new CommandMoveTo(6,new Position(-5,47)));
        comandos.add(new CommandMoveTo(10, new Position(15,40)));
        comandos.add(new CommandMoveTo(9, new Position(-15,40)));
        comandos.add(new CommandMoveTo(8, new Position(10,20)));
        comandos.add(new CommandMoveTo(7, new Position(-10,20)));
    }
    private double delAde() {
        double delY = 52;
        for (int i=0;i<11;i++) {
            if (atSp.rivalPlayers()[i].getY() < delY) {
                delY = atSp.rivalPlayers()[i].getY();
            }
        }
        return delY;
    }
}
    
    
    
