package org.dsaw.javacup.tacticas.jvc2012.iria.util;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class TacticUtils {
	
  public enum Attitude{
    	Deffensive,
    	Offensive
    }
	
    public static Attitude getAttitude(GameSituations sp) {
		int iMPIterationsToBall = getIterationsToPositionBall(sp, false);
		int iRivalIterationsToBall = getIterationsToPositionBall(sp, true);
		if ((!sp.isStarts()) && (sp.isRivalStarts() || (iMPIterationsToBall > iRivalIterationsToBall))) return Attitude.Deffensive;
		return Attitude.Offensive;
	}
	
	private static int getIterationsToPositionBall(GameSituations sp, boolean bIsRival) {
		int iStep = 0;
        boolean bFound = false;
		Position[] apPositionPlayers = sp.myPlayers();
    	PlayerDetail[] aoPlayersDetail = sp.myPlayersDetail();
        if (bIsRival){
        	apPositionPlayers = sp.rivalPlayers();
        	aoPlayersDetail = sp.rivalPlayersDetail();
        }
        while (!bFound){
        	double[] dTrajectoryBall = sp.getTrajectory(iStep);
            if (!(new Position(dTrajectoryBall[0], dTrajectoryBall[1])).isInsideGameField(2)) {
                return -1;
            }
            if (dTrajectoryBall[2] <= Constants.ALTO_ARCO) {
				for (int i = 0; i < apPositionPlayers.length; i++) {
                    if (dTrajectoryBall[2] <= (aoPlayersDetail[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	 double dDistanceDetail = (double) iStep * Constants.getVelocidad(aoPlayersDetail[i].getSpeed()) + (aoPlayersDetail[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                    	 double dDistanceReal = apPositionPlayers[i].distance(new Position(dTrajectoryBall[0], dTrajectoryBall[1]));
                    	 if (dDistanceDetail >= dDistanceReal) {
                    		 bFound = true;
                    		 break;
                    	 }
                    }
				}
            }
            iStep++;
        }
		return iStep;
	}
    
	public static Position goTrayectoryToPositionBall(GameSituations sp) {
		int iStep = 0;
        boolean bFound = false;
		Position[] apPositionPlayers = sp.myPlayers();
    	PlayerDetail[] aoPlayersDetail = sp.myPlayersDetail();
    	Position p = null;
        while (!bFound){
        	double[] dTrajectoryBall = sp.getTrajectory(iStep);
            if (!(new Position(dTrajectoryBall[0], dTrajectoryBall[1])).isInsideGameField(2)) {
                return p;
            }
            if (dTrajectoryBall[2] <= Constants.ALTO_ARCO) {
				for (int i = 0; i < apPositionPlayers.length; i++) {
                    if (dTrajectoryBall[2] <= (aoPlayersDetail[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	 double dDistanceDetail = (double) iStep * Constants.getVelocidad(aoPlayersDetail[i].getSpeed()) + (aoPlayersDetail[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                    	 double dDistanceReal = apPositionPlayers[i].distance(new Position(dTrajectoryBall[0], dTrajectoryBall[1]));
                    	 if (dDistanceDetail >= dDistanceReal) {
                    		 bFound = true;
                    		 p = new Position(dTrajectoryBall[0], dTrajectoryBall[1]);
                    		 break;
                    	 }
                    }
				}
            }
            iStep++;
        }
		return p;
	}
	
}
