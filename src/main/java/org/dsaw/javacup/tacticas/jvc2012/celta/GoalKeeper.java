package org.dsaw.javacup.tacticas.jvc2012.celta;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import org.dsaw.javacup.tacticas.jvc2012.celta.geom.Line;

/**
 * Generador de comandos para el portero.
 */
public class GoalKeeper extends BasicCommandGenerator {

	/** Distancia entre el portero y la portería en situaciones normales */
	private static final int GOAL_DISTANCE = 2;

	/** Línea de fondo (de nuestro campo) */
	private static final Line GOAL_LINE = new Line(Constants.cornerInfIzq, Constants.cornerInfDer);
			
	/** Recta sobre la cuál se moverá el portero normalmente */
	protected Line goalKeeperLine;

	/**
	 * Constructor.
	 */
	public GoalKeeper() {
		// definimos la recta del portero
		 double goalKeeperX = (Constants.LARGO_CAMPO_JUEGO / -2) + GOAL_DISTANCE;
		 goalKeeperLine = new Line(new Position(-20, goalKeeperX), new Position(20, goalKeeperX));
	}
	
	/**
	 * Devuelve la lista de commandos del portero en función de la situación del
	 * partido
	 * @param sp	Situación del partido
	 * @return 	Lista de comandos
	 */
	@Override
	public List<Command> getCommandList(GameSituations sp) {
		super.getCommandList(sp);
		List<Command> commands = new LinkedList<Command>();
		
		// si es un tiro a puerta, intentamos pararlo
		if (isPossibleGoal()) {
			if (Celta.DEBUG_GOAL_KEEPER)
				System.out.println("Tiro a puerta! Ojo portero!");
			commands.add(new CommandMoveTo(GOAL_KEEPER, catchRivalShoot()));
		}
		// si el portero es el más cercano al balón y el balón está parado
		// significa que tenemos que ir hacia él para sacar de puerta
		else if (isGoalKick()) {
			if (Celta.DEBUG_GOAL_KEEPER)
				System.out.println("Saque de puerta. El portero es el más cercano.");
			commands.add(new CommandMoveTo(GOAL_KEEPER, sp.ballPosition()));
			// patapúm p'arriba
			//commands.add(longPass(GOAL_KEEPER));
		}
		// si el balón está en nuestro campo, posicionamos al portero
		else if (sp.ballPosition().getY() < -15)
			commands.add(new CommandMoveTo(GOAL_KEEPER, getBestPosition()));
		// si el balón está aún muy lejos, movemos el portero a la posición inicial
		else
			commands.add(new CommandMoveTo(GOAL_KEEPER, Alignment.INITIAL[GOAL_KEEPER]));
		
		return commands;
	}
	
	/**
	 * Devuelve la mejor posición del portero respecto al balón.
	 * Es el resultado de hallar la intersección entre la línea de portero (paralela a la línea de meta)
	 * y la bisectriz formada por las rectas A y B, siendo A la recta entre el balón y el poste izquierdo
	 * y B la recta entre el balón y el poste derecho.
	 * @return Posición en la que se deberá situar el portero
	 */
	private Position getBestPosition() {
		Position pos = null;
		// si la pelota está cerca de la línea de fondo pero fuera del área
		if (sp.ballPosition().getY() <= (Constants.LARGO_CAMPO_JUEGO / -2) + (Constants.ANCHO_AREA_GRANDE / 2) 
				&& !isBallInsideOwnSmallArea()) {
			if (sp.ballPosition().getX() < 0) 
				pos = new Position(Constants.posteIzqArcoInf.movePosition(3, 1));
			else
				pos = new Position(Constants.posteDerArcoInf.movePosition(-3, 1));			
		} else {
		
			Line bisectriz1 = null;
			Line bisectriz2 = null;
			Line lineA = new Line(sp.ballPosition(), Constants.posteIzqArcoInf);
			Line lineB = new Line(sp.ballPosition(), Constants.posteDerArcoInf);
			
			// el cambio de signo de N en las paralelas es para sacar las dos bisectrices que son posibles dadas dos rectas
			Line paralellA = lineA.getParalell(10);  
			Line paralellB = lineB.getParalell(-10);
			Position intersection = Line.getIntersection(paralellA, paralellB);
			if (intersection != null)
				bisectriz1 = new Line(sp.ballPosition(), Line.getIntersection(paralellA, paralellB));
	
			paralellA = lineA.getParalell(10);  
			paralellB = lineB.getParalell(10);
			intersection = Line.getIntersection(paralellA, paralellB);
			if (intersection != null)
				bisectriz2 = new Line(sp.ballPosition(), Line.getIntersection(paralellA, paralellB));
			
			// escogemos la bisectriz que intersecta con la línea de meta		
			if (bisectriz1 != null && isGoal(Line.getIntersection(bisectriz1, GOAL_LINE))) 
				pos = Line.getIntersection(bisectriz1, goalKeeperLine);
			if (bisectriz2 != null && isGoal(Line.getIntersection(bisectriz2, GOAL_LINE)))
				pos = Line.getIntersection(bisectriz2, goalKeeperLine);
			
			// si no se ha podido calcular una mejor posición, se mantiene la actual
			if (pos == null)
				pos = sp.myPlayers()[GOAL_KEEPER];
		}
		// si no se ha podido calcular una mejor posición, se mantiene la actual
		if (pos == null)
			pos = sp.myPlayers()[GOAL_KEEPER];
			
		return pos;
	}
	
	/**
	 * Indica si la posición dada está bajo los tres palos.
	 * @param ball	Punto a comprobar
	 * @return		true si está dentro de la portería
	 */
	private boolean isGoal(Position ball) {		
		return (ball != null &&
				ball.getX() > Constants.posteIzqArcoInf.getX() && 
				ball.getX() < Constants.posteDerArcoInf.getX() &&
				ball.getY() == Constants.posteDerArcoInf.getY()); 
	}
	
	/**
	 * Calcula si la trayectoria del balón puede acabar en gol.
	 * @return true si puede ser gol, false si es imposible
	 */
	private boolean isPossibleGoal() {
		boolean couldBeGoal = false; // maloserá
		try {
			Position intersection = Line.getIntersection(getShootTrajectory(), GOAL_LINE);
			couldBeGoal = (isGoal(intersection));
		} catch (IllegalArgumentException e) {
			/*if (Celta.DEBUG_GOAL_KEEPER)
				System.out.println("No se pueda calcular la trayectoria del balón (puntos iguales en ambas iteraciones)");*/
		}
		return couldBeGoal;
	}
	
	/**
	 * Devuelve la posición a la cuál se debe mover el portero para parar el disparo del rival.
	 * @return	posición de interceptación del balón
	 */
	private Position catchRivalShoot() {
		if (isBallInsideOwnSmallArea())
			return sp.ballPosition();
		else					
			return Line.getIntersection(getShootTrajectory(), GOAL_LINE);
	}
	
}
