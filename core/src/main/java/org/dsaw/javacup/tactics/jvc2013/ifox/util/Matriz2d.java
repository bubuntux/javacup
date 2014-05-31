/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import java.util.List;

/**
 *
 * @author Usuario
 */
public final class Matriz2d {

    public double m11, m12, m13;
    public double m21, m22, m23;
    public double m31, m32, m33;

    public Matriz2d() {
        identidad();
    }

    public Matriz2d(Matriz2d matrix2d) {
        this.m11 = matrix2d.m11;
        this.m12 = matrix2d.m11;
        this.m13 = matrix2d.m11;
        this.m21 = matrix2d.m21;
        this.m22 = matrix2d.m22;
        this.m23 = matrix2d.m23;
        this.m31 = matrix2d.m31;
        this.m32 = matrix2d.m32;
        this.m33 = matrix2d.m33;
    }

    public Matriz2d(double m11, double m12, double m13, double m21, double m22,
            double m23, double m31, double m32, double m33) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public void multiplicar(Matriz2d matrix2d) {
        Matriz2d mult = new Matriz2d(
                this.m11 * matrix2d.m11 + this.m12 * matrix2d.m21 + this.m13 * matrix2d.m31,
                this.m11 * matrix2d.m12 + this.m12 * matrix2d.m22 + this.m13 * matrix2d.m32,
                this.m11 * matrix2d.m13 + this.m12 * matrix2d.m23 + this.m13 * matrix2d.m33,
                this.m21 * matrix2d.m11 + this.m22 * matrix2d.m21 + this.m23 * matrix2d.m31,
                this.m21 * matrix2d.m12 + this.m22 * matrix2d.m22 + this.m23 * matrix2d.m32,
                this.m21 * matrix2d.m13 + this.m22 * matrix2d.m23 + this.m23 * matrix2d.m33,
                this.m31 * matrix2d.m11 + this.m32 * matrix2d.m21 + this.m33 * matrix2d.m31,
                this.m31 * matrix2d.m12 + this.m32 * matrix2d.m22 + this.m33 * matrix2d.m32,
                this.m31 * matrix2d.m13 + this.m32 * matrix2d.m23 + this.m33 * matrix2d.m33);

        this.m11 = mult.m11;
        this.m12 = mult.m12;
        this.m13 = mult.m13;
        this.m21 = mult.m21;
        this.m22 = mult.m22;
        this.m23 = mult.m23;
        this.m31 = mult.m31;
        this.m32 = mult.m32;
        this.m33 = mult.m33;
    }

    public void transformar(Vector2d punto) {
        Vector2d aux = new Vector2d();
        aux.x = this.m11 * punto.x + this.m21 * punto.y + this.m31;
        aux.y = this.m12 * punto.x + this.m22 * punto.y + this.m32;
        punto.x = aux.x;
        punto.y = aux.y;
        aux = null;
    }

    public void transformar(List<Vector2d> puntos) {
        Vector2d aux = new Vector2d();
        for (Vector2d punto : puntos) {
            aux.x = this.m11 * punto.x + this.m21 * punto.y + this.m31;
            aux.y = this.m12 * punto.x + this.m22 * punto.y + this.m32;
            punto.x = aux.x;
            punto.y = aux.y;
        }
        aux = null;
    }

    public void identidad() {
        this.m11 = 1;
        this.m12 = 0;
        this.m13 = 0;
        this.m21 = 0;
        this.m22 = 1;
        this.m23 = 0;
        this.m31 = 0;
        this.m32 = 0;
        this.m33 = 1;
    }

    public void transladar(double x, double y) {
        Matriz2d translacion = new Matriz2d();
        translacion.m11 = 1;
        translacion.m12 = 0;
        translacion.m13 = 0;
        translacion.m21 = 0;
        translacion.m22 = 1;
        translacion.m23 = 0;
        translacion.m31 = x;
        translacion.m32 = y;
        translacion.m33 = 1;

        multiplicar(translacion);
    }

    public void escalar(double escalaX, double escalaY) {
        Matriz2d escala = new Matriz2d();
        escala.m11 = escalaX;
        escala.m12 = 0;
        escala.m13 = 0;
        escala.m21 = 0;
        escala.m22 = escalaY;
        escala.m23 = 0;
        escala.m31 = 0;
        escala.m32 = 0;
        escala.m33 = 1;

        multiplicar(escala);
    }

    public void rotar(double angulo) {
        Matriz2d rotacion = new Matriz2d();
        double cos = Math.cos(angulo);
        double sin = Math.sin(angulo);
        rotacion.m11 = cos;
        rotacion.m12 = sin;
        rotacion.m13 = 0;
        rotacion.m21 = -sin;
        rotacion.m22 = cos;
        rotacion.m33 = 0;
        rotacion.m31 = 0;
        rotacion.m32 = 0;
        rotacion.m33 = 1;

        multiplicar(rotacion);
    }

    public void rotar(Vector2d frente, Vector2d lado) {
        Matriz2d rotacion = new Matriz2d();
        rotacion.m11 = frente.x;
        rotacion.m12 = frente.y;
        rotacion.m13 = 0;
        rotacion.m21 = lado.x;
        rotacion.m22 = lado.y;
        rotacion.m33 = 0;
        rotacion.m31 = 0;
        rotacion.m32 = 0;
        rotacion.m33 = 1;

        multiplicar(rotacion);
    }

    @Override
    public String toString() {
        return "Matriz2d{" + "m11=" + m11 + "m12=" + m12 + "m13=" + m13
                + "m21=" + m21 + "m22=" + m22 + "m23=" + m23 + "m31="
                + m31 + "m32=" + m32 + "m33=" + m33 + '}';
    }
}
