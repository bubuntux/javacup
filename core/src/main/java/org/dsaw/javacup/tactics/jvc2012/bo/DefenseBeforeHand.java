/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2012.bo;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jsebas
 */
public class DefenseBeforeHand implements SoccerStrategy {

    private GameSituations gameSituation;
    private int[] indexDefenses;
    private int indexGoalKeeper;

    public DefenseBeforeHand(GameInformation data, int indexGoalKeeper, int[] indexDefenses) {

        this.indexGoalKeeper = indexGoalKeeper;
        this.indexDefenses = indexDefenses;
    }

    @Override
    public void setCurrentGameSituations(GameSituations gameSituation) {
        this.gameSituation = gameSituation;
    }

    @Override
    public List<Command> makeStrategy() {
        List<Command> commands = new ArrayList<>();

        int freeDefenses[] = getFreeDefenses(gameSituation);
        Position[] closerRivalPositions = getRivalsPositionsAroundGoalKeeper(gameSituation);
        Position[] myPlayersPositions = gameSituation.myPlayers();

        for (Position rivalPosition : closerRivalPositions) {

            if (freeDefenses.length > 0) {
                Position nearest = null;
                int iNearest = -1;

                for (int i = 0; i < freeDefenses.length; i++) {
                    int iFreeDefense = freeDefenses[i];
                    Position iDefensePosition = myPlayersPositions[iFreeDefense];

                    if (i == 0) {
                        nearest = iDefensePosition;
                        iNearest = 0;
                    } else {
                        if (rivalPosition.distance(nearest) > rivalPosition.distance(iDefensePosition)) {
                            nearest = iDefensePosition;
                            iNearest = i;
                        }
                    }
                }

                if (iNearest != -1) {
                    Position coverPosition;

                    if (rivalPosition.getY() > 0) {
                        
                        double posDefaultY = myPlayersPositions[indexGoalKeeper].getY()/2;
                        coverPosition = new Position(gameSituation.ballPosition().getX(), posDefaultY);
                        
                    } else {
                        coverPosition = new Position(rivalPosition.getX(), rivalPosition.getY() - Constants.JUGADORES_SEPARACION);
                    }

                    commands.add(new CommandMoveTo(freeDefenses[iNearest], coverPosition));
                }

                freeDefenses = ChimueloTacticUtils.getLessOneIndex(freeDefenses, iNearest);
            }
        }

        return commands;
    }

    private int[] getFreeDefenses(GameSituations gs) {

        int[] recuperadores = gs.getRecoveryBall();

        if ((recuperadores.length == 0) || (recuperadores.length > 1 && gs.iteration() + 15 < recuperadores[0])) {
            return this.indexDefenses;

        } else {
            List<Integer> freeDefensesList = new LinkedList<>();

            for (int i = 0; i < this.indexDefenses.length; i++) {

                //is available ?
                int j = 1;
                boolean found = false;
                while (j < recuperadores.length && !found) {

                    if (recuperadores[j] == this.indexDefenses[i]) {
                        found = true;
                    }
                    j++;
                }

                if (!found) {
                    freeDefensesList.add(this.indexDefenses[i]);
                }
            }

            //just shit
            int[] res = new int[freeDefensesList.size()];
            for (int i = 0; i < freeDefensesList.size(); i++) {
                res[i] = freeDefensesList.get(i);
            }

            return res;
        }
    }

    private Position[] getRivalsPositionsAroundGoalKeeper(GameSituations gs) {

        Position[] rivalPositionPlayers = gs.rivalPlayers();
        Position[] myPositionPlayers = gs.myPlayers();
        Position posGoalKeeper = myPositionPlayers[indexGoalKeeper];

        for (int pivot = 0; pivot < rivalPositionPlayers.length - 1; pivot++) {

            for (int i = pivot + 1; i < rivalPositionPlayers.length; i++) {

                if (rivalPositionPlayers[i].distance(posGoalKeeper) < rivalPositionPlayers[pivot].distance(posGoalKeeper)) {

                    Position tempPosition = rivalPositionPlayers[pivot];
                    rivalPositionPlayers[pivot] = rivalPositionPlayers[i];
                    rivalPositionPlayers[i] = tempPosition;
                }
            }
        }

        return rivalPositionPlayers;
    }
}
