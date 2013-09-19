package org.javahispano.javacup.tacticas.jvc2012.iria.util;

import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;

/**** Based in Masia Tactic **/

public class Shoot {
	private GameSituations sp;
	private List<Command> comandos;
	
	public Shoot(List<Command> comandos) {
		this.comandos = comandos;
	}
	
	private boolean isShootSituation(double power) {
		double factor = Math.abs(Math.sin(sp.ballPosition().angle(Constants.centroArcoSup)));
		factor = 1 - (1 - factor)*(1 - factor);
		int e = Math.min(4,Math.max(0,sp.myGoals()-sp.rivalGoals()));
		return sp.ballPosition().distance(Constants.centroArcoSup) <= (30-e)*factor*Constants.getVelocidadRemate(power)/Constants.REMATE_VELOCIDAD_MAX;
	}

	void execute(GameSituations sp,  int[] canShoot){
		this.sp = sp;
		double power = 1;
		for (int i = 0; i < canShoot.length; i++) {
			if(sp.myPlayersDetail()[canShoot[i]].getPower() < power)
				power = sp.myPlayersDetail()[canShoot[i]].getPower();
		}
		if(isShootSituation(power)){
			double maxVertAngle = Constants.ANGULO_VERTICAL_MAX*Math.PI/180;
			double angleVert = 0;
			double angle = sp.ballPosition().angle(Constants.posteDerArcoSup.movePosition(-Constants.RADIO_BALON*2, 0));
			angle = Math.min(angle + 0.5*Constants.getErrorAngular(1.0)/2*Math.PI, sp.ballPosition().angle(Constants.centroArcoSup));
			double maxAngle = sp.ballPosition().angle(Constants.posteIzqArcoSup.movePosition(Constants.RADIO_BALON*2, 0));
			maxAngle = Math.max(maxAngle - 0.5*Constants.getErrorAngular(1.0)/2*Math.PI, sp.ballPosition().angle(Constants.centroArcoSup));
			ShootInfo shoot = new ShootInfo();
			while(angle <= maxAngle){
				angleVert = 0;
				while(angleVert <= maxVertAngle){
					shoot = compareShoot(shoot, evaluateShoot(power, angle, angleVert));
					angleVert += Math.PI/180;
				}				
				angle += Math.PI/180;
			}
			for (int player : canShoot) {
	        	comandos.add(new CommandHitBall(player, shoot.angle*180/Math.PI, 1, shoot.angleVert*180/Math.PI));
	        }			
        }		
	}
		
	
	private ShootInfo compareShoot(ShootInfo shoot1, ShootInfo shoot2) {
		if(shoot1.fitness >= shoot2.fitness)
			return shoot1;
		return shoot2;
	}

	private ShootInfo evaluateShoot(double power, double angle, double angleVert) {
		Trajectory trajectory = new Trajectory(sp.ballPosition(), Constants.getVelocidadRemate(power), angle, angleVert);
		double fitness = 0, f;
		for (int i = 0; i < trajectory.length; i++) {
			if(i == 0)
				f = calculateFitness(trajectory.positions[i], trajectory.z[i], i + 1, sp.ballPosition(), 0);
			else
				f = calculateFitness(trajectory.positions[i], trajectory.z[i], i + 1, trajectory.positions[i-1], trajectory.z[i-1]);
			fitness += f;
			if(f >1 ||f == -1)
				break;
		}
		return new ShootInfo(angle, angleVert, fitness);
	}
	public int calculateIterToBall(Position position, double z, Position[] players, PlayerDetail[] details, int[] iterToShoot) {
		int it, best = Constants.ITERACIONES;
		for (int i = 0; i < players.length; i++) {
			if((details[i].isGoalKeeper()? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON) >= z){
				it = (int) Math.ceil(Math.max(0, players[i].distance(position)- (details[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON))/Constants.getVelocidad(details[i].getSpeed()));
				if (iterToShoot[i] <= it && it < best) {
	            	best = it;                            
	            }
			}			            
        }         
		return best;		
	}

	private double calculateFitness(Position position, double z, int iter, Position last, double lastZ) {
		int oppIterToBall = calculateIterToBall(position, z, sp.rivalPlayers(), sp.rivalPlayersDetail(), sp.rivalIterationsToKick());
		double Dx = position.getX()-last.getX();
		double Dy = position.getY()-last.getY();
		if(!position.isInsideGameField(0)){
			if(position.getY() > Constants.LARGO_CAMPO_JUEGO/2){
				double posX = (Dx / Dy) * (Constants.LARGO_CAMPO_JUEGO/2 - position.getY()) + position.getX();
	            double Dz = z - lastZ;
				double posZ = (Dz  / Dy) * (Constants.LARGO_CAMPO_JUEGO/2 - position.getY()) + z;
				if(posZ < Constants.ALTO_ARCO &&
					Math.abs(posX) < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON &&
					z - Dz < Constants.ALTO_ARCO){
					double fvel = Math.sqrt(Dx*Dx + Dy*Dy)/Constants.REMATE_VELOCIDAD_MAX;
					double fiter = Math.max(0, Math.min(1, (oppIterToBall - iter)/75.0));
					double fx = Math.max(0, Math.min(1, 1 - Math.abs(posX) /( Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON)));
					return 1 + .40*fvel + .45*fiter + .15*fx;
				}
			}
			return -1;
		}
		if(oppIterToBall <= iter){
			double fdist = Math.max(0, Math.min(1, position.getY()/Constants.centroArcoSup.getY()));
			double fiter = Math.max(0.0, 1 - (iter - oppIterToBall)/5.0);
			double fvel = Math.max(0, Math.min(1, Math.sqrt(Dx*Dx + Dy*Dy)/Constants.REMATE_VELOCIDAD_MAX));
			return -1 + (.3*fdist + .1*fiter + .6*fvel);
		}	
		return 0;
	}

	
	class ShootInfo{
		public double angle = 0;
		public double angleVert = 0;
		public double fitness = Double.NEGATIVE_INFINITY; 
		
		
		public ShootInfo(double angle, double angleVert, double fitness) {
			super();
			this.angle = angle;
			this.angleVert = angleVert;
			this.fitness = fitness;
		}

		public ShootInfo(){			
		}
	}
}
