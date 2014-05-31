/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import org.dsaw.javacup.model.util.Position;

/**
 * @author Usuario
 */
public class Geometria {

  public static Position[] getPuntosTangenciales(Position cp, double r, Position pp) {
    Vector2d c = new Vector2d(cp);
    Vector2d p = new Vector2d(pp);

    Vector2d pmc = p.resta(c);
    double longitudCuadrada = pmc.magnitudCuadrada();
    double rCuadrada = r * r;
    if (longitudCuadrada <= rCuadrada) {
      return null;
    }

    double inversoLongidtudCuadrado = 1 / longitudCuadrada;
    double raiz = Math.sqrt(Math.abs(longitudCuadrada - rCuadrada));

    Vector2d tangenteA = new Vector2d(c.x + r * (r * pmc.x - pmc.y * raiz)
                                            * inversoLongidtudCuadrado,
                                      c.y + r * (r * pmc.y + pmc.x * raiz)
                                            * inversoLongidtudCuadrado);
    Vector2d tangenteB = new Vector2d(c.x + r * (r * pmc.x + pmc.y * raiz)
                                            * inversoLongidtudCuadrado,
                                      c.y + r * (r * pmc.y - pmc.x * raiz)
                                            * inversoLongidtudCuadrado);

    return new Position[]{tangenteA.toPosicion(), tangenteB.toPosicion()};
  }

  public static ResultadoInterseccion interseccionLineas(Vector2d puntoA,
                                                         Vector2d puntoB, Vector2d puntoC,
                                                         Vector2d puntoD) {
    double rNum = (puntoA.y - puntoC.y) * (puntoD.x - puntoC.x)
                  - (puntoA.x - puntoC.x) * (puntoD.y - puntoC.y);
    double rDen = (puntoB.x - puntoA.x) * (puntoD.y - puntoC.y)
                  - (puntoB.y - puntoA.y) * (puntoD.x - puntoC.x);

    double sNum = (puntoA.y - puntoC.y) * (puntoB.x - puntoA.x)
                  - (puntoA.x - puntoC.x) * (puntoB.y - puntoA.y);
    double sDen = (puntoB.x - puntoA.x) * (puntoD.y - puntoC.y)
                  - (puntoB.y - puntoA.y) * (puntoD.x - puntoC.x);

    if (rDen == 0 || sDen == 0) {
      ResultadoInterseccion resultado = new ResultadoInterseccion();
      resultado.intersectados = false;
      return resultado;
    }

    double r = rNum / rDen;
    double s = sNum / sDen;

    ResultadoInterseccion resultado = new ResultadoInterseccion();
    if (r > 0 && r < 1 && s > 0 && s < 1) {
      resultado.intersectados = true;
      resultado.distancia = puntoA.distancia(puntoB) * r;
      resultado.punto = puntoA.suma(puntoB.resta(puntoA).productoEscalar(r));
      return resultado;
    } else {
      resultado.intersectados = false;
    }

    return resultado;
  }

  public static class ResultadoInterseccion {

    public boolean intersectados;
    public double distancia;
    public Vector2d punto;

    public ResultadoInterseccion() {
      punto = Vector2d.CERO;
    }
  }
}
