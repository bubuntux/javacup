package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import org.dsaw.javacup.model.util.Position;

/**
 * <br>
 * Clase que representa un punto.
 * <br>
 * <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class Punto {

    /**
     * Coordenada X.
     */
    private final double x;
    /**
     * Coordenada Y.
     */
    private final double y;

    public Punto(Position posicion) {
        this(posicion.getX(), posicion.getY());
    }

    /**
     * Constructor.
     * @param x coordenada x.
     * @param y coordenada y.
     */
    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Devuelve la coo rdenada x.
     * @return coordenada x.
     */
    public double getX() {
        return x;
    }

    /**
     * Devuelve la coordenada y.
     * @return coordenada y.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("Punto -> x:%s, y:%s", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Punto punto = (Punto) o;

        if (Double.compare(punto.x, x) != 0) {
            return false;
        }
        if (Double.compare(punto.y, y) != 0) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
