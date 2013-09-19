/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.bo;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author jsebas
 */
public class GameInformation {

    private GameSituations currentSituation;
    private int iterToBall;
    private int playerCloserBall;
    private final double[][] ballPositions = new double[100][3];

    public GameInformation() {
        iterToBall = -2;
        playerCloserBall = -1;
    }

    public void update(GameSituations sp) {
        this.currentSituation = sp;
        
        for (int i = 0; i < 100; i++) {
            ballPositions[i] = sp.getTrajectory(i);
        }
        
        iterToBall = -2;
        playerCloserBall = -2;
    }

    public int getIterToBall() {
        if (iterToBall >= -1) {
            return iterToBall;
        }
        calculateIterToBallPlayerCloserBall();
        return iterToBall;
    }

    public int getPlayerCloserToBall() 
    {
        if (playerCloserBall >= -1) {
            return playerCloserBall;
        }
        calculateIterToBallPlayerCloserBall();
        return playerCloserBall;
    }

    private void calculateIterToBallPlayerCloserBall() 
    {  
        iterToBall = -1;
        double reachDistance, distanceToBall;
        
        PlayerDetail myDetails[] = currentSituation.myPlayersDetail();
        Position[] myPlayersPositions = currentSituation.myPlayers();
        
        int it = 0;
        boolean found = false;
        
        while ( it <= Constants.ITERACIONES && !found) 
        {
            Position ballPosition = getBallPosition(it);
            
            if (!ballPosition.isInsideGameField(2)) {
                iterToBall = -1;
                playerCloserBall = ballPosition.nearestIndex(myPlayersPositions);
                break;
            }
            
            if (getBallAltitude(it) <= Constants.ALTO_ARCO) 
            {
                for (int i = 0; i < myPlayersPositions.length; i++) 
                {
                    if (getBallAltitude(it) <= (myDetails[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON))
                    {
                        double playerVelocity = Constants.getVelocidad(myDetails[i].getSpeed());
                        reachDistance = it * playerVelocity + (myDetails[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        distanceToBall = myPlayersPositions[i].distance(ballPosition);
                        
                        if (reachDistance >= distanceToBall) {
                            found = true;
                            iterToBall = it;
                            playerCloserBall = i;
                        }
                    }
                }
            }
            it++;
        }
    }

    public Position getBallPosition(int iter) {
        Position ballPosition;
        
        if (iter > 99) {
            double[] trayectoria = currentSituation.getTrajectory(iter);
            ballPosition = new Position(trayectoria[0], trayectoria[1]);
            
        } else {
            ballPosition = new Position(ballPositions[iter][0], ballPositions[iter][1]);
        }
        
        return ballPosition;
    }

    public double getBallAltitude(int iter) {
        if (iter > 99) {
            return currentSituation.getTrajectory(iter)[2];
        }
        return ballPositions[iter][2];
    }
}
