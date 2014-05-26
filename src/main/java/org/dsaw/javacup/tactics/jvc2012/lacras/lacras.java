package org.dsaw.javacup.tactics.jvc2012.lacras;

import java.awt.Color;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.UniformStyle;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;

import java.util.ArrayList;

public class lacras implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.0,-52.02488687782805),
        new Position(-19.46564885496183,-31.6044776119403),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(19.496503496503497,-31.83257918552036),
        new Position(-19.25874125874126,-5.701357466063349),
        new Position(0.7132867132867133,-13.303167420814479),
        new Position(-0.4755244755244755,0.23755656108597287),
        new Position(19.25874125874126,-2.8506787330316743),
        new Position(-14.274809160305344,30.559701492537314),
        new Position(-0.951048951048951,17.104072398190045),
        new Position(12.601398601398602,30.16968325791855)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-14.265734265734267,-34.20814479638009),
        new Position(0.951048951048951,-27.08144796380091),
        new Position(16.405594405594407,-33.970588235294116),
        new Position(-8.55944055944056,-16.391402714932127),
        new Position(7.846153846153847,-16.153846153846153),
        new Position(-17.356643356643357,-7.364253393665159),
        new Position(18.06993006993007,-0.23755656108597287),
        new Position(-9.510489510489512,-0.23755656108597287),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(7.132867132867133,-5.9389140271493215)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-16.88111888111888,-31.83257918552036),
        new Position(0.0,-38.95927601809955),
        new Position(16.167832167832167,-32.07013574660634),
        new Position(6.6573426573426575,-32.30769230769231),
        new Position(-6.6573426573426575,-32.782805429864254),
        new Position(-24.65648854961832,-2.3507462686567164),
        new Position(23.099236641221374,-2.873134328358209),
        new Position(-8.55944055944056,-4.038461538461538),
        new Position(-0.951048951048951,-9.264705882352942),
        new Position(7.608391608391608,-5.463800904977376)
    };
    double xadd = ((Constants.ANCHO_CAMPO_JUEGO/3)/2);
    double yadd = (Constants.LARGO_CAMPO_JUEGO/6)/2;
    Position[]fZ=focusZones();



    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "Lacras";
        }

        @Override
        public String getCountry() {
            return "España";
        }

        @Override
        public String getCoach() {
            return "Marco";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 0, 204);
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
            return new Color(0, 0, 255);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(255, 102, 0        );
        }

        @Override
        public UniformStyle getStyle() {
            return UniformStyle.FRANJA_DIAGONAL;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(204, 0, 0);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(130, 226, 207);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(0, 204, 51        );
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
                new JugadorImpl("Cano", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, true),
                new JugadorImpl("Rubia", 2, new Color(255,200,150), new Color(204,204,0),1.0d,0.66d,0.5d, false),
                new JugadorImpl("Negro", 3, new Color(255,255,255), new Color(255,255,255),1.0d,0.66d,0.5d, false),
                new JugadorImpl("Furier", 4, new Color(51,0,0), new Color(50,0,0),1.0d,0.66d,0.5d, false),
                new JugadorImpl("sr burns", 5, new Color(255,200,150), new Color(204,204,204),1.0d,0.66d,0.5d, false),
                new JugadorImpl("Araya", 6, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Migue", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.66d,0.5d, false),
                new JugadorImpl("Dany", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.66d,0.5d, false),
                new JugadorImpl("Tron", 9, new Color(153,153,255), new Color(102,102,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Hulk", 10, new Color(0,153,0), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Chicle", 11, new Color(255,102,255), new Color(153,0,0),1.0d,1.0d,1.0d, false)
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
    return alineacion2;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion3;
    }
    
    //Empezamos con la táctica
    ArrayList<Command> commands;

    
    public lacras(){
        commands = new ArrayList<>();
    }
    
    //Deprecated
    //¿Tenemos la bola? 1-YES 2-NO 3-AIR
    public int powerOnTheBall(double altura, Position[] myPlayers,Position[] rivalPlayers, Position ball){
        if(altura>Constants.ALTURA_CONTROL_BALON)
            return 3;
        else{
            for(int i=0; i<11; i++){
                if(myPlayers[i].distance(ball)<=(Constants.DISTANCIA_CONTROL_BALON))
                    return 1;
            }
        }
        int myN = ball.nearestIndex(myPlayers);
        int rivalN = ball.nearestIndex(rivalPlayers);
        if(ball.distance(myPlayers[myN])<=ball.distance(rivalPlayers[rivalN]))
            return 1;
        return 2;
    }
    
    //Posicion estandar de cada jugador y se moveran si es necesario
    //Debe ser de los primeros metodos a llamar, y ya dependiendo de la situación
    //algunas ordenes se sobrescribiran posteriormente
    public void basicAlignment(){
        //for(int i=0; i<alineacion1.length; i++)
            //commands.add(new CommandMoveTo (i, alineacion1[i]));
        commands.add(new CommandMoveTo (1, fZ[3]));
        commands.add(new CommandMoveTo (2, fZ[4]));
        commands.add(new CommandMoveTo (3, fZ[5]));
        commands.add(new CommandMoveTo (4, fZ[6]));
        commands.add(new CommandMoveTo (5, new Position(-Constants.RADIO_CIRCULO_CENTRAL,0)));
        commands.add(new CommandMoveTo (6, fZ[8]));
        commands.add(new CommandMoveTo (7, new Position(Constants.RADIO_CIRCULO_CENTRAL,0)));
        commands.add(new CommandMoveTo (8, fZ[10]));
        commands.add(new CommandMoveTo (9, new Position(fZ[12].getX()+xadd,fZ[12].getY())));
        commands.add(new CommandMoveTo (10, new Position(fZ[14].getX()-xadd,fZ[14].getY())));
    }
    
    //Dice en que zona estan los jugadores
    public int[][] playerZones(Position[] playerPos){
        int [][] zones = new int[18][12]; 
        //Primer elemento de cada fila dice la longitud "util" de esta o lo que es lo mismo
        //el numero de jugadore que hay en una zona determinada.
        for(int i=0; i<playerPos.length; i++){
            if(playerPos[i].getX()<Constants.cornerInfIzq.getX()+Constants.ANCHO_CAMPO_JUEGO/3){
                if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+Constants.LARGO_CAMPO_JUEGO/6){
                    zones[0][0]++;
                    zones[0][zones[0][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*2){
                    zones[3][0]++;
                    zones[3][zones[3][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*3){
                    zones[6][0]++;
                    zones[6][zones[6][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*4){
                    zones[9][0]++;
                    zones[9][zones[9][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*5){
                    zones[12][0]++;
                    zones[12][zones[12][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+Constants.LARGO_CAMPO_JUEGO){
                    zones[15][0]++;
                    zones[15][zones[15][0]] = i;
                }
            }
            else if(playerPos[i].getX()>Constants.cornerInfDer.getX()-Constants.ANCHO_CAMPO_JUEGO/3){
                if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+Constants.LARGO_CAMPO_JUEGO/6){
                    zones[2][0]++;
                    zones[2][zones[2][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*2){
                    zones[5][0]++;
                    zones[5][zones[5][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*3){
                    zones[8][0]++;
                    zones[8][zones[8][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*4){
                    zones[11][0]++;
                    zones[11][zones[11][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*5){
                    zones[14][0]++;
                    zones[14][zones[14][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+Constants.LARGO_CAMPO_JUEGO){
                    zones[17][0]++;
                    zones[17][zones[17][0]] = i;
                }
            }
            else
                if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+Constants.LARGO_CAMPO_JUEGO/6){
                    zones[1][0]++;
                    zones[1][zones[1][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*2){
                    zones[4][0]++;
                    zones[4][zones[4][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*3){
                    zones[7][0]++;
                    zones[7][zones[7][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*4){
                    zones[10][0]++;
                    zones[10][zones[10][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+(Constants.LARGO_CAMPO_JUEGO/6)*5){
                    zones[13][0]++;
                    zones[13][zones[13][0]] = i;
                }
                else if(playerPos[i].getY()<Constants.cornerInfIzq.getY()+Constants.LARGO_CAMPO_JUEGO){
                    zones[16][0]++;
                    zones[16][zones[16][0]] = i;
                }
        }
        return zones;
    }
    
    //Devuelve un array con el centro de cada posicion
    public Position[] focusZones (){
        Position [] pos = new Position [18];
        double xleft = (Constants.cornerInfIzq.getX()+(Constants.ANCHO_CAMPO_JUEGO/3)/2);
        double xmid = 0;
        double xright = (Constants.cornerInfDer.getX()-(Constants.ANCHO_CAMPO_JUEGO/3)/2);
        
        pos[0]= new Position(xleft,Constants.centroArcoInf.getY()+yadd);
        pos[1]= new Position(xmid,Constants.centroArcoInf.getY()+yadd);
        pos[2]= new Position(xright,Constants.centroArcoInf.getY()+yadd);
        pos[3]= new Position(xleft,Constants.centroArcoInf.getY()+yadd*3);
        pos[4]= new Position(xmid,Constants.centroArcoInf.getY()+yadd*3);
        pos[5]= new Position(xright,Constants.centroArcoInf.getY()+yadd*3);
        pos[6]= new Position(xleft,Constants.centroArcoInf.getY()+yadd*5);
        pos[7]= new Position(xmid,Constants.centroArcoInf.getY()+yadd*5);
        pos[8]= new Position(xright,Constants.centroArcoInf.getY()+yadd*5);
        pos[9]= new Position(xleft,Constants.centroArcoInf.getY()+yadd*7);
        pos[10]= new Position(xmid,Constants.centroArcoInf.getY()+yadd*7);
        pos[11]= new Position(xright,Constants.centroArcoInf.getY()+yadd*7);
        pos[12]= new Position(xleft,Constants.centroArcoInf.getY()+yadd*9);
        pos[13]= new Position(xmid,Constants.centroArcoInf.getY()+yadd*9);
        pos[14]= new Position(xright,Constants.centroArcoInf.getY()+yadd*9);
        pos[15]= new Position(xleft,Constants.centroArcoInf.getY()+yadd*11);
        pos[16]= new Position(xmid,Constants.centroArcoInf.getY()+yadd*11);
        pos[17]= new Position(xright,Constants.centroArcoInf.getY()+yadd*11);
        return pos;
    }
    
    //Saca los angulos dados tres lados(triangulo)
    /**Devuelve (punta, base_left, base_right)*/
    public double[] angles3P(double base, double left, double right){
        double [] angles = new double [3];
        angles[0] = Math.acos(((base*base)-(right*right)-(left*left))/(-2*right*left));
        angles[2] = Math.acos(((left*left)-(base*base)-(right*right))/(-2*base*right));
        angles[1] = Math.PI-angles[0]-angles[2];
        return angles;
    }
    
    //Falta optimizar
    /**Devuelve la posicion que debe situarse el defensor
       a partir de la posicion del atacante*/
    public Position defensivePosition(Position rival){
        double [] angles;
        double dRight = rival.distance(Constants.posteDerArcoInf);
        double dLeft = rival.distance(Constants.posteIzqArcoInf);
        angles = angles3P(Constants.LARGO_ARCO, dLeft, dRight);
        double pAngle = Math.PI-(angles[0]/2)-angles[2];
        double x = 0, y = rival.getY()-1;
        double dPR, dAux, dAuxF, aAux;

        if(rival.getX()<0){
            dPR = (dRight*Math.sin(angles[2]))/Math.sin(pAngle);
            dAux = (dRight*Math.sin(angles[0]/2))/Math.sin(pAngle)/dPR;
            dAuxF = dRight/dPR;
            //Ya tenemos el triangulo a distancia 1 entre P y R
            x = rival.getX()+((Math.sin(Math.PI/2-angles[2])*dAuxF)-dAux);
            y = rival.getY()-(Math.cos(Math.PI/2-angles[2])*dAuxF);
        }
        if(rival.getX()>0){
            aAux = Math.PI/2-pAngle;
            x = rival.getX()-Math.sin(aAux);
            y = rival.getY()-Math.cos(aAux);
        }
        return new Position(x, y);
    }
    
    /**
     * Recuperción de balon y cuantos queremos q vallan
     * Devuelve int[] de los jugadores que van
     */
    public int[] catchBall (GameSituations mySp, int howMany){
        Position [] myPos = mySp.myPlayers();
        Position [] rivalPos = mySp.rivalPlayers();
        Position [] myPlayers = new Position [11];
        MyTrajectory jty = predictedTrajectory(mySp, false);
        double dist,dist2;
        int jaux = 0;
        boolean isPosible = false;
        ArrayList<Double> whoP = new ArrayList<>();
        int[] select = new int[1];
        
        for(int j=0; j<jty.rebounds.size()&&!isPosible; j++){
            for(int i=1; i<myPos.length; i++){
                dist = j*mySp.getMyPlayerSpeed(i);//distancia que da mi jugador
                dist2 = myPos[i].distance(jty.rebounds.get(j));
                if(dist<=dist2){
                    whoP.add(dist2);
                    whoP.add((double) i);
                    //myPlayers[i]= new Position(jty.rebounds.get(j).getX(), jty.rebounds.get(j).getY());
                    //commands.add(new CommandMoveTo(i,myPlayers[i]));
                    isPosible = true;
                    jaux=j;
                }
            }
        }
        bucle:
        for(int j=0; j<jty.rebounds.size()&&isPosible; j++){
            for(int i=0; i<rivalPos.length; i++){
                dist = j*mySp.getMyPlayerSpeed(i);
                if(dist<=rivalPos[i].distance(jty.rebounds.get(j))&&j<jaux){
                    isPosible = false;
                    break;
                }
                if(j>jaux)
                    break bucle;
            }
        }
        if(isPosible){
            for (int i = 2; i < whoP.size(); i = i + 2) {
                for (int j = 0; j < i; j = j + 2) {
                    if (whoP.get(i) < whoP.get(j)) {
                        dist2 = whoP.get(i);
                        dist = whoP.get(i + 1);
                        whoP.set(i, whoP.get(j));
                        whoP.set(i + 1, whoP.get(j + 1));
                        whoP.set(j, dist2);
                        whoP.set(j + 1, dist);
                    }
                }
            }
            for (int i = whoP.size() - 1; i >= 0; i = i - 2) {
                whoP.remove(i - 1);
            }
            if(whoP.size()<howMany){
                howMany = whoP.size();
            }
            select = new int[howMany];
            for(int i=0; i<howMany; i++){
                select[i] = (int) whoP.get(i).doubleValue();
                myPlayers[select[i]]= new Position(jty.rebounds.get(jaux).getX(), jty.rebounds.get(jaux).getY());
                commands.add(new CommandMoveTo(select[i],myPlayers[select[i]]));
            }
        }
        else{
            select[0] = mySp.ballPosition().nearestIndex(myPos, 0);
            commands.add(new CommandMoveTo(select[0], mySp.ballPosition()));
        }
        //(solo si tenemos que sacar)
        if(mySp.isStarts())
            select[0] = mySp.ballPosition().nearestIndex(myPos);
            commands.add(new CommandMoveTo(select[0], mySp.ballPosition()));
        return select;
    }
    
    //Que hace cada jugador
    public void brains(GameSituations mySp){
        //Portero
        Position [] myPlayers = new Position [11];
        double dPorter = Constants.LARGO_ARCO;
        double dRight = mySp.ballPosition().distance(Constants.posteDerArcoInf);
        double dLeft = mySp.ballPosition().distance(Constants.posteIzqArcoInf);
        double ballAngle = Math.acos(((dPorter*dPorter)-(dRight*dRight)-(dLeft*dLeft))/(-2*dRight*dLeft));
        double rightAngle = Math.acos(((dLeft*dLeft)-(dPorter*dPorter)-(dRight*dRight))/(-2*dPorter*dRight));
        double finalAngle = Math.PI-(ballAngle/2)-rightAngle;
        //double dPorterBall = (dRight*Math.sin(rightAngle))/Math.sin(finalAngle);
        double dPorterRight = (dRight*Math.sin(ballAngle/2))/Math.sin(finalAngle);
        myPlayers[0] = new Position((Constants.posteDerArcoInf.getX()-dPorterRight), alineacion1[0].getY());
        commands.add(new CommandMoveTo (0, myPlayers[0]));
        //Comprobamos que no nos han tirado a puerta
        MyTrajectory pty = predictedTrajectory(mySp, true);
        for(int i=0; i<pty.rebounds.size(); i++){
            if(Math.abs(pty.rebounds.get(i).getX())<Constants.posteDerArcoInf.getX()&&
                    pty.rebounds.get(i).getY()<(Constants.centroArcoInf.getY()+Constants.ANCHO_AREA_CHICA)){
                myPlayers[0]= new Position(pty.rebounds.get(i).getX(), pty.rebounds.get(i).getY());
                commands.add(new CommandMoveTo (0, myPlayers[0]));
                break;
            }
        }
        
        //Jugadores
        Position [] rivalPos = mySp.rivalPlayers();
        Position [] myPos = mySp.myPlayers();
        int [][] rZones = playerZones(rivalPos);
        int [][] myZones = playerZones(myPos);
        //Con la bola en nuestro campo -> Defensivos
        if(mySp.ballPosition().getY()<0){
            commands.add(new CommandMoveTo (5, new Position(-Constants.RADIO_CIRCULO_CENTRAL,0-yadd*2)));
            commands.add(new CommandMoveTo (7, new Position(Constants.RADIO_CIRCULO_CENTRAL,0-yadd*2)));
            catchBall(mySp,1);
            for(int i=0; i<rivalPos.length; i++){
                if(rivalPos[i].getX()<(Constants.centroArcoInf.getX()-Constants.LARGO_AREA_CHICA/2)){
                    commands.add(new CommandMoveTo (1, new Position(rivalPos[i].getX(),fZ[3].getY())));
                    if(rivalPos[i].getY()<alineacion1[2].getY()){
                        myPlayers[1] = new Position (defensivePosition(rivalPos[i]));
                        commands.add(new CommandMoveTo (1, myPlayers[1]));
                    }
                }
                else if(rivalPos[i].getX()>(Constants.centroArcoInf.getX()+Constants.LARGO_AREA_CHICA/2)){
                    commands.add(new CommandMoveTo (3, new Position(rivalPos[i].getX(),fZ[5].getY())));
                    if(rivalPos[i].getY()<alineacion1[2].getY()){
                        myPlayers[3] = new Position (defensivePosition(rivalPos[i]));
                        commands.add(new CommandMoveTo (3, myPlayers[3]));
                    }
                }
                else{
                    commands.add(new CommandMoveTo (2, new Position(rivalPos[i].getX(),fZ[4].getY())));
                    if(rivalPos[i].getY()<alineacion1[2].getY()){
                        myPlayers[2] = new Position (defensivePosition(rivalPos[i]));
                        commands.add(new CommandMoveTo (2, myPlayers[2]));
                    }
                }
            }
        }
        
        //Con la bola en su campo -> Atacantes
        if(mySp.ballPosition().getY()>=0){
            catchBall(mySp,2);
        }
        //El mas cercano que valla a buscarla, excluimos el portero(solo para sacar)
        /*int [] recoList = mySp.getRecoveryBall();
        if(recoList.length>1){
            if(recoList[1]!=0||mySp.isStarts())
                commands.add(new CommandMoveTo (recoList[1], new Position (mySp.getTrajectory(recoList[0])[0],
                        mySp.getTrajectory(recoList[0])[1])));
            else{
                if(recoList.length>2)
                    commands.add(new CommandMoveTo (recoList[1], new Position (mySp.getTrajectory(recoList[0])[0],
                        mySp.getTrajectory(recoList[0])[1])));
            }
        }*/
        /*
        MyTrajectory jty = predictedTrajectory(mySp, false);
        System.out.println("air: "+jty.air);
        System.out.print("traject");
        for(int i=0; i<jty.rebounds.size(); i++){
            System.out.print(" "+jty.rebounds.get(i));
        }
        System.out.println();
        for(int i=0; i<jty.rebounds.size(); i++){
            System.out.print(" "+jty.iterations.get(i));
        }
        System.out.println();*/
    }
    
    
    //Propiedades de una trayectoria.
    //En principio nos da igual que la pelota valla alta o baja porque sacaremos
    //los puntos donde esta es recuperable.
    public class MyTrajectory {
        double x, y, m; //Atributos de la recta
        public boolean air; //En algun momento vuela
        public ArrayList<Position>  rebounds; //puntos donde será recuperable (si va a ras pos seran todos)
        public ArrayList<Integer>  iterations; //iteración asociada a cada punto (importante para interceptar)
    
        public MyTrajectory(){
            x = y = m = 0;
            rebounds = new ArrayList<>();
            iterations = new ArrayList<>();
            air = false;
        }
        public MyTrajectory (Position a, Position b){
            m = (b.getY()-a.getY())/(b.getX()-a.getX());
            x = (y-a.getY()+m*a.getX())/m;
            y = m*(x-a.getX())+a.getY();
        }
        public double getX (){
            return x;
        }
        public double getY (){
            return y;
        }
        public double getM (){
            return m;
        }
        public void setX (double x){
            this.x = x;
        }
        public void setY (double y){
            this.y = y;
        }
        public void setM (double m){
            this.m = m;
        }
    }
    
    //Predice donde se va a poder controlar la bola y en cuantas iteraciones estará ahí
    public MyTrajectory predictedTrajectory(GameSituations mySp, boolean porter){
        MyTrajectory ty = new MyTrajectory();
        double [] myPoint;
        double control = Constants.ALTURA_CONTROL_BALON;
        Position position;
        
        if(porter)
            control = Constants.ALTO_ARCO;
        //vamos cogiendo puntos
        for(int i=0; i!=-1; i++){
            myPoint = mySp.getTrajectory(i);
            position = new Position(myPoint[0], myPoint[1]);
            //Vemos si la pelota sigue en el campo
            if(Math.abs(position.getX())>(Constants.ANCHO_CAMPO_JUEGO/2)||
                    Math.abs(position.getY())>(Constants.LARGO_CAMPO_JUEGO/2))
                break;
            //Vemos si la pelota deja de moverse
            if(myPoint[0]==mySp.getTrajectory(i+1)[0]&&myPoint[1]==mySp.getTrajectory(i+1)[1])
                break;
            //Anotamos los puntos y iteraciones donde la pelota es controlable
            if(myPoint[2]<=control){
                ty.rebounds.add(position);
                ty.iterations.add(i);
            }
            //Anotamos si la trayectoria ha sido alta
            if(myPoint[2]>Constants.ALTURA_CONTROL_BALON&&!ty.air)
                ty.air = true;
        }
        return ty;
    }
    
    @Override
    public ArrayList<Command> execute(GameSituations sp) {
        Position [] myPlayers = sp.myPlayers();
        int nearest = sp.ballPosition().nearestIndex(myPlayers);
        int [] anyKick = sp.canKick();
        //int[] recover = sp.getRecoveryBall();
        //double [] fTrajectory = sp.getTrajectory(sp.iteration()+20);
        //double [] pTrajectory = sp.getTrajectory(1);
        
        /*if(recover.length>0){
            pTrajectory = sp.getTrajectory(recover[0]);
            System.out.println("traject: "+pTrajectory[0]+" "+pTrajectory[1]+" "+pTrajectory[2]);
        }*/
    
        basicAlignment();
        brains(sp);
        //commands.add(new CommandMoveTo(nearest, sp.ballPosition()));  
        /*if(sp.iteration()==itCounter){
            System.out.println("traject: "+pTrajectory[0]+" "+pTrajectory[1]+" "+pTrajectory[2]);
            System.out.println("traject: "+fTrajectory[0]+" "+fTrajectory[1]+" "+fTrajectory[2]);
            itCounter = itCounter+20;
        }*/
        if (anyKick.length>0&&sp.ballPosition().getY()<Constants.RADIO_CIRCULO_CENTRAL){
            commands.add(new CommandHitBall(nearest, Constants.centroArcoSup,1,40));
        }
        else if (anyKick.length>0&&sp.ballPosition().getY()<Constants.RADIO_CIRCULO_CENTRAL){
            commands.add(new CommandHitBall(nearest, Constants.centroArcoSup,1,25));
        }
        else if (anyKick.length>0&&sp.ballPosition().getY()<15){
            commands.add(new CommandHitBall(nearest, Constants.centroArcoSup,1,20));
        }
        else if (anyKick.length>0&&sp.ballPosition().getY()<20){
            commands.add(new CommandHitBall(nearest, Constants.centroArcoSup,1,15));
        }
        else if(anyKick.length>0)
            commands.add(new CommandHitBall(nearest, Constants.centroArcoSup,1,false));
        /*
        if (anyKick.length>0&&forward==true){
            commands.add(new CommandMoveTo(nearest, Constants.centroArcoInf));
        }
        if (1!=powerOnTheBall(sp.ballAltitude(),myPlayers,rivalPlayers,sp.ballPosition()))
            forward = false;*/
        /*int [] zones;
        int total = 0;
        if (nearest != nearestOld){
            System.out.println("cercano "+(nearest+1));
            zones = managementZones(sp);
            for(int i=0; i<zones.length; i++){
                System.out.print(" "+zones[i]);
                total+=zones[i];
            }
            System.out.println(" total "+total);
            nearestOld = nearest;
        }/*
        if(powerOnTheBall != powerOld){
            System.out.println(powerOnTheBall(sp.ballAltitude(),myPlayers,rivalPlayers,sp.ballPosition()));
            powerOld = powerOnTheBall;
        }*/
        //System.out.println("ballPosition: "+sp.ballPosition()+" altitude: "+sp.ballAltitude());
        //System.out.println("traject: "+pTrajectory[0]+" "+pTrajectory[1]+" "+pTrajectory[2]);
        return commands;
    }
}