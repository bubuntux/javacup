/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

/**
 *
 * @author MaN
 */
public class MSGEstadisticas {

    private final double precision;
    private final double remate;
    private final double velocidad;

    private MSGEstadisticas(double precision, double remate, double velocidad) {
        this.precision = precision;
        this.remate = remate;
        this.velocidad = velocidad;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRemate() {
        return remate;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public static class Builder {

        private double precision;
        private double remate;
        private double velocidad;

        public Builder setPrecision(double precision) {
            this.precision = precision;
            return this;
        }

        public Builder setRemate(double remate) {
            this.remate = remate;
            return this;
        }

        public Builder setVelocidad(double velocidad) {
            this.velocidad = velocidad;
            return this;
        }

        public MSGEstadisticas getMSGEstadisticas()
        {
            return new MSGEstadisticas(this.precision, this.remate, this.velocidad);
        }
    }
}
