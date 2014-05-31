package org.dsaw.javacup.tactics.jvc2013.CTeam.tactica;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

class TacticaDetalleImpl implements TacticDetail {

  @Override
  public String getTacticName() {
    return "cTeam";
  }

  @Override
  public CountryCode getCountry() {
    return CountryCode.CO;
  }

  @Override
  public String getCoach() {
    return "Con Tragos";
  }

  @Override
  public Color getShirtColor() {
    return new Color(243, 210, 26);
  }

  @Override
  public Color getShortsColor() {
    return new Color(22, 70, 115);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(73, 232, 100);
  }

  @Override
  public Color getSocksColor() {
    return new Color(115, 138, 54);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(35, 174, 116);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.FRANJA_DIAGONAL;
  }

  @Override
  public Color getShirtColor2() {
    return new Color(220, 207, 239);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(208, 217, 187);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(137, 97, 55);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(181, 22, 234);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(190, 43, 231);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.FRANJA_DIAGONAL;
  }

  class JugadorImpl implements PlayerDetail {

    String nombre;
    int numero;
    Color piel, pelo;
    double velocidad, remate, presicion;
    boolean portero;
    Position posicion;

    public JugadorImpl(String nombre, int numero, Color piel, Color pelo, double velocidad,
                       double remate, double presicion, boolean portero) {
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
        new JugadorImpl("El Brincon", 1, new Color(252, 239, 227), new Color(50, 0, 0), 1.0d, 0.77d,
                        0.55d, true),
        new JugadorImpl("LaPared", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.59d,
                        0.6d, false),
        new JugadorImpl("Tronco1", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.56d,
                        0.6d, false),
        new JugadorImpl("Tronco2", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.6d,
                        0.8d, false),
        new JugadorImpl("PataBrava", 5, new Color(152, 88, 30), new Color(50, 0, 0), 1.0d, 0.69d,
                        0.8d, false),
        new JugadorImpl("Speedy1", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.67d,
                        0.8d, false),
        new JugadorImpl("NoEsMessiPeroJuega!", 7, new Color(204, 151, 102), new Color(50, 0, 0),
                        1.0d, 0.9d, 0.8d, false),
        new JugadorImpl("MedioTronco", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                        0.98d,
                        0.8d, false),
        new JugadorImpl("Speedy2", 9, new Color(79, 41, 6), new Color(50, 0, 0), 1.0d, 0.71d, 0.8d,
                        false),
        new JugadorImpl("NoMete", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.94d,
                        0.8d, false),
        new JugadorImpl("NiUno", 11, new Color(110, 73, 41), new Color(50, 0, 0), 1.0d, 0.94d, 0.8d,
                        false)};
  }
}