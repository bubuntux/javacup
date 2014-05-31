/**
 * 
 */
package org.dsaw.javacup.tactics.jvc2012.team2012;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase que contiene el historial de las marcas durante el partido
 * 
 * @author willBender
 * 
 */
public class HistorialMarcas {

	private Map<Integer, ArrayList<PosicionJugador>>	marcasRivales	= new HashMap<>();

	public HistorialMarcas() {

	}

	/**
	 * Adiciona las posiciones marca a las ya existentes
	 * 
	 * @param sp
	 */
	public void addMarcasRivalIteracionActual(GameSituations sp) {
		int[] rivales = sp.ballPosition().nearestIndexes(sp.rivalPlayers());
		for (int i = 0; i < rivales.length; i++) {
			int idRival = rivales[i];
			ArrayList<PosicionJugador> marcaRival = marcasRivales.get(idRival);
			if (marcaRival == null) {
				marcaRival = new ArrayList<>();
			}
			Position pRival = sp.rivalPlayers()[idRival];
			int[] puedenPatear = sp.canKick();
			Boolean puedePatear = false;
			for (int j = 0; j < puedenPatear.length; j++) {
				if (puedenPatear[j] == idRival) {
					puedePatear = true;
					break;
				}
			}
			PosicionJugador pm = new PosicionJugador(pRival, sp.iteration(), puedePatear);
			marcaRival.add(pm);
			marcasRivales.put(idRival, marcaRival);
		}
	}

	public Position predecirPosicionRival(GameSituations sp, Integer idRival, int pasos) {
		PlayerDetail detalleRival = sp.rivalPlayersDetail()[idRival];
		double velocidadRival = detalleRival.getSpeed();
		ArrayList<PosicionJugador> posicionesRival = getMarcasRivales().get(idRival);
		if (posicionesRival == null) {
			return null;
		}
		int nPasos = 0;
		Position pInicial = posicionesRival.get(posicionesRival.size() - 1).getPosicion();
		Position pFinal = posicionesRival.get(posicionesRival.size() - 1).getPosicion();
		for (int i = posicionesRival.size() - 1; i >= 0; i--) {
			pInicial = posicionesRival.get(i).getPosicion();
			nPasos++;
			if (nPasos == pasos + 1) {
				break;
			}
		}
		Position resultado;
		if ((pInicial.getX() == pFinal.getX() && pInicial.getY() == pFinal.getY()) || (pInicial.getY() < pFinal.getY())) {
			resultado = pInicial;
		} else {
			double angulo = pInicial.angle(pFinal);
			resultado = pFinal.moveAngle(angulo, velocidadRival);
		}
		return resultado;
	}

	/**
	 * @return the marcasRivales
	 */
	public Map<Integer, ArrayList<PosicionJugador>> getMarcasRivales() {
		return marcasRivales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Collection<Integer> idsRivales = getMarcasRivales().keySet();
		for (Iterator<Integer> iterator = idsRivales.iterator(); iterator.hasNext();) {
			Integer integer = iterator.next();
			sb.append("Jugador - ");
			sb.append(integer);
			sb.append("\n");
			ArrayList<PosicionJugador> posicionesMarca = getMarcasRivales().get(integer);
			for (Iterator<PosicionJugador> iterator2 = posicionesMarca.iterator(); iterator2.hasNext();) {
				PosicionJugador posicionMarca = iterator2.next();
				sb.append(posicionMarca);
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}