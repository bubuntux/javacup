package org.dsaw.javacup.model.engine;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerI;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.io.Serializable;

/**
 * Implementación de TacticDetail, usada internamente
 */
final class TacticaDetalleImpl implements Team, Serializable {

  private static final long serialVersionUID = 1L;

  private final String nombre;
  private final CountryCode pais;
  private final String entrenador;
  private final Color colorCamiseta;
  private final Color colorPantalon;
  private final Color colorCalcetas;
  private final Color colorFranja;
  private final Color colorPortero;
  private final UniformStyle estilo;
  private final Color colorCamiseta2;
  private final Color colorPantalon2;
  private final Color colorCalcetas2;
  private final Color colorFranja2;
  private final Color colorPortero2;
  private final UniformStyle estilo2;
  private final PlayerDetailImpl[] jugadores = new PlayerDetailImpl[11];

  /**
   * Copia el detalle y deja inmutables sus valores
   */
  TacticaDetalleImpl(Team impl) {
    this.nombre = impl.getName();
    this.pais = impl.getCountryCode();
    this.entrenador = impl.getCoachName();
    this.colorCamiseta = impl.getShirtColor();
    this.colorPantalon = impl.getShortsColor();
    this.colorCalcetas = impl.getSocksColor();
    this.colorFranja = impl.getShirtLineColor();
    this.colorPortero = impl.getGoalKeeper();
    this.estilo = impl.getStyle();
    this.colorCamiseta2 = impl.getShirtColor2();
    this.colorPantalon2 = impl.getShortsColor2();
    this.colorCalcetas2 = impl.getSocksColor2();
    this.colorFranja2 = impl.getShirtLineColor2();
    this.colorPortero2 = impl.getGoalKeeper2();
    this.estilo2 = impl.getStyle2();
    for (int i = 0; i < 11; i++) {
      this.jugadores[i] = new PlayerDetailImpl(impl.getPlayers()[i]);
    }
  }

  @Override
  public String getName() {
    return nombre;
  }

  @Override
  public CountryCode getCountryCode() {
    return pais;
  }

  @Override
  public String getCoachName() {
    return entrenador;
  }

  @Override
  public Color getShirtColor() {
    return colorCamiseta;
  }

  @Override
  public Color getShortsColor() {
    return colorPantalon;
  }

  @Override
  public Color getShirtLineColor() {
    return colorFranja;
  }

  @Override
  public Color getSocksColor() {
    return colorCalcetas;
  }

  @Override
  public UniformStyle getStyle() {
    return estilo;
  }

  @Override
  public PlayerI[] getPlayers() {
    return jugadores;
  }

  @Override
  public Color getGoalKeeper() {
    return colorPortero;
  }

  @Override
  public Color getShirtColor2() {
    return colorCamiseta2;
  }

  @Override
  public Color getShortsColor2() {
    return colorPantalon2;
  }

  @Override
  public Color getShirtLineColor2() {
    return colorFranja2;
  }

  @Override
  public Color getSocksColor2() {
    return colorCalcetas2;
  }

  @Override
  public Color getGoalKeeper2() {
    return colorPortero2;
  }

  @Override
  public UniformStyle getStyle2() {
    return estilo2;
  }
}

