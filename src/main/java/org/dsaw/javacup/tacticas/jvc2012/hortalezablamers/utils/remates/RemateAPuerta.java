package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.remates;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.HBConstants;
import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.Common;

public class RemateAPuerta {

	/**
	 * Calcula el angulo horizontal de remate en funcion de la posicion del balon
	 * y la posicion del portero rival 
	 * @param gs
	 * @param indicePorteroRival 
	 * @param i 
	 * @return
	 */
	public static CommandHitBall rematarAPuerta(GameSituations gs, int indicePorteroRival, int indiceRematador) {
		double direccionRemate;

		Position posicionBalon = gs.ballPosition();
		Position posicionPorteroRival = gs.rivalPlayers()[indicePorteroRival];

		// calcular el angulo a los palos, al porteria y el alcance del portero por izda y dcha
		double direccionPaloIzdo = Common.calcularDireccion(posicionBalon, Constants.posteIzqArcoSup);
		double direccionPaloDcho = Common.calcularDireccion(posicionBalon, Constants.posteDerArcoSup);
		double direccionPortero = Common.calcularDireccion(posicionBalon, posicionPorteroRival);
		double dx = posicionPorteroRival.getX() - posicionBalon.getX();
		double dy = posicionPorteroRival.getY() - posicionBalon.getY();
		double anguloAlcancePortero = Math.atan2(
				HBConstants.FACTOR_MOVIMIENTO_PORTERO * Constants.DISTANCIA_CONTROL_BALON_PORTERO,
				Math.hypot(dx, dy));

		double direccionAlcancePorteroIzda = direccionPortero + anguloAlcancePortero;
		double direccionAlcancePorteroDcha = direccionPortero - anguloAlcancePortero;

		// calculamos el ancho de la zona de remate por la izda y dcha
		double anguloRemateIzda = Common.calcularAngulo(direccionPaloIzdo, direccionAlcancePorteroIzda);
		double anguloRemateDcha = Common.calcularAngulo(direccionAlcancePorteroDcha, direccionPaloIzdo);

		// si no hay zona de remate por la izda y dcha, devuelve null
		if((anguloRemateIzda < 0) && (anguloRemateDcha < 0)) {
			return null;
		}
		// si lo hay, remata al hueco de la izda o de la dcha
		else {
			// si el mayor hueco esta a la izda
			if(anguloRemateIzda > anguloRemateDcha) {
				// si el portero esta fuera de la porteria
				if(anguloRemateIzda > Common.calcularAngulo(direccionPaloIzdo, direccionPaloDcho))
					direccionRemate = Common.calcularDireccion(posicionBalon, Constants.centroArcoSup);
				else
					direccionRemate = (direccionPaloIzdo + direccionAlcancePorteroIzda) / 2;
			}
			// si el mayor hueco esta a la dcha
			else {
				// si el portero esta fuera de la porteria
				if(anguloRemateDcha > Common.calcularAngulo(direccionPaloIzdo, direccionPaloDcho))
					direccionRemate = Common.calcularDireccion(posicionBalon, Constants.centroArcoSup);
				else
					direccionRemate = (direccionPaloDcho + direccionAlcancePorteroDcha) / 2;
			}
		}
		
		return new CommandHitBall(indiceRematador, Math.toDegrees(direccionRemate), HBConstants.FUERZA_GOLPEO_MAXIMA, HBConstants.ANGULO_GOLPEO_TIRO);
	}
}
