package org.dsaw.javacup.tacticas.jvc2012.iria;

import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;

import org.dsaw.javacup.tacticas.jvc2012.iria.util.GoalKeeper;
import org.dsaw.javacup.tacticas.jvc2012.iria.util.Pass;
import org.dsaw.javacup.tacticas.jvc2012.iria.util.TacticUtils;
import org.dsaw.javacup.tacticas.jvc2012.iria.util.TacticUtils.Attitude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Iria implements Tactic {

	// Lista de comandos
	LinkedList<Command> comandos = new LinkedList<>();

	@SuppressWarnings("unchecked")
	@Override
	public List<Command> execute(GameSituations sp) {

		// Inicializamos lista de comandos en cada iteracion
		comandos.clear();

		GoalKeeper oGK = new GoalKeeper(sp.myPlayersDetail());
		comandos.add(oGK.indexGK(), oGK.calculePositionGoalkeeper(sp));

		Attitude myAttitudeInIteration = TacticUtils.getAttitude(sp);
		// Matriz de distancias
		double[][] distances = new double[sp.rivalPlayers().length][sp
				.myPlayers().length];
		for (int i = 0; i < sp.rivalPlayers().length; i++) {
			for (int j = 0; j < sp.myPlayers().length; j++) {
				if ((i > sp.rivalPlayers().length+1)||(j > sp.myPlayers().length+1)) distances[i][j] = Double.MAX_VALUE;
				distances[i][j] = sp.rivalPlayers()[i].norm(sp.myPlayers()[j]);
			}
		}
		// Mejor posicion de defensa
		for (int k = 0; k < sp.rivalPlayers().length; k++) {
			int fRival = 0;
			int fPlayer = 0;
			double minDistance = Double.MAX_VALUE;
			if (!sp.rivalPlayersDetail()[k].isGoalKeeper()) {
				for (int i = sp.rivalPlayers().length-1; i >= 0; i--) {
					if (!sp.rivalPlayersDetail()[k].isGoalKeeper()) {
						for (int j = 0; j < sp.myPlayers().length; j++) {
							if ((distances[i][j]+(i*5) < minDistance)
									&& (!sp.myPlayersDetail()[j].isGoalKeeper())) {
								fRival = i;
								fPlayer = j;
								minDistance = distances[i][j];
							}
						}
					}
				}
				for (int r = 0; r < sp.rivalPlayers().length; r++) {
					distances[fRival][r] = Double.MAX_VALUE;
					distances[r][fPlayer] = Double.MAX_VALUE;

				}
				Position rival = sp.rivalPlayers()[fRival];
				comandos.add(new CommandMoveTo(fPlayer, rival.moveAngle(rival
						.angle(new Position(0,
								-(Constants.LARGO_CAMPO_JUEGO / 2))),
						Constants.DISTANCIA_CONTROL_BALON)));
			}
		}
	
		if (myAttitudeInIteration == Attitude.Offensive) {
			Position fp = TacticUtils.goTrayectoryToPositionBall(sp);
			int indexAtack = 0;
			if (fp != null) {
				indexAtack = fp.nearestIndex(sp.myPlayers(), oGK.indexGK());
				comandos.add(new CommandMoveTo(indexAtack, fp));
			}
			if (sp.ballPosition().getY() > -(Constants.LARGO_CAMPO_JUEGO/4)){
				ArrayList<Integer> exclude = new ArrayList(Arrays.asList(sp.canKick()));
				int candidates[] = new Position(0, (Constants.LARGO_CAMPO_JUEGO/2)).nearestIndexes(sp.myPlayers(), oGK.indexGK());
				int e=1;
				if (sp.myGoals()-sp.rivalGoals() <= 0) e=0;
				for (int i=0; i < 2-e; i++){
					if (!exclude.contains(candidates[i])&& (indexAtack != candidates[i]) && (sp.myPlayersDetail()[candidates[i]].getPower() >= 0.8d)) comandos.add(new CommandMoveTo(candidates[i], getNormalPositions(sp)[candidates[i]]));
				}
			}
			
		}

		pass.execute(sp,myAttitudeInIteration);
		
		// Retorna la lista de comandos
		return comandos;
	}
	
    private double control(double numero, double min, double max) {
        if (numero < min) {
            return min;
        }
        if (numero > max) {
            return max;
        }
        return numero;
    }
    
	private Pass pass = new Pass(comandos);
	/******** Default Tactics ******/
	@Override
	public TacticDetail getDetail() {
		return new IriaDetail();
	}

	@Override
	public Position[] getStartPositions(GameSituations sp) {
		Position[] aTacticDefault = new Position[] { new Position(0.0, -51),
				new Position(-17.0, -30.0), new Position(0.0, -36.0),
				new Position(17.0, -30.0), new Position(9.0, -17.0),
				new Position(-9.0, -17.0), new Position(-25.0, -17.0),
				new Position(25.0, -17.0), new Position(0.0, 0.0),
				new Position(21.0, -4.0), new Position(-21.0, -4.0) };
		return aTacticDefault;
	}

	@Override
	public Position[] getNoStartPositions(GameSituations sp) {
		Position[] aTacticDefault = new Position[] { new Position(0.0, -51),
				new Position(-13.0, -32.0), new Position(-0.0, -36.0),
				new Position(13.0, -32.0), new Position(-8.0, -21.0),
				new Position(8.0, -21.0), new Position(-25.0, -24.0),
				new Position(25.0, -24.0), new Position(0.0, -10.0),
				new Position(16.0, -10.0), new Position(-16.0, -10.0) };
		return aTacticDefault;
	}

	public Position[] getNormalPositions(GameSituations sp)  {
		double coenfAdX = sp.ballPosition().getX();
        double coenfAdY = sp.ballPosition().getY();
		Position[] aTacticDefault = new Position[] { new Position(0.0, -50.4),
				new Position(-20.0, -26.0), new Position(0.0, -31.0),
				new Position(20.0, -26.0), new Position(0.0, -11.0),
				new Position(0.0, 11.0), new Position(-24.0, 0.0),
				new Position(24.0, 0.0), 
				new Position(this.control(0 + coenfAdX, -10, 10), this.control(42 + coenfAdY, 31, 42)) ,
				new Position(this.control(12 + coenfAdX, 0, 20), this.control(27 + coenfAdY, 0, 42)),
		        new Position(this.control(-12 + coenfAdX, -20, 0), this.control(27 + coenfAdY, 0, 42))};
		return aTacticDefault;
	}
}