package org.dsaw.javacup.tactics.jvc2013.elepanderevo;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ElepanderEvo implements Tactic {

    Position alineacion1[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-22.11188811188811, -22.805429864253394),
        new Position(0.2595419847328244, -31.082089552238806),
        new Position(21.636363636363637, -23.042986425339365),
        new Position(8.797202797202797, -4.98868778280543),
        new Position(-9.272727272727272, -5.463800904977376),
        new Position(-25.44055944055944, 6.651583710407239),
        new Position(23.062937062937063, 6.176470588235294),
        new Position(-14.274809160305344, 30.559701492537314),
        new Position(-0.951048951048951, 23.993212669683256),
        new Position(12.717557251908397, 29.51492537313433)
    };
    Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(22.825174825174827, -22.092760180995477),
        new Position(-25.916083916083913, -21.142533936651585),
        new Position(15.216783216783217, -11.877828054298643),
        new Position(-2.8531468531468533, -14.728506787330318),
        new Position(9.272727272727272, -3.088235294117647),
        new Position(-19.97202797202797, -8.552036199095022),
        new Position(-0.23776223776223776, -0.47511312217194573),
        new Position(-3.5664335664335667, -1.6628959276018098)
    };
    Position alineacion3[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(24.727272727272727, -19.004524886877828),
        new Position(-29.32824427480916, -21.67910447761194),
        new Position(-0.23776223776223776, -19.95475113122172),
        new Position(-20.20979020979021, -11.402714932126697),
        new Position(19.734265734265733, -8.552036199095022),
        new Position(-0.4755244755244755, -9.739819004524888),
        new Position(-19.020979020979023, -0.47511312217194573),
        new Position(13.552447552447552, -0.23755656108597287)
    };
    Position alineacion4[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(11.16030534351145, -1.3059701492537314),
        new Position(-10.641221374045802, -0.7835820895522387),
        new Position(-27.251908396946565, 31.6044776119403),
        new Position(-10.641221374045802, 30.559701492537314),
        new Position(9.603053435114505, 28.992537313432837),
        new Position(25.69465648854962, 28.992537313432837)
    };
    Position alineacion5[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(5.969465648854961, -5.485074626865671),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(22.580152671755727, -1.3059701492537314)
    };
    Position alineacion6[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(6.4885496183206115, -6.529850746268657),
        new Position(-6.4885496183206115, -6.529850746268657),
        new Position(22.580152671755727, -1.3059701492537314)
    };

    class TacticaDetalleImpl implements TacticDetail {

        public String getTacticName() {
            return "ElepanderEvo";
        }

        public CountryCode getCountry() {
            return CountryCode.ES;
        }

        public String getCoach() {
            return "Señor Burns";
        }

        public Color getShirtColor() {
            return new Color(0, 0, 255);
        }

        public Color getShortsColor() {
            return new Color(238, 238, 238);
        }

        public Color getShirtLineColor() {
            return new Color(102, 204, 255);
        }

        public Color getSocksColor() {
            return new Color(153, 204, 255);
        }

        public Color getGoalKeeper() {
            return new Color(255, 0, 255);
        }

        public UniformStyle getStyle() {
            return UniformStyle.FRANJA_DIAGONAL;
        }

        public Color getShirtColor2() {
            return new Color(77, 137, 159);
        }

        public Color getShortsColor2() {
            return new Color(11, 46, 184);
        }

        public Color getShirtLineColor2() {
            return new Color(244, 246, 55);
        }

        public Color getSocksColor2() {
            return new Color(52, 224, 161);
        }

        public Color getGoalKeeper2() {
            return new Color(78, 69, 117);
        }

        public UniformStyle getStyle2() {
            return UniformStyle.FRANJA_HORIZONTAL;
        }

        class JugadorImpl implements PlayerDetail {

            String nombre;
            int numero;
            Color piel, pelo;
            double velocidad, remate, presicion;
            boolean portero;
            Position posicion;

            public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                    double velocidad, double remate, double presicion, boolean portero) {
                this.nombre = nombre;
                this.numero = numero;
                this.piel = piel;
                this.pelo = pelo;
                this.velocidad = velocidad;
                this.remate = remate;
                this.presicion = presicion;
                this.portero = portero;
            }

            public String getPlayerName() {
                return nombre;
            }

            public Color getSkinColor() {
                return piel;
            }

            public Color getHairColor() {
                return pelo;
            }

            public int getNumber() {
                return numero;
            }

            public boolean isGoalKeeper() {
                return portero;
            }

            public double getSpeed() {
                return velocidad;
            }

            public double getPower() {
                return remate;
            }

            public double getPrecision() {
                return presicion;
            }
        }

        public PlayerDetail[] getPlayers() {
            return new PlayerDetail[]{
                        new JugadorImpl("Juan", 1, new Color(255, 200, 150), new Color(50, 0, 0), 0.94d, 0.89d, 0.98d, true),
                        new JugadorImpl("Alvaro", 2, new Color(255, 200, 150), new Color(50, 0, 0), 0.9d, 0.89d, 0.89d, false),
                        new JugadorImpl("Alberto", 3, new Color(255, 200, 150), new Color(50, 0, 0), 0.84d, 0.85d, 0.85d, false),
                        new JugadorImpl("FranFerri", 4, new Color(255, 200, 150), new Color(50, 0, 0), 0.75d, 0.74d, 0.74d, false),
                        new JugadorImpl("Jose", 5, new Color(255, 200, 150), new Color(50, 0, 0), 0.78d, 0.78d, 0.79d, false),
                        new JugadorImpl("Roberto", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.82d, 0.83d, 0.84d, false),
                        new JugadorImpl("Alex", 7, new Color(255, 200, 150), new Color(50, 0, 0), 0.78d, 0.77d, 0.76d, false),
                        new JugadorImpl("Miguel", 8, new Color(255, 200, 150), new Color(50, 0, 0), 0.81d, 0.8d, 0.81d, false),
                        new JugadorImpl("Paco", 9, new Color(255, 200, 150), new Color(50, 0, 0), 0.82d, 0.83d, 0.85d, false),
                        new JugadorImpl("Ruben", 10, new Color(255, 200, 150), new Color(50, 0, 0), 0.85d, 0.85d, 0.85d, false),
                        new JugadorImpl("Angel", 11, new Color(255, 200, 150), new Color(50, 0, 0), 0.84d, 0.84d, 0.94d, false)
                    };
        }
    }
    TacticDetail detalle = new TacticaDetalleImpl();

    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
        return alineacion2;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion3;
    }
    public final static int NUM_JUGADORES = 11;
    public final static int DISTANCIA_ROBO_BALON = 6;

    public double distanciaRivalMasCercano(Position miPosicion, GameSituations sp) {
        final Position[] posicionRivales = sp.rivalPlayers();
        double minimaDistancia = 500;
        double distancia;
        for (Position posicionRival : posicionRivales) {
            distancia = miPosicion.distance(posicionRival);
            if (minimaDistancia > distancia) {
                minimaDistancia = distancia;
            }
        }
        return minimaDistancia;
    }

    public Position posicionRivalMasCercano(Position miPosicion, GameSituations sp) {
        final Position[] posicionRivales = sp.rivalPlayers();
        double minimaDistancia = 500;
        double distancia;
        Position posicionRivalMasCercano = new Position();
        for (Position posicionRival : posicionRivales) {
            distancia = miPosicion.distance(posicionRival);
            if (minimaDistancia > distancia) {
                minimaDistancia = distancia;
                posicionRivalMasCercano = posicionRival;
            }
        }
        return posicionRivalMasCercano;
    }

    /**
     *
     * @param miPosicion
     * @param sp
     * @param nivel 0 busca el jugador más cercano, 1 busca el siguiente más cercano
     * @return
     */
    public Position posicionRivalMasNivelCercania(Position miPosicion, GameSituations sp, int nivel) {
        final Position[] posicionRivales = sp.rivalPlayers();
        List<Position> arrayPosicionesJugadoresRivalesPorCercania = new ArrayList();
        double minimaDistancia = 500;
        double distancia;
        Position posicionRivalMasCercano = new Position();
        Position posicionRivalMasCercano2 = new Position();
        for (Position posicionRival : posicionRivales) {
            distancia = miPosicion.distance(posicionRival);
            if (minimaDistancia > distancia) {
                minimaDistancia = distancia;
                posicionRivalMasCercano2.setPosition(posicionRivalMasCercano.getX(), posicionRivalMasCercano.getY());
                posicionRivalMasCercano = posicionRival;
                arrayPosicionesJugadoresRivalesPorCercania.add(posicionRivalMasCercano);
            }
        }
        return arrayPosicionesJugadoresRivalesPorCercania.get(nivel);
    }

    public int[] obtenerDelanterosRivalesCubiertos(GameSituations sp) {

        int[] listaJugadoresCubiertos = new int[11];
        inicializarListaACero(listaJugadoresCubiertos);

        final Position[] posicionmisJugadores = sp.myPlayers();
        final Position[] posicionRivales = sp.rivalPlayers();

        PlayerDetail[] detalleJugadoresRivales = sp.rivalPlayersDetail();
        for (PlayerDetail detalleJugadorRival : detalleJugadoresRivales) {
            int numero = detalleJugadorRival.getNumber();
            if (detalleJugadorRival.isGoalKeeper()) {
                if (numero >= 0 && numero < 11) {
                    listaJugadoresCubiertos[numero] = 1;//Error detalleJugadorRival.getNumber() puede ser 20
                }
            } else if (numero > 0 && numero < 11 && rivalEstaCubierto(posicionRivales[numero - 1], posicionmisJugadores)) {
                listaJugadoresCubiertos[numero - 1]++;//Error detalleJugadorRival.getNumber() puede ser 20
            }
        }
        return listaJugadoresCubiertos;
    }

    private void inicializarListaACero(int[] listaJugadoresCubiertos) {
        for (int i = 0; i < NUM_JUGADORES; i++) {
            listaJugadoresCubiertos[i] = 0;
        }
    }

    private boolean rivalEstaCubierto(Position posicionRival, Position[] posicionMisJugadores) {
        for (Position miPosicion : posicionMisJugadores) {
            if ((posicionRival.getX() - DISTANCIA_ROBO_BALON <= miPosicion.getX())
                    && (miPosicion.getX() <= posicionRival.getX() + DISTANCIA_ROBO_BALON)
                    && (posicionRival.getY() - DISTANCIA_ROBO_BALON <= miPosicion.getY())
                    && (miPosicion.getY() <= posicionRival.getY() + DISTANCIA_ROBO_BALON)) {
                return true;
            }
        }
        return false;
    }

    public boolean rivalEstaCubiertoPorMi(Position posicionRival, Position miPosicion) {
        if ((posicionRival.getX() - Constants.DISTANCIA_CONTROL_BALON <= miPosicion.getX())
                && (miPosicion.getX() <= posicionRival.getX() + Constants.DISTANCIA_CONTROL_BALON)
                && (posicionRival.getY() - Constants.DISTANCIA_CONTROL_BALON <= miPosicion.getY())
                && (miPosicion.getY() <= posicionRival.getY() + Constants.DISTANCIA_CONTROL_BALON)) {
            return true;
        }
        return false;
    }

    public boolean rivalEstaCubiertoSoloPorMi(Position posicionRival, Position[] misPosiciones, int i) {
        int jugadoresCubriendo = 0;
        boolean yoCubro = false;
        for (int jugador = 0; jugador < misPosiciones.length; jugador++) {
            if (rivalEstaCubiertoPorMi(posicionRival, misPosiciones[i])) {
                if (jugador == i) {
                    yoCubro = true;
                }
                jugadoresCubriendo++;
            }
            if (jugadoresCubriendo > 1) {
                return false;
            }
        }
        return jugadoresCubriendo == 1 && yoCubro;
    }

    public boolean rivalEstaCubiertoPorVariosIncluidoYo(Position posicionRival, Position[] misPosiciones, int i) {
        int jugadoresCubriendo = 0;
        boolean yoCubro = false;
        for (int jugador = 0; jugador < misPosiciones.length; jugador++) {
            if (rivalEstaCubiertoPorMi(posicionRival, misPosiciones[i])) {
                if (jugador == i) {
                    yoCubro = true;
                }
                jugadoresCubriendo++;
            }
            if (jugadoresCubriendo > 1 && yoCubro) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve true si he sobrepasado la vertical a 30 metros de la porteria
     * @param miPosicion
     * @param sp
     * @return
     */
    public boolean estoyEnDisposicionDeRematar(Position miPosicion, GameSituations sp) {
        int numeroPorteroRival = numeroPorteroRival(sp);
        if ((sp.rivalPlayers()[numeroPorteroRival].getY() - 30 <= miPosicion.getY())
                && (miPosicion.getY() <= sp.rivalPlayers()[numeroPorteroRival].getY() + 30)) {
            return true;
        }
        return false;

    }

    /**
     * Devuelve el numero del portero rival
     * @param sp
     * @return
     */
    public int numeroPorteroRival(GameSituations sp) {
        int numeroPorteroRival = 0;
        for (int i = 0; i < sp.rivalPlayersDetail().length; i++) {
            if (sp.rivalPlayersDetail()[i].isGoalKeeper()) {
                numeroPorteroRival = i;
            }
        }
        return numeroPorteroRival;
    }

    public int buscarJugadorOptimo(int i, GameSituations sp) {

        //jugador mas cercano adelantado que el
        Random r = new Random();
        return (r.nextInt(5)) + 6;
    }

    public boolean estoyCubierto(Position miPosicion, GameSituations sp) {
        for (int i = 1; i <= NUM_JUGADORES; i++) {
            if (rivalEstaCubierto(miPosicion, sp.rivalPlayers())) {
                return true;
            }
        }
        return false;
    }

    public Position getTrayectoriaPorteriaContraria(Position miPosicion, GameSituations sp) {
        int numeroPorteroRival = numeroPorteroRival(sp);
        return Position.medium(sp.rivalPlayers()[numeroPorteroRival], miPosicion);
    }
    LinkedList<Command> comandos = new LinkedList<Command>();

    @Override
    public List<Command> execute(GameSituations sp) {

        int[] indiceRivalesCubiertos = obtenerDelanterosRivalesCubiertos(sp);
        List rivalesCubiertos = new ArrayList();
        List nuestrosCubriendo = new ArrayList();
        PlayerDetail[] misJugadoresDetalle = sp.myPlayersDetail();
        comandos.clear();
        //Obtiene las posiciones de tus jugadores
        Position[] posicionJugadores = sp.myPlayers();
        for (int i = 0; i < NUM_JUGADORES; i++) {
            //Ordena a cada jugador que se ubique segun la alineacion1
            if (misJugadoresDetalle[i].isGoalKeeper()) {
                comandos.add(new CommandMoveTo(i, alineacion1[i]));
            }
            //Soy defensa
            if ((i <= 4) && (i > 1)) {
                int j = 0;
                boolean cubriendoRival = false;
                for (Position rival : sp.rivalPlayers()) {

                    // si el rival está cubierto solo por mí
                    if (rivalEstaCubiertoSoloPorMi(rival, posicionJugadores, i)) {
                        // sigo cubriendole ---- moverme a su posicion
                        rivalesCubiertos.add(sp.rivalPlayersDetail()[j]);
                        nuestrosCubriendo.add(sp.myPlayersDetail()[i]);
                        cubriendoRival = true;
                        comandos.add(new CommandMoveTo(i, new Position(rival.getX(), (rival.getY() + 10))));
                    } // si el rival está cubierto por mí y por más jugadores míos
                    else if (rivalEstaCubiertoPorVariosIncluidoYo(rival, posicionJugadores, i)
                            && !rivalesCubiertos.contains(rival)) {
                        // ver si soy el que peor cubre y cubrirle (el primero en evaluar)
                        rivalesCubiertos.add(rival);
                        nuestrosCubriendo.add(sp.myPlayersDetail()[i]);
                        cubriendoRival = true;
                        comandos.add(new CommandMoveTo(i, new Position(rival.getX(), (rival.getY() + 10))));

                    }
                    j++;
                }
                // buscar contrario no cubierto mas cercano --- y me voy a el
                // marco a ese jugador rival como ya cubierto
                if (!cubriendoRival) {
                    comandos.add(new CommandMoveTo(i, posicionRivalMasCercano(posicionJugadores[i], sp)));
                }
            } else {
                comandos.add(new CommandMoveTo(i, alineacion1[i]));
            }
        }
        if (!sp.isRivalStarts()) {
            int[] recuperadores = sp.getRecoveryBall();
            if (recuperadores.length > 1) {
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
                for (int i = 1; i < recuperadores.length; i++) {
                    comandos.add(new CommandMoveTo(recuperadores[1], //TODO: abarranco: va solo 1 de los jugadores, el mas cercano
                            new Position(posRecuperacion[0], posRecuperacion[1])));
                }
            }
        }
        Random r = new Random();
        for (int i : sp.canKick()) {
            //Si el jugador es de indice 8 o 10
            if (i == 0) { //Soy el portero
                comandos.add(new CommandHitBall(i, alineacion1[1], 1, 12 + Constants.ANGULO_VERTICAL_MAX));
            }
            if (estoyEnDisposicionDeRematar(posicionJugadores[i], sp)) {
                comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));
            } else {
                int jugadorDestino;
                if (estoyCubierto(posicionJugadores[i], sp) || i == 0) {
                    jugadorDestino = buscarJugadorOptimo(i, sp);
                    comandos.add(new CommandHitBall(i, posicionJugadores[jugadorDestino], 1, 15));
                } else {
                    Position trayectoriaPorteriaContraria = getTrayectoriaPorteriaContraria(posicionJugadores[i], sp);
                    comandos.add(new CommandHitBall(i, trayectoriaPorteriaContraria, 0.42, false));

                }

            }
        }

        Position trayectoriaParaJugador6 = getTrayectoriaPorteriaContraria(posicionJugadores[5], sp);
        comandos.add(new CommandMoveTo(6, trayectoriaParaJugador6));
        Position trayectoriaParaJugador4 = getTrayectoriaPorteriaContraria(sp.ballPosition(), sp);
        comandos.add(new CommandMoveTo(4, trayectoriaParaJugador4));
        return comandos;
    }
}
