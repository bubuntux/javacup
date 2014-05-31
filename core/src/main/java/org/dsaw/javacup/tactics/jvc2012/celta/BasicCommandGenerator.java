package org.dsaw.javacup.tactics.jvc2012.celta;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2012.celta.geom.Line;

import java.util.List;

public class BasicCommandGenerator implements CommandGenerator {

	public static final int OWN_FIELD = -1;
	
	public static final int RIVAL_FIELD = 1;
	
	/** Número de jugador que corresponde al portero */
	protected static final int GOAL_KEEPER = 0;
	
	/** Situación del partido en la iteración actual */
	protected GameSituations sp;
	
	/** 
	 * Devuelve la lista de commandos en función de la situación del partido
	 * @param sp 	Situación del partido
	 * @return Lista de comandos
	 */
	@Override
	public List<Command> getCommandList(GameSituations sp) {
		this.sp = sp;
		return null;
	}
	
	/**
	 * Indica si el balón está parado.
	 * @return	true si está parado, false si está en movimiento
	 */
	protected boolean isBallStopped() {
		return (sp.getTrajectory(0)[0] == sp.getTrajectory(1)[0]) &&
				(sp.getTrajectory(0)[1] == sp.getTrajectory(1)[1]);
	}
    
	/**
	 * Devuelve una recta que representa la trayectoria del balón.
	 * @return	trayectoria
	 * @throws 	IllegalArgumentException si las dos posiciones del balón son la misma en ambas iteraciones
	 */
	protected Line getShootTrajectory() throws IllegalArgumentException {
		return new Line(new Position(sp.getTrajectory(0)[0], sp.getTrajectory(0)[1]),
				new Position(sp.getTrajectory(2)[0], sp.getTrajectory(2)[1]));
	}
	
	/**
	 * Indica si el balón se encuentra dentro del área pequeña rival.
	 * @return true si el balón está dentro, false en caso contrario
	 */
	protected boolean isBallInsideRivalSmallArea() {
		return isInsideRivalSmallArea(sp.ballPosition());
	}
	
	/**
	 * Indica si el balón se encuentra dentro de nuestra área pequeña.
	 * @return true si el balón está dentro, false en caso contrario
	 */
	protected boolean isBallInsideOwnSmallArea() {
		return isInsideOwnSmallArea(sp.ballPosition());
	}
	
	/**
	 * Indica si el balón se encuentra dentro del área grande rival.
	 * @return true si el balón está dentro, false en caso contrario
	 */
	protected boolean isBallInsideRivalLargeArea() {
		return isInsideRivalLargeArea(sp.ballPosition());
	}
	
	/**
	 * Indica si el balón se encuentra dentro del área grande (la nuestra).
	 * @return true si el balón está dentro, false en caso contrario
	 */
	protected boolean isBallInsideOwnLargeArea() {
		return isInsideOwnLargeArea(sp.ballPosition());
	}
	
	/**
	 * Indica si la posición indicada se encuentra dentro del área pequeña rival.
	 * @return true siestá dentro, false en caso contrario
	 */
	protected boolean isInsideRivalSmallArea(Position pos) {
		boolean res = (Math.abs(pos.getX()) < Constants.LARGO_AREA_CHICA / 2 + 1
				&& Math.abs(pos.getY()) > Constants.LARGO_CAMPO / 2 - Constants.ANCHO_AREA_CHICA - 1
				&& pos.getY() > 0);
		return res; 
	}
	
	protected boolean isPlayerInsideLargeArea (int playerIdx) {
	
		return (sp.myPlayers()[playerIdx].getX() <= (Constants.ANCHO_CAMPO_JUEGO / -2) + (Constants.ANCHO_AREA_GRANDE / 2) + 2
				&& sp.myPlayers()[playerIdx].getX() <= (Constants.LARGO_CAMPO_JUEGO / -2) + (Constants.LARGO_AREA_GRANDE / 2) + 2);
	}
	
	/**
	 * Indica si la posición indicada se encuentra dentro nuestra área pequeña.
	 * @return true siestá dentro, false en caso contrario
	 */
	protected boolean isInsideOwnSmallArea(Position pos) {
		boolean res = (Math.abs(pos.getX()) < Constants.LARGO_AREA_CHICA / 2 + 2 
				&& Math.abs(pos.getY()) > Constants.LARGO_CAMPO / 2 - Constants.ANCHO_AREA_CHICA - 2
				&& pos.getY() < 0);
		return res; 
	}
	
	/**
	 * Indica si la posición indicada se encuentra dentro del área grande rival.
	 * @return true si está dentro, false en caso contrario
	 */
	protected boolean isInsideRivalLargeArea(Position pos) {
		return (Math.abs(pos.getX()) < Constants.LARGO_AREA_GRANDE / 2 + 1
				&& Math.abs(pos.getY()) > Constants.LARGO_CAMPO / 2 - Constants.ANCHO_AREA_GRANDE - 1
				&& pos.getY() > 0); 
	}
	
	/**
	 * Indica si la posición indicada se encuentra dentro nuestra área grande.
	 * @return true si está dentro, false en caso contrario
	 */
	protected boolean isInsideOwnLargeArea(Position pos) {
		return (Math.abs(pos.getX()) < Constants.LARGO_AREA_GRANDE / 2 + 1
				&& Math.abs(pos.getY()) > Constants.LARGO_CAMPO / 2 - Constants.ANCHO_AREA_GRANDE - 1
				&& pos.getY() < 0); 
	}
	
	/**
	 * Indica si se va a producir un saque de puerta (de la nuestra).
	 * @return	true si es saque de puerta
	 */
	protected boolean isGoalKick() {
		boolean res = (isBallStopped() 
				&& Math.abs(sp.ballPosition().getX()) == 9.16 
				&& sp.ballPosition().getY() == -47);
		return res;
	}
	
	/**
	 * Pase largo hacia el jugador más adelantado
	 * @param playerIdx	Índice del jugador que realiza el pase
	 * @return	Comando de pase
	 */
	protected Command longPass(int playerIdx) {
		return new CommandHitBall(playerIdx, 
				sp.myPlayers()[ sp.ballPosition().nearestIndexes(sp.myPlayers())[10] ], 
				1, true);
	}
	
	/**
	 * Devuelve la potencia a la que se deberá lanzar el balón para llegar al destino indicado. 
	 * @param playerIdx		Índice del jugador que realiza el pase
	 * @param destination	Posición objetivo
	 * @return potencia de tiro
	 */
	protected double getPower(int playerIdx, Position destination) {
		double passDistance = sp.myPlayers()[playerIdx].distance(destination);
		return passDistance * .035;
	}
	
}
