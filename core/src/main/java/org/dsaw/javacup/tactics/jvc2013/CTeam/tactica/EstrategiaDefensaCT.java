package org.dsaw.javacup.tactics.jvc2013.CTeam.tactica;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.util.DistanciaMinima;

import java.util.ArrayList;
import java.util.List;

import static org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT.Equipo.*;

public class EstrategiaDefensaCT implements IEstrategiaCT {
	private final GameSituations sp;

	private final ICTeam2011 tactica;

	public EstrategiaDefensaCT(GameSituations sp, ICTeam2011 tactica) {
		this.sp = sp;
		this.tactica = tactica;
	}

	@Override
	public boolean ejecutarIteracion() {
		double[] posB = sp.getTrajectory(tactica.iteracionesRecuperacion());
		PosicionCT balon = new PosicionCT(posB[0], posB[1]);
		double aBalon = sp.ballPosition().angle(balon.getPos());
		
		int maxRecuperacion = 3;
		boolean controlBalon = false;
		double p[] = new double[] { 0.0, -0.25, 0.25 };
		int idx = 0;
		List<IJugadorCT> aRecuperar = new ArrayList<IJugadorCT>();
		for (IJugadorCT jug : tactica.previoJugadoresRecuperacion()) {
			if (tactica.jugadoresRecuperacion().contains(jug)) {
				aRecuperar.add(jug);
				if (aRecuperar.size() >= maxRecuperacion) {
					break;
				}
			}
		}

		for (IJugadorCT jug : tactica.jugadoresRecuperacion()) {
			if (jug.getIndice() == 0)
				continue;
			if (tactica.recuperando().size() > tactica.rivalesRecuperacion().size()) {
				break;
			}
			aRecuperar.add(jug);

			if (aRecuperar.size() >= maxRecuperacion) {
				break;
			}
		}

		for (IJugadorCT jug : aRecuperar) {
			controlBalon = controlBalon || jug.isControlBalon();
			tactica.recuperando().add(jug);
			jug.setSiguiente(balon.moverAngulo(aBalon, p[idx++ % p.length]));
		}

		List<IJugadorCT> restantes = new ArrayList<IJugadorCT>();
		for (IJugadorCT jug : tactica.getJugadores(PROPIO)) {
			if (!tactica.recuperando().contains(jug) && jug.getIndice() >= 1 && jug.getIndice() < 10) {
				if (restantes.size() < 3) {
					restantes.add(jug);
				}
			}
		}

		List<PosicionCT> posRivales = new ArrayList<PosicionCT>(5);
		IJugadorCT[] rivales = tactica.getJugadores(RIVAL).toArray(new IJugadorCT[0]);
		PosicionCT[] rivalesPos = new PosicionCT[11];
		for (int i = 0; i < rivalesPos.length; i++) {
			rivalesPos[i] = rivales[i].getActual();
		}
		PosicionCT centroInf = new PosicionCT(Constants.centroArcoInf);
		int[] indices = centroInf.indicesMasCercanos(rivalesPos);
		for (int idx2 : indices) {
			if (posRivales.size() == restantes.size()) {
				break;
			}
			posRivales.add(rivalesPos[idx2]);
		}

		DistanciaMinima dm = new DistanciaMinima(restantes, posRivales);
		List<IJugadorCT> ordenados = dm.getOrdenados();

		for (int i = 0; i < ordenados.size(); i++) {
			IJugadorCT jug = ordenados.get(i);
			PosicionCT pos = posRivales.get(i);
			jug.setSiguiente(pos);
		}

		return true;
	}
}
