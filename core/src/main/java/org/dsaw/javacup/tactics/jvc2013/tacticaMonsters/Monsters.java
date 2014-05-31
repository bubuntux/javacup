/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.tacticaMonsters;


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
import java.util.LinkedList;
import java.util.List;

public class Monsters implements Tactic {
GameSituations spAct;
Simulador sim=new Simulador();
LinkedList<Command> comandos = new LinkedList<Command>();
    Position alineacion1[]=new Position[]{
        //(-30,30)(-50..50)
        new Position(0,-49),
        new Position(6,-46),
        new Position(-6,-46),
        new Position(-12,-25),
        new Position(12,-25),
        new Position(0,0),

        new Position(-18,6),
        new Position(18,6),
        new Position(5,21),

        new Position(-5,21),
        new Position(0.0,42.047511312217196)
        
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

    private void aPorPelota(int jugador) {
       
        Position pos=spAct.myPlayers()[jugador];
        double alturaControl=Constants.ALTURA_CONTROL_BALON;
        if(jugador==0){alturaControl= Constants.ALTO_ARCO;}
        double velocidad=0.25+spAct.myPlayersDetail()[jugador].getSpeed()*0.25;
        for(int i=0;i<500;i++){
            double[] posBalon3c=spAct.getTrajectory(i);
            if(posBalon3c[2]>alturaControl){continue;}
            Position posBalon=new Position(posBalon3c[0],posBalon3c[1]);
            double dist=pos.distance(posBalon);
            if(dist-Constants.DISTANCIA_CONTROL_BALON<=velocidad*i){
                comandos.add(new CommandMoveTo(jugador, posBalon));
                break;
            }
        }
    }

    class TacticaDetalleImpl implements TacticDetail {

        public String getTacticName() {
            return "Monsters";
        }

        public CountryCode getCountry() {
            return CountryCode.DE;
        }

        public String getCoach() {
            return "Maicky Materson";
        }

        public Color getShirtColor() {
            return new Color(51, 255, 0);
        }

        public Color getShortsColor() {
            return new Color(255, 255, 255);
        }

        public Color getShirtLineColor() {
            return new Color(255, 51, 51);
        }

        public Color getSocksColor() {
            return new Color(255, 0, 0);
        }

        public Color getGoalKeeper() {
            return new Color(150, 85, 35        );
        }

        public UniformStyle getStyle() {
            return UniformStyle.SIN_ESTILO;
        }

        public Color getShirtColor2() {
            return new Color(68, 79, 16);
        }

        public Color getShortsColor2() {
            return new Color(23, 52, 180);
        }

        public Color getShirtLineColor2() {
            return new Color(109, 53, 160);
        }

        public Color getSocksColor2() {
            return new Color(203, 107, 206);
        }

        public Color getGoalKeeper2() {
            return new Color(13, 111, 103        );
        }

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
                this.nombre=nombre;
                this.numero=numero;
                this.piel=piel;
                this.pelo=pelo;
                this.velocidad=velocidad;
                this.remate=remate;
                this.presicion=presicion;
                this.portero=portero;
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
                new JugadorImpl("Mark Lenders", 1, new Color(51,0,0), new Color(255,255,255),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Michael Jordan", 2, new Color(51,51,51), new Color(255,255,255),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Hubert Farnsworth", 3, new Color(255,204,204), new Color(255,255,255),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Roberto", 4, new Color(255,102,51), new Color(255,255,255),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Zoidberg", 5, new Color(255,102,102),new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Trokero", 6, new Color(255,204,204), new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Txori", 7, new Color(255,0,0), new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Jonan", 8, new Color(255,0,0), new Color(255,255,255),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Josepo", 9, new Color(255,0,0), new Color(255,255,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Harringui", 10, new Color(255,0,0), new Color(255,255,255),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Jonhy Temeto", 11, new Color(255,0,0), new Color(255,255,255),1.0d,1.0d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticaDetalleImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion5;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
    }

    public void posicionarJugadores(){
         Position[] jugadores = spAct.myPlayers();
        for (int i = 0; i < jugadores.length; i++) {
            comandos.add(new CommandMoveTo(i, Alineacion.obtPos(i, spAct.ballPosition())));
        }

    }
    public void irAporPelota(){
         if (!spAct.isRivalStarts()) {
            int[] recuperadores = spAct.getRecoveryBall();
            if (recuperadores.length > 1) {
                double[] posRecuperacion = spAct.getTrajectory(recuperadores[0]);
                for (int i = 1; i < recuperadores.length; i++) {
                    Position posRecu=new Position(posRecuperacion[0], posRecuperacion[1]);
                    if(recuperadores[i]==0&&posRecu.getY()<-38&&Math.abs(posRecu.getX())<10){
                    comandos.add(new CommandMoveTo(recuperadores[i],posRecu));}
                    if(recuperadores[i]==10&&(recuperadores.length==1||(posRecu.getY()>32))){
                    comandos.add(new CommandMoveTo(recuperadores[i],posRecu));}
                    if(recuperadores[i]>0&&recuperadores[i]<10){
                    comandos.add(new CommandMoveTo(recuperadores[i],posRecu));}
                 }
                 Position posRecu=new Position(posRecuperacion[0], posRecuperacion[1]);
                if(posRecu.getY()<-38&&Math.abs(posRecu.getX())<15){

                        aPorPelota(1);
                        aPorPelota(2);
                }
            }


        }
    }

    public List<Command> execute(GameSituations sp) {

       
        spAct=sp;
        comandos.clear();
        posicionarJugadores();
        irAporPelota();

        if(spAct.canKick().length>0){
             for (int i : sp.canKick()) {
                 if(i<3){//despejar
                     //if(i==0){System.out.println("porteroo"+spAct.iteration());}
                     comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 45));
                 }else if(sp.ballPosition().distance(Constants.centroArcoSup)<35){//tiro desde cerca
                     double tiro[]=sim.calcularTiroCercano(spAct.ballAltitude(),
                             sp.ballPosition().distance(Constants.centroArcoSup),
                             spAct.myPlayersDetail()[i].getPower()*1.2+1.2);
                     comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, tiro[0]));
                 }else{
                     double[] distMaxPosible=sim.distanciaHastaControlBalon2(spAct.ballAltitude(),
                     spAct.myPlayersDetail()[i].getPower()*1.2+1.2, 45, true);
                      boolean tiroLejano=false;
                     if(sp.ballPosition().distance(Constants.centroArcoSup)<distMaxPosible[0]){
                         //puedo tirar de lejos
                         tiroLejano=true;
                         if(tiroLejano){
                                double[] tiro= sim.calcularTiroAlto(sp.ballAltitude(),
                                 sp.ballPosition().distance(Constants.centroArcoSup),
                            1.2+1.2*sp.myPlayersDetail()[i].getPower());
                                comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
                                        tiro[0], tiro[1]));

                         }


                     }
                     if(!tiroLejano){
                        //buscamos el pase
                         int jug=jugAdelantadoPosible(i);
                         boolean pase2=false;
                        if(jug!=-1){

                            float factor=1;
                            if(spAct.isStarts()&&Math.abs(spAct.ballPosition().getX())>30){
                                factor=(float) 0.4;
                            }
                             double[] pase=sim.calcularPaseAire((1.2+1.2*spAct.myPlayersDetail()[i].getPower())*factor,
                            spAct.myPlayers()[jug].distance(sp.ballPosition()), sp.ballAltitude());
                    if(pase[0]!=-1){
                        pase2=true;
                        //System.out.println("pase!");
                        //System.out.println("pase Bueno :) "+spAct.iteration());
                        comandos.add(new CommandHitBall(i, sp.myPlayers()[jug], pase[0], pase[1]));
                    }else{
                        //System.out.println("paseSecon!");
                        //System.out.println("pase maloo! "+spAct.iteration());
                         comandos.add(new CommandHitBall(i, sp.myPlayers()[jug], 1,45 ));
                         pase2=true;
                    }
                }else{
                     
                }
                if(!pase2){
                    //System.out.println("Tiro!!! defecto "+i);
                   // System.out.println("aleatorio "+spAct.iteration());
                    comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 20));

                }
                         
                     }

                 }

             }
        }
       
        
        
        
        return comandos;
    }



     public int[] iteracionesDelEnemigoHastaPosicion(Position posObj,int maxI){
         //respuesta 0 en que iteration 1 cuantos
        int[] respuesta=new int[2];
        boolean resuelto=false;
        for(int itAct=0;itAct<maxI;itAct++)
        {

            int aux=0;
            int cuantos=0;
            for(Position posAct:spAct.rivalPlayers()){
                double distancia=posObj.distance(posAct);
                double distanciaControl=Constants.DISTANCIA_CONTROL_BALON;
                if(spAct.rivalPlayersDetail()[aux].isGoalKeeper()){
                     distanciaControl=Constants.DISTANCIA_CONTROL_BALON_PORTERO;
                }
                double velocidadJugador=spAct.rivalPlayersDetail()[aux].getSpeed();
                if(distancia-(distanciaControl+velocidadJugador*itAct)<=0){
                    //llegan
                    cuantos++;
                    resuelto=true;
                    respuesta[0]=itAct;

                }


                aux++;

            }
            if(resuelto){
                    respuesta[1]=cuantos;
                    return respuesta;

                }
         }
        respuesta[0]=-1;
        return respuesta;
    }

     public int simularPase(float fuerza, float angulo,Position posAct){
         final int  paseBueno=0;
         final int paseMediano=1;
         final int paseMalo=2;
         return -1;


     }
     public  Simulador.datosTiro calcularPase(Position posAct,int jugador,PlayerDetail jd){
         
         Simulador.datosTiro mejorTiro = null;

         double distActP=posAct.distance(Constants.centroArcoSup);
         for (Position posPos:spAct.myPlayers()){
            double distPosibleP=posPos.distance(Constants.centroArcoSup);
            if(distPosibleP<distActP){
                Simulador.datosTiro tiro=sim.calcularPase(spAct, jd, posAct, posPos);
                if(tiro.tipo==Simulador.PaseBueno||tiro.tipo==Simulador.PaseMediano){

                    mejorTiro=tiro;
                    break;

                }

            }
         }
         return mejorTiro;
         
     }

     public int jugAdelantadoPosible(int j){
         double distAct=spAct.ballPosition().distance(Constants.centroArcoSup);
         int jug=-1;
         double distancia=99999;

         double[] distMaxPosible=sim.distanciaHastaControlBalon2(spAct.ballAltitude(),
                     spAct.myPlayersDetail()[j].getPower()*1.2+1.2, 45, false);
                     // System.out.println("distancia maxx  "+distMaxPosible[0]+"  fuerza"+(spAct.myPlayersDetail()[j].getPower()*1.2+1.2));

         for(int i=0;i<11;i++){
             double distBalon=spAct.ballPosition().distance(spAct.myPlayers()[i]);
             

             if(distMaxPosible[0]>=distBalon){
                double distPos=spAct.myPlayers()[i].distance(Constants.centroArcoSup);
                if(distAct>distPos){
                  distAct=distPos;
                 
                    jug=i;
                }
         }
         }
         return jug;
     }


    
     

}