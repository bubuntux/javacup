package org.dsaw.javacup.tactics.jvc2012.masia2012;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Defense {
	private List<Command> commands;
	private final Analysis analysis;
	private int defenseCount = 4;

	public Defense(List<Command> commands, Analysis analysis) {
		super();		
		this.commands = commands;
		this.analysis = analysis;
	}
	
	public void execute(){
		defenseCount = analysis.mentality.defenseCount;
		if(!analysis.attacking){
			if(analysis.actualPossession > 0.65){
				int extraDefenseCount = 10 - defenseCount - analysis.mentality.attackerCount;
				defenseCount += Math.ceil(extraDefenseCount * (1 - Math.max(0, Math.min(analysis.actualPossession - 0.55, 0.20))/0.20));
			}
			else
				defenseCount = 9; 
		}
		int[] exclude = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		exclude[analysis.goalkeeperIndex] = analysis.goalkeeperIndex;
		for (int i = 0; i < exclude.length; i++) {
			if(i > defenseCount)
				exclude[i] = i;
		}
		List<PositionsDefend> positionsDefends = calculatePositionsDefends();
		for (PositionsDefend positionsDefend : positionsDefends) {
			int defender;
			if(positionsDefend.isBallPosition){
				if(analysis.attacking)
					defender = recoveryBall();
				else
					defender = defensePosition(positionsDefend, analysis.rivalIterationToBall, exclude);
			}else
				defender = defensePosition(positionsDefend, 0, exclude);
			exclude[defender] = defender;
		}
	}

	private int recoveryBall() {
		if(analysis.isService && !analysis.isGoalKick){
			int player = analysis.recoveryBallPosition.nearestIndex(analysis.myPlayers, 0, 1, 2, 3, 4);
			commands.add(new CommandMoveTo(player, analysis.recoveryBallPosition));
			return player;
		}
		int i = analysis.iterationToBall + 1;
		double height = 0;
		double ballControlDistance = 0;
		while(i < analysis.rivalIterationToBall && i < analysis.ballPositions.length){
			if(analysis.ballPositions[i].isInsideGameField(0)){
				if (analysis.myPlayersDetail[analysis.playerClosestToBall].isGoalKeeper() && Math.abs(analysis.ballPositions[i].getX()) <= Constants.LARGO_AREA_GRANDE / 2 && Math.abs(analysis.ballPositions[i].getY())  > Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE) {
	                height = Constants.ALTO_ARCO;
	                ballControlDistance = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
	            } else {
	                height = Constants.ALTURA_CONTROL_BALON;
	                ballControlDistance = Constants.DISTANCIA_CONTROL_BALON;
	            }
				if(height >= analysis.ballZ[i]){
					int it = (int) Math.ceil(Math.max(0, analysis.myPlayers[analysis.playerClosestToBall].distance(analysis.ballPositions[i]) - ballControlDistance)/Constants.VELOCIDAD_MAX);
					if (it <= i) {
		            	if(analysis.ballPositions[i].distance(analysis.ballPositions[i-1]) < 2*ballControlDistance){
		            		commands.add(new CommandMoveTo(analysis.playerClosestToBall, Position.medium(analysis.ballPositions[i-1], analysis.ballPositions[i])));
		            		return analysis.playerClosestToBall;
		            	}
		            	else
		            		i++;
		            }else break;
				}else break;
			}else break;
		}
		commands.add(new CommandMoveTo(analysis.playerClosestToBall, analysis.ballPositions[i-1]));
		return analysis.playerClosestToBall;
	}

	private int defensePosition(PositionsDefend defend, int iterations,
			int[] exclude) {
		int rival = defend.position.nearestIndex(analysis.rivalPlayers);  
		Position[] intersection = calculateIntersection(defend.position, analysis.rivalPlayersDetail[rival].getSpeed());
		Position best = intersection[0];
		int bestIterations = Integer.MAX_VALUE;
		int bestDefender = 0;
		for (int i = 0; i < intersection.length; i++) {
			int defender = intersection[i].nearestIndex(analysis.myPlayers, exclude);
			int iterationsToPosition = (int) Math.ceil(analysis.myPlayers[defender].distance(intersection[i])/Constants.getVelocidad(analysis.myPlayersDetail[defender].getSpeed()));
			//int iterationsToPosition = (int) Math.ceil(Math.max(0, analysis.myPlayers[defender].distance(intersection[i]) - (defend.isBallPosition? Constants.DISTANCIA_CONTROL_BALON: 0))/Constants.getVelocidad(analysis.myPlayersDetail[defender].getSpeed()));;
			if(iterationsToPosition <=  iterations + i){
				if(defend.isBallPosition)
					commands.add(new CommandMoveTo(defender, intersection[i]));
				else if(defender <= analysis.mentality.centralDefenseCount)
					commands.add(new CommandMoveTo(defender, new Position(intersection[i].getX(), Math.min(intersection[i].getY(), 0))));
				else if (defender <= analysis.mentality.defenseCount)
					commands.add(new CommandMoveTo(defender, intersection[i]));
				else if (defend.position.getY()< 0 || analysis.ball.distance(defend.position) <= 25)
					commands.add(new CommandMoveTo(defender, intersection[i]));
				else{
					double ratio = Math.max(analysis.ball.distance(defend.position)/Constants.REMATE_VELOCIDAD_MAX*Constants.VELOCIDAD_MAX - defend.position.distance(intersection[i]), 0);
					commands.add(new CommandMoveTo(defender, intersection[i].moveAngle(intersection[i].angle(Constants.centroArcoInf), ratio )));
				}
				return defender;
			}
			if(iterationsToPosition - (iterations + i) < bestIterations){
				bestIterations = iterationsToPosition - (iterations + i);
				bestDefender = defender;
				best = intersection[i];
			}
		}
		if(defend.isBallPosition || bestDefender > analysis.mentality.centralDefenseCount)
			commands.add(new CommandMoveTo(bestDefender, best));
		else 
			commands.add(new CommandMoveTo(bestDefender, new Position(best.getX(), Math.min(best.getY(), 0))));		
		return bestDefender;
	}

	
	private List<PositionsDefend> calculatePositionsDefends() {
		List<PositionsDefend> positionsDefends = new ArrayList<>();
		for (int i = 0; i < analysis.rivalPlayers.length; i++) {
			if(i != analysis.rivalClosestToBall)
				positionsDefends.add(new PositionsDefend(analysis.rivalPlayers[i], false));
		}
		if(analysis.attacking){
			Collections.sort(positionsDefends);
			if(!analysis.isGoalKick)
				positionsDefends.add(0, new PositionsDefend(analysis.recoveryBallPosition, true));
		}else if(analysis.iterationToBall == analysis.rivalIterationToBall){
			Collections.sort(positionsDefends);
			positionsDefends.add(0, new PositionsDefend(analysis.rivalRecoveryBallPosition, true));
		}
		else{
			positionsDefends.add(new PositionsDefend(analysis.rivalRecoveryBallPosition, true));
			Collections.sort(positionsDefends);
		}
		int k = positionsDefends.size() - 1;
		while(positionsDefends.size() > defenseCount){
			if(!positionsDefends.get(k).isBallPosition)
				positionsDefends.remove(k);
			k--;
		}
		return positionsDefends;
	}
	
	private Position[] calculateIntersection(Position position, double rivalVelocity) {
		double velocity = Constants.getVelocidad(rivalVelocity);
		int steps = (int)(position.distance(Constants.centroArcoInf)/velocity) + 1;
		Position[] intersections  = new Position[steps];
		for (int i = 0; i < intersections.length; i++) {
			intersections[i] = position.moveAngle(position.angle(Constants.centroArcoInf), velocity*i); 
		}
		return intersections;
	}
	
	class PositionsDefend implements Comparable<PositionsDefend>{
		
		private Position position;
		private boolean isBallPosition;
		private PositionsDefend(Position position, boolean isBallPosition) {
			super();
			this.position = position;
			this.isBallPosition = isBallPosition;
		}
		
		@Override
		public int compareTo(PositionsDefend arg0) {
			return ((Double)position.distance(Constants.centroArcoInf)).compareTo(arg0.position.distance(Constants.centroArcoInf));
		}
	}
}
