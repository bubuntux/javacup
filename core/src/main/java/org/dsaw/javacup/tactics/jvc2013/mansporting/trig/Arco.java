/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.trig;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author MaN
 */
public class Arco {

    private final Punto puntoA;
    private final Punto puntoB;
    private final Punto centro;
    private final Circulo circulo;

    /**
     * Segmento.
     * @param puntoA punto A.
     * @param puntoB punto B
     */
    public Arco(Punto puntoA, Punto puntoB, Punto centro) {

        this.puntoA = puntoA;
        this.puntoB = puntoB;
        this.centro = centro;

        if (puntoA.equals(puntoB)) {
            circulo = null;
        } else {
            this.circulo = new Circulo(centro, TrigonometriaUtils.getDistancia(puntoA, centro));
        }
    }

    public Punto getCentro() {
        return centro;
    }

    public Punto getPuntoA() {
        return puntoA;
    }

    public Punto getPuntoB() {
        return puntoB;
    }

    public Circulo getCirculo() {
        return circulo;
    }



    /**
     * Devuelve el punto de la coordenada X.
     * @param y coordenada Y.
     * @return puntos posibles de la coordenada X.
     */
    public double[] getX(double y) {
        double[] result;
        if (circulo == null) {
            result = puntoA.getY() == y ? new double[]{puntoA.getX()} : new double[0];
        } else {
            result = circulo.getX(y);
            if (result.length > 0) {
                List<Double> resultValidos = new LinkedList<Double>();
                for (double x : result) {
                    if (isPoint(new Punto(x, y))) {
                        resultValidos.add(x);
                    }
                }
                result = new double[resultValidos.size()];
                for (int i = 0; i < resultValidos.size(); i++) {
                    result[i] = resultValidos.get(i);
                }
            }
        }
        return result;
    }

    /**
     * Devuelve la Y.
     * @param x coordenada x.
     * @return puntos posibles de la coordenada y,
     */
    public double[] getY(double x) {
        double[] result;
        if (circulo == null) {
            result = puntoA.getX() == x ? new double[]{puntoA.getY()} : new double[0];
        } else {
            result = circulo.getX(x);
            if (result.length > 0) {
                List<Double> resultValidos = new LinkedList<Double>();
                for (double y : result) {
                    if (isPoint(new Punto(x, y))) {
                        resultValidos.add(y);
                    }
                }
                result = new double[resultValidos.size()];
                for (int i = 0; i < resultValidos.size(); i++) {
                    result[i] = resultValidos.get(i);
                }
            }
        }
        return result;
    }

    /**
     * Indica si es un punto de la recta
     * @param punto punto a comprobar.
     * @return si es un punto de la recta.
     */
    public boolean isPoint(Punto punto) {
        boolean isPoint = circulo.isPoint(punto);
        if (isPoint) {
        }
        return isPoint;


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
        return String.format("Arco -> puntoA:%s, puntoB:%s, centro:%s", puntoA, puntoB, centro);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Arco other = (Arco) obj;
        if (this.puntoA != other.puntoA && (this.puntoA == null || !this.puntoA.equals(other.puntoA))) {
            return false;
        }
        if (this.puntoB != other.puntoB && (this.puntoB == null || !this.puntoB.equals(other.puntoB))) {
            return false;
        }
        if (this.centro != other.centro && (this.centro == null || !this.centro.equals(other.centro))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.puntoA != null ? this.puntoA.hashCode() : 0);
        hash = 71 * hash + (this.puntoB != null ? this.puntoB.hashCode() : 0);
        hash = 71 * hash + (this.centro != null ? this.centro.hashCode() : 0);
        return hash;
    }
}
