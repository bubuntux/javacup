package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.movimientos;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.Alineacion;
import org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.Common;

public class MovimientoBascular {

	private static Position alineacionEnJuego[] = Alineacion.alineacionEnJuego;
	private static Position[] zonasLD = Alineacion.zonasLD;
	private static Position[] zonasRU = Alineacion.zonasRU;

	/**
	 * Para el movimiento bascular del equipo
	 * @param posicionBalon
	 * @param indicePorteroPropio
	 * @return comandor para mover a los jugadores basculando
	 */
	public static List<Command> movimientoBascular(Position posicionBalon, 
			int indicePorteroPropio) {
		LinkedList<Command> comandos = new LinkedList<>();
		
		for (int i = 0; i < 11; i++) {
			Position p;
			if(i == indicePorteroPropio)
				p = movimientoBascularPortero(posicionBalon);
			else
				p = movimientoBascularJugador(i, posicionBalon);
			
			comandos.add(new CommandMoveTo(i, p));
		}
		return comandos;
	}

	/**
	 * Esta funcion calcula la posicion que deber� ocupar el jugador en el campo, en funcion de
	 * un punto sobre el que pivota, y un rectangulo de maximo alcance
	 * 
	 * @param indice indice del jugador al que hay que calcular la posicion
	 * @param posicionBalon posicion actual del balon en el campo
	 * @return la posicion a la que se debe dirigir el jugador
	 */
	private static Position movimientoBascularJugador(int indice, Position posicionBalon) {
		double dividendoX;
		double dividendoY;
		
		// calculamos parte de la formula dependiendo del cuadrante que ocupa en el campo
		if(posicionBalon.getX() > 0)
			dividendoX = zonasRU[indice].getX() - alineacionEnJuego[indice].getX();
		else
			dividendoX = alineacionEnJuego[indice].getX() - zonasLD[indice].getX(); 
		
		if(posicionBalon.getY() > 0)
			dividendoY = zonasRU[indice].getY() - alineacionEnJuego[indice].getY();
		else
			dividendoY = alineacionEnJuego[indice].getY() - zonasLD[indice].getY();
			
		// calculamos la posicion en funcion de lo que el balon se separe del centro del campo
		// y de una zona de infuencia del jugador
		double posicionX = alineacionEnJuego[indice].getX() + 
		((dividendoX / (Constants.ANCHO_CAMPO_JUEGO / 2)) * posicionBalon.getX());
		double posicionY = alineacionEnJuego[indice].getY() + 
		((dividendoY / (Constants.LARGO_CAMPO_JUEGO / 2)) * posicionBalon.getY());

		return new Position(posicionX, posicionY);
	}

	/**
	 * Esta funcion calcula la posicion que deber� ocupar el portero en el campo.
	 * Se situa siempre tratando de cubrir la mayor cantidad de porteria posible
	 * @param posicionBalon
	 * @return
	 */
	private static Position movimientoBascularPortero(Position posicionBalon) {
		Position posicionPaloIzdo = Constants.posteIzqArcoInf;
		Position posicionPaloDcho = Constants.posteDerArcoInf;

		double posicionX;
		double posicionY =
			(
					(Constants.ANCHO_AREA_CHICA / Constants.LARGO_CAMPO_JUEGO) * 
					(posicionBalon.getY() +  Constants.LARGO_CAMPO_JUEGO / 2)) - 
					Constants.LARGO_CAMPO_JUEGO / 2;
		
		// calcular las direcciones a los palos
		double direccionPaloIzdo = Common.calcularDireccion(posicionBalon, Constants.posteIzqArcoInf);
		double direccionPaloDcho = Common.calcularDireccion(posicionBalon, Constants.posteDerArcoInf);;

		try {
			posicionX = posicionBalon.getX() + (
					(posicionY - posicionBalon.getY()) /
					Math.tan((direccionPaloIzdo + direccionPaloDcho) / 2));
		} 
		catch (ArithmeticException e) {
			// en el caso de que el balon este en frente justo del centro de la porteria
			// si esta a la izda de la porteria, se pone en el palo izquierdo
			if(posicionBalon.getX() < 0)
				posicionX = posicionPaloIzdo.getX();
			// si esta a la dcha de la porteria, se pone en el palo derecho
			else if(posicionBalon.getX() > 0)
				posicionX = posicionPaloDcho.getX();
			// si esta a la altura del centro de la la porteria, se pone en el centro
			else
				posicionX = 0;
		}

		return new Position(posicionX, posicionY);
	}

}
