package org.dsaw.javacup.tactics.jvc2013.CTeam.tactica;

import org.dsaw.javacup.model.util.Position;

public class PosicionCT {

  private final Position pos;

  public PosicionCT(Position pos) {
    this.pos = pos;
  }

  public PosicionCT(double x, double y) {
    this.pos = new Position(x, y);
  }

  public PosicionCT moverAngulo(double angulo, double radio) {
    return new PosicionCT(pos.moveAngle(angulo, radio));
  }

  public PosicionCT moverAngulo(double angulo, double radio, double radioMax) {
    return new PosicionCT(pos.moveAngle(angulo, radio, radioMax));
  }

  public PosicionCT moverPosicion(double dx, double dy) {
    return new PosicionCT(pos.movePosition(dx, dy));
  }

  public PosicionCT moverPosicion(double dx, double dy, double radioMax) {
    return new PosicionCT(pos.movePosition(dx, dy, radioMax));
  }

  public double getX() {
    return pos.getX();
  }

  public double getY() {
    return pos.getY();
  }

  public PosicionCT getPosicionInvertida() {
    return new PosicionCT(pos.getInvertedPosition());
  }

  public PosicionCT setPosicion(double x, double y) {
    return new PosicionCT(pos.setPosition(x, y));
  }

  public boolean isDentroCampoJuego(double mas) {
    return pos.isInsideGameField(mas);
  }

  public PosicionCT setDentroCampoJuego() {
    return new PosicionCT(pos.setInsideGameField());
  }

  public double angulo(PosicionCT p) {
    return pos.angle(p.pos);
  }

  public String toString() {
    return pos.toString();
  }

  public double distancia(PosicionCT p) {
    return pos.distance(p.pos);
  }

  public int indiceMasCercano(PosicionCT[] poss) {
    Position[] poss1 = arrayPosicion(poss);
    return pos.nearestIndex(poss1);
  }

  private Position[] arrayPosicion(PosicionCT[] poss) {
    Position[] poss1 = new Position[poss.length];
    for (int i = 0; i < poss1.length; i++) {
      poss1[i] = poss[i].pos;
    }
    return poss1;
  }

  public int indiceMasCercano(PosicionCT[] poss, int... excluir) {
    Position[] poss1 = arrayPosicion(poss);
    return pos.nearestIndex(poss1, excluir);
  }

  public int[] indicesMasCercanos(PosicionCT[] poss) {
    Position[] poss1 = arrayPosicion(poss);
    return pos.nearestIndexes(poss1);
  }

  public int[] indicesMasCercanos(PosicionCT[] poss, int... excluir) {
    Position[] poss1 = arrayPosicion(poss);
    return pos.nearestIndexes(poss1, excluir);
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof PosicionCT) {
      PosicionCT posCT = (PosicionCT) obj;
      return pos.equals(posCT.pos);
    }
    return pos.equals(obj);
  }

  public int hashCode() {
    return pos.hashCode();
  }

  public double angulo() {
    return pos.angle();
  }

  public double distancia() {
    return pos.distance();
  }

  public double norma() {
    return pos.norm();
  }

  public double norma(Position p) {
    return pos.norm(p);
  }

  /**
   * producto punto entre BA y AC, siendo A este punto
   */
  public double productoPunto(PosicionCT b, PosicionCT c) {
    double[] ab = new double[2];
    double[] ac = new double[2];
    ab[0] = getX() - b.getX();
    ab[1] = getY() - b.getY();
    ac[0] = c.getX() - getX();
    ac[1] = c.getY() - getY();
    double dot = ab[0] * ac[0] + ab[1] * ac[1];
    return dot;
  }

  /**
   * producto cruzado AB x AC
   */
  public double productoCruzado(PosicionCT b, PosicionCT c) {
    double[] ab = new double[2];
    double[] ac = new double[2];
    ab[0] = b.getX() - getX();
    ab[1] = b.getY() - getY();
    ac[0] = c.getX() - getX();
    ac[1] = c.getY() - getY();
    double cross = ab[0] * ac[1] - ab[1] * ac[0];
    return cross;
  }

  /**
   * distancia de este punto a la linea/segmento ab
   */
  public double distanciaALinea(PosicionCT a, PosicionCT b, boolean seg) {
    double dist = a.productoCruzado(b, this) / a.distancia(b);
    if (seg) {
      double d1 = b.productoPunto(a, this);
      if (d1 > 0) {
        return b.distancia(this);
      }
      d1 = a.productoPunto(b, this);
      if (d1 > 0) {
        return a.distancia(this);
      }
    }
    return Math.abs(dist);
  }

  /**
   * analiza si este punto se encuentra en el segmento ab
   */
  public boolean estaEnSegmento(PosicionCT a, PosicionCT b) {
    double d1 = a.productoPunto(b, this);
    if (d1 > 0) {
      return false;
    }
    double d2 = b.productoPunto(a, this);
    if (d2 > 0) {
      return false;
    }
    return true;
  }

  public Position getPos() {
    return pos;
  }

  public static void main(String[] args) {
    PosicionCT a = new PosicionCT(0, 0);
    PosicionCT b = new PosicionCT(4, 4);

    PosicionCT c = new PosicionCT(5, 5);
    double d = c.distanciaALinea(a, b, true);
    //System.out.println(d);
    //System.out.println(c.distancia(b));

    c = new PosicionCT(1, 2);
    d = c.distanciaALinea(a, b, true);
    //System.out.println(d);
    //System.out.println(Math.sqrt(0.5*0.5*2));

    c = new PosicionCT(2, 1);
    d = c.distanciaALinea(a, b, true);
    //System.out.println(d);
    //System.out.println(Math.sqrt(0.5*0.5*2));

    c = new PosicionCT(-2, -0.5);
    d = c.distanciaALinea(a, b, true);
    //System.out.println(d);
    //System.out.println(c.distancia(a));

  }

}
