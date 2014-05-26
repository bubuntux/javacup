package org.dsaw.javacup.tacticas.jvc2012.iria.util;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class GoalKeeper {
	
	   private int iNumberGK = Integer.MAX_VALUE;
	   private final Position pPosteIzquierdo = new Position (Constants.posteIzqArcoInf.getX(),- (Constants.LARGO_CAMPO_JUEGO / 2));
	   private final Position pPosteDerecho = new Position (Constants.posteDerArcoInf.getX(),- (Constants.LARGO_CAMPO_JUEGO / 2));
	   
       /*** Obtiene dorsal ***/
	   public GoalKeeper(PlayerDetail[] pd){
		   if(iNumberGK == Integer.MAX_VALUE){
			   for (int i=0; i < pd.length;i++){
				   if (pd[i].isGoalKeeper()){
					   iNumberGK = i;
					   break;
				   }
			   }
		   }
	   }
	   
	   /*** Calcula Posicion */
	   public Command calculePositionGoalkeeper(GameSituations sp){
		    Position p = new Position(sp.myPlayers()[iNumberGK].getX(), sp.myPlayers()[iNumberGK].getY());
			double dAngle = p.angle(sp.ballPosition());
			p = new Position(0, - (Constants.LARGO_CAMPO_JUEGO / 2) );
			p = p.moveAngle(dAngle, ((Constants.posteDerArcoInf.getX() - Constants.posteIzqArcoInf.getX()) / 2));
			Position c = Position.Intersection(pPosteIzquierdo, 
											   pPosteDerecho, 
											   new Position(sp.getTrajectory(0)[0], sp.getTrajectory(0)[1]),
											   new Position(sp.getTrajectory(1)[0], sp.getTrajectory(1)[1]));
			
    		if ((c != null ) && (((Constants.posteDerArcoInf.getX()) > c.getX()) && ((Constants.posteIzqArcoInf.getX()) < c.getX()))){
    			if (sp.ballAltitude() > Constants.ALTO_ARCO){ 
    				p = new Position(c.getX(), - ((Constants.LARGO_CAMPO_JUEGO / 2)));
    				p = p.moveAngle(dAngle, 0.1);
    			}
    		}
			
    		Command command = new CommandMoveTo(iNumberGK, p);
	    	
    		int iJugador = sp.ballPosition().nearestIndex(sp.myPlayers());
    		if (iJugador == iNumberGK){
    			int iJugadorRival = sp.ballPosition().nearestIndex(sp.rivalPlayers());
    			double distancia = sp.ballPosition().distance(sp.myPlayers()[iJugador]);
    			double distanciaRival = sp.ballPosition().distance(sp.rivalPlayers()[iJugadorRival]);
    			if ((distancia < distanciaRival)&&(sp.ballAltitude() == 0)){
    				command = new CommandMoveTo(iJugador, sp.ballPosition());
    			}
    		}
			return command;
	    }
		
	    public int indexGK(){
	    	return iNumberGK;
	    }
		
}
