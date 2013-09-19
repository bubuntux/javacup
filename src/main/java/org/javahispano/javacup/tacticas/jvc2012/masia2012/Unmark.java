package org.javahispano.javacup.tacticas.jvc2012.masia2012;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Unmark {
	private List<Command> commands;
	private final Analysis analysis;
	
	List<AttackPosition> selected = new ArrayList<AttackPosition>();
	private double[] rivalBallAngle = new double[11];
	Position ball;
	
	public Unmark(List<Command> commands, Analysis analysis) {		
		this.commands = commands;
		this.analysis = analysis;
	}
	
	public void execute(){
		List<AttackPosition> positions = calculatePositions();
		int[] exclude = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		exclude[analysis.goalkeeperIndex] = analysis.goalkeeperIndex;
		for (AttackPosition attackPosition : positions) {
			int player = attackPosition.position.nearestIndex(analysis.myPlayers, exclude);
			commands.add(new CommandMoveTo(player, attackPosition.position));
			exclude[player] = player;
		}
	}

	final static double MIN_RATIO = Constants.LARGO_CAMPO_JUEGO/4;
	final static double MAX_RATIO = Constants.LARGO_CAMPO_JUEGO/2;
	final static double DELTA_RATIO = MAX_RATIO - MIN_RATIO;
	
	private List<AttackPosition> calculatePositions() {
		selected.clear();
		ball = analysis.recoveryBallPosition.setInsideGameField();
		double power = 1;
		double precision = 1;
		for (int i = 0; i < analysis.canShoot.length; i++) {
			if(analysis.myPlayersDetail[analysis.canShoot[i]].getPower() < power)
				power = analysis.myPlayersDetail[analysis.canShoot[i]].getPower();
			if(analysis.myPlayersDetail[analysis.canShoot[i]].getPrecision() < precision)
				precision = analysis.myPlayersDetail[analysis.canShoot[i]].getPrecision();
		}
		double ratio = MIN_RATIO + power*DELTA_RATIO;
		if(!analysis.attacking)
			ratio = MIN_RATIO;
		for (int i = 0; i < rivalBallAngle.length; i++) {
			rivalBallAngle[i] = ball.angle(analysis.rivalPlayers[i]);
		}
		List<AttackPosition> positions = new ArrayList<AttackPosition>();
		while(ratio > 0){
			double angle = 0;
			while(angle < 2*Math.PI){
				Position position = ball.moveAngle(angle, ratio);
				if(position.isInsideGameField(0)){
					positions.add(new AttackPosition(position, precision));
				}
				angle += 5*Math.PI/180;
			}
			ratio -= 3;
		}
		for (int i = 0; i < 10; i++) {
			Collections.sort(positions);
			selected.add(positions.get(0));
			positions.remove(0);
		}
		return selected;
	}
	
	class AttackPosition implements Comparable<AttackPosition>{
		Position position;
		double factor;
		double ballAngle;
		
		private AttackPosition(Position position, double precision) {
			super();
			this.position = position;
			ballAngle = ball.angle(position);
			double rivalDistance =  Math.min(Math.max(position.distance(analysis.rivalPlayers[position.nearestIndex(analysis.rivalPlayers)]), Constants.DISTANCIA_CONTROL_BALON_PORTERO), 5)/5;
			double goalDistance = Math.max(1 - (Math.max(position.distance(Constants.penalSup),Constants.ANCHO_AREA_GRANDE) - Constants.ANCHO_AREA_GRANDE)/100.0, 0);
			double angleFactor = calculateAngleFactor(ball, precision);
			this.factor = 0.45*angleFactor + 0.2*goalDistance + 0.15 * rivalDistance;
		}
		
		private double calculateAngleFactor(Position ball, double precision){
			double minAngle = 2*Math.PI;
			double d = ball.distance(position);
			for (int j = 0; j < analysis.rivalPlayers.length; j++) {
				if(ball.distance(analysis.rivalPlayers[j]) <= d){
					double angle = Math.abs(ballAngle -  rivalBallAngle[j]);
					if(angle < minAngle)
						minAngle = angle;
				}
			}
			return Math.min(minAngle/(Constants.getErrorAngular(precision)*Math.PI/2), 1);
		}
		
		@Override
		public int compareTo(AttackPosition other) {
			if(analysis.isGoalKick){
				if(!analysis.insideArea(position) && analysis.insideArea(other.position))
					return -1;
				if(analysis.insideArea(position) && !analysis.insideArea(other.position))
					return 1;
			}
			return other.calculateFactor().compareTo(calculateFactor());
		}

		private Double calculateFactor() {
			return factor + calculateSelectedFactor();
		}
		
		private double calculateSelectedFactor() {
			double minDistance = 12;
	        for (int i = 0; i < selected.size(); i++) {
	        	double distance = position.distance(selected.get(i).position);
				if(distance < minDistance)
					minDistance = distance;
	        }
	        double fdistSelected = minDistance/12.0;
	        return  0.2*fdistSelected;
		}
	}
}

