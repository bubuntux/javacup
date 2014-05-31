package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.Common;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.pases.simulador.RecoveryException;

import java.util.LinkedList;
import java.util.List;

public class MovimientoDesmarque {

	protected static final double ITERACIONES_DE_DESMARQUE = 1;
	
	public static List<Command> mover(GameSituations gs) {
		LinkedList<Command> comandos = new LinkedList<>();

		IndiceEIteracion iei = getMyPlayersRecovery(gs);
		Integer iteracionRecuperacionRival = getRivalPlayersRecoveryIteration(gs);
		
		if(iei.getIteracion() <= ITERACIONES_DE_DESMARQUE && iteracionRecuperacionRival >= iei.getIteracion())
			for(int i = 0; i < gs.myPlayers().length; i++)
				comandos.add(new CommandMoveTo(i, gs.myPlayers()[iei.getIndice()]));
		
		return comandos;
	}

    /**retorna la iteration donde se puede recuperar el ballPosition por mis jugadores
     * @param indicePasador 
     * @throws RecoveryException */
    protected static IndiceEIteracion getMyPlayersRecovery(GameSituations gs) {
    	int it = 1;
        while(true) {
            double[] posBalon = gs.getTrajectory(it);
        	Position posicionBalon = new Position(posBalon[0], posBalon[1]);
            if (!posicionBalon.isInsideGameField(0)) {
            	return new IndiceEIteracion(0, Constants.ITERACIONES);
            }
            if (posBalon[2] <= Constants.ALTO_ARCO) {
                for (int i = 0; i < gs.myPlayers().length; i++) {
                    if (posBalon[2] <= (gs.myPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaPropia(posicionBalon) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	Position pJug = gs.myPlayers()[i];
                        double dist0 = (double) it * Constants.getVelocidad(gs.myPlayersDetail()[i].getSpeed()); //uso una iteracion menos, porque el jugador se puede mover en la misma iteracion en que se pasa
                        double dist = pJug.distance(posicionBalon) - (gs.myPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaPropia(posicionBalon) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        if (dist0 >= dist && it >= gs.iterationsToKick()[i])
                        	return new IndiceEIteracion(i, it);
                    }
                }
            }
            it++;
        }
    }

    /**retorna la iteration donde se puede recuperar el ballPosition por mis jugadores
     * @throws RecoveryException */
    protected static Integer getRivalPlayersRecoveryIteration(GameSituations gs) {
        int it = 1;
        while(true) {
            double[] posBalon = gs.getTrajectory(it);
            if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(0)) {
                return Constants.ITERACIONES;
            }
            if (posBalon[2] <= Constants.ALTO_ARCO) {
                for (int i = 0; i < gs.rivalPlayers().length; i++) {
                	Position posicionBalon = new Position(posBalon[0], posBalon[1]);
                    if (posBalon[2] <= (gs.rivalPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaRival(posicionBalon) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	Position pJug = gs.rivalPlayers()[i];
                        double dist0 = (double) it * Constants.getVelocidad(gs.rivalPlayersDetail()[i].getSpeed());
                        double dist = pJug.distance(posicionBalon) - (gs.rivalPlayersDetail()[i].isGoalKeeper() && Common.dentroAreaRival(posicionBalon) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        if (dist0 >= dist && it >= gs.rivalIterationsToKick()[i])
                            return it;
                    }
                }
            }
            it++;
        }
    }

}
