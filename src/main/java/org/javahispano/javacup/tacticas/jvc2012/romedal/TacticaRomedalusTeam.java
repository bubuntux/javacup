package org.javahispano.javacup.tacticas.jvc2012.romedal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

import org.javahispano.javacup.tacticas.jvc2012.romedal.util.Balon;
import org.javahispano.javacup.tacticas.jvc2012.romedal.util.ComandoGolpear;
import org.javahispano.javacup.tacticas.jvc2012.romedal.util.DestinoBalon;
import org.javahispano.javacup.tacticas.jvc2012.romedal.util.InfoTrayectoria;
import org.javahispano.javacup.tacticas.jvc2012.romedal.util.OpcionesDisparo;

/**
 * @author Roland Cruz
 */
/**
 * @author Roland
 */
public class TacticaRomedalusTeam implements Tactic, Alineaciones {
	public static final double			TO_ANG				= 180 / Math.PI;
	public static final double			TO_RAD				= Math.PI / 180;
	public static final double			yMeta				= Constants.LARGO_CAMPO_JUEGO / 2;

	private static Position				metaAbajoIzquierda	= Constants.esqSupIzqAreaChicaSup.movePosition(0, Constants.ANCHO_AREA_CHICA);
	private static Position				metaAbajoDerecha	= Constants.esqSupIzqAreaChicaSup.movePosition(Constants.LARGO_AREA_CHICA, Constants.ANCHO_AREA_CHICA);
	private static Position				metaArribaIzquierda	= Constants.esqSupIzqAreaChicaInf;
	private static Position				metaArribaDerecha	= Constants.esqSupIzqAreaChicaInf.movePosition(Constants.LARGO_AREA_CHICA, 0);

	// comandos a ejecutar por iteracion
	private final ArrayList<Command>	comandos			= new ArrayList<Command>();
	// detalle de mis jugadores
	private final DetalleTactica		detalle				= new DetalleTactica(this.getClass().getSimpleName().substring(7));
	// indica el indice del portero rival
	private int							idxPortRival		= -1;
	// indica el indice de mi portero
	private final int					idxMiPortero		= 0;
	// iteraciones para sacar
	private int							iterSaco			= 0;
	// situacion del partido actual
	private GameSituations				sp;
	// indica si el balon est� en mi posesion
	boolean								laTengo				= false;
	// identificador del ultimo pasador de mi equipo
	private int							ultimoPasador		= -1;
	// identificador del jugador al cual va dirigido el pase private
	private int							destinoPase			= -1;
	// posicion de destino para pase en vacio
	private Balon						balonDestinoPase	= null;
	// lista de jugadores que se encuentran realizando marcacion de rivales
	final List<Integer>					marcando			= new ArrayList<Integer>();
	// lista de jugadores que intentan recuperar el balon
	final List<Integer>					recuperando			= new ArrayList<Integer>();
	// posicion del balon en la iteracion anterior
	Balon								balonAnt			= null;
	// informaci�n de la trayectoria del balon en las iteraciones siguientes
	private final LinkedList<Balon>		trayectoria			= new LinkedList<Balon>();
	// lista con las diferentes opciones de disparo
	final TreeSet<OpcionesDisparo>		opcionesDisparo		= new TreeSet<OpcionesDisparo>();
	// posision de los rivales en la iteracion anterior
	private Position[]					rivales				= null;
	double								puntosMios			= 0;
	double								posesionMia			= 0;
	double								puntosTotal			= 0;

	@Override
	public TacticDetail getDetail() {
		return detalle;
	}

	@Override
	public List<Command> execute(final GameSituations sp) {
		inicializar(sp);
		laTengo = true;
		// se obtiene la informacion del jugador mejor ubicado
		DestinoBalon destino = obtenerMejorUbicado(trayectoria, true, false, false);
		if (destino == null || destino.isGanaRival()) {
			destino = obtenerMejorUbicado(trayectoria, true, true, false);
		}
		if (destino == null || destino.isGanaRival() || (trayectoria.size() <= 1 && !sp.isStarts()) || tieneElbalon()) {
			laTengo = false;
		}
		// si tengo el balon busco pasarlo, controlarlo o rematar al arco
		if (tengoElBalon()) {
			destinoPase = -1;
			balonDestinoPase = null;
			golpearBalon();
			recuperando.add(ultimoPasador);
			marcando.add(ultimoPasador);
			recuperando.add(destinoPase);
			marcando.add(destinoPase);
		} else {
			// se determina el ultimo en hacer pase
			for (int i = 0; i < 11; i++) {
				if (sp.iterationsToKick()[i] > 0) {
					ultimoPasador = i;
					break;
				}
			}
			// si el rival tiene el balon, se reinician las variables adecuadas
			if (tieneElbalon()) {
				balonDestinoPase = null;
				ultimoPasador = -1;
				destinoPase = -1;
			}
			recuperarBalon(destino);
		}
		marcarRivales();
		// M�todo encargado de definir la funcionalidad de mi portero, si ya no ha sido definida
		ubicarPortero();

		Position pos = null;
		for (final Command c : comandos) {
			if (c instanceof CommandMoveTo && c.getPlayerIndex() == idxMiPortero) {
				pos = ((CommandMoveTo) c).getMoveTo();
				break;
			}
		}
		if (pos != null) {
			final ArrayList<CommandMoveTo> listaBorrar = new ArrayList<CommandMoveTo>();
			for (final Command c : comandos) {
				if (c instanceof CommandMoveTo) {
					final CommandMoveTo cmd = (CommandMoveTo) c;
					if (cmd.getPlayerIndex() != idxMiPortero && cmd.getMoveTo().equals(pos)) {
						listaBorrar.add(cmd);
					}
				}
			}
			comandos.removeAll(listaBorrar);
		}

		// se ubica la alineacion adecuada
		setAlineacion();

		if (sp.iteration() > 0) {
			rivales = sp.rivalPlayers();
		}
		balonAnt = trayectoria.get(0);

		return comandos;
	}

	/** Logica inicial de cada iteracion */
	private void inicializar(final GameSituations sp) {
		this.sp = sp;
		if (sp.iteration() == 0) {
			// se obtiene el id del portero rival
			setPorteroRival();

			// se determinan las opciones de disparo por distancia y altura
			setOpcionesDeTiro();
		}

		// se limpia la lista de comandos en cada iteracion
		comandos.clear();

		// se obtiene la informacion de la trayectoria del balon en las iteraciones siguientes hasta salir del campo o detenerse
		getInfoTrayectoriaReal();

		if (!sp.isStarts() && !sp.isRivalStarts()) {
			final double py = sp.ballPosition().getY();
			if (py > 0) {
				if (py > Constants.LARGO_CAMPO_JUEGO / 4) {
					puntosMios += 2;
					puntosTotal += 2;
				} else {
					puntosMios += 1;
					puntosTotal += 1;
				}
			} else if (py < 0) {
				if (py < -Constants.LARGO_CAMPO_JUEGO / 4) {
					puntosTotal += 2;
				} else {
					puntosTotal += 1;
				}
			}
			posesionMia = round(puntosMios * 100 / puntosTotal, 2);
		}

	}

	/** Se determinan todas las opciones de tiro */
	private void setOpcionesDeTiro() {
		// datos iniciales del balon
		final Balon balon = new Balon(new Position(0, -yMeta), 0);
		// angulo horizontal de disparo
		opcionesDisparo.clear();
		final double maxAng = Constants.ANGULO_VERTICAL_MAX;
		for (int idJugador = 0; idJugador < 11; idJugador++) {
			final double fuerzaJugador = sp.getMyPlayerPower(idJugador);
			final double velocidadRemate = Constants.getVelocidadRemate(sp.getMyPlayerPower(idJugador));
			final double velocidadJugador = Constants.getVelocidad(sp.getMyPlayerSpeed(idJugador));
			// 0 -> tiro sacando, 1-> tiro normal
			for (int tipoDisparo = 0; tipoDisparo < 2; tipoDisparo++) {
				// si la opcion no ha sido calculada
				final OpcionesDisparo opcion = new OpcionesDisparo(fuerzaJugador, tipoDisparo);
				if (!opcionesDisparo.contains(opcion)) {
					// se prueban diferentes opciones de fuerza
					for (double fuerza = 1; fuerza >= 0.2; fuerza -= 0.01d) {
						// potecia obtenida dependiendo de la fuerza aplicada
						final double potencia = fuerza * velocidadRemate * (tipoDisparo == 0 ? 0.75 : 1);
						// se prueban diferentes opciones de angulos
						for (double angZ = maxAng; round(angZ, 2) > 10; angZ -= 0.05) {
							angZ = round(angZ, 2);
							// angulo en radianes
							final double angRadZ = angZ * TO_RAD;
							// se obtiene la trayectoria del balon al punto
							final InfoTrayectoria info = obtenerDistanciaCalculada(balon, fuerza, potencia, angRadZ, velocidadJugador);
							if (info != null) {
								TreeSet<InfoTrayectoria> set = new TreeSet<InfoTrayectoria>();
								final TreeMap<Double, TreeSet<InfoTrayectoria>> map = opcion.getOpciones();
								if (map.containsKey(info.getDistancia())) {
									set = map.get(info.getDistancia());
								}
								set.add(info);
								map.put(info.getDistancia(), set);
							}
						}
						// calculos con angulo 0
						final InfoTrayectoria info = obtenerDistanciaCalculada(balon, fuerza, potencia, 0, velocidadJugador);
						opcion.getOpcionesAngCero().add(info);
					}
					opcionesDisparo.add(opcion);
				}
			}
		}

	}

	/**
	 * Calcula la ruta dependiendo de los parametros de potencia y angulo vertical
	 * 
	 * @param velocidadJugador
	 */
	private InfoTrayectoria obtenerDistanciaCalculada(final Balon balon, final double fuerza, final double potencia, final double anguloZ, final double velocidadJugador) {
		// instancia para manejar la trayectoria
		final AbstractTrajectory trajectory = new AirTrajectory(Math.cos(anguloZ) * potencia, Math.sin(anguloZ) * potencia, 0, 0);
		balon.setIteracion(0);

		// indica si se trata de una trayectoria por el piso
		final boolean paseRastrero = anguloZ == 0;

		// altura a la cual se podr� controlar el balon
		final double altControl = paseRastrero ? 0 : Constants.ALTURA_CONTROL_BALON;

		// minimo numero de iteraciones a calcular
		final int iteracionesControl = Constants.ITERACIONES_GOLPEAR_BALON;

		// indica si el balon se est� elevando
		boolean elevando = !paseRastrero;
		double oldtrY = 0d;
		boolean dzAntPosivivo = true;
		double dxAnt = 0d;
		final double x = balon.getPosition().getX();// cos(90)=0 por eso se omite
		boolean altMax = false;
		// iteraciones simuladas
		final TreeSet<Double> distancias = new TreeSet<Double>();
		distancias.add(0d);
		for (int step = 1; step <= 200; step++) {

			final double time = step / 60d;
			final double dX = trajectory.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
			final double dY = trajectory.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
			final double y = balon.getPosition().getY() + dX;

			// nueva informacion del balon
			final Balon nuevoBalon = new Balon(x, y, dY, step);
			elevando = oldtrY < dY;

			// si el balon no se est� elevando se asume que lleg� a su altura maxima
			if (!paseRastrero && !elevando) {
				dzAntPosivivo = false;
				if (!altMax && !paseRastrero && nuevoBalon.getAltura() < altControl) {
					return null;
				}
				altMax = true;
				// si es un tiro al arco, la idea es que el arquero no la alcance
				if (step >= iteracionesControl && (dY >= altControl - 1 && dY <= altControl)) {
					return new InfoTrayectoria(fuerza, potencia, anguloZ, round(dX, 1), step, null);
				}
			}
			// si el angulo es cero y el delta de desplazamiento es menor a la distancia de control
			else if (anguloZ == 0 && step >= iteracionesControl && dX == dxAnt) {
				return new InfoTrayectoria(fuerza, potencia, anguloZ, round(dX, 1), step, distancias);
			}
			// si el balon se detuvo o sale del campo, o es un rebote o esta cayendo y no alcanzo la altura, se retorna 0
			if (dxAnt == dX || !nuevoBalon.getPosition().isInsideGameField(0) || ((elevando && !dzAntPosivivo) || (!elevando && dY < altControl))) {
				return null;
			}
			oldtrY = dY;
			dxAnt = dX;
			distancias.add(round(dX, 1));
		}
		return null;
	}

	/** Determina la trayectoria teorica del balon cuando ejecuto un disparo con los parametros datos */
	private int obtenerTrayectoriaCalculada(final LinkedList<Balon> trayectoriaBalon, final Balon balon, final double potencia, final double angleXY, final double angleZ, final Position pFinal, final boolean completa, final int idxReceptor,
			final int maxPasos) {
		trayectoriaBalon.clear();
		balon.setIteracion(0);
		trayectoriaBalon.add(balon);
		final AbstractTrajectory trajectory = new AirTrajectory(Math.cos(angleZ) * potencia, Math.sin(angleZ) * potencia, 0, 0);

		final double[] datosControl = getDatosControl(idxReceptor, pFinal, false);

		double distanciaAPunto = 1000d;
		int iteracionesAlcance = -1;
		boolean elevando = true;
		double oldtrY = 0d;
		boolean dzAntPosivivo = true;
		double dxAnt = 0d;
		Balon b0 = null;
		// si el balon se encuentra en el centro del campo, se dispara a meta esperando a que sea gol
		final boolean centro = sp.ballPosition().distance(Constants.centroCampoJuego) == 0;
		for (int step = 1; step <= maxPasos; step++) {

			final double time = step / 60d;
			final double dX = trajectory.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
			final double dY = trajectory.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
			final double x = balon.getPosition().getX() + dX * Math.cos(angleXY);
			final double y = balon.getPosition().getY() + dX * Math.sin(angleXY);
			final Balon nuevoBalon = new Balon(x, y, dY, step);
			elevando = oldtrY < dY;
			// distancia del balon al punto final deseado
			distanciaAPunto = nuevoBalon.getPosition().setInsideGameField().distance(pFinal);
			// si el balon no se est� elevando se asume que lleg� a su altura maxima
			if (!elevando) {
				dzAntPosivivo = false;
				if (iteracionesAlcance == -1) {
					// si es un tiro al arco, la idea es que el arquero no la alcance
					if (idxReceptor == -1 && !trayectoriaBalon.isEmpty()) {
						if (angleZ > 0) {
							final Balon balonAnterior = trayectoriaBalon.getLast();
							// se verifica que el balon anterior ente en el campo y el actual no
							final boolean antAdentro = balonAnterior.getAltura() > Constants.ALTO_ARCO && balonAnterior.getPosition().getY() <= yMeta;
							final boolean actAfuera = nuevoBalon.getAltura() < Constants.ALTO_ARCO && nuevoBalon.getPosition().getY() > yMeta;
							if (antAdentro && actAfuera) {
								final double deltaX = balonAnterior.getPosition().distance(nuevoBalon.getPosition());
								// se determina la altura en la linea de meta
								final double deltaY = balonAnterior.getAltura() - nuevoBalon.getAltura();
								final double h = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
								// arc cos[(b� + c� - a�)/2bc]
								final double teta = Math.acos((deltaX * deltaX + h * h - deltaY * deltaY) / (2 * deltaX * h));
								// tan (teta)=opuesto/adyacente
								final double nuevoX = deltaX - (yMeta - balonAnterior.getPosition().getY());
								final double alturaMeta = nuevoBalon.getAltura() + (Math.tan(teta) * nuevoX);
								// si la altura en linea de meta es aceptable
								if (alturaMeta <= Constants.ALTO_ARCO && (nuevoBalon.getAltura() - (balonAnterior.getAltura() - b0.getAltura()) <= Constants.ALTO_ARCO || centro)) {
									trayectoriaBalon.add(nuevoBalon);
									return step;
								}
							}
						} else if (nuevoBalon.getPosition().getY() >= Constants.LARGO_CAMPO_JUEGO / 2 && Math.abs(nuevoBalon.getPosition().getX()) <= Constants.LARGO_ARCO) {
							trayectoriaBalon.add(nuevoBalon);
							return step;
						}
					}
					// en caso de un pase a un jugador
					else if (iteracionesAlcance == -1 && distanciaAPunto <= datosControl[0] && (angleZ == 0 || (dY <= datosControl[1]))) {
						iteracionesAlcance = Math.max(step, Constants.ITERACIONES_GOLPEAR_BALON);
						// si no se quiere la ruta completa y se alcanz� el balon en algun punto, se retorna
						if (!completa) {
							trayectoriaBalon.add(nuevoBalon);
							return iteracionesAlcance;
						}
					}
				}
			}
			// si el balon se est� elevando y ya toc� el piso, se trata de un rebote
			if (!completa && iteracionesAlcance == -1 && (angleZ > 0 && ((elevando && !dzAntPosivivo) || (!elevando && dY < datosControl[1])))) {
				return iteracionesAlcance;
			}
			// si el balon se detuvo o sale del campo, se retorna
			if (dxAnt == dX || !nuevoBalon.getPosition().isInsideGameField(0)) {
				return iteracionesAlcance;
			}
			oldtrY = dY;
			dxAnt = dX;
			if (trayectoriaBalon.size() > 0) {
				b0 = trayectoriaBalon.getLast();
			}
			trayectoriaBalon.add(nuevoBalon);

		}
		return iteracionesAlcance;
	}

	/** Metodo encargado de ubicar al portero segun cada iteracion */
	private void ubicarPortero() {
		// Si el arquero no est� en posicion de saque, se ubica lo mejor posible
		if (!sp.isStarts()) {
			// // se borra cualquier comando de movimiento asignado al portero
			for (final Command c : comandos) {
				if (c.getPlayerIndex() == idxMiPortero && c instanceof CommandMoveTo && ultimoPasador != destinoPase) {
					comandos.remove(c);
					break;
				}
			}
			// si el portero puede controlar el balon, se busca el despeje
			// // verifico si el portero es la mejor opcion para interceptar el pase
			final DestinoBalon destino = obtenerMejorUbicado(trayectoria, true, true, false);

			if (destino != null /* && posFinalEnAreaGrande(destino.getPosBalon(), false) */&& !destino.isGanaRival()) {
				comandos.add(new CommandMoveTo(idxMiPortero, destino.getPosBalon()));
				return;
			}
			comandos.add(new CommandMoveTo(idxMiPortero, mejorPosArquero()));
		} else {
			// si tengo que sacar de meta
			if (sp.ballPosition().equals(metaAbajoDerecha) || sp.ballPosition().equals(metaAbajoIzquierda)) {
				comandos.add(new CommandMoveTo(idxMiPortero, sp.ballPosition()));
			} else {
				comandos.add(new CommandMoveTo(idxMiPortero, mejorPosArquero()));
			}
		}
	}

	private Position mejorPosArquero() {
		// distancia entre el poste der y el balon
		final double distDer = Constants.posteDerArcoInf.distance(sp.ballPosition()) / 3;

		// distancia entre el poste izq y el balon
		final double distIzq = Constants.posteIzqArcoInf.distance(sp.ballPosition()) / 3;

		// se determina cual es el poste mas cercano al balon
		final double minDist = Math.min(distDer, distIzq);

		// distancia del rival mas cercano al balon
		final double distRival = sp.ballPosition().distance(sp.rivalPlayers()[sp.ballPosition().nearestIndex(sp.rivalPlayers())]);

		// si el rival esta en control del balon y el balon est� cerca al arco
		if (minDist <= Constants.ANCHO_AREA_GRANDE && distRival <= Constants.DISTANCIA_CONTROL_BALON) {
			// se ubica al portero para cubrirlo lo mejor posible
			final double anguloPoste = Constants.posteIzqArcoInf.angle(sp.ballPosition());
			final Position posRet = Position.Intersection(sp.ballPosition(), Constants.centroArcoInf, Constants.posteDerArcoInf, Constants.posteIzqArcoInf.moveAngle(anguloPoste, distIzq));
			if (posRet != null) {
				return posRet;
			}
		}
		// // se ubica el portero en angulo
		final double angulo = Constants.centroArcoInf.angle(sp.ballPosition());
		return Constants.centroArcoInf.moveAngle(angulo, (Constants.LARGO_ARCO - Constants.DISTANCIA_CONTROL_BALON_PORTERO) / 2);
	}

	/** Determino si saco o estoy en capacidad de patear el balon */
	private boolean tengoElBalon() {
		return sp.isStarts() || sp.canKick().length > 0;
	}

	/** Determina si el rival saca o puede patear */
	private boolean tieneElbalon() {
		return sp.isRivalStarts() || sp.rivalCanKick().length > 0;
	}

	/** Se obtiene el indicador del arquero (no necesariamente siempre es el 1) */
	private void setPorteroRival() {
		if (idxPortRival < 0) {
			final PlayerDetail[] rivales = sp.rivalPlayersDetail();
			for (int i = 0; i < rivales.length; i++) {
				if (rivales[i].isGoalKeeper()) {
					idxPortRival = i;
					return;
				}
			}
		}
	}

	/** Se intenta quitarle el balon al rival */
	private void recuperarBalon(final DestinoBalon destino) {
		recuperando.clear();
		boolean autopase = false;
		boolean paseAlVacio = false;
		// si en la iteracion anterior se hizo un autopase
		if (ultimoPasador == destinoPase && destinoPase >= 0 && (laTengo || ultimoPasador != idxMiPortero)) {
			autopase = true;
			recuperando.add(ultimoPasador);
			comandos.add(new CommandMoveTo(destinoPase, trayectoria.getLast().getPosition()));
		}
		// si se hizo un pase y el jugador lo alcanza
		else if (balonDestinoPase != null && (laTengo || destinoPase != idxMiPortero) && moverAlHueco(trayectoria)) {
			paseAlVacio = true;
			recuperando.add(destinoPase);
			// se mueve el jugador que realiz� el pase hacia adelante en busca de un nuevo pase
			if (ultimoPasador >= 0 && ultimoPasador != idxMiPortero && ultimoPasador != destinoPase) {
				comandos.add(new CommandMoveTo(ultimoPasador, sp.myPlayers()[ultimoPasador].moveAngle(90 * TO_RAD, sp.getMyPlayerSpeed(ultimoPasador)).setInsideGameField()));
				recuperando.add(ultimoPasador);
			}
		}
		if (laTengo && ultimoPasador >= 0 && ultimoPasador != destinoPase) {
			recuperando.add(ultimoPasador);
			comandos.add(new CommandMoveTo(ultimoPasador, sp.myPlayers()[ultimoPasador].movePosition(0, 0.5)));
		}
		if (destino != null && !destino.isGanaRival() && !autopase && !paseAlVacio) {
			comandos.add(new CommandMoveTo(destino.getIdxJugador(), destino.getPosBalon().setInsideGameField()));
			recuperando.add(destino.getIdxJugador());
		}
		// si no la tengo
		if (!laTengo) {
			if (trayectoria.getLast().getPosition().getY() >= 52.5 || sp.ballPosition().equals(metaArribaDerecha) || sp.ballPosition().equals(metaArribaIzquierda)) {
				final int idx = Constants.centroArcoSup.nearestIndex(sp.myPlayers());
				recuperando.add(idx);
				if (trayectoria.getLast().getPosition().getX() < 0) {
					comandos.add(new CommandMoveTo(idx, metaArribaIzquierda));
				} else {
					comandos.add(new CommandMoveTo(idx, metaArribaDerecha));
				}
			}
			final DestinoBalon nuevoDestino = obtenerMejorUbicado(trayectoria, true, false, true);
			if (nuevoDestino != null) {
				comandos.add(new CommandMoveTo(nuevoDestino.getIdxJugador(), nuevoDestino.getPosBalon()));
				recuperando.add(nuevoDestino.getIdxJugador());
			}
			final Position pDest = destino != null && destino.isGanaRival() ? destino.getPosBalon() : trayectoria.getLast().getPosition();
			for (final int idxCerca : pDest.nearestIndexes(sp.myPlayers(), idxMiPortero)) {
				comandos.add(new CommandMoveTo(idxCerca, pDest));
				recuperando.add(idxCerca);
				break;
			}
		}
		marcando.clear();
	}

	private void marcarRivales() {
		final DestinoBalon destinoRival = laTengo ? null : obtenerRivalMejorUbicado(trayectoria, true);
		final double delta = Constants.DISTANCIA_CONTROL_BALON;
		// se marcan los rivales cercanos a mi arco
		final int minMarcas = laTengo ? 2 : 6;
		int marcas = 0;
		final int[] rivales = Constants.centroArcoInf.nearestIndexes(sp.rivalPlayers());
		for (final int i : rivales) {
			boolean marcar = false;
			if (destinoRival != null && i == destinoRival.getIdxJugador()) {
				marcar = true;
			}
			final Position rival = getPosRival(i);
			if (!enAreaChica(rival, false) && (marcar || marcas < minMarcas || (!laTengo && rival.getY() < -30))) {
				final int[] cercanosAtacante = rival.nearestIndexes(sp.myPlayers());
				for (final int c : cercanosAtacante) {
					if (c != idxMiPortero && !marcando.contains(c) && !recuperando.contains(c)) {
						double ang = rival.angle(Constants.centroArcoInf);
						if (!marcar && sp.ballPosition().getY() < sp.myPlayers()[c].getY()) {
							ang = rival.angle(sp.ballPosition());
						}
						final Position p = rival.moveAngle(ang, delta);
						comandos.add(new CommandMoveTo(c, p));
						marcando.add(c);
						marcas++;
						break;
					}
				}
			}
		}
	}

	private boolean enAreaChica(final Position pos, final boolean rival) {
		final double minY = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_CHICA);
		final boolean yEnArea = rival ? pos.getY() >= minY : pos.getY() < -minY;
		if (Math.abs(pos.getX()) <= Constants.LARGO_AREA_CHICA / 2 && yEnArea) {
			return true;
		}
		return false;
	}

	private Position getPosRival(final int i) {
		final Position rivalAct = sp.rivalPlayers()[i];
		if (sp.ballPosition().getY() < 0 && sp.iteration() > 1) {
			// se asume que el jugador se seguira moviendo en la misma direccion
			final double ang = rivales[i].angle(rivalAct);
			return rivalAct.moveAngle(ang, 0.5);
		}
		return rivalAct;
	}

	private boolean moverAlHueco(final LinkedList<Balon> trayectoriaBalon) {
		boolean llegaAlPase = true;
		if (!trayectoriaBalon.contains(balonDestinoPase)) {
			boolean alcanzaBalon = false;
			Balon opcion = null;
			for (final Balon balon : trayectoriaBalon) {
				if (balon.getPosition().isInsideGameField(0)) {
					final double distancia = balonDestinoPase.getPosition().distance(balon.getPosition());
					if (distancia <= Constants.DISTANCIA_CONTROL_BALON && balon.getAltura() <= Constants.ALTURA_CONTROL_BALON && balon.getIteracion() >= sp.iterationsToKick()[destinoPase]) {
						alcanzaBalon = true;
						opcion = balon;
					} else if (alcanzaBalon) {// si en iteraciones pasadas alcanza el balon y en esta no
						break;
					}
				}
			}
			if (opcion != null) {
				balonDestinoPase = opcion;
			} else {
				llegaAlPase = false;
			}
		}
		if (llegaAlPase) {
			comandos.add(new CommandMoveTo(destinoPase, balonDestinoPase.getPosition()));
			return true;
		}

		return false;
	}

	/** Se busca el balon para golpearlo si est[a en un punto de saque */
	private boolean puedoGolpear() {
		if (sp.canKick().length > 0 && sp.ballPosition().distance(Constants.centroCampoJuego) == 0) {
			return true;
		}
		if (sp.canKick().length == 0 || sp.isStarts()) {
			iterSaco++;
			if (iterSaco <= Constants.ITERACIONES_SAQUE * 2 / 5) {
				boolean hayOpcion = false;
				int i = -1;
				for (int idx = 0; idx < 11; idx++) {
					if (sp.getMyPlayerError(idx) >= 0.9 && sp.myPlayers()[idx].distance(sp.ballPosition()) <= (((Constants.ITERACIONES_SAQUE / 2) - iterSaco) * 0.5)) {
						hayOpcion = true;
						i = idx;
						break;
					}
				}
				if (!hayOpcion) {
					i = sp.ballPosition().nearestIndex(sp.myPlayers(), idxMiPortero);
				}
				comandos.add(new CommandMoveTo(i, sp.ballPosition()));

				if (sp.ballPosition().distance(Constants.cornerSupDer) == 0 || sp.ballPosition().distance(Constants.cornerSupIzq) == 0) {
					final int j = Constants.penalSup.nearestIndex(sp.myPlayers(), i);
					comandos.add(new CommandMoveTo(j, Constants.penalSup));
				}

			} else {
				iterSaco = 0;
				if (sp.canKick().length > 0) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	private boolean hacerPaseAlVacio(final boolean adelante, final boolean autopase, final int minIterDif, final boolean mejorSkills) {
		final int[] receptores = Constants.centroArcoSup.nearestIndexes(sp.myPlayers(), idxMiPortero, ultimoPasador);
		ComandoGolpear mejorDestino = null;
		int mejorReceptor = 0;
		if (autopase) {
			final ComandoGolpear dest = getOpcionPase(ultimoPasador, ultimoPasador, adelante, minIterDif);
			if (dest != null) {
				mejorDestino = dest;
				mejorReceptor = ultimoPasador;
			}
		} else {
			final double skillPas = sp.getMyPlayerPower(ultimoPasador) + sp.getMyPlayerError(ultimoPasador);
			double mejorDif = 0d;
			for (final int idxReceptor : receptores) {
				if (idxReceptor != ultimoPasador) {
					final double skilRec = sp.getMyPlayerPower(idxReceptor) + sp.getMyPlayerError(idxReceptor);
					final boolean esMejor = skilRec >= skillPas;
					if ((mejorSkills && esMejor) || !mejorSkills) {
						// se verifica si se pudo hacer un pase hacia adelante preciso al jugador
						final ComandoGolpear dest = getOpcionPase(ultimoPasador, idxReceptor, adelante, minIterDif);
						if (dest != null) {
							final int dif = dest.getIterRival() - dest.getIterJugador();
							if (mejorDestino == null || (dif > mejorDif)) {
								mejorDestino = dest;
								mejorReceptor = idxReceptor;
								mejorDif = mejorDestino.getIterRival() - mejorDestino.getIterJugador();
							}
						}
					}
				}
			}
		}
		// si existe destino de pase se usa el mas cercano al arco rival
		if (mejorDestino != null) {
			final CommandHitBall comando = mejorDestino.getComando();
			// se realiza el pase si es relativamente seguro o es hacia adelante
			destinoPase = mejorReceptor;
			balonDestinoPase = new Balon(comando.getDestiny(), comando.getVerticalAngle() > 0 ? Constants.ALTURA_CONTROL_BALON : 0);
			comandos.add(comando);
			// se mueve al receptor
			comandos.add(new CommandMoveTo(destinoPase, comando.getDestiny()));
			return true;
		}
		return false;
	}

	private boolean hacerDisparo(final boolean obstaculo, final double minAngZ, final double maxAngZ) {
		final ComandoGolpear command = mejorDisparoAlArco(ultimoPasador, obstaculo, minAngZ, maxAngZ);
		if (command != null) {
			comandos.add(command.getComando());
			return true;
		}
		return false;
	}

	private boolean defender() {
		final boolean esPortero = ultimoPasador == idxMiPortero;

		// se intenta pasar el balon hacia adelante a un mejor jugador
		final double skillPas = sp.getMyPlayerPower(ultimoPasador) + sp.getMyPlayerError(ultimoPasador);
		if (skillPas >= 1.9 || !hacerPaseAlVacio(true, false, 1, true)) {
			if (esPortero || !hacerPaseAlVacio(true, true, 1, false)) {
				if (!hacerPaseAlVacio(true, false, 1, false)) {
					// se intenta pasar el balon a cualquier jugador
					if (esPortero) {
						despejarBalon();
					} else {
						// se intenta retroceder con el balon
						if (sp.ballPosition().getY() < -35 || !hacerPaseAlVacio(false, true, 1, false)) {
							if (sp.ballPosition().getY() < -35 || !hacerPaseAlVacio(false, false, 1, false)) {
								despejarBalon();
							}
						}
					}
				}
			}
		}
		return true;
	}

	private void controlar() {
		final boolean puedeRematar = sp.getMyPlayerPower(ultimoPasador) >= 0.8;
		// se intenta pasar el balon hacia adelante
		if (!(puedeRematar && hacerDisparo(true, 10, Constants.ANGULO_VERTICAL_MAX))) {
			if (!hacerPaseAlVacio(true, true, 2, false)) {
				final double skillPas = sp.getMyPlayerPower(ultimoPasador) + sp.getMyPlayerError(ultimoPasador);
				if (skillPas >= 1.9 || !hacerPaseAlVacio(true, false, 1, true)) {
					if (!hacerPaseAlVacio(true, false, 1, false)) {
						// se intenta avanzar con el balon hacia atras
						if (!hacerPaseAlVacio(false, true, 1, false)) {
							if (!hacerPaseAlVacio(false, false, 1, true)) {
								if (!hacerPaseAlVacio(false, false, 1, false)) {
									if (!(puedeRematar && hacerDisparo(false, 10, Constants.ANGULO_VERTICAL_MAX))) {
										if (!quedarseQuieto()) {
											despejarBalon();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void atacar() {
		final boolean puedeRematar = sp.getMyPlayerPower(ultimoPasador) >= 0.8 || sp.myGoals() < sp.rivalGoals();
		// se intenta pasar el balon hacia adelante
		if (!(puedeRematar && hacerDisparo(true, 8, Constants.ANGULO_VERTICAL_MAX))) {
			// se intenta pasar el balon hacia adelante
			final double skillPas = sp.getMyPlayerPower(ultimoPasador) + sp.getMyPlayerError(ultimoPasador);
			if (!hacerPaseAlVacio(true, true, 1, false)) {
				if (skillPas >= 1.9 || !hacerPaseAlVacio(true, false, 1, true)) {
					if (!hacerPaseAlVacio(true, false, 1, false)) {
						// se intenta avanzar con el balon hacia atras
						if (!(puedeRematar && hacerDisparo(false, 10, Constants.ANGULO_VERTICAL_MAX))) {
							if (!hacerPaseAlVacio(false, true, 1, false)) {
								if (!hacerPaseAlVacio(false, false, 1, true)) {
									if (!hacerPaseAlVacio(false, false, 1, false)) {
										if (!quedarseQuieto()) {
											despejarBalon();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void definir() {
		final boolean noSacoRival = sp.isStarts() && (sp.ballPosition().equals(metaArribaDerecha) || sp.ballPosition().equals(metaArribaIzquierda));
		final boolean paseAtras = Math.abs(sp.ballPosition().getX()) > Constants.LARGO_AREA_GRANDE / 2 && sp.ballPosition().getY() >= Constants.penalSup.getY();

		final boolean enAreaChica = sp.ballPosition().getY() > 42 && Math.abs(sp.ballPosition().getX()) < (Constants.LARGO_AREA_CHICA / 2);
		// si el rival no efectuo el saque, disparamos directo a meta con fuerza

		if (noSacoRival) {
			comandos.add(new CommandHitBall(ultimoPasador, Constants.centroArcoSup, 1, false));
			return;
		} else if (enAreaChica) {
			if (!hacerDisparo(true, 0, 0)) {
				if (hacerDisparo(false, 0, 0)) {
					return;
				}
			}
		}
		// si esta +- de frente al arco y cerca y no pudo disparar
		if (!hacerDisparo(true, 0, 0)) {
			if (!hacerDisparo(true, 10, Constants.ANGULO_VERTICAL_MAX)) {
				if (!paseAtras || !hacerPaseAlVacio(false, false, 1, true)) {
					if (!paseAtras || !hacerPaseAlVacio(false, true, 1, false)) {
						if (!paseAtras || !hacerPaseAlVacio(false, false, 1, false)) {
							if (!hacerPaseAlVacio(true, false, 1, true)) {
								if (!hacerPaseAlVacio(true, false, 1, false)) {
									if (!hacerPaseAlVacio(true, true, 1, false)) {
										if (!(hacerDisparo(false, 0, 0))) {
											if (!hacerDisparo(false, 10, Constants.ANGULO_VERTICAL_MAX)) {
												if (!hacerPaseAlVacio(false, false, 0, true)) {
													if (!hacerPaseAlVacio(false, true, 2, false)) {
														if (!hacerPaseAlVacio(false, false, 0, false)) {
															if (!quedarseQuieto()) {
																despejarBalon();
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean quedarseQuieto() {
		// si puedo controlar el balon en la siguiente iteracion
		final boolean tendreControl = trayectoria.size() > 1 && trayectoria.get(1).getPosition().distance(sp.myPlayers()[ultimoPasador]) <= Constants.DISTANCIA_CONTROL_BALON;
		final DestinoBalon destinoRival = obtenerRivalMejorUbicado(trayectoria, true);
		if (tendreControl && (destinoRival == null || destinoRival.getIterJugador() > 3)) {
			final Position posFinal = trayectoria.size() > 1 ? trayectoria.get(1).getPosition() : sp.ballPosition();
			comandos.add(new CommandMoveTo(ultimoPasador, posFinal));
			return true;
		}
		return false;
	}

	private void despejarBalon() {
		// como ultimo recurso, se intenta despejar el balon
		if (sp.ballPosition().getY() > 45) {
			if (hacerPaseAlVacio(false, false, -1, true) || hacerPaseAlVacio(false, false, -1, false)) {
				return;
			}
		}
		if (sp.ballPosition().getY() > 35) {
			comandos.add(new CommandHitBall(ultimoPasador, Constants.centroArcoSup, 1, false));
			return;
		}
		if (!hacerPaseAlVacio(true, false, -1, true)) {
			if (!hacerPaseAlVacio(true, false, -1, false)) {
				if (sp.ballPosition().getY() < 0 || !hacerPaseAlVacio(true, true, -1, false)) {
					if (sp.ballPosition().getY() < 0 || !hacerPaseAlVacio(true, false, -1, false)) {
						comandos.add(new CommandHitBall(ultimoPasador, Constants.centroArcoSup, 1, sp.ballPosition().getY() < -10 ? 35 : 50));
					}
				}
			}
		}
	}

	private boolean golpearBalon() {
		final boolean sacar = puedoGolpear();
		// si no puedo sacar, hacerco a un jugador para hacerlo
		if (sacar) {
			iterSaco = 0;
			// si intenta golpear el balon con todos los jugadores disponibles
			for (int pateador = 0; pateador < sp.canKick().length; pateador++) {
				ultimoPasador = sp.canKick()[pateador];
				final double y = sp.ballPosition().getY();
				if (y < -25) {
					defender();
				} else if (y < 10) {
					controlar();
				} else if (y < 35) {
					atacar();
				} else {
					definir();
				}
			}
		}
		return true;
	}

	/**
	 * Se ubica la alineaci�n dependiendo de la situraci�n del partido
	 */
	private void setAlineacion() {
		final List<Integer> excluir = new ArrayList<Integer>();
		// se determinan los jugadores que ya tienen un comando de movimiento
		// asignado para no reubicarlos en la alineacion
		for (final Command com : comandos) {
			if (com instanceof CommandMoveTo) {
				excluir.add(com.getPlayerIndex());
			}
		}
		if (sp.isStarts() && sp.ballPosition().getY() < -45) {
			alinear(alineaciones[2], excluir);
		} else {
			// se determina la alineaci�n a usar dependiendo de la pos del bal�n en el campo
			final double y = Math.max(0, sp.ballPosition().getY() + Constants.centroArcoSup.getY() - 1);
			int seccionAct = Math.max(0, Math.min(alineaciones.length - 1, (int) Math.floor(y / seccion)));
			if (sp.isRivalStarts()) {
				seccionAct = Math.max(0, seccionAct - 3);
			}
			alinear(alineaciones[seccionAct], excluir);
		}
	}

	/** Alinea el equipo en una Position dada */

	/** Alinea el equipo en una Position dada */
	private void alinear(final Position alineacion[], final List<Integer> excluir) {
		// angulos a evaluar
		final double dy = balonAnt != null && !sp.isStarts() ? (sp.ballPosition().getY() - balonAnt.getPosition().getY()) : sp.isStarts() ? 3 : 0;
		final double delta = 0.5;
		for (int i = 1; i < alineacion.length; i++) {
			if (!excluir.contains(i)) {
				final Position pos = alineacion[i];
				if (laTengo) {
					double angulo = 0;
					if (pos.getX() < 0) {
						angulo = pos.angle(Constants.posteIzqArcoSup);
					} else {
						angulo = pos.angle(Constants.posteDerArcoSup);
					}
					comandos.add(new CommandMoveTo(i, pos.moveAngle(angulo, delta, dy).setInsideGameField()));
				} else {
					comandos.add(new CommandMoveTo(i, alineacion[i]));
				}
			}
		}
	}

	private Position getMejorPosJugador(final Position pos) {
		final double height = Math.max(Constants.LARGO_CAMPO_JUEGO / 3, 2 * Math.min(Math.abs(Constants.centroArcoInf.getY() - sp.ballPosition().getY()), Math.abs(Constants.centroArcoSup.getY() - sp.ballPosition().getY())));
		final double width = Math.max(Constants.ANCHO_CAMPO_JUEGO * 2 / 3, 2 * Math.min(Math.abs(Constants.cornerInfIzq.getX() - sp.ballPosition().getX()), Math.abs(Constants.cornerInfDer.getX() - sp.ballPosition().getX())));
		Position center = new Position(sp.ballPosition().getX(), sp.ballPosition().getY());
		if (center.getX() + width / 2 > Constants.cornerInfDer.getX()) {
			center = center.movePosition(-(center.getX() + width / 2 - Constants.cornerInfDer.getX()), 0);
		}
		if (center.getX() - width / 2 < Constants.cornerInfIzq.getX()) {
			center = center.movePosition(Constants.cornerInfIzq.getX() - (center.getX() - width / 2), 0);
		}
		if (center.getY() + height / 2 > Constants.centroArcoSup.getY()) {
			center = center.movePosition(0, -(center.getY() + height / 2 - Constants.centroArcoSup.getY()));
		}
		if (center.getY() - height / 2 < Constants.centroArcoInf.getY()) {
			center = center.movePosition(0, Constants.centroArcoInf.getY() - (center.getY() - height / 2));
		}
		return new Position(pos.getX() * width / Constants.ANCHO_CAMPO_JUEGO + center.getX(), pos.getY() * height / Constants.LARGO_CAMPO_JUEGO + center.getY());
	}

	/**
	 * Metodo que retorna el jugador con mas posibilidades de interceptar el balon en iteraciones futuras
	 * 
	 * @param esTrReal
	 * 
	 * @return Posicion 0: id del jugador, Posicion 1: id del rival, Posicion 2: Jugador que llega primero 0->jugador propio 1->rival
	 */
	private DestinoBalon obtenerMejorUbicado(final LinkedList<Balon> trayectoriaBalon, final boolean esTrReal, final boolean analizarPortero, final boolean soloMios) {
		DestinoBalon destinoMio = null;
		DestinoBalon destinoRival = null;
		if (!analizarPortero) {
			for (int i = 0; i < 11; i++) {
				if (i != idxMiPortero) {
					final DestinoBalon bDestino = obtenerPosDestino(trayectoriaBalon, i, false, destinoMio == null ? 100 : destinoMio.getIterBalon(), esTrReal);
					if (bDestino != null
							&& (destinoMio == null || (destinoMio != null && bDestino.compareTo(destinoMio) > 0 || (bDestino.compareTo(destinoMio) == 0 && sp.myPlayers()[bDestino.getIdxJugador()].distance(bDestino.getPosBalon()) < sp.myPlayers()[destinoMio
									.getIdxJugador()].distance(destinoMio.getPosBalon()))))) {
						destinoMio = bDestino;
					}
				}
			}
		} else {
			destinoMio = obtenerPosDestino(trayectoriaBalon, idxMiPortero, false, 100, esTrReal);
		}
		if (soloMios) {
			return destinoMio;
		}
		// se busca el rival mejor ubicado
		destinoRival = obtenerRivalMejorUbicado(trayectoriaBalon, esTrReal);

		if (analizarPortero && destinoMio != null && destinoRival != null) {
			// si el portero intercepta el balon antes
			if ((destinoMio.getAltura() > Constants.ALTURA_CONTROL_BALON && destinoMio.getPosBalon().distance(sp.ballPosition()) <= destinoRival.getPosBalon().distance(sp.ballPosition()))
					|| destinoMio.getIterJugador() + 3 < destinoRival.getIterBalon()) {
				return destinoMio;
			}
		}
		if (destinoMio != null && destinoRival == null) {
			return destinoMio;
		}
		if (destinoMio == null && destinoRival != null) {
			return destinoRival;
		}
		if (destinoMio != null && destinoRival != null) {
			return destinoMio.compareTo(destinoRival) > 0 ? destinoMio : destinoRival;

		}
		return destinoMio;
	}

	private DestinoBalon obtenerRivalMejorUbicado(final LinkedList<Balon> trayectoriaBalon, final boolean esTrReal) {
		final int[] indices = sp.ballPosition().nearestIndexes(sp.rivalPlayers());
		DestinoBalon destino = null;
		for (final int i : indices) {
			final DestinoBalon bDestino = obtenerPosDestino(trayectoriaBalon, i, true, destino == null ? 1000 : destino.getIterBalon(), esTrReal);
			if (bDestino != null) {
				// si no hay datos previos
				if (destino == null) {
					destino = bDestino;
				} else {
					// si ambas opciones requiren de las mismas iteraciones, se decide por el mas cercano al punto de destino
					if (bDestino.compareTo(destino) == 0) {
						final Position posDestinoAnt = destino.isGanaRival() ? sp.rivalPlayers()[destino.getIdxJugador()] : sp.myPlayers()[destino.getIdxJugador()];
						final Position posDestinoAct = sp.rivalPlayers()[bDestino.getIdxJugador()];

						final double distDestinoAnt = posDestinoAnt.distance(destino.getPosBalon());
						final double distDestinoAct = posDestinoAct.distance(destino.getPosBalon());
						if (distDestinoAnt > distDestinoAct) {
							destino = bDestino;
						}
					}
					// si la opcion es mejor a la anterior
					else if (bDestino.compareTo(destino) > 0) {
						destino = bDestino;
					}
				}
				destino.setGanaRival(true);
			}
		}

		return destino;
	}

	private DestinoBalon obtenerPosDestino(final LinkedList<Balon> trayectoriaBalon, final int idx, final boolean esRival, final int maxIteraciones, final boolean esTrReal) {
		// posicion alcual del jugador y el balon
		final Position jugador = esRival ? sp.rivalPlayers()[idx] : sp.myPlayers()[idx];
		// si hay informacion de trayectoria
		if (trayectoriaBalon.size() > 1) {

			// se obtiene la velocidad de desplazamiento y altura de control del jugador
			final double velocidad = Constants.getVelocidad(esRival ? sp.getRivalPlayerSpeed(idx) : sp.getMyPlayerSpeed(idx));
			final double alturaMax = esArquero(idx, esRival) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;

			// se definen las iteraciones en las cuales puede controlar el balon el jugador
			final int iteracionesControl = esRival ? sp.rivalIterationsToKick()[idx] : (esTrReal || idx != ultimoPasador ? sp.iterationsToKick()[idx] : Constants.ITERACIONES_GOLPEAR_BALON);

			// iteracion inicial examinada
			int iterBalon = 0;
			do {
				final Balon infoBalonSig = trayectoriaBalon.get(iterBalon);
				if (infoBalonSig.getAltura() <= alturaMax) {

					// se determinan nuevamente los datos de control
					final double[] datosControl = getDatosControl(idx, infoBalonSig.getPosition(), esRival);

					// distancia del jugador al balon
					final double distBalon = jugador.distance(infoBalonSig.getPosition()) - datosControl[0];

					// se verifica si la distancia y la altura permiten controlar el balon
					if (distBalon <= velocidad * iterBalon && infoBalonSig.getAltura() <= datosControl[1] && iterBalon >= iteracionesControl) {
						return new DestinoBalon(infoBalonSig, idx, iterBalon, iterBalon);
					}
				}
				iterBalon++;
			} while (iterBalon < trayectoriaBalon.size() && iterBalon <= maxIteraciones);
		}
		return null;
	}

	/**
	 * Round a double value to a specified number of decimal places.
	 * 
	 * @param val
	 *            the value to be rounded.
	 * @param places
	 *            the number of decimal places to round to.
	 * @return val rounded to places decimal places.
	 */
	public static double round(double val, final int places) {
		final long factor = (long) Math.pow(10, places);
		// Shift the decimal the correct number of places to the right.
		val = val * factor;
		// Round to the nearest integer.
		final long tmp = Math.round(val);
		// Shift the decimal the correct number of places back to the left.

		return (double) tmp / factor;
	}

	@Override
	public Position[] getStartPositions(final GameSituations sp) {
		return alineacionSaca;
	}

	@Override
	public Position[] getNoStartPositions(final GameSituations sp) {
		return alineacionRecibe;
	}

	private ComandoGolpear getOpcionPase(final int idxPasador, final int idxReceptor, final boolean arriba, final int minIterDif) {
		final Position balon = sp.ballPosition();
		// posicion inicial del pasador y receptor
		final Position receptor = sp.myPlayers()[idxReceptor];
		final boolean autoPase = idxPasador == idxReceptor;
		// velocidad de desplazamiento del receptor
		final double velReceptor = Constants.getVelocidad(sp.getMyPlayerSpeed(idxReceptor));

		final LinkedHashMap<Integer, TreeSet<ComandoGolpear>> angulosPosibles = new LinkedHashMap<Integer, TreeSet<ComandoGolpear>>();

		// posicion final de avance
		// final Position pAvance = receptor.getX() < 0 ? Constants.centroArcoSup.movePosition(-16, -6) : Constants.centroArcoSup.movePosition(16, -6);
		final Position pAvance = Constants.centroArcoSup.movePosition(0, -5);

		// indica cuales angulos evaluar primero (derecha o izquierda con relacion al angulo de 90 grados)
		final int delta = receptor.angle(pAvance) * TO_ANG <= 90 ? -1 : 1;

		// angulo inicial de avance
		final int angInicial = (int) Math.round(receptor.angle(pAvance) * TO_ANG);
		// angulos a evaluar
		angulosPosibles.put(angInicial, null);
		for (int i = 10; i <= 180; i += 10) {
			final int a = angInicial - (i * delta);
			final int b = angInicial + (i * delta);
			angulosPosibles.put(a, null);
			angulosPosibles.put(b, null);
		}
		// se determina si es un disparo normal o un saque
		final int tipoDisparo = sp.isStarts() && !balon.equals(Constants.cornerSupDer) && !balon.equals(Constants.cornerSupIzq) && !balon.equals(metaAbajoDerecha) && !balon.equals(metaAbajoIzquierda) ? 0 : 1;
		// iteraciones minimas para golpear el balon
		final int pasoIni = autoPase ? Constants.ITERACIONES_GOLPEAR_BALON : 0;

		final double distanciaMinima = 5 + (4.0 * minIterDif);
		ComandoGolpear cgRet = null;
		for (final int ang : angulosPosibles.keySet()) {
			final double angReceptor = ang * TO_RAD;
			for (int paso = ang == angInicial ? pasoIni : 3; paso <= 40; paso += 3) {
				// movemos el receptor en angulo
				final Position pFinal = receptor.moveAngle(angReceptor, (velReceptor * paso));
				// restriccion para pases hacia arriba -> pos final >= a la posicion del balon
				final boolean paseAdeltante = arriba && pFinal.getY() >= balon.getY();
				if ((arriba && paseAdeltante) || (!arriba && !paseAdeltante)) {
					if (autoPase && idxReceptor == idxMiPortero && (!posFinalEnAreaGrande(pFinal, false) || pFinal.getY() < sp.ballPosition().getY())) {
						break;
					}
					// se determina la distancia entre el balon y el punto destino del pase
					final double distancia = balon.distance(pFinal);
					// si el punto se encuentra en el campo de juego y se cumplen las condiciones de pase
					if (pFinal.isInsideGameField(-2) && (autoPase || distancia >= distanciaMinima)) {
						// se obtiene la mejor opciones de golpeo (fuerza y menos iteraciones para llegar al punto)
						InfoTrayectoria infoTrayectoria = getOpcionesDisparo(idxPasador, tipoDisparo, distancia + (autoPase ? Constants.DISTANCIA_CONTROL_BALON : 0), 0, paso);
						// se calcula el disparo al punto y se verifica si es interceptado por un jugador mio antes que un rival
						ComandoGolpear comando = disparoAlPunto(infoTrayectoria, idxPasador, pFinal, idxReceptor, paso, minIterDif);
						// si no es un autopase y no existe opcion de pase rastrero, se intenta el pase elevado
						if (!autoPase && comando == null) {
							infoTrayectoria = getOpcionesDisparo(idxPasador, tipoDisparo, distancia, Constants.ALTURA_CONTROL_BALON, paso);
							comando = disparoAlPunto(infoTrayectoria, idxPasador, pFinal, idxReceptor, paso, minIterDif);
						}
						// si hay opcion de pase y el balon no sale del campo
						if (comando != null) {
							// si el jugador llega en las mismas iteraciones que el balon al punto, se retorna la opcion
							if (!autoPase && comando.getIterJugador() == paso) {
								return comando;
							}
							comando.setAnguloXY(ang);
							TreeSet<ComandoGolpear> list = new TreeSet<ComandoGolpear>();
							if (angulosPosibles.get(ang) != null) {
								list = angulosPosibles.get(ang);
							}
							list.add(comando);
							angulosPosibles.put(ang, list);
						}
					}
					// si el destino est� fuera del campo
					else if (!pFinal.isInsideGameField(-2)) {
						break;
					}
				}
			}
			// si se trata de un autopase y se obtuvo alguna opcion de avance
			if (angulosPosibles.get(ang) != null) {
				final TreeSet<ComandoGolpear> opciones = angulosPosibles.get(ang);
				for (final ComandoGolpear c : opciones.descendingSet()) {
					final int dif = c.getIterRival() - c.getIterJugador();
					if (cgRet == null || (dif > cgRet.getIterRival() - cgRet.getIterJugador() /* || (dif == cgRet.getIterRival() - cgRet.getIterJugador() && c.getComando().getDestiny().getY() > cgRet.getComando().getDestiny().getY()) */)) {
						cgRet = c;
					}
				}
			}

		}
		return cgRet;
	}

	private ComandoGolpear disparoAlPunto(final InfoTrayectoria info, final int idxPateador, final Position pFinal, final int idxReceptor, int maxIteraciones, final double minIterDif) {
		if (info != null) {
			maxIteraciones = Math.max(maxIteraciones, info.getIteraciones());
			// datos iniciales del balon
			final Balon balon = new Balon(sp.ballPosition(), sp.ballAltitude());
			final Position pateador = sp.myPlayers()[idxPateador];
			final Position receptor = idxReceptor == -1 ? Constants.centroArcoSup : sp.myPlayers()[idxReceptor];
			final double distPase = idxPateador != idxReceptor ? receptor.distance(pFinal) : pateador.distance(pFinal);
			final int minIter = idxPateador == idxReceptor || idxReceptor == -1 ? Constants.ITERACIONES_GOLPEAR_BALON : sp.iterationsToKick()[idxReceptor];
			// angulo horizontal de disparo
			final double angleXY = sp.ballPosition().angle(pFinal);

			// si el tiro es preciso al punto calculado
			final LinkedList<Balon> trayectoriaBalon = new LinkedList<Balon>();
			// se obtiene la trayectoria del balon al punto y las iteraciones que pasan hasta que el receptor reciba el balon
			final int iterReceptor = obtenerTrayectoriaCalculada(trayectoriaBalon, balon, info.getPotencia(), angleXY, info.getAngulo(), pFinal, true, idxReceptor, 100);
			// si el metodo retorna mas de una iteracion, es pq se alcanza el punto deseado
			if ((iterReceptor <= maxIteraciones && iterReceptor >= minIter) || (iterReceptor >= 0 && idxReceptor == -1)) {

				// si es un pase a un jugador nuestro, se verifica que ningun rival intercepte el pase
				final DestinoBalon destino = obtenerRivalMejorUbicado(trayectoriaBalon, false);
				int idxRival = pFinal.nearestIndex(sp.rivalPlayers());
				// distancia del rival al punto de intercepcion
				double distRivAlPunto = sp.rivalPlayers()[idxRival].distance(pFinal);
				if (destino != null) {
					idxRival = destino.getIdxJugador();
					distRivAlPunto = sp.rivalPlayers()[idxRival].distance(destino.getPosBalon());
				}
				final int iterRival = destino != null && destino.getIterJugador() > 0 ? destino.getIterJugador() : (int) Math.ceil((distRivAlPunto - Constants.DISTANCIA_CONTROL_BALON)
						/ Constants.getVelocidad(sp.rivalPlayersDetail()[idxRival].getSpeed()));
				// si nadie intercepta el pase
				if (iterRival - iterReceptor > minIterDif && (destino == null || distRivAlPunto > distPase || (idxReceptor == -1 && destino.getIdxJugador() == idxPortRival))) {
					Position posFin = pFinal;
					if (trayectoriaBalon.size() > iterReceptor) {
						posFin = trayectoriaBalon.get(iterReceptor).getPosition();
					}
					return new ComandoGolpear(new CommandHitBall(idxPateador, posFin, info.getFuerza(), info.getAngulo() * TO_ANG), trayectoriaBalon, info.getAngulo(), iterReceptor, iterRival, false, false);
				}
				return null;
			}
		}
		return null;
	}

	private InfoTrayectoria getOpcionesDisparo(final int idxJugador, final int tipoDisparo, final double distancia, final double altFinal, final int minIteraciones) {
		InfoTrayectoria trayectoria = null;
		final double fuerza = sp.getMyPlayerPower(idxJugador);
		final double key = round(distancia, 1);
		for (final OpcionesDisparo opc : opcionesDisparo) {
			// se busca la info por fuerza del jugador y tipo de disparo
			if (opc.getFuerza() == fuerza && opc.getTipoDisparo() == tipoDisparo) {
				if (altFinal == 0) {
					int mejorOpcion = 100;
					// se retorna la opcion que alcance el punto lo mas rapido posible y despues de minIteraciones
					for (final InfoTrayectoria info : opc.getOpcionesAngCero().descendingSet()) {
						if (info.getDistancia() >= key && info.getIteraciones() >= minIteraciones) {
							// se obtiene la distancia recorrida en la iteracion mas cercana
							final NavigableSet<Double> subSet = info.getDistancias().headSet(key, true);
							if (subSet.size() < mejorOpcion && subSet.size() >= minIteraciones + 1) {
								int iter = 0;
								for (final Double d : subSet) {
									if (d + Constants.DISTANCIA_CONTROL_BALON >= key) {
										if (iter < minIteraciones) {
											break;
										}
										mejorOpcion = subSet.size();
										trayectoria = new InfoTrayectoria(info);
										trayectoria.setIteraciones(iter);
										break;
									}
									iter++;
								}
							}
						}
					}
				} else {
					double mejorOpcion = 0;
					if (opc.getOpciones().containsKey(key)) {
						for (final InfoTrayectoria info : opc.getOpciones().get(key).descendingSet()) {
							if (info.getIteraciones() >= minIteraciones && info.getPotencia() > mejorOpcion) {
								mejorOpcion = info.getPotencia();
								trayectoria = new InfoTrayectoria(info);
							}
						}
					} else {
						final Double floor = opc.getOpciones().floorKey(key);
						if (floor != null) {
							for (final InfoTrayectoria info : opc.getOpciones().get(floor).descendingSet()) {
								if (info.getIteraciones() >= minIteraciones && info.getPotencia() > mejorOpcion) {
									mejorOpcion = info.getPotencia();
									trayectoria = new InfoTrayectoria(info);
								}
							}
						}
					}
				}
				return trayectoria;
			}
		}
		return trayectoria;
	}

	/**
	 * Se determina la trayectoria total del balon hasta que salga del campo o se detenga
	 * 
	 * @return Lista de posiciones de la trayectoria completa incluyendo la altura del balon
	 */
	private void getInfoTrayectoriaReal() {
		trayectoria.clear();
		boolean balonEnCampo = true;
		int iter = 0;
		double yAnt = 1000d;
		double xAnt = 1000d;
		// FIXME ajuste para cuando hay rebote ya que al parecer tiene lios la funcion
		final double alturaTeorica = sp.getTrajectory(0)[2];
		// se itera mientras el balon se encuentre en el campo
		while (balonEnCampo) {
			// posicion del balon en la iteracion indicada
			final double[] valores = sp.getTrajectory(iter);
			final Balon b = new Balon(new Position(valores[0], valores[1]), sp.ballAltitude() != alturaTeorica ? 0 : valores[2], iter);

			final Position p = b.getPosition();

			balonEnCampo = p.isInsideGameField(0);

			// si el balon se encuentra en la misma posicion en la siguiente
			// iteracion -> Balon detenido
			if (p.getY() == yAnt && p.getX() == xAnt && balonEnCampo) {
				break;
			}
			yAnt = p.getY();
			xAnt = p.getX();
			iter++;
			trayectoria.add(b);
		}
	}

	/**
	 * Indica se se trata de mi portero
	 * 
	 * @param idx
	 * @param esRival
	 * @return
	 */
	private boolean esArquero(final int idx, final boolean esRival) {
		return esRival ? idxPortRival == idx : idx == idxMiPortero;
	}

	/**
	 * Retorna un arreglo con la distancia y al altura de control del balon del jugador segun la posicion del campo a examinar
	 * 
	 * @param idxJugador
	 * @param p
	 * @return posicion 0: distancia, posicion 1: altura
	 */
	private double[] getDatosControl(final int idxJugador, final Position p, final boolean esRival) {
		if ((idxJugador == -1 || esArquero(idxJugador, esRival)) && posFinalEnAreaGrande(p, esRival)) {
			return new double[] { Constants.DISTANCIA_CONTROL_BALON_PORTERO, Constants.ALTO_ARCO };
		}
		return new double[] { Constants.DISTANCIA_CONTROL_BALON, Constants.ALTURA_CONTROL_BALON };
	}

	private boolean posFinalEnAreaGrande(final Position p, final boolean areaRival) {
		final boolean cumpleY = areaRival ? p.getY() > (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE : p.getY() < (-Constants.LARGO_CAMPO_JUEGO / 2) + Constants.ANCHO_AREA_GRANDE;
		return Math.abs(p.getX()) <= Constants.LARGO_AREA_GRANDE / 2 && cumpleY;
	}

	private ComandoGolpear mejorDisparoAlArco(final int idxPateador, final boolean obstaculo, final double minAngZ, final double maxAngZ) {
		// datos iniciales del balon
		final Balon balon = new Balon(sp.ballPosition(), sp.ballAltitude());
		// informacion del pateador
		final PlayerDetail pateador = detalle.getPlayers()[idxPateador];

		final double velocidadRemate = Constants.getVelocidadRemate(pateador.getPower());
		final double maxX = (Constants.LARGO_ARCO / 2);
		final LinkedList<Position> opciones = new LinkedList<Position>();
		final Position pInicial = Constants.centroArcoSup;

		opciones.add(pInicial);
		for (double x = 0.5; x < maxX; x += 0.5d) {
			opciones.add(pInicial.movePosition(x, 0));
			opciones.add(pInicial.movePosition(-x, 0));
		}
		// opciones.add(new Position(maxX, 52.5));
		// opciones.add(new Position(maxX, 52.5));
		for (final Position position : opciones) {
			final double angleXY = sp.ballPosition().angle(position);
			// se prueban diferentes opciones de fuerza
			for (double fuerza = 1; fuerza > (maxAngZ > 0 ? 0.6 : 0.95); fuerza -= 0.05d) {
				// potecia obtenida dependiendo de la fuerza aplicada
				final double potencia = fuerza * velocidadRemate * (sp.isStarts() ? 0.75 : 1);
				// se prueban diferentes opciones de angulos
				for (double angZ = minAngZ; angZ <= maxAngZ; angZ += 0.5) {
					// angulo vertical en radianes
					final double angRadZ = Math.max(0, Math.min(angZ, Constants.ANGULO_VERTICAL_MAX)) * (TO_RAD);
					// si el tiro es preciso al punto calculado
					final LinkedList<Balon> trayectoriaBalon = new LinkedList<Balon>();
					// se obtiene la trayectoria del balon al punto
					final int iteraciones = obtenerTrayectoriaCalculada(trayectoriaBalon, balon, potencia, angleXY, angRadZ, position, false, -1, 100);
					// si el metodo retorna mas de una iteracion, es pq se alcanza el punto deseado
					if (iteraciones >= 0) {
						DestinoBalon destino = null;
						if (obstaculo) {
							destino = obtenerRivalMejorUbicado(trayectoriaBalon, false);
						}
						int idxRival = position.nearestIndex(sp.rivalPlayers());
						if (destino != null) {
							idxRival = destino.getIdxJugador();
						}
						// distancia del rival al punto del pase
						final double distRivAlPunto = sp.rivalPlayers()[idxRival].distance(position);
						int iterRival = (int) Math.ceil(distRivAlPunto / Constants.getVelocidad(sp.rivalPlayersDetail()[idxRival].getSpeed()));
						if (destino != null) {
							idxRival = destino.getIdxJugador();
							iterRival = destino.getIterJugador();
						}
						// si "nadie" intercepta el pase
						if (destino == null || (destino.getIterJugador() > iteraciones) || (destino.getIdxJugador() == idxPortRival && trayectoriaBalon.get(destino.getIterJugador()).getPosition().getY() > 52.5)) {
							// si se trata de un disparo a meta
							return new ComandoGolpear(new CommandHitBall(idxPateador, trayectoriaBalon.getLast().getPosition(), fuerza, angZ), trayectoriaBalon, angleXY, iteraciones, iterRival, false, false);
						}
					}
				}
			}
		}
		return null;
	}

}
