package org.dsaw.javacup.tactics.jvc2013.emandem.configuracion;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

public class TacticaDetalleImpl implements TacticDetail {

  //Detalles del equipo
  @Override
  public String getTacticName() {
    return "EmAndEm";
  }

  @Override
  public CountryCode getCountry() {
    return CountryCode.MX;
  }

  @Override
  public String getCoach() {
    return "Marcos E. Coronado B.";
  }

  // Detalles del uniforme 1
  @Override
  public Color getShirtColor() {
    return new Color(0, 153, 204);
  }

  @Override
  public Color getShortsColor() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getShirtLineColor() {
    return new Color(102, 102, 102);
  }

  @Override
  public Color getSocksColor() {
    return new Color(102, 0, 0);
  }

  @Override
  public Color getGoalKeeper() {
    return new Color(153, 153, 0);
  }

  @Override
  public UniformStyle getStyle() {
    return UniformStyle.FRANJA_VERTICAL;
  }

  // Detalles del uniforme 2
  @Override
  public Color getShirtColor2() {
    return new Color(102, 204, 0);
  }

  @Override
  public Color getShortsColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getShirtLineColor2() {
    return new Color(0, 153, 153);
  }

  @Override
  public Color getSocksColor2() {
    return new Color(255, 255, 255);
  }

  @Override
  public Color getGoalKeeper2() {
    return new Color(0, 0, 0);
  }

  @Override
  public UniformStyle getStyle2() {
    return UniformStyle.FRANJA_HORIZONTAL;
  }


  // Configuraci�n de los jugadores
  @Override
  public PlayerDetail[] getPlayers() {
    return new PlayerDetail[]{
        new JugadorImpl("Heineken", 1, new Color(255, 204, 153), new Color(255, 204, 102), 1.0d,
                        1.0d, 1.0d, true),
        new JugadorImpl("Mort Subite", 2, new Color(255, 200, 150), new Color(255, 204, 102), 0.76d,
                        0.5d, 1.0d, false),
        new JugadorImpl("La Biere du Demon", 3, new Color(255, 200, 150), new Color(255, 204, 102),
                        0.76d, 0.5d, 1.0d, false),
        new JugadorImpl("Miller Genuine Draft", 4, new Color(255, 200, 150),
                        new Color(255, 204, 102), 0.76d, 0.5d, 1.0d, false),
        new JugadorImpl("Waldhaus", 5, new Color(255, 200, 150), new Color(255, 204, 102), 0.75d,
                        0.5d, 0.75d, false),
        new JugadorImpl("Duff", 6, new Color(255, 200, 150), new Color(255, 204, 102), 0.75d, 0.5d,
                        0.75d, false),
        new JugadorImpl("Karhu III", 7, new Color(255, 200, 150), new Color(255, 204, 102), 1.0d,
                        1.0d, 1.0d, false),
        new JugadorImpl("Guinness", 8, new Color(255, 200, 150), new Color(255, 204, 102), 1.0d,
                        1.0d, 1.0d, false),
        new JugadorImpl("Minerva", 9, new Color(255, 200, 150), new Color(255, 204, 102), 0.72d,
                        0.85d, 1.0d, false),
        new JugadorImpl("Berliner Kindl Weisse", 10, new Color(255, 200, 150),
                        new Color(255, 204, 102), 0.72d, 0.85d, 1.0d, false),
        new JugadorImpl("Youngs Double Chocolate Stout", 11, new Color(255, 200, 150),
                        new Color(255, 204, 102), 0.72d, 0.85d, 1.0d, false)
    };
  }
}