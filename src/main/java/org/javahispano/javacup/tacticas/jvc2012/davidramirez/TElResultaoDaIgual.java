package org.javahispano.javacup.tacticas.jvc2012.davidramirez;

/**
 *
 * @author david ramirez reina
 */

import java.awt.Color;
import java.util.LinkedList;
import org.javahispano.javacup.model.*;
import org.javahispano.javacup.model.util.*;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.model.command.*;
import org.javahispano.javacup.model.engine.GameSituations;
import java.util.List;
import java.util.Random;

public class TElResultaoDaIgual implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.888111888111888,-31.83257918552036),
        new Position(-6.6573426573426575,-19.95475113122172),
        new Position(12.601398601398602,-32.54524886877828),
        new Position(4.755244755244756,-22.330316742081447),
        new Position(13.076923076923078,-14.728506787330318),
        new Position(-17.11888111888112,-17.34162895927602),
        new Position(4.27972027972028,-2.8506787330316743),
        new Position(-2.6153846153846154,-2.3755656108597285),
        new Position(-21.3986013986014,-2.6131221719457014),
        new Position(20.447552447552447,-2.1380090497737556)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(-6.895104895104895,-21.142533936651585),
        new Position(8.321678321678322,-32.54524886877828),
        new Position(1.6643356643356644,-21.142533936651585),
        new Position(14.027972027972028,-15.678733031674208),
        new Position(-20.685314685314687,-15.678733031674208),
        new Position(6.895104895104895,-8.789592760180994),
        new Position(-7.132867132867133,-7.839366515837104),
        new Position(-13.314685314685315,-3.5633484162895925),
        new Position(10.937062937062937,-2.8506787330316743)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-17.594405594405593,-34.20814479638009),
        new Position(-7.846153846153847,-14.015837104072398),
        new Position(15.692307692307693,-34.68325791855204),
        new Position(6.6573426573426575,-15.203619909502263),
        new Position(6.181818181818182,19.004524886877828),
        new Position(-15.454545454545453,18.529411764705884),
        new Position(12.125874125874127,31.59502262443439),
        new Position(-4.27972027972028,31.59502262443439),
        new Position(-11.412587412587413,42.997737556561084),
        new Position(2.6153846153846154,42.28506787330317)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-14.027972027972028,-40.62217194570136),
        new Position(-7.608391608391608,-35.15837104072398),
        new Position(13.076923076923078,-38.95927601809955),
        new Position(5.706293706293707,-34.68325791855204),
        new Position(9.748251748251748,-20.90497737556561),
        new Position(-16.643356643356643,-21.380090497737555),
        new Position(2.6153846153846154,-8.789592760180994),
        new Position(-11.174825174825173,-9.264705882352942),
        new Position(-14.265734265734267,29.694570135746606),
        new Position(12.839160839160838,29.457013574660635)
    };


    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "ErPrahFurbolClu";
        }

        @Override
        public String getCountry() {
            return "España";
        }

        @Override
        public String getCoach() {
            return "ErPapa";
        }

        @Override
        public Color getShirtColor() {
            return new Color(179, 134, 71);
        }

        @Override
        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(179, 134, 71);
        }

        @Override
        public Color getSocksColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(179, 134, 71        );
        }

        @Override
        public EstiloUniforme getStyle() {
            return EstiloUniforme.SIN_ESTILO;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(0, 0, 0);
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
            return new Color(0, 0, 0        );
        }

        @Override
        public EstiloUniforme getStyle2() {
            return EstiloUniforme.LINEAS_HORIZONTALES;
        }

        class JugadorImpl implements PlayerDetail {

            String nombre;
            int numero;
            Color piel, pelo;
            double velocidad, remate, presicion;
            boolean portero;
            Position Position;

            public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                    double velocidad, double remate, double presicion, boolean portero) {
                this.nombre=nombre;
                this.numero=numero;
                this.piel=piel;
                this.pelo=pelo;
                this.velocidad=velocidad;
                this.remate=remate;
                this.presicion=presicion;
                this.portero=portero;
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
                new JugadorImpl("Macias P", 1, new Color(179,134,71), new Color(19,0,0),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Paquico Navaja", 2, new Color(179,134,71), new Color(50,0,0),1.0d,0.76d,0.0d, false),
                new JugadorImpl("Johny Er Lokoh", 3, new Color(179,134,71), new Color(50,0,0),1.0d,0.76d,0.0d, false),
                new JugadorImpl("Lau ReShul", 4, new Color(179,134,71), new Color(50,0,0),1.0d,0.66d,0.0d, false),
                new JugadorImpl("Toni Cuchara", 5, new Color(179,134,71), new Color(50,0,0),1.0d,0.66d,0.0d, false),
                new JugadorImpl("Peper Manosuelta", 6, new Color(179,134,71), new Color(50,0,0),0.9d,1.0d,1.0d, false),
                new JugadorImpl("Se Morenikoh", 7, new Color(179,134,71), new Color(50,0,0),0.9d,1.0d,1.0d, false),
                new JugadorImpl("El Ruso", 8, new Color(179,134,71), new Color(233,213,54),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Paco Reyes Heredia", 9, new Color(179,134,71), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Richal", 10, new Color(179,134,71), new Color(50,0,0),0.93d,1.0d,1.0d, false),
                new JugadorImpl("Megamarc", 11, new Color(179,134,71), new Color(50,0,0),0.93d,1.0d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
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
    return alineacion2;
    }

    //Lista de comandos
    LinkedList<Command> comandos = new LinkedList<Command>();
    Position lastPosBall = null;
    Position[] newPosJugs = null;
    int[] idsDefensa = new int[] {1, 2, 3, 4};
    @Override
    public List<Command> execute(GameSituations sp) {
        if (lastPosBall == null) lastPosBall = sp.ballPosition();
        if (newPosJugs == null) newPosJugs = new Position[alineacion1.length];
        comandos.clear();
        Position[] jugadores = sp.myPlayers();
        for (int i = 0; i < jugadores.length; ++i) {
            if (!posesionEnemiga(sp)) newPosJugs[i] = alineacion3[i];
            else newPosJugs[i] = alineacion4[i];
        }

        mueveDefensas(idsDefensa, newPosJugs, sp);

        if (sp.isStarts()) {
            for (int i : sp.canKick()) {
                pasa(i, comandos, sp);
            }
        }
        if (!sp.isRivalStarts()) {
            if (sp.canKick().length == 0) {
            for (int i = 5; i < jugadores.length; ++i) {
                if (dist(jugadores[i], sp.ballPosition()) < Constants.ANCHO_CAMPO_JUEGO/6.0)
                    newPosJugs[i] = sp.ballPosition();
            }

            
//Obtiene los datos de recuperacion del balon
            int[] recuperadores = sp.getRecoveryBall();
//Si existe posibilidad de recuperar el balon
            if (recuperadores.length > 1) {
//Obtiene las coordenadas del balon en el instante donde
//se puede recuperar el balon
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
//Recorre la lista de jugadores que pueden recuperar
                int nRecuperadores = 2;
                for (int i = 1; i < recuperadores.length && i <= nRecuperadores; i++) {
//Ordena a los jugadores recuperadores que se ubique
//en la posicion de recuperacion
                    newPosJugs[recuperadores[i]] = new Position(posRecuperacion[0], posRecuperacion[1]);
                }
            }
            }

            //CHUTE
            Random r = new Random();
            for (int i : sp.canKick()) {
                //comandos.add(new CommandHitBall(i));
                //if (newPosJugs[i].getY() > jugadores[i].getY()) {
                //    comandos.add(new CommandHitBall(i, newPosJugs[i], 0.5, 5));
                //}
                if (i <= 4) {
                    comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 45));
                }
                else if (i <= 6) {
                    pasa(i, comandos, sp);
                }
                else if (i <= 8) {
                    if (r.nextBoolean())
                        comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));
                    else
                        pasa(i, comandos, sp);
                }
                else if (i <= 10) {
                    Position pf;
                    if (jugadores[i].getX() < 0)
                        pf = new Position(-Constants.LARGO_ARCO/3.0, Constants.LARGO_CAMPO_JUEGO/2);
                    else
                        pf = new Position(+Constants.LARGO_ARCO/3.0, Constants.LARGO_CAMPO_JUEGO/2);
                    comandos.add(new CommandHitBall(i, pf, 1, 5 + r.nextInt(3)));
                }
                /*
                else
                if (i >= 7 && dist(jugadores[i], Constants.centroArcoSup)
                        < Constants.LARGO_CAMPO_JUEGO / 3.0) {
                    if (r.nextBoolean())
                        comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));
                    else
                        pasa(i, comandos, sp);
                } else if (peligroEntrada(jugadores[i], sp)) {
                    pasa(i, comandos, sp);
                }*/
            }

        }

        //ENDING ITERATION
        for (int i = 0; i < newPosJugs.length; ++i) {
            comandos.add(new CommandMoveTo(i, newPosJugs[i]));
        }
        lastPosBall = sp.ballPosition();
        return comandos;
    }

    private void mueveDefensas(int[] ids_o, Position[] newPosJugs, GameSituations sp) {
        int[] ids = ids_o.clone();
        if (sp.ballPosition().getY() < Constants.LARGO_CAMPO_JUEGO/3.0) {
            int id = masCercanoIDMy(sp.ballPosition(), sp, ids);
            if (id >= 0) {
                int i = 0;
                while (ids[i] != id) ++i;
                ids[i] = -1;
                newPosJugs[id] = sp.ballPosition();
                //System.err.println("cubre: "+100+id);//
            }
        }

        int[] ids_ene = new int[sp.rivalPlayers().length];
        for (int k = 0; k < ids_ene.length; ++k) {
            if (sp.rivalPlayers()[k].getY() < -Constants.LARGO_CAMPO_JUEGO/6.0
                    && sp.rivalPlayers()[k].getY() < sp.ballPosition().getY()) {
                ids_ene[k] = k;
                //System.err.println(k);//
            } else {
                ids_ene[k] = -1;
            }
        }
        for (int i = 0; i < ids.length; ++i) {
            if (ids[i] >= 0) {
                int id = masCercanoIDEne(alineacion4[ids[i]], sp, ids_ene);
                if (id >= 0) {
                    newPosJugs[ids[i]] = posIntermedia(
                            sp.rivalPlayers()[id],
                            sp.ballPosition(),
                            Constants.DISTANCIA_CONTROL_BALON*2);
                    ids_ene[id] = -1;
                    //System.err.println("cubre: "+ids[i]);//
                }
                //elimina id y etc
            }
        }
    }

    private Position posIntermedia(Position p0, Position pf, double dist) {
        if (dist(p0, pf) <= dist) return pf;
        double ang = p0.angle(pf);
        return new Position(p0.getX()+Math.cos(ang)*dist, p0.getY()+Math.sin(ang)*dist);
    }

    private int masCercanoIDMy(Position p, GameSituations sp, int[] ids) {
        return masCercanoID(p, sp, ids, sp.myPlayers());
    }

    private int masCercanoIDEne(Position p, GameSituations sp, int[] ids) {
        return masCercanoID(p, sp, ids, sp.rivalPlayers());
    }

    private int masCercanoID(Position p1, GameSituations sp, int[] ids, Position[] p2s) {
        int id_res = -1;
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < ids.length; ++i) {
            //System.err.println("id: "+ids[i]);
            if (ids[i] >= 0) {
                double dist = dist(p1, p2s[ids[i]]);
                //System.err.println("id: "+dist+" , "+minDist);
                if (dist < minDist) {
                    id_res = ids[i];
                    minDist = dist;
                }
            }
        }
        return id_res;
    }

    private int masCercanoID(Position p, GameSituations sp, int[] ids) {
        return -1;
    }

    private double dist(Position p1, Position p2) {
        double dx = Math.abs(p1.getX() - p2.getX());
        double dy = Math.abs(p1.getY() - p2.getY());
        return Math.sqrt(dx*dx + dy*dy);
    }

    private boolean peligroEntrada(Position p1, GameSituations sp) {
        Position[] pos_ene = sp.rivalPlayers();
        for (int i = 0; i < pos_ene.length; i++) {
            if (dist(p1, pos_ene[i]) < Constants.DISTANCIA_CONTROL_BALON*6)
                return true;
        }
        return false;
    }

    private double minDistEneJugs(Position p, GameSituations sp) {
        Position[] pos_ene = sp.rivalPlayers();
        double min = Double.MAX_VALUE;
        for (int i = 0; i < pos_ene.length; i++) {
            double d = dist(p, pos_ene[i]);
            if (min > d) min = d;
        }
        return min;
    }

    private double minDistJugs(Position p, GameSituations sp) {
        Position[] poss = sp.myPlayers();
        double min = Double.MAX_VALUE;
        for (int i = 0; i < poss.length; i++) {
            double d = dist(p, poss[i]);
            if (min > d) min = d;
        }
        return min;
    }

    private boolean posesionEnemiga(GameSituations sp) {
        if (sp.iteration() == 0) return false;
        //System.err.println(sp.ballPosition());
        return    lastPosBall.getY() > sp.ballPosition().getY()
            && sp.canKick().length == 0
            && minDistEneJugs(sp.ballPosition(), sp) < minDistJugs(sp.ballPosition(), sp)
            ;
    }

    private void pasa(int i, List<Command> comandos, GameSituations sp) {
        Random r = new Random();
        Position[] jugadores = sp.myPlayers();
        int count = 0;
        int jugadorDestino;
        while (((jugadorDestino = r.nextInt(11)) == i
                || jugadores[i].getY() > jugadores[jugadorDestino].getY())
                && count < 20) {
            count++;
        }
        if (i == jugadorDestino) {
            while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
            }
        }
        double fuerza = Math.min(1.0, dist(jugadores[i], jugadores[jugadorDestino])/(Constants.ANCHO_CAMPO/4));
        comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], fuerza, r.nextInt(15)));
    }
}