package org.dsaw.javacup.tactics.jvc2013.Sag;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
//import pdsanchez.prbs.Network;

public class Sag implements Tactic {

    Position alineacion1[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-12.125874125874127, -35.8710407239819),
        new Position(0.4755244755244755, -33.257918552036195),
        new Position(12.125874125874127, -35.63348416289593),
        new Position(7.526717557251908, -11.753731343283583),
        new Position(-8.564885496183205, -11.753731343283583),
        new Position(-14.97902097902098, 12.828054298642533),
        new Position(7.846153846153847, 16.153846153846153),
        new Position(-20.923076923076923, 40.62217194570136),
        new Position(-5.230769230769231, 33.73303167420815),
        new Position(9.272727272727272, 35.63348416289593)
    };
    Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-8.083916083916083, -35.63348416289593),
        new Position(6.6573426573426575, -35.63348416289593),
        new Position(-1.4265734265734267, -19.95475113122172),
        new Position(-6.419580419580419, -3.088235294117647),
        new Position(8.564885496183205, -7.574626865671642),
        new Position(-7.846153846153847, 20.90497737556561),
        new Position(23.062937062937063, 20.90497737556561),
        new Position(-23.3006993006993, 10.927601809954751),
        new Position(14.027972027972028, 34.920814479638004),
        new Position(-6.895104895104895, 39.671945701357465)
    };
    Position alineacion3[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-4.5174825174825175, -26.606334841628957),
        new Position(1.6643356643356644, -26.84389140271493),
        new Position(13.076923076923078, -34.44570135746606),
        new Position(-14.74125874125874, -34.920814479638004),
        new Position(-0.23776223776223776, -9.502262443438914),
        new Position(-9.748251748251748, -17.816742081447966),
        new Position(12.125874125874127, -18.766968325791854),
        new Position(-6.6573426573426575, 17.57918552036199),
        new Position(3.804195804195804, 11.165158371040723),
        new Position(7.37062937062937, 29.457013574660635)
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
        new Position(0.23776223776223776, -31.59502262443439),
        new Position(11.888111888111888, -35.8710407239819),
        new Position(5.230769230769231, -16.153846153846153),
        new Position(-6.895104895104895, -16.866515837104075),
        new Position(-17.356643356643357, -8.076923076923077),
        new Position(12.125874125874127, -7.839366515837104),
        new Position(-13.076923076923078, -2.1380090497737556),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(3.804195804195804, -2.1380090497737556)
    };
    Position alineacion6[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-8.797202797202797, -36.10859728506787),
        new Position(-0.23776223776223776, -32.30769230769231),
        new Position(9.748251748251748, -35.63348416289593),
        new Position(5.706293706293707, -21.380090497737555),
        new Position(-6.419580419580419, -20.90497737556561),
        new Position(-17.594405594405593, -8.552036199095022),
        new Position(17.11888111888112, -8.789592760180994),
        new Position(6.4885496183206115, -6.529850746268657),
        new Position(-6.4885496183206115, -6.529850746268657),
        new Position(22.580152671755727, -1.3059701492537314)
    };

    public class TacticaDetalleImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "sagFC";
        }

        @Override
        public CountryCode getCountry() {
            return CountryCode.ES;
        }

        @Override
        public String getCoach() {
            return "YoMismo";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 255, 0);
        }

        @Override
        public Color getShortsColor() {
            return new Color(0, 0, 51);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getSocksColor() {
            return new Color(255, 255, 0);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(51, 51, 51);
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.SIN_ESTILO;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(0, 51, 255);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(102, 0, 153);
        }

        @Override
        public UniformStyle getStyle2() {
            return UniformStyle.SIN_ESTILO;
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

            @Override
            public String getPlayerName() {
                return nombre;
            }

            @Override
            public Color getSkinColor() {
                return piel;
            }

            @Override
            public Color getHairColor() {
                return pelo;
            }

            @Override
            public int getNumber() {
                return numero;
            }

            @Override
            public boolean isGoalKeeper() {
                return portero;
            }

            @Override
            public double getSpeed() {
                return velocidad;
            }

            @Override
            public double getPower() {
                return remate;
            }

            @Override
            public double getPrecision() {
                return presicion;
            }
        }

        @Override
        public PlayerDetail[] getPlayers() {
            return new PlayerDetail[]{
                        new JugadorImpl("Javi", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d, 0.5d, true),
                        new JugadorImpl("Berto", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.75d, false),
                        new JugadorImpl("Paudavy", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.75d, false),
                        new JugadorImpl("Jorge", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.54d, 0.74d, false),
                        new JugadorImpl("Raul", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.76d, false),
                        new JugadorImpl("Richi", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.99d, 0.59d, 0.8d, false),
                        new JugadorImpl("Martin", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.73d, 0.75d, false),
                        new JugadorImpl("Jose", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.65d, 0.75d, false),
                        new JugadorImpl("Patxi", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.92d, 0.95d, false),
                        new JugadorImpl("Chema", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
                        new JugadorImpl("Monty", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false)
                    };
        }
    }
    TacticDetail detalle = new TacticaDetalleImpl();

    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return alineacion5;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion6;
    }
    //Network network = new Network(8, 5, 3, 0.5, 0.7);
    //Lista de comandos
    private List<Command> comandos = new LinkedList<Command>();
    private Jugador[] jugadores;
    private Jugador[] rivales;

    @Override
    public List<Command> execute(GameSituations sp) {
        //Limpia la lista de comandos
        comandos.clear();

        // Goles
        int golesSag = sp.myGoals();
        int golesRival = sp.rivalGoals();

        // Posiciones de los jugadores
        Position[] posJugadoresSag = sp.myPlayers();
        Position[] posJugadoresRivales = sp.rivalPlayers();

        // Crear jugadores en la primera iteration
        if (sp.iteration() == 0) {
            // Jugadores sag
            PlayerDetail[] jdetalle = sp.myPlayersDetail();
            jugadores = new Jugador[jdetalle.length];
            for (int i = 0; i < jdetalle.length; i++) {
                Position posRef = alineacion3[i];
                if (jdetalle[i].isGoalKeeper()) {
                    jugadores[i] = new Portero(i, jdetalle[i], posRef);
                } // Determinar tipo segun la posicion de referencia
                else {
                    double y = posRef.getY();
                    if (y < -20) {
                        jugadores[i] = new Defensa(i, jdetalle[i], posRef);
                    } else if (y > 20) {
                        jugadores[i] = new Delantero(i, jdetalle[i], posRef);
                    } else {
                        jugadores[i] = new Central(i, jdetalle[i], posRef);
                    }
                }
                jugadores[i].setPosicionActual(posJugadoresSag[i]);
            } // Fin for

            // Jugadores rivalPlayers
            jdetalle = sp.rivalPlayersDetail();
            rivales = new Jugador[jdetalle.length];
            for (int i = 0; i < jdetalle.length; i++) {
                if (jdetalle[i].isGoalKeeper()) {
                    rivales[i] = new JRival(i, jdetalle[i], true);
                } else {
                    rivales[i] = new JRival(i, jdetalle[i]);
                }
                rivales[i].setPosicionActual(posJugadoresRivales[i]);
            } // Fin for

        } // Fin if: crear jugadores en la primera iteration



        // Actualizar datos jugadores y rivalPlayers
        for (int i = 0; i < posJugadoresRivales.length; i++) {
            rivales[i].update(sp, rivales, jugadores);
        }
        for (int i = 0; i < posJugadoresSag.length; i++) {
            jugadores[i].update(sp, jugadores, rivales);
        }



        // Establecer recuperadores/interceptores
        //----------------------------------------------------------------------

        // RECUPERADORES
        int[] rc = sp.getRecoveryBall();
        if (rc.length > 0) {
            int tBalon = rc[0];
            double[] puntoRecuperacion = sp.getTrajectory(tBalon);

            Position posRc = new Position(puntoRecuperacion[0], puntoRecuperacion[1]);
            int[] recuperadoresSag = posRc.nearestIndexes(posJugadoresSag);

            // Mis 2 recuperadores mas proximos
            int rc1 = recuperadoresSag[0];
            int rc2 = recuperadoresSag[1];

            // El recuperador rival mas proximo
            Jugador rival = rivales[posRc.nearestIndex(posJugadoresRivales)];

            // Evaluar recuperadores
            jugadores[rc1].evaluarRecuperacion(posRc, rival, jugadores[rc2]);


            // INTERCEPCION
            // Busco posibilidades de interceptar a partir de la iteration 1
            for (int i = 1; i < tBalon; i++) {
                double[] puntoIc = sp.getTrajectory(i);
                // Si la altura del ballPosition es correcta
                if (puntoIc[0] != puntoRecuperacion[0]
                        && puntoIc[1] != puntoRecuperacion[1]
                        && puntoIc[2] <= Constants.ALTURA_CONTROL_BALON) {
                    posRc = new Position(puntoIc[0], puntoIc[1]);
                    recuperadoresSag = posRc.nearestIndexes(posJugadoresSag);

                    jugadores[recuperadoresSag[0]].evaluarIntercepcion(posRc, jugadores[recuperadoresSag[1]], i);
                }
            } // Fin for: posibilidades de interceptar
        } // Fin if: existen recuperadores


        // GOLPEADORES: REMATADORES/PASADORES
        int[] golpeadoresSag = sp.canKick();
        int totalGolpeadoresSag = golpeadoresSag.length;
        if (totalGolpeadoresSag > 0) {
            int jRt = golpeadoresSag[0];
            // Si hay varios golpeadores me quedo con el que tenga mejor precision
            // o mejor disparo
            if (totalGolpeadoresSag > 1) {
                for (int rt : golpeadoresSag) {
                    double error = sp.getMyPlayerError(rt);
                    if (error == sp.getMyPlayerError(jRt)
                            && sp.getMyPlayerPower(rt) > sp.getMyPlayerPower(jRt)) {
                        jRt = rt;
                    } else if (error > sp.getMyPlayerError(jRt)) {
                        jRt = rt;
                    }
                } // End for: recorrer golpeadores
            } // End if: mas de un golpeador

            // Marcar jugador como rematador
            Jugador j = jugadores[jRt];
            j.setRematador(true);

        } // Fin if: existen rematadores


        // Recorrer jugadores
        //----------------------------------------------------------------------
        for (Jugador j : jugadores) {
            // 1. Posicionar jugadores :::::::::::::::::::::::::::::::
            // Recuperadores
            if (j.isRecuperador()) {
                comandos.add(new CommandMoveTo(j.getIndice(), j.getPosicionDestino()));
            }
            else if (j.isInterceptor()) {
                comandos.add(new CommandMoveTo(j.getIndice(), j.getPosicionDestino()));
            }
            // Resto de jugadores
            else {
                // Cambiar posicion de referencia en funcion de los goles
                // Si el rival gana
                if (golesRival - golesSag > 2) {
                    j.setPosicionReferencia(alineacion2[j.getIndice()]);
                } // Yo gano
                else if (golesSag - golesRival > 2) {
                    j.setPosicionReferencia(alineacion3[j.getIndice()]);
                } else {
                    j.setPosicionReferencia(alineacion1[j.getIndice()]);
                }

                comandos.add(j.irAPosicionDestino());
            }



            // 2. Rematadores/Pasadores :::::::::::::::::::::::::::::::
            if (j.isRematador()) {
                comandos.add(j.GolpearBalon());
            }

        } // Fin for: recorrer jugadores

        //Retorna la lista de comandos
        return comandos;
    }
}
