/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.moleteam;

import org.javahispano.javacup.model.util.Position;
import java.util.ArrayList;

/**
 *
 * @author jlosarcos
 */
class Helpers {
 public static Position IntersectionOfTwoSegments(Position line1Position1,
            Position line1Position2,
            Position line2Position1, 
            Position line2Position2)
    {
        double x1 = line1Position1.getX();
        double y1 = line1Position1.getY();
        double x2 = line1Position2.getX();
        double y2 = line1Position2.getY();
        double x3 = line2Position1.getX();
        double y3 = line2Position1.getY();
        double x4 = line2Position2.getX();
        double y4 = line2Position2.getY();
        
        double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
        if(d==0)
        {
            return null;
        }
        double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
        double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
        
        Position p = new Position(xi, yi);
        if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
        if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
        return p;
    }
    public static double PositionToLineDistance(Position linePosition1, 
            Position linePosition2, 
            Position p) {
        double x1 = linePosition1.getX();
        double y1 = linePosition1.getY();
        double x2 = linePosition2.getX();
        double y2 = linePosition2.getY();
        double xp = p.getX();
        double yp = p.getY();
        double normalLength = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        return Math.abs((xp-x1)*(y2-y1)-(yp-y1)*(x2-x1))/normalLength;
    }
}
class Position3D {
    double x;
    double y;
    double z;
    int iteration = -1;
    boolean inUpTrayectory = false;
    public Position3D(double[] coords)
    {
        this.x = coords[0];
        this.y = coords[1];
        this.z = coords[2];
    }
    public Position3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Position3D(double x, double y, double z, int iteration)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.iteration = iteration;
    }
    public Position3D(double x, double y, double z, int iteration, boolean inUpTrayectory)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.iteration = iteration;
        this.inUpTrayectory =  inUpTrayectory;
    }
    public Position3D(Position position, double angleZ)
    {
        this.x = position.getX();
        this.y = position.getY();
        this.z = angleZ;
    }
    public Position3D(Position position)
    {
        this.x = position.getX();
        this.y = position.getY();
        this.z = 0;
    }
    public Position to2D()
    {
        return new Position(this.x, this.y);
    }
    public Player nearestPlayer(Variables vars, Players players)
    {
        return players.GetPlayerByIndex(this.to2D().nearestIndex(players.positions));
    }
    public Player nearestPlayer(Variables vars, Players players, int... exclude)
    {
        return players.GetPlayerByIndex(this.to2D().nearestIndex(players.positions, exclude));
    }
    public Players nearestPlayers(Variables vars, Players players)
    {
        int[] nearestIndexes = this.to2D().nearestIndexes(players.positions);
        Players p = new Players();
        for(int idx : nearestIndexes)
        {
            p.add(players.GetPlayerByIndex(idx));
        }
        return p;
    }
    public Players nearestPlayers(Variables vars, Players players, int... exclude)
    {
        int[] nearestIndexes = this.to2D().nearestIndexes(players.positions, exclude);
        Players p = new Players();
        for(int idx : nearestIndexes)
        {
            p.add(players.GetPlayerByIndex(idx));
        }
        return p;
    }
}
