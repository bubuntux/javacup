package org.dsaw.javacup.tactics.jvc2012.romedal;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

public class TacticaRomedalusTeamDetalleImpl implements TacticDetail {

  @Override
  public String getTacticName() {
    return "Romedalus";
  }

  @Override
  public CountryCode getCountry() {
    return CountryCode.CO;
  }

  @Override
  public String getCoach() {
    return "RolandUN";
  }

  @Override
  public Color getShirtColor() {
    return new Color(255, 0, 51);
  }

  @Override
  public Color getShortsColor() {
    return new Color(0, 0, 153);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getSocksColor() {
    return new Color(255, 255, 0);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(255, 153, 0);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.LINEAS_VERTICALES;
  }

  @Override
  public Color getShirtColor2() {
    return new Color(0, 0, 0);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(0, 0, 0);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(0, 0, 0);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(255, 255, 255);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.SIN_ESTILO;
  }

  class JugadorImpl implements PlayerDetail {

    String nombre;
    int numero;
    Color piel, pelo;
    double velocidad, remate, presicion;
    boolean portero;
    Position Position;

    public JugadorImpl(final String nombre, final int numero, final Color piel, final Color pelo,
                       final double velocidad, final double remate, final double presicion,
                       final boolean portero) {
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
    return new PlayerDetail[]{//
                              // new JugadorImpl("Ren� Iguita", 1, new Color(238, 190, 147), new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, true),//
                              // new JugadorImpl("Cordoba", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1d, 0.4d, false),//
                              // new JugadorImpl("Yepez", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.5d, false),//
                              // new JugadorImpl("Perea", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.5d, false),//
                              // new JugadorImpl("Leonel", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.67d, false),//
                              // new JugadorImpl("Ronaldinho", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.51d, 0.42d, false),//
                              // new JugadorImpl("Kak�", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),//
                              // new JugadorImpl("Roland :D", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),//
                              // new JugadorImpl("Angel", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),//
                              // new JugadorImpl("Pel�", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),//
                              // new JugadorImpl("Adriano", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false) };//

                              new JugadorImpl("Valdes", 1, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.75d, 1.0d, true),//
                              new JugadorImpl("Pique", 2, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Puyol", 3, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Muniesa", 4, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Montoya", 5, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Busquet", 6, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Xavi", 7, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Iniesta", 8, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.35d, 1.0d, false),//
                              new JugadorImpl("Bojan", 9, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.65d, 1.0d, false),//
                              new JugadorImpl("Messi", 10, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),//
                              new JugadorImpl("Pedro", 11, new Color(255, 200, 150),
                                              new Color(50, 0, 0), 1.0d, 0.65d, 1.0d, false)};//

    // new JugadorImpl("Jones", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, true),//
    // new JugadorImpl("Hamon", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Viano", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Weissert", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Chque", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Janin", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Lacroix", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Lextrait", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.625d, 0.5d, false),//
    // new JugadorImpl("Laburthe", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
    // new JugadorImpl("Niewen", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
    // new JugadorImpl("Vizzari", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false) };//

  }
}
