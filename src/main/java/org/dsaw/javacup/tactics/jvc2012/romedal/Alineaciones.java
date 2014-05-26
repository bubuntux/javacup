package org.dsaw.javacup.tactics.jvc2012.romedal;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public interface Alineaciones {

	Position		alineacion1[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, -32),//
			new Position(8, -34),//
			new Position(23, -32),//
			new Position(-1, -22),//
			new Position(-9, -35),//
			new Position(-15, -19),//
			new Position(15, -18),//
			new Position(-4, 2),//
			new Position(5, -9),//
			new Position(8, 13)		//
										};																																//

	Position		alineacion2[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, -22),//
			new Position(9, -26),//
			new Position(24, -23),//
			new Position(-1, -16),//
			new Position(-9, -26),//
			new Position(-17, -10),//
			new Position(14, -12),//
			new Position(-4, 8),//
			new Position(1, -3),//
			new Position(8, 20)		//
										};

	Position		alineacion3[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, -13),//
			new Position(9, -17),//
			new Position(24, -12),//
			new Position(-0, -8),//
			new Position(-9, -17),//
			new Position(-17, -2),//
			new Position(17, -2),//
			new Position(-6, 15),//
			new Position(2, 5),//
			new Position(7, 27)		//
										};																																//

	Position		alineacion4[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-22, -4),//
			new Position(6, -10),//
			new Position(22, -5),//
			new Position(-2, 3),//
			new Position(-8, -8),//
			new Position(-17, 8),//
			new Position(14, 7),//
			new Position(-9, 23),//
			new Position(0.0, 15),//
			new Position(11, 32)		//
										};																																//

	Position		alineacion5[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-23, 4),//
			new Position(9, 1),//
			new Position(23, 4),//
			new Position(-1, 11),//
			new Position(-8, 1),//
			new Position(-17, 19),//
			new Position(14, 18),//
			new Position(-7, 30),//
			new Position(1, 24),//
			new Position(8, 40)		//
										};																																//

	Position		alineacion6[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-24, 11),//
			new Position(9, 5),//
			new Position(23, 11),//
			new Position(-0, 17),//
			new Position(-9, 4),//
			new Position(-17, 24),//
			new Position(14, 25),//
			new Position(-9, 39),//
			new Position(-0, 32),//
			new Position(3, 48)		//
										};																																//

	Position		alineacion7[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-24, 18),//
			new Position(9, 12),//
			new Position(23, 18),//
			new Position(-0, 23),//
			new Position(-9, 15),//
			new Position(-17, 30),//
			new Position(14, 33),//
			new Position(-9, 43),//
			new Position(-0, 39),//
			new Position(3, 48)		//
										};																																//

	Position		alineacion8[]		= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-24, 22),//
			new Position(9, 18),//
			new Position(23, 24),//
			new Position(-0, 29),//
			new Position(-9, 21),//
			new Position(-17, 35),//
			new Position(14, 38),//
			new Position(-9, 45),//
			new Position(-0, 42),//
			new Position(3, 48)		//
										};																																//

	Position		alineacionSaca[]	= new Position[] {//
										//
			new Position(0, -50),//
			new Position(-11, -35),//
			new Position(12, -35),//
			new Position(-28, -28), //
			new Position(28, -28), //
			new Position(-14, -19),//
			new Position(14, -19), //
			new Position(-23, -0), //
			new Position(5, 0),//
			new Position(0, -0),//
			new Position(22, -1)		};

	Position		alineacionRecibe[]	= new Position[] { //
										//
			new Position(0, -50), //
			new Position(-11, -35), //
			new Position(11, -35),//
			new Position(-28, -28), //
			new Position(28, -28), //
			new Position(-14, -19),//
			new Position(14, -18), //
			new Position(-23, -0), //
			new Position(6, -6), //
			new Position(0, -6), //
			new Position(22, -1)		};																																//

	Position[][]	alineaciones		= new Position[][] { alineacion1, alineacion2, alineacion3, alineacion4, alineacion5, alineacion6, alineacion7, alineacion8 };

	int				seccion				= (int) Constants.LARGO_CAMPO / alineaciones.length;

}
