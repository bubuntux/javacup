package org.dsaw.javacup.model.util;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.PlayerStats;
import org.dsaw.javacup.model.PlayerStyle;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.TeamStyle;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.HashSet;

/**
 * Clase usada para validar una tactica
 */
public final class TacticValidate { //TODO remove

  /**
   * Clase para Validar implementaciones tactica
   */
  public static void validateDetail(String name, Team t) throws Exception {
    if (t == null) {
      throw new Exception(name + "TacticDetail null");
    }
    TeamStyle teamStyle = t.getStyle();
    if (t.getStyle() == null) {
      throw new Exception(name + "TacticDetail: estilo es nulo");
    }
    if (teamStyle.getSocksColor() == null ||
        teamStyle.getShirtColor() == null ||
        teamStyle.getShirtLineColor() == null ||
        teamStyle.getShortsColor() == null ||
        teamStyle.getGoalkeeper() == null) {
      throw new Exception(name
                          + "TacticDetail: ColorCalcetas , ColorCamiseta , ColorFranja , ColorPantalon o ColorPortero es nulo");
    }
    if (t.getName() == null || t.getCountryCode() == null) {
      throw new Exception(name + "TacticDetail: Entrenador, Nombre o Pais nulo");
    }
    if (t.getName().trim().length() == 0 ||
        t.getCountryCode() == null) {
      throw new Exception(name + "TacticDetail: Entrenador, Nombre o Pais vacio");
    }
    if (t.getPlayers() == null) {
      throw new Exception(name + "TacticDetail: Jugadores nulo");
    }

    if (t.getPlayers().size() != 11) {
      throw new Exception(name + "TacticDetail: Cantidad de Jugadores distinto de 11");
    }

    int porteros = 0;
    double creditos = 0;
    HashSet<Integer> set = new HashSet<>();
    for (int i = 0; i < 11; i++) {
      Player player = t.getPlayers().get(i);
      if (player == null) {
        throw new Exception(name + "TacticDetail: Jugador[" + i + "] nulo");
      }
      PlayerStyle playerStyle = player.getStyle();
      if (playerStyle.getHairColor() == null || playerStyle.getSkinColor() == null) {
        throw new Exception(name + "TacticDetail: Jugador[" + i + "] ColorPiel o ColorPelo nulo");
      }
      if (player.getName() == null) {
        throw new Exception(name + "TacticDetail: Jugador[" + i + "] Nombre nulo");
      }
      if (player.getNumber() <= 0 || player.getNumber() > 99) {
        throw new Exception(
            name + "TacticDetail: Jugador[" + i + "] Numero fuera del rango [1,99]");
      }
      if (set.contains(player.getNumber())) {
        throw new Exception(
            name + "TacticDetail: Numero de jugador " + player.getNumber()
            + " repetido");
      }
      set.add((int) player.getNumber());
      PlayerStats stats = player.getStats();
      if (stats.getPrecision() < 0 || stats.getPrecision() > 1) {
        throw new Exception(
            name + "TacticDetail: Jugador[" + i + "] con presicion fuera del rango [0,1]");
      }
      if (stats.getPower() < 0 || stats.getPower() > 1) {
        throw new Exception(
            name + "TacticDetail: Jugador[" + i + "] con remate fuera del rango [0,1]");
      }
      if (stats.getSpeed() < 0 || stats.getSpeed() > 1) {
        throw new Exception(
            name + "TacticDetail: Jugador[" + i + "] con velocidad fuera del rango [0,1]");
      }
      creditos = creditos + stats.getPrecision();
      creditos = creditos + stats.getSpeed();
      creditos = creditos + stats.getPower();
      if (player.isGoalkeeper()) {
        porteros++;
      }
    }
    creditos = (int) (creditos * 10000) / 10000d;
    if (porteros != 1) {
      throw new Exception(name + "TacticDetail: Cantidad porteros distinto de 1");
    }
    if (creditos > Constants.CREDITOS_INICIALES) {
      throw new Exception(name + "TacticDetail: Uso " + creditos + " creditos, pero son permitidos "
                          + Constants.CREDITOS_INICIALES);
    }
    //if (equalsColors(t)) {
    //throw new Exception(nombre + "TacticDetail: modificar colores de la camiseta, muy parecidos al uniforme alternativo ");
    //}
  }

  /**
   * Valida una alineacion para el saque y para la recepcion
   */
  public static Position[][] validatePositions(String name, Position[] starts, Position[] noStarts)
      throws Exception {
    Position[] recibe0 = new Position[11], saca0 = new Position[11];
    for (int i = 0; i < 11; i++) {
      if (noStarts[i] == null) {
        throw new Exception("Alineacion Recibe[" + i + "] nulo");
      }
      if (starts[i] == null) {
        throw new Exception("Alineacion Saca[" + i + "] nulo");
      }
      recibe0[i] = new Position(noStarts[i]);
      saca0[i] = new Position(starts[i]);
    }
    if (noStarts == null) {
      throw new Exception("Alineacion Recibe nulo");
    }
    if (starts == null) {
      throw new Exception("Alineacion Recibe nulo");
    }
    if (noStarts.length != 11) {
      throw new Exception("Alineacion Recibe tamaño distinto de 11");
    }
    if (starts.length != 11) {
      throw new Exception("Alineacion Saca tamaño distinto de 11");
    }
    for (int i = 0; i < 11; i++) {
      if (saca0[i].getY() > 0) {
        saca0[i] = new Position(saca0[i].getX(), 0);
      }
      if (recibe0[i].getY() > 0) {
        recibe0[i] = new Position(recibe0[i].getX(), 0);
      }
      if (recibe0[i].distance(Constants.centroCampoJuego) <= Constants.RADIO_CIRCULO_CENTRAL) {
        double ang = Constants.centroCampoJuego.angle(recibe0[i]);
        recibe0[i] = Constants.centroCampoJuego;
        recibe0[i] = recibe0[i].moveAngle(ang, Constants.RADIO_CIRCULO_CENTRAL + 1);
      }
    }
    return new Position[][]{saca0, recibe0};
  }

  private static double distancia(Color c1, Color c2) {
    return Math.sqrt((c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed())
                     + (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen())
                     + (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue()));
  }

  private static Color mesclarColor(Color c1, Color c2, double p1) {
    double p2 = 1d - p1;
    return new Color((int) (c1.getRed() * p1 + c2.getRed() * p2),
                     (int) (c1.getGreen() * p1 + c2.getGreen() * p2),
                     (int) (c1.getBlue() * p1 + c2.getBlue() * p2));
  }

  private static double getP1(UniformStyle est) {
    switch (est) {
      case LINEAS_HORIZONTALES:
        return .5;
      case LINEAS_VERTICALES:
        return .5;
      case FRANJA_VERTICAL:
        return .8;
      case FRANJA_HORIZONTAL:
        return .8;
      case FRANJA_DIAGONAL:
        return .8;
      case SIN_ESTILO:
        return 1;
    }
    return 0;
  }

  private static double umbral = 150;

  /**
   * Indica true si es necesario que el equipo visita cambie a su uniforme alternativo
   */
  public static boolean useAlternativeColors(Team local, Team visita) {
    Color cl1, cv1, cv2;
    TeamStyle localStyle = local.getStyle();
    cl1 =
        mesclarColor(localStyle.getShirtColor(), localStyle.getShirtLineColor(),
                     getP1(localStyle.getUniformStyle()));
    TeamStyle visitaStyle = visita.getStyle();
    cv1 =
        mesclarColor(visitaStyle.getShirtColor(), visitaStyle.getShirtLineColor(),
                     getP1(visitaStyle.getUniformStyle()));
    cv2 =
        mesclarColor(visitaStyle.getShirtColor2(), visitaStyle.getShirtLineColor2(),
                     getP1(visitaStyle.getUniformStyle2()));
    double d1 = distancia(cl1, cv1);
    double d2 = distancia(cl1, cv2);
    return d1 < umbral && d2 > d1;
  }

  /**
   * Indica true si los dos unifermes de una tactica son muy parecidos
   */
  public static boolean equalsColors(Team local) {

    Color cl1, cl2;
    TeamStyle localStyle = local.getStyle();
    cl1 =
        mesclarColor(localStyle.getShirtColor(), localStyle.getShirtLineColor(),
                     getP1(localStyle.getUniformStyle()));
    cl2 =
        mesclarColor(localStyle.getShirtColor2(), localStyle.getShirtLineColor2(),
                     getP1(localStyle.getUniformStyle2()));
    double d = distancia(cl1, cl2);
    return d < umbral;
  }
}
