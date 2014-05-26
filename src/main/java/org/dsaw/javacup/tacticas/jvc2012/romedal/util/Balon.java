package org.dsaw.javacup.tacticas.jvc2012.romedal.util;

import org.dsaw.javacup.model.util.Position;

public class Balon {
	Position	position;
	double		altura;
	int			iteracion;
	boolean		rebote;

	public Balon(final double x, final double y, final double z) {
		this(new Position(x, y), z, 0, false);
	}

	public Balon(final double x, final double y, final double z, final int iteracion) {
		this(new Position(x, y), z, iteracion, false);
	}

	public Balon(final Position p, final double a) {
		this(p, a, 0, false);
	}

	public Balon(final Position p, final double a, final int iteracion) {
		this(p, a, iteracion, false);
	}

	public Balon(final Balon b) {
		this(b.getPosition(), b.getAltura(), 0, false);
	}

	public Balon(final Position position, final double altura, final int iteracion, final boolean rebote) {
		this.position = new Position(position);
		this.altura = altura;
		this.iteracion = iteracion;
		this.rebote = rebote;
	}

	/**
	 * @return the Position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param Position
	 *            the Position to set
	 */
	public void setPosition(final Position Position) {
		this.position = new Position(Position);
	}

	/**
	 * @return the altura
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * @param altura
	 *            the altura to set
	 */
	public void setAltura(final double altura) {
		this.altura = altura;
	}

	public int getIteracion() {
		return iteracion;
	}

	public void setIteracion(final int iteracion) {
		this.iteracion = iteracion;
	}

	public boolean isRebote() {
		return rebote;
	}

	public void setRebote(final boolean rebote) {
		this.rebote = rebote;
	}

	@Override
	public boolean equals(final Object obj) {
		final Balon b = (Balon) obj;
		return position.equals(b.getPosition()) && altura == b.getAltura();
	}

	@Override
	public String toString() {
		return "[" + position.getX() + "] [" + position.getY() + "] [" + altura + "] [" + iteracion + "][" + rebote + "]\n";
	}
}