/*
    This file is part of "LivingTeam" for JavaCup 2010 (http://javacup.javahispano.org)
    Copyright (C) 2010  Angel Martinez-Cavero & Miguel-Angel Llorente-Carmona

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

// Package
package org.dsaw.javacup.tactics.jvc2013.LivingLabTeam;

// Imports

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerI;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Clase principal con las alineaciones del equipo
 */
public class LivingLabTeam implements Tactic {

  /**
   * Atributos
   */
  // Lista de comandos a ejecutar por el equipo en cada iteration
  LinkedList<Command> comandos = new LinkedList<>();

  // Coordenadas del ballPosition en caso de ser recuperable
  double[] coordenadasBalonRecuperable = new double[3];

  // Instancia un generador aleatorio
  Random r = new Random();

  // Numero de jugadores rivalPlayers ubicados en mi campo y en su campo
  int jugadoresRivalesCampoPropio = 0;
  int jugadoresRivalesCampoAjeno = 0;

  // Array con los dorsales de jugadores a cubrir que estan en nuestro campo
  int jugadoresRivalesPeligrosos[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

  // Distancia maxima para considerar que un jugador este libre de marca
  double distanciaCobertura = 2.5;

  // Posibilidad de recuperar el ballPosition en esta iteration
  boolean balonRecuperable = false;

  // Distancia minima de tiro
  int distanciaTiro = 20;

  // Margen de seguridad para decidir si el pase debe hacerse entre lineas
  double margenSeguridadPase = 3;

  // Determina inactividad de los delanteros
  int iteracionBalonJugadoDelanteros = 0;
  int limiteDelanterosSinJugar = 20;
  boolean chutarGol = false;

  // Alineacion normal
  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-20.447552447552447, -24.943438914027148),
      new Position(-0.951048951048951, -32.30769230769231),
      new Position(18.78321678321678, -23.755656108597286),
      new Position(4.5174825174825175, -6.414027149321266),
      new Position(-11.65034965034965, -5.463800904977376),
      new Position(-26.867132867132867, 6.889140271493213),
      new Position(22.825174825174827, 7.364253393665159),
      new Position(-7.608391608391608, 38.95927601809955),
      new Position(-2.6153846153846154, 19.717194570135746),
      new Position(7.132867132867133, 37.77149321266968)
  };

  // Alineacion inicio recibiendo
  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-9.510489510489512, -35.15837104072398),
      new Position(3.3286713286713288, -35.8710407239819),
      new Position(17.594405594405593, -32.07013574660634),
      new Position(-23.538461538461537, -31.83257918552036),
      new Position(10.461538461538462, -18.054298642533936),
      new Position(-15.216783216783217, -17.816742081447966),
      new Position(-23.538461538461537, -3.088235294117647),
      new Position(5.230769230769231, -9.264705882352942),
      new Position(-5.706293706293707, -9.027149321266968),
      new Position(23.062937062937063, -3.5633484162895925)
  };

  // Alineacion inicio sacando
  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -35.78358208955224),
      new Position(12.717557251908397, -35.26119402985075),
      new Position(28.290076335877863, -28.470149253731343),
      new Position(-28.290076335877863, -28.470149253731343),
      new Position(7.846153846153847, -17.34162895927602),
      new Position(-9.272727272727272, -16.866515837104075),
      new Position(-23.618320610687025, -0.7835820895522387),
      new Position(3.3286713286713288, -4.276018099547511),
      new Position(0.2595419847328244, -0.26119402985074625),
      new Position(22.580152671755727, -1.3059701492537314)
  };

  // Alineacion kamikaze (al ataque)
  Position alineacion4[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-21.874125874125873, -13.065610859728507),
      new Position(-2.13986013986014, -23.042986425339365),
      new Position(20.20979020979021, -13.303167420814479),
      new Position(4.27972027972028, 4.038461538461538),
      new Position(-13.076923076923078, 4.276018099547511),
      new Position(-25.44055944055944, 22.330316742081447),
      new Position(24.48951048951049, 23.042986425339365),
      new Position(-8.55944055944056, 40.859728506787334),
      new Position(-1.188811188811189, 28.50678733031674),
      new Position(4.9930069930069925, 40.62217194570136)
  };

  // Alineacion defensiva
  Position alineacion5[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-19.25874125874126, -33.49547511312217),
      new Position(-2.377622377622378, -36.10859728506787),
      new Position(18.78321678321678, -33.970588235294116),
      new Position(7.846153846153847, -19.479638009049776),
      new Position(-13.076923076923078, -19.95475113122172),
      new Position(-19.496503496503497, 17.57918552036199),
      new Position(19.020979020979023, 18.766968325791854),
      new Position(-14.74125874125874, 0.47511312217194573),
      new Position(8.797202797202797, 0.23755656108597287),
      new Position(-0.4755244755244755, 39.19683257918552)
  };

  // Alineacion en caso de que el equipo rival este metido en su campo
  Position alineacion6[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-19.734265734265733, 3.5633484162895925),
      new Position(-2.377622377622378, -6.414027149321266),
      new Position(13.552447552447552, 4.038461538461538),
      new Position(0.23776223776223776, 18.529411764705884),
      new Position(-9.986013986013985, 19.479638009049776),
      new Position(-22.825174825174827, 30.88235294117647),
      new Position(13.314685314685315, 29.93212669683258),
      new Position(-8.083916083916083, 42.522624434389144),
      new Position(-1.6643356643356644, 38.24660633484163),
      new Position(4.27972027972028, 42.997737556561084)
  };

  // Alineacion en caso de que el equipo rival este metido en mi campo
  Position alineacion7[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-21.874125874125873, -30.407239819004527),
      new Position(-8.797202797202797, -36.34615384615385),
      new Position(10.937062937062937, -36.10859728506787),
      new Position(23.538461538461537, -28.269230769230766),
      new Position(-0.23776223776223776, -35.39592760180996),
      new Position(-17.11888111888112, -9.739819004524888),
      new Position(16.88111888111888, -6.651583710407239),
      new Position(-0.23776223776223776, 20.667420814479637),
      new Position(0.4755244755244755, -4.038461538461538),
      new Position(-0.23776223776223776, 41.57239819004525)
  };

  /**
   * Caracteristicas del equipo
   */
  class TacticaDetalleImpl implements Team {

    @Override
    public String getName() {
      return "LivingTeam";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.ES;
    }

    @Override
    public String getCoachName() {
      return "Nick Platino";
    }

    @Override
    public Color getShirtColor() {
      return new Color(0, 102, 102);
    }

    @Override
    public Color getShortsColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(0, 102, 102);
    }

    @Override
    public Color getSocksColor() {
      return new Color(0, 102, 102);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 102, 102);
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
      return new Color(0, 102, 102);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(30, 151, 212);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(0, 102, 102);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
    }

    /**
     * Caracteristicas de los jugadores
     */
    class JugadorImpl implements PlayerI {

      String nombre;
      int numero;
      Color piel, pelo;
      double velocidad, remate, presicion;
      boolean portero;
      Position posicion;

      public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                         double velocidad, double remate, double presicion, boolean portero) {
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
      public String getName() {
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
    public PlayerI[] getPlayers() {
      return new PlayerI[]{
          new JugadorImpl("jplazaro", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, true),
          new JugadorImpl("mllorente", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.66d, 0.5d, false),
          new JugadorImpl("anmarca", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.34d, false),
          new JugadorImpl("alfiva", 4, new Color(255, 200, 150), new Color(50, 0, 0), 0.72d, 0.73d,
                          0.38d, false),
          new JugadorImpl("aaracil", 5, new Color(255, 200, 150), new Color(50, 0, 0), 0.72d, 0.72d,
                          0.36d, false),
          new JugadorImpl("vribes", 6, new Color(255, 200, 150), new Color(50, 0, 0), 0.5d, 0.34d,
                          0.79d, false),
          new JugadorImpl("serdula", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.94d, false),
          new JugadorImpl("johercu", 8, new Color(255, 200, 150), new Color(50, 0, 0), 0.98d, 1.0d,
                          0.82d, false),
          new JugadorImpl("jobabluc", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("fraperod", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("ccacho", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false)
      };
    }

  }

  /**
   * Metodo a implementar con carcateristicas de la tactica del equipo
   */
  Team detalle = new TacticaDetalleImpl();

  @Override
  public Team getDetail() {
    return detalle;
  }

  /**
   * Metodo a implementar con la ubicacion de los jugadores cuando reciben un gol
   */
  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return alineacion3;
  }

  /**
   * Metodo a implementar con la ubicacion de los jugadores cuando marcan un gol
   */
  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
  }

  /**
   * Metodo a implementar con la estrategia a seguir del equipo en cada iteration
   */
  @Override
  public List<Command> execute(GameSituations sp) {
    /** Variables locales */

    // Obtiene las posiciones de nuestros jugadores
    Position[] jugadores = sp.myPlayers();

    // Obtiene las posiciones de los jugadores rivalPlayers
    Position[] jugadoresRivales = sp.rivalPlayers();

    // Limpia la lista de comandos previos ejecutados
    comandos.clear();

    // Reset de las variables
    resetVariables();

    // Cuento el numero de jugadores rivalPlayers y anoto el dorsal de cada uno de los que estan en nuestro
    // campo para marcarlos de forma individual
    detectarJugadoresRivalesPeligrosos(jugadoresRivales);

    // Determinamos los jugadores que nunca podran salir del campo
    setPositionJugadores(jugadores);

    // Distribucion de nuestros jugadores
    distribuirJugadores(sp, jugadores, jugadoresRivales);

    // Definir distancia de tiro
    setDistanciaMinimaTiro(sp);

    // Determinamos si es posible recuperar el ballPosition en esta interaccion y en caso de que haya un jugador con elevada
    // probabilidad de recuperacion le ordenamos que se mueva
    int[] posibilidadRecuperacion = sp.getRecoveryBall();
    if (posibilidadRecuperacion.length > 1) {
      balonRecuperable = true;
      // Obtiene las coordenadas (X,Y,Z) del ballPosition en el instante en el que se puede recuperar
      coordenadasBalonRecuperable = sp.getTrajectory(posibilidadRecuperacion[0]);
    }
    // El ballPosition se encuentra en movimiento
    if ((!sp.isRivalStarts()) && (!sp.isStarts())) {
      if (balonRecuperable) {
        // De los jugadores con opciones de recuperar el ballPosition solo modifico la posicion de los que tienen mas
        // probabilidad
        if (posibilidadRecuperacion.length == 2) {
          comandos.add(new CommandMoveTo(posibilidadRecuperacion[1],
                                         new Position(coordenadasBalonRecuperable[0],
                                                      coordenadasBalonRecuperable[1])));
        } else {
          comandos.add(new CommandMoveTo(posibilidadRecuperacion[1],
                                         new Position(coordenadasBalonRecuperable[0],
                                                      coordenadasBalonRecuperable[1])));
          comandos.add(new CommandMoveTo(posibilidadRecuperacion[2],
                                         new Position(coordenadasBalonRecuperable[0],
                                                      coordenadasBalonRecuperable[1])));
        }
      }
    } else if (sp.isStarts()) {
      // Determina que hacer cuando realizo un saque con el tiempo parado
      if (balonRecuperable) {
        int jugadorDestino;
        // De los jugadores con opciones de recuperar el ballPosition solo modifico la posicion de los que tienen mas
        // probabilidad
        comandos.add(new CommandMoveTo(posibilidadRecuperacion[1],
                                       new Position(coordenadasBalonRecuperable[0],
                                                    coordenadasBalonRecuperable[1])));
        jugadorDestino =
            buscarJugadorDesmarcado(sp, true, posibilidadRecuperacion[1], jugadores,
                                    jugadoresRivales);
        comandos.add(new CommandHitBall(posibilidadRecuperacion[1],
                                        setPaseEntreLineas(jugadorDestino,
                                                           jugadores[posibilidadRecuperacion[1]],
                                                           jugadores[jugadorDestino],
                                                           jugadoresRivales),
                                        setFuerzaRemate(jugadores[posibilidadRecuperacion[1]],
                                                        jugadores[jugadorDestino]), false));
      }
    }

    // Recorre la lista de mis jugadores que pueden rematar
    setRematadores(sp, jugadores, jugadoresRivales);

    //Retorna la lista de comandos
    return comandos;
  }

  /**
   * Calcula el numero de jugadores rivalPlayers que estan en nuestro campo para asignarles una
   * marca
   */
  public void detectarJugadoresRivalesPeligrosos(Position[] rivales) {
    for (int i = 0; i < rivales.length; i++) {
      if (rivales[i].getY() > 0) {
        // Numero de jugadores rivalPlayers que estan en su campo
        jugadoresRivalesCampoPropio++;
      } else {
        // Numero de jugadores rivalPlayers que estan en nuestro campo
        jugadoresRivalesCampoAjeno++;
        // Dorsales de los jugadores para cubrir
        jugadoresRivalesPeligrosos[i] = 1;
      }
    }
  }

  /**
   * Especifica que jugadores no podran salir bajo ningun concepto del terreno de juego
   */
  public void setPositionJugadores(Position[] propios) {
    for (int i = 0; i < propios.length; i++) {
      if ((i != 0)) {
        propios[i].setInsideGameField();
      }
    }
  }

  /**
   * Ajusta la minima distancia de tiro a partir de la cual los jugadores tiran a gol
   */
  public void setDistanciaMinimaTiro(GameSituations sp) {
    if (sp.myGoals() == sp.rivalGoals()) {
      distanciaTiro = 30;
    } else if (sp.myGoals() > sp.rivalGoals()) {
      distanciaTiro = 25;
    } else if ((sp.myGoals() < sp.rivalGoals())
               && ((sp.iteration() * 100) / (Constants.ITERACIONES) <= 50)) {
      distanciaTiro = 35;
    } else if ((sp.myGoals() < sp.rivalGoals())
               && ((sp.iteration() * 100) / (Constants.ITERACIONES) >= 60)) {
      distanciaTiro = 50;
    }
  }

  /**
   * Distribuye a nuestros jugadores en funcion del marcador, del tiempo que quede para terminar el
   * partido y la distribucion de los jugadores rivalPlayers
   */
  public void distribuirJugadores(GameSituations sp, Position[] propios, Position[] rivales) {
    for (int i = 0; i < propios.length; i++) {
      if (i == 0) {
        // Su ubicacion es siempre la misma (en la porteria)
        comandos.add(new CommandMoveTo(i, alineacion1[i]));
      } else {
        switch (jugadoresRivalesCampoAjeno) {
          case 0:
            // Todos los jugadores rivalPlayers estan en su campo
            comandos.add(new CommandMoveTo(i, alineacion6[i]));
            break;
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
            for (int j = jugadoresRivalesPeligrosos.length - 1; j > 0; j--) {
              if (jugadoresRivalesPeligrosos[j] == 1) {
                comandos.add(new CommandMoveTo(i, new Position(rivales[j].getX() + 0.7,
                                                               rivales[j].getY() + 0.7)));
                jugadoresRivalesPeligrosos[j] = 0;
                break;
              } else {
                if ((sp.myGoals() >= sp.rivalGoals()) && (
                    (sp.iteration() * 100) / (Constants.ITERACIONES) <= 75)) {
                  // Estamos empatando o ganando
                  if (i == 10) {
                    comandos.add(new CommandMoveTo(i, new Position(Constants.penalSup.getX() + 2.0,
                                                                   Constants.penalSup.getY())));
                  } else {
                    comandos.add(new CommandMoveTo(i, alineacion1[i]));
                  }
                } else if (sp.myGoals() < sp.rivalGoals()) {
                  // Estamos perdiendo
                  if (i == 10) {
                    comandos.add(new CommandMoveTo(i, new Position(Constants.penalSup.getX() + 2.0,
                                                                   Constants.penalSup.getY())));
                  } else {
                    comandos.add(new CommandMoveTo(i, alineacion4[i]));
                  }
                } else if ((sp.myGoals() > sp.rivalGoals()) && (
                    (sp.iteration() * 100) / (Constants.ITERACIONES) > 75)) {
                  // Estamos ganando
                  if (i == 10) {
                    chutarGol = true;
                    comandos.add(new CommandMoveTo(i, new Position(-0.23776223776223776,
                                                                   32.782805429864254)));
                  } else {
                    comandos.add(new CommandMoveTo(i, alineacion5[i]));
                  }
                }
              }
            }
            break;
          case 8:
          case 9:
          case 10:
          case 11:
            // La mayor parte de jugadores rivalPlayers (o todos) estan en mi campo
            for (int j = jugadoresRivalesPeligrosos.length - 1; j > 0; j--) {
              if (jugadoresRivalesPeligrosos[j] == 1) {
                if ((i == 8) || (i == 9) || (i == 10)) {
                  comandos.add(new CommandMoveTo(i, alineacion7[i]));
                } else {
                  comandos.add(new CommandMoveTo(i, new Position(rivales[j].getX() + 0.7,
                                                                 rivales[j].getY() + 0.7)));
                  jugadoresRivalesPeligrosos[j] = 0;
                  break;
                }
              }
            }
            break;
        }
      }
    }
  }

  /**
   * Devuelve un entero con el dorsal del jugador que se encuentra en las mejores condiciones para
   * recibir el ballPosition
   */
  public int buscarJugadorDesmarcado(GameSituations sp, boolean salirJugando, int limiteInferior,
                                     Position[] propios, Position[] rivales) {
    int retorno = -1;
    int retorno_temp = -1;
    int[] indicesJugadoresPropiosProximos = sp.ballPosition().nearestIndexes(propios);
    if (!salirJugando) {
      // Busco al jugador mas alejado para evitar el peligro
      retorno = indicesJugadoresPropiosProximos[indicesJugadoresPropiosProximos.length - 1];
      retorno_temp = -1;
    } else {
      // Busco al jugador mas proximo para salir jugando
      for (int i = 1; i < indicesJugadoresPropiosProximos.length; i++) {
        if (!jugadorMarcado(indicesJugadoresPropiosProximos[i], propios, rivales)) {
          if (propios[indicesJugadoresPropiosProximos[i]].distance(Constants.centroArcoSup)
              < (propios[indicesJugadoresPropiosProximos[0]].distance(Constants.centroArcoSup))) {
            retorno = indicesJugadoresPropiosProximos[i];
            break;
          } else {
            retorno_temp = indicesJugadoresPropiosProximos[i];
            continue;
          }
        }
      }
    }
    // En caso de que todos esten marcados devuelvo un numero aleatorio
    if (retorno == -1) {
      return 1 + r.nextInt(10);
    } else {
      if (retorno_temp == -1) {
        return retorno;
      } else {
        if (propios[retorno].distance(Constants.centroArcoSup) < (propios[retorno_temp].distance(
            Constants.centroArcoSup))) {
          return retorno;
        } else {
          return retorno_temp;
        }
      }
    }
  }

  /**
   * Devuelve true en caso de que el jugador se encuentre marcado false en caso contrario
   */
  public boolean jugadorMarcado(int jugador, Position[] propios, Position[] rivales) {
    boolean retorno = false;
    for (int i = 0; i < rivales.length; i++) {
      if (propios[jugador].distance(rivales[i]) < distanciaCobertura) {
        retorno = true;
        break;
      }
    }
    return retorno;
  }

  /**
   * Devuelve true en caso de que el portero se encuentre fuera del area grande
   */
  public boolean porteroRivalFueraAreaGrande(Position porteroRival) {
    boolean retorno = false;
    if ((porteroRival.distance(Constants.centroArcoSup) > Constants.ANCHO_AREA_GRANDE) || (
        porteroRival.getX() > Constants.LARGO_AREA_GRANDE)) {
      retorno = true;
    }
    return retorno;
  }

  /**
   * Devuelve true en caso de que el portero se encuentre fuera del area peque�a
   */
  public boolean porteroRivalFueraAreaPequena(Position porteroRival) {
    boolean retorno = false;
    if ((porteroRival.distance(Constants.centroArcoSup) > Constants.ANCHO_AREA_CHICA) || (
        porteroRival.getX() > Constants.LARGO_AREA_CHICA)) {
      retorno = true;
    }
    return retorno;
  }

  /**
   * Devuelve una posicion a la que no podra llegar el portero
   */
  public Tiro setDireccionDisparo(int golpeador, GameSituations sp) {
    double miDistanciaAPuerta = sp.myPlayers()[golpeador].distance(Constants.centroArcoSup);
    double miPosicionY = 52.5 - miDistanciaAPuerta;
    double miPosicionX = sp.myPlayers()[golpeador].getX();

    Tiro retorno;
    retorno = new Tiro();
    Random r = new Random();
    Position auxpos0 = new Position();
    auxpos0.setPosition(Constants.centroArcoSup.getX() + (Math.pow(-1, r.nextInt(3)) * 1.2d),
                        Constants.centroArcoSup.getY());
    retorno.destino = auxpos0;

    if (miPosicionY < 0) {
      retorno.fuerza = 1;
      retorno.anguloZ = 60;
    } else if (miPosicionY >= 0 && miPosicionY < 4) {
      retorno.fuerza = 1;
      retorno.anguloZ = 55;
    } else if (miPosicionY >= 4 && miPosicionY < 10) {
      retorno.fuerza = 1;
      retorno.anguloZ = 60;
    } else if (miPosicionY >= 10 && miPosicionY < 20) {
      retorno.fuerza = 0.8;
      retorno.anguloZ = 45;
    } else if (miPosicionY >= 20 && miPosicionY < 25) {
      retorno.fuerza = 0.75;
      retorno.anguloZ = 45;
    } else if (miPosicionY >= 25 && miPosicionY < 27.5) {
      retorno.fuerza = 0.75;
      retorno.anguloZ = 27.6;
    } else if (miPosicionY >= 27.5 && miPosicionY < 30) {
      retorno.fuerza = 0.8;
      retorno.anguloZ = 27.5;
    } else if (miPosicionY >= 30 && miPosicionY < 35) {
      Position auxpos = new Position();
      if (miPosicionX >= 0 && miPosicionX < 5) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.4d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX >= 5 && miPosicionX < 7) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.6d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX >= 7 && miPosicionX < 10) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 1d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX > 10) {
        auxpos.setPosition(Constants.centroArcoSup.getX() + 1.5d, Constants.centroArcoSup.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX < 0 && miPosicionX > -5) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.4d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX <= -5 && miPosicionX > -7) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.6d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX <= -7 && miPosicionX > -10) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 1d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX < -10) {
        auxpos.setPosition(Constants.centroArcoSup.getX() - 1.5d, Constants.centroArcoSup.getY());
        retorno.destino = auxpos;
      }

      retorno.fuerza = 0.85;
      retorno.anguloZ = 18.5;
    } else if (miPosicionY >= 35 && miPosicionY < 40) {
      Position auxpos = new Position();
      if (miPosicionX >= 0 && miPosicionX < 5) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.5d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX >= 5 && miPosicionX < 7) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.7d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX >= 7 && miPosicionX < 10) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 1.2d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX > 10) {
        auxpos.setPosition(Constants.centroArcoSup.getX() + 1.5d, Constants.centroArcoSup.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX < 0 && miPosicionX > -5) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.5d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX <= -5 && miPosicionX > -7) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.7d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX <= -7 && miPosicionX > -10) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 1.2d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX < -10) {
        auxpos.setPosition(Constants.centroArcoSup.getX() - 1.5d, Constants.centroArcoSup.getY());
        retorno.destino = auxpos;
      }
      retorno.fuerza = 0.85;
      retorno.anguloZ = 19;
    } else if (miPosicionY >= 40) {
      Position auxpos = new Position();
      if (miPosicionX >= 0 && miPosicionX < 5) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.5, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX >= 5 && miPosicionX < 7) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.66d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX >= 7 && miPosicionX < 10) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteIzqArcoSup);
        auxpos.setPosition(auxpos.getX() + 0.9d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX > 10) {
        auxpos.setPosition(Constants.centroArcoSup.getX() + 1d, Constants.centroArcoSup.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX < 0 && miPosicionX > -5) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.5, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX <= -5 && miPosicionX > -7) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.66d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX <= -7 && miPosicionX > -10) {
        auxpos = Position.medium(Constants.centroArcoSup, Constants.posteDerArcoSup);
        auxpos.setPosition(auxpos.getX() - 0.9d, auxpos.getY());
        retorno.destino = auxpos;
      } else if (miPosicionX < -10) {
        auxpos.setPosition(Constants.centroArcoSup.getX() - 1d, Constants.centroArcoSup.getY());
        retorno.destino = auxpos;
      }

      if (miPosicionY > 40 && miPosicionY <= 41) {
        retorno.fuerza = 1;
        retorno.anguloZ = 19.4;
      } else if (miPosicionY > 41 && miPosicionY <= 42) {
        retorno.fuerza = 1;
        retorno.anguloZ = 18.8;
      } else if (miPosicionY > 42 && miPosicionY <= 43) {
        retorno.fuerza = 1;
        retorno.anguloZ = 18.3;
      } else {
        retorno.fuerza = 1;
        retorno.anguloZ = 17.9;
      }
    }

    return retorno;
  }

  /**
   * Metodo para ajustar la fuerza del pase en funcion de la distancia entre ambos jugadores
   */
  public double setFuerzaRemate(Position jugadorFuente, Position jugadorDestino) {
    double retorno = 1.0;
    if (jugadorFuente.distance(jugadorDestino) <= 10) {
      retorno = 0.2;
    } else if (jugadorFuente.distance(jugadorDestino) <= 15) {
      retorno = 0.4;
    } else if (jugadorFuente.distance(jugadorDestino) <= 20) {
      retorno = 0.6;
    } else if (jugadorFuente.distance(jugadorDestino) > 25) {
      retorno = 1.0;
    }
    return retorno;
  }

  /**
   * Metodo para determinar si el pase debe hacerse entre lineas o ir directo al jugador
   */
  public Position setPaseEntreLineas(int dorsalJugadorDestino, Position jugadorOrigen,
                                     Position jugadorDestino, Position[] jugadoresRivales) {
    Position retorno = null;
    // Compruebo si hay rivalPlayers en la linea de pase
    for (int i = 0; i < jugadoresRivales.length; i++) {
      if ((jugadoresRivales[i].getX() >= jugadorDestino.getX() - margenSeguridadPase) && (
          jugadoresRivales[i].getX() <= jugadorDestino.getX() + margenSeguridadPase)) {
        // Defino el pase al hueco
        if ((jugadoresRivales[i].distance(
            new Position(jugadorDestino.getX() - margenSeguridadPase, jugadorDestino.getY())))
            < jugadoresRivales[i].distance(
            new Position(jugadorDestino.getX() + margenSeguridadPase, jugadorDestino.getY()))) {
          // Debo hacer el desmarque hacia la derecha (menor probabilidad de perder el ballPosition)
          retorno =
              new Position(jugadorDestino.getX() + margenSeguridadPase, jugadorDestino.getY());
          comandos.add(new CommandMoveTo(dorsalJugadorDestino,
                                         new Position(jugadorDestino.getX() + margenSeguridadPase,
                                                      jugadorDestino.getY())));
        } else {
          // Debo hacer el desmarque hacia la izquierda (menor probabilidad de perder el ballPosition)
          retorno =
              new Position(jugadorDestino.getX() - margenSeguridadPase, jugadorDestino.getY());
          comandos.add(new CommandMoveTo(dorsalJugadorDestino,
                                         new Position(jugadorDestino.getX() - margenSeguridadPase,
                                                      jugadorDestino.getY())));
        }
      }
    }
    // Posicion del pase
    if (retorno == null) {
      return jugadorDestino;
    } else {
      return retorno;
    }
  }

  /**
   * Metodo para definir el disparo
   */
  public void setRematadores(GameSituations sp, Position[] jugadores, Position[] jugadoresRivales) {
    for (int i : sp.canKick()) {
      // Jugador que recibira el pase
      int jugadorDestino;
      if (i == 0) {
        if (((sp.iteration() * 100) / (Constants.ITERACIONES) <= 50) && (sp.myGoals() >= sp
            .rivalGoals())) {
          jugadorDestino = buscarJugadorDesmarcado(sp, true, i, jugadores, jugadoresRivales);
          comandos.add(new CommandHitBall(i, jugadores[jugadorDestino],
                                          setFuerzaRemate(jugadores[i], jugadores[jugadorDestino]),
                                          45));
        } else {
          jugadorDestino = buscarJugadorDesmarcado(sp, false, i, jugadores, jugadoresRivales);
          comandos.add(new CommandHitBall(i, setPaseEntreLineas(jugadorDestino, jugadores[i],
                                                                jugadores[jugadorDestino],
                                                                jugadoresRivales),
                                          setFuerzaRemate(jugadores[i], jugadores[jugadorDestino]),
                                          45));
        }
      } else if ((i == 1) || (i == 2) || (i == 3) || (i == 4) || (i == 5)) {
        // Si no hace mucho tiempo que los delanteros no tocan el ballPosition
        if (sp.iteration() - iteracionBalonJugadoDelanteros >= limiteDelanterosSinJugar) {
          if (r.nextBoolean()) {
            comandos.add(new CommandHitBall(i, jugadores[8], 1, 45));
          } else {
            comandos.add(new CommandHitBall(i, jugadores[10], 1, 45));
          }
        } else {
          if (r.nextBoolean()) {
            jugadorDestino = buscarJugadorDesmarcado(sp, true, i, jugadores, jugadoresRivales);
            comandos.add(new CommandHitBall(i, setPaseEntreLineas(jugadorDestino, jugadores[i],
                                                                  jugadores[jugadorDestino],
                                                                  jugadoresRivales),
                                            setFuerzaRemate(jugadores[i],
                                                            jugadores[jugadorDestino]), false));
          } else {
            jugadorDestino = buscarJugadorDesmarcado(sp, false, i, jugadores, jugadoresRivales);
            comandos.add(new CommandHitBall(i, setPaseEntreLineas(jugadorDestino, jugadores[i],
                                                                  jugadores[jugadorDestino],
                                                                  jugadoresRivales),
                                            setFuerzaRemate(jugadores[i],
                                                            jugadores[jugadorDestino]), false));
          }
        }
      } else if ((i == 6) || (i == 7)) {
        if ((jugadores[i].distance(Constants.centroArcoSup) <= distanciaTiro)) {
          if ((porteroRivalFueraAreaGrande(jugadoresRivales[0])) || (porteroRivalFueraAreaPequena(
              jugadoresRivales[0]))) {
            Tiro t = setDireccionDisparo(i, sp);
            comandos.add(new CommandHitBall(i, Constants.centroArcoSup, t.fuerza, t.anguloZ));
          } else {
            Tiro t = setDireccionDisparo(i, sp);
            comandos.add(new CommandHitBall(i, Constants.centroArcoSup, t.fuerza, t.anguloZ));
          }
        } else {
          // Si no hace mucho tiempo que los delanteros no tocan el ballPosition
          if (sp.iteration() - iteracionBalonJugadoDelanteros >= limiteDelanterosSinJugar) {
            if (r.nextBoolean()) {
              comandos.add(new CommandHitBall(i, jugadores[8], 1, false));
            } else {
              comandos.add(new CommandHitBall(i, jugadores[10], 1, false));
            }
          } else {
            jugadorDestino = buscarJugadorDesmarcado(sp, true, i, jugadores, jugadoresRivales);
            comandos.add(new CommandHitBall(i, setPaseEntreLineas(jugadorDestino, jugadores[i],
                                                                  jugadores[jugadorDestino],
                                                                  jugadoresRivales),
                                            setFuerzaRemate(jugadores[i],
                                                            jugadores[jugadorDestino]), false));
          }
        }
      } else if ((i == 8) || (i == 10)) {
        // Iteracion en la que entran en juego los delanteros (timestamp)
        iteracionBalonJugadoDelanteros = sp.iteration();
        if ((i == 10) && (chutarGol == true)) {
          Tiro t = setDireccionDisparo(i, sp);
          comandos.add(new CommandHitBall(i, t.destino, t.fuerza, t.anguloZ));
        } else {
          if ((jugadores[i].distance(Constants.centroArcoSup) <= distanciaTiro)) {
            if (porteroRivalFueraAreaGrande(jugadoresRivales[0]) || (porteroRivalFueraAreaPequena(
                jugadoresRivales[0]))) {
              Tiro t = setDireccionDisparo(i, sp);
              comandos.add(new CommandHitBall(i, t.destino, t.fuerza, t.anguloZ));
            } else {
              Tiro t = setDireccionDisparo(i, sp);
              comandos.add(new CommandHitBall(i, Constants.centroArcoSup, t.fuerza, t.anguloZ));
            }
          } else {
            jugadorDestino = buscarJugadorDesmarcado(sp, true, i, jugadores, jugadoresRivales);
            comandos.add(new CommandHitBall(i, setPaseEntreLineas(jugadorDestino, jugadores[i],
                                                                  jugadores[jugadorDestino],
                                                                  jugadoresRivales),
                                            setFuerzaRemate(jugadores[i],
                                                            jugadores[jugadorDestino]), false));
          }
        }
      } else if (i == 9) {
        if ((jugadores[i].distance(Constants.centroArcoSup) <= distanciaTiro)) {
          if ((porteroRivalFueraAreaGrande(jugadoresRivales[0])) || (porteroRivalFueraAreaPequena(
              jugadoresRivales[0]))) {
            Tiro t = setDireccionDisparo(i, sp);
            comandos.add(new CommandHitBall(i, t.destino, t.fuerza, t.anguloZ));
          } else {
            Tiro t = setDireccionDisparo(i, sp);
            comandos.add(new CommandHitBall(i, t.destino, t.fuerza, t.anguloZ));
          }
        } else {
          // Si no hace mucho tiempo que los delanteros no tocan el ballPosition
          if (sp.iteration() - iteracionBalonJugadoDelanteros >= limiteDelanterosSinJugar) {
            if (r.nextBoolean()) {
              comandos.add(new CommandHitBall(i, jugadores[8], 1, false));
            } else {
              comandos.add(new CommandHitBall(i, jugadores[10], 1, false));
            }
          } else {
            jugadorDestino = buscarJugadorDesmarcado(sp, true, i, jugadores, jugadoresRivales);
            comandos.add(new CommandHitBall(i, setPaseEntreLineas(jugadorDestino, jugadores[i],
                                                                  jugadores[jugadorDestino],
                                                                  jugadoresRivales),
                                            setFuerzaRemate(jugadores[i],
                                                            jugadores[jugadorDestino]), false));
          }
        }
      }
    }
  }

  /** */
  public void buscarDesmarque(GameSituations sp, Position[] jugadores) {
    for (int i = 8; i < jugadores.length; i++) {
      if ((i == 8) || (i == 10)) {
        if ((jugadores[i].distance(Constants.centroArcoSup) <= distanciaTiro)) {
          comandos.add(new CommandMoveTo(i, new Position(jugadores[i].getX(),
                                                         jugadores[i].getY() + r.nextInt(2))));
        } else {
          comandos.add(new CommandMoveTo(i, new Position(jugadores[i].getX(),
                                                         jugadores[i].getY() - r.nextInt(2))));
        }
      }
    }
  }

  /**
   * Reset de todas las variables
   */
  public void resetVariables() {
    jugadoresRivalesCampoAjeno = 0;
    jugadoresRivalesCampoPropio = 0;
    balonRecuperable = false;
  }
}

class Tiro {

  Position destino;
  double fuerza;
  double anguloZ;

  public Tiro() {

  }

  public Tiro(Position d, double F, double z) {

    destino = d;
    fuerza = F;
    anguloZ = z;
  }

}
