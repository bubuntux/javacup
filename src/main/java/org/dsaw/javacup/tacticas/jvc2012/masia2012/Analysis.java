package org.dsaw.javacup.tacticas.jvc2012.masia2012;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Analysis {
	private GameSituations sp;
	public int iterations;
	
	public Position[] ballPositions = new Position[100];
	public double[] ballZ = new double[100];
	
	public Mentality mentality;
	public int defenseCount;
	public boolean attacking;
	public Position ball;
	
	public int myGoals;
	public int rivalGoals;
	
	public double possession;
	private int possessionPoints = 0;
	private int totalPossessionPoints = 0;
	
	public double actualPossession = 0;
	private int actualPossessionPoints = 0;
	private int totalActualPossessionPoints = 0;
	
	public Position[] myPlayers;
	public int[] canShoot;
	public int[] iterationsToShoot;
	public PlayerDetail[] myPlayersDetail;
	public boolean isService;
	public final int goalkeeperIndex = 0;
	public int iterationToBall;
	public int playerClosestToBall;
	public Position recoveryBallPosition;
	
	public Position[] rivalPlayers;
	public PlayerDetail[] rivalPlayersDetail;
	public int[] rivalIterationsToShoot;
	public int rivalIterationToBall;
	public int rivalClosestToBall;
	public Position rivalRecoveryBallPosition;
	public boolean isRivalService;
	public boolean isRivalGoal;
	
	public boolean isGoalKick;
	public boolean goalKickWaiting;
	private int goalKickIterations = 0;
	private int goalKickIterationsCount = Constants.ITERACIONES_SAQUE/2;
	public boolean isGoalDefended = true;
	
	public int shootCount = 0;
	
	
	
	public void update(GameSituations sp){
		this.sp = sp;
		iterations = sp.iteration();
		isRivalGoal = false;
		for (int i = 0; i < 100; i++) {
			double[] trayectory = sp.getTrajectory(i);
			ballPositions[i] = new Position(trayectory[0], trayectory[1]);
			ballZ[i] = trayectory[2];
			if(i > 0 && isRivalGoal(ballPositions[i], ballZ[i], ballPositions[i-1], ballZ[i-1]))
				isRivalGoal = true;
		}		
		
		ball = sp.ballPosition();
		
		myGoals = sp.myGoals();
		rivalGoals = sp.rivalGoals();
		
		myPlayers = sp.myPlayers();
		myPlayersDetail = sp.myPlayersDetail();
		isService = sp.isStarts();
		canShoot = sp.canKick();
		iterationsToShoot = sp.iterationsToKick();
		
		rivalPlayers = sp.rivalPlayers();
		rivalPlayersDetail = sp.rivalPlayersDetail();
		rivalIterationsToShoot = sp.rivalIterationsToKick();
		isRivalService = sp.isRivalStarts();
		
		CalculateIterationsResults calculateIterationsResults = calculateIterationsToBall(myPlayers, myPlayersDetail);
		iterationToBall = calculateIterationsResults.iterationToBall;
		playerClosestToBall = calculateIterationsResults.playerClosestToBall;
		recoveryBallPosition = calculateIterationsResults.recoveryBallPosition;
		
		calculateIterationsResults = calculateIterationsToBall(rivalPlayers, rivalPlayersDetail);
		rivalIterationToBall = calculateIterationsResults.iterationToBall;;
		rivalClosestToBall = calculateIterationsResults.playerClosestToBall;
		rivalRecoveryBallPosition = calculateIterationsResults.recoveryBallPosition;
	
		isGoalKick = isService && insideArea(ball);
		if(isGoalKick){
			goalKickWaiting = goalKickIterations < goalKickIterationsCount;
			goalKickIterations++;
		}
		else{
			goalKickWaiting = false;
			goalKickIterations = 0;
		}
		attacking = attacking();
		possession = calculatePossession();
		actualPossession = calculateActualPossession();
		mentality = calculateMentality();
	}
	
	private double calculateActualPossession() {
		if(isService || iterationToBall <= rivalIterationToBall){
			if(ball.getY() >= 0){
				actualPossessionPoints += 2;
				totalActualPossessionPoints += 2;
			}else{
				actualPossessionPoints++;
				totalActualPossessionPoints++;
			}
			
		}
		if(isRivalService || iterationToBall >= rivalIterationToBall){
			if(ball.getY() > 0)
				totalActualPossessionPoints++;
			else
				totalActualPossessionPoints += 2;
		}
		return ((double)actualPossessionPoints)/totalActualPossessionPoints;
	}

	public boolean insideArea(Position position) {
		return Math.abs(position.getX()) <= Constants.LARGO_AREA_GRANDE/2 &&
				position.getY() <= Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_GRANDE;
	}

	private boolean attacking() {
		return sp.isStarts() || (!sp.isRivalStarts() && iterationToBall >= 0 && (rivalIterationToBall < 0 || iterationToBall < rivalIterationToBall));
	}
	
	private double calculatePossession() {
		if (!isService && !isRivalService) {
            double y = ball.getY();
            if (y > 0) {
                if (y > Constants.LARGO_CAMPO_JUEGO/4) {
                    possessionPoints += 2;
                    totalPossessionPoints += 2;
                } else {
                	possessionPoints += 1;
                    totalPossessionPoints += 1;
                }
            } else if (y < 0) {
                if (y < - Constants.LARGO_CAMPO_JUEGO/4) {
                    totalPossessionPoints += 2;
                } else {
                	totalPossessionPoints += 1;
                }
            }
        }
		if(totalPossessionPoints == 0)
			return .5;
		return possessionPoints/(double)totalPossessionPoints;
	}
	
	private Mentality calculateMentality() {
		if(myGoals >= rivalGoals + 3){
			if(possession > 0.5)
				return Mentality.Offensive;
			return Mentality.Normal;
		}
		if(myGoals> rivalGoals){
			if(iterations < 2*Constants.ITERACIONES/3)
				return Mentality.Normal;
			else
				return Mentality.Defensive;
		}if(myGoals == rivalGoals){
			if(iterations < Constants.ITERACIONES/3){
				return Mentality.Normal;
			}
			if(possession < 0.5)
				return Mentality.Defensive;
			return Mentality.Offensive;
		} 
		return Mentality.Offensive;
	}
	
	private CalculateIterationsResults calculateIterationsToBall(Position[] players, PlayerDetail[] details) {
		int iteration = 0;
		double height = 0;
		double ballControlDistance = 0;
        while (iteration < ballPositions.length) {
            Position ballPosition = ballPositions[iteration];
            if (!ballPosition.isInsideGameField(2)) {
            	return new CalculateIterationsResults(ballPositions.length, ballPosition.nearestIndex(players), ballPositions[iteration].setInsideGameField());
            }
            if (ballZ[iteration] <= Constants.ALTO_ARCO) {
				for (int i = 0; i < players.length; i++) {
					if (details[i].isGoalKeeper() && Math.abs(ballPosition.getX()) <= Constants.LARGO_AREA_GRANDE / 2 && Math.abs(ballPosition.getY())  > Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE) {
	                    height = Constants.ALTO_ARCO;
	                    ballControlDistance = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
	                } else {
	                    height = Constants.ALTURA_CONTROL_BALON;
	                    ballControlDistance = Constants.DISTANCIA_CONTROL_BALON;
	                }
                    if (ballZ[iteration] <= height) {
                        double runDistance = (double) iteration * Constants.getVelocidad(details[i].getSpeed()) + ballControlDistance;
                        double distanceToBall = players[i].distance(ballPosition);
                        if (runDistance >= distanceToBall) {
                        	return new CalculateIterationsResults(iteration, i, ballPositions[iteration].setInsideGameField());
                        }
                    }
                }
            }
            iteration++;
        }
        return new CalculateIterationsResults(ballPositions.length -1, ballPositions[ballPositions.length - 1].nearestIndex(players), ballPositions[ballPositions.length - 1].setInsideGameField());
	}	
	
	public boolean isRivalGoal(Position ballPosition, double z, Position last, double lastZ) {
		ballPosition = ballPosition.getInvertedPosition();
		last = last.getInvertedPosition();
		double Dx = ballPosition.getX()-last.getX();
		double Dy = ballPosition.getY()-last.getY();
		if(!ballPosition.isInsideGameField(0)){
			if(ballPosition.getY() > Constants.LARGO_CAMPO_JUEGO/2){
				double posX = (Dx / Dy) * (Constants.LARGO_CAMPO_JUEGO/2 - ballPosition.getY()) + ballPosition.getX();//proyeccion x de la trayectoria del ballPosition en la linea de meta
	            double Dz = z - lastZ;
				double posZ = (Dz  / Dy) * (Constants.LARGO_CAMPO_JUEGO/2 - ballPosition.getY()) + z;//proyeccion z de la trayectoria del ballPosition en la linea de meta
				if(posZ <= Constants.ALTO_ARCO &&
					Math.abs(posX) < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON &&
					z - Dz <= Constants.ALTO_ARCO){
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public int calculateIterationsToBall(Position position, double z, Position[] players, PlayerDetail[] details, int[] iterToShoot) {
		int it, best = Constants.ITERACIONES;
		double height = 0;
		double ballControlDistance = 0;
		for (int i = 0; i < players.length; i++) {
			if (details[i].isGoalKeeper() && Math.abs(position.getX()) <= Constants.LARGO_AREA_GRANDE / 2 && Math.abs(position.getY())  > Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE) {
                height = Constants.ALTO_ARCO;
                ballControlDistance = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
            } else {
                height = Constants.ALTURA_CONTROL_BALON;
                ballControlDistance = Constants.DISTANCIA_CONTROL_BALON;
            }
			if(height >= z){
				it = (int) Math.ceil(Math.max(0, players[i].distance(position) - ballControlDistance)/Constants.getVelocidad(details[i].getSpeed()));
				if (iterToShoot[i] <= it && it < best) {
	            	best = it;                            
	            }
			}			            
        }         
		return best;		
	}
	
	public int calculateRivalIterationsToBall(Position position, double z) {        
		return calculateIterationsToBall(position, z, rivalPlayers, rivalPlayersDetail, rivalIterationsToShoot);		
	}
	
	private class CalculateIterationsResults{
		
		public int iterationToBall;
		public int playerClosestToBall;
		public Position recoveryBallPosition;
		private CalculateIterationsResults(int iterationToBall,
				int playerClosestToBall, Position recoveryBallPosition) {
			super();
			this.iterationToBall = iterationToBall;
			this.playerClosestToBall = playerClosestToBall;
			this.recoveryBallPosition = recoveryBallPosition;
		}
	}  
}
