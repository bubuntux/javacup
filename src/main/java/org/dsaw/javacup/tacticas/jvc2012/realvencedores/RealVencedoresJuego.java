package org.dsaw.javacup.tacticas.jvc2012.realvencedores;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import org.dsaw.javacup.tacticas.jvc2012.realvencedores.equipo.Distribucion;
import org.dsaw.javacup.tacticas.jvc2012.realvencedores.equipo.EquipoImpl;

/**
 * Tactica del Equipo Real Vencedores.<br/>
 * 
 * <p>
 * Entrenador: Mi hijo
 * </p>
 * 
 * @author Jose Roman Martin Gil (jromanmartin@gmail.com)
 */
public class RealVencedoresJuego implements Tactic {

    /** Posiciones del equipo */
    Distribucion distribuciones = new Distribucion();

    /** Equipo */
    EquipoImpl equipo = new EquipoImpl();

    public TacticDetail getDetail() {
        return this.equipo;
    }

    public Position[] getStartPositions(GameSituations sp) {
        return this.distribuciones.getInicioSacando();
    }

    public Position[] getNoStartPositions(GameSituations sp) {
        return this.distribuciones.getInicioRecibiendo();
    }

    /**
     * Ejecución de la iteración de comandos a ejecutar según la situación del partido.
     * 
     * @see org.dsaw.javacup.model.Tactic#execute(org.dsaw.javacup.model.engine.GameSituations)
     */
    public List<Command> execute(GameSituations sp) {
        // Lista de comandos
        LinkedList<Command> comandos = new LinkedList<>();

        // Distribución normal de juego
        Position[] distribucionNormal = this.distribuciones.getNormal();

        // Posiciones de mis jugadores
        Position[] jugadores = sp.myPlayers();
        // Posiciones de los rivales
        Position[] rivales = sp.rivalPlayers();
        // Rivales en posición de remate
        int[] rivalesEnRemate = sp.rivalCanKick();
        boolean hayRivalesEnRemate = (rivalesEnRemate.length > 1);
        // Rivales cubiertos por defensas
        Position[] rivalesCubiertos = new Position[rivalesEnRemate.length];

        // TACTICA DE DEFENSA
        // Colocar jugadores según distribución normal y cubrir a los posibles rematadores
        for (int i = 0; i < jugadores.length; i++) {
            // Defensas a cubrir a jugadores
            if (i != 0 && i < 4) { // Solo defensas
                Position miJugador = jugadores[i];
                if (hayRivalesEnRemate) {
                    // Identificar posición de rival contrario y ver si lo puede cubrir mi jugador
                    boolean jugadorEstaCubriendo = false;
                    for (int rer = 0; rer < rivalesEnRemate.length && !jugadorEstaCubriendo; rer++) {
                        Position rival = rivales[rer];
                        if (!rivalCubierto(rivalesCubiertos, rival) && !miJugadorCubreRival(miJugador, rival)) {
                            rivalesCubiertos[rer] = rival;
                            comandos.add(new CommandMoveTo(i, new Position(rival.getX(), (rival.getY() + 5))));
                            // Mi jugador ya está cubriendo a este contrario
                            jugadorEstaCubriendo = true;
                        }
                    }
                }
            } else {
                // Resto en posición normal
                comandos.add(new CommandMoveTo(i, distribucionNormal[i]));
            }
        }

        // TACTICA DE RECUPERACIÓN DE BALÓN

        // Si no saca el rival
        if (!sp.isRivalStarts()) {
            // Obtiene los datos de recuperacion del balon
            int[] recuperadores = sp.getRecoveryBall();
            // Si existe posibilidad de recuperar el balon
            if (recuperadores.length > 1) {
                // Obtiene las coordenadas del balon en el instante donde se puede recuperar el balon
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
                // Indicamos a los dos jugadores más cercanos, si los hay, que se ubiquen en la posición de recuperación
                comandos.add(new CommandMoveTo(recuperadores[1], new Position(posRecuperacion[0], posRecuperacion[1])));
                if (recuperadores.length > 3) {
                    comandos.add(new CommandMoveTo(recuperadores[2], new Position(posRecuperacion[0], posRecuperacion[1])));
                }
            }
        }

        // TACTICA DE TIKI-TAKA Y ATAQUE

        // Instancia un generador aleatorio
        Random r = new Random();

        // Recorre la lista de mis jugadores que pueden rematar
        for (int i : sp.canKick()) {
            // Si los jugadores son delanteros, chutarán a portería
            if (i >= 8) {
                // Posición destino del chute
                Position destino = null;
                // condicion aleatoria
                if (r.nextBoolean()) {
                    // Ordena que debe rematar al centro del arco
                    destino = Constants.centroArcoSup;
                } else if (r.nextBoolean()) {
                    // Ordena que debe rematar al poste derecho
                    destino = Constants.posteDerArcoSup;
                } else {
                    // Ordena que debe rematar al poste izquierdo
                    destino = Constants.posteIzqArcoSup;
                }

                // Ordenamos el remate
                comandos.add(new CommandHitBall(i, destino, 1, 5 + r.nextInt(15)));
            } else {
                // inicia contador en cero
                int count = 0;
                int jugadorDestino;
                // Repetir mientras el jugador destino sea igual al jugador que remata
                while (((jugadorDestino = r.nextInt(11)) == i
                // o mientras la coordenada y del jugador que remata
                // es mayor que la coordenada y del que recibe
                        || jugadores[i].getY() > jugadores[jugadorDestino].getY())
                        // Y mientras el contador es menor a 20
                        && count < 20) {
                    // incrementa el contador
                    count++;
                }
                // Si el receptor del balon es el que remata
                if (i == jugadorDestino) {
                    while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
                    }
                }
                // Ordena que el jugador que puede rematar que de un pase al jugador destino
                comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, r.nextInt(45)));
            }
        }

        // Retorna la lista de comandos
        return comandos;
    }

    /* ********************************* Métodos privados auxiliares ********************************* */

    /**
     * @param rivalesCubiertos
     *            Lista de rivales cubiertos por mis jugadores
     * @param rival
     *            Rival a evaluar
     * 
     * @return <code>true</code> si el rival ya está cubierto por un jugador mio
     */
    private boolean rivalCubierto(Position[] rivalesCubiertos, Position rival) {
        for (Position rivalCubierto : rivalesCubiertos) {
            if (rival.equals(rivalCubierto)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evaluamos si mi jugador es en posición de cubrir al rival
     * 
     * @param miJugador
     *            Posición de mi jugador
     * @param rival
     *            Posición del jugador rival
     * 
     * @return <code>true</code> si mi jugador cubre al rival
     */
    private boolean miJugadorCubreRival(Position miJugador, Position rival) {
        if ((rival.getX() - Constants.DISTANCIA_CONTROL_BALON <= miJugador.getX())
                && (miJugador.getX() <= rival.getX() + Constants.DISTANCIA_CONTROL_BALON)
                && (rival.getY() - Constants.DISTANCIA_CONTROL_BALON <= miJugador.getY())
                && (miJugador.getY() <= rival.getY() + Constants.DISTANCIA_CONTROL_BALON)) {
            return true;
        }

        return false;
    }

}