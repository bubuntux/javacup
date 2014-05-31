package org.dsaw.javacup.tactics.jvc2013.emandem.controladores;

import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class ControladorTirosPortero {
	
	private static ControladorTirosPortero instance;
	
	static {
		instance = new ControladorTirosPortero();
	}
	
	private ControladorTirosPortero() {

	}

	public static ControladorTirosPortero getInstance() {
		return instance;
	}

	public CommandHitBall getTiro(final int jugador, final GameSituations situacionPartido) {
		Position destinoBola = null;
		double ejeXDestino = ControladorUtils.getInstance().generaNumeroDobleAleatorioEntre(0, 34);
		double fuerzaRemate = 1;
		boolean tiraLadoDerecho = ControladorUtils.getInstance().generaBoleanoAleatorioEntre();

		destinoBola = Constants.centroArcoSup;
		if (tiraLadoDerecho) {
			destinoBola.setPosition((ejeXDestino * -1), destinoBola.getY());
		} else {
			destinoBola.setPosition(ejeXDestino, destinoBola.getY());
		}

		return new CommandHitBall(jugador, destinoBola, fuerzaRemate, 30);
	}
}
