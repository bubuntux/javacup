/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.bo;

import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author jsebas
 */
public class TrajectoryMasia {

    public Position[] positions;
    public double[] z;
    public int length;

    public TrajectoryMasia(Position ball, double velocity, double angle, double angleVert) 
    {
        length = 75;
        positions = new Position[length];
        z = new double[length];
        
        AbstractTrajectory t = new AirTrajectory(velocity * Math.cos(angleVert), velocity * Math.sin(angleVert), 0, 0);
        for (int i = 0; i < length; i++) {
            double radio = t.getX((double) (i + 1) / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
            positions[i] = new Position(ball.getX() + radio * Math.cos(angle), ball.getY() + radio * Math.sin(angle));
            z[i] = t.getY((double) (i + 1) / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        }
    }
}
