package org.dsaw.javacup.tacticas.jvc2012.celta;

import java.awt.Color;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.util.Position;

public class PlayerImpl implements PlayerDetail {

	String name;
	int number;
	Color skin, hair;
	double velocity, shot, presicion;
	boolean goalKeeper;
	Position Position;

	public PlayerImpl(String name, int number, Color skin, Color hair,
			double velocity, double shot, double presicion,
			boolean portero) {
		this.name = name;
		this.number = number;
		this.skin = skin;
		this.hair = hair;
		this.velocity = velocity;
		this.shot = shot;
		this.presicion = presicion;
		this.goalKeeper = portero;
	}

	@Override
        public String getPlayerName() {
		return name;
	}

	@Override
        public Color getSkinColor() {
		return skin;
	}

	@Override
        public Color getHairColor() {
		return hair;
	}

	@Override
        public int getNumber() {
		return number;
	}

	@Override
        public boolean isGoalKeeper() {
		return goalKeeper;
	}

	@Override
        public double getSpeed() {
		return velocity;
	}

	@Override
        public double getPower() {
		return shot;
	}

	@Override
        public double getPrecision() {
		return presicion;
	}


}
