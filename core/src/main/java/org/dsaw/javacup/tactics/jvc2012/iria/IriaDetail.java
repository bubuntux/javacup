package org.dsaw.javacup.tactics.jvc2012.iria;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

public class IriaDetail implements TacticDetail {

  @Override
  public String getTacticName() {
    return "Iria";
  }

  @Override
  public CountryCode getCountry() {
    return CountryCode.ES;
  }

  @Override
  public String getCoach() {
    return "Juan Carlos";
  }

  @Override
  public Color getShirtColor() {
    return new Color(51, 204, 255);
  }

  @Override
  public Color getShortsColor() {
    return new Color(255, 0, 102);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getSocksColor() {
    return new Color(0, 204, 255);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(0, 0, 0);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.FRANJA_HORIZONTAL;
  }

  @Override
  public Color getShirtColor2() {
    return new Color(255, 0, 204);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(0, 204, 255);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(255, 0, 204);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(255, 255, 0);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.FRANJA_HORIZONTAL;
  }

  class JugadorImpl implements PlayerDetail {

    String nombre;
    int numero;
    Color piel, pelo;
    double velocidad, remate, presicion;
    boolean portero;
    Position Position;

    public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                       double velocidad, double remate, double presicion, boolean portero) {
      this.nombre = nombre;
      this.numero = numero;
      this.piel = piel;
      this.pelo = pelo;
      this.velocidad = velocidad;
      this.remate = remate;
      this.presicion = presicion;
      this.portero = portero;
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

  @Override
  public PlayerDetail[] getPlayers() {
    return new PlayerDetail[]{
        new JugadorImpl("Perlis", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.71d,
                        1.0d, true),
        new JugadorImpl("Wilkes", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.25d,
                        1.0d, false),
        new JugadorImpl("Hamming", 3, new Color(255, 200, 150), new Color(255, 0, 51), 1.0d, 0.25d,
                        1.0d, false),
        new JugadorImpl("Minsky", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.25d,
                        1.0d, false),
        new JugadorImpl("Wilkinson", 5, new Color(255, 200, 150), new Color(0, 102, 102), 1.0d,
                        0.36d, 1.0d, false),
        new JugadorImpl("McCarthy", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.36d,
                        1.0d, false),
        new JugadorImpl("Dijkstra", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.36d,
                        1.0d, false),
        new JugadorImpl("Bachman", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.36d,
                        1.0d, false),
        new JugadorImpl("Knut", 9, new Color(255, 200, 150), new Color(255, 255, 0), 1.0d, 1.0d,
                        1.0d, false),
        new JugadorImpl("Newell", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d,
                        1.0d, false),
        new JugadorImpl("Simon", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.8d,
                        1.0d, false)
    };
  }
}
