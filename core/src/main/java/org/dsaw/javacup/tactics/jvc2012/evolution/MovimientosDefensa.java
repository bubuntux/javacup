package org.dsaw.javacup.tactics.jvc2012.evolution;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class MovimientosDefensa {

  private static double
      LINEA_DEFENSA_TOPE =
      Constants.centroArcoInf.getY() + Constants.LARGO_AREA_GRANDE;
  public static double LINEA_DEFENSA_BASE = -25;
  public static double LINEA_CENTROCAMPO_BASE = -15;
  public static double LINEA_DELANTERA_BASE = 15;

  private static double lineaDefensaActual = -1000;
  private static double lineaCentroActual = -1000;
  private static double lineaDelanteraActual = -1000;

  public static Command moverDefensa(Jugador jugador, Position posicionBalon, Jugador[] jugadores,
                                     Rival[] rivales, GameSituations sp, Position posicion) {
    // TODO Auto-generated method stub
    Command comando = new CommandMoveTo(jugador.getId(), posicion);
    Position posRecuperacion = TacticasUtil.puedoRecuperar(jugador, rivales, sp);
    if (TacticasUtil.tengoBalon(jugador.getId(), sp.canKick())) {
      Pase pase = TacticasUtil.calcularPosicionPase(jugador, jugadores, rivales, sp);

      if (pase != null) {
        comando = new CommandHitBall(jugador.getId(), pase.getPosicionPase(),
                                     TacticasUtil.getPotenciaPase(jugador.getPosicion(),
                                                                  pase.getPosicionPase()),
                                     TacticasUtil.getAlturaPase(jugador.getPosicion(),
                                                                pase.getPosicionPase()));
      } else {
        comando =
            new CommandHitBall(jugador.getId(), jugadores[10].getPosicion().movePosition(0, 10), 1,
                               39);
      }


    } else if (posRecuperacion != null) {

      comando = new CommandMoveTo(jugador.getId(), posRecuperacion);

    } else if (jugador.getRivalMarcado() != null) {
      Position corte = TacticasUtil.calcularCortePaseMarca(jugador, sp);

      if (corte == null) {
        comando =
            new CommandMoveTo(jugador.getId(),
                              rivales[jugador.getRivalMarcado().getId()].getPosicion());
      } else {
        comando = new CommandMoveTo(jugador.getId(), corte);
      }
    }

    return comando;
  }

  public static Command moverCentrocampo(Jugador jugador, Position posicionBalon,
                                         Jugador[] jugadores,
                                         Rival[] rivales, GameSituations sp, Position posicion) {
    // TODO Auto-generated method stub
    Command comando = new CommandMoveTo(jugador.getId(), posicion);
    Position posRecuperacion = TacticasUtil.puedoRecuperar(jugador, rivales, sp);
    if (TacticasUtil.tengoBalon(jugador.getId(), sp.canKick())) {

//			Pase pase = TacticasUtil.calcularPosicionPase(jugador, jugadores, rivales, sp);
//			
//			if(pase!=null){
//				comando = new CommandHitBall(jugador.getId(), pase.getPosicionPase(), 
//        				TacticasUtil.getPotenciaPase(jugador.getPosicion(), 
//        						pase.getPosicionPase()), 10);
//			}else{
//				comando = new CommandHitBall(jugador.getId(),jugadores[10].getPosicion().movePosition(0, 20),
//						TacticasUtil.getPotenciaPase(jugador.getPosicion(), jugadores[10].getPosicion().movePosition(0, 20)), 30);
//			}
      comando = calcularComandoConBalon(jugador, jugadores, rivales, sp);

    } else if (posRecuperacion != null) {

      comando = new CommandMoveTo(jugador.getId(), posRecuperacion);

    } else if (jugador.getRivalMarcado() != null) {

      Position corte = TacticasUtil.calcularCortePaseMarca(jugador, sp);

      if (corte == null) {
        comando =
            new CommandMoveTo(jugador.getId(),
                              rivales[jugador.getRivalMarcado().getId()].getPosicion());
      } else {
        comando = new CommandMoveTo(jugador.getId(), corte);
      }
    }

    return comando;
  }

  public static Command moverDelantera(Jugador jugador, Position posicionBalon, Jugador[] jugadores,
                                       Rival[] rivales, GameSituations sp, Position posicion) {
    // TODO Auto-generated method stub
    Command comando = new CommandMoveTo(jugador.getId(), jugador.getPosicion());
    Position posRecuperacion = TacticasUtil.puedoRecuperar(jugador, rivales, sp);
    if (TacticasUtil.tengoBalon(jugador.getId(), sp.canKick())) {

      comando = calcularComandoConBalon(jugador, jugadores, rivales, sp);


    } else if (posRecuperacion != null) {

      comando = new CommandMoveTo(jugador.getId(), posRecuperacion);

    } else {
      comando = new CommandMoveTo(jugador.getId(), posicion);
    }

    return comando;
  }


  public static Command calcularComandoConBalon(Jugador jugador, Jugador[] jugadores,
                                                Rival[] rivales, GameSituations sp) {
    Pase pase = TacticasUtil.calcularPosicionPase(jugador, jugadores, rivales, sp);
    Position puedoConducir = TacticasUtil.puedoConducir(jugador, rivales);
    CommandHitBall tiro = TacticasUtil.calcularTiro(jugador, jugadores, rivales, sp);

    if (tiro != null) {

      return tiro;

    } else if (jugador.getRivalesZona() == null || (jugador.getRivalesZona() != null
                                                    && jugador.getRivalesZona().length == 0)) {

      return new CommandHitBall(jugador.getId(), Constants.centroArcoSup, 0.38, false);

    } else if (puedoConducir != null) {
      return new CommandHitBall(jugador.getId(), puedoConducir, 0.38, false);
    } else if (pase != null) {
      return new CommandHitBall(jugador.getId(), pase.getPosicionPase(),
                                TacticasUtil.getPotenciaPase(jugador.getPosicion(),
                                                             pase.getPosicionPase()),
                                TacticasUtil.getAlturaPase(jugador.getPosicion(),
                                                           pase.getPosicionPase()));
    } else {
      Position pos = TacticasUtil.posicionJugadorMasAdelantado(jugador, jugadores);
      return new CommandHitBall(jugador.getId(), pos.movePosition(0, 0),
                                TacticasUtil.getPotenciaPase(jugador.getPosicion(),
                                                             pos),
                                TacticasUtil.getAlturaPase(jugador.getPosicion(), pos));
    }
  }


  public static double getLineaDefensaActual() {
    return lineaDefensaActual;
  }

  public static void setLineaDefensaActual(double linea) {
    lineaDefensaActual = linea;
  }

  public static double getLineaCentroActual() {
    return lineaCentroActual;
  }

  public static void setLineaCentroActual(double linea) {
    lineaCentroActual = linea;
  }

  public static double getLineaDelanteraActual() {
    return lineaDelanteraActual;
  }

  public static void setLineaDelanteraActual(double linea) {
    lineaDelanteraActual = linea;
  }

  private static void calcularLineaDefensa(Rival[] rivales, Position posicionBalon,
                                           Jugador[] jugadores) {

    Position masAdelantado = TacticasUtil.posicionRivalMasAdelantado(rivales);

    if (getLineaDefensaActual() == -1000) {
      setLineaDefensaActual(LINEA_DEFENSA_BASE);
    }

    if (!posicionBalon.isInsideGameField(0)) {
      setLineaDefensaActual(LINEA_DEFENSA_BASE);
    } else if (masAdelantado.getY() <= getLineaDefensaActual()) {

//			if(masAdelantado.getY()< LINEA_DEFENSA_TOPE){
//				setLineaDefensaActual(LINEA_DEFENSA_TOPE);
//			}else{
      setLineaDefensaActual(masAdelantado.getY() - 1);
//			}
    } else {
      if (masAdelantado.getY() < LINEA_CENTROCAMPO_BASE) {
        setLineaDefensaActual(masAdelantado.getY() - 1);
      }
//			else{
//				setLineaDefensaActual(LINEA_DEFENSA_BASE);
//			}

    }
  }

  private static void calcularLineaCentrocampo(Rival[] rivales, Position posicionBalon,
                                               Jugador[] jugadores) {

    if (getLineaCentroActual() == -1000) {
      setLineaCentroActual(LINEA_CENTROCAMPO_BASE);
    }

    if (!posicionBalon.isInsideGameField(0)) {
      setLineaCentroActual(LINEA_CENTROCAMPO_BASE);
    } else if (posicionBalon.getY() < getLineaCentroActual()
               && posicionBalon.getY() > getLineaDefensaActual()) {
      setLineaCentroActual(posicionBalon.getY() - 1);
    } else if (posicionBalon.getY() < getLineaCentroActual()
               && posicionBalon.getY() < getLineaDefensaActual()) {
      setLineaCentroActual(getLineaDefensaActual() + 15);
    } else if (posicionBalon.getY() > Constants.centroCampoJuego.getY()) {
      setLineaCentroActual(Constants.centroCampoJuego.getY());
    } else {
      setLineaCentroActual(posicionBalon.getY() - 1);
    }
//		else{
//			setLineaCentroActual(LINEA_CENTROCAMPO_BASE);
//		}

  }

  private static void calcularLineaDelantera(Rival[] rivales, Position posicionBalon,
                                             Jugador[] jugadores) {

    if (getLineaDelanteraActual() == -1000) {
      setLineaDelanteraActual(LINEA_DELANTERA_BASE);
    }

    if (posicionBalon.getY() < getLineaDelanteraActual()
        && getLineaCentroActual() + 30 <= getLineaDelanteraActual()) {
      setLineaDelanteraActual(getLineaDelanteraActual() - 5);
    } else if (posicionBalon.getY() > getLineaDelanteraActual()) {
      setLineaDelanteraActual(posicionBalon.getY());
    } else {
      setLineaDelanteraActual(LINEA_DELANTERA_BASE);
    }
  }

  public static double[] calcularLineasHorizontales(Rival[] rivales, Position posicionBalon,
                                                    Jugador[] jugadores) {

    calcularLineaDefensa(rivales, posicionBalon, jugadores);
    calcularLineaCentrocampo(rivales, posicionBalon, jugadores);
    calcularLineaDelantera(rivales, posicionBalon, jugadores);

    return new double[]{getLineaDefensaActual(), getLineaCentroActual(), getLineaDelanteraActual()};
  }


}
