package org.dsaw.javacup.tactics.jvc2012.hortalezablamers;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class HBConstants {

	// constantes
	public static final double ANGULO_GOLPEO_DESPEJE = 45;
	public static final double ANGULO_GOLPEO_CUELGUE = 45;
	public static final double ANGULO_GOLPEO_PASE = 0;
	public static final double ANGULO_GOLPEO_TIRO = 15;
	public static final double FUERZA_GOLPEO_MAXIMA = 1;
	public static final double FUERZA_GOLPEO_MEDIA = 0.5;
	public static final double FACTOR_MOVIMIENTO_PORTERO = 1.1;
	public static final double VELOCIDAD_RECEPCION_PASE = 0.5;
	public static final Position POSICION_SAQUE_PUERTA_IZDA = new Position(-Constants.LARGO_AREA_CHICA / 2, -(Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA));
	public static final Position POSICION_SAQUE_PUERTA_DCHA = new Position(Constants.LARGO_AREA_CHICA / 2, -(Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA));
	public static final Position POSICION_CENTRO_CAMPO_PROPIO = new Position(0, -(Constants.LARGO_CAMPO_JUEGO / 4));
	public static final double POSICIONES_PASE_MARGEN_IZDO = 4;
	public static final double POSICIONES_PASE_MARGEN_INF = 2.5;
	public static final double DISTANCIA_POSICIONES_PASE = 5;
	public static final double DISTANCIA_SEGURIDAD_PASE_RASO = 10;
	public static final double DISTANCIA_LIMITE_PASE_ALTO = 10;
	public static final double DISTANCIA_LIMITE_TIRO = 18;
	
}
