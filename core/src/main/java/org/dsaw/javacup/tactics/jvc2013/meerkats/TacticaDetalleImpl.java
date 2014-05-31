package org.dsaw.javacup.tactics.jvc2013.meerkats;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

/**
 * Implementación de TacticDetail.
 */
public class TacticaDetalleImpl implements TacticDetail {

  @Override
  public String getTacticName() {
    return "Meerkats FC";
  }

  @Override
  public CountryCode getCountry() {
    return CountryCode.CO;
  }

  @Override
  public String getCoach() {
    return "Christian Ortega";
  }

  @Override
  public Color getShirtColor() {
    return new Color(255, 0, 0);
  }

  @Override
  public Color getShortsColor() {
    return new Color(0, 0, 0);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(0, 0, 0);
  }

  @Override
  public Color getSocksColor() {
    return new Color(0, 0, 0);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(255, 153, 0);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.SIN_ESTILO;
  }

  @Override
  public Color getShirtColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(255, 0, 0);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(255, 0, 0);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(0, 0, 204);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.SIN_ESTILO;
  }

  @Override
  public PlayerDetail[] getPlayers() {
    return Alineacion.getJugadores();
  }

}
