package org.dsaw.javacup.tactics.jvc2012.rchavarria;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

class TacticDetailImpl implements TacticDetail {

  @Override
  public String getTacticName() {
    return "rchavarria";
  }

  @Override
  public CountryCode getCountry() {
    return CountryCode.ES;
  }

  @Override
  public String getCoach() {
    return "Ruben";
  }

  @Override
  public Color getShirtColor() {
    return new Color(207, 103, 21);
  }

  @Override
  public Color getShortsColor() {
    return new Color(24, 33, 189);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(14, 42, 145);
  }

  @Override
  public Color getSocksColor() {
    return new Color(155, 226, 55);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(156, 120, 108);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.SIN_ESTILO;
  }

  @Override
  public Color getShirtColor2() {
    return new Color(21, 112, 183);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(201, 5, 36);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(2, 189, 190);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(80, 241, 13);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(146, 5, 53);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.SIN_ESTILO;
  }

  @Override
  public PlayerDetail[] getPlayers() {
    return new PlayerDetail[]{
        new PlayerImpl("Portero", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       0.0d, true),
        new PlayerImpl("Lateral Izqdo", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                       1.0d, 0.0d, false),
        new PlayerImpl("Defensa Izqdo", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                       1.0d, 0.61d, false),
        new PlayerImpl("Defensa Dcho", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       0.61d, false),
        new PlayerImpl("Lateral Dcho", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       0.0d, false),
        new PlayerImpl("Avance Izqdo", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       0.62d, false),
        new PlayerImpl("Medio Izqdo", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.54d,
                       1.0d, false),
        new PlayerImpl("Medio Dcho", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.49d,
                       1.0d, false),
        new PlayerImpl("Avance Dcho", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       0.63d, false),
        new PlayerImpl("Delantero", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       1.0d, false),
        new PlayerImpl("Goleador", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                       1.0d, false)
    };
  }
}