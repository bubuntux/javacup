/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.base;

import java.util.Comparator;

/**
 * @author Usuario
 */
public class TelegramaComparador implements Comparator<Telegrama> {

  @Override
  public int compare(Telegrama tel1, Telegrama tel2) {
    if (tel1.getTiempoDeEntrega() > tel2.getTiempoDeEntrega()) {
      return 1;
    } else if (tel1.getTiempoDeEntrega() < tel2.getTiempoDeEntrega()) {
      return -1;
    } else {
      return 0;
    }
  }
}
