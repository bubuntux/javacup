package org.dsaw.javacup.tactics.jvc2013.meerkats;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

/**
 * Implementación de TacticDetail.
 */
public class TacticaDetalleImpl implements Team {

  @Override
  public String getName() {
    return "Meerkats FC";
  }

  @Override
  public CountryCode getCountryCode() {
    return CountryCode.CO;
  }

  @Override
  public String getCoachName() {
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
  public Player[] getPlayers() {
    return Alineacion.getJugadores();
  }

}
