/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.IdentificableData;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.JugadorBalonData;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.JugadoresBalonData;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.JugadoresBalonThread;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.PaseData;
import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.PaseThread;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author MaN
 */
public class MSGSituacionPartidoContext {

    /**
     * Log.
     */
    private static final Logger log = Logger.getLogger(MSGSituacionPartidoContext.class.getName());
    /**
     * Listeners para notificar de actualizaciones.
     */
    private final Set<MSGSituacionPartidoListener> listeners;
    /**
     * Tática asociada al contexto.
     */
    private MSGTactica tactica;
    /**
     * Alineación.
     */
    private Map<Integer, MSGAlineacionPosicion> alineacion;
    /**
     * Situación del partido.
     */
    private GameSituations situacionPartido;
    /**
     * Balón.
     */
    private MSGBalon balon = null;
    /**
     * Jugadores del rival
     */
    private Map<Integer, MSGJugadorPropio> jugadoresPropios = null;
    /**
     * Portero rival.
     */
    private MSGJugadorDetalle porteroRival;
    /**
     * Lista de jugadores rivalPlayers.
     */
    private Map<Integer, MSGJugadorDetalle> jugadoresRivales = null;
    /**
     * Posición de los jugadores mas cercanos al ballPosition
     */
    private JugadoresBalonData jugadoresCercanosBalonData;
    /**
     * Lista de los jugadores más cercanos al balón.
     */
    private List<MSGJugadorPropio> jugadoresPropiosCercanosBalon;
    /**
     * Lista de los jugadores más cercanos al balón.
     */
    private List<MSGJugadorDetalle> jugadoresRivalesCercanosBalon;
    /**
     * Lista de los jugadores más cercanos al balón.
     */
    private List<MSGJugadorPropio> jugadoresPropiosCercanosBalonFuturo;
    /**
     * Lista de los jugadores más cercanos al balón.
     */
    private List<MSGJugadorDetalle> jugadoresRivalesCercanosBalonFuturo;
    /**
     * Marcajes de los jugadores
     */
    private Map<MSGJugadorPropio, MSGJugadorDetalle> jugadoresPropiosMarcajes;
    /**
     * Desmarques de cada uno de los jugadores.
     */
    private Map<MSGJugadorPropio, PaseData> jugadoresPropiosDesmarques;
    /**
     * Iteraciones que tengo para sacar.
     */
    private Integer iteracionesSaco;

    /**
     * Constructor.
     */
    public MSGSituacionPartidoContext() {
        this.listeners = new HashSet<MSGSituacionPartidoListener>();
        if (MSGConstants.VISUAL_DEBUG) {
            this.listeners.add(MSGSituacionPartidoUIListener.getInstance());
        }
    }

    public void update(MSGTactica tactica, Map<Integer, MSGAlineacionPosicion> alineacion, GameSituations situacionPartido) {
        if (situacionPartido.isStarts()) {
            iteracionesSaco--;
        } else {
            iteracionesSaco = Constants.ITERACIONES_SAQUE;
        }
        if (balon == null) {
            balon = new MSGBalon();
            this.listeners.add(balon);
        }
        if (jugadoresRivales == null) {
            jugadoresRivales = new HashMap<Integer, MSGJugadorDetalle>();
            jugadoresRivalesCercanosBalon = new ArrayList<MSGJugadorDetalle>();
            jugadoresRivalesCercanosBalonFuturo = new ArrayList<MSGJugadorDetalle>();
            for (int i = 0; i < situacionPartido.rivalPlayers().length; i++) {
                MSGJugadorDetalle jugador = new MSGJugadorRival(i, situacionPartido.rivalPlayersDetail()[i]);
                this.jugadoresRivales.put(i, jugador);
                this.jugadoresRivalesCercanosBalon.add(jugador);
                this.jugadoresRivalesCercanosBalonFuturo.add(jugador);
                this.listeners.add(jugador);

                if (jugador.isGoalKeeper()) {
                    porteroRival = jugador;
                }
            }
        }
        if (jugadoresPropios == null) {
            jugadoresPropios = new HashMap<Integer, MSGJugadorPropio>();
            jugadoresPropiosCercanosBalon = new ArrayList<MSGJugadorPropio>();
            jugadoresPropiosCercanosBalonFuturo = new ArrayList<MSGJugadorPropio>();
            jugadoresPropiosMarcajes = new HashMap<MSGJugadorPropio, MSGJugadorDetalle>();
            jugadoresPropiosDesmarques = new HashMap<MSGJugadorPropio, PaseData>();
            for (MSGJugadorPropio jugador : tactica.getJugadores()) {
                this.jugadoresPropios.put(jugador.getIndex(), jugador);
                this.jugadoresPropiosCercanosBalon.add(jugador);
                this.jugadoresPropiosCercanosBalonFuturo.add(jugador);
                this.listeners.add(jugador);
            }
        }

        this.tactica = tactica;
        this.alineacion = alineacion;
        this.situacionPartido = situacionPartido;
        this.updateListeners();
        this.updateListasOrdenadas();
        this.updateJugadoresCercanosBalon();
        this.updateMarcajes();
        this.updateDesmarques();
    }

    private void updateListeners() {
        for (MSGSituacionPartidoListener listener : listeners) {
            listener.afterUpdate(this);
        }
    }

    private void updateJugadoresCercanosBalon() {
        JugadorBalonData propioData = handle(situacionPartido.getRecoveryBall(), jugadoresPropios);
        try {
            List<MSGJugadorDetalle> jugadores = new LinkedList<MSGJugadorDetalle>();
            jugadores.addAll(jugadoresRivales.values());
            jugadores.add(propioData.getJugador());
            JugadoresBalonThread
                thread = new JugadoresBalonThread(null, jugadores, MSGTrayectoria.getInstance(situacionPartido));
            this.jugadoresCercanosBalonData = thread.call();
        } catch (Exception ex) {
            this.jugadoresCercanosBalonData = null;
        }

        if (this.jugadoresCercanosBalonData == null) {
            this.jugadoresCercanosBalonData = new JugadoresBalonData(null, propioData);
        } else {
            this.jugadoresCercanosBalonData.setPropio(propioData);
        }

        if (this.jugadoresCercanosBalonData.getContrario() == null && this.jugadoresCercanosBalonData.getPropio() == null) {
            this.jugadoresCercanosBalonData = null;
        }

    }

    private JugadorBalonData handle(int[] puedenRematar, Map<Integer, ? extends MSGJugadorDetalle> jugadores) {
        if (puedenRematar.length > 1) {
            int iteraciones = puedenRematar[0];
            Punto3D punto = new Punto3D(situacionPartido.getTrajectory(iteraciones));
            MSGJugadorDetalle jugador = jugadores.get(puedenRematar[1]);
            return new JugadorBalonData(jugador, false, iteraciones - situacionPartido.iteration(), punto);
        }
        return null;
    }

    private void updateListasOrdenadas() {
        Punto posicionBalon = new Punto(situacionPartido.ballPosition());
        Comparator<MSGJugadorDetalle> balonComparator = new PosicionComparator(posicionBalon);
        Comparator<MSGJugadorDetalle> balonFuturoComparator = null;
        if (this.jugadoresCercanosBalonData == null) {
            balonFuturoComparator = balonComparator;
        } else {
            balonFuturoComparator = new PosicionComparator(
                    jugadoresCercanosBalonData.isAlcanzable()
                    ? jugadoresCercanosBalonData.getPropio().getPunto().getPuntoXY()
                    : jugadoresCercanosBalonData.getContrario().getPunto().getPuntoXY());
        }
        Collections.sort(jugadoresPropiosCercanosBalon, balonComparator);
        Collections.sort(jugadoresRivalesCercanosBalon, balonComparator);
        Collections.sort(jugadoresPropiosCercanosBalonFuturo, balonFuturoComparator);
        Collections.sort(jugadoresRivalesCercanosBalonFuturo, balonFuturoComparator);
    }

    private void updateMarcajes() {
        jugadoresPropiosMarcajes.clear();
        List<MSGJugadorDetalle> rivales = new LinkedList<MSGJugadorDetalle>(this.jugadoresRivales.values());
        List<MSGJugadorPropio> propios = new LinkedList<MSGJugadorPropio>(this.jugadoresPropios.values());

        for (Iterator<MSGJugadorDetalle> it = rivales.iterator(); it.hasNext();) {
            MSGJugadorDetalle rival = it.next();
            it.remove();
            if (!rival.isGoalKeeper()) {
                Comparator<MSGJugadorDetalle> comparator = new PosicionComparator(rival.getPosicion());
                Collections.sort(propios, comparator);
                MSGJugadorPropio detalle = propios.get(0);
                propios.remove(detalle);
                if (detalle.isGoalKeeper()) {
                    detalle = propios.get(0);
                    propios.remove(detalle);
                }
                this.jugadoresPropiosMarcajes.put(detalle, rival);
            }
        }
    }

    private void updateDesmarques() {
        jugadoresPropiosDesmarques.clear();
        if (this.jugadoresCercanosBalonData != null && this.jugadoresCercanosBalonData.isAlcanzable() || situacionPartido.isStarts()) {
            Punto3D posicionBalon = situacionPartido.isStarts()
                    ? new Punto3D(situacionPartido.ballPosition().getX(), situacionPartido.ballPosition().getY(), situacionPartido.ballAltitude())
                    : jugadoresCercanosBalonData.getPropio().getPunto();
            MSGJugadorPropio jugadorPasador = situacionPartido.isStarts() ? jugadoresPropiosCercanosBalon.get(0) : (MSGJugadorPropio) jugadoresCercanosBalonData.getPropio().getJugador();
            List<MSGJugadorPropio> jugadoresADesmarcar = MSGUtils.getJugadoresADesmarcar(jugadorPasador, getJugadoresPropios());
            Map<MSGJugadorPropio, List<Punto3D>> puntosComprobacion = MSGUtils.getPuntosDesmarque(jugadoresADesmarcar);
            Map<MSGJugadorPropio, List<PaseData>> pases = getPases(posicionBalon, jugadorPasador, puntosComprobacion);

            for (Map.Entry<MSGJugadorPropio, List<PaseData>> entry : pases.entrySet()) {
                final MSGJugadorPropio jugadorPropio = (MSGJugadorPropio) entry.getKey();
                List<PaseData> pasesParaElJugador = entry.getValue();
                if (!pasesParaElJugador.isEmpty()) {
                    List<Comparator<PaseData>> comparators = Arrays.asList(
                            new AlturaComparator(),
                            new DistanciaJugadorComparator(jugadorPropio),
                            new ProbabilidadControlComparator());
                    for (Comparator<PaseData> comparator : comparators) {
                        pasesParaElJugador = MSGUtils.filter(pasesParaElJugador, comparator, 0.75D);
                    }
                    jugadoresPropiosDesmarques.put(jugadorPropio, pasesParaElJugador.get(0));
                }
            }
        }
    }

    /**
     *
     * @param posicionBalon
     * @param jugadorPasador
     * @param areaJugadores
     * @return
     */
    public Map<MSGJugadorPropio, List<PaseData>> getPases(Punto3D posicionBalon, MSGJugadorPropio jugadorPasador, Map<MSGJugadorPropio, List<Punto3D>> areaJugadores) {
        List<PaseThread> desmarquesThreads = new LinkedList<PaseThread>();
        for (Map.Entry<MSGJugadorPropio, List<Punto3D>> entry : areaJugadores.entrySet()) {
            MSGJugadorPropio jugadorADesmarcar = entry.getKey();
            List<Punto3D> puntosAComprobar = entry.getValue();
            for (Punto3D punto : puntosAComprobar) {
                desmarquesThreads.add(new PaseThread(jugadorPasador, posicionBalon, jugadorADesmarcar, punto, this));
            }
        }

        Map<MSGJugadorPropio, List<PaseData>> pases = new HashMap<MSGJugadorPropio, List<PaseData>>();
        for (IdentificableData<MSGJugadorPropio, PaseData> result : tactica.getThreadOperationHandler().executeThreaded(desmarquesThreads)) {
            MSGJugadorPropio jugador = result.getId();
            PaseData pase = result.getData();
            if (MSGUtils.isPaseValido(pase.getPuntoDestino().getPuntoXY())
                && !pase.getJugadoresMasCercanos().getPropio().isFueraCampo()
                && pase.getJugadoresMasCercanos().getIteracionesVentaja() > jugador.getIteracionesVentajaPase()) {
                List<PaseData> pasesAlJugador = pases.get(jugador);
                if (pasesAlJugador == null) {
                    pasesAlJugador = new LinkedList<PaseData>();
                    pases.put(jugador, pasesAlJugador);
                }
                pasesAlJugador.add(pase);
            }
        }
        return pases;
    }

    public GameSituations getSituacionPartido() {
        return situacionPartido;
    }

    public Map<Integer, MSGAlineacionPosicion> getAlineacion() {
        return Collections.unmodifiableMap(alineacion);
    }

    public MSGTactica getTactica() {
        return tactica;
    }

    public Map<Integer, MSGJugadorPropio> getJugadoresPropios() {
        return Collections.unmodifiableMap(jugadoresPropios);
    }

    public Map<Integer, MSGJugadorDetalle> getJugadoresRivales() {
        return Collections.unmodifiableMap(jugadoresRivales);
    }

    public MSGBalon getBalon() {
        return balon;
    }

    public boolean isAtaque() {
        return jugadoresCercanosBalonData != null && jugadoresCercanosBalonData.isAlcanzable();
    }

    public JugadoresBalonData getJugadoresCercanosBalonData() {
        return jugadoresCercanosBalonData;
    }

    public List<MSGJugadorPropio> getJugadoresPropiosCercanosBalon() {
        return Collections.unmodifiableList(jugadoresPropiosCercanosBalon);
    }

    public List<MSGJugadorPropio> getJugadoresPropiosCercanosBalonFuturo() {
        return Collections.unmodifiableList(jugadoresPropiosCercanosBalonFuturo);
    }

    public List<MSGJugadorDetalle> getJugadoresRivalesCercanosBalon() {
        return Collections.unmodifiableList(jugadoresRivalesCercanosBalon);
    }

    public List<MSGJugadorDetalle> getJugadoresRivalesCercanosBalonFuturo() {
        return Collections.unmodifiableList(jugadoresRivalesCercanosBalonFuturo);
    }

    public Map<MSGJugadorPropio, MSGJugadorDetalle> getJugadoresPropiosMarcajes() {
        return Collections.unmodifiableMap(jugadoresPropiosMarcajes);
    }

    public Map<MSGJugadorPropio, PaseData> getJugadoresPropiosDesmarques() {
        return Collections.unmodifiableMap(jugadoresPropiosDesmarques);
    }

    public Integer getIteracionesSaco() {
        return iteracionesSaco;
    }

    public MSGJugadorDetalle getPorteroRival() {
        return porteroRival;
    }



    public static class PosicionComparator implements Comparator<MSGJugadorDetalle> {

        private final Punto punto;

        public PosicionComparator(Punto punto) {
            this.punto = punto;
        }

        public int compare(MSGJugadorDetalle o1, MSGJugadorDetalle o2) {
            Double d1 = TrigonometriaUtils.getDistancia(punto, o1.getPosicion());
            Double d2 = TrigonometriaUtils.getDistancia(punto, o2.getPosicion());
            return d1.compareTo(d2);
        }
    }

    public static class AlturaComparator implements Comparator<PaseData> {

        public int compare(PaseData o1, PaseData o2) {
            Double y1 = o1.getPuntoDestino().getY();
            Double y2 = o2.getPuntoDestino().getY();
            return (-1) * y1.compareTo(y2);
        }
    }

    public static class ProbabilidadControlComparator implements Comparator<PaseData> {

        public int compare(PaseData o1, PaseData o2) {
            Double p1 = o1.getTrayectoriaPunto().getProbabilidadControl();
            Double p2 = o2.getTrayectoriaPunto().getProbabilidadControl();
            return (-1) * p1.compareTo(p2);
        }
    }

    public static class DistanciaJugadorComparator implements Comparator<PaseData> {

        private final MSGJugadorDetalle jugadorPropio;

        public DistanciaJugadorComparator(MSGJugadorDetalle jugadorPropio) {
            this.jugadorPropio = jugadorPropio;
        }

        public int compare(PaseData o1, PaseData o2) {
            Double d1 = TrigonometriaUtils.getDistancia(o1.getPuntoDestino().getPuntoXY(), jugadorPropio.getPosicion());
            Double d2 = TrigonometriaUtils.getDistancia(o1.getPuntoDestino().getPuntoXY(), jugadorPropio.getPosicion());

            return d1.compareTo(d2);
        }
    }

    public static class IteracionesVentajaComparator implements Comparator<PaseData> {

        public int compare(PaseData o1, PaseData o2) {
            Integer d1 = o1.getJugadoresMasCercanos().getIteracionesVentaja();
            Integer d2 = o2.getJugadoresMasCercanos().getIteracionesVentaja();

            return (-1) * d1.compareTo(d2);
        }
    }
}
