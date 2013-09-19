package org.javahispano.javacup.tacticas.jvc2012.celta;

import org.javahispano.javacup.model.util.Position;

/**
 * Clase que contiene las alineaciones.
 */
public final class Alignment {

	
	/** Alineación inicial */
	public static final Position INITIAL[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(-19.46564885496183, -31.6044776119403),
			new Position(0.2595419847328244, -31.082089552238806),
			new Position(19.984732824427482, -31.6044776119403),
			new Position(7.526717557251908, -11.753731343283583),
			new Position(-8.564885496183205, -11.753731343283583),
			new Position(-24.727272727272727, 3.3257918552036196),
			new Position(23.099236641221374, -2.873134328358209),
			new Position(14.74125874125874, 27.31900452488688),
			new Position(-0.7786259541984732, 8.097014925373134),
			new Position(-13.076923076923078, 32.30769230769231) };

	/** Alineación de saque (si sacamos nosotros) */
	public static final Position START_POSITIONS[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(-19.46564885496183, -31.6044776119403),
			new Position(0.2595419847328244, -31.082089552238806),
			new Position(19.984732824427482, -31.6044776119403),
			new Position(13.79020979020979, -14.96606334841629),
			new Position(-13.79020979020979, -15.678733031674208),
			new Position(-24.48951048951049, -0.7126696832579186),
			new Position(23.099236641221374, -2.873134328358209),
			new Position(0.951048951048951, -0.7126696832579186),
			new Position(-0.951048951048951, -13.303167420814479),
			new Position(-3.5664335664335667, 0.0) };
	
	/** Alineación de saque (si saca el rival) */
	public static final Position NO_START_POSITIONS[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(-19.46564885496183, -31.6044776119403),
			new Position(0.2595419847328244, -31.082089552238806),
			new Position(19.984732824427482, -31.6044776119403),
			new Position(13.79020979020979, -14.96606334841629),
			new Position(-15.454545454545453, -14.728506787330318),
			new Position(-24.48951048951049, -0.7126696832579186),
			new Position(23.099236641221374, -2.873134328358209),
			new Position(10.223776223776223, -0.47511312217194573),
			new Position(-0.951048951048951, -13.303167420814479),
			new Position(-10.461538461538462, -0.9502262443438915) };
}
