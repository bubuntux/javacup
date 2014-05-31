package org.dsaw.javacup.tactics.jvc2013.CTeam.tactica;

import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT;

import java.util.List;

public interface ICTeam2011 {

  public List<IJugadorCT> getJugadores(IJugadorCT.Equipo equipo);

  public int iteracionesRecuperacion();

  public int iteracionesRecuperacionRival();

  public List<IJugadorCT> jugadoresRecuperacion();

  public List<IJugadorCT> previoJugadoresRecuperacion();

  public List<IJugadorCT> rivalesRecuperacion();

  public IJugadorCT.Equipo posesionBalon();

  public Position[] alineacionActual();

  public boolean posicionUsada(PosicionCT p);

  public void usada(PosicionCT p);

  public List<IJugadorCT> recuperando();

  public void ultimoPase(PosicionCT ultimo);

  public PosicionCT getUltimoPase();

  public boolean cambioPosesion();
}
