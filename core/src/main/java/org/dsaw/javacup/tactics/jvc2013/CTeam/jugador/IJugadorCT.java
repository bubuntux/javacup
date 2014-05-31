package org.dsaw.javacup.tactics.jvc2013.CTeam.jugador;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.PosicionCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Disparo;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.Triangulation;

import java.util.Collection;
import java.util.Deque;

public interface IJugadorCT {

	public boolean isControlBalon();

	public PosicionCT getSiguiente();

	public PosicionCT getActual();

	public Deque<PosicionCT> getPosiciones();

	public PlayerDetail getDetalle();

	public int getIndice();

	public Equipo getEquipo();

	public Disparo paseLibre(PosicionCT destino, int itRematar);

	public void iniciarTurno(GameSituations sp, boolean puedeRematar);

	public double distanciaEn(int iteraciones);

	public void setPuedeRecuperarBalon(boolean puede, int it);

	public void disparar(PosicionCT destino, Disparo d);

	public PosicionCT posicionVoronoi();

	public void setSiguiente(PosicionCT siguiente);

	public void setVoronoi(Triangulation propios, Triangulation rivales, Triangulation todos);

	public enum Equipo {
		PROPIO, RIVAL, NEUTRAL;
	}

	public Collection<? extends Command> jugar();

}
