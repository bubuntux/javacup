/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.ifox.futbol;

import org.dsaw.javacup.model.util.Position;

/**
 *
 * @author Usuario
 */
public class Alineacion {

    private Tipo tipo;
    private Position[] posiciones;

    public Alineacion(Tipo tipo) {
        this.tipo = tipo;
        posiciones = new Position[11];
    }

    public Alineacion(Tipo tipo, Position[] posiciones) {
        this.tipo = tipo;
        this.posiciones = posiciones;
    }

    public Position getPosicion(int id) {
        return posiciones[id];
    }

    public void setPosicion(int id, Position posicion) {
        posiciones[id] = posicion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public enum Tipo {

        RECEPCION,
        SAQUE,
        ATAQUE_FUERTE,
        ATAQUE_NORMAL,
        DEFENSA_FUERTE,
        DEFENSA_NORMAL,
    }
}
