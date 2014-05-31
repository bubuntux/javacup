/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.tacticaMonsters;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

/**
 *
 * @author Goteck
 */
public class Alineacion {
    static Position alineacion1[]=new Position[]{

        new Position(0,-49),
        new Position(3,-48),
        new Position(-3,-48),
        new Position(-12,-20),
        new Position(12,-20),
        new Position(0,0),

        new Position(-18,6),
        new Position(18,6),
        new Position(5,21),

        new Position(-5,21),
        new Position(0.0,37.047511312217196)

    };
    static Position limite[]=new Position[]{

        new Position(0,0),
        new Position(0,0),
        new Position(0,0),
        new Position(-20,15),
        new Position(-25,15),
        new Position(-25,25),
        new Position(-25,20),
        new Position(-25,20),
        new Position(-25,13),
        new Position(-25,13),
        new Position(0,0)

    };
    public static Position obtPos(int jug, Position posBalon){
        if(jug==0){
            Position posP=Constants.centroArcoInf;
            double ang=posP.angle(posBalon);
            Position pos=posP.moveAngle(ang, 1);
            pos.movePosition(posBalon.getX()/6,0);
            return pos;
        }
        if(jug<3){
            Position posP=Constants.centroArcoInf;
            double ang=posP.angle(posBalon);
            if(jug==1){ang=ang+0.4;}
             if(jug==2){ang=ang-0.4;}
            Position pos=posP.moveAngle(ang, 7);
            return pos;
        }

        else{
        float valY=(float) (alineacion1[jug].getY() + posBalon.getY());
        valY=(float) Math.min(valY, alineacion1[jug].getY()+limite[jug].getY());
        valY=(float) Math.max(valY, alineacion1[jug].getY()+limite[jug].getX());
        
        Position pos=new Position(alineacion1[jug].getX()+posBalon.getX()/2.5, valY);
        if(jug==10){
             Position posP= Constants.centroArcoSup;
            double ang=posP.angle(posBalon);
            pos=posP.moveAngle(ang, 20);
            pos.movePosition(posBalon.getX()/6,0);
            return pos;
        }
        return pos;
        }
    }

}
