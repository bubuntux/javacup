package org.dsaw.javacup.tacticas.jvc2012.celta.geom;

import org.dsaw.javacup.model.util.Position;

public class Line {

	/** Definición del recta según la función y = mx + n */
	private double m, n;

	/**
	 * Define una línea a partir de dos puntos.
	 * @param p1	Punto 1
	 * @param p2    Punto 2
	 */
	public Line(Position p1, Position p2) {
		double a, b, c;
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		
		if (!p1.equals(p2)) {
			if (p1.getX() == p2.getX()) {
				m = Double.POSITIVE_INFINITY;
				n = p2.getX();
			} else {
				if(p1.getY() == p2.getY()) {
					m = 0;
					n = p2.getY();
				} else {
					a = y2 - y1;
					b = (x2 - x1) * -1;
					c = (x1 * (y2 - y1)) - (y1 * (x2 - x1));
					m = a / b * -1;
					n = c / b;
				}
			}
		} else
			throw new IllegalArgumentException("Imposible calcular la recta con dos puntos iguales");
	}

	/**
	 * Define una línea a partir de la pendiente y del coeficiente n, según
	 * ecuación y = mx + n
	 * @param m		Pendiente de la recta
	 * @param b     Coeficiente n
	 */
	public Line(double m, double n) {
		this.m = m;
		this.n = n;
	}

	/**
	 * Devuelve la pendiente (m) de la recta.
	 * @return m
	 */
	public double getM() {
		return m;
	}

	/** 
	 * Devuelve el coeficiente de posición (c) de la recta.
	 * @return n
	 */
	public double getN() {
		return n;
	}
	
	/**
     * Calcula X para el valor de Y dado. 
     * @param y
     * @return x
     */
    public double getX(double y) {
        if (m == Double.POSITIVE_INFINITY)
            return n;
        else 
            return (y - n) / m;
    }

    /**
     * Calcula Y para el valor de X dado. 
     * @param x
     * @return y
     */
    public double getY(double x) {
        if (m == Double.MIN_VALUE)
            return n;
        else
            return x * m + n;
    }

	/**
	 * Devuelve la mediatriz de la recta que pasa por el punto indicado.
	 * @param Position	punto
	 * @return Mediatriz
	 */
	public Line getBisection(Position p) {
		double angle = Math.atan(getM());
		angle += Math.PI / 2;
		double m = Math.tan(angle);
		return new Line(m, p.getY() - m * p.getX());
	}

	/**
	 * Devuelve una paralela a la recta a la distancia indicada.
	 * @param	d	Distancia de la paralela sobre la recta
	 * @return		Recta paralela
	 */
	public Line getParalell(double distance) {
		return new Line(m, n + distance);
	}
	
	/**
	 * Devuelve la intersección con la recta indicada.
	 * @param 	line
	 * @return 	punto de intersección
	 */
	public Position getIntersection(Line line) {
		return Line.getIntersection(this, line);
	}
	
	/**
	 * Devuelve la intersección entre las dos rectas indicadas.
	 * @param line1
	 * @param line2
	 * @return	punto de intersección
	 */
	public static Position getIntersection(Line line1, Line line2) {
		if (line1.getM() == line2.getM())  // rectas paralelas
			return null;
		if (line1.getM() == Double.POSITIVE_INFINITY)
			return new Position(line1.getN(), line2.getY(line1.getN()));
		else if (line2.getM() == Double.POSITIVE_INFINITY)
			return new Position(line2.getN(), line1.getY(line2.getN()));
		double x = (line2.getN() - line1.getN()) / (line1.getM() - line2.getM());
		double y = line1.getY(x);
		return new Position(x, y);
	}

}
