package org.javahispano.javacup.tacticas.jvc2012.hortalezablamers.utils.remates.simulador;

import java.util.Random;
import java.util.Vector;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.trajectory.FloorTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

import org.javahispano.javacup.tacticas.jvc2012.hortalezablamers.utils.Common;

public class SimuladorRemates {

	private GameSituations gsOriginal;
	private PlayerDetail[] detallesLocal, detallesVisita;//detalle local y visita
	private Position[] posicionVisita;
	
    public SimuladorRemates(GameSituations gsOriginal) {
		this.gsOriginal = gsOriginal;
		this.detallesLocal = gsOriginal.myPlayersDetail();
		this.detallesVisita = gsOriginal.rivalPlayersDetail();
		this.posicionVisita = gsOriginal.rivalPlayers();
		
	}

    private static final double angConvert = Math.PI / 180d;
    private Random rand = new Random();//random para la aleatoriedad

    // variables de entrada
    private int iteracion = 0;//iteration actual
    private Position balon = new Position(Constants.centroCampoJuego);//posicion del ballPosition
    private boolean saque = false;

    // variables calculadas
    private AbstractTrajectory trayectoria = new FloorTrajectory(0, 0);
    private double angTrayectoria = 0, x0Trayectoria = 0, y0Trayectoria = 0, t0Trayectoria;

    /**
     * fija los valores al partido en funcion de lo rescatado del gsOriginal
     */
    private void setGameSituation() {
		this.iteracion = gsOriginal.iteration();
		this.balon = gsOriginal.ballPosition();
		this.saque = gsOriginal.isStarts();
    }
    
    /**Simula un remate con la situacion actual del partido*/
    public double simularRemate(CommandHitBall chb) {
    	int numGoles = 0;
		for(int k = 0; k < 100; k++) {
			setGameSituation();
			executeCommand(chb);//ejecuta los comandos
			
			if(isGoal())
				numGoles++; 
		}

    	return numGoles / (double) 100;
    }

	/**Ejecuta la lista de comandos enviados por las tacticas*/
    private void executeCommand(CommandHitBall cgp) {

        double angulo = 0, error = 0;//el angle y el error
        Position p = null;
        PlayerDetail j = null;//detalles del jugador para obtener sus aptitudes (remate y error)

        /////////////////////////////////////////////
        //////Ejecuta el comando Golpear Balon///////
        /////////////////////////////////////////////
        if (saque) {
            cgp.setHitPower(cgp.getHitPower() * .75);
            saque = false;
        }
        j = detallesLocal[cgp.getPlayerIndex()];//obtiene al jugador
        error = j.getPrecision();//obtiene el error de remate del jugador

        if (cgp.isAngle()) {//si el destino es un angle
        	angulo = cgp.getAngle() * angConvert;//obtiene el angle para el local
        } else {// si el destino es una coordenada
        	p = cgp.getDestiny();
        	angulo = balon.angle(p);//calcula el angle
        }

        error = Constants.getErrorAngular(error);//obtiene el error angular
        double randDouble = rand.nextDouble();
        angulo = angulo + (randDouble * error - error / 2) * Math.PI;//calcula el angle destino
        double vel = cgp.getHitPower() * Constants.getVelocidadRemate(j.getPower());//calcula la velocidad del remate
        double angVer = Math.min(cgp.getVerticalAngle(), Constants.ANGULO_VERTICAL_MAX);
        angVer = Math.max(angVer, 0);
        angVer = angVer * angConvert;
        if (vel != 0) {//si el remate tiene velocidad
            //calcula la velocidad en el plano x/y
            t0Trayectoria = iteracion;
            x0Trayectoria = balon.getX();
            y0Trayectoria = balon.getY();
            trayectoria = new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
            angTrayectoria = angulo;
        }
    }
    
    /**Retorna un array con las coordenadas x,y,z de la trayectoria del ballPosition en la
    iteration indicada.*/
    protected double[] getTrajectory(int iteracion) {
        double time = (iteracion + this.iteracion - t0Trayectoria) / 60d;
        double radio = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double z = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        double x = x0Trayectoria + radio * Math.cos(angTrayectoria);
        double y = y0Trayectoria + radio * Math.sin(angTrayectoria);
        return new double[]{x, y, z};
    }
    
    protected Boolean isGoal() {
    	Vector<Integer> indicesPendientesIntercepcion = new Vector<Integer>();
    	for(int i = 0; i < posicionVisita.length; i++)
    		indicesPendientesIntercepcion.add(i);
    	
    	int it = 1;
        double[] posBalonInicial = getTrajectory(0);
        Position posicionAnterior = new Position(posBalonInicial[0], posBalonInicial[1]);
        while(true) {
            double[] posBalon = getTrajectory(it);
            Position posicionBalon = new Position(posBalon[0], posBalon[1]);
            double alturaBalon = posBalon[2];
            if (alturaBalon <= Constants.ALTO_ARCO) {
            	Vector<Integer> indicesAEliminar = new Vector<Integer>();
            	for(int i : indicesPendientesIntercepcion) {
            		if (alturaBalon <= (Common.dentroAreaRival(posicionBalon) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
            			Position pJug = posicionVisita[i];
            			double dist0 = (double) it * Constants.getVelocidad(detallesVisita[i].getSpeed());
            			double dist = pJug.distance(posicionBalon) - (Common.dentroAreaRival(posicionBalon) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
            			if (dist0 >= dist && it >= gsOriginal.rivalIterationsToKick()[i]) {
            				if(puedeRematar(posicionAnterior, posicionBalon))
            					return false;
            				else
            					indicesAEliminar.add(i);
            			}
            		}
            	}
            	indicesPendientesIntercepcion.removeAll(indicesAEliminar);
            }
            if(balonEnGol(posicionBalon, alturaBalon))
                return true;
            if(!posicionBalon.isInsideGameField(0))
            	return false;
            posicionAnterior = posicionBalon;
            it++;
        }
	}

	protected boolean puedeRematar(Position posicionAnterior, Position posicionBalon) {
		double dx = Math.abs(posicionBalon.getX() - posicionAnterior.getX());
		double dy = Math.abs(posicionBalon.getY() - posicionAnterior.getY());
        double vel = Math.sqrt(dx * dx + dy * dy);
 
        double probabilidad = (7d - vel) / 7d;
        double random = rand.nextDouble();

		return random < probabilidad;
	}

	protected boolean balonEnGol(Position posicionBalon, double alturaBalon) {
		return Math.abs(posicionBalon.getX()) < Constants.LARGO_ARCO / 2 &&
				posicionBalon.getY() > Constants.LARGO_CAMPO_JUEGO / 2 &&
				alturaBalon < Constants.ALTO_ARCO;
	}
}
