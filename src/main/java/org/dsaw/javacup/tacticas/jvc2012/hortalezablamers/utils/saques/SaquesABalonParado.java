package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.saques;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;

import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.HBConstants;
import org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.pases.PaseAlHuecoSimulado;

public class SaquesABalonParado {

	/**
	 * Realiza el golpeo a balon parado (corners, saques de puerta, saques de centro)
	 * @param gs
	 */
	public static List<Command> sacarABalonParado(GameSituations gs) {
		LinkedList<Command> comandos = new LinkedList<>();
		
		int rematan[] = gs.canKick();
		if(rematan.length > 0) {
			// para el saque de puerta, saca al centro del campo, con un angulo d 45� para evitar rivales
			if(gs.ballPosition().equals(HBConstants.POSICION_SAQUE_PUERTA_IZDA) || gs.ballPosition().equals(HBConstants.POSICION_SAQUE_PUERTA_DCHA)) {
				comandos.add(new CommandHitBall(rematan[0], Constants.centroCampoJuego, HBConstants.FUERZA_GOLPEO_MAXIMA, HBConstants.ANGULO_GOLPEO_DESPEJE));
			}
			// para el saque de centro, saca al centro del campo propio
			else if(gs.ballPosition().equals(Constants.centroCampoJuego)) { 
				comandos.add(new CommandHitBall(rematan[0], HBConstants.POSICION_CENTRO_CAMPO_PROPIO, HBConstants.FUERZA_GOLPEO_MEDIA, HBConstants.ANGULO_GOLPEO_PASE));
			}
			// para el saque de corner, saca al punto de penalty
			else if(gs.ballPosition().equals(Constants.cornerSupIzq) || gs.ballPosition().equals(Constants.cornerSupDer)) { 
				comandos.add(new CommandHitBall(rematan[0], Constants.penalSup, HBConstants.FUERZA_GOLPEO_MAXIMA, HBConstants.ANGULO_GOLPEO_CUELGUE));
			}
			else {
				List<Command> comandosDePase = PaseAlHuecoSimulado.pasar(gs, rematan[0]);
				if(comandosDePase != null)
					comandos.addAll(comandosDePase);
				else
					comandos.add(new CommandHitBall(rematan[0], Constants.penalSup, HBConstants.FUERZA_GOLPEO_MAXIMA, false));
			}
		}
		return comandos;
	}

}
