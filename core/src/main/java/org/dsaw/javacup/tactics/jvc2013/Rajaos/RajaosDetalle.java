package org.dsaw.javacup.tactics.jvc2013.Rajaos;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerI;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

/**
 * @author Zafiu
 */
public class RajaosDetalle implements Team {

  @Override
  public String getName() {
    return "Rajaos F.C.";
  }

  @Override
  public CountryCode getCountryCode() {
    return CountryCode.ES;
  }

  @Override
  public String getCoachName() {
    return "Zafiu";
  }

  @Override
  public Color getShirtColor2() {
    return new Color(204, 50, 50);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(255, 0, 0);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(0, 153, 255);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.FRANJA_HORIZONTAL;
  }

  @Override
  public Color getShirtColor() {
    return new Color(204, 204, 204);
  }

  @Override
  public Color getShortsColor() {
    return new Color(204, 0, 204);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(255, 255, 102);
  }

  @Override
  public Color getSocksColor() {
    return new Color(255, 255, 153);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(75, 14, 248);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.FRANJA_HORIZONTAL;
  }

  class JugadorImpl implements PlayerI {

    String nombre;
    int numero;
    Color piel, pelo;
    double velocidad, remate, presicion;
    boolean portero;
    Position posicion;

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
    public String getName() {
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
  public PlayerI[] getPlayers() {
    return new PlayerI[]{
        new JugadorImpl("Barru", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d,
                        true),
        new JugadorImpl("Sierra", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                        1.0d, false),
        new JugadorImpl("David V.", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.68d,
                        0.31d, false),
        new JugadorImpl("Chuscar", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.75d,
                        0.0d, false),
        new JugadorImpl("Santi B", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.66d,
                        0.0d, false),
        new JugadorImpl("Miki", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.78d, 1.0d, 0.5d,
                        false),
        new JugadorImpl("Diego D", 7, new Color(255, 200, 150), new Color(50, 0, 0), 0.83d, 1.0d,
                        0.5d, false),
        new JugadorImpl("Chechu", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                        1.0d, false),
        new JugadorImpl("Zaf", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d,
                        false),
        new JugadorImpl("Espina", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.76d,
                        0.9d, false),
        new JugadorImpl("Ferni", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.83d,
                        1.0d, false)
    };
  }
}

