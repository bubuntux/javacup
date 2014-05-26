package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.remates;

import java.util.Vector;

import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;

import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.HBConstants;
import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.remates.simulador.SimuladorRemates;

public class RemateAPuertaSimulado {

	protected static final double MAX_RELACION_ANGULOS = 0.5;
	protected static final double MIN_PROBABILIDAD_GOLES = 0.10;
	
	
	public static CommandHitBall rematarAPuerta(GameSituations gs, int indiceRematador) {
		
		SimuladorRemates sr = new SimuladorRemates(gs);
		
		double anguloACentroPorteria = gs.myPlayers()[indiceRematador].angle(Constants.centroArcoSup);
		double anguloAPosteIzdo = gs.myPlayers()[indiceRematador].angle(Constants.posteIzqArcoSup);
		double anguloAPosteDcho = gs.myPlayers()[indiceRematador].angle(Constants.posteDerArcoSup);
	
		// angulo de error
        double error = Constants.getErrorAngular(gs.myPlayersDetail()[indiceRematador].getPrecision());
        double anguloErrorIzda = anguloACentroPorteria + (error / 2) * Math.PI;
        double anguloErrorDcha = anguloACentroPorteria - (error / 2) * Math.PI;

        // si el margen de error es muy grande, ni siquiera pruebo
        if((anguloAPosteIzdo - anguloAPosteDcho) < (anguloErrorIzda - anguloErrorDcha) * MAX_RELACION_ANGULOS)
        	return null;
    
        // pasamos a grados
        anguloACentroPorteria = Math.toDegrees(anguloACentroPorteria);
        anguloAPosteIzdo = Math.toDegrees(anguloAPosteIzdo);
        anguloAPosteDcho = Math.toDegrees(anguloAPosteDcho);
        
        Vector<Double> angulos = new Vector<Double>();
        angulos.add(anguloACentroPorteria);
        
        double deltaAngulo = Math.toDegrees((error / 2) * Math.PI);
        double anguloIzda = anguloACentroPorteria + deltaAngulo;
        double anguloDcha = anguloACentroPorteria - deltaAngulo;
        while(true) {
        	if(anguloIzda >= anguloAPosteIzdo + deltaAngulo && anguloDcha <= anguloAPosteDcho - deltaAngulo)
        		break;
        	
        	if(anguloIzda < anguloAPosteIzdo + deltaAngulo)
        		angulos.add(anguloIzda);
        	if(anguloDcha > anguloAPosteDcho - deltaAngulo)
        		angulos.add(anguloDcha);
        	
        	anguloIzda += deltaAngulo;
        	anguloDcha -= deltaAngulo;
        }
        
        // simulamos los angulos de remate
		double probabilidadMaximaDeGol = 0.;
		CommandHitBall chbMejorRemate = null;
		for(Double angulo : angulos) { // indice de angulos
			CommandHitBall chb = new CommandHitBall(indiceRematador, angulo, HBConstants.FUERZA_GOLPEO_MAXIMA, HBConstants.ANGULO_GOLPEO_TIRO);
			double probGol = sr.simularRemate(chb);
			if(probGol > probabilidadMaximaDeGol) {
				probabilidadMaximaDeGol = probGol;
				chbMejorRemate = chb;
			}
		}
		
		// si es mejor que cierta probabilidad, devolvemos el remate
		if(probabilidadMaximaDeGol > MIN_PROBABILIDAD_GOLES)
			return chbMejorRemate;
		
		return null;
	}
}
