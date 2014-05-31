/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Usuario
 */
public class Util {

  public static List<Vector2d> copiarLista(List<Vector2d> lista) {
    List<Vector2d> copia = new ArrayList<>(lista.size());
    for (Vector2d vector : lista) {
      try {
        copia.add((Vector2d) vector.clone());
      } catch (CloneNotSupportedException ex) {
        Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return copia;
  }
}
