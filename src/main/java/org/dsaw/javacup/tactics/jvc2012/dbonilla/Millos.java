package org.dsaw.javacup.tactics.jvc2012.dbonilla;


import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Millos implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0,-50.41044776119403),
        new Position(-19.46564885496183,-31.6044776119403),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(19.984732824427482,-31.6044776119403),
        new Position(7.526717557251908,-11.753731343283583),
        new Position(-8.564885496183205,-11.753731343283583),
        new Position(-24.65648854961832,-2.3507462686567164),
        new Position(23.099236641221374,-2.873134328358209),
        new Position(-14.274809160305344,30.559701492537314),
        new Position(-0.7786259541984732,8.097014925373134),
        new Position(12.717557251908397,29.51492537313433)
    };

    MYPosition align1[] = new MYPosition[]{
        new MYPosition(0,-50.41044776119403),
        new MYPosition(-19.46564885496183,-31.6044776119403),
        new MYPosition(0.2595419847328244,-31.082089552238806),
        new MYPosition(19.984732824427482,-31.6044776119403),
        new MYPosition(7.526717557251908,-11.753731343283583),
        new MYPosition(-8.564885496183205,-11.753731343283583),
        new MYPosition(-24.65648854961832,-2.3507462686567164),
        new MYPosition(23.099236641221374,-2.873134328358209),
        new MYPosition(-14.274809160305344,30.559701492537314),
        new MYPosition(-0.7786259541984732,8.097014925373134),
        new MYPosition(12.717557251908397,29.51492537313433)
    };

    double minX[] = new double[]{
        0,
        -Constants.ANCHO_CAMPO_JUEGO/2,
        -Constants.ANCHO_CAMPO_JUEGO/4,
        -Constants.ANCHO_CAMPO_JUEGO/6,
        0,
        -Constants.ANCHO_CAMPO_JUEGO/2,
        -Constants.ANCHO_CAMPO_JUEGO/4,
        0,
        -Constants.ANCHO_CAMPO_JUEGO/2,
        -Constants.ANCHO_CAMPO_JUEGO/4,
        0
    };

    double maxX[] = new double[]{
        0,
        0,
        Constants.ANCHO_CAMPO_JUEGO/6,
        Constants.ANCHO_CAMPO_JUEGO/4,
        Constants.ANCHO_CAMPO_JUEGO/2,
        0,
        Constants.ANCHO_CAMPO_JUEGO/4,
        Constants.ANCHO_CAMPO_JUEGO/2,
        0,
        Constants.ANCHO_CAMPO_JUEGO/4,
        Constants.ANCHO_CAMPO_JUEGO/2
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(27.251908396946565,-27.94776119402985),
        new Position(-29.84732824427481,-26.902985074626866),
        new Position(8.564885496183205,-7.574626865671642),
        new Position(-10.641221374045802,-7.052238805970149),
        new Position(27.251908396946565,4.440298507462686),
        new Position(-29.32824427480916,3.3955223880597014),
        new Position(-0.2595419847328244,19.067164179104477),
        new Position(-0.2595419847328244,35.78358208955224)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(26.732824427480914,-20.111940298507463),
        new Position(-29.32824427480916,-21.67910447761194),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(-18.946564885496183,-0.26119402985074625),
        new Position(18.946564885496183,-0.26119402985074625),
        new Position(-19.46564885496183,35.78358208955224),
        new Position(-0.2595419847328244,19.067164179104477),
        new Position(18.946564885496183,35.26119402985075)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(11.16030534351145,-1.3059701492537314),
        new Position(-10.641221374045802,-0.7835820895522387),
        new Position(-27.251908396946565,31.6044776119403),
        new Position(-10.641221374045802,30.559701492537314),
        new Position(9.603053435114505,28.992537313432837),
        new Position(25.69465648854962,28.992537313432837)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(5.969465648854961,-5.485074626865671),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(6.4885496183206115,-6.529850746268657),
        new Position(-6.4885496183206115,-6.529850746268657),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Millos";
        }

        @Override
        public String getCountry() {
            return "Colombia";
        }

        @Override
        public String getCoach() {
            return "David B";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(2, 2, 54);
        }

        @Override
        public Color getSocksColor() {
            return new Color(2, 2, 54);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(255, 153, 0        );
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.FRANJA_VERTICAL;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(2, 2, 54);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(2, 2, 54);
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
            return new Color(255, 0, 0        );
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
                new JugadorImpl("Jones", 1, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, true),
                new JugadorImpl("Hamon", 2, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Viano", 3, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Weissert", 4, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Chérèque", 5, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Janin", 6, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Lacroix", 7, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Lextrait", 8, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
                new JugadorImpl("Laburthe", 9, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, false),
                new JugadorImpl("Niewen", 10, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, false),
                new JugadorImpl("Vizzari", 11, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, false)
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
    return alineacion5;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
    }


    List<Command> commands; // list of output commands
    List<Integer> availablePlayers; // Players who can be used for the strategy
    boolean attacking;
    MYPosition[] enemies;
    MYPosition[] myPlayers;
    List<Integer> dangerousEnemies;
    int[] ableToShot;
    PlayerDetail[] myPlayersDetails;
    PlayerDetail[] enemiesDetails;
    int[] myPlayersCantShoot;
    int[] enemiesCantShoot;
    MYPosition ballwithZ, theNextBall;
    double currentDx, currentDy, currentDz;
    MYPosition[] minDestinations, maxDestinations;
    boolean[] defense, middle, attack;
    GameSituations Tacticsp;
    public Millos() {
        commands = new ArrayList<>();
        availablePlayers = new ArrayList<>();
        dangerousEnemies = new ArrayList<>();
        ballwithZ = new MYPosition(new Position());
        theNextBall = new MYPosition(new Position());
        minDestinations = new MYPosition[align1.length];
        maxDestinations = new MYPosition[align1.length];
        myPlayers = new MYPosition[align1.length];
        enemies = new MYPosition[align1.length];
        defense = new boolean[align1.length];
        middle = new boolean[align1.length];
        attack = new boolean[align1.length];
        for (int i = 0; i < align1.length; ++i){
            defense[i] = i >= 1 && i <= 4;
            middle[i] = i >= 5 && i <= 7;
            attack[i] = i >= 8;
        }
        minDestinations[0] = align1[0];
        maxDestinations[0] = align1[0];
    }


    public void initRound(GameSituations sp) {

        // Clean the list of output commands
        commands.clear();
        // Players who can be used for the strategy
        availablePlayers.clear();
        for (int i = 0; i < 11; ++i){
            availablePlayers.add(i);
        }
        attacking = false;
        Position[] temp = sp.rivalPlayers();
        for (int i = 0; i < temp.length; ++i){
            enemies[i] = new MYPosition(temp[i]);
        }

        temp = sp.myPlayers();
        for (int i = 0; i < temp.length; ++i){
            myPlayers[i] = new MYPosition(temp[i]);
        }

        dangerousEnemies.clear();
        MYPosition myGoal = new MYPosition(0, -Constants.LARGO_CAMPO_JUEGO/2);
        while (enemies.length != dangerousEnemies.size()) {
            double minDist = 1000;
            int minJ = 0;
            for (int j = enemies.length - 1; j >= 0 ; --j){
                if (dangerousEnemies.contains(new Integer(j))) {
                    continue;
                }
                double dist = myGoal.distance(enemies[j]);
                if (dist < minDist) {
                    minDist = dist;
                    minJ = j;
                }
            }
            dangerousEnemies.add(new Integer(minJ));
        }
        ableToShot = sp.canKick();
        myPlayersDetails = sp.myPlayersDetail();
        enemiesDetails = sp.rivalPlayersDetail();
        myPlayersCantShoot = sp.iterationsToKick();
        enemiesCantShoot = sp.rivalIterationsToKick();

        MYPosition oldBallwithZ = ballwithZ;
        ballwithZ = new MYPosition(sp.ballPosition());
        ballwithZ.z = sp.ballAltitude();
        Tacticsp = sp;

        //System.out.println(ballwithZ);

        for (int i = 1; i < minDestinations.length; ++i){
            if (attack[i]) {
                minDestinations[i] = new MYPosition(minX[i], Math.min(Constants.LARGO_CAMPO_JUEGO/2 - 10, (Math.max(0, ballwithZ.y))));
                maxDestinations[i] = new MYPosition(maxX[i], (Math.max(0,Math.min(Constants.LARGO_CAMPO_JUEGO/2 - 10, ballwithZ.y + 30))));
            } else if (middle[i]) {
                minDestinations[i] = new MYPosition(minX[i], Math.min(Constants.LARGO_CAMPO_JUEGO/2 - 10, (Math.max(-Constants.LARGO_CAMPO_JUEGO/2 + 10, ballwithZ.y - 20))));
                maxDestinations[i] = new MYPosition(maxX[i], (Math.max(-Constants.LARGO_CAMPO_JUEGO/2 + 10,Math.min(Constants.LARGO_CAMPO_JUEGO/2 - 10, ballwithZ.y + 20))));
            }
            else {
                minDestinations[i] = new MYPosition(align1[i]);
                maxDestinations[i] = new MYPosition(align1[i]);
            }
            /*
            if (ballwithZ.getY() > 0 && !myPlayersDetails[i].esPortero()){
                destinations[i] = align1[i].getDiffPosition(0, ballwithZ.getY());
                if (isOut(destinations[i])) {
                    destinations[i] = align1[i];
                }
            } else {
                destinations[i] = align1[i];
            }
            */
        }

        /*
        System.out.println("X : " + ballwithZ.getX() + "/" + theNextBall.getX());
        System.out.println("Y : " + ballwithZ.getY() + "/" + theNextBall.getY());
        System.out.println("Z : " + ballwithZ.z + "/" + theNextBall.z);
        */
    }

    public class MYPosition {
        double x, y, z;

        public MYPosition() {
            x = y = z = 0;
        }
        public MYPosition(MYPosition p) {
            x = p.x;
            y = p.y;
            z = p.z;
        }
        public MYPosition(Position p) {
            x = p.getX();
            y = p.getY();
            z = 0;
        }
        public MYPosition(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = 0;
        }
        public MYPosition(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public double distanceSQ(double px, double py) {
            return (x - px) * (x - px) + (y - py) * (y - py);
        }
        public double distance() {
            return this.distance(new MYPosition());
        }
        public double distance(double px, double py) {
            return Math.sqrt((x - px) * (x - px) + (y - py) * (y - py));
        }
        public double distance(MYPosition p) {
            return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
        }
        public double distance(Position p) {
            return this.distance(new MYPosition(p));
        }
        public Position getPosition() {
            return new Position(x,y);
        }
        public MYPosition getDiffPosition(double dx, double dy) {
            return new MYPosition(this.x + dx, this.y + dy);
        }

        private void moveDiff(double dx, double dy) {
            this.x += dx;
            this.y += dy;
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ", " + this.z + ")";
        }
    }

    public int determineGoal(MYPosition lastBall, MYPosition ball) {
        double posY = 0;
        if (ball.y < 0) {
            posY = -Constants.LARGO_CAMPO_JUEGO / 2;
        } else {
            posY = Constants.LARGO_CAMPO_JUEGO / 2;
        }
        double newDy = lastBall.y - ball.y;
        double posZ;
        double posX;

        if (newDy != 0) {
            posZ = ((lastBall.z - ball.z) / newDy) * (posY - ball.y) + ball.z;
        } else {
            posZ = ball.z;
        }
        if (Math.abs(ball.getY()) >= Constants.LARGO_CAMPO_JUEGO / 2) {
            if (posZ <= Constants.ALTO_ARCO) {

                if (newDy != 0) {
                    posX = ((lastBall.x - ball.x) / newDy) * (posY - ball.y) + ball.x;
                } else {
                    posX = ball.x;
                }

                if (Math.abs(posX) < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON) {
                    if (ball.getY() < 0) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        }

        return 0;
    }

    public class DecisionScore {
        boolean forUs;
        int turn;
        int receiver;
        MYPosition finalBall;
        int isGoal;

        int shooter;
        double strength;
        double angle;
        double angleZ;

        MYPosition[] evol;

        private DecisionScore() {}

        public DecisionScore(DecisionScore ds) {
            this.turn = ds.turn;
            this.forUs = ds.forUs;
            this.receiver = ds.receiver;
            this.finalBall = ds.finalBall;
            this.isGoal = ds.isGoal;
            this.shooter = ds.shooter;
            this.strength = ds.strength;
            this.angle = ds.angle;
            this.angleZ = ds.angleZ;
            this.evol = ds.evol.clone();
        }
        public DecisionScore(MYPosition[] ball) {
            this(ball, false);
        }

        public DecisionScore(MYPosition[] ball, boolean onlyMyPlayers) {

            evol = ball.clone();

            Mainloop : for (int k = 1; k < ball.length; ++k){
                isGoal = determineGoal(ball[k-1], ball[k]);
                if (isGoal != 0 || isOut(ball[k])) {
                    turn = k;
                    forUs = false;
                    break Mainloop;
                }

                if (k > 0) {
                    if (!onlyMyPlayers){
                        for (int j = 0; j < enemies.length; ++j){
                            if (isReachable(ball[k], enemiesDetails[j], enemies[j], enemiesCantShoot[j], k)){
                                turn = k;
                                receiver = j;
                                forUs = false;
                                break Mainloop;
                            }
                        }
                    }

                    for (int i = 0; i < myPlayers.length; ++i){
                        if (isReachable(ball[k], myPlayersDetails[i], myPlayers[i], myPlayersCantShoot[i], k)){
                            turn = k;
                            receiver = i;
                            forUs = true;
                            break Mainloop;
                        }
                    }
                }
            }

            finalBall = ball[turn];

        }

        public boolean isReachable(MYPosition ball, PlayerDetail jd, MYPosition currentPos, int cantShoot, int turn){
            if (cantShoot <= turn){
                double maxHeight = Constants.ALTURA_CONTROL_BALON;
                double maxDist = Constants.DISTANCIA_CONTROL_BALON;
                if (jd.isGoalKeeper() && Math.abs(currentPos.getX()) <= Constants.LARGO_AREA_GRANDE / 2 && Math.abs(currentPos.getY()) > Constants.ANCHO_AREA_GRANDE){
                    maxDist = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
                    maxHeight = Constants.ALTO_ARCO;
                }
                if (ball.z > maxHeight) {
                    return false;
                }

                double dist = ball.distance(currentPos);
                return dist - maxDist <= turn * Constants.getVelocidad(jd.getSpeed());
            } else {
                return false;
            }
        }

        public boolean isBetter(DecisionScore ds) {
            if (this.isGoal != ds.isGoal) {
                return this.isGoal > ds.isGoal;
            }

            if (this.forUs != ds.forUs) {
                return this.forUs;
            }

            if (attack[this.receiver] != attack[ds.receiver]) {
                return attack[this.receiver];
            }

            if (isOut(this.finalBall) != isOut(ds.finalBall)){
                return !isOut(this.finalBall);
            }

            double thisDist = this.finalBall.distance(Constants.centroArcoSup);
            double dsDist = ds.finalBall.distance(Constants.centroArcoSup);
            if (thisDist != dsDist) {
                return thisDist < dsDist;
            }

            if (this.strength != ds.strength) {
                return this.strength > ds.strength;
            }

            return false;
        }
    }

    public class AverageDecisionScore extends DecisionScore {

        int count;
        double countForUs;
        double countIsGoal;

        public AverageDecisionScore (DecisionScore ds, ArrayList<DecisionScore> dsList, double errorAngle) {
            super(ds);
            finalBall = new MYPosition();
            count = 0;
            countIsGoal= 0;
            countForUs = 0;
            double errorAngleDeg = errorAngle * 180 / Math.PI;
            //System.out.println(errorAngleDeg + " =+= " + errorAngle);
            for (DecisionScore ds2 : dsList) {
                if (Math.abs(ds2.angle - ds.angle) <= errorAngleDeg) {

                    finalBall = new MYPosition((finalBall.getX()*count + ds2.finalBall.getX()) / (count + 1),
                            (finalBall.getY()*count + ds2.finalBall.getY()) / (count + 1), ds.finalBall.z);
                    if (ds2.forUs) {
                        ++countForUs;
                    }
                    if (ds2.isGoal != 0) {
                        countIsGoal += ds2.isGoal;
                    }
                    ++count;
                }
            }
        }

        public boolean isBetter(AverageDecisionScore avgDs) {
            if (countIsGoal/count != avgDs.countIsGoal/avgDs.count) {
                return countIsGoal/count > avgDs.countIsGoal/avgDs.count;
            }

            if (countForUs/count != avgDs.countForUs/avgDs.count) {
                return countForUs/count > avgDs.countForUs/avgDs.count;
            }

            return super.isBetter(avgDs);
        }
    }

    public AverageDecisionScore determineWhereToShot(int player, MYPosition ball) {
        AverageDecisionScore dsMin = null;
        double errorAngle = Constants.getErrorAngular(myPlayersDetails[player].getPrecision());

        int oldCannotShot = myPlayersCantShoot[player];
        double dz, oldDz = -1;
        myPlayersCantShoot[player] = Constants.ITERACIONES_GOLPEAR_BALON + 1;
        for (double strength = 0.1d; strength < 1.01; strength += 0.15d) {
            double speed = strength * Constants.getVelocidadRemate(myPlayersDetails[player].getPower());
            for (int angleZ = 0; angleZ <= 61; ++angleZ) {
                double radAngleZ = Math.max(0, Math.min(angleZ, Constants.ANGULO_VERTICAL_MAX)) * (Math.PI / 180d);
                double tempSpeed = speed * Math.cos(radAngleZ);
                dz = roundAtMultiple(tempSpeed * Math.sin(radAngleZ), Constants.G);
                if (dz == oldDz) {
                    continue;
                }
                oldDz = dz;
                //System.out.println("dz = " + dz);

                ArrayList<DecisionScore> dsList = new ArrayList<>();
                double anglestep = 3;
                for (double angle = 0; angle < 360; angle+=anglestep) {
                    MYPosition[] evolution = predictShot(player, ball, strength, angle, angleZ);
                    DecisionScore ds = new DecisionScore(evolution);
                    ds.strength = strength;
                    ds.angle = angle;
                    ds.angleZ = angleZ;
                    dsList.add(ds);
                    if (angle > 20 && angle < 160) {
                        anglestep = 2;
                    } else {
                        anglestep = 5;
                    }
                }
                for (DecisionScore ds : dsList) {
                    AverageDecisionScore avgDs = new AverageDecisionScore(ds, dsList, errorAngle);
                    if (dsMin == null || avgDs.isBetter(dsMin)) {
                        dsMin = avgDs;
                    }
                }
            }
        }

        myPlayersCantShoot[player] = oldCannotShot;


        dsMin.shooter = player;
        return dsMin;
    }

    private double roundAtMultiple(double valor, double divisor) {
        return Math.round(valor / divisor) * divisor;
    }

    public static final int MAX_PREVISION = 70;

    public MYPosition[] predictShot(int player, MYPosition ball, double iStrength, double iAngle, double iAngleZ){

        double fuerzaRemate=iStrength;
        double angHorizontal=iAngle*Math.PI/180;
        double angVer = Math.max(0, Math.min(iAngleZ, Constants.ANGULO_VERTICAL_MAX)) * (Math.PI / 180d);
        double vel = fuerzaRemate * Constants.getVelocidadRemate(myPlayersDetails[player].getPower());
        AbstractTrajectory trayectoria=new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);

        MYPosition[] res = new MYPosition[MAX_PREVISION];
        res[0] = ball;
        for (int step = 1; step < MAX_PREVISION; ++step) {
            double time=(double)step/60d;
            double desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
            double desplazamientoVertical = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
            double x = ball.getX() + desplazamientoHorizontal * Math.cos(angHorizontal);
            double y = ball.getY() + desplazamientoHorizontal * Math.sin(angHorizontal);
            double z = desplazamientoVertical;
            MYPosition nextBall = new MYPosition(x,y,z);
            res[step] = nextBall;
        }
       return res;
    }

    public boolean isOut(Position b){
        double MITAD_ANCHO = Constants.ANCHO_CAMPO_JUEGO / 2;
        double MITAD_LARGO = Constants.LARGO_CAMPO_JUEGO / 2;
        return !(b.getX() >= -MITAD_ANCHO && b.getX() <= MITAD_ANCHO &&
                b.getY() >= -MITAD_LARGO && b.getY() <= MITAD_LARGO);
    }

    public boolean isOut(MYPosition b){
        double MITAD_ANCHO = Constants.ANCHO_CAMPO_JUEGO / 2;
        double MITAD_LARGO = Constants.LARGO_CAMPO_JUEGO / 2;
        return !(b.getX() >= -MITAD_ANCHO && b.getX() <= MITAD_ANCHO &&
                b.getY() >= -MITAD_LARGO && b.getY() <= MITAD_LARGO);
    }

    public MYPosition[] predictBall(MYPosition ball, double iBallDx, double iBallDy, double iBallDz, int previsions,GameSituations sp){

        MYPosition[] res = new MYPosition[previsions];
        res[0] = ball;
        for (int step = 1; step < previsions; ++step) {
            double[] destino = sp.getTrajectory(step);
            //System.out.println(destino[0]+"-"+destino[1]+"-"+destino[2]);
            MYPosition nextBall = new MYPosition(destino[0],destino[1],destino[2]);
            res[step] = nextBall;
        }
        return res;
    }

    int nbturns = 0;

    @Override
    public List<Command> execute(GameSituations sp) {
        // Initialize the round
        initRound(sp);
        ++nbturns;


        if (ableToShot.length > 0) {
            AverageDecisionScore ds =  determineWhereToShot(ableToShot[0], ballwithZ);
            if (ds == null) {
                //System.out.println("ds null !!");
            } else {
                //System.out.println(myPlayersDetails[ds.shooter].getNumero() + " === " + ds.count);
                commands.add(new CommandHitBall(ds.shooter, ds.angle, ds.strength, ds.angleZ));
                availablePlayers.remove(new Integer(ds.shooter));

                if (ds.forUs) {
                    commands.add(new CommandMoveTo(ds.receiver, ds.finalBall.getPosition()));
                    availablePlayers.remove(new Integer(ds.receiver));
                    //System.out.println(myPlayersDetails[ds.shooter].getNumero() + "->" + myPlayersDetails[ds.receiver].getNumero() + " in " + ds.turn + " turns to (" + ds.finalBall.getX() + "," + ds.finalBall.getY() + ") : proba = " + (ds.countForUs/ds.count));
                } else if (ds.isGoal != 0) {
                    /*System.out.println(myPlayersDetails[ds.shooter].getNumero() + " shoots to score ! : proba = " + (ds.countIsGoal/ds.count));
                    for (int i = 0; i < ds.evol.length ; ++ i){
                     System.out.println("Evol " + i + ": " + ds.evol[i]);
                    }*/
                } else {
                    //System.out.println(myPlayersDetails[ds.shooter].getNumero() + "-> ??? in " + ds.turn + " turns to (" + ds.finalBall.getX() + "," + ds.finalBall.getY() + ") : probaForUs = " + (ds.countForUs/ds.count) + " probaToScore = " + (ds.countIsGoal/ds.count));
                }
            }
        }

        {
            MYPosition[] evolution = predictBall(ballwithZ, currentDx, currentDy, currentDz, MAX_PREVISION,sp);
            DecisionScore ds = new DecisionScore(evolution);
            theNextBall = evolution[1];
            attacking = ds.forUs || ballwithZ.getY() > 0;
        }

        { // The player who will be the nearest to the ball have to go there
            MYPosition[] evolution = predictBall(ballwithZ, currentDx, currentDy, currentDz, MAX_PREVISION,sp);
            DecisionScore ds = new DecisionScore(evolution, true);
            if (ds.forUs){
                commands.add(new CommandMoveTo(ds.receiver, ds.finalBall.getPosition()));
                availablePlayers.remove(new Integer(ds.receiver));
            } else if (ds.isGoal != 0) {
                commands.add(new CommandMoveTo(0, ds.finalBall.getPosition()));
                availablePlayers.remove(new Integer(0));
            }
        }

        for (int j : dangerousEnemies) {
            int iMin = -1;
            double distMin = 0;
            for (int i : availablePlayers) {
                if (i == 0 || (attacking && !defense[i])) continue;
                double dist = enemies[j].distance(myPlayers[i]);
                if (dist < distMin || iMin < 0) {
                    iMin = i;
                    distMin = dist;
                }
            }
            if (iMin < 0) break;

            commands.add(new CommandMoveTo(iMin, enemies[j].getPosition().movePosition(0, -1)));
            availablePlayers.remove(new Integer(iMin));
        }


        for (int i : availablePlayers) {
            int jMin = -1;
            double distMin = 0;
            for (int j = 0; j < enemies.length; ++j) {
                double dist = enemies[j].distance(myPlayers[i]);
                if (dist < distMin || jMin < 0) {
                    jMin = j;
                    distMin = dist;
                }
            }
            Position diff = new Position(myPlayers[i].getX() - enemies[jMin].getX(), myPlayers[i].getY() - enemies[jMin].getY());
            double dist = diff.distance();
            if (dist == 0) {
                diff = new Position(0, 1);
            } else {
                diff = new Position(diff.getX() / dist, diff.getY() / dist);
            }

            dist = Constants.getVelocidad(myPlayersDetails[i].getSpeed());
            MYPosition destination = myPlayers[i].getDiffPosition(diff.getX()*dist, diff.getY()*dist);

            MYPosition tempDest = new MYPosition((minDestinations[i].x + maxDestinations[i].x)/2, (minDestinations[i].y + maxDestinations[i].y)/2);
            dist = destination.distance(tempDest);
            double maxDist = 0;
            for (double x = minDestinations[i].x; x <= maxDestinations[i].x; ++x) {
                for (double y = minDestinations[i].y; y <= maxDestinations[i].y; ++y) {
                    dist = destination.distanceSQ(x,y);
                    if (dist > maxDist) {
                        maxDist = dist;
                        tempDest.x = x;
                        tempDest.y = y;
                    }
                }
            }
            //System.out.println("Destination set for player " + myPlayersDetails[i].getNumero());
            commands.add(new CommandMoveTo(i, tempDest.getPosition()));

        }
                // es córner
        if(sp.isStarts() && (sp.ballPosition()==Constants.cornerSupDer || sp.ballPosition()==Constants.cornerSupDer)){
            int jugador = sp.ballPosition().nearestIndex(sp.myPlayers());
            if(jugador!=10){
                commands.add(new CommandHitBall(jugador, sp.myPlayers()[10],1,true));
            }else{
                commands.add(new CommandHitBall(jugador, Constants.centroArcoSup,1,true));
            }
        }
        return commands;
    }
}
