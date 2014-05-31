package org.dsaw.javacup.tactics.jvc2013.emandem.controladores;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.emandem.enums.JUGADOR;

import java.util.ArrayList;
import java.util.List;

public class ControladorPortero {

	private static ControladorPortero instance;

	static {
		instance = new ControladorPortero();
	}

	public static ControladorPortero getInstance() {
		return instance;
	}

	public List<Command> getComandos(final GameSituations situacionPartido, final Position[] alineacionActual) {
		List<Command> comandos = new ArrayList<Command>();

		if (ControladorEstadoPartido.getInstance().isPorteroPuedeRematar()) {
			comandos.add(ControladorTirosPortero.getInstance().getTiro(JUGADOR.PORTERO.numero(), situacionPartido));

		} else if(ControladorEstadoPartido.getInstance().isPorteroPuedeRecuperarBalon()) {
			Position posicionBalon = ControladorEstadoPartido.getInstance().getPosicionRecuperaBalon();

			if (ControladorEstadoPartido.getInstance().isEstaBalonEnElAreaGrandeSup()) {
				comandos.add(new CommandMoveTo(JUGADOR.PORTERO.numero(), new Position(posicionBalon.getX(), posicionBalon.getY())));

			} else {
				if (situacionPartido.myPlayers()[JUGADOR.PORTERO.numero()].getX() != alineacionActual[JUGADOR.PORTERO.numero()].getX() &&
						situacionPartido.myPlayers()[JUGADOR.PORTERO.numero()].getY() != alineacionActual[JUGADOR.PORTERO.numero()].getY()) {
					comandos.add(new CommandMoveTo(JUGADOR.PORTERO.numero(), new Position(alineacionActual[JUGADOR.PORTERO.numero()].getX(), alineacionActual[JUGADOR.PORTERO.numero()].getY())));
				}
			}
		} else {
			if (situacionPartido.myPlayers()[JUGADOR.PORTERO.numero()].getX() != alineacionActual[JUGADOR.PORTERO.numero()].getX() &&
					situacionPartido.myPlayers()[JUGADOR.PORTERO.numero()].getY() != alineacionActual[JUGADOR.PORTERO.numero()].getY()) {
				comandos.add(new CommandMoveTo(JUGADOR.PORTERO.numero(), new Position(alineacionActual[JUGADOR.PORTERO.numero()].getX(), alineacionActual[JUGADOR.PORTERO.numero()].getY())));
			}
		}
		return comandos;
	}

}
