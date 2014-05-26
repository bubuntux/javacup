package org.dsaw.javacup.tacticas.jvc2012.tomatesmecanicos;

import java.awt.Color;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.UniformStyle;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class tomates implements Tactic {

    Position alineacion1[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-22.11188811188811, -28.98190045248869),
        new Position(-6.419580419580419, -36.34615384615385),
        new Position(22.825174825174827, -26.84389140271493),
        new Position(5.468531468531468, -31.83257918552036),
        new Position(-0.4755244755244755, -13.303167420814479),
        new Position(-25.916083916083913, 3.5633484162895925),
        new Position(26.62937062937063, 3.088235294117647),
        new Position(-10.223776223776223, 27.794117647058822),
        new Position(-0.951048951048951, 11.64027149321267),
        new Position(10.937062937062937, 33.73303167420815)
    };
    Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-16.643356643356643, -30.88235294117647),
        new Position(0.951048951048951, -36.10859728506787),
        new Position(15.454545454545453, -28.98190045248869),
        new Position(-1.902097902097902, -14.490950226244346),
        new Position(8.083916083916083, -0.7126696832579186),
        new Position(-25.202797202797203, 12.828054298642533),
        new Position(24.965034965034967, 13.778280542986426),
        new Position(-11.888111888111888, 33.49547511312217),
        new Position(0.0, 25.656108597285066),
        new Position(9.510489510489512, 40.38461538461539)
    };
    Position alineacion3[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-14.74125874125874, -29.457013574660635),
        new Position(-0.23776223776223776, -35.63348416289593),
        new Position(14.027972027972028, -28.744343891402718),
        new Position(0.23776223776223776, -11.877828054298643),
        new Position(0.0, 8.314479638009049),
        new Position(-25.202797202797203, 20.429864253393667),
        new Position(27.81818181818182, 23.755656108597286),
        new Position(-9.272727272727272, 42.997737556561084),
        new Position(0.23776223776223776, 30.88235294117647),
        new Position(6.6573426573426575, 47.036199095022624)
    };
    Position alineacion4[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-17.11888111888112, -22.56787330316742),
        new Position(0.4755244755244755, -37.77149321266968),
        new Position(16.88111888111888, -24.468325791855204),
        new Position(0.951048951048951, -9.264705882352942),
        new Position(8.55944055944056, 14.96606334841629),
        new Position(-6.895104895104895, 19.004524886877828),
        new Position(24.251748251748253, 43.710407239819006),
        new Position(-24.965034965034967, 42.76018099547511),
        new Position(-8.083916083916083, 36.82126696832579),
        new Position(6.895104895104895, 41.80995475113122)
    };
    Position alineacion5[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-21.16083916083916, -26.606334841628957),
        new Position(8.797202797202797, -35.63348416289593),
        new Position(23.062937062937063, -27.794117647058822),
        new Position(-8.321678321678322, -35.8710407239819),
        new Position(9.272727272727272, -18.29185520361991),
        new Position(-14.503496503496503, -14.96606334841629),
        new Position(21.3986013986014, -0.23755656108597287),
        new Position(-19.734265734265733, -0.47511312217194573),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(6.6573426573426575, -5.463800904977376)
    };
    Position alineacion6[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-20.923076923076923, -28.50678733031674),
        new Position(6.419580419580419, -38.72171945701358),
        new Position(21.636363636363637, -27.794117647058822),
        new Position(-7.37062937062937, -35.63348416289593),
        new Position(2.8531468531468533, -20.667420814479637),
        new Position(-14.74125874125874, -14.490950226244346),
        new Position(23.776223776223777, -2.8506787330316743),
        new Position(-20.447552447552447, -0.9502262443438915),
        new Position(-6.4885496183206115, -6.529850746268657),
        new Position(6.181818181818182, -6.889140271493213)
    };

    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Tomates Mecanicos";
        }

        @Override
        public String getCountry() {
            return "Colombia";
        }

        @Override
        public String getCoach() {
            return "Diego Parra";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 0, 0);
        }

        @Override
        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getSocksColor() {
            return new Color(51, 51, 51);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(0, 153, 0);
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.LINEAS_VERTICALES;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(153, 0, 0);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(255, 255, 0);
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
            Position Position;

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
                        new JugadorImpl("Fabian", 1, new Color(255, 200, 150), new Color(50, 0, 0), 0.92d, 0.95d, 0.76d, true),
                        new JugadorImpl("Camilo", 2, new Color(255, 200, 150), new Color(51, 51, 51), 0.76d, 0.81d, 0.77d, false),
                        new JugadorImpl("Andres", 3, new Color(102, 0, 0), new Color(50, 0, 0), 0.8d, 0.72d, 0.74d, false),
                        new JugadorImpl("Francisco", 4, new Color(255, 200, 150), new Color(204, 0, 0), 0.87d, 0.83d, 0.74d, false),
                        new JugadorImpl("David", 5, new Color(255, 255, 204), new Color(51, 51, 51), 0.85d, 0.73d, 0.75d, false),
                        new JugadorImpl("Alvaro", 23, new Color(255, 200, 150), new Color(50, 0, 0), 0.84d, 0.72d, 0.83d, false),
                        new JugadorImpl("Juan", 7, new Color(255, 200, 150), new Color(50, 0, 0), 0.88d, 0.7d, 0.8d, false),
                        new JugadorImpl("Gonzalo", 8, new Color(255, 200, 150), new Color(50, 0, 0), 0.83d, 0.8d, 0.85d, false),
                        new JugadorImpl("Hernan", 9, new Color(255, 200, 150), new Color(50, 0, 0), 0.93d, 0.86d, 0.92d, false),
                        new JugadorImpl("Miguel", 10, new Color(255, 255, 204), new Color(0, 0, 0), 0.96d, 0.84d, 0.92d, false),
                        new JugadorImpl("Alejandro", 17, new Color(255, 200, 150), new Color(50, 0, 0), 0.97d, 0.92d, 0.93d, false)
                    };
        }
    }
    TacticDetail detalle = new TacticDetailImpl();

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
    LinkedList<Command> comandos = new LinkedList<>();

    @Override
    public List<Command> execute(GameSituations sp) {

        comandos.clear();
        //Posicion inicial jugadores y balon
        Position[] jugadores = sp.myPlayers();
        Position[] rivales = sp.rivalPlayers();
        Position balon = sp.ballPosition();

        //Alineacion defecto
        for (int i = 1; i < jugadores.length; i++) {
            //Alineacion Default
            comandos.add(new CommandMoveTo(i, alineacion1[i]));
        }

        //Posicionamiento del arquero
        double h = balon.distance(new Position(0, -52.5));
        double x = balon.getX();
        double y = balon.getY();

        double y1 = 52.5 + y;
        double x1;
        if (x < 0) {
            x1 = -x;
        } else {
            x1 = x;
        }
        double r1 = 50 / h;
        if (balon.getX() < 9.5 && balon.getX() > -9.5 && balon.getY() > -42) {
            r1 = r1 * 1.5;
        }
        if (sp.ballAltitude() >= Constants.ALTURA_CONTROL_BALON && balon.getY() < -35) {
            r1 = r1 / sp.ballAltitude();
        }
        double xf = x1 * r1 / h;
        double yf = y1 * r1 / h;
        if (x < 0) {
            xf = -xf;
        }
        yf = yf - 52.5;
        comandos.add(new CommandMoveTo(0, new Position(xf, yf)));

        //Cuenta jugadores al ataque
        int rat = 0;
        int[] jugrat = new int[5];
        for (int i = 1; i < rivales.length; i++) {
            if (rivales[i].getY() < -15 && rat < 5) {
                jugrat[rat] = i;
                rat++;
            }
        }

        //Alineacion Defensa con menos de 4 atancantes
        if (rat < 4) {
            for (int i = 1; i < jugadores.length; i++) {
                //Alineacion Default
                comandos.add(new CommandMoveTo(i, alineacion2[i]));
            }
        }

        //Marcaje Defensivo
        if (rat < 4) {

            //Un solo atacante
            if (rat == 1) {
                //defensor contra atacante
                comandos.add(new CommandMoveTo(2, rivales[jugrat[0]]));
                //defensorers libres
                Position pmediader = Position.medium(alineacion2[3], rivales[jugrat[0]]);
                Position pmediaizq = Position.medium(alineacion2[1], rivales[jugrat[0]]);
                comandos.add(new CommandMoveTo(1, new Position(pmediaizq.getX(), rivales[jugrat[0]].getY() - 3)));
                comandos.add(new CommandMoveTo(3, new Position(pmediader.getX(), rivales[jugrat[0]].getY() - 3)));
            }

            //Dos atacantes
            if (rat == 2) {

                //Jugadores contra atacantes : buscar rival mas sercano
                if (jugadores[1].distance(rivales[jugrat[0]]) > jugadores[1].distance(rivales[jugrat[1]])) {
                    comandos.add(new CommandMoveTo(1, rivales[jugrat[1]]));
                    comandos.add(new CommandMoveTo(3, rivales[jugrat[0]]));
                } else {
                    comandos.add(new CommandMoveTo(1, rivales[jugrat[0]]));
                    comandos.add(new CommandMoveTo(3, rivales[jugrat[1]]));
                }

                //defensor libre : buscando estar bajo el ultimo atacante
                Position pmedia = Position.medium(rivales[jugrat[0]], rivales[jugrat[1]]);
                if (rivales[jugrat[1]].getY() < rivales[jugrat[0]].getY()) {
                    comandos.add(new CommandMoveTo(2, new Position(pmedia.getX(), rivales[jugrat[1]].getY() - 3)));
                } else {
                    comandos.add(new CommandMoveTo(2, new Position(pmedia.getX(), rivales[jugrat[0]].getY() - 3)));
                }
            }

            //Tres atacantes
            if (rat == 3) {

                //Busccando rival mas cercano
                int aux;
                for (int i = 0; i < 3; i++) {
                    for (int j = 1; j < 3; j++) {
                        if (rivales[jugrat[i]].getX() < rivales[jugrat[i]].getX()) {
                            aux = jugrat[i];
                            jugrat[i] = jugrat[i + 1];
                            jugrat[i + 1] = aux;
                        }
                    }
                }
                comandos.add(new CommandMoveTo(3, rivales[jugrat[0]]));
                comandos.add(new CommandMoveTo(2, rivales[jugrat[1]]));
                comandos.add(new CommandMoveTo(1, rivales[jugrat[2]]));

            }
        } else {

            //Cuatro atacantes
            if (rat == 4) {

                //Busccando rival mas cercano
                int aux;
                for (int i = 0; i < 4; i++) {
                    for (int j = 1; j < 3; j++) {
                        if (rivales[jugrat[i]].getX() < rivales[jugrat[i]].getX()) {
                            aux = jugrat[i];
                            jugrat[i] = jugrat[i + 1];
                            jugrat[i + 1] = aux;
                        }
                    }
                }
                comandos.add(new CommandMoveTo(1, rivales[jugrat[0]]));
                comandos.add(new CommandMoveTo(3, rivales[jugrat[1]]));
                comandos.add(new CommandMoveTo(2, rivales[jugrat[2]]));
                comandos.add(new CommandMoveTo(4, rivales[jugrat[3]]));

                //Mas de 4 atacantes
            } else {
                for (int i = 1; i < 5; i++) {
                    int aux = 0;
                    for (int j = 1; j < rivales.length; j++) {
                        if (jugadores[i].distance(rivales[j]) < jugadores[i].distance(rivales[aux])) {
                            aux = i;
                        }
                    }
                    if (jugadores[i].getY() > alineacion1[i].getY() - 6) {
                        comandos.add(new CommandMoveTo(i, new Position(alineacion1[i].getX(), alineacion1[i].getY() - 12)));
                    } else {
                        comandos.add(new CommandMoveTo(i, rivales[aux]));
                    }
                }
            }
        }

        //jugador menor distancia con medio
        int min = 0;
        for (int i = 1; i < rivales.length; i++) {
            if (jugadores[5].distance(rivales[i]) < jugadores[5].distance(rivales[min])) {
                min = i;
            }
        }

        //Volante de recuperacion
        if (balon.getY() > sp.getTrajectory(sp.iteration() + 1)[1] && balon.getY() < -40) {
            comandos.add(new CommandMoveTo(5, rivales[min]));
        } else {

            //Movimiento de ataque
            Random r = new Random();
            int inv = r.nextInt(2), l = 3;
            if (inv == 1) {
                l = -l;
            }
            int inv1 = r.nextInt(4), l1 = 5;
            if (inv1 == 1) {
                l1 = -l1 / 2;
            }
            if (jugadores[5].distance(rivales[min]) < 5) {
                comandos.add(new CommandMoveTo(5, new Position(jugadores[5].getX() + l, jugadores[5].getY() + l1)));
            }
        }

        //Movimientos de jugadores para evitar defensas
        for (int i = 6; i < jugadores.length; i++) {
            int mi = 0;
            for (int j = 1; j < rivales.length; j++) {
                if (jugadores[i].distance(rivales[j]) < jugadores[i].distance(rivales[mi])) {
                    mi = i;
                }
            }
            Random r = new Random();
            int inv = r.nextInt(2), l = 3;
            if (inv == 1) {
                l = -l;
            }
            int inv1 = r.nextInt(2), l1 = r.nextInt(6) + 1;
            if (inv1 == 1) {
                l1 = -l;
            }
            if (jugadores[i].distance(rivales[min]) < 5) {
                comandos.add(new CommandMoveTo(i, new Position(jugadores[i].getX() + l, jugadores[5].getY() + l1)));
            }
        }

        //Avance de Medios
        if (balon.getY() > 15) {
            if (rat < 4) {
                comandos.add(new CommandMoveTo(6, new Position(alineacion2[6].getX(), alineacion2[6].getY() + 12)));
                comandos.add(new CommandMoveTo(7, new Position(alineacion2[7].getX(), alineacion2[7].getY() + 12)));
            } else {
                comandos.add(new CommandMoveTo(6, new Position(alineacion1[6].getX(), alineacion1[6].getY() + 12)));
                comandos.add(new CommandMoveTo(7, new Position(alineacion1[7].getX(), alineacion1[7].getY() + 12)));
            }
        }

        //Situaciones de Juego
        int mrec = 0;//lo usaremos en el remate
        if (!sp.isRivalStarts()) {

            //Obtiene los datos de recuperacion del balon
            int[] recuperadores = sp.getRecoveryBall();

            //Saco
            if (sp.isStarts()) {
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);

                //Saque de meta
                if (balon.getY() == -47 && (balon.getX() == 9.16 || balon.getX() == -9.16)) {

                    //Saca el arquero
                    comandos.add(new CommandMoveTo(0, new Position(posRecuperacion[0], posRecuperacion[1])));

                    //Sauqe de esquina
                } else {
                    if (balon.getY() == 52.5 && (balon.getX() == 34 || balon.getX() == -34)) {

                        //Se posicionan los laterales para cobrar
                        if (balon.getX() == 34) {
                            comandos.add(new CommandMoveTo(7, new Position(posRecuperacion[0], posRecuperacion[1])));
                        } else {
                            comandos.add(new CommandMoveTo(6, new Position(posRecuperacion[0], posRecuperacion[1])));
                        }

                        //Posicion atacantes para recivir
                        comandos.add(new CommandMoveTo(5, new Position(28.032, 0.0)));
                        comandos.add(new CommandMoveTo(8, new Position(45.373, -10.699)));
                        comandos.add(new CommandMoveTo(9, new Position(40.622, -1.902)));
                        comandos.add(new CommandMoveTo(10, new Position(47.986, 11.175)));

                        //Saque de centro
                    } else {
                        if (balon.getY() == 0 && balon.getX() == 0) {
                            comandos.add(new CommandMoveTo(9, new Position(posRecuperacion[0], posRecuperacion[1])));

                            //Saques Laterales
                        } else {
                            if (recuperadores.length > 1) {

                                //Movemos jugador que va a sacar
                                comandos.add(new CommandMoveTo(recuperadores[1], new Position(posRecuperacion[0], posRecuperacion[1])));

                                //encontramos los 3 jugadores mas sercanos al saque
                                int[] rec = new int[3];
                                rec[1] = 1;
                                rec[2] = 2;
                                for (int i = 1; i < jugadores.length; i++) {
                                    if (jugadores[i].distance(jugadores[recuperadores[1]]) < jugadores[i].distance(jugadores[rec[0]]) && i != recuperadores[1]) {
                                        int aux = rec[0], aux1 = rec[1];
                                        rec[0] = i;
                                        rec[1] = aux;
                                        rec[2] = aux1;
                                    }
                                }

                                //Ayudar a el que saca
                                if (jugadores[rec[0]].distance(jugadores[recuperadores[1]]) > 5) {
                                    comandos.add(new CommandMoveTo(rec[0], jugadores[recuperadores[1]]));
                                }

                                //encontramos el rival a menor distacia de los receptores
                                int[] recdr = new int[3];
                                for (int i = 0; i < rec.length; i++) {
                                    for (int j = 1; j < jugadores.length; j++) {
                                        if (jugadores[rec[i]].distance(rivales[j]) < jugadores[rec[i]].distance(rivales[recdr[i]])) {
                                            recdr[i] = j;
                                        }
                                    }
                                }

                                //encontramos el jugador que tiene mas alejado al rival
                                for (int i = 0; i < rec.length; i++) {
                                    for (int j = i + 1; j < recdr.length; j++) {
                                        if (jugadores[rec[i]].distance(rivales[recdr[i]]) > jugadores[rec[j]].distance(rivales[recdr[j]])) {
                                            mrec = j;
                                        }
                                    }
                                    if (mrec == 0) {
                                        break;
                                    }
                                }
                            } else {
                                //jugador mas cercano al balon
                                int aux = 5;
                                for (int i = 1; i < jugadores.length; i++) {
                                    if (jugadores[i].distance(balon) < jugadores[aux].distance(balon)) {
                                        aux = i;
                                    }
                                }
                                comandos.add(new CommandMoveTo(aux, balon));
                            }
                        }
                    }
                }

                //Balon en juego
            } else {
                if (recuperadores.length > 1) {
                    double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);

                    //Evitar salida excesiva arquero
                    if (recuperadores[1] == 0) {
                        if (recuperadores.length > 2 && recuperadores[0] > 2) {
                            comandos.add(new CommandMoveTo(recuperadores[2], new Position(posRecuperacion[0], posRecuperacion[1])));
                        }
                    } else {
                        comandos.add(new CommandMoveTo(recuperadores[1], new Position(posRecuperacion[0], posRecuperacion[1])));
                    }
                }
            }

            //Saque rival
        } else {
            for (int i = 1; i < jugadores.length; i++) {
                int aux = 0;
                for (int j = 1; j < rivales.length; j++) {
                    if (jugadores[i].distance(rivales[j]) < jugadores[i].distance(rivales[aux])) {
                        aux = j;
                    }
                }
                comandos.add(new CommandMoveTo(i, rivales[aux]));
            }
        }
        //Corrige posicion arquero, en caso de elevacion
        if (sp.ballAltitude() >= Constants.ALTURA_CONTROL_BALON && balon.getY() < -35) {
            comandos.add(new CommandMoveTo(0, new Position(xf, yf)));
        }
        for (int k : sp.canKick()) {

            //por si acaso
            Random r = new Random();
            comandos.add(new CommandHitBall(k, Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));

            //Saco
            if (sp.isStarts()) {

                //Saque de meta
                if (balon.getY() == -47 && (balon.getX() == 9.16 || balon.getX() == -9.16)) {
                    int recp = r.nextInt(4) + 4;
                    comandos.add(new CommandHitBall(k, jugadores[recp], 1, 30 + r.nextInt(recp * recp)));

                    //Sauqe de esquina
                } else {
                    if (balon.getY() == 52.5 && (balon.getX() == 34 || balon.getX() == -34)) {
                        int recp = r.nextInt(5) + 8;
                        if (recp > 10) {
                            recp = 5;
                        }
                        comandos.add(new CommandHitBall(k, jugadores[recp], 1, 10 + r.nextInt(15)));

                        //Saque de centro
                    } else {
                        if (balon.getY() == 0 && balon.getX() == 0) {
                            int recp = r.nextInt(3) + 5;
                            if (recp > 6) {
                                recp = 10;
                            }
                            comandos.add(new CommandHitBall(k, jugadores[recp], 1, false));

                            //Saques Laterales
                        } else {
                            int recp = mrec, angulo = 0;
                            double dist = jugadores[k].distance(jugadores[recp]);
                            if (dist < 12) {
                                angulo = r.nextInt(18) + 14;
                            }
                            comandos.add(new CommandHitBall(k, jugadores[recp], 1, angulo));
                        }
                    }
                }

                //Balon en juego
            } else {
                int riv = 0;
                for (int j = 1; j < jugadores.length; j++) {
                    if (jugadores[k].distance(rivales[j]) < jugadores[k].distance(rivales[riv])) {
                        riv = j;
                    }
                }
                //Atacamos
                if (balon.getY() >= 25) {
                    //Encontramos jgador
                    if (k == 5) {
                        if (jugadores[k].distance(rivales[riv]) > 2) {
                            comandos.add(new CommandHitBall(k));
                        } else {
                            int recp = r.nextInt(3) + 8;
                            comandos.add(new CommandHitBall(k, jugadores[recp], 1, 10 + r.nextInt(5)));
                        }
                    }
                    if (k == 8 || k == 9 || k == 10) {
                        if (balon.getY() < 35) {
                            if (jugadores[k].distance(rivales[riv]) > 2) {
                                comandos.add(new CommandHitBall(k));
                            } else {
                                int recp;
                                do {
                                    recp = r.nextInt(3) + 8;
                                } while (recp != k);
                                comandos.add(new CommandHitBall(k, jugadores[recp], 1, r.nextInt(5)));
                            }
                        } else {
                            int op = r.nextInt(2);
                            if (op == 0) {
                                int recp;
                                if (k >= 8 && k <= 10) {
                                    do {
                                        recp = r.nextInt(3) + 8;
                                    } while (recp != k);
                                    comandos.add(new CommandHitBall(k, jugadores[recp], 1, r.nextInt(5)));
                                }
                            } else {
                                int re = r.nextInt(3);
                                if (re == 0) {
                                    comandos.add(new CommandHitBall(k, Constants.posteDerArcoSup, 1, 10 + r.nextInt(12)));
                                } else {
                                    if (re == 1) {
                                        comandos.add(new CommandHitBall(k, Constants.posteIzqArcoSup, 1, 10 + r.nextInt(12)));
                                    } else {
                                        comandos.add(new CommandHitBall(k, Constants.centroArcoSup, 1, 10 + r.nextInt(12)));
                                    }
                                }
                            }
                        }
                    } else {
                        if (jugadores[k].distance(rivales[riv]) > 2) {
                            comandos.add(new CommandHitBall(k));
                        } else {
                            int recp;
                            if (k >= 8 && k <= 10) {
                                do {
                                    recp = r.nextInt(3) + 8;
                                } while (recp != k);
                                comandos.add(new CommandHitBall(k, jugadores[recp], 1, 10 + r.nextInt(5)));
                            }
                        }
                    }
                }

                //Juego en medio
                if (balon.getY() < 25 && balon.getY() > -20) {
                    if (jugadores[k].distance(rivales[riv]) > 1) {
                        comandos.add(new CommandHitBall(k));
                    } else {
                        int recp;
                        if (k >= 6 && k <= 10) {//Modificacion realizada por fabnun para evitar que se quede pegado el partido
                            do {
                                recp = r.nextInt(5) + 6;
                            } while (recp != k);
                            int t = 10;
                            if (jugadores[k].distance(jugadores[recp]) > 20) {
                                t = 30;
                            }
                            comandos.add(new CommandHitBall(k, jugadores[recp], 1, t + r.nextInt(5)));
                        }
                    }
                }

                //Salida en defensa
                if (balon.getY() >= 25) {
                    //Arquero
                    if (k == 0) {
                        int recp;
                        if (k >= 7 && k <= 10) {
                            do {
                                recp = r.nextInt(4) + 7;
                            } while (recp != k);
                            int t = 20;
                            if (jugadores[k].distance(jugadores[recp]) > 20) {
                                t = 30;
                            }
                            comandos.add(new CommandHitBall(k, jugadores[recp], 1, t + r.nextInt(5)));
                        }
                        //Otros jugadores
                    } else {
                        if (jugadores[k].distance(rivales[riv]) > 3) {
                            comandos.add(new CommandHitBall(k));
                        } else {
                            int recp;
                            if (k >= 7 && k <= 10) {
                                do {
                                    recp = r.nextInt(4) + 7;
                                } while (recp != k);
                                int t = 10;
                                if (jugadores[k].distance(jugadores[recp]) > 20) {
                                    t = 30;
                                }
                                comandos.add(new CommandHitBall(k, jugadores[recp], 1, t + r.nextInt(5)));
                            }
                        }
                    }
                }
            }
            if (balon.getY() == 0 && balon.getX() == 0) {
                int recp = r.nextInt(3) + 5;
                if (recp > 6) {
                    recp = 10;
                }
                comandos.add(new CommandHitBall(k, jugadores[recp], 1, false));
            }
        }
        return comandos;
    }
}
