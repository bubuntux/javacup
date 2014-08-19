package org.dsaw.javacup.tactics.jvc2013.Orange;

public class Orange {

}/*implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(25.44055944055944, -27.556561085972852),
      new Position(-27.81818181818182, -27.31900452488688),
      new Position(9.272727272727272, -31.119909502262445),
      new Position(-10.461538461538462, -30.407239819004527),
      new Position(0.7132867132867133, -8.552036199095022),
      new Position(29.95804195804196, -4.276018099547511),
      new Position(-30.195804195804197, -3.800904977375566),
      new Position(-0.4755244755244755, 26.131221719457013),
      new Position(31.622377622377623, 21.855203619909503),
      new Position(-31.146853146853147, 21.380090497737555)
  };
  Position alineacion2[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.16030534351145, -31.082089552238806),
      new Position(11.16030534351145, -31.6044776119403),
      new Position(27.251908396946565, -27.94776119402985),
      new Position(-29.84732824427481, -26.902985074626866),
      new Position(6.181818181818182, -18.054298642533936),
      new Position(-9.034965034965035, -19.95475113122172),
      new Position(30.67132867132867, -2.6131221719457014),
      new Position(-1.6643356643356644, -0.47511312217194573),
      new Position(1.902097902097902, -1.900452488687783),
      new Position(-31.384615384615387, -1.6628959276018098)
  };
  Position alineacion3[] = new Position[]{
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(28.76923076923077, -30.88235294117647),
      new Position(-30.195804195804197, -29.694570135746606),
      new Position(-9.034965034965035, -30.6447963800905),
      new Position(9.986013986013985, -31.357466063348415),
      new Position(-4.755244755244756, -14.490950226244346),
      new Position(5.230769230769231, -14.015837104072398),
      new Position(-13.79020979020979, -4.276018099547511),
      new Position(11.888111888111888, -4.513574660633484),
      new Position(30.195804195804197, -3.5633484162895925),
      new Position(-31.622377622377623, -3.800904977375566)
  };

  class TacticaDetalleImpl implements ITeam {

    @Override
    public String getName() {
      return "Orange";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.NL;
    }

    @Override
    public String getCoachName() {
      return "Maca";
    }

    @Override
    public Color getShirtColor() {
      return new Color(255, 124, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 0, 255);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.SIN_ESTILO;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(31, 67, 250);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
    }

    @Override
    public IPlayer[] getPlayers() {
      return new IPlayer[]{
          new JugadorImpl("Van Peten", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, true),
          new JugadorImpl("Van a Verle", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.5d, 0.56d, false),
          new JugadorImpl("Van Tigre", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d,
                          0.6d, false),
          new JugadorImpl("R Puuma", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.79d, false),
          new JugadorImpl("De Blaind", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.65d, 0.58d, false),
          new JugadorImpl("El Ricard", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.83d, 0.71d, false),
          new JugadorImpl("Pouters", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.59d,
                          0.77d, false),
          new JugadorImpl("Ouvermarxx", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.69d, 0.6d, false),
          new JugadorImpl("Van Pasta", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          1.0d, false),
          new JugadorImpl("Bullit", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.88d, false),
          new JugadorImpl("E. Puuma", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d,
                          0.64d, 0.61d, false)
      };
    }
  }

  ITeam detalle = new TacticaDetalleImpl();

  @Override
  public ITeam getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return alineacion2;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion3;
  }

  //Lista de comandos
  LinkedList<Command> comandos = new LinkedList<>();
  List<Integer> availablePlayers; // Players who can be used for the strategy
  List<Integer> dangerousEnemies;
  Position[] enemies;
  Position[] myPlayers;
  boolean enRegate;
  int faseRegate = 1;
  Map<Integer, Position> posicionBalon = new HashMap<>();

  @Override
  public List<Command> execute(GameSituations sp) {
    //Limpia la lista de comandos
    comandos.clear();
    //Actualizar posicion de jugadores
    udpatePositionPlayers(sp.ballPosition(), sp.myPlayers());
    //Cubrir a jugadores potencialmente peligrosos solo cuando yo no isStarts
    if (!sp.isStarts()) {
      cubrirJugadoresPeligrosos(sp);
    }

    //Si no saca el rival
    if (!sp.isRivalStarts()) {
      //Obtiene los datos de recuperacion del ballPosition
      int[] recuperadores = sp.getRecoveryBall();
      //Si existe posibilidad de recuperar el ballPosition
      if (recuperadores.length > 1) {
        //Obtiene las coordenadas del ballPosition en el instante donde se puede recuperar el ballPosition
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        //Recorre la lista de jugadores que pueden recuperar
        for (int i = 1; i < recuperadores.length; i++) {
          //Ordena a los jugadores recuperadores que se ubique en la posicion de recuperacion
          if (i != 0) {
            comandos.add(new CommandMoveTo(recuperadores[i],
                                           new Position(posRecuperacion[0], posRecuperacion[1])));
          }
        }
      }
    }

    //Recorre la lista de mis jugadores que pueden rematar o pasar en definitiva que pueden hacer algo con el ballPosition
    for (int i : sp.canKick()) {
      //Si el jugador es de indice 0 (portero) que despeje
      if (i != 0) {
        double distancia = distanciaPorteria(sp, true);
        // Si estamos cerca de la porteria
        if (distancia < 22) {
          tirarAPuerta(sp);
        } else {
          dondePasar(sp);
        }


      }
    }

    zonaPortero(sp);
    //Retorna la lista de comandos
    return comandos;
  }

  private void dondePasar(GameSituations sp) {
    boolean[] defensa, medio, delantero;
    defensa = new boolean[alineacion1.length];
    medio = new boolean[alineacion1.length];
    delantero = new boolean[alineacion1.length];

    //DEFINIMOS EN QUE LINEA ESTA CADA JUGADOR
    for (int i = 0; i < alineacion1.length; ++i) {
      defensa[i] = i >= 1 && i <= 4;
      medio[i] = i >= 5 && i <= 7;
      delantero[i] = i >= 8 && i <= 10;
    }
    int llevaBalon[] = sp.canKick();

    //QUIEN LLEVA EL BALON
    if (defensa[llevaBalon[0]]) {
      pasarDesdeDefensa(sp);
    } else if (medio[llevaBalon[0]]) { //UN CENTROCAMPISTA
      pasarDesdeMedios(sp);
    } else { // UN DELANTERO
      pasarDelanteros(sp);
    }

  }

  private void pasarDesdeDefensa(GameSituations sp) {
    Position[] jugadoresPosicion = sp.myPlayers();
    int jugadores[] = sp.getRecoveryBall();

    int llevaBalon[] = sp.canKick();
    //Si son los centrales abrir a las bandas
    if (llevaBalon[0] == 3 || llevaBalon[0] == 4) {
      if (tengoRivalCerca(sp)) {
        comandos.add(new CommandHitBall(llevaBalon[0], Constants.centroArcoSup, 1, 40));
      } else {
        double distanciaAlJugadorDeBanda;
        int jugadorDestino;
        if (jugadores.length > 1) {
          if (bandaIzquierda(sp.ballPosition())) {
            distanciaAlJugadorDeBanda =
                jugadoresPosicion[jugadores[1]].distance(jugadoresPosicion[7]);
            jugadorDestino = 7;
            comandos.add(new CommandMoveTo(jugadorDestino, jugadoresPosicion[llevaBalon[0]]));
            comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPosicion[jugadorDestino],
                                            calcularFuerza(distanciaAlJugadorDeBanda),
                                            calcularAngulo(distanciaAlJugadorDeBanda)));
          } else {
            distanciaAlJugadorDeBanda =
                jugadoresPosicion[jugadores[1]].distance(jugadoresPosicion[6]);
            jugadorDestino = 6;
            comandos.add(new CommandMoveTo(jugadorDestino, jugadoresPosicion[llevaBalon[0]]));
            comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPosicion[jugadorDestino],
                                            calcularFuerza(distanciaAlJugadorDeBanda),
                                            calcularAngulo(distanciaAlJugadorDeBanda)));
          }
        }
      }
    } else {
      if (bandaIzquierda(sp.ballPosition())) {
        comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPosicion[7], 1, 10));
      } else {
        comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPosicion[6], 1, 10));
      }
    }

  }

  private void pasarDesdeMedios(GameSituations sp) {
    Position[] jugadoresPase = sp.myPlayers();
    int llevaBalon[] = sp.canKick();
    int jugadores[] = sp.getRecoveryBall();
    Random r = new Random();
    double distanciaAlJugadorDeBanda;
    int jugadorDestino;
    if (tengoRivalCerca(sp) && !rivalPorDetras(sp)) {
      if (bandaIzquierda(sp.ballPosition())) {
        if (llevaBalon[0] == 5) {
          jugadorDestino = 7;
          distanciaAlJugadorDeBanda =
              jugadoresPase[jugadores[1]].distance(jugadoresPase[jugadorDestino]);
          comandos.add(new CommandMoveTo(7, jugadoresPase[llevaBalon[0]]));
          comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPase[7],
                                          calcularFuerza(distanciaAlJugadorDeBanda),
                                          calcularAngulo(distanciaAlJugadorDeBanda)));
        } else {
          jugadorDestino = 10;
          distanciaAlJugadorDeBanda =
              jugadoresPase[jugadores[1]].distance(jugadoresPase[jugadorDestino]);
          comandos.add(new CommandMoveTo(10, jugadoresPase[llevaBalon[0]]));
          comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPase[10],
                                          calcularFuerza(distanciaAlJugadorDeBanda),
                                          calcularAngulo(distanciaAlJugadorDeBanda)));

        }
      } else {
        if (llevaBalon[0] == 5) {
          jugadorDestino = 6;
          distanciaAlJugadorDeBanda =
              jugadoresPase[jugadores[1]].distance(jugadoresPase[jugadorDestino]);
          comandos.add(new CommandMoveTo(6, jugadoresPase[llevaBalon[0]]));
          comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPase[6],
                                          calcularFuerza(distanciaAlJugadorDeBanda),
                                          calcularAngulo(distanciaAlJugadorDeBanda)));
        } else {
          jugadorDestino = 9;
          distanciaAlJugadorDeBanda =
              jugadoresPase[jugadores[1]].distance(jugadoresPase[jugadorDestino]);
          comandos.add(new CommandMoveTo(9, jugadoresPase[llevaBalon[0]]));
          comandos.add(new CommandHitBall(llevaBalon[0], jugadoresPase[9],
                                          calcularFuerza(distanciaAlJugadorDeBanda),
                                          calcularAngulo(distanciaAlJugadorDeBanda)));
        }
      }
    } else {
      //avanzar a porteria o tirar
      if (jugadoresPase[llevaBalon[0]].distance(Constants.centroArcoSup)
          < Constants.ANCHO_AREA_GRANDE + 5) {
        comandos
            .add(new CommandHitBall(llevaBalon[0], Constants.centroArcoSup, 1, 12 + r.nextInt(5)));
      } else {
        comandos.add(new CommandHitBall(llevaBalon[0],
                                        new Position(jugadoresPase[llevaBalon[0]].getX(),
                                                     Constants.LARGO_CAMPO_JUEGO / 2), 0.4, 0));
      }
    }
  }

  private void pasarDelanteros(GameSituations sp) {
    Position[] jugadoresPase = sp.myPlayers();
    int llevaBalon[] = sp.canKick();

    if (tengoRivalCerca(sp) && !rivalPorDetras(sp)) {
      if (jugadoresPase[llevaBalon[0]].getX() > coordenadasRivalCerca(sp).getX()) {
        comandos.add(new CommandMoveTo(llevaBalon[0],
                                       new Position(jugadoresPase[llevaBalon[0]].getX(),
                                                    Constants.ANCHO_CAMPO_JUEGO)));

      } else {
        comandos.add(new CommandMoveTo(llevaBalon[0],
                                       new Position(jugadoresPase[llevaBalon[0]].getX(),
                                                    -Constants.ANCHO_CAMPO_JUEGO)));
      }
    } else {
      //avanzar a porteria o tirar
      if (jugadoresPase[llevaBalon[0]].distance(Constants.centroArcoSup)
          < Constants.ANCHO_AREA_GRANDE + 5) {
        tirarAPuerta(sp);
      } else {
        if (jugadoresPase[llevaBalon[0]].getY() > Constants.LARGO_CAMPO_JUEGO / 2 - 15) {
          comandos.add(new CommandMoveTo(8, Constants.penalSup));
          comandos.add(new CommandHitBall(llevaBalon[0], new Position(Constants.penalSup.getX(),
                                                                      Constants.penalSup.getY()),
                                          0.9, 15));
        } else {
//		        	if (jugadoresPase[llevaBalon[0]].getY() > Constants.LARGO_CAMPO_JUEGO/2 -20 ){
//		        		comandos.add(new CommandHitBall(llevaBalon[0],Constants.centroArcoSup,0.5,0));
//		        	}else{
          comandos.add(new CommandHitBall(llevaBalon[0],
                                          new Position(jugadoresPase[llevaBalon[0]].getX(),
                                                       jugadoresPase[llevaBalon[0]].getY() + 10),
                                          0.5, 0));
//		        	}
        }
      }
    }

  }

  private boolean bandaIzquierda(Position balon) {
    if (balon.getX() < 0) {
      return true;
    } else {
      return false;
    }

  }

  private boolean tengoRivalCerca(GameSituations sp) {
    int jugadores[] = sp.getRecoveryBall();
    Position posicionJugadores[] = sp.myPlayers();

    Position posicionRivales[] = sp.rivalPlayers();

    if (jugadores.length > 1) {
      if (posicionRivales != null) {
        for (Position i : posicionRivales) {
          double distancia = posicionJugadores[jugadores[1]].distance(i);
          double distanciaPorteriaPropia = distanciaPorteria(sp, false);
          if (distancia < 9 && distanciaPorteriaPropia < 30) {
            comandos.add(new CommandHitBall(jugadores[1], Constants.centroArcoSup, 1, 40));
          } else if (distancia < 9) {
            return true;
          }
        }
      }
    } else {
      return false;
    }
    return false;
  }

  private boolean rivalPorDetras(GameSituations sp) {
    int jugadores[] = sp.getRecoveryBall();
    Position posicionJugadores[] = sp.myPlayers();

    Position posicionRivales[] = sp.rivalPlayers();

    if (jugadores.length > 1) {
      if (posicionRivales != null) {
        for (Position i : posicionRivales) {
          double distancia = posicionJugadores[jugadores[1]].distance(i);
          if (distancia < 4 && posicionJugadores[jugadores[1]].getY() > i.getY()) {
            if (posicionJugadores[jugadores[1]].getY() > i.getY()) {
              return true;
            } else {
              return false;
            }
          }
        }
      }
    } else {
      return false;
    }
    return false;
  }

  private Position coordenadasRivalCerca(GameSituations sp) {
    int jugadores[] = sp.getRecoveryBall();
    Position posicionJugadores[] = sp.myPlayers();
    Position posicionRivales[] = sp.rivalPlayers();
    Position rivalMasCerca = new Position();

    if (jugadores.length > 1) {
      if (posicionRivales != null) {
        for (Position i : posicionRivales) {
          double distancia = posicionJugadores[jugadores[1]].distance(i);
          if (distancia < 8) {
            rivalMasCerca = i;
            return rivalMasCerca;
          }
        }
      }
    } else {
      return rivalMasCerca;
    }
    return rivalMasCerca;
  }

  private void cubrirJugadoresPeligrosos(GameSituations sp) {
    availablePlayers = new ArrayList<>();
    dangerousEnemies = new ArrayList<>();
    myPlayers = new Position[alineacion1.length];
    enemies = new Position[alineacion1.length];

    availablePlayers.clear();
    for (int i = 0; i < 11; ++i) {
      if (i != 1 && i != 2) {
        availablePlayers.add(i);
      }
    }
    Position[] temp = sp.rivalPlayers();
    for (int i = 0; i < temp.length; ++i) {
      enemies[i] = new Position(temp[i]);
    }

    temp = sp.myPlayers();
    for (int i = 0; i < temp.length; ++i) {
      myPlayers[i] = new Position(temp[i]);
    }

    dangerousEnemies.clear();
    Position miCampo = new Position(0, -Constants.LARGO_CAMPO_JUEGO / 3);
    while (enemies.length != dangerousEnemies.size()) {
      double minDist = 1000;
      int minJ = 0;
      for (int j = enemies.length - 1; j >= 0; --j) {
        if (dangerousEnemies.contains(new Integer(j))) {
          continue;
        }
        //Comprobamos que el rival está dentro del terreno de juego
        if (enemies[j].isInsideGameField(0.0)) {
          double dist = miCampo.distance(enemies[j]);
          if (dist < minDist) {
            minDist = dist;
            minJ = j;
          }
        }
      }
      dangerousEnemies.add(minJ);
    }

    for (int j : dangerousEnemies) {
      int iMin = -1;
      double distMin = 0;
      for (int i : availablePlayers) {
        if (i == 1 || i == 2 || i == 3 || i == 4) {
          double dist = enemies[j].distance(myPlayers[i]);
          if (dist < distMin || iMin < 0) {
            iMin = i;
            distMin = dist;
          }
        }
      }
      if (iMin < 0) {
        break;
      }

      Position
          positionUpdated =
          new Position(enemies[j].getX(), enemies[j].getY()).movePosition(0, -1);
      comandos.add(new CommandMoveTo(iMin, positionUpdated));
      availablePlayers.remove(new Integer(iMin));
    }
  }

  private void udpatePositionPlayers(Position balon, Position[] misJugadores) {
    Position positionUpdated[] = new Position[]{};
    //Zona 1
    if (balon.getY() <= -26 && balon.getY() > -52.5) {
      positionUpdated = new Position[]{
          new Position(0.2595419847328244, -50.41044776119403),
          new Position(19.97202797202797, -36.82126696832579),
          new Position(-20.923076923076923, -35.8710407239819),
          new Position(7.608391608391608, -36.34615384615385),
          new Position(-10.937062937062937, -34.920814479638004),
          new Position(0, 0),
          new Position(30.909090909090907, -18.054298642533936),
          new Position(-31.146853146853147, -17.816742081447966),
          new Position(0.23776223776223776, 9.502262443438914),
          new Position(31.146853146853147, 3.3257918552036196),
          new Position(-31.86013986013986, 1.6628959276018098)
      };
    }
    //Zona 2
    if (balon.getY() <= 0 && balon.getY() > -26) {
      positionUpdated = new Position[]{
          new Position(0.2595419847328244, -50.41044776119403),
          new Position(19.97202797202797, -36.82126696832579),
          new Position(-20.923076923076923, -35.8710407239819),
          new Position(7.608391608391608, -36.34615384615385),
          new Position(-10.937062937062937, -34.920814479638004),
          new Position(0, 0),
          new Position(30.909090909090907, -7.601809954751132),
          new Position(-32.0979020979021, -11.64027149321267),
          new Position(-0.4755244755244755, 26.606334841628957),
          new Position(30.909090909090907, 16.131221719457013),
          new Position(-32.81118881118881, 16.391402714932127)
      };
    }
    //Zona 3
    if (balon.getY() > 0 && balon.getY() < 26) {
      positionUpdated = new Position[]{
          new Position(0.2595419847328244, -50.41044776119403),
          new Position(25.44055944055944, -27.556561085972852),
          new Position(-27.81818181818182, -27.31900452488688),
          new Position(9.272727272727272, -31.119909502262445),
          new Position(-10.461538461538462, -30.407239819004527),
          new Position(0, 0),
          new Position(30.433566433566433, 2.6131221719457014),
          new Position(-30.67132867132867, 4.513574660633484),
          new Position(0.0, 22.092760180995477),
          new Position(32.33566433566433, 26.93212669683258),
          new Position(-32.33566433566433, 26.606334841628957)
      };
    }
    //Zona 4
    if (balon.getY() >= 26 && balon.getY() <= 52.5) {
      positionUpdated = new Position[]{
          new Position(0.2595419847328244, -50.41044776119403),
          new Position(25.916083916083913, -13.065610859728507),
          new Position(-27.58041958041958, -14.96606334841629),
          new Position(9.034965034965035, -25.18099547511312),
          new Position(-11.412587412587413, -23.755656108597286),
          new Position(0, 0),
          new Position(29.72027972027972, 18.766968325791854),
          new Position(-32.0979020979021, 20.429864253393667),
          new Position(0.0, 36.82126696832579),
          new Position(10.20979020979021, 38.80995475113122),
          new Position(-10.97202797202797, 38.95927601809955)
      };
    }
    //Ordena a cada jugador que se ubique segun la alineacion actualizada
    if (positionUpdated.length > 0) {
      for (int i = 0; i < misJugadores.length; i++) {
        comandos.add(new CommandMoveTo(i, positionUpdated[i]));
      }
    }
  }

  private void tirarAPuerta(GameSituations sp) {
    Random r = new Random();
    int llevaBalon[] = sp.canKick();
    if (r.nextBoolean()) {
      //Ordena que debe rematar al centro del arco
      comandos.add(new CommandHitBall(llevaBalon[0], Constants.centroArcoSup, 1, 9 + r.nextInt(2)));
    } else if (r.nextBoolean()) {
      //Ordena que debe rematar al poste derecho
      comandos
          .add(new CommandHitBall(llevaBalon[0], Constants.posteDerArcoSup, 1, 9 + r.nextInt(2)));
    } else {
      //Ordena que debe rematar al poste izquierdo
      comandos
          .add(new CommandHitBall(llevaBalon[0], Constants.posteIzqArcoSup, 1, 9 + r.nextInt(2)));
    }
  }

  private double distanciaPorteria(GameSituations sp, boolean rival) {
    double coordenadas[] = sp.getTrajectory(sp.iteration());
    Position balon = new Position(coordenadas[0], coordenadas[1]);
    Position pos;
    if (rival) {
      pos = Constants.centroArcoSup;
    } else {
      pos = Constants.centroArcoInf;
    }
    double distancia = pos.distance(balon);

    return distancia;
  }

  private void zonaPortero(GameSituations s) {

    posicionBalon.put(s.iteration(), s.ballPosition());

    if (posicionBalon.size() > 3) {

      Position p1 = posicionBalon.get(s.iteration() - 2);
      Position p2 = posicionBalon.get(s.iteration() - 1);
      Position p3 = posicionBalon.get(s.iteration());

      if ((p3.getX() < (Constants.LARGO_AREA_CHICA / 2)) && (p3.getX() > -(
          Constants.LARGO_AREA_CHICA / 2))) {

//        		 System.out.println("RANGO!");

        if (p3.getX() < p2.getX()) {
          if (p2.getX() < p1.getX()) {
//		    			 System.out.println("IZQUIERDA!");
            comandos.add(new CommandMoveTo(0, new Position(s.ballPosition().getX(),
                                                           Constants.centroArcoInf.getY())));
          }
        }

        if (p3.getX() > p2.getX()) {
          if (p2.getX() > p1.getX()) {
//		    			System.out.println("DERECHA!");
            comandos.add(new CommandMoveTo(0, new Position(s.ballPosition().getX(),
                                                           Constants.centroArcoInf.getY())));
          }
        }

        posicionBalon.clear();
      }

    }

//    	if((s.ballPosition().getX()>Constants.posteIzqArcoInf.getX()) && (s.ballPosition().getX()<Constants.posteDerArcoInf.getX())){
//    		comandos.add(new ComandoIrA(0,new Posicion(s.ballPosition().getX(),Constants.centroArcoInf.getY()+2)));
//    	}

    comandos.add(new CommandHitBall(0, Constants.penalInf, 1, true));

//    	if (bandaIzquierda(posicionPortero)) {
//			double distancia = jugadoresPase[0].distancia(jugadoresPase[6]);
//			comandos.add(new CommandHitBall(0, s.myPlayers()[7],calcularFuerza(distancia),calcularFuerza(distancia)));
//		} else {
//			double distancia = jugadoresPase[0].distancia(jugadoresPase[6]);
//			comandos.add(new CommandHitBall(0, s.myPlayers()[6], calcularFuerza(distancia),calcularFuerza(distancia)));
//		}

  }

  private double calcularAngulo(double distanciaAlJugador) {
    double fuerza;
    if (distanciaAlJugador > 30) {
      fuerza = 1;
    } else if (distanciaAlJugador > 20) {
      fuerza = 0.8;
    } else {
      fuerza = 0.6;
    }
    return fuerza;
  }

  private double calcularFuerza(double distanciaAlJugador) {
    double angulo;
    if (distanciaAlJugador > 30) {
      angulo = 1;
    } else if (distanciaAlJugador > 20) {
      angulo = 0.8;
    } else {
      angulo = 0.6;
    }
    return angulo;
  }
}*/
