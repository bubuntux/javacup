package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.pases;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;

import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.HBConstants;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.pases.simulador.CommandHitBallSimulated;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.pases.simulador.SimuladorPases;

public class PaseAlHuecoSimulado {
	
	public static final double DELTA_ANGULO = 7.5;
	public static final double MAX_ANGULO_1 = 45.;
	public static final double MAX_ANGULO_2 = 105.;
	public static final double MAX_ANGULO_3 = 180.;

	public static Vector<Double> angulosRelativos1 = new Vector<>();
	public static Vector<Double> angulosRelativos2 = new Vector<>();
	public static Vector<Double> angulosRelativos3 = new Vector<>();
	public static Vector<Double> potencias = new Vector<>();
	public static Vector<Double> angulosVerticales = new Vector<>();
	public static Random random = new Random();

	static {
		angulosRelativos1.add(0.);
		double angulo = DELTA_ANGULO;
		for(; angulo <= MAX_ANGULO_1; angulo += DELTA_ANGULO) {
			angulosRelativos1.add(angulo);
			angulosRelativos1.add(-angulo);
		}
		for(; angulo <= MAX_ANGULO_2; angulo += DELTA_ANGULO) {
			angulosRelativos2.add(angulo);
			angulosRelativos2.add(-angulo);
		}
		for(; angulo < MAX_ANGULO_3; angulo += DELTA_ANGULO) {
			angulosRelativos3.add(angulo);
			angulosRelativos3.add(-angulo);
		}
		angulosRelativos3.add(MAX_ANGULO_3);
	
		for(double potencia = .45; potencia < HBConstants.FUERZA_GOLPEO_MAXIMA; potencia += .10)
			potencias.add(potencia);

		angulosVerticales.add(0.);
		angulosVerticales.add(10.);
		angulosVerticales.add(20.);
		angulosVerticales.add(30.);
		angulosVerticales.add(40.);
	}
	
	public static List<Command> pasar(GameSituations gs, int indicePasador) {
		CommandHitBallSimulated chbsToReturn = obtenerMejorPase(gs, indicePasador);
		if(chbsToReturn == null)
			return null;
//		HBLogger.log(
//				"\nJug: " + (indicePasador+1) + 
//				"\nIt: " + gs.iteration() + 
//				"\nPos: " + gs.ballPosition() + 
//				"\nItDiff: " + chbsToReturn.getDiferenciaIteraciones() + 
//				"\nItRec: " + chbsToReturn.getIteracionesRecuperacion() + 
//				"\nJugRec: " + (chbsToReturn.getIndiceReceptor()+1) + 
//				"\nAng: " + chbsToReturn.getChb().getAngle() + 
//				"\nPot: " + chbsToReturn.getChb().getHitPower() +
//				"\nAngV: " + chbsToReturn.getChb().getVerticalAngle()
//				);
		
		// TODO: si el receptor es el mismo que el pasador, modifico la potencia para hacer un autopase preciso
		
		// calculo la posicion a la que debe correr el receptor
		CommandMoveTo cmt = new CommandMoveTo(chbsToReturn.getIndiceReceptor(), chbsToReturn.getPosicionRecepcionMedia());

		// devuelvo los comandos
		Vector<Command> comandos = new Vector<>();
		comandos.add(chbsToReturn.getChb());
		comandos.add(cmt);
		return comandos;
	}

	/**
	 * realiza simulaciones y obtiene el mejor pase de un conjunto de simulaciones
	 * @param gs
	 * @param indicePasador
	 * @return
	 */
	private static CommandHitBallSimulated obtenerMejorPase(GameSituations gs, int indicePasador) {
		SimuladorPases sp = new SimuladorPases(gs);
		
		//PASES HACIA DELANTE
		Vector<CommandHitBallSimulated> comandosSimulados = simularComandosPase(sp, gs, indicePasador, angulosRelativos1);
		// filtramos los que tienen la diferencia de iteraciones en positivo
		Vector<CommandHitBallSimulated> comandosSimuladosBeneficiosos = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados)
			if(chbs.getDiferenciaIteraciones() <= -3)
				comandosSimuladosBeneficiosos.add(chbs);

		// si no hay ningun pase posible devolvemos null, y si lo hay devolvemos el mejor pase
		if(!comandosSimuladosBeneficiosos.isEmpty())
			return decidirMejorPase(gs, comandosSimuladosBeneficiosos);
		
		// filtramos los que tienen la diferencia de iteraciones en positivo
		Vector<CommandHitBallSimulated> comandosSimuladosAlLimite = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados)
			if(chbs.getDiferenciaIteraciones() < 0)
				comandosSimuladosAlLimite.add(chbs);

		// si no hay ningun pase posible devolvemos null, y si lo hay devolvemos el mejor pase
		if(!comandosSimuladosAlLimite.isEmpty())
			return decidirMejorPase(gs, comandosSimuladosAlLimite);

		//PASES HACIA LOS LATERALES
		comandosSimulados = simularComandosPase(sp, gs, indicePasador, angulosRelativos2);
		// filtramos los que tienen la diferencia de iteraciones en positivo
		comandosSimuladosBeneficiosos = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados)
			if(chbs.getDiferenciaIteraciones() <= -3 && !paseDemasiadoAtras(gs, chbs))
				comandosSimuladosBeneficiosos.add(chbs);

		// si no hay ningun pase posible devolvemos null, y si lo hay devolvemos el mejor pase
		if(!comandosSimuladosBeneficiosos.isEmpty())
			return decidirMejorPase(gs, comandosSimuladosBeneficiosos);
		
		// filtramos los que tienen la diferencia de iteraciones en positivo
		comandosSimuladosAlLimite = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados)
			if(chbs.getDiferenciaIteraciones() < 0 && !paseDemasiadoAtras(gs, chbs))
				comandosSimuladosAlLimite.add(chbs);

		// si no hay ningun pase posible devolvemos null, y si lo hay devolvemos el mejor pase
		if(!comandosSimuladosAlLimite.isEmpty())
			return decidirMejorPase(gs, comandosSimuladosAlLimite);

		//PASES HACIA ATRAS
		comandosSimulados = simularComandosPase(sp, gs, indicePasador, angulosRelativos3);
		// filtramos los que tienen la diferencia de iteraciones en positivo
		comandosSimuladosBeneficiosos = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados)
			if(chbs.getDiferenciaIteraciones() <= -3 && !paseDemasiadoAtras(gs, chbs))
				comandosSimuladosBeneficiosos.add(chbs);

		// si no hay ningun pase posible devolvemos null, y si lo hay devolvemos el mejor pase
		if(!comandosSimuladosBeneficiosos.isEmpty())
			return decidirMejorPase(gs, comandosSimuladosBeneficiosos);
		
		// filtramos los que tienen la diferencia de iteraciones en positivo
		comandosSimuladosAlLimite = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados)
			if(chbs.getDiferenciaIteraciones() < 0 && !paseDemasiadoAtras(gs, chbs))
				comandosSimuladosAlLimite.add(chbs);

		// si no hay ningun pase posible devolvemos null, y si lo hay devolvemos el mejor pase
		if(!comandosSimuladosAlLimite.isEmpty())
			return decidirMejorPase(gs, comandosSimuladosAlLimite);

		return null;
	}

	private static boolean paseDemasiadoAtras(GameSituations gs,
			CommandHitBallSimulated chbs) {
		return gs.ballPosition().distance(chbs.getPosicionRecepcionMedia()) > Constants.LARGO_CAMPO_JUEGO / 3. || 
				chbs.getPosicionRecepcionMedia().getY() < -Constants.LARGO_CAMPO_JUEGO / 6.;
	}

	/**
	 * devuelve el resultado de simular los pases con los angulos recibidos
	 * @param sp 
	 * @param gs
	 * @param indicePasador
	 * @param angulosRelativos 
	 * @return
	 */
	private static Vector<CommandHitBallSimulated> simularComandosPase(SimuladorPases sp, GameSituations gs, int indicePasador, Vector<Double> angulosRelativos) {
		Vector<CommandHitBallSimulated> comandosSimulados = new Vector<>();
		double anguloAPorteria = Math.toDegrees(gs.ballPosition().angle(Constants.centroArcoSup));
		for(double anguloRelativo : angulosRelativos) // indice de angulos
			for(double potencia : potencias) // indice de potencias
				for(double anguloVertical : angulosVerticales) { // indice de angulos verticales
					CommandHitBall chb = new CommandHitBall(indicePasador, anguloAPorteria + anguloRelativo, potencia, anguloVertical);
					if(!esPaseHaciaFuera(gs, chb)) {
						CommandHitBallSimulated chbs = sp.simularPase(chb);
						if(chbs != null)
							comandosSimulados.add(chbs);
					}
				}
		
		return comandosSimulados;
	}

	/**
	 * devuelve true si el comando de pase va hacia fuera
	 * @param gs
	 * @param chb
	 * @return
	 */
	protected static boolean esPaseHaciaFuera(GameSituations gs, CommandHitBall chb) {
		// evitamos hacer pases hacia la linea de fuera
		if((Constants.ANCHO_CAMPO_JUEGO / 2 - Math.abs(gs.ballPosition().getX())) < 5) {
			if(gs.ballPosition().getX() > 0 && chb.getAngle() < 90)
				return true;
			if(gs.ballPosition().getX() < 0 && chb.getAngle() > 90)
				return true;
		}
		// evitamos hacer pases hacia la linea de fondo
		if((Constants.LARGO_CAMPO_JUEGO / 2 - Math.abs(gs.ballPosition().getY())) < 5)
			if(gs.ballPosition().getX() >= 0) {
				if(chb.getAngle() < 180 || chb.getAngle() > 270)
					return true;
			}
			else {
				if(chb.getAngle() > 0 || chb.getAngle() < -90)
					return true;
			}
		return false;
	}
	
	/**
	 * Obtiene el mejor pase ordenandolos segun el criterio de ordenacion de la clase CommandHitBallSimulated.
	 * usa los pases mas seguros en cuanto a diferencia de iteraciones para ser recuperados
	 * @param gs
	 * @param comandosSimulados
	 * @return
	 */
	private static CommandHitBallSimulated decidirMejorPase(GameSituations gs, Vector<CommandHitBallSimulated> comandosSimulados) {
		Collections.sort(comandosSimulados);

		double diferencia = comandosSimulados.firstElement().getDiferenciaIteraciones();
		Vector<CommandHitBallSimulated> comandosSimuladosDeIgualDiferencia = new Vector<>();
		for(CommandHitBallSimulated chbs : comandosSimulados) {
			if(chbs.getDiferenciaIteraciones() == diferencia)
				comandosSimuladosDeIgualDiferencia.add(chbs);
			else
				break;
		}

		return comandosSimuladosDeIgualDiferencia.get(random.nextInt(comandosSimuladosDeIgualDiferencia.size()));
	}
}
