/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MaN
 */
public class MSGTacticaDetalle implements Team {

  private static final String NOMBRE = "Mansporting de Gijón";
  private static final CountryCode PAIS = CountryCode.ES;
  private static final String ENTRENADOR = "MaN";
  private static final Color COLOR_CAMISETA = new Color(217, 227, 228);
  private static final Color COLOR_PANTALON = new Color(28, 75, 155);
  private static final Color COLOR_FRANJA = new Color(120, 0, 0);
  private static final Color COLOR_CALCETOS = new Color(28, 75, 155);
  private static final Color COLOR_PORTERO = new Color(88, 181, 111);
  private static final UniformStyle ESTILO_UNIFORME = UniformStyle.LINEAS_VERTICALES;
  private static final Color COLOR_CAMISETA2 = new Color(200, 0, 0);
  private static final Color COLOR_PANTALON2 = new Color(200, 0, 0);
  private static final Color COLOR_FRANJA2 = new Color(200, 0, 0);
  private static final Color COLOR_CALCETOS2 = new Color(200, 0, 0);
  private static final Color COLOR_PORTERO2 = new Color(255, 143, 83);
  private static final UniformStyle ESTILO_UNIFORME2 = UniformStyle.SIN_ESTILO;
  private final List<MSGJugadorDetalle> jugadoresDetalle;

  public MSGTacticaDetalle(Collection<? extends MSGJugadorDetalle> jugadoresDetalle) {
    this.jugadoresDetalle = new LinkedList<>();
    this.jugadoresDetalle.addAll(jugadoresDetalle);
  }

  @Override
  public String getName() {
    return NOMBRE;
  }

  @Override
  public CountryCode getCountryCode() {
    return PAIS;
  }

  @Override
  public String getCoachName() {
    return ENTRENADOR;
  }

  @Override
  public Color getShirtColor() {
    return COLOR_CAMISETA;
  }

  @Override
  public Color getShortsColor() {
    return COLOR_PANTALON;
  }

  @Override
  public Color getShirtLineColor() {
    return COLOR_FRANJA;
  }

  @Override
  public Color getSocksColor() {
    return COLOR_CALCETOS;
  }

  @Override
  public Color getGoalKeeper() {
    return COLOR_PORTERO;
  }

  @Override
  public UniformStyle getStyle() {
    return ESTILO_UNIFORME;
  }

  @Override
  public Color getShirtColor2() {
    return COLOR_CAMISETA2;
  }

  @Override
  public Color getShortsColor2() {
    return COLOR_PANTALON2;
  }

  @Override
  public Color getShirtLineColor2() {
    return COLOR_FRANJA2;
  }

  @Override
  public Color getSocksColor2() {
    return COLOR_CALCETOS2;
  }

  @Override
  public Color getGoalKeeper2() {
    return COLOR_PORTERO2;
  }

  @Override
  public UniformStyle getStyle2() {
    return ESTILO_UNIFORME2;
  }

  @Override
  public Player[] getPlayers() {
    return MSGUtils.convertJugadores(jugadoresDetalle);
  }
}
