package org.dsaw.javacup.tacticas.jvc2012.romedal.util;

import java.util.LinkedList;

import org.dsaw.javacup.model.command.CommandHitBall;

import org.dsaw.javacup.tacticas.jvc2012.romedal.TacticaRomedalusTeam;

public class ComandoGolpear implements Comparable<ComandoGolpear> {
	private CommandHitBall		comando;
	private double				anguloXY;
	private int					iterJugador;
	private int					iterRival;
	private LinkedList<Balon>	trayectoria;
	private final int			difIter;

	private final boolean		arriba;
	private boolean				autoPase;
	private boolean				mejorSkill;

	// @Deprecated
	// public ComandoGolpear(final CommandHitBall comando, final LinkedList<Balon> trayectoria, final double anguloXY, final int iterJugador, final int iterRival, final int iterBalon) {
	// super();
	// this.comando = comando;
	// this.trayectoria = trayectoria;
	// this.anguloXY = anguloXY;
	// this.iterJugador = iterJugador;
	// this.iterRival = iterRival;
	// }

	public ComandoGolpear(final CommandHitBall comando, final LinkedList<Balon> trayectoria, final double anguloXY, final int iterJugador, final int iterRival, final boolean autoPase, final boolean mejorSkill) {
		super();
		this.comando = comando;
		this.trayectoria = trayectoria;
		this.anguloXY = anguloXY;
		this.iterJugador = iterJugador;
		this.iterRival = iterRival;
		this.difIter = this.iterRival - this.iterJugador;
		arriba = trayectoria.get(0).getPosition().getY() < comando.getDestiny().getY();
	}

	public CommandHitBall getComando() {
		return comando;
	}

	public void setComando(final CommandHitBall comando) {
		this.comando = comando;
	}

	public double getAnguloXY() {
		return anguloXY;
	}

	public void setAnguloXY(final double anguloXY) {
		this.anguloXY = anguloXY;
	}

	public int getIterJugador() {
		return iterJugador;
	}

	public void setIterJugador(final int iterJugador) {
		this.iterJugador = iterJugador;
	}

	public int getIterRival() {
		return iterRival;
	}

	public void setIterRival(final int iterRival) {
		this.iterRival = iterRival;
	}

	public LinkedList<Balon> getTrayectoria() {
		return trayectoria;
	}

	public void setTrayectoria(final LinkedList<Balon> trayectoria) {
		this.trayectoria = trayectoria;
	}

	public int getDifIter() {
		return difIter;
	}

	public boolean isArriba() {
		return arriba;
	}

	public boolean isAutoPase() {
		return autoPase;
	}

	public boolean isMejorSkill() {
		return mejorSkill;
	}

	@Override
	public String toString() {
		return comando.getPlayerIndex() + "\t" + comando.getDestiny() + "\tAngXY: " + anguloXY + "\tAngZ: " + TacticaRomedalusTeam.round(comando.getVerticalAngle(), 1) + "\tIter: " + iterJugador + "\tIterRival: " + iterRival + "\tdifIter" + difIter;
	}

	@Override
	public int compareTo(final ComandoGolpear o) {
		if (iterRival != o.iterRival) {
			final int difA = iterRival - iterJugador;
			final int difB = o.iterRival - o.iterJugador;
			if (difA > 0 || difB > 0) {
				return difA - difB;
			}
		}
		// if (iterJugador != o.iterJugador) {
		// return iterJugador > o.iterJugador ? -1 : 1;
		// }
		return 0;// (int) Math.round(getComando().getDestiny().getY() - o.getComando().getDestiny().getY());
	}
}