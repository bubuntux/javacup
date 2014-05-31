package org.dsaw.javacup.tactics.jvc2013.diversion;

/*
 * Licence: To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

/**
 *
 * @author carlos
 */
public class NossoUtil {
    // CONSTANTES
    static final double forcaToque = 1.0;
    static final double EPS=1e-9;
    static final double MENOR_INTERCEPT = 5.0;
    static final double DISTANCIA_CHUTE_GOL = 35.0;
    static final double DISTANCIA_TOQUE = 20.0;

    static double dist( double x1, double y1, double x2, double y2 ){
        return Math.sqrt( Math.pow(x1-x2,2)+ Math.pow(y1-y2,2) );
    }
    static double dist( Position pos1, Position pos2 ){
        return dist( pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY() );
    }

    static double distToLineSegment( Position orig, Position dest, Position rival )
    {
        double ax=orig.getX();
        double ay=orig.getY();
        double bx=dest.getX();
        double by=dest.getY();
        double px=rival.getX();
        double py=rival.getY();

        if( (bx - ax) * (px - ax) + ( by - ay ) * ( py - ay ) < EPS )
            return dist( ax, ay, px, py );

        if( (ax-bx)*(px-bx) + (ay-by)*(py-by) < EPS )
            return dist( bx,by,px,py);
        return distToLine( ax,ay,bx,by,px,py );
    }

    static double distToLine(
        double ax, double ay,
        double bx, double by,
        double px, double py
        )
    {
        double proj = ( (px-ax)*(bx-ax) + (py-ay)*(by-ay) ) /
                ((bx-ax)*(bx-ax)+(by-ay)*(by-ay));
        double cpx = ax+proj*(bx-ax);
        double cpy = ay+proj*(by-ay);
        return dist( px, py, cpx, cpy );
    }

    static boolean posToque( Position orig, Position dest, Position[] adversarios ){
        for( int i=1; i<11; i++ ){
            if( NossoUtil.distToLineSegment(orig, dest, adversarios[i]) < NossoUtil.MENOR_INTERCEPT )
                return false;
        }
        return true;
    }

    static double getAnguloNecesario( Position from, Position to ){
        return (Math.atan2( to.getY()-from.getY(), to.getX()-from.getX() )*180.0)/Math.PI; // A gravidade era 0,12; mas para nos, vai ser 12
    }
    static double elevacao( double V ){
        double angle = (Math.asin( Math.sqrt(2* Constants.G*Constants.ALTO_ARCO)/V )*180.0)/Math.PI;
        //System.out.println(angle);
        if( angle>= 0.0 && angle <=360)
            return angle;
        return 10;
    }

    static Command tocarAoProximo( int i, Position[] jogadores, Position[] rivais, int[][] ordenAvaliacao ){
        if( i<ordenAvaliacao.length ){
            for( int j=0; j<ordenAvaliacao[i].length; j++ ){
                if( posToque(jogadores[i], jogadores[ordenAvaliacao[i][j]], rivais) )
                    return new CommandHitBall(
                            i,
                            jogadores[ordenAvaliacao[i][j]],
                            1.0,
                            0.0);
            }
        }
        return null;
    }

    static Command chutarGol( int i, double precisao, double velocidade, Position pos, Position[] rivais ) {
        int goleiro =  getProximoJogador(Constants.centroArcoSup, rivais);
        double angulo=90, anguloZ=0;
        precisao *= Constants.ERROR_MIN;
        velocidade *= Constants.VELOCIDAD_MAX;
        if( rivais[goleiro].getX()<0.0 ){
            angulo = getAnguloNecesario( pos, Constants.posteDerArcoSup );
            if( pos.getX() > Constants.posteDerArcoSup.getX() )
                angulo *= (1.0+precisao);
            else
                angulo *= (1.0-precisao);

        } else {
            angulo = getAnguloNecesario( pos, Constants.posteIzqArcoSup );
            if( pos.getX() < Constants.posteIzqArcoSup.getX() )
                angulo *= (1.0+precisao);
            else
                angulo *= (1.0-precisao);
        }
        anguloZ = (1.0-precisao)*elevacao(velocidade );

        return new CommandHitBall(i,angulo,1.0,anguloZ);
    }
    static boolean possoChutar( Position pos ){
        return (NossoUtil.dist(pos, Constants.centroArcoSup) < NossoUtil.DISTANCIA_CHUTE_GOL);
    }

    static int getProximoJogador( int orig, Position[] jogadores ){
        double distMin = Double.MAX_VALUE;
        int idxDistMin = 9;
        for( int i=1; i<11; i++ ){
            if( i!=orig ){
                double distTmp = NossoUtil.dist( jogadores[orig].getX(), jogadores[orig].getY(), jogadores[i].getX(), jogadores[i].getY());
                if( distTmp < distMin ){
                    distMin = distTmp;
                    idxDistMin = i;
                }
            }
        }
        return idxDistMin;
    }

    static int getProximoJogador( Position pos, Position[] jogadores ){
        double distMin = Double.MAX_VALUE;
        int idxDistMin = -1;
        for( int i=1; i<11; i++ ){
            double distTmp = NossoUtil.dist( pos, jogadores[i]);
            if( distTmp < distMin ){
                distMin = distTmp;
                idxDistMin = i;
            }
        }
        return idxDistMin;
    }
}