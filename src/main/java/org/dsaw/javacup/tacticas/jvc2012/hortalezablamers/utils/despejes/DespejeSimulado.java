package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.despejes;

import java.util.Collections;
import java.util.Vector;

import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;

import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.HBConstants;
import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.despejes.simulador.CommandHitBallSimulated;
import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.despejes.simulador.SimuladorDespejes;

public class DespejeSimulado {

	public static final double DELTA_ANGULO = 7.5;
	public static final double MAX_ANGULO = 180.;

	public static Vector<Double> angulos = new Vector<Double>();

	static {
		angulos.add(0.);
		double angulo = DELTA_ANGULO;
		for(; angulo < MAX_ANGULO; angulo += DELTA_ANGULO) {
			angulos.add(angulo);
			angulos.add(-angulo);
		}
		angulos.add(MAX_ANGULO);
	}

	public static CommandHitBall despejar(GameSituations gs, int indiceRematador) {
		
		SimuladorDespejes sd = new SimuladorDespejes(gs);
		
		Vector<CommandHitBallSimulated> comandosSimulados = new Vector<CommandHitBallSimulated>();
		for(double angulo : angulos) { // indice de angulos
			CommandHitBall chb = new CommandHitBall(indiceRematador, angulo, HBConstants.FUERZA_GOLPEO_MAXIMA, HBConstants.ANGULO_GOLPEO_DESPEJE);
			CommandHitBallSimulated chbs = sd.simularDespeje(chb);
			if(chbs != null)
				comandosSimulados.add(chbs);

			chb = new CommandHitBall(indiceRematador, angulo, HBConstants.FUERZA_GOLPEO_MAXIMA, Constants.ANGULO_VERTICAL_MAX);
			chbs = sd.simularDespeje(chb);
			if(chbs != null)
				comandosSimulados.add(chbs);
		}
		
		Collections.sort(comandosSimulados);
		if(!comandosSimulados.isEmpty())
			return comandosSimulados.firstElement().getChb();
		
		// TODO: hacer esto si es el portero, si es un despeje de otro jugador, despejar a una posicion mas vetajosa
		return new CommandHitBall(indiceRematador, 90, HBConstants.FUERZA_GOLPEO_MAXIMA, Constants.ANGULO_VERTICAL_MAX);
	}
}
