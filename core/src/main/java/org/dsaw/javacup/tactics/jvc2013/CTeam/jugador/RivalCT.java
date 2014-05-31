package org.dsaw.javacup.tactics.jvc2013.CTeam.jugador;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.ICTeam2011;

import java.util.ArrayList;
import java.util.Collection;

public class RivalCT extends AbstractJugador {

	public RivalCT(Equipo equipo, int indice, PlayerDetail detalle, ICTeam2011 tactica) {
		super(equipo, indice, detalle, tactica);
	}

	@Override
	public Collection<? extends Command> jugar() {
		return new ArrayList<Command>(0);
	}
}
