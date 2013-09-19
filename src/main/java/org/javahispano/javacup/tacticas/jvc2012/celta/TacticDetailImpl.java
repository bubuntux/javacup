package org.javahispano.javacup.tacticas.jvc2012.celta;

import java.awt.Color;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.render.EstiloUniforme;

public class TacticDetailImpl implements TacticDetail {

	public String getTacticName() {
		return "Celta";
	}

	public String getCountry() {
		return "España";
	}

	public String getCoach() {
		return "Cristian González";
	}

	public Color getShirtColor() {
		return new Color(102, 204, 255);
	}

	public Color getShortsColor() {
		return new Color(255, 255, 255);
	}

	public Color getShirtLineColor() {
		return new Color(255, 51, 51);
	}

	public Color getSocksColor() {
		return new Color(102, 204, 255);
	}

	public Color getGoalKeeper() {
		return new Color(255, 51, 51);
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.SIN_ESTILO;
	}

	public Color getShirtColor2() {
		return new Color(255, 51, 51);
	}

	public Color getShortsColor2() {
		return new Color(255, 0, 0);
	}

	public Color getShirtLineColor2() {
		return new Color(255, 255, 255);
	}

	public Color getSocksColor2() {
		return new Color(255, 51, 51);
	}

	public Color getGoalKeeper2() {
		return new Color(51, 51, 51);
	}

	public EstiloUniforme getStyle2() {
		return EstiloUniforme.SIN_ESTILO;
	}
	
	public PlayerDetail[] getPlayers() {
		return new PlayerDetail[] {
				new PlayerImpl("Yoel", 1, new Color(255, 200, 150), new Color(
						50, 0, 0), 0.87d, 1.0d, 1.0d, true),//.57
				new PlayerImpl("Lago", 2, new Color(255, 200, 150), new Color(
						50, 0, 0), 1.0d, 0.6d, 0.61d, false),
				new PlayerImpl("Catalá", 3, new Color(255, 200, 150),
						new Color(50, 0, 0), .8d, 0.2d, 1.0d, false),
				new PlayerImpl("Vila", 4, new Color(255, 200, 150), new Color(
						50, 0, 0), 1.0d, 0.5d, 0.5d, false),
				new PlayerImpl("Alex", 5, new Color(255, 200, 150), new Color(
						50, 0, 0), 1.0d, 0.8d, 0.64d, false),
				new PlayerImpl("Oubiña", 6, new Color(255, 200, 150),
						new Color(50, 0, 0), 0.89d, 0.78d, 0.6d, false),
				new PlayerImpl("Orellana", 7, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 0.76d, 0.75d, false),
				new PlayerImpl("Bellvís", 8, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 0.7d, 0.5d, false),
				new PlayerImpl("De Lucas", 9, new Color(255, 200, 150),
						new Color(46, 25, 25), 1.0d, 1.0d, 1.0d, false),
				new PlayerImpl("Bermejo", 10, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
				new PlayerImpl("Áspas", 11, new Color(255, 200, 150),
						new Color(50, 0, 0), 0.99d, 1.0d, 1.0d, false) };
	}
}
