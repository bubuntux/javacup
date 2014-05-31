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
public class Transformacion {

    public static List<Vector2d> transformacionGlobal(List<Vector2d> puntos,
            Vector2d posicion, Vector2d frente, Vector2d lado,
            Vector2d escala) {
        List<Vector2d> puntosTranformados = Util.copiarLista(puntos);

        Matriz2d matriz2d = new Matriz2d();
        if (escala.x != 1.0 || escala.y != 1.0) {
            matriz2d.escalar(escala.x, escala.y);
        }
        matriz2d.rotar(frente, lado);
        matriz2d.transladar(posicion.x, posicion.y);
        matriz2d.transformar(puntosTranformados);

        return puntosTranformados;
    }

    public static List<Vector2d> transformacionGlobal(List<Vector2d> puntos,
            Vector2d posicion, Vector2d frente, Vector2d lado) {
        List<Vector2d> puntosTranformados = Util.copiarLista(puntos);

        Matriz2d matrix2d = new Matriz2d();
        matrix2d.rotar(frente, lado);
        matrix2d.transladar(posicion.x, posicion.y);
        matrix2d.transformar(puntosTranformados);

        return puntosTranformados;
    }

    public static Vector2d puntoAEspacioGlobal(Vector2d punto,
            Vector2d frente, Vector2d lado, Vector2d posicion) {
        Vector2d puntoTransformado = new Vector2d(punto);
        Matriz2d matrix2d = new Matriz2d();
        matrix2d.rotar(frente, lado);
        matrix2d.transladar(posicion.x, posicion.y);
        matrix2d.transformar(puntoTransformado);
        return puntoTransformado;
    }

    public static Vector2d vectorAEspacioGlobal(Vector2d vector,
            Vector2d frente, Vector2d lado) {
        Vector2d vectorTransformado = new Vector2d(vector);
        Matriz2d matrix2d = new Matriz2d();
        matrix2d.rotar(frente, lado);
        matrix2d.transformar(vectorTransformado);
        return vectorTransformado;
    }

    public static Vector2d puntoAEspacioLocal(Vector2d punto, Vector2d frente,
            Vector2d lado, Vector2d posicion) {
        Vector2d puntoTransformado = new Vector2d(punto);
        Matriz2d matrix2d = new Matriz2d();

        double tx = -posicion.punto(frente);
        double ty = -posicion.punto(lado);

        matrix2d.m11 = frente.x;
        matrix2d.m12 = lado.x;
        matrix2d.m21 = frente.y;
        matrix2d.m22 = lado.y;
        matrix2d.m31 = tx;
        matrix2d.m32 = ty;

        matrix2d.transformar(puntoTransformado);
        return puntoTransformado;
    }

    public static Vector2d vectorAEspacioLocal(Vector2d vector,
            Vector2d frente, Vector2d lado) {
        Vector2d vectorTransformado = new Vector2d(vector);
        Matriz2d matrix2d = new Matriz2d();
        matrix2d.m11 = frente.x;
        matrix2d.m12 = lado.x;
        matrix2d.m21 = frente.y;
        matrix2d.m22 = lado.y;
        matrix2d.transformar(vectorTransformado);
        return vectorTransformado;
    }

    public static void rotarSobreOrigen(Vector2d vector, double angulo) {
        Matriz2d matrix2d = new Matriz2d();
        matrix2d.rotar(angulo);
        matrix2d.transformar(vector);
    }
}
