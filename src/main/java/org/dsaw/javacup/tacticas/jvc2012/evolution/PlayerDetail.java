package org.dsaw.javacup.tacticas.jvc2012.evolution;

import java.awt.Color;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.util.Position;;

class JugadorImpl implements PlayerDetail {

    String nombre;
    int numero;
    Color piel, pelo;
    double velocidad, remate, presicion;
    boolean portero;
    Position Position;

    public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
            double velocidad, double remate, double presicion, boolean portero) {
        this.nombre=nombre;
        this.numero=numero;
        this.piel=piel;
        this.pelo=pelo;
        this.velocidad=velocidad;
        this.remate=remate;
        this.presicion=presicion;
        this.portero=portero;
    }

    @Override
    public String getPlayerName() {
        return nombre;
    }

    @Override
    public Color getSkinColor() {
        return piel;
    }

    @Override
    public Color getHairColor() {
        return pelo;
    }

    @Override
    public int getNumber() {
        return numero;
    }

    @Override
    public boolean isGoalKeeper() {
        return portero;
    }

    @Override
    public double getSpeed() {
        return velocidad;
    }

    @Override
    public double getPower() {
        return remate;
    }

    @Override
    public double getPrecision() {
        return presicion;
    }

}
