/*
 * Rabobong v.1.20.0 es un equipo de futbol que pretende participar del JavaCup 2011.
    Copyright (C) 2011  freymam.

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

package org.dsaw.javacup.tactics.jvc2013.rabobong;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
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

public class Rabobong implements Tactic {

  Position alineacion1[] = new Position[]{
      new Position(0.0, -48.79864253393666),
      new Position(-17.11888111888112, -27.31900452488688),
      new Position(0.7132867132867133, -31.357466063348415),
      new Position(15.692307692307693, -27.794117647058822),
      new Position(-10.223776223776223, -19.479638009049776),
      new Position(8.797202797202797, -14.728506787330318),
      new Position(-1.188811188811189, -11.402714932126697),
      new Position(-7.608391608391608, -4.276018099547511),
      new Position(0.7132867132867133, 0.0),
      new Position(-22.11188811188811, -0.47511312217194573),
      new Position(19.97202797202797, -0.47511312217194573)
  };

  Position alineacion2[] = new Position[]{
      new Position(0.0, -48.79864253393666),
      new Position(-17.11888111888112, -27.31900452488688),
      new Position(-0.4755244755244755, -30.88235294117647),
      new Position(15.692307692307693, -27.794117647058822),
      new Position(-10.223776223776223, -19.479638009049776),
      new Position(6.895104895104895, -19.95475113122172),
      new Position(-1.6643356643356644, -16.628959276018097),
      new Position(-5.468531468531468, -7.364253393665159),
      new Position(5.468531468531468, -7.364253393665159),
      new Position(-22.11188811188811, -0.47511312217194573),
      new Position(19.97202797202797, -0.47511312217194573)
  };

  Position alineacion3[] = new Position[]{
      new Position(0.0, -43.43438914027149),
      new Position(-16.405594405594407, -14.015837104072398),
      new Position(-0.23776223776223776, -16.866515837104075),
      new Position(15.692307692307693, -13.778280542986426),
      new Position(-10.937062937062937, 10.690045248868778),
      new Position(7.37062937062937, 2.6131221719457014),
      new Position(21.874125874125873, 18.29185520361991),
      new Position(-24.251748251748253, 26.368778280542987),
      new Position(1.902097902097902, 27.556561085972852),
      new Position(-11.174825174825173, 38.72171945701358),
      new Position(14.503496503496503, 40.38461538461539)
  };

  Position alineacion4[] = new Position[]{
      new Position(0.0, -50.93665158371041),
      new Position(-16.405594405594407, -31.119909502262445),
      new Position(-0.4755244755244755, -34.20814479638009),
      new Position(16.405594405594407, -32.07013574660634),
      new Position(-9.748251748251748, -12.59049773755656),
      new Position(7.37062937062937, -17.57918552036199),
      new Position(15.454545454545453, 1.4253393665158371),
      new Position(-19.734265734265733, 4.038461538461538),
      new Position(-2.13986013986014, 12.115384615384617),
      new Position(-10.937062937062937, 25.893665158371043),
      new Position(8.083916083916083, 26.131221719457013)
  };

  Position alineacion5[] = new Position[]{
      new Position(0.0, -48.660633484162894),
      new Position(-16.405594405594407, -22.330316742081447),
      new Position(-0.23776223776223776, -26.131221719457013),
      new Position(15.93006993006993, -23.042986425339365),
      new Position(-10.223776223776223, 0.47511312217194573),
      new Position(6.6573426573426575, -7.126696832579185),
      new Position(17.832167832167833, 13.540723981900454),
      new Position(-19.25874125874126, 16.866515837104075),
      new Position(-1.188811188811189, 21.380090497737555),
      new Position(-11.65034965034965, 36.58371040723982),
      new Position(12.363636363636363, 36.82126696832579)
  };

  Position alineacion6[] = new Position[]{
      new Position(0., -42.62217194570136),
      new Position(-16.405594405594407, -22.330316742081447),
      new Position(-0.23776223776223776, -26.131221719457013),
      new Position(15.93006993006993, -23.042986425339365),
      new Position(-9.986013986013985, -3.5633484162895925),
      new Position(6.895104895104895, -6.889140271493213),
      new Position(15.93006993006993, 12.828054298642533),
      new Position(-19.020979020979023, 11.877828054298643),
      new Position(-0.951048951048951, 18.766968325791854),
      new Position(-11.65034965034965, 36.58371040723982),
      new Position(12.363636363636363, 36.82126696832579)
  };

  class TacticaDetalleImpl implements TacticDetail {

    @Override
    public String getTacticName() {
      return "Rabobong";
    }

    @Override
    public CountryCode getCountry() {
      return CountryCode.CO;
    }

    @Override
    public String getCoach() {
      return "Freymam Vallejo";
    }

    @Override
    public Color getShirtColor() {
      return new Color(255, 153, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(0, 0, 102);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor() {
      return new Color(255, 153, 0);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(0, 153, 0);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.SIN_ESTILO;
    }

    @Override
    public Color getShirtColor2() {
      return new Color(0, 0, 102);
    }

    @Override
    public Color getShortsColor2() {
      return new Color(0, 0, 102);
    }

    @Override
    public Color getShirtLineColor2() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(255, 153, 0);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(255, 0, 0);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
    }

    class JugadorImpl implements PlayerDetail {

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
      public String getPlayerName() {
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
    public PlayerDetail[] getPlayers() {
      return new PlayerDetail[]{
          new JugadorImpl("Oscor Feyde", 20, new Color(102, 51, 0), new Color(50, 0, 0), 1.0d, 1.0d,
                          0.58d, true),
          new JugadorImpl("Haymen", 21, new Color(204, 153, 0), new Color(50, 0, 0), 1.0d, 0.69d,
                          0.69d, false),
          new JugadorImpl("Walkar", 22, new Color(153, 102, 0), new Color(50, 0, 0), 0.81d, 0.9d,
                          0.81d, false),
          new JugadorImpl("Mann Dimar", 23, new Color(102, 0, 0), new Color(50, 0, 0), 1.0d, 0.74d,
                          0.76d, false),
          new JugadorImpl("Van Heeswick", 24, new Color(255, 200, 150), new Color(50, 0, 0), 0.91d,
                          0.47d, 0.95d, false),
          new JugadorImpl("Juust Posthunna", 25, new Color(255, 204, 204), new Color(50, 0, 0),
                          0.61d, 0.86d, 0.85d, false),
          new JugadorImpl("Johan Fecha", 26, new Color(255, 204, 102), new Color(50, 0, 0), 0.57d,
                          0.49d, 1.0d, false),
          new JugadorImpl("Theodoro Eltin", 27, new Color(102, 102, 0), new Color(50, 0, 0), 0.58d,
                          0.54d, 1.0d, false),
          new JugadorImpl("Thomas Dakkor", 28, new Color(102, 0, 51), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, false),
          new JugadorImpl("Robert Geskin", 29, new Color(51, 0, 0), new Color(50, 0, 0), 0.9d, 0.9d,
                          0.89d, false),
          new JugadorImpl("Donis Henchov", 30, new Color(179, 145, 114), new Color(50, 0, 0), 1.0d,
                          1.0d, 1.0d, false)
      };
    }
  }

  TacticDetail detalle = new TacticaDetalleImpl();

  @Override
  public TacticDetail getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return alineacion1;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
  }

  public Position[] getPosicionAtaque() {
    return alineacion3;
  }

  public Position[] getPosicionDefensa() {
    return alineacion4;
  }

  public Position[] getPosicionMedia() {
    return alineacion5;
  }

  public Position[] getPosicionEstirado() {
    return alineacion6;
  }

  // OBJETOS GLOBALES
  Random r = new Random();
  Position[] formacionActual;
  // las direcciones hacia donde se desmarcaran
  double[] desmarcandoseDireccion = new double[11];
  // iteraciones faltantes para dejar ese desmarcaje y buscar otro
  int[] desmarcandoseIteracion = new int[11];
  //Posicion referenciaPortero = Constants.centroArcoInf.moverPosicion(0, -15);
  Position referenciaPortero = Constants.centroArcoInf;
  LinkedList<Command> comandos = new LinkedList<>();

  // MIS VARIABLES GLOBALES
  double dispararAGol = 0;
  double darPase = 0;
  double acarrearBalon = 0;
  int jugadorDestino = -1;
  int jugadorOrigen = -1;
  boolean posecionBalon = false;
  boolean posec = false;
  boolean jugando = true;
  double anguloEspacio;
  double distanciaEspacio;
  Position posicionDestino;
  double velocidadBalon = 0;
  int indPorteroRival = -1;
  Position porteroRival;
  int indPortero = 0;
  int desespero = 0;

  // MIS CONSTANTES
  // Tengo el ballPosition
  double DISPARO_MORTAL = 77.0; //78.0;
  double PASE_MORTAL = 81.0;
  double ACARREO_MORTAL = 85.0;
  double MINIMO_DISPARA_A_GOL = 65.0;
  double DARPASE = 63.5; //66.0;
  double MINIMO_ACARREAR_BALON = 65.0;// 67.3;
  // AnguloVerticalPase
  int MAX_DISTANCIA_PASE_RASTRERO = 22;
  int MAX_DISTANCIA_PASE_AEREO = 50;
  // Cubrir pase
  int DISTANCIA_MAXIMA_BUSQUEDA_RIVAL = 30;
  int DISTANCIA_MARCAJE_RIVAL = 5;
  int JUGADORES_MARCANDO = 4;
  int DISTANCIA_CORRECCION_FORMACION_MARCAJE = 15;
  // Pase exitoso
  int MIN_DISTANCIA_PASE = 7;
  double probDistanciaPase = 0.13;
  double probPresenciaRival = 0.16;
  double probHaciaAdelante = 0.21;
  double probCercaniaAlArcoRival = 0.26;
  double probLlegarPrimero = 0.24;
  // Hacia adelante
  int AVANCE_MEDIA_PASE_RETRASADO = 5;
  int AVANCE_MAXIMA_PASE_RETRASADO = 20;
  int AVANCE_MAXIMA_PASE_ADELANTE = 35;
  // Presencia rival
  double DISTANCIA_MINIMA_CERCANIA_TRASCENDENTE = 1.3;
  // Disparar a gol
  int DISPARAR_DISTANCIA_MINIMA = 20;
  int DISPARAR_DISTANCIA_MEDIA = 32;
  int DISPARAR_DISTANCIA_LARGA = 54;
  // Angulo vertical
  int DISTANCIA_PARA_45_GRADOS = 48;
  int DISTANCIA_PARA_0_GRADOS = 12;
  // Desmarcarse
  int DESMARCANDOSE_ITERACIONES_MAXIMO = 30;
  int RADIO_DE_DESPLAZAMIENTO_MAXIMO = 25;// 25;
  int JUGADORES_A_DESMARCARSE = 6;
  int MINIMA_CERCANIA_A_JUGADOR = 15;
  // Hacer formacion
  int DISTANCIA_BALON_ATAQUE = 13;
  int DISTANCIA_BALON_DEFENSA = -12;

  /**
   * Modifica el valor de las constantes. Es usada solo en tareas de depuracion.
   */
  private void ModificarConstantes() {
    if (posec != posecionBalon) {
      posec = posecionBalon;
      if (posecionBalon) {
        // Desmarcarse
        DESMARCANDOSE_ITERACIONES_MAXIMO += 10;// = 40;
        RADIO_DE_DESPLAZAMIENTO_MAXIMO += 10;// = 35;
        JUGADORES_A_DESMARCARSE += 1;// = 7;
        MINIMA_CERCANIA_A_JUGADOR -= 3;// = 12;
        // Cubrir pase
        DISTANCIA_MAXIMA_BUSQUEDA_RIVAL -= 10;// = 20;
        DISTANCIA_MARCAJE_RIVAL += 4; // = 9;
        JUGADORES_MARCANDO -= 2;// = 2;
        DISTANCIA_CORRECCION_FORMACION_MARCAJE -= 5;// = 10;
        // Hacer formacion
        DISTANCIA_BALON_ATAQUE -= 5;// = 8;
        DISTANCIA_BALON_DEFENSA -= 6; // = -18;
      } else {
        // Desmarcarse
        DESMARCANDOSE_ITERACIONES_MAXIMO -= 10;// = 40;
        RADIO_DE_DESPLAZAMIENTO_MAXIMO -= 10;// = 35;
        JUGADORES_A_DESMARCARSE -= 1;// = 7;
        MINIMA_CERCANIA_A_JUGADOR += 3;// = 12;
        // Cubrir pase
        DISTANCIA_MAXIMA_BUSQUEDA_RIVAL += 10;// = 20;
        DISTANCIA_MARCAJE_RIVAL -= 4; // = 9;
        JUGADORES_MARCANDO += 2;// = 2;
        DISTANCIA_CORRECCION_FORMACION_MARCAJE += 5;// = 10;
        // Hacer formacion
        DISTANCIA_BALON_ATAQUE += 5;// = 8;
        DISTANCIA_BALON_DEFENSA += 6; // = -18;
      }
    }
  }

  private void Desesperar(GameSituations sp) {
    int deses = (int) (sp.iteration() / (Constants.ITERACIONES / 4));
    if (deses > desespero && sp.rivalGoals() > sp.myGoals()) {
      desespero = deses;
      // Pase exitoso
      MIN_DISTANCIA_PASE += 1 * desespero; // = 10; //+3
      probDistanciaPase -= 0.01 * desespero; // 0.13;
      probPresenciaRival -= 0.02 * desespero; // = 0.16;
      probHaciaAdelante += 0.02 * desespero; // = 0.21;
      probCercaniaAlArcoRival += 0.03 * desespero; // = 0.26;
      probLlegarPrimero -= 0.02 * desespero; // = 0.24;
      // Desmarcarse
      RADIO_DE_DESPLAZAMIENTO_MAXIMO += 1 * desespero; // = 40; //+
      MINIMA_CERCANIA_A_JUGADOR -= 1 * desespero; // = 12; //-
      // Cubrir pase
      DISTANCIA_MAXIMA_BUSQUEDA_RIVAL += 1 * desespero; // = 20; //-
      DISTANCIA_MARCAJE_RIVAL -= 0.5 * desespero; // = 9; //+
      JUGADORES_MARCANDO += 0.4 * desespero; // = 2; //-
      DISTANCIA_CORRECCION_FORMACION_MARCAJE += 2 * desespero; // = 10; //-
      // Hacer formacion
      DISTANCIA_BALON_ATAQUE -= 3 * desespero; // = 8; //-
      DISTANCIA_BALON_DEFENSA -= 4 * desespero; // = -8; //-
    }
  }

  @Override
  public List<Command> execute(GameSituations sp) {
    comandos.clear();

    // Modifica variables
    ModificarConstantes();
    Desesperar(sp);

    CalcularVelocidadBalon(sp);
    EncontrarPorteroRival(sp);

    // QUIEN TIENE EL BALON?
    PosecionBalon(sp);

    // UBICACION BASICA SEGUN LA FORMACION
    HacerFormacion(sp);

    // PORTERO
    MoverPortero(sp);

    // ATACANDO
    // NO TENGO BALON
    // Desmarcarse
    Desmarcarse(sp);

    // DEFENDIENDO
    // Cubrir pase
    CubrirPase(sp);
    // Recuperar ballPosition
    RecuperarBalon2(sp);

    // TENGO EL BALON
    dispararAGol = DispararAGol(sp);
    DominarAntesDeRematar(sp);
    darPase = DarPase(sp);
    acarrearBalon = AcarrearBalon(sp);

    // ACERCARSE AL BALON
    MoverJugadorCercano(sp);

    // ELIMINAR ESTO
    if (sp.canKick().length > 0) {
      int a = 7;
      a += darPase;
    }
    if (sp.rivalCanKick().length > 0) {
      int a = 8;
      a += darPase;
    }

    return comandos;
  }

  private void EncontrarPorteroRival(GameSituations sp) {
    if (indPorteroRival == -1) {
      indPorteroRival = 0;
      PlayerDetail[] detallesRivales = sp.rivalPlayersDetail();
      for (int j = 0; j < detallesRivales.length; j++) {
        if (detallesRivales[j].isGoalKeeper()) {
          indPorteroRival = j;
          break;
        }
      }
      porteroRival = sp.rivalPlayers()[indPorteroRival];
    }
  }

  private void DominarAntesDeRematar(GameSituations sp) {
    Position[] jugadores = sp.myPlayers();
    for (int i : sp.canKick()) {
      if (JugadorDentroAreaGrandeRival(jugadores[i])) {
        int[] recuperadoresRival = getRecuperacionBalonRival(sp);
        if (recuperadoresRival.length <= 1
            || (recuperadoresRival.length > 1
                && recuperadoresRival[0] > 19)) {
          double potencia = PotenciaPase(3, sp.getMyPlayerPower(i), false, true);
          comandos.add(new CommandHitBall(i, Constants.centroArcoSup, potencia, 0.0));
        }
      }
    }
  }

  private boolean JugadorDentroAreaGrandeRival(Position posicion) {
    if (Math.abs(posicion.getX()) <= (Constants.LARGO_AREA_GRANDE / 2)) {
      if ((Constants.LARGO_CAMPO_JUEGO / 2) - posicion.getY() <= Constants.ANCHO_AREA_GRANDE) {
        return true;
      }
    }
    return false;
  }

  private void CalcularVelocidadBalon(GameSituations sp) {
    int iteraciones = 5;
    double[] posRecuperacion = sp.getTrajectory(iteraciones);
    Position destino = new Position(posRecuperacion[0], posRecuperacion[1]);
    velocidadBalon = sp.ballPosition().distance(destino) / iteraciones;
  }

  private void MoverJugadorCercano(GameSituations sp) {
//		double MINIMA_DISTANCIA_CERCANIA_JUGADORES = 5;
//		Posicion[] jugadores = sp.myPlayers();
//		int i = sp.ballPosition().indiceMasCercano(jugadores);
//		int j = jugadores[i].indiceMasCercano(jugadores, i);
//		if (i != 0 && jugadores[i].distancia(jugadores[j]) > 
//			MINIMA_DISTANCIA_CERCANIA_JUGADORES
//		) {
//			comandos.add(new ComandoIrA(i, sp
//				.ballPosition()));
//		}
  }

  private void MoverPortero(GameSituations sp) {
    double x;
    double y;
    double a = 12;//15;
    double
        b =
        Constants.centroArcoInf.distance(formacionActual[0])
        + 10; // Constants.centroArcoInf.distancia(formacionActual[0]);
    double xj = sp.ballPosition().getX();
    double yj = Constants.centroArcoInf.distance(new Position(0, sp.ballPosition().getY()));
    double cj = 7;//10;

    double r = 1 / (a * a) + Math.pow(yj / (xj * b), 2);
    double s = 2 * (yj * cj) / (xj * b * b);
    double t = Math.pow(cj / b, 2) - 1;

    double det = s * s - 4 * r * t;
    if (det >= 0) {
      if (xj > 0) {
        x = -s / (2 * r) + Math.sqrt(det) / (2 * r);
      } else {
        x = -s / (2 * r) - Math.sqrt(det) / (2 * r);
      }
      y = Math.abs((yj * x) / xj);
      comandos.add(new CommandMoveTo(0, Constants.centroArcoInf.movePosition(x, y)));
    }
    if (!posecionBalon) {
      double[] posBalon = sp.getTrajectory(10);
      Position destinoBalon = new Position(posBalon[0], posBalon[1]);
      double anguloPosteIzq = sp.ballPosition().angle(Constants.posteIzqArcoInf);
      double anguloPosteDer = sp.ballPosition().angle(Constants.posteDerArcoInf);
      double anguloDestino = sp.ballPosition().angle(destinoBalon);
      // hacia la porteria
      if (anguloDestino >= anguloPosteIzq
          && anguloDestino <= anguloPosteDer) {
        if (sp.ballPosition().getX() > 0) {
          comandos.add(new CommandMoveTo(0, Position
              .Intersection(sp.myPlayers()[0], Constants.posteDerArcoInf, sp.ballPosition(),
                            destinoBalon)));
        } else {
          comandos.add(new CommandMoveTo(0, Position
              .Intersection(sp.myPlayers()[0], Constants.posteIzqArcoInf, sp.ballPosition(),
                            destinoBalon)));
        }
      }
    }
    //TODO mover portero
  }

  /**
   * Encuentra quien tiene el ballPosition en el momento asi el ballPosition no este en dominio de
   * algun jugador. No modifica comandos
   */
  private void PosecionBalon(GameSituations sp) {
    if (!sp.ballPosition().isInsideGameField(-1)) {
      if (jugando) {
        posecionBalon = !posecionBalon;
      }
      jugando = false;
    } else {
      if (sp.isRivalStarts()) {
        posecionBalon = false;
      } else if (sp.isStarts()) {
        posecionBalon = true;
      } else {
        jugando = true;
      }
    }
    if (sp.rivalCanKick().length > 0) {
      posecionBalon = false;
    }
    if (sp.canKick().length > 0) {
      if (!posecionBalon) {
        LimpiarDesmarcandoseIteraciones();
      }
      posecionBalon = true;
    }
    if (!posecionBalon) {
      jugadorOrigen = -1;
      jugadorDestino = -1;
    }
  }

  public int[] getRecuperacionBalonRival(GameSituations sp) {
    int it = 0;
    boolean found = false;
    Position pJug;
    double dist0, dist;
    int idxFound = -1;
    LinkedList<Double> founds = new LinkedList<>();
    PlayerDetail detalles[] = sp.rivalPlayersDetail();
    while (!found) {
      double[] posBalon = sp.getTrajectory(it);
      if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
        return new int[]{};
      }
      if (posBalon[2] <= Constants.ALTO_ARCO) {
        for (int i = 0; i < sp.rivalPlayers().length; i++) {
          if (posBalon[2] <= (detalles[i].isGoalKeeper() ? Constants.ALTO_ARCO
                                                         : Constants.ALTURA_CONTROL_BALON)) {
            pJug = sp.rivalPlayers()[i];
            dist0 = (double) it * Constants.getVelocidad(sp.rivalPlayersDetail()[i].getSpeed());
            dist = pJug.distance(new Position(posBalon[0], posBalon[1]));
            if (dist0 >= dist) {
              found = true;
              founds.add(dist);
              founds.add((double) i);
              idxFound = it;
            }
          }
        }
      }
      it++;
    }
    for (int i = 2; i < founds.size(); i = i + 2) {
      for (int j = 0; j < i; j = j + 2) {
        if (founds.get(i) < founds.get(j)) {
          dist0 = founds.get(i);
          dist = founds.get(i + 1);
          founds.set(i, founds.get(j));
          founds.set(i + 1, founds.get(j + 1));
          founds.set(j, dist0);
          founds.set(j + 1, dist);
        }
      }
    }
    for (int i = founds.size() - 1; i >= 0; i = i - 2) {
      founds.remove(i - 1);
    }
    founds.add(0, (double) idxFound);
    int[] result = new int[founds.size()];
    for (int i = 0; i < founds.size(); i++) {
      result[i] = (int) founds.get(i).doubleValue();
    }
    return result;
  }

  /**
   * Le dice al jugador que puede recuperar el ballPosition acercar a este. Se eligen jugadores
   * segun la funcion sp.getTrajectory. Modifica comandos.
   */
  private void RecuperarBalon2(GameSituations sp) {
    int[] recuperadores = sp.getRecoveryBall();
    int[] recuperadoresRival = getRecuperacionBalonRival(sp);
    Position[] jugadores = sp.myPlayers();

    if (recuperadores.length > 1) {
      double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
      Position destino = new Position(posRecuperacion[0], posRecuperacion[1]);
      for (int i = 1; i < recuperadores.length; i++) {
        int j = recuperadores[i];
        if (j != 0) {
          //no portero
          comandos.add(new CommandMoveTo(j, destino));
          break;
        } else {
          if (recuperadoresRival.length <= 1 ||
              (recuperadoresRival.length > 1 && recuperadoresRival[0] - 4 > recuperadores[0])) {
            //portero, retroceder
            if (recuperadores[i] == 0) {
              if (jugadores[0]
                      .distance(sp.rivalPlayers()[jugadores[0].nearestIndex(sp.rivalPlayers())])
                  > 4) {
                if (sp.ballPosition().distance(jugadores[0]) - 4
                    < Constants.DISTANCIA_CONTROL_BALON_PORTERO) {
                  double altura = sp.ballAltitude();
                  if (altura > Constants.ALTO_ARCO - 1 ||
                      velocidadBalon > 1.7) {
                    Position
                        des =
                        jugadores[0]
                            .moveAngle((jugadores[0].angle(sp.ballPosition()) - Math.PI), 3);
                    if (des.isInsideGameField(0)) {
                      destino = des;
                    }
                  }
                }
              }
            }
            comandos.add(new CommandMoveTo(j, destino));
            break;
          }
        }
      }
    }
  }

  /**
   * Marcaje a los posibles destinos de pase cuando no se tiene el ballPosition. Modifica comandos.
   */
  private void CubrirPase(GameSituations sp) {
    boolean[] jugadorMarcando = {false, false, false, false, false, false,
                                 false, false, false, false, false};
    int jugador;
    double distanciaAlRival;
    Position[] jugadores = sp.myPlayers();
    Position[] rivales = sp.rivalPlayers();
    Position destino;

    int[] indicesRivales = jugadores[0].nearestIndexes(rivales);
    for (int j = 0; j < indicesRivales.length; j++) {// : indicesRivales) {
      if (j > JUGADORES_MARCANDO) {
        break;
      }
      int k = indicesRivales[j];
      jugador = rivales[k].nearestIndex(jugadores, 0);
      if (jugador != 0 && !jugadorMarcando[jugador]) {
        distanciaAlRival = formacionActual[jugador]
            .distance(rivales[k]);
        if (distanciaAlRival < DISTANCIA_MAXIMA_BUSQUEDA_RIVAL) {
          destino = rivales[k].moveAngle(rivales[k].angle(sp
                                                              .ballPosition()),
                                         DISTANCIA_MARCAJE_RIVAL);
          jugadorMarcando[jugador] = true;
          if ((jugador == 1
               || jugador == 2
               || jugador == 3)
              && distanciaAlRival > DISTANCIA_CORRECCION_FORMACION_MARCAJE
              && destino.getY() < formacionActual[jugador].getY()) {
            formacionActual[jugador] =
                formacionActual[jugador].moveAngle(formacionActual[jugador].angle(destino), 0.1);
          }
          comandos.add(new CommandMoveTo(jugador, destino));
        }
      }
    }
  }

  /**
   * Busca, califica y selecciona la mejor opcion de pase. Modifica comandos.
   *
   * @return double para el mejor pase exitoso.
   */
  private double DarPase(GameSituations sp) {
    // valor entre 0 y 100. Se consultara a cada jugador su probabilidad de
    // recibir pase exitoso
    // si el valor es menor que la constante DARPASE
    double paseExitoso = 0;
    double actualPaseExitoso;
    double potencia;
    double anguloVertical;
    double distanciaPase = 100;
    double actualDistanciaPase;
    Position[] jugadores = sp.myPlayers();
    Position actualDestino;
    Position destino = jugadores[10];

    for (int i : sp.canKick()) {
      for (int j = 1; j < jugadores.length; j++) {
        if (i != j &&
            jugadores[i].distance(jugadores[j]) > MIN_DISTANCIA_PASE) {
          // posicion modificada respecto al espacio a recorrer
          actualDestino = PosicionBalonAlEspacio(sp, i, jugadores[i],
                                                 j, jugadores[j], sp.rivalPlayers(), 0.9, 50,
                                                 false);
          // exito del pase elegido
          actualPaseExitoso = PaseExitoso(sp, jugadores[i], actualDestino);
          // distancia al jugador destino
          actualDistanciaPase = jugadores[i].distance(actualDestino);
          // escoge el mejor
          if (actualPaseExitoso >= paseExitoso) {
            distanciaPase = actualDistanciaPase;
            paseExitoso = actualPaseExitoso;
            destino = actualDestino;
            jugadorDestino = j;
            jugadorOrigen = i;
            anguloEspacio = 180 * jugadores[j].angle(destino) / Math.PI;
            distanciaEspacio = jugadores[j].distance(destino);
            posicionDestino = destino;
          }
        }
      }
      potencia = PotenciaPase(distanciaPase, sp.myPlayersDetail()[i]
          .getPower(), sp.isStarts(), false);
      anguloVertical = AnguloVerticalPase(distanciaPase, sp.myPlayersDetail()[i].getPower());
      // Negocia con entre dar pase, o disparar
      if (paseExitoso > DARPASE) {
        if (dispararAGol < DISPARO_MORTAL
            || (dispararAGol >= DISPARO_MORTAL && paseExitoso >= PASE_MORTAL)) {
          comandos.add(new CommandHitBall(i, destino, potencia,
                                          anguloVertical));
        }
      }
    }
    return paseExitoso;
  }

  /**
   * Encuentra una posicion cercana al destino cuya presencia de rivalPlayers sea lo menor posible.
   *
   * @return Posicion cercana o igual al destino.
   */
  private Position PosicionBalonAlEspacio(GameSituations sp, int i, Position origen, int j,
                                          Position destino,
                                          Position[] rivales, double radioAvanceEspacio,
                                          double maximoRadioAvance, boolean minimaCercania) {
    Position destinoEspacio = destino;
    Position actualDestino;
    double angulo = -Math.PI / 8;
    double paseExitoso = 0;
    double actualPaseExitoso;
    int destinos = 11;
    Position[] jugadores = sp.myPlayers();
    int actualJugadorCercano;
    double actualCercania;

    for (int d = 0; d < destinos; d++) {
      for (int e = 0; e < 10; e++) {
        actualDestino = destino.moveAngle(angulo, e
                                                  * radioAvanceEspacio);
        actualJugadorCercano = actualDestino.nearestIndexes(jugadores, j)[0];
        actualCercania = actualDestino.distance(jugadores[actualJugadorCercano]);
        if (actualDestino.isInsideGameField(-4)
            && e * radioAvanceEspacio < maximoRadioAvance) {
          if (!minimaCercania || (minimaCercania && actualCercania > MINIMA_CERCANIA_A_JUGADOR)) {
            actualPaseExitoso = PaseExitoso(sp, origen, actualDestino);
            if (actualPaseExitoso > paseExitoso) {
              paseExitoso = actualPaseExitoso;
              destinoEspacio = actualDestino;
            }
          }
        }
      }
      angulo += (5 * Math.PI / 4) / destinos;
    }
    return destinoEspacio;
  }

  /**
   * Decide cual sera la altura del pase. El resultado debe estar entre 0 y 45.
   *
   * @return double angulo vertical
   */
  private double AnguloVerticalPase(double distanciaPase, double remate) {
    if (distanciaPase > MAX_DISTANCIA_PASE_AEREO) {
      return 24;
    }
    if (distanciaPase > MAX_DISTANCIA_PASE_RASTRERO) {
      return distanciaPase * 0.85;
    }
    return 0;
  }

  /**
   * Decide cual sera la potencia del pase dependiendo de la distancia del pase, la potencia del
   * rematador, si es pelota quieta o si es para un acarrereo.
   *
   * @return double entre 0 y 1
   */
  private double PotenciaPase(double distancia, double remate,
                              boolean sacando, boolean acarreo) {
    double potencia = 1.0;
    if (distancia < 48) {
      double medioCampo = Constants.LARGO_CAMPO_JUEGO / 2;
      potencia = Math.sqrt(distancia / (medioCampo * remate));
    }
    //TODO potencia pase
    if (sacando) {
      if (!acarreo) {
        potencia = potencia * 1.80; // sacando
      } else {
        potencia = potencia * 1.10; // acarreo sacando
      }
    } else if (acarreo) {
      potencia = potencia * 0.80; // acarreo
    } else if (distancia < MAX_DISTANCIA_PASE_RASTRERO) {
      potencia = potencia * 1.32; // pase rastrero
    } else {
      potencia = potencia * 0.99; // pase aereo
    }
    return potencia > 1.0 ? 1.0 : potencia;
  }

  /**
   * Califica el exito que tendria un pase desde origen a destino. Se calcula segun la distancia del
   * pase, la presencia del rival, la verticalizacion, la cercania al arco rival y si es posible
   * llegar antes que el rival.
   *
   * @return double de 0 a 100
   */
  private double PaseExitoso(GameSituations sp, Position origen,
                             Position destino) {
    // un pase exitoso esta en funcion del la posicion del jugador origen y
    // el jugador destino.
    // en base a la distancia entre ellos, entre menor distancia mayor
    // probabilidad.
    // depente tambien de la menor distancia a sus rivalPlayers
    // si el rival esta a una distancia menor a la distancia entre los del
    // pase, la proba deberia ser 0
    double paseExitoso;
    double valorDistanciaPase;
    //probDistanciaPase = 0.13;
    double valorPresenciaRival;
    //probPresenciaRival = 0.16;
    double valorHaciaAdelante;
    //probHaciaAdelante = 0.21;
    double valorCercaniaAlArcoRival;
    //probCercaniaAlArcoRival = 0.26;
    double valorLlegarPrimero;
    //probLlegarPrimero = 0.24;
    double distanciaPase = origen.distance(destino);

    if (sp.isStarts()) {
      //probDistanciaPase += 0.09;
      //probPresenciaRival -= 0.00;
      //probHaciaAdelante -= 0.08;
      //probCercaniaAlArcoRival -= 0.01;
      //probLlegarPrimero += 0.0;
    }
    if (distanciaPase < MIN_DISTANCIA_PASE) {
      paseExitoso = 40;
    } else {
      valorDistanciaPase = 100 - distanciaPase;
      valorPresenciaRival = 100 - PresenciaRivales(origen, destino, sp
          .rivalPlayers());
      valorHaciaAdelante = HaciaAdelante(origen, destino);
      valorCercaniaAlArcoRival = CercaniaAlArcoRival(destino);
      valorLlegarPrimero = LlegarPrimero(sp, destino);
      paseExitoso = ((valorDistanciaPase * probDistanciaPase)
                     + (valorPresenciaRival * probPresenciaRival)
                     + (valorHaciaAdelante * probHaciaAdelante)
                     + (valorCercaniaAlArcoRival * probCercaniaAlArcoRival) + (valorLlegarPrimero
                                                                               * probLlegarPrimero));
    }
    return paseExitoso;
  }

  /**
   * Da un valor a la disputa entre dos jugadores rivalPlayers entre si para llegar a un punto fijo
   * destino Depende principalmente de la distancia y la velocidad de cada jugador.
   *
   * @return double entre 0 y 100
   */
  private double LlegarPrimero(GameSituations sp, Position destino) {
    // valor entre 0 .. 100
    // depende de la velocidad de los mas cercanos al punto destino
    double llegarPrimero;
    Position[] rivales = sp.rivalPlayers();
    Position[] jugadores = sp.myPlayers();
    int j = destino.nearestIndex(rivales);
    int i = destino.nearestIndex(jugadores);
    Position rival = rivales[j];
    Position jugador = jugadores[i];
    double distanciaJugador = destino.distance(jugador);
    double distanciaRival = destino.distance(rival);
    double velocidadJugador = Constants.getVelocidad(sp.getMyPlayerSpeed(i));
    double velocidadRival = Constants.getVelocidad(sp.getRivalPlayerSpeed(j));
    double trayectoJugador = distanciaJugador * (2 - velocidadJugador);
    double trayectoRival = distanciaRival * (2 - velocidadRival);
    double diferencia = trayectoRival - trayectoJugador;

    llegarPrimero = (diferencia + 28) * 100 / 60;
    return llegarPrimero;
  }

  /**
   * Cercania al arco rival en un valor entre 40 y 80.
   *
   * @return double de 40 a 80.
   */
  private double CercaniaAlArcoRival(Position posicion) {
    // si esta mas cerca al arco rival tendra mejor valor
    // empieza a contar desde -52 a 52 tomando valores de 40 .. 80
    double cercaniaAlArcoRival;

    cercaniaAlArcoRival =
        Constants.centroArcoSup.distance(posicion) * 50 / Constants.LARGO_CAMPO_JUEGO;
    cercaniaAlArcoRival = 90 - cercaniaAlArcoRival;
    return cercaniaAlArcoRival;
  }

  /**
   * Valora la verticalizacion entre dos posiciones
   */
  private double HaciaAdelante(Position origen, Position destino) {
    double haciaAdelante;
    double diferenciaY = Math.abs(origen.getY() - destino.getY());

    if (origen.getY() - AVANCE_MEDIA_PASE_RETRASADO < destino.getY()) {
      // para adelante
      if (diferenciaY > AVANCE_MAXIMA_PASE_ADELANTE) {
        haciaAdelante = 50;
      } else {
        haciaAdelante = 65 + (diferenciaY * 15 / AVANCE_MAXIMA_PASE_ADELANTE);
      }
    } else {
      // para atras
      if (diferenciaY > AVANCE_MAXIMA_PASE_RETRASADO) {
        haciaAdelante = 30;
      } else {
        haciaAdelante = 65 - (diferenciaY * 10 / AVANCE_MAXIMA_PASE_RETRASADO);
      }
    }
    return haciaAdelante;
  }

  /**
   * Encuentra la mayor presencia de rival entre dos posiciones amigas. La mayor valoracion (100)
   * corresponde a una presencia muy alta de rivalPlayers en el camino.
   *
   * @return double valorando entre 0 y 100
   */
  private double PresenciaRivales(Position jugadorOrigen,
                                  Position jugadorDestino, Position[] rivales) {
    double[] presenciasRivales = new double[rivales.length];
    double presenciaRival;
    int[] r = jugadorDestino.nearestIndexes(rivales);

    for (int i = 0; i < 4; i++) {// rivalPlayers.length; i++) {
      presenciasRivales[i] = PresenciaRival(jugadorOrigen,
                                            jugadorDestino, rivales[r[i]]);
    }
    presenciaRival = EscogerMayorDeLista(presenciasRivales);
    return presenciaRival;
  }

  /**
   * Valora la presencia de un rival entre dos posiciones amigas. La mayor valoracion indica la
   * presencia alta de el rival entre ellos.
   *
   * @return double entre 0 y 100.
   */
  private double PresenciaRival(Position jugadorOrigen,
                                Position jugadorDestino, Position rival) {
    // la presencia de un rival esta en su cercania al jugador
    // entre menor distancia mayor presencia
    // si el rival se interpone entre el origen y el destino su presencia
    // sera mayor
    // se presenta cuando la distancia al origen es menor desde el rival que
    // desde el destino
    // y la distancia entre los angulos a) del origen y al rival y b) del
    // origen al destino es peque�o
    // retorna un valor entre 0 y 100: 0 es lo mejor
    double presenciaRival;
    double valorCercaniaADestino; // al destino
    double probCercaniaADestino = 0.50; // 0.65;
    double valorAngulo;
    double probAngulo = 0.35; // 0.35;
    double valorCercaniaAOrigen;
    double probCercaniaAOrigen = 0.15;
    double distanciaPase = jugadorOrigen.distance(jugadorDestino);
    double distanciaRivalDestino = jugadorDestino.distance(rival);
    double distanciaRivalOrigen = jugadorOrigen.distance(rival);
    double anguloDestino = jugadorOrigen.angle(jugadorDestino);
    anguloDestino = anguloDestino > 0 ? anguloDestino * 180 / Math.PI
                                      : 360 + (anguloDestino * 180 / Math.PI);
    double anguloRival = jugadorOrigen.angle(rival);
    anguloRival = anguloRival > 0 ? anguloRival * 180 / Math.PI
                                  : 360 + (anguloRival * 180 / Math.PI);
    double diferenciaAngulos = Math.abs(anguloDestino - anguloRival);
    if (diferenciaAngulos > 180) {
      diferenciaAngulos = 360 - diferenciaAngulos;
    }
    double DISTANCIA_PRESENCIA_TRASCENDENTE = 7 + distanciaPase / 0.9;//1.3;
    double INICIO_CERCANIA_TRASCENDENTE = 7 + distanciaPase / 0.7;//1.1;

    valorCercaniaADestino = CercaniaADestino(distanciaPase,
                                             distanciaRivalOrigen, distanciaRivalDestino,
                                             DISTANCIA_PRESENCIA_TRASCENDENTE,
                                             INICIO_CERCANIA_TRASCENDENTE);
    valorAngulo = AnguloPresenciaRival(distanciaPase,
                                       distanciaRivalDestino, diferenciaAngulos);
    valorCercaniaAOrigen = CercaniaAOrigen(distanciaRivalOrigen,
                                           DISTANCIA_PRESENCIA_TRASCENDENTE);

    presenciaRival = (valorCercaniaADestino * probCercaniaADestino)
                     + (valorAngulo * probAngulo)
                     + (valorCercaniaAOrigen * probCercaniaAOrigen);
    return presenciaRival;
  }

  /**
   * Cercania a origen que depende de la distancia del rival al origen y esta limitada por un radio
   * maximo.
   *
   * @return double entre 0 y 100.
   */
  private double CercaniaAOrigen(double distanciaRivalOrigen,
                                 double distanciaPresenciaTrascendente) {
    double valorCercaniaAOrigen = 40;
    if (distanciaRivalOrigen < distanciaPresenciaTrascendente) {
      valorCercaniaAOrigen = 75 - (distanciaRivalOrigen * 30 / distanciaPresenciaTrascendente);
    }
    return valorCercaniaAOrigen;
  }

  /**
   * Angulo de presencia del rival respecto al destino.
   *
   * @return double
   */
  private double AnguloPresenciaRival(double distanciaPase,
                                      double distanciaRivalDestino, double diferenciaAngulos) {
    double valorAngulo;
    if (distanciaPase < distanciaRivalDestino) {
      valorAngulo = 20;
    } else {
      // falta incluir la distancia del rival al origen
      if (diferenciaAngulos < 35) {
        valorAngulo = 85 - (diferenciaAngulos * 25 / 35);
      } else if (diferenciaAngulos < 90) {
        valorAngulo = 60 - ((diferenciaAngulos - 35) * 45 / 55);
      } else {
        valorAngulo = 15;
      }
    }
    return valorAngulo;
  }

  /**
   * Cercania al destino de los rivalPlayers que depende de la distancia al origen y limitantes
   * minimo y maximo.
   */
  private double CercaniaADestino(double distanciaPase,
                                  double distanciaRivalOrigen, double distanciaRivalDestino,
                                  double distanciaPresenciaTrascendente,
                                  double inicioCercaniaTrascendente) {
    double valorCercaniaADestino;
    if (distanciaPase > distanciaRivalOrigen) {
      if (distanciaRivalDestino > distanciaPresenciaTrascendente) {
        if (distanciaRivalDestino > 50) {
          valorCercaniaADestino = 30 - (distanciaRivalDestino / 10);
        } else {
          valorCercaniaADestino = (65 - distanciaRivalDestino);
        }
      } else if (distanciaRivalOrigen < DISTANCIA_MINIMA_CERCANIA_TRASCENDENTE) {
        valorCercaniaADestino = 40 - (distanciaRivalDestino / 10);
      } else {
        valorCercaniaADestino =
            (100 - (distanciaRivalDestino * 60 / distanciaPresenciaTrascendente));
      }
    } else {
      if (distanciaRivalDestino > inicioCercaniaTrascendente) {
        valorCercaniaADestino = 40 - (distanciaRivalDestino / 10);
      } else {
        valorCercaniaADestino = 85 - (distanciaRivalDestino * 50 / inicioCercaniaTrascendente);
      }
    }
    return valorCercaniaADestino;
  }

  /**
   * De una lista, escoge el menor
   *
   * @param valores
   * @return
   */
        /*
	 * private double EscogerMenorDeLista(double[] valores) { double valor =
	 * 100; double actual;
	 * 
	 * for (int i = 0; i < valores.length; i++) { actual = valores[i]; if
	 * (actual < valor) { valor = actual; } } return valor; }
	 */

  /**
   * De una lista escoge la mayor
   */
  private double EscogerMayorDeLista(double[] valores) {
    double valor = 0;
    double actual;

    for (int i = 0; i < valores.length; i++) {
      actual = valores[i];
      if (actual > valor) {
        valor = actual;
      }
    }
    return valor;
  }
	
	/*
	private double EscogerPromedioDeLista(double[] valores) {
		double valor = 0;
		int j = 0;

		for (int i = 0; i < valores.length; i++) {
			if (valores[i] != 0 && valores[i] != 100) {
				j++;
				valor += valores[i];
			}
		}
		valor = valor / j;
		return valor;
	}*/

  /**
   * Disparar a gol. Modifica comandos.
   *
   * @return double de 0 a 100.
   */
  private double DispararAGol(GameSituations sp) {
    double dispararAGol = 100;
    Double distanciaAArco;
    double valorDistanciaAArco;
    double probDistanciaAArco = 0.48;
    double valorPresenciaRival;
    double probPresenciaRival = 0.18;
    double valorPorteroFueraDelArco;
    double probPorteroFueraDelArco = 0.34;
    Position objetivoEnArco;

    Position[] jugadores = sp.myPlayers();
    Position[] rivalesNoPortero = sp.rivalPlayers().clone();
    rivalesNoPortero[0] = rivalesNoPortero[10];

    for (int i : sp.canKick()) {
      distanciaAArco = jugadores[i].distance(Constants.centroArcoSup);
      if (distanciaAArco < DISPARAR_DISTANCIA_MINIMA) {
        valorDistanciaAArco = 100;
      } else if (distanciaAArco < DISPARAR_DISTANCIA_MEDIA) {
        valorDistanciaAArco =
            100 - ((distanciaAArco - DISPARAR_DISTANCIA_MINIMA) * 25 / (DISPARAR_DISTANCIA_MEDIA
                                                                        - DISPARAR_DISTANCIA_MINIMA));
      } else if (distanciaAArco < DISPARAR_DISTANCIA_LARGA) {
        valorDistanciaAArco =
            75 - ((distanciaAArco - DISPARAR_DISTANCIA_MEDIA) * 25 / (DISPARAR_DISTANCIA_LARGA
                                                                      - DISPARAR_DISTANCIA_MEDIA));
      } else {
        valorDistanciaAArco =
            50 - ((distanciaAArco - DISPARAR_DISTANCIA_LARGA) * 50 / (Constants.LARGO_CAMPO_JUEGO
                                                                      - DISPARAR_DISTANCIA_LARGA));
      }
      valorPresenciaRival = 100 - PresenciaRivales(jugadores[i],
                                                   Constants.centroArcoSup, rivalesNoPortero);
      valorPorteroFueraDelArco = 55 + (30 * Constants.centroArcoSup
          .distance(porteroRival) / (1.4 * Constants.DISTANCIA_PENAL));
      if (valorPorteroFueraDelArco > 90) {
        valorPorteroFueraDelArco = 90;
      }
      objetivoEnArco =
          ObjetivoEnArco(porteroRival, sp.ballPosition(), sp.getMyPlayerError(i), distanciaAArco);
      double anguloVertical = AnguloVertical(distanciaAArco, sp
          .myPlayersDetail()[i].getPower());
      dispararAGol = ((valorDistanciaAArco * probDistanciaAArco)
                      + (valorPresenciaRival * probPresenciaRival) + (valorPorteroFueraDelArco
                                                                      * probPorteroFueraDelArco));

      comandos.add(new CommandHitBall(i, objetivoEnArco, 1,
                                      anguloVertical));
    }
    return dispararAGol;
  }

  /**
   * Angulo vertical para la altura del disparo
   *
   * @return double entre 8 y 35
   */
  private double AnguloVertical(Double distanciaAArco, double remate) {
    //TODO angulo disparo
    if (distanciaAArco > 60) {
      return 30;
    }
    double
        anguloVertical =
        2 * (Constants.ALTO_ARCO + 1) / ((Constants.LARGO_CAMPO_JUEGO - distanciaAArco) / 2);
    anguloVertical = Math.atan(anguloVertical);
    anguloVertical =
        anguloVertical + anguloVertical * (1 - Constants.FACTOR_DISMINUCION_VEL_BALON_AIRE);
    anguloVertical = anguloVertical * 180 / Math.PI;
    return anguloVertical;
  }

  /**
   * Objetivo en el arco. Encuentra la mejor colocacion del ballPosition en el arco.
   *
   * @return Posicion dentro del arco.
   */
  private Position ObjetivoEnArco(Position portero, Position balon, double error,
                                  Double distanciaAArco) {
    Position objetivoEnArco;
    double anguloPortero = Constants.centroArcoSup.angle(portero);
    double anguloBalon = Constants.centroArcoSup.angle(balon);
    double diferenciaAngulos;

    anguloPortero = anguloPortero > 0 ? anguloPortero
                                      : 360 + (anguloPortero * 180 / Math.PI);
    anguloBalon = anguloBalon > 0 ? anguloBalon * 180 / Math.PI
                                  : 360 + (anguloBalon * 180 / Math.PI);
    diferenciaAngulos = anguloPortero - anguloBalon;
    if (diferenciaAngulos > 180) {
      diferenciaAngulos = 360 - diferenciaAngulos;
    }
    if (portero.distance(Constants.centroArcoSup) > 11) {
      objetivoEnArco = Constants.centroArcoSup;
    } else {
      double dx = (1 - error) * 6;
      if (diferenciaAngulos > 1.0 && diferenciaAngulos < 25) {
        objetivoEnArco = Constants.posteIzqArcoSup.movePosition(dx,
                                                                0);
      } else if (diferenciaAngulos < -1.0 && diferenciaAngulos > -25) {
        objetivoEnArco = Constants.posteDerArcoSup.movePosition(-dx,
                                                                0);
      } else {
        objetivoEnArco = Constants.centroArcoSup;
      }
    }
    distanciaAArco = balon.distance(objetivoEnArco);
    return objetivoEnArco;
  }

  /**
   * Acarrear el ballPosition. Le da un valor a la posibilidad de regate teniendo en cuenta la
   * presencia de rivalPlayers.
   *
   * @return double entre 0 a 100
   */
  private double AcarrearBalon(GameSituations sp) {
    double acarrearBalon = 0;
    double valorPresenciaRival;
    double probPresenciaRival = 0.45;
    double valorPosicionY;
    double probPosicionY = 0.40;
    double valorPosicionX;
    double probPosicionX = 0.15;
    double potenciaAcarreo;
    double avanceY = 9.5;
    Position[] jugadores = sp.myPlayers();
    Position balon = sp.ballPosition();
    double anguloBalon = Constants.centroArcoSup.angle(balon);
    anguloBalon = anguloBalon > 0 ? anguloBalon * 180 / Math.PI
                                  : 360 + (anguloBalon * 180 / Math.PI);

    Position[] rivalesNoPortero = sp.rivalPlayers().clone();
    rivalesNoPortero[0] = rivalesNoPortero[1];
    for (int i : sp.canKick()) {
      if (true) {//i != 0 && !sp.isStarts()) {
        Position destino = PosicionDestinoAcarreo(sp, jugadores[i]);
        valorPresenciaRival = 100 - PresenciaRivales(jugadores[i],
                                                     destino, rivalesNoPortero);
        valorPosicionY = 81 - (26 * (Constants.centroArcoSup
                                         .distance(jugadores[i])) / Constants.LARGO_CAMPO_JUEGO);
        valorPosicionX = 50 + (30 * Math.abs(jugadores[i].getX()) /
                               (Constants.ANCHO_CAMPO_JUEGO / 2));
        acarrearBalon = ((valorPresenciaRival * probPresenciaRival) +
                         (valorPosicionY * probPosicionY) +
                         (valorPosicionX * probPosicionX));
        if (avanceY < 5) {
          acarrearBalon = acarrearBalon * 0.81;
        }
        if (acarrearBalon > MINIMO_ACARREAR_BALON) {
          if (darPase <= PASE_MORTAL) {
            potenciaAcarreo = PotenciaPase(avanceY, sp
                .myPlayersDetail()[i].getPower(), sp.isStarts(), true);
            if (dispararAGol <= DISPARO_MORTAL) {
              comandos.add(new CommandHitBall(i, destino,
                                              potenciaAcarreo, false));
              anguloEspacio = jugadores[i].angle(destino);
              distanciaEspacio = jugadores[i].distance(destino);
            } else if (acarrearBalon > ACARREO_MORTAL) {
              comandos.add(new CommandHitBall(i, destino,
                                              potenciaAcarreo, false));
              anguloEspacio = jugadores[i].angle(destino);
              distanciaEspacio = jugadores[i].distance(destino);
            }
          }
        }
      }
    }
    return acarrearBalon;
  }

  /**
   * Encuentra una posicion o direccion para acarrear el ballPosition.
   *
   * @return Posicion de destino para el acarreo
   */
  private Position PosicionDestinoAcarreo(GameSituations sp, Position origen) {
    double avance = 7.0;
    double angulo = -Math.PI / 8;
    int cantDirecciones = 32;
    double valorPresenciaRival;
    double probPresenciaRival = 0.35;
    double valorCercaniaAlArcoRival;
    double probCercaniaAlArcoRival = 0.50;
    double valorLlegarPrimero;
    double probLlegarPrimero = 0.15;
    double posicionDestinoAcarreo = 0;
    double actualPosicionDestinoAcarreo;
    Position destino = origen;
    Position actualDestino;

    Position[] rivalesNoPortero = sp.rivalPlayers().clone();
    rivalesNoPortero[0] = rivalesNoPortero[1];
    if (origen.angle(Constants.centroArcoSup) > -Math.PI / 4 ||
        origen.angle(Constants.centroArcoSup) < -3 * Math.PI / 4) {
      probPresenciaRival -= 0.04;
      probCercaniaAlArcoRival += 0.01;
      probLlegarPrimero -= 0.06;
    }
    for (int i = 0; i < cantDirecciones; i++) {
      angulo += (Math.PI / cantDirecciones) + (2 * Math.PI / 8) / cantDirecciones;
      actualDestino = origen.moveAngle(angulo, avance);
      if (actualDestino.isInsideGameField(-3)) {
        valorPresenciaRival = 90 - PresenciaRivales(origen, actualDestino, rivalesNoPortero);
        valorCercaniaAlArcoRival = CercaniaAlArcoRival(actualDestino);
        valorLlegarPrimero = LlegarPrimero(sp, actualDestino);
        actualPosicionDestinoAcarreo = ((valorPresenciaRival * probPresenciaRival) +
                                        (valorCercaniaAlArcoRival * probCercaniaAlArcoRival) +
                                        (valorLlegarPrimero * probLlegarPrimero));

        if (actualPosicionDestinoAcarreo > posicionDestinoAcarreo) {
          destino = actualDestino;
          posicionDestinoAcarreo = actualPosicionDestinoAcarreo;
        }
      }
    }
    return destino;
  }

  /**
   * Se desmarcan todos los jugadores excepto los defensas y el portero Modifica comandos.
   */
  private void Desmarcarse(GameSituations sp) {
    int avance = 7;
    Position destino;
    Position[] jugadores = sp.myPlayers();
    int[] indicesJugadores = sp.ballPosition().nearestIndexes(
        sp.myPlayers(), 0, 1, 2, 3);
    int j = 1;

    for (int i : indicesJugadores) {
      if (i == 10) {
        int a = 0;
        a++;
      }
      if (j > JUGADORES_A_DESMARCARSE) {
        break;
      }
      j++;
      double angulo = 180 * desmarcandoseDireccion[i] / Math.PI;
      destino = jugadores[i].moveAngle(angulo, avance);
      double distanciaDesplazada = formacionActual[i]
          .distance(jugadores[i]);
      int jugadorCercano = jugadores[i].nearestIndexes(jugadores)[1];
      double minimaCercania = jugadores[i]
          .distance(jugadores[jugadorCercano]);
      if (i != jugadorDestino && i != 0 && i != 1 && i != 3) {
        if (desmarcandoseIteracion[i] > 0
            && distanciaDesplazada < RADIO_DE_DESPLAZAMIENTO_MAXIMO
            && minimaCercania > MINIMA_CERCANIA_A_JUGADOR
            && destino.isInsideGameField(-4)
            && sp.ballPosition().nearestIndex(jugadores) != i) {
          comandos.add(new CommandMoveTo(i, destino));
          desmarcandoseIteracion[i] -= 1;
        } else {
          int iter = desmarcandoseIteracion[i];
          desmarcandoseIteracion[i] = DESMARCANDOSE_ITERACIONES_MAXIMO;
          if (jugadorDestino != -1) {
            destino = PosicionBalonAlEspacio(sp, jugadorDestino,
                                             jugadores[jugadorDestino], i, jugadores[i], sp
                    .rivalPlayers(), 1.4,
                                             RADIO_DE_DESPLAZAMIENTO_MAXIMO, true);
          } else {
            destino = PosicionBalonAlEspacio(sp, -1, sp.ballPosition(), i,
                                             jugadores[i], sp.rivalPlayers(), 1.4,
                                             RADIO_DE_DESPLAZAMIENTO_MAXIMO, true);
          }
          desmarcandoseDireccion[i] = jugadores[i].angle(destino);
          if (iter == 0) {
            iter = 0;
          }
        }
      }
    }
  }

  /**
   * Detiene los movimientos de desmarcaje colocando la cantidad de iteraciones faltantes a 0
   */
  private void LimpiarDesmarcandoseIteraciones() {
    for (int i = 0; i < desmarcandoseIteracion.length; i++) {
      desmarcandoseIteracion[i] = 0;
    }
  }

  /**
   * Indica a los jugadores tomar una posicion de formacion segun la posicion del ballPosition y
   * quien este atacando.
   */
  private void HacerFormacion(GameSituations sp) {
    Position[] jugadores = sp.myPlayers();
    formacionActual = getPosicionMedia();

    if (sp.ballPosition().getY() > DISTANCIA_BALON_ATAQUE) {
      if (!sp.isRivalStarts() && posecionBalon) {
        formacionActual = getPosicionAtaque();
      }
    } else if (sp.ballPosition().getY() < DISTANCIA_BALON_DEFENSA) {
      if (!sp.isStarts() && !posecionBalon) {
        formacionActual = getPosicionDefensa();
      }
    }
    for (int i = 0; i < jugadores.length; i++) {
      comandos.add(new CommandMoveTo(i, formacionActual[i]));
    }
  }
}
