package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

/**
 * <br>
 * Clase que representa un rect�ngulo.
 * <br>
 * <br>Copyright Fi2net S.A. 10-ene-2011
 */
public class Rectangulo {

    /**
     * Centro del rectángulo.
     */
    private final Punto centro;

    /**
     * Ancho del rectángulo.
     */
    private final double ancho;

    /**
     * Alto del rectángulo.
     */
    private final double alto;


    /**
     * Constructor.
     * @param centro centro del rectangulo.
     * @param ancho ancho del rectangulo.
     * @param alto alto del rectangulo.
     */
    public Rectangulo(Punto centro, double ancho, double alto) {
        if (centro == null)
            throw new IllegalArgumentException("centro no puede ser <null>");
        this.centro = centro;
        this.ancho = ancho;
        this.alto = alto;
    }

    /**
     * Devuelve el centro del rect�ngulo.
     * @return centro del rect�ngulo.
     */
    public Punto getCentro() {
        return centro;
    }

    /**
     * Devuelve el ancho del rect�ngulo.
     * @return ancho del rect�ngulo.
     */
    public double getAncho() {
        return ancho;
    }

    /**
     * Alto del rect�ngulo.
     * @return alto del rect�ngulo.
     */
    public double getAlto() {
        return alto;
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
        return String.format("Rectangulo -> centro:%s, ancho:%s, alto:%s", centro, ancho, alto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangulo that = (Rectangulo) o;

        if (Double.compare(that.alto, alto) != 0) return false;
        if (Double.compare(that.ancho, ancho) != 0) return false;
        if (!centro.equals(that.centro)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = centro.hashCode();
        temp = ancho != +0.0d ? Double.doubleToLongBits(ancho) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = alto != +0.0d ? Double.doubleToLongBits(alto) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
