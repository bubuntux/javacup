/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.bo;

import java.util.ArrayList;
import java.util.List;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

/**
 * @author jsebas
 */
public class MoveToAttackStrategy implements SoccerStrategy {

    private GameSituations currentSituation;
    private Position[] alineacionNormal;
    private int indexGoalKeeper;
    private GameInformation data;

    public MoveToAttackStrategy(GameInformation data, int indexGoalKeeper) {
        this.data = data;
        this.indexGoalKeeper = indexGoalKeeper;
        
        alineacionNormal = new Position[] {
             new Position(0.2595419847328244,-50.41044776119403),
            new Position(-11.16030534351145,-31.082089552238806),
            new Position(11.16030534351145,-31.6044776119403),
            new Position(-0.23776223776223776,-14.96606334841629),
            new Position(-23.776223776223777,-7.126696832579185),
            new Position(12.601398601398602,5.226244343891403),
            new Position(-7.846153846153847,13.303167420814479),
            new Position(30.909090909090907,18.766968325791854),
            new Position(-25.678321678321677,25.418552036199095),
            new Position(10.937062937062937,29.21945701357466),
            new Position(-12.125874125874127,34.920814479638004)
        };
    }
    
    @Override
    public List<Command> makeStrategy() {
        List<Command> commands = new ArrayList<>();
        Position[] newPositions = calculatePositions(alineacionNormal);
        
        for (int i = 0; i < newPositions.length; i++) {
            if(i != indexGoalKeeper) {
                commands.add(new CommandMoveTo(i, newPositions[i]));
            }
        }
        
        int iterToGetBall = data.getIterToBall();
        if(iterToGetBall >= 0) {
            commands.add(new CommandMoveTo(data.getPlayerCloserToBall(), data.getBallPosition(iterToGetBall)));
        }
        
        return commands;
    }

    private Position[] calculatePositions(Position[] positions) {
        Position ballPosition = currentSituation.ballPosition();

        double height = Math.max(Constants.LARGO_CAMPO_JUEGO / 3
                , 2 * Math.min(Math.abs(Constants.centroArcoInf.getY() - ballPosition.getY())
                                , Math.abs(Constants.centroArcoSup.getY() - ballPosition.getY())));
        
        double width = Math.max(Constants.ANCHO_CAMPO_JUEGO * 2 / 3
                , 2 * Math.min(Math.abs(Constants.cornerInfIzq.getX() - ballPosition.getX())
                                , Math.abs(Constants.cornerInfDer.getX() - ballPosition.getX())));

        Position center = new Position(ballPosition);
        if (center.getX() + width / 2 > Constants.cornerInfDer.getX()) {
            center = center.movePosition(-(center.getX() + width / 2 - Constants.cornerInfDer.getX()), 0);
        }
        if (center.getX() - width / 2 < Constants.cornerInfIzq.getX()) {
            center = center.movePosition(Constants.cornerInfIzq.getX() - (center.getX() - width / 2), 0);
        }
        if (center.getY() + height / 2 > Constants.centroArcoSup.getY()) {
            center = center.movePosition(0, -(center.getY() + height / 2 - Constants.centroArcoSup.getY()));
        }
        if (center.getY() - height / 2 < Constants.centroArcoInf.getY()) {
            center = center.movePosition(0, Constants.centroArcoInf.getY() - (center.getY() - height / 2));
        }

        Position[] newPositions = new Position[ChimueloTacticUtils.NUM_OF_PLAYERS];
        for (int i = 0; i < newPositions.length; i++) {
            newPositions[i] = new Position(positions[i].getX() * width / Constants.ANCHO_CAMPO_JUEGO + center.getX()
                    , positions[i].getY() * height / Constants.LARGO_CAMPO_JUEGO + center.getY());
        }

        return newPositions;
    }
    
    @Override
    public void setCurrentGameSituations(GameSituations sp) {
        this.currentSituation = sp;
    }
}
