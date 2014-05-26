/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tacticas.jvc2012.bo;

import java.util.LinkedList;
import java.util.List;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

/**
 *
 * @author jsebas
 */
public class ChimuelosTactic implements Tactic {

    private Position[] alineacionSacando;
    private Position[] alineacionRecibiendo;
   
    private TacticDetail detalle = new TacticSpecification();
    private List<Command> commands = new LinkedList<Command>();
    
    private int indexGoalKeeper;
    private int[] indexDefenses = {1, 2, 3, 4};
    private int[] indexCentralCamps = {5, 6};
    
    private GameInformation data;
    
    private SoccerStrategy defenseStrategy;
    private SoccerStrategy cornerStrategy;
    private SoccerStrategy goalKeeperStrategy;
    private SoccerStrategy moveStrategy;
    private SoccerStrategy passStrategy;

    public ChimuelosTactic() {
        fillAlineaciones();

        this.data = new GameInformation();
        this.goalKeeperStrategy = new GoalkeeperStrategy(indexGoalKeeper, indexCentralCamps);
        this.cornerStrategy = new CornerStrategy();
        this.defenseStrategy = new DefenseBeforeHand(data, indexGoalKeeper, indexDefenses);
        this.moveStrategy = new MoveToAttackStrategy(data, indexGoalKeeper);
        this.passStrategy = new PassStrategy();
    }

    @Override
    public List<Command> execute(GameSituations currentSituation) {
        commands.clear();
        data.update(currentSituation);
        
        this.moveStrategy.setCurrentGameSituations(currentSituation);
        this.commands.addAll(this.moveStrategy.makeStrategy());
        
        this.passStrategy.setCurrentGameSituations(currentSituation);
        this.commands.addAll(this.passStrategy.makeStrategy());

        this.getDefenseStrategy().setCurrentGameSituations(currentSituation);
        this.commands.addAll(this.getDefenseStrategy().makeStrategy());

        this.cornerStrategy.setCurrentGameSituations(currentSituation);
        this.commands.addAll(this.cornerStrategy.makeStrategy());
        
        this.goalKeeperStrategy.setCurrentGameSituations(currentSituation);
        this.commands.addAll(this.goalKeeperStrategy.makeStrategy());

        return commands;
    }

    private void fillAlineaciones() {

        alineacionSacando = new Position[] {
            new Position(0.2595419847328244, -50.41044776119403),
            new Position(-20.685314685314687, -31.119909502262445),
            new Position(0.2595419847328244, -31.082089552238806),
            new Position(19.984732824427482, -31.6044776119403),
            new Position(7.526717557251908, -11.753731343283583),
            new Position(-8.564885496183205, -11.753731343283583),
            new Position(-31.146853146853147, -7.601809954751132),
            new Position(28.76923076923077, -8.314479638009049),
            new Position(-16.643356643356643, -1.1877828054298643),
            new Position(-0.4755244755244755, -4.038461538461538),
            new Position(16.167832167832167, -2.1380090497737556)};

        alineacionRecibiendo = new Position[] {
            new Position(0.2595419847328244, -50.41044776119403),
            new Position(-11.16030534351145, -31.082089552238806),
            new Position(11.174825174825173, -29.21945701357466),
            new Position(25.916083916083913, -17.104072398190045),
            new Position(-27.34265734265734, -17.816742081447966),
            new Position(-1.4265734265734267, -25.893665158371043),
            new Position(-15.692307692307693, -2.1380090497737556),
            new Position(16.167832167832167, -0.9502262443438915),
            new Position(-7.37062937062937, -7.601809954751132),
            new Position(0.4755244755244755, -9.264705882352942),
            new Position(10.6993006993007, -8.314479638009049)
        };
    }

    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return alineacionSacando;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacionRecibiendo;
    }

    public SoccerStrategy getDefenseStrategy() {
        return defenseStrategy;
    }

    public void setDefenseStrategy(SoccerStrategy defenseStrategy) {
        this.defenseStrategy = defenseStrategy;
    }
}