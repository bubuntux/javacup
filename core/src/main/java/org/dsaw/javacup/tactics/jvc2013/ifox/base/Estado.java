/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.base;

/**
 *
 * @author Usuario
 */
public abstract class Estado<T> {

    public void abrir(T objeto) {
    }

    public abstract void ejecutar(T objeto);

    public void cerrar(T objeto) {
    }

    public boolean mensajeRecibido(T objeto, Telegrama telegrama) {
        return false;
    }
}
