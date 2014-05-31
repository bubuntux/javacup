package org.dsaw.javacup.tactics.jvc2012.masia2012;

import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Trajectory{
	public Position[] positions;
	public double[] z;
	public int length;
	public double angle;
	
	public Trajectory(Position ball, double velocity,
			double angle, double angleVert) {
		this.angle = angle;
		length = 75;
		positions = new Position[length];
		z = new double[length];
		AbstractTrajectory t = new AirTrajectory(velocity*Math.cos(angleVert), velocity*Math.sin(angleVert), 0, 0);
		for (int i = 0; i < length; i++) {
			double radio = t.getX((double)(i + 1)/60d)* Constants.AMPLIFICA_VEL_TRAYECTORIA;
			positions[i] = new Position(ball.getX() + radio*Math.cos(angle), ball.getY() + radio*Math.sin(angle));
			z[i] = t.getY((double)(i + 1)/60d)* Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
		}
	}	
}
