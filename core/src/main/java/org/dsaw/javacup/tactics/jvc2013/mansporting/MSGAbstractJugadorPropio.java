 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

 import org.dsaw.javacup.model.command.Command;
 import org.dsaw.javacup.model.command.CommandHitBall;
 import org.dsaw.javacup.model.command.CommandMoveTo;
 import org.dsaw.javacup.model.util.Constants;
 import org.dsaw.javacup.model.util.Position;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.JugadoresBalonData;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.thread.PaseData;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaManager;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.tray.TrayectoriaPunto;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Punto3D;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.Segmento;
 import org.dsaw.javacup.tactics.jvc2013.mansporting.trig.TrigonometriaUtils;

 import java.awt.*;
 import java.util.Arrays;
 import java.util.Comparator;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Map;
 import java.util.Random;
 import java.util.logging.Logger;

 /**
 *
 * @author MaN
 */
public abstract class MSGAbstractJugadorPropio extends MSGAbstractJugadorDetalle implements MSGJugadorPropio {

    /**
     * Log.
     */
    private static final Logger log = Logger.getLogger(MSGAbstractJugadorPropio.class.getName());
    /**
     * Alineación del jugador.
     */
    protected MSGAlineacionPosicion alineacion;

    public MSGAbstractJugadorPropio(int index, Color colorPelo, Color colorPiel, String nombre, int numero, MSGEstadisticas estadisticas) {
        super(index, colorPelo, colorPiel, nombre, numero, estadisticas, false);
    }

    /**
     * Alineación del jugador.
     * @return alineación
     */
    public MSGAlineacionPosicion getAlineacion() {
        return alineacion;
    }

    @Override
    public final void afterUpdate(MSGSituacionPartidoContext context) {
        super.afterUpdate(context);
        alineacion = context.getAlineacion().get(index);
    }

    public List<Command> ejecuta() {
        List<Command> comando = new MSGComandoList();
        if (getPuedeRematar()) {
            comando.add(pasa());
        } else if (isJugadorMasCercano()) {
            comando.add(irABalon());
        } else if (context.isAtaque()) {
            comando.add(desmarque());
        } else {
            comando.add(cubreContrario());
        }
        return comando.isEmpty() ? Arrays.asList(irAPosicionAlineacion()) : comando;
    }

    protected final Command irAPosicionAlineacion() {
        MSGAlineacionPosicion a = context.getAlineacion().get(index);
        return new CommandMoveTo(
                getIndex(),
                new Position(a.getPosicion().getX(), a.getPosicion().getY()));
    }

    protected Command irABalon() {
        Punto posicionBalon;
        if (context.getJugadoresCercanosBalonData() != null) {
            JugadoresBalonData balonData = context.getJugadoresCercanosBalonData();
            posicionBalon = context.isAtaque() ? balonData.getPropio().getPunto().getPuntoXY() : balonData.getContrario().getPunto().getPuntoXY();
        } else {
            posicionBalon = context.getBalon().getPosicion().getPuntoXY();
        }
        if (posicionBalon == null) {
            return null;
        }
        Punto punto = MSGUtils.situaEnTerrenoJuego(posicionBalon);
        return new CommandMoveTo(index, new Position(punto.getX(), punto.getY()));
    }

    protected Command pasa() {
        PaseData pase = getMejorPase();
        if (pase == null) {
            if (context.getSituacionPartido().isStarts() && context.getIteracionesSaco() > 1) {
                return irABalon();
            } else {
                return despeja();
            }
        } else {
            double fuerza = pase.getTrayectoriaPunto().getTrayectoria().getVelocidad() / Constants
                .getVelocidadRemate(getEstadisticas().getRemate());
            return new CommandHitBall(
                    index,
                    new Position(pase.getPuntoDestino().getX(), pase.getPuntoDestino().getY()),
                    fuerza,
                    pase.getTrayectoriaPunto().getTrayectoria().getAnguloVertical());
        }
    }

    protected CommandHitBall tira(Punto destino) {
        TrayectoriaPunto candidato = TrayectoriaManager.getInstance().getDisparosByDistancia(
                TrigonometriaUtils
                    .getDistancia(context.getBalon().getPosicion().getPuntoXY(), destino));

        double fuerza = candidato.getTrayectoria().getVelocidad() / Constants.getVelocidadRemate(getEstadisticas().getRemate());
        return new CommandHitBall(
                index,
                new Position(destino.getX(), destino.getY()),
                fuerza,
                candidato.getTrayectoria().getAnguloVertical());
    }

    /**
     * Indica si el jugador es el mas cercano.
     * @return mas cercano.
     */
    public boolean isJugadorMasCercano() {
        if (context.getSituacionPartido().isStarts()) {
            return context.getJugadoresPropiosCercanosBalon().get(0) == this;
        } else {
            JugadoresBalonData iteraciones = context.getJugadoresCercanosBalonData();
            return iteraciones != null && iteraciones.getPropio() != null && iteraciones.getPropio().getJugador() == this;
        }
    }

    /**
     * Devuelve el mejor pase.
     * @return mejor pase.
     */
    protected final PaseData getMejorPase() {
        Map<MSGJugadorPropio, PaseData> pases = context.getJugadoresPropiosDesmarques();
        if (pases == null || pases.isEmpty()) {
            return null;
        }
        List<PaseData> totalPases = new LinkedList<PaseData>();
        for (Map.Entry<MSGJugadorPropio, PaseData> pase : pases.entrySet()) {
            totalPases.add(pase.getValue());
        }
        List<Comparator<PaseData>> comparators = Arrays.<Comparator<PaseData>>asList(new MSGSituacionPartidoContext.AlturaComparator());
        for (Comparator<PaseData> comparator : comparators) {
            totalPases = MSGUtils.filter(totalPases, comparator, 0.99D);
        }
        MSGSituacionPartidoUIListener.getInstance().segmentosEvaluarPase.clear();
        for (PaseData pase : totalPases) {
            MSGSituacionPartidoUIListener.getInstance().segmentosEvaluarPase.add(new Segmento(this.posicion, pase.getPuntoDestino().getPuntoXY()));
        }
        return totalPases.get(0);
    }

    /**
     * Tiro a puerta.
     * @return retorna el comando de tiro a puerta.
     */
    protected CommandHitBall tiroAPuerta() {
        double distancia = TrigonometriaUtils.getDistancia(posicion, new Punto(Constants.centroArcoSup));
        TrayectoriaPunto pase = TrayectoriaManager.getInstance().getDisparosByDistancia(distancia);
        double fuerza = pase.getTrayectoria().getVelocidad() / Constants.getVelocidadRemate(getEstadisticas().getRemate());
        return new CommandHitBall(
                index,
                Constants.centroArcoSup,
                fuerza,
                pase.getTrayectoria().getAnguloVertical());
    }

    protected Command despeja() {
        List<MSGJugadorPropio> jugadoresADespejar = MSGUtils.getJugadorADespejar(this, context.getJugadoresPropios());
        if (jugadoresADespejar.isEmpty()) {
            return tiroAPuerta();
        }
        Random random = new Random(System.identityHashCode(System.currentTimeMillis()));
        MSGJugadorPropio jugador = jugadoresADespejar.get(random.nextInt(jugadoresADespejar.size()));

        return new CommandHitBall(
                index,
                new Position(jugador.getPosicion().getX(), jugador.getPosicion().getY()),
                1,
                true);

    }

    protected Command desmarque() {
        PaseData pase = context.getJugadoresPropiosDesmarques().get(this);
        Punto3D desmarque;
        if (pase == null) {
            Punto3D posicionBalon = context.getJugadoresCercanosBalonData().getPropio().getPunto();
            desmarque = desmarqueCercano(posicionBalon);
        } else {
            desmarque = pase.getPuntoDestino();
        }
        return desmarque == null ? null : new CommandMoveTo(index, new Position(desmarque.getX(), desmarque.getY()));
    }

    private Punto3D desmarqueCercano(Punto3D posicionBalon) {
        Punto3D puntoIra = null;
        if (!getAlineacion().getPosicion().equals(posicionBalon.getPuntoXY())) {
            Segmento
                segmento = new Segmento(getAlineacion().getPosicion(), posicionBalon.getPuntoXY());

            for (Punto p : TrigonometriaUtils.getInterseccion(segmento, getAlineacion().getArea())) {
                if (puntoIra == null) {
                    puntoIra = new Punto3D(p.getX(), p.getY(), 0);
                } else {
                    double d1 = TrigonometriaUtils.getDistancia(puntoIra.getPuntoXY(), posicionBalon.getPuntoXY());
                    double d2 = TrigonometriaUtils.getDistancia(p, posicionBalon.getPuntoXY());
                    if (d2 < d1) {
                        puntoIra = new Punto3D(p.getX(), p.getY(), 0);
                    }
                }
            }
        } else {
            puntoIra = context.getJugadoresCercanosBalonData().getPropio().getPunto();
        }
        return puntoIra;

    }

    protected Command cubreContrario() {
        Command comando = null;
        MSGJugadorDetalle jugadorAMarcar = context.getJugadoresPropiosMarcajes().get(this);
        if (jugadorAMarcar.getPosicion().getY() <= context.getBalon().getPosicion().getY()) {
            Segmento tiroAPuerta = new Segmento(jugadorAMarcar.getPosicion(), new Punto(Constants.centroArcoInf));
            Punto[] puntoMarcaje = TrigonometriaUtils.getInterseccion(tiroAPuerta, jugadorAMarcar.getAreaCubierta(5));
            Punto punto = jugadorAMarcar.getPosicion();
            for (Punto p : puntoMarcaje) {
                if (punto == null || p.getY() < punto.getY()) {
                    punto = p;
                }
            }
            comando = new CommandMoveTo(index, new Position(punto.getX(), punto.getY()));
            MSGSituacionPartidoUIListener.getInstance().segmentosMarcaje.add(new Segmento(getPosicion(), punto));
        }
        return comando;
    }
}
