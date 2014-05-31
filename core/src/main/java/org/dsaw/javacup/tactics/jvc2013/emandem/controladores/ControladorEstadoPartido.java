package org.dsaw.javacup.tactics.jvc2013.emandem.controladores;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.emandem.cancha.AreasCancha;
import org.dsaw.javacup.tactics.jvc2013.emandem.cancha.CuadrantesCancha;
import org.dsaw.javacup.tactics.jvc2013.emandem.enums.CUADRANTE_CANCHA;
import org.dsaw.javacup.tactics.jvc2013.emandem.enums.JUGADOR;
import org.dsaw.javacup.tactics.jvc2013.emandem.utils.CirculoMarcacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorEstadoPartido {

	private static ControladorEstadoPartido instance;
	private static AreasCancha areasCancha = AreasCancha.getInstance();
	private static CuadrantesCancha cuadrantesCancha = CuadrantesCancha.getInstance();
	private static int BALON = 0;
	private static int NUMERO_TOTAL_JUGADORES = 11;
	
	private Map<String, List<Integer>> jugadoresPorCuadrantes = new HashMap<String, List<Integer>>(0);
	private Map<Integer, String> jugadoresConCuadrante = new HashMap<Integer, String>();
	private List<Integer> jugadoresPuedenRecuperar = new ArrayList<Integer>();
	private List<Integer> jugadoresPuedenRematar = new ArrayList<Integer>();
	private List<Integer> jugadoresRestantes = new ArrayList<Integer>();
	private Map<Integer,CirculoMarcacion> marcasDeJugadores = new HashMap<Integer, CirculoMarcacion>();
	private Map<Integer, List<Integer>> jugadoresMarcados = new HashMap<Integer, List<Integer>>(0);

	private int iteracionesParaRecuperarBalon;
	private Position posicionRecuperaBalon;
	private boolean porteroPuedeRecuperarBalon;
	private boolean porteroPuedeRematar;
	
	static {
		instance = new ControladorEstadoPartido();
	}
	
	public static ControladorEstadoPartido getInstance() {
		return instance;
	}

	private void limpiarInformacion() {
		this.jugadoresMarcados.clear();
		this.marcasDeJugadores.clear();
		this.jugadoresConCuadrante.clear();
		this.jugadoresPorCuadrantes.clear();
		this.jugadoresPuedenRecuperar.clear();
		this.jugadoresPuedenRematar.clear();
		this.jugadoresRestantes.clear();
		this.iteracionesParaRecuperarBalon = 0;
		this.posicionRecuperaBalon = null;
		this.porteroPuedeRecuperarBalon = Boolean.FALSE;
		this.porteroPuedeRematar = Boolean.FALSE;
	}
	
	public void analizarYCargarInformacion(final GameSituations situacionPartido) {
		this.limpiarInformacion();
		
		// Recuperar Balon
		for (int indice = 0; indice < situacionPartido.getRecoveryBall().length; indice++) {
			if (indice == BALON) {
				this.iteracionesParaRecuperarBalon = situacionPartido.getRecoveryBall()[indice];
				double[] posicionBalon = situacionPartido.getTrajectory(this.iteracionesParaRecuperarBalon);
				this.posicionRecuperaBalon = new Position(posicionBalon[0], posicionBalon[1]);
			} else if(situacionPartido.getRecoveryBall()[indice] == JUGADOR.PORTERO.numero()) {
				this.porteroPuedeRecuperarBalon = Boolean.TRUE;
			} else {
				this.jugadoresPuedenRecuperar.add(situacionPartido.getRecoveryBall()[indice]);
			}
		}
		
		// Rematar Balon
		for (int indice = 0; indice < situacionPartido.canKick().length; indice++) {
			if(situacionPartido.canKick()[indice] == JUGADOR.PORTERO.numero()) {
				this.porteroPuedeRematar = Boolean.TRUE;
			} else {
				this.jugadoresPuedenRematar.add(situacionPartido.canKick()[indice]);
			}
		}
		
		// Catalogar jugadores por cuadrante
		for (int numJugador = 0; numJugador < situacionPartido.myPlayers().length; numJugador++) {
			if (numJugador > JUGADOR.PORTERO.numero()) {
				CUADRANTE_CANCHA
                                    cuadrante = CuadrantesCancha.getInstance().getCuadrantePorPosicion(situacionPartido.myPlayers()[numJugador]);
				if (!this.jugadoresPorCuadrantes.containsKey(cuadrante.name())) {
					this.jugadoresPorCuadrantes.put(cuadrante.name(), new ArrayList<Integer>());
				}
				this.jugadoresPorCuadrantes.get(cuadrante.name()).add(numJugador);
			}
		}
		
		// Catalogar jugadores con cuadrante
		for (int numJugador = 0; numJugador < situacionPartido.myPlayers().length; numJugador++) {
			if (numJugador > JUGADOR.PORTERO.numero()) {
				CUADRANTE_CANCHA cuadrante = CuadrantesCancha.getInstance().getCuadrantePorPosicion(situacionPartido.myPlayers()[numJugador]);
				this.jugadoresConCuadrante.put(numJugador, cuadrante.name());
			}
		}
		
		// Obtiene los jugadores restantes
		for(int numJugador = 0; numJugador < NUMERO_TOTAL_JUGADORES ;numJugador++) {
			if (numJugador == JUGADOR.PORTERO.numero()) {
				continue;
			}
			if ( !this.jugadoresPuedenRecuperar.contains(Integer.valueOf(numJugador)) && 
					!this.jugadoresPuedenRematar.contains(Integer.valueOf(numJugador))) {
				this.jugadoresRestantes.add(numJugador);
			}
		}
		
		// Actualiza la zona de marca de los jugadores
		for (int numJugador = 0; numJugador < situacionPartido.myPlayers().length; numJugador++) {
			if (numJugador == JUGADOR.PORTERO.numero()) {
				continue;
			}
			if (!this.marcasDeJugadores.containsKey(numJugador)) {
				this.marcasDeJugadores.put(numJugador, new CirculoMarcacion(situacionPartido.myPlayers()[numJugador], ControladorUtils.DISTANCIA_MARCAJE));
			}
			this.marcasDeJugadores.get(numJugador).setJugadorPosicionX(situacionPartido.myPlayers()[numJugador].getX());
			this.marcasDeJugadores.get(numJugador).setJugadorPosicionY(situacionPartido.myPlayers()[numJugador].getY());
		}
		
		// Cataloga a los jugadores con marca
		for (int numJugador = 0; numJugador < situacionPartido.myPlayers().length; numJugador++) {
			if (numJugador > JUGADOR.PORTERO.numero()) {
				for(int numJugadorEnemigo = 0; numJugadorEnemigo < situacionPartido.rivalPlayers().length; numJugadorEnemigo++) {
					if (this.marcasDeJugadores.get(numJugador).estoyMarcado(situacionPartido.rivalPlayers()[numJugadorEnemigo])) {
						if (!this.jugadoresMarcados.containsKey(numJugador)) {
							this.jugadoresMarcados.put(numJugador, new ArrayList<Integer>());
						}
						this.jugadoresMarcados.get(numJugador).add(numJugadorEnemigo);
					}
				}
			}
		}
	}

	public Map<Integer, String> getJugadoresConCuadrante() {
		return jugadoresConCuadrante;
	}

	public boolean isEstaBalonEnElAreaGrandeSup() {
		return areasCancha.estaEnElAreaGrandeSup(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElAreaGrandeInf() {
		return areasCancha.estaEnElAreaGrandeInf(posicionRecuperaBalon);
	}
	
	public int getIteracionesParaRecuperarBalon() {
		return iteracionesParaRecuperarBalon;
	}

	public Position getPosicionRecuperaBalon() {
		return posicionRecuperaBalon;
	}

	public boolean isPorteroPuedeRecuperarBalon() {
		return porteroPuedeRecuperarBalon;
	}

	public boolean isPorteroPuedeRematar() {
		return porteroPuedeRematar;
	}	
	
	public Map<String, List<Integer>> getJugadoresPorCuadrantes() {
		return jugadoresPorCuadrantes;
	}

	public List<Integer> getJugadoresPuedenRecuperar() {
		return jugadoresPuedenRecuperar;
	}

	public List<Integer> getJugadoresPuedenRematar() {
		return jugadoresPuedenRematar;
	}

	public List<Integer> getJugadoresRestantes() {
		return jugadoresRestantes;
	}
	
	public Map<Integer, CirculoMarcacion> getMarcasDeJugadores() {
		return marcasDeJugadores;
	}

	public Map<Integer, List<Integer>> getJugadoresMarcados() {
		return jugadoresMarcados;
	}
	
	public boolean isEstaBalonEnElCuadrante1Sup() {
		return cuadrantesCancha.estaEnElCuadrante1Sup(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante2Sup() {
		return cuadrantesCancha.estaEnElCuadrante2Sup(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante3Sup() {
		return cuadrantesCancha.estaEnElCuadrante3Sup(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante4Sup() {
		return cuadrantesCancha.estaEnElCuadrante4Sup(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante1Inf() {
		return cuadrantesCancha.estaEnElCuadrante1Inf(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante2Inf() {
		return cuadrantesCancha.estaEnElCuadrante2Inf(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante3Inf() {
		return cuadrantesCancha.estaEnElCuadrante3Inf(posicionRecuperaBalon);
	}
	
	public boolean isEstaBalonEnElCuadrante4Inf() {
		return cuadrantesCancha.estaEnElCuadrante4Inf(posicionRecuperaBalon);
	}
}
