/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.base;

import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Jugador;

/**
 * @author Usuario
 */
public class Telegrama {

  private double tiempoDeEntrega;
  private Jugador emisor;
  private Jugador receptor;
  private Mensaje mensaje;
  private Object extra;

  public Telegrama(Jugador emisor, Jugador receptor, Mensaje mensaje) {
    this.emisor = emisor;
    this.receptor = receptor;
    this.mensaje = mensaje;

    tiempoDeEntrega = Mensajero.INMEDIATO;
    extra = Mensajero.NO_EXTRA;
  }

  public Telegrama(Jugador emisor, Jugador receptor, Mensaje mensaje,
                   Object extra) {
    this.emisor = emisor;
    this.receptor = receptor;
    this.mensaje = mensaje;
    this.extra = extra;

    tiempoDeEntrega = Mensajero.INMEDIATO;
  }

  public Telegrama(double tiempoDeEntrega, Jugador emisor, Jugador receptor,
                   Mensaje mensaje, Object extra) {
    this.tiempoDeEntrega = tiempoDeEntrega;
    this.emisor = emisor;
    this.receptor = receptor;
    this.mensaje = mensaje;
    this.extra = extra;
  }

  public Jugador getEmisor() {
    return emisor;
  }

  public void setEmisor(Jugador emisor) {
    this.emisor = emisor;
  }

  public Object getExtra() {
    return extra;
  }

  public void setExtra(Object extra) {
    this.extra = extra;
  }

  public Mensaje getMensaje() {
    return mensaje;
  }

  public void setMensaje(Mensaje mensaje) {
    this.mensaje = mensaje;
  }

  public Jugador getReceptor() {
    return receptor;
  }

  public void setReceptor(Jugador receptor) {
    this.receptor = receptor;
  }

  public double getTiempoDeEntrega() {
    return tiempoDeEntrega;
  }

  public void setTiempoDeEntrega(double tiempoDeEntrega) {
    this.tiempoDeEntrega = tiempoDeEntrega;
  }
}
