/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.base;

/**
 *
 * @author Usuario
 */
public class MaquinaEstado<T> {

    private T objeto;
    private Estado<T> estadoActual;
    private Estado<T> estadoAnterior;
    private Estado<T> estadoGlobal;

    public MaquinaEstado(T objeto) {
        this.objeto = objeto;
    }

    public void actualizar() {
        if (estadoGlobal != null) {
            estadoGlobal.ejecutar(objeto);
        }
        if (estadoActual != null) {
            estadoActual.ejecutar(objeto);
        }
    }

    public void cambiarEstado(Estado nuevoEstado) {
        estadoAnterior = estadoActual;
        estadoActual.cerrar(objeto);
        estadoActual = nuevoEstado;
        estadoActual.abrir(objeto);
    }

    public void revertirCambioDeEstado() {
        cambiarEstado(estadoAnterior);
    }

    public boolean manejarMensaje(Telegrama telegrama) {
        if (estadoActual != null) {
            if (estadoActual.mensajeRecibido(objeto, telegrama)) {
                return true;
            }
        }
        if (estadoGlobal != null) {
            if (estadoGlobal.mensajeRecibido(objeto, telegrama)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnEstado(Estado estado) {
        return estadoActual.getClass() == estado.getClass();
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado<T> estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Estado getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(Estado<T> estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public Estado getEstadoGlobal() {
        return estadoGlobal;
    }

    public void setEstadoGlobal(Estado estadoGlobal) {
        this.estadoGlobal = estadoGlobal;
    }
}
