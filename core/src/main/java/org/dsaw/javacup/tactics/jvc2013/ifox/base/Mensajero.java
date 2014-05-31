/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.base;

import org.dsaw.javacup.tactics.jvc2013.ifox.futbol.Jugador;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Usuario
 */
public class Mensajero {

  public static final int INMEDIATO = 0;
  public static final Object NO_EXTRA = null;
  private static final Mensajero INSTANCIA = new Mensajero();
  private SortedSet<Telegrama> telegramas;

  public Mensajero() {
    if (INSTANCIA != null) {
      throw new IllegalStateException("La instancia ya existe");
    }

    telegramas = new TreeSet<>(new TelegramaComparador());
  }

  public void entregarMensaje(double retardo, Jugador emisor,
                              Jugador receptor, Mensaje mensaje, Object extra) {
    Telegrama telegrama = new Telegrama(emisor, receptor, mensaje, extra);

    if (retardo <= 0) {
//            System.out.println("Mensajero: Mensaje "
//                    + mensaje + " de entrega inmediata");
      entregar(receptor, telegrama);
    } else {
//            System.out.println("Mensajero: Mensaje "
//                    + mensaje + " postergado " + retardo + "ms");
      double tiempoActual = System.currentTimeMillis();
      telegrama.setTiempoDeEntrega(tiempoActual + retardo);
      telegramas.add(telegrama);
    }
  }

  public void entragrMensajesRetrasados() {
    double tiempoActual = System.currentTimeMillis();
    if (!telegramas.isEmpty()
        && telegramas.first().getTiempoDeEntrega() < tiempoActual
        && telegramas.first().getTiempoDeEntrega() > 0) {
      Telegrama telegrama = telegramas.first();
      Jugador receptor = telegrama.getReceptor();
      entregar(receptor, telegrama);
      telegramas.remove(telegrama);
    }
  }

  private void entregar(Jugador receptor, Telegrama mensaje) {
    receptor.recibirMensaje(mensaje);
  }

  public synchronized static Mensajero getInstancia() {
    return INSTANCIA;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }
}
