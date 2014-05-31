package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGConstants;

/**
 * <br>
 * Figura que representa un círculo (x - a)^2 + (y - b)^2 = r^2.
 * <br>
 * <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class Circulo {

    /**
     * Centro del c�rculo.
     */
    private final Punto centro;
    /**
     * Radio del circulo.
     */
    private final double radio;

    /**
     * Constructor.
     *
     * @param centro centro del circulo.
     * @param radio  radio del circulo.
     */
    public Circulo(Punto centro, double radio) {
        if (centro == null) {
            throw new IllegalArgumentException("centro no puede ser <null>");
        }
        this.centro = centro;
        this.radio = radio;
    }

    /**
     * Devuelve el centro del circulo.
     *
     * @return centro del circulo.
     */
    public Punto getCentro() {
        return centro;
    }

    /**
     * Devuelve el radio del circulo.
     *
     * @return radio del circulo.
     */
    public double getRadio() {
        return radio;
    }

    /**
     * Devuelve el punto de la coordenada X.
     * @param y coordenada Y.
     * @return puntos posibles de la coordenada X.
     */
    public double[] getX(double y) {
        if (y > (centro.getY() + radio) || (y < centro.getY() - radio)) {
            return new double[0];
        }

        // (x - a)^2 + (y - b)^2 = r^2
        // (x - a) (x - a) + (y - b)^2 - r^2 = 0
        // x^2 - 2ax + a^2 + (y - b)^2 - r^2 = 0
        // x1 = (2a + sqrt((-2a)^2 - 4(a^2 + (y - b)^2 - r^2))) / 2
        // x2 = (2a - sqrt((-2a)^2 - 4(a^2 + (y - b)^2 - r^2))) / 2

        return TrigonometriaUtils.getSolutionEcuationReal(
                1,
                -2 * centro.getX(),
                Math.pow(centro.getX(), 2) + Math.pow(y - centro.getY(), 2) - Math.pow(radio, 2));
    }

    /**
     * Devuelve la Y.
     * @param x coordenada x.
     * @return puntos posibles de la coordenada y,
     */
    public double[] getY(double x) {
        if (x > (centro.getX() + radio) || (x < centro.getX() - radio)) {
            return new double[0];
        }

        // (x - a)^2 + (y - b)^2 = r^2
        // (x - a) (x - a) + (y - b)^2 - r^2 = 0
        // y^2 - 2by + b^2 + (x - a)^2 - r^2 = 0
        // y1 = (2b + sqrt((-2b)^2 - 4(b^2 + (x - a)^2 - r^2))) / 2
        // y2 = (2b - sqrt((-2b)^2 - 4(b^2 + (x - a)^2 - r^2))) / 2

        return TrigonometriaUtils.getSolutionEcuationReal(
                1,
                -2 * centro.getY(),
                Math.pow(centro.getY(), 2) + Math.pow(x - centro.getX(), 2) - Math.pow(radio, 2));
    }

    /**
     * Indica si es un punto de la recta
     * @param punto punto a comprobar.
     * @return si es un punto de la recta.
     */
    public boolean isPoint(Punto punto) {
        boolean isPoint = false;
        for(double y : getY(punto.getX()))
        {
            if(TrigonometriaUtils.getDistancia(punto, new Punto(punto.getX(), y)) <= MSGConstants.PRECISION_COMPARAR_DISTANCIAS)
            {
                return true;
            }
        }
        return false;
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
        return String.format("Circulo -> centro:%s, radio:%s", centro, radio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Circulo circulo = (Circulo) o;

        if (Double.compare(circulo.radio, radio) != 0) {
            return false;
        }
        if (!centro.equals(circulo.centro)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = centro.hashCode();
        temp = radio != +0.0d ? Double.doubleToLongBits(radio) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
