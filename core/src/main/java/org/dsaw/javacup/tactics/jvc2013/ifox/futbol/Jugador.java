/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.futbol;

import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.MaquinaEstado;
import org.dsaw.javacup.tactics.jvc2013.ifox.base.Telegrama;
import org.dsaw.javacup.tactics.jvc2013.ifox.estado.Esperando;
import org.dsaw.javacup.tactics.jvc2013.ifox.estado.Global;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Parametros;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Region;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Transformacion;
import org.dsaw.javacup.tactics.jvc2013.ifox.util.Vector2d;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Jugador {

    private MaquinaEstado maquinaEstado;
    private Equipo equipo;
    private int id;
    private Rol rol;
    private Position posicionPase;

    public Jugador(Equipo equipo, int id, Rol rol) {
        this.equipo = equipo;
        this.id = id;
        this.rol = rol;
        
        maquinaEstado = new MaquinaEstado<Jugador>(this);
        maquinaEstado.setEstadoActual(Esperando.getInstance());
        maquinaEstado.setEstadoGlobal(Global.getInstance());
    }

    public void actualizar() {
        if (maquinaEstado != null) {
            maquinaEstado.actualizar();
        }
    }

    public void recibirMensaje(Telegrama telegrama) {
        if (maquinaEstado != null) {
            maquinaEstado.manejarMensaje(telegrama);
        }
    }

    public void recuperarBalon() {
        int[] recuperadores = equipo.getSituacion().getRecoveryBall();
        if (recuperadores.length > 1) {
            if (recuperadores[1] == id) {
                double[] posicionRecuperacion = equipo.getSituacion()
                        .getTrajectory(recuperadores[0]);
                irAObjetivo(new Position(posicionRecuperacion[0],
                        posicionRecuperacion[1]));
            }
        }
    }

    public void golpearBalon(Position objetivo, boolean alto) {
        double fuerza = Util.fuerzaDisparo(this, objetivo);
        if (equipo.getSituacion().isStarts()) {
            fuerza = 1.0;
        }
        equipo.getComandos().add(new CommandHitBall(id, objetivo, fuerza,
                alto));
    }

    public void regatear() {
        if (!equipo.getSituacion().myPlayersDetail()[id].isGoalKeeper()) {
            //equipo.getComandos().add(new CommandHitBall(id));
            Vector2d direccion = new Vector2d(evitarOponente());
            Vector2d posicion = new Vector2d(getPosicion());
            if (!posicion.suma(direccion).toPosicion().isInsideGameField(-5)) {
                direccion = direccion.perpendicular().inverso();
            }
            equipo.getComandos().add(new CommandHitBall(id,
                    posicion.suma(direccion).productoEscalar(0.8).toPosicion(), 0.4, false));
        }
    }

    public Position evitarOponente() {
        double longitudVista = 20.0;
        Position[] posicionOponentes = equipo.getSituacion().rivalPlayers();
        List<Position> posicionAmenazantes = new ArrayList<Position>();
        for (int i = 0; i < posicionOponentes.length; i++) {
            if (getPosicion().norm(posicionOponentes[i])
                    < longitudVista * longitudVista) {
                posicionAmenazantes.add(posicionOponentes[i]);
            }
        }

        Position cib = null;
        double distanciaACib = Double.MAX_VALUE;
        Vector2d posicionLocalCib = null;
        Vector2d posicion = new Vector2d(getPosicion());
        Vector2d haciaAdelanteNormal = new Vector2d(0.0, 1.0);

        for (Position obstaculo : posicionAmenazantes) {
            Vector2d posicionLocal = Transformacion.puntoAEspacioLocal(
                new Vector2d(obstaculo), haciaAdelanteNormal,
                haciaAdelanteNormal.perpendicular(), posicion);
            if (posicionLocal.x >= 0) {
                double radioExpandido = 10.0;
                if (Math.abs(posicionLocal.y) < radioExpandido) {
                    double cx = posicionLocal.x;
                    double cy = posicionLocal.y;
                    double raiz = Math.sqrt(radioExpandido * radioExpandido - cy * cy);
                    double ip = cx - raiz;
                    if (ip <= 0) {
                        ip = cx + raiz;
                    }
                    if (ip < distanciaACib) {
                        distanciaACib = ip;
                        cib = obstaculo;
                        posicionLocalCib = posicionLocal;
                    }
                }
            }
        }

        Vector2d fuerza = new Vector2d();
        double freno = 0.8;
        if (cib != null) {
            double multiplicador = 1.0 + (longitudVista - posicionLocalCib.x)
                    / longitudVista;
            fuerza.y = (1.0 - posicionLocalCib.y) * multiplicador;
            fuerza.x = (1.0 - posicionLocalCib.x) * freno;
        }

        fuerza = Transformacion.vectorAEspacioGlobal(fuerza, haciaAdelanteNormal,
                haciaAdelanteNormal.perpendicular());
        return fuerza.normal().toPosicion();
    }
    
    public void irAObjetivo(Position posicion) {
        equipo.getComandos().add(new CommandMoveTo(id, posicion));
    }

    public Position cubrir() {
        int idRival = getPosicion().nearestIndex(equipo.getSituacion().rivalPlayers());
        Position posicion = equipo.getSituacion().rivalPlayers()[idRival];
        return posicion;
    }

    public void buscarAyuda() {
        Jugador jugador = equipo.getMejorJugadorDeAyuda();
        equipo.setJugadorAyudante(jugador);
    }

    public boolean isJugadorAyudante() {
        return this.equals(equipo.getJugadorAyudante());
    }

    public boolean isEnRegionAlineacion(Position posicion) {
        Region region = null;
        Position posicionAlineacion = equipo.getAlineacion().getPosicion(id);
        if (rol == Rol.PORTERO) {
            double anchoRegion = Constants.ANCHO_AREA_GRANDE;
            double altoRegion = Constants.LARGO_AREA_GRANDE;
            region = new Region(anchoRegion, altoRegion, posicionAlineacion);
            return region.isDentro(posicion, Region.Tipo.MITAD);
        } else {
            double anchoRegion = Constants.ANCHO_CAMPO_JUEGO
                / Parametros.Equipo.REGIONES_ANCHO;
            double altoRegion = Constants.LARGO_CAMPO_JUEGO
                    / Parametros.Equipo.REGIONES_ALTO;
            region = new Region(anchoRegion, altoRegion, posicionAlineacion);
            return region.isDentro(posicion, Region.Tipo.NORMAL);
        }
    }

    public boolean isEnRegionOfensiva() {
        return Math.abs(Constants.centroArcoSup.getY() - getPosicion().getY())
                < Constants.DISTANCIA_PENAL * 3;
    }

    public boolean isAmenazado(double areaSegura) {
        Position[] oponentes = equipo.getSituacion().rivalPlayers();
        for (Position oponente : oponentes) {
            if (getPosicion().norm(oponente)
                    < areaSegura) {
                return true;
            }
        }
        return false;
    }

    public boolean isBalonEnRangoDeControl() {
        return equipo.getSituacion().ballPosition().norm(getPosicion())
                < Constants.DISTANCIA_CONTROL_BALON
                * Constants.DISTANCIA_CONTROL_BALON;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getId() {
        return id;
    }

    public MaquinaEstado getMaquinaEstado() {
        return maquinaEstado;
    }

    public double getMaxVelocidadRemate() {
        return Constants.getVelocidadRemate(equipo.getSituacion()
                .getMyPlayerPower(id));
    }

    public double getMaxVelocidad() {
        return Constants.getVelocidad(equipo.getSituacion()
                .getMyPlayerSpeed(id));
    }

    public Position getPosicion() {
        return equipo.getSituacion().myPlayers()[id];
    }

    public Rol getRol() {
        return rol;
    }

    public Position getPosicionPase() {
        return posicionPase;
    }

    public void setPosicionPase(Position posicionPase) {
        this.posicionPase = posicionPase;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "Jugador{" + "id=" + id + "rol=" + rol + '}';
    }

    public enum Rol {
        PORTERO,
        DEFENSOR,
        MEDIO,
        ATACANTE
    }
}
