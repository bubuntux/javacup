package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.despejes.simulador;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.trajectory.FloorTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.Common;

import java.util.Random;

public class SimuladorDespejes {

	private static final int NUMERO_SIMULACIONES = 10;

	private GameSituations gsOriginal;
	private PlayerDetail[] detallesLocal, detallesVisita;//detalle local y visita
	private Position[] posicionVisita;
	
    public SimuladorDespejes(GameSituations gsOriginal) {
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
    
    /**Simula un remate con la situacion actual del partido
     * @param indicePorteroRival */
    public CommandHitBallSimulated simularDespeje(CommandHitBall chb) {
    	try {
			double x = 0.;
			double y = 0.;
			for(int k = 0; k < NUMERO_SIMULACIONES; k++) { // indice de estadistica de despeje
				setGameSituation();
				executeCommand(chb);//ejecuta los comandos

				Position posicionRecuperacionRival = getRivalPlayersRecoveryDistance();
				x += posicionRecuperacionRival.getX();
				y += posicionRecuperacionRival.getY();
			}
			Position posicionRecepcionMedia = new Position(x / (double) NUMERO_SIMULACIONES, y / (double) NUMERO_SIMULACIONES);
			return new CommandHitBallSimulated(chb, posicionRecepcionMedia);
		} 
    	catch (RecoveryException e) {
			return null;
		}
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
 
    /**retorna la posicion donde se puede recuperar el ballPosition por los jugadores rivales
     * @param indicePasador 
     * @throws RecoveryException */
    protected Position getRivalPlayersRecoveryDistance() throws RecoveryException {
    	int it = 1;
        while(true) {
            double[] posBalon = getTrajectory(it);
            Position posicionBalon = new Position(posBalon[0], posBalon[1]);
            if(!posicionBalon.isInsideGameField(0)) {
            	if(balonEnGol(posicionBalon, posBalon[2]))
            		throw new RecoveryException();
            	else {
            		if(posicionBalon.getY() < -Constants.LARGO_CAMPO_JUEGO / 2.)
            			return Constants.cornerInfIzq;
            		if(posicionBalon.getY() > Constants.LARGO_CAMPO_JUEGO / 2.)
            			return Constants.centroArcoSup;
            		else
                    	return posicionBalon;
            	}
            }      
            if(posBalon[2] <= Constants.ALTO_ARCO) {
                for(int i = 0; i < posicionVisita.length; i++) {
                    if(posBalon[2] <= (detallesVisita[i].isGoalKeeper() && Common.dentroAreaPropia(posicionBalon) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    	Position pJug = posicionVisita[i];
                        double dist0 = (double) it * Constants.getVelocidad(detallesVisita[i].getSpeed());
                        double dist = pJug.distance(posicionBalon) - (detallesVisita[i].isGoalKeeper() && Common.dentroAreaPropia(posicionBalon) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        if (dist0 >= dist && it >= gsOriginal.rivalIterationsToKick()[i]) {
                        	if(Common.dentroAreaPropia(posicionBalon))
                        		throw new RecoveryException();
                        	return posicionBalon;
                        }
                    }
                }
            }
            it++;
        }
    }

	protected boolean balonEnGol(Position posicionBalon, double alturaBalon) {
		return Math.abs(posicionBalon.getX()) < Constants.LARGO_ARCO / 2 &&
				posicionBalon.getY() < -Constants.LARGO_CAMPO_JUEGO / 2 &&
				alturaBalon < Constants.ALTO_ARCO;
	}
}
