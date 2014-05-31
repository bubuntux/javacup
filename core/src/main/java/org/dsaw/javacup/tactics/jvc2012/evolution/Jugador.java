package org.dsaw.javacup.tactics.jvc2012.evolution;

import org.dsaw.javacup.model.util.Position;

public class Jugador {

  public static final int DELANTERO = 4;
  public static final int CENTROCAMPISTA = 3;
  public static final int DEFENSA = 2;
  public static final int PORTERO = 1;

  public static int RADIO_ZONA = 10;

  private Integer id;

  private Position posicion;

  private Position posicionBase;

  private Position posicionAtaqueBase;

  private Position posicionDesmarque;

  private int rol;

  private boolean puedoRecuperar;

  private Rival rivalMarcado;

  private Rival[] rivalesZona;

  public Jugador(int i) {
    id = i;
  }

  public Position getPosicion() {
    return posicion;
  }

  public void setPosicion(Position posicion) {
    this.posicion = posicion;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Position getPosicionBase() {
    return posicionBase;
  }

  public void setPosicionBase(Position posicionBase) {
    this.posicionBase = posicionBase;
  }

  public int getRol() {
    return rol;
  }

  public void setRol(int rol) {
    this.rol = rol;
  }

  public boolean isPuedoRecuperar() {
    return puedoRecuperar;
  }

  public void setPuedoRecuperar(boolean puedoRecuperar) {
    this.puedoRecuperar = puedoRecuperar;
  }

  public Rival getRivalMarcado() {
    return rivalMarcado;
  }

  public void setRivalMarcado(Rival rivalMarcado) {
    this.rivalMarcado = rivalMarcado;
  }

  public Position getPosicionDesmarque() {
    return posicionDesmarque;
  }

  public void setPosicionDesmarque(Position posicionDesmarque) {
    this.posicionDesmarque = posicionDesmarque;
  }

  public Rival[] getRivalesZona() {
    return rivalesZona;
  }

  public void setRivalesZona(Rival[] rivalesZona) {
    this.rivalesZona = rivalesZona;
  }

  public Position getPosicionAtaqueBase() {
    return posicionAtaqueBase;
  }

  public void setPosicionAtaqueBase(Position posicionAtaqueBase) {
    this.posicionAtaqueBase = posicionAtaqueBase;
  }

}
