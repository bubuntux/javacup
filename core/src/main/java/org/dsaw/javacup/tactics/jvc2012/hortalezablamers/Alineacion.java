package org.dsaw.javacup.tactics.jvc2012.hortalezablamers;

import org.dsaw.javacup.model.util.Position;

public class Alineacion {

	public static Position alineacionEnJuego[]=new Position[]{
		new Position(0, -50.5),
		new Position(-0.25, -35),
		new Position(11, -24),
		new Position(-11.25, -24),
		new Position(-0.25, -13),
		new Position(11, -1.5),
		new Position(-11.25, -1.75),
		new Position(1.5, 9),
		new Position(7.75, 30.75),
		new Position(-2.25, 20.25),
		new Position(-8.25, 32),
	};
	public static Position alineacionSaca[]=new Position[]{
		new Position(0.2595419847328244,-50.41044776119403),
		new Position(0.23776223776223776,-42.997737556561084),
		new Position(16.405594405594407,-35.15837104072398),
		new Position(-16.643356643356643,-34.44570135746606),
		new Position(0.23776223776223776,-28.50678733031674),
		new Position(15.692307692307693,-18.766968325791854),
		new Position(-16.88111888111888,-19.004524886877828),
		new Position(-5.468531468531468,-5.9389140271493215),
		new Position(16.167832167832167,-3.5633484162895925),
		new Position(0.2595419847328244,-0.26119402985074625),
		new Position(-16.643356643356643,-3.088235294117647)
	};
	public static Position alineacionRecibe[]=new Position[]{
		new Position(0.2595419847328244,-50.41044776119403),
		new Position(0.0,-41.57239819004525),
		new Position(15.454545454545453,-35.15837104072398),
		new Position(-15.692307692307693,-35.15837104072398),
		new Position(0.4755244755244755,-27.08144796380091),
		new Position(15.93006993006993,-19.2420814479638),
		new Position(-15.692307692307693,-19.2420814479638),
		new Position(0.0,-13.065610859728507),
		new Position(6.4885496183206115,-6.529850746268657),
		new Position(-6.4885496183206115,-6.529850746268657),
		new Position(9.986013986013985,-0.47511312217194573)
	};
	public static Position[] zonasLD = new Position[]{
		new Position(0, 0),
		new Position(-20.75, -51.5),
		new Position(-6.25, -50),
		new Position(-28.5, -49.75),
		new Position(-19.25, -51),
		new Position(-6, -49.5),
		new Position(-28.25, -49.25),
		new Position(-9.5, -26.75),
		new Position(-8, -7.25),
		new Position(-27, -16.25),
		new Position(-23.75, -5.75),
	};
	public static Position[] zonasRU = new Position[]{
		new Position(0, 0),
		new Position(20.25, -15),
		new Position(27.5, -3),
		new Position(6.25, -2.75),
		new Position(18.75, 13),
		new Position(27.75, 31.75),
		new Position(6, 32),
		new Position(28.25, 36.5),
		new Position(24.75, 50),
		new Position(10, 40),
		new Position(8, 51.25),
	};
}
