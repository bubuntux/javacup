/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.javacup.tactica;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;
import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Equipo;

import java.awt.*;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Leti implements Tactic {

    public static Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-7.37062937062937,-35.15837104072398),
        new Position(7.608391608391608,-34.920814479638004),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.594405594405593,-20.667420814479637),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(2.377622377622378,-8.076923076923077),
        new Position(21.3986013986014,-1.900452488687783),
        new Position(-1.902097902097902,-0.47511312217194573)
    };

    public static Position alineacion2[]=new Position[]{
        new Position(-0.4755244755244755,-46.08597285067873),
        new Position(-7.37062937062937,-23.518099547511312),
        new Position(9.510489510489512,-23.993212669683256),
        new Position(27.81818181818182,-16.866515837104075),
        new Position(-29.244755244755243,-19.004524886877828),
        new Position(9.510489510489512,6.889140271493213),
        new Position(-10.937062937062937,-3.5633484162895925),
        new Position(-21.16083916083916,26.131221719457013),
        new Position(0.951048951048951,21.142533936651585),
        new Position(14.265734265734267,33.02036199095023),
        new Position(-4.041958041958042,34.68325791855204)
    };

    public static Position alineacion3[]=new Position[]{
        new Position(-0.4755244755244755,-46.08597285067873),
        new Position(-8.321678321678322,-7.364253393665159),
        new Position(5.706293706293707,-8.314479638009049),
        new Position(10.937062937062937,27.794117647058822),
        new Position(-14.027972027972028,25.893665158371043),
        new Position(19.020979020979023,2.3755656108597285),
        new Position(-21.874125874125873,-1.1877828054298643),
        new Position(-21.16083916083916,36.34615384615385),
        new Position(-0.951048951048951,14.490950226244346),
        new Position(17.356643356643357,39.19683257918552),
        new Position(0.951048951048951,41.334841628959275)
    };

    public static Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(7.37062937062937,-6.889140271493213),
        new Position(21.3986013986014,-1.900452488687783),
        new Position(-7.846153846153847,-6.651583710407239)
    };

    public static Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-7.37062937062937,-33.257918552036195),
        new Position(8.083916083916083,-31.59502262443439),
        new Position(22.11188811188811,-24.468325791855204),
        new Position(-23.3006993006993,-25.418552036199095),
        new Position(10.461538461538462,-4.513574660633484),
        new Position(-1.188811188811189,-14.728506787330318),
        new Position(-24.013986013986013,5.463800904977376),
        new Position(-11.412587412587413,-0.7126696832579186),
        new Position(20.20979020979021,12.59049773755656),
        new Position(-4.041958041958042,19.95475113122172)
    };

    public static Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-0.7132867132867133,-37.29638009049774),
        new Position(11.412587412587413,-34.20814479638009),
        new Position(24.013986013986013,-28.269230769230766),
        new Position(-26.867132867132867,-30.88235294117647),
        new Position(6.181818181818182,-18.766968325791854),
        new Position(-12.839160839160838,-34.44570135746606),
        new Position(-4.9930069930069925,0.7126696832579186),
        new Position(-9.986013986013985,-15.91628959276018),
        new Position(11.888111888111888,9.97737556561086),
        new Position(-1.4265734265734267,23.28054298642534)
    };

    class TacticaDetalleImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Leti";
        }

        @Override
        public CountryCode getCountry() {
            return CountryCode.CO;
        }

        @Override
        public String getCoach() {
            return "Henrry Vallejo";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor() {
            return new Color(255, 102, 0);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(255, 102, 0);
        }

        @Override
        public Color getSocksColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(0, 0, 0);
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.FRANJA_VERTICAL;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(255, 102, 0);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(255, 102, 0);
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
            return new Color(0, 0, 0);
        }

        @Override
        public UniformStyle getStyle2() {
            return UniformStyle.FRANJA_VERTICAL;
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
                        new JugadorImpl("Lago", 10, new Color(70, 55, 27), new Color(50, 0, 0), 0.49d, 1.0d, 0.88d, true),
                        new JugadorImpl("Fabio", 11, new Color(246, 194, 148), new Color(50, 0, 0), 0.9d, 0.85d, 0.9d, false),
                        new JugadorImpl("Damares", 12, new Color(116, 68, 24), new Color(50, 0, 0), 0.76d, 0.81d, 0.78d, false),
                        new JugadorImpl("Paulo", 13, new Color(255, 200, 150), new Color(50, 0, 0), 0.9d, 0.65d, 0.71d, false),
                        new JugadorImpl("Kaik", 14, new Color(255, 200, 150), new Color(33, 27, 27), 0.97d, 0.75d, 0.78d, false),
                        new JugadorImpl("Igor", 15, new Color(255, 200, 150), new Color(50, 0, 0), 0.58d, 0.63d, 0.92d, false),
                        new JugadorImpl("Nathan", 16, new Color(213, 146, 85), new Color(50, 0, 0), 0.69d, 0.73d, 1.0d, false),
                        new JugadorImpl("Vitor", 17, new Color(255, 200, 150), new Color(0, 0, 0), 0.74d, 0.64d, 0.86d, false),
                        new JugadorImpl("Raul", 18, new Color(201, 138, 82), new Color(50, 0, 0), 0.81d, 0.77d, 1.0d, false),
                        new JugadorImpl("Otto", 19, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
                        new JugadorImpl("Adrian", 20, new Color(255, 200, 150), new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false)
                    };
        }
    }
    TacticDetail detalle = new TacticaDetalleImpl();

    private Equipo leti;

    public Leti() {
        leti = new Equipo();
    }

    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return alineacion1;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion4;
    }

    @Override
    public List<Command> execute(GameSituations sp) {
        leti.setSituacion(sp);
        leti.actualizar();
        return leti.getComandos();
    }
}
