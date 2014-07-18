/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.pringaos2011;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerI;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author adou
 */
public class Pringaos2011 implements Tactic {

  Position alineacion5[] = new Position[]
      {
          new Position(0.2595419847328244, -50.41044776119403),
          new Position(-11.16030534351145, -35.78358208955224),
          new Position(12.717557251908397, -35.26119402985075),
          new Position(28.290076335877863, -28.470149253731343),
          new Position(-28.290076335877863, -28.470149253731343),
          new Position(14.793893129770993, -18.544776119402986),
          new Position(-17.389312977099234, -19.58955223880597),
          new Position(0.0, -15.0),
          new Position(5.969465648854961, -5.485074626865671),
          new Position(0.2595419847328244, -0.26119402985074625),
          new Position(15.0, -1.0)
      };

  Position alineacion6[] = new Position[]
      {
          new Position(0.2595419847328244, -50.41044776119403),
          new Position(-11.16030534351145, -35.78358208955224),
          new Position(12.717557251908397, -35.26119402985075),
          new Position(28.290076335877863, -28.470149253731343),
          new Position(-28.290076335877863, -28.470149253731343),
          new Position(14.793893129770993, -18.544776119402986),
          new Position(-17.389312977099234, -19.58955223880597),
          new Position(0.0, -15.0),
          new Position(6.4885496183206115, -6.529850746268657),
          new Position(-6.4885496183206115, -6.529850746268657),
          new Position(15.0, -1.0)
      };

  class TacticaDetalleImpl implements Team {

    @Override
    public String getName() {
      return "Los Pringaos";
    }

    @Override
    public CountryCode getCountryCode() {
      return CountryCode.ES;
    }

    @Override
    public String getCoachName() {
      return "Sito";
    }

    @Override
    public Color getShirtColor() {
      return new Color(255, 0, 0);
    }

    @Override
    public Color getShortsColor() {
      return new Color(47, 66, 203);
    }

    @Override
    public Color getShirtLineColor() {
      return new Color(255, 255, 255);
    }

    @Override
    public Color getSocksColor() {
      return new Color(255, 0, 0);
    }

    @Override
    public Color getGoalKeeper() {
      return new Color(255, 255, 0);
    }

    @Override
    public UniformStyle getStyle() {
      return UniformStyle.LINEAS_VERTICALES;
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
      return new Color(255, 0, 0);
    }

    @Override
    public Color getSocksColor2() {
      return new Color(0, 0, 0);
    }

    @Override
    public Color getGoalKeeper2() {
      return new Color(255, 255, 0);
    }

    @Override
    public UniformStyle getStyle2() {
      return UniformStyle.SIN_ESTILO;
    }

    class JugadorImpl implements PlayerI {

      String nombre;
      int numero;
      Color piel, pelo;
      double velocidad, remate, precision;
      boolean portero;
      Position posicion;

      public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                         double velocidad, double remate, double precision, boolean portero) {
        this.nombre = nombre;
        this.numero = numero;
        this.piel = piel;
        this.pelo = pelo;
        this.velocidad = velocidad;
        this.remate = remate;
        this.precision = precision;
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
        return precision;
      }

    }

    @Override
    public PlayerI[] getPlayers() {
      return new PlayerI[]{
          new JugadorImpl("Calamardo", 1, new Color(255, 200, 150), new Color(255, 200, 150), 1.0d,
                          1.0d, 0.67d, true),
          new JugadorImpl("Bob", 2, new Color(255, 102, 102), new Color(255, 200, 150), 1.0d, 1.0d,
                          0.5d, false),
          new JugadorImpl("Patricio", 3, new Color(255, 200, 150), new Color(255, 200, 150), 1.0d,
                          1.0d, 0.5d, false),
          new JugadorImpl("Billy", 4, new Color(255, 200, 150), new Color(255, 200, 150), 1.0d,
                          1.0d, 0.56d, false),
          new JugadorImpl("Scooby", 5, new Color(255, 200, 150), new Color(255, 200, 150), 0.71d,
                          0.7d, 0.49d, false),
          new JugadorImpl("Calimero", 6, new Color(255, 200, 150), new Color(255, 200, 150), 0.67d,
                          1.0d, 0.69d, false),
          new JugadorImpl("Burt", 7, new Color(255, 200, 150), new Color(255, 200, 150), 0.67d,
                          1.0d, 0.69d, false),
          new JugadorImpl("Hommer", 8, new Color(255, 200, 150), new Color(255, 200, 150), 0.75d,
                          1.0d, 0.64d, false),
          new JugadorImpl("Lisa", 9, new Color(255, 200, 150), new Color(255, 200, 150), 0.87d,
                          1.0d, 0.79d, false),
          new JugadorImpl("Mandy", 10, new Color(255, 200, 150), new Color(255, 200, 150), 0.85d,
                          1.0d, 0.75d, false),
          new JugadorImpl("Berni", 11, new Color(255, 200, 150), new Color(255, 200, 150), 1.0d,
                          1.0d, 1.0d, false)
      };
    }
  }

  class PuntoPase {

    double distancia; // distancia desde el punto de origen
    double puntuacion;
    Position p;
  }

  Team detalle = new TacticaDetalleImpl();

  @Override
  public Team getDetail() {
    return detalle;
  }


  public static final int ESTADO_EQUIPO_DEFENSA = 1;
  public static final int ESTADO_EQUIPO_ATAQUE = 2;

  public static final int TIPO_JUGADOR_PORTERO = 0;
  public static final int TIPO_JUGADOR_DEFENSA = 1;
  public static final int TIPO_JUGADOR_ATAQUE = 2;

  public final static double MITAD_ANCHO = Constants.ANCHO_CAMPO_JUEGO / 2;
  public final static double MITAD_LARGO = Constants.LARGO_CAMPO_JUEGO / 2;

  public int estado_equipo = ESTADO_EQUIPO_DEFENSA;
  public int estado_equipo_anterior = estado_equipo;

  public int tipo_jugadores[] = {TIPO_JUGADOR_PORTERO,
                                 TIPO_JUGADOR_DEFENSA,
                                 TIPO_JUGADOR_DEFENSA,
                                 TIPO_JUGADOR_DEFENSA,
                                 TIPO_JUGADOR_DEFENSA,
                                 TIPO_JUGADOR_DEFENSA,
                                 TIPO_JUGADOR_ATAQUE,
                                 TIPO_JUGADOR_ATAQUE,
                                 TIPO_JUGADOR_ATAQUE,
                                 TIPO_JUGADOR_ATAQUE,
                                 TIPO_JUGADOR_ATAQUE};
  public Position[] misJugadores;
  public Position[] rivales;
  public PlayerI[] detalleMisJugadores;
  public PlayerI[] detalleRivales;
  public LinkedList<Command> comandos = new LinkedList<>();
  public boolean[] rivales_defendidos = new boolean[11];
  public double[][] posicionesPase;
  public double distanciaOptimaPase = 20.0;


  public double getFuerza(Position desde, Position hasta, int jugador, double anguloI,
                          double limiteAltura) {
    int i;
    double fuerzaRemate = 1.05;
    double vel;
    AbstractTrajectory trayectoria;
    double time;
    double desplazamientoHorizontal;
    double desplazamientoVertical = limiteAltura + 1.0;
    double desplazamientoTotal = desde.distance(hasta);
    double x = desde.getX(), x0;
    double y = desde.getY(), y0;
    double aux;
    double distancia;
    double distanciaRival;
    double probabilidad = 0;
    double angulo = anguloI * Math.PI / 180d;

    while ((fuerzaRemate > 0) && (probabilidad < 0.9)) {
      fuerzaRemate -= 0.05;
      i = 1;
      vel = fuerzaRemate * Constants.getVelocidadRemate(detalleMisJugadores[jugador].getPower());
      trayectoria = new AirTrajectory(Math.cos(angulo) * vel, Math.sin(angulo) * vel, 0, 0);
      desplazamientoHorizontal = 0;
      distancia = 0;
      aux = 9999;
      while ((distancia != aux) && (distancia < desplazamientoTotal)) {
        time = (double) i / 60d;
        aux = distancia;
        desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        desplazamientoVertical = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        x0 = x;
        y0 = y;
        x = desde.getX() + desplazamientoHorizontal * Math.cos(desde.angle(hasta) * Math.PI / 180d);
        y = desde.getY() + desplazamientoHorizontal * Math.sin(desde.angle(hasta) * Math.PI / 180d);
        distancia = desde.distance(new Position(x, y));
        vel = Math.sqrt((x - x0) * (x - x0) + (y - y0) * (y - y0));
        probabilidad = (7d - vel) / 7d;
        // si la probabilidad de interceptar el ballPosition es mayor que el 60% y la altura del balón es menor que el límite -> miro si llega algún rival a este punto
        if ((probabilidad >= 0.5) && (desplazamientoVertical <= limiteAltura)) {
          for (int f = 0; f < 11; f++) {
            distanciaRival = rivales[f].distance(new Position(x, y));
            if ((int) (distanciaRival / detalleRivales[f].getSpeed()) <= i) {
              return -1;
            }
          }
        }
        i++;
      }
      // si aux es igual a desplazamientoHorizontal es que no llega el pase
      if (distancia == aux) {
        return -1;
      }
    }

    if (desplazamientoVertical > limiteAltura) {
      return -1;
    }

    return fuerzaRemate;
  }

  public boolean dentroCampo(Position p) {
    return (p.getX() >= -MITAD_ANCHO && p.getX() <= MITAD_ANCHO &&
            p.getY() >= -MITAD_LARGO && p.getY() <= MITAD_LARGO);
  }

  public double[][] calculaPosicionesPase(int pasador) {
    double[][] posiciones;
    int x, y, i, f;
    Position origen;

    posiciones =
        new double[16
                   * 10][7]; // distancia al punto origen, valor del pase, coordenada x, coordenada y, angulo, fuerza, jugador destino
    i = 0;
    for (f = 1; f < 11; f++) {
      if (f != pasador) {
        origen = misJugadores[f];
        for (x = (int) (origen.getX() - 10); x < (int) origen.getX() + 10; x += 5) {
          for (y = (int) (origen.getY() - 10); y < (int) origen.getY() + 10; y += 5) {
            posiciones[i][2] = x;
            posiciones[i][3] = y;
            posiciones[i][4] = 0;
            posiciones[i][5] = 0;
            posiciones[i][6] = f;
            if (!dentroCampo(new Position(x, y)) || ((x == origen.getX()) && (y == origen
                .getY()))) {
              posiciones[i][0] = -1;
              posiciones[i][1] = -1;
            } else {
              posiciones[i][0] = 0; //origen.distancia(new Posicion(x, y));
              posiciones[i][1] = 0;
            }
            //System.out.println("------- Indice Pase: " + i + " + Punto Pase: (" + x + ", " + y + ")" + " + Jugador Destino: " + (int)(posiciones[i][6] + 1));
            i++;
          }
        }
      }
    }

    return posiciones;
  }

  public int calculaValorPase(int jugador, Position origen) {
    int i;
    double valorMejorPase = 0;
    int indiceMejorPase = 0;
    double fuerza;
    double angulo;
    double distanciaPorteria;
    Position posicion;

    for (i = 0; i < 16 * 10; i++) {
      if (posicionesPase[i][1] == -1) {
        continue;
      }

      posicion = new Position(posicionesPase[i][2], posicionesPase[i][3]);
      angulo = 0;
      fuerza = this.getFuerza(origen, posicion, jugador, angulo, Constants.ALTURA_CONTROL_BALON);
/*            while((fuerza == -1) && (angulo < Constants.ANGULO_VERTICAL_MAX)) {
                angulo += 5;
                fuerza = this.getFuerza(origen, posicion, jugador, angulo, Constants.ALTURA_CONTROL_BALON);
            }*/

      if (fuerza == -1) {
        posicionesPase[i][1] = -1;
        continue;
      } else {
        posicionesPase[i][4] = angulo;
        posicionesPase[i][5] = fuerza;
      }

//            if (posicionesPase[i][0] < distanciaOptimaPase) posicionesPase[i][1] += 0.5; // Es un pase cerca

//            if (posicionesPase[i][3] > 0) posicionesPase[i][1] += 0.25; // es un pase a campo contrario

      distanciaPorteria = posicion.distance(Constants.centroArcoSup);
      posicionesPase[i][1] += 1 / distanciaPorteria;

      if (valorMejorPase < posicionesPase[i][1]) {
        indiceMejorPase = i;
        valorMejorPase = posicionesPase[i][1];
      }
    }

    return indiceMejorPase;
  }

  private boolean pendienteNoInfinita(Position a, Position b) {
    double denominador = a.getX() - b.getX();
    return (denominador != 0);
  }

  private double calculoPendiente(Position a, Position b) {
    double m = a.getY() - b.getY();
    if (m != 0) {
      double denominador = a.getX() - b.getX();
      if (denominador != 0) {
        m = m / denominador;
      }
    }
    if (m == Double.NaN) {
      return 0;
    } else {
      return m;
    }
  }

  private double calculoInterseccionEjeY(Position a, Position b, double pendiente) {
    return (a.getY() - pendiente * a.getX());
  }

  public void moverPortero(Position pBalonAnterior, Position balon) {
    // Movimientos portero
    double mPortero, nPortero;
    double xPortero;
    double yPortero;
    double mBalon, nBalon;

    // Se calcula la recta que sigue el ballPosition
    double xAux;
    if (this.pendienteNoInfinita(pBalonAnterior, balon)) {
      mBalon = this.calculoPendiente(pBalonAnterior, balon);
      nBalon = this.calculoInterseccionEjeY(pBalonAnterior, balon, mBalon);

      xAux = (Constants.posteIzqArcoInf.getY() - nBalon) / mBalon;
    } else {
      mBalon = Double.POSITIVE_INFINITY;
      nBalon = pBalonAnterior.getX();
      xAux = balon.getX();
    }

    if ((xAux >= Constants.posteIzqArcoInf.getX()) && (xAux <= Constants.posteDerArcoInf.getX())) {
      yPortero = Constants.centroArcoInf.getY() + Constants.DISTANCIA_CONTROL_BALON_PORTERO;
      xPortero = (yPortero - nBalon) / mBalon;
    } else if (this.pendienteNoInfinita(
        new Position(Constants.centroArcoInf.getX(), Constants.centroArcoInf.getY() - 1), balon)) {
      mPortero =
          this.calculoPendiente(balon, new Position(Constants.centroArcoInf.getX(),
                                                    Constants.centroArcoInf.getY() - 1));
      nPortero =
          this.calculoInterseccionEjeY(balon, new Position(Constants.centroArcoInf.getX(),
                                                           Constants.centroArcoInf.getY() - 1),
                                       mPortero);

      yPortero = Constants.centroArcoInf.getY() + Constants.DISTANCIA_CONTROL_BALON_PORTERO;
      xPortero = (yPortero - nPortero) / mPortero;

      if (xPortero < Constants.posteIzqArcoInf.getX() - 1.5) {
        xPortero = Constants.posteIzqArcoInf.getX() - 1.5;
      }
      if (xPortero > Constants.posteDerArcoInf.getX() + 1.5) {
        xPortero = Constants.posteDerArcoInf.getX() + 1.5;
      }
    } else {
      yPortero = Constants.centroArcoInf.getY() + Constants.DISTANCIA_CONTROL_BALON_PORTERO;
      xPortero = balon.getX();
      if (xPortero < Constants.posteIzqArcoInf.getX() - 1.5) {
        xPortero = Constants.posteIzqArcoInf.getX() - 1.5;
      }
      if (xPortero > Constants.posteDerArcoInf.getX() + 1.5) {
        xPortero = Constants.posteDerArcoInf.getX() + 1.5;
      }
    }

    comandos.add(new CommandMoveTo(0, new Position(xPortero, yPortero)));
  }

  public boolean autoPase(int p, Position balon) {
    double[][] posiciones = new double[8][3];
    int i, f;
    double distancia;
    double distanciaMinima;
    double aux, aux1;
    int indice;

    if (detalleMisJugadores[p].isGoalKeeper()) {
      return false;
    }

    distancia = detalleMisJugadores[p].getSpeed() * Constants.ITERACIONES_GOLPEAR_BALON;
    posiciones[0][0] = misJugadores[p].getX() + distancia;
    posiciones[0][1] = misJugadores[p].getY();
    posiciones[1][0] = misJugadores[p].getX();
    posiciones[1][1] = misJugadores[p].getY() + distancia;
    posiciones[2][0] = misJugadores[p].getX() - distancia;
    posiciones[2][1] = misJugadores[p].getY();
    posiciones[3][0] = misJugadores[p].getX();
    posiciones[3][1] = misJugadores[p].getY() - distancia;
    posiciones[4][0] = misJugadores[p].getX() + (distancia / 2);
    posiciones[4][1] = misJugadores[p].getY() + (distancia / 2);
    posiciones[5][0] = misJugadores[p].getX() - (distancia / 2);
    posiciones[5][1] = misJugadores[p].getY() + (distancia / 2);
    posiciones[6][0] = misJugadores[p].getX() + (distancia / 2);
    posiciones[6][1] = misJugadores[p].getY() - (distancia / 2);
    posiciones[7][0] = misJugadores[p].getX() - (distancia / 2);
    posiciones[7][1] = misJugadores[p].getY() - (distancia / 2);

    aux1 = 0;
    indice = 0;
    for (i = 0; i < 7; i++) {
      distanciaMinima = 999;
      for (f = 0; f < 11; f++) {
        aux = rivales[f].distance(new Position(posiciones[i][0], posiciones[i][1]));
        if (aux < distanciaMinima) {
          posiciones[i][2] = aux;
          aux = distanciaMinima;
        }
      }
      if (aux1 > posiciones[i][2]) {
        aux1 = posiciones[i][2];
        indice = i;
      }
    }

    if (posiciones[indice][2] < distancia) {
      return false;
    }

    double
        fuerza =
        this.getFuerza(balon, new Position(posiciones[indice][0], posiciones[indice][1]), p, 0,
                       Constants.ALTURA_CONTROL_BALON);
    comandos.add(
        new CommandHitBall(p, new Position(posiciones[indice][0], posiciones[indice][1]), fuerza,
                           0));
    comandos.add(new CommandMoveTo(p, new Position(posiciones[indice][0], posiciones[indice][1])));

    return true;
  }

  public boolean tiroAPuerta(int p, Position pos) {
    double inc;
    int i;
    double fuerza;

    inc = Constants.LARGO_ARCO / 5;
    for (i = 0; i < 5; i++) {
      fuerza =
          this.getFuerza(pos, new Position(Constants.posteIzqArcoSup.getX() + (inc * i),
                                           Constants.posteIzqArcoSup.getY()), p, 0,
                         Constants.ALTO_ARCO);
      if (fuerza != -1) {
        comandos.add(new CommandHitBall(p,
                                        new Position(Constants.posteIzqArcoSup.getX() + (inc * i),
                                                     Constants.posteIzqArcoSup.getY()), fuerza, 0));
        return true;
      }
    }

    return false;
  }

  public void mueveDelanteros(Position balon) {
    if (balon.getY() < 0) {
      if (balon.getX() > 15) {
        comandos.add(new CommandMoveTo(5, new Position(balon.getX() - 45, balon.getY() + 10)));
        comandos.add(new CommandMoveTo(6, new Position(balon.getX(), balon.getY() + 10)));
        comandos.add(new CommandMoveTo(7, new Position(10.0, balon.getY() + 20)));
        comandos.add(new CommandMoveTo(8, new Position(balon.getX(), balon.getY() + 20)));
        comandos.add(new CommandMoveTo(9, new Position(balon.getX() - 20, balon.getY() + 30)));
      } else if (balon.getX() < -15) {
        comandos.add(new CommandMoveTo(5, new Position(balon.getX(), balon.getY() + 10)));
        comandos.add(new CommandMoveTo(6, new Position(balon.getX() + 45, balon.getY() + 10)));
        comandos.add(new CommandMoveTo(7, new Position(-10.0, balon.getY() + 20)));
        comandos.add(new CommandMoveTo(8, new Position(balon.getX() + 20, balon.getY() + 20)));
        comandos.add(new CommandMoveTo(9, new Position(balon.getX(), balon.getY() + 30)));
      } else {
        comandos.add(new CommandMoveTo(5, new Position(balon.getX() - 20, balon.getY() + 10)));
        comandos.add(new CommandMoveTo(6, new Position(balon.getX() + 20, balon.getY() + 10)));
        comandos.add(new CommandMoveTo(7, new Position(balon.getX(), balon.getY() + 20)));
        comandos.add(new CommandMoveTo(8, new Position(balon.getX() + 20, balon.getY() + 30)));
        comandos.add(new CommandMoveTo(9, new Position(balon.getX() - 20, balon.getY() + 30)));
      }
      comandos.add(new CommandMoveTo(10, new Position(balon.getX(), balon.getY() + 50)));
    } else {
      double yAux = balon.getY() + 30;
      if (yAux > Constants.LARGO_CAMPO_JUEGO / 2.0) {
        yAux = (Constants.LARGO_CAMPO_JUEGO / 2.0) - 10;
      }
      if (balon.getX() > 15) {
        comandos.add(new CommandMoveTo(5, new Position(balon.getX() - 55, balon.getY() - 5)));
        comandos.add(new CommandMoveTo(6, new Position(balon.getX(), balon.getY() - 5)));
        comandos.add(new CommandMoveTo(7, new Position(10.0, balon.getY() - 15)));
        comandos.add(new CommandMoveTo(8, new Position(balon.getX() - 5, yAux)));
        comandos.add(
            new CommandMoveTo(9, new Position(Constants.posteIzqArcoSup.getX() + 1, balon.getY())));
      } else if (balon.getX() < -15) {
        comandos.add(new CommandMoveTo(5, new Position(balon.getX(), balon.getY() - 5)));
        comandos.add(new CommandMoveTo(6, new Position(balon.getX() + 55, balon.getY() - 5)));
        comandos.add(new CommandMoveTo(7, new Position(-10.0, balon.getY() - 15)));
        comandos.add(
            new CommandMoveTo(8, new Position(Constants.posteDerArcoSup.getX() - 1, balon.getY())));
        comandos.add(new CommandMoveTo(9, new Position(balon.getX() + 5, yAux)));
      } else {
        comandos.add(new CommandMoveTo(5, new Position(balon.getX() - 20, balon.getY() - 5)));
        comandos.add(new CommandMoveTo(6, new Position(balon.getX() + 20, balon.getY() - 5)));
        comandos.add(new CommandMoveTo(7, new Position(balon.getX(), balon.getY() - 15)));
        comandos.add(new CommandMoveTo(8, new Position(balon.getX() + 10, yAux)));
        comandos.add(new CommandMoveTo(9, new Position(balon.getX() - 10, yAux)));
      }
      int portero = 0;
      for (int i = 0; i < 11; i++) {
        if (detalleRivales[i].isGoalKeeper()) {
          portero = i;
        }
      }
      comandos.add(new CommandMoveTo(10, new Position(rivales[portero].getX(),
                                                      rivales[portero].getY() - 4.9)));
    }

  }

  @Override
  public List<Command> execute(GameSituations sp) {
    int i, j, aux;
    double distancia, distancia_aux, x, y;
    int indicePase;

    // control de jugadores
    misJugadores = sp.myPlayers();
    rivales = sp.rivalPlayers();
    detalleMisJugadores = sp.myPlayersDetail();
    detalleRivales = sp.rivalPlayersDetail();

    comandos.clear();

    // Establecemos estado del equipo
    if ((sp.isRivalStarts()) || ((sp.rivalCanKick().length > 0) && (sp.canKick().length == 0))) {
      estado_equipo_anterior = estado_equipo;
      estado_equipo = ESTADO_EQUIPO_DEFENSA;
    } else {
      estado_equipo_anterior = estado_equipo;
      estado_equipo = ESTADO_EQUIPO_ATAQUE;
    }

    // PORTERO
    this.moverPortero(sp.ballPosition(),
                      new Position(sp.getTrajectory(1)[0], sp.getTrajectory(1)[1]));

    // DEFENSAS
    for (i = 0; i < 11; i++) {
      rivales_defendidos[i] = false;
    }

    for (i = 0; i < 11; i++) {
      if (tipo_jugadores[i] == TIPO_JUGADOR_DEFENSA) {
        distancia = 999;
        aux = 10;
        for (j = 10; j >= 0; j--) {
          distancia_aux =
              Math.sqrt(
                  Math.pow(sp.rivalPlayers()[j].getX() - Constants.centroArcoInf.getX(), 2) + Math
                      .pow(sp.rivalPlayers()[j].getY() - Constants.centroArcoInf.getY(), 2));
          if ((distancia_aux < distancia) && (!rivales_defendidos[j])) {
            distancia = distancia_aux;
            aux = j;
          }
        }
        rivales_defendidos[aux] = true;

        x = sp.rivalPlayers()[aux].getX();
        if (sp.ballPosition().getY() > sp.rivalPlayers()[aux].getY()) {
          y = sp.rivalPlayers()[aux].getY() + 2;
        } else {
          y = sp.rivalPlayers()[aux].getY() - 2;
        }
        comandos.add(new CommandMoveTo(i, new Position(x, y)));
      } else if (tipo_jugadores[i] == TIPO_JUGADOR_ATAQUE) {
        mueveDelanteros(sp.ballPosition());
      }
    }

    if (!sp.isRivalStarts()) {
      //Obtiene los datos de recuperacion del ballPosition
      int[] recuperadores = sp.getRecoveryBall();
      //Si existe posibilidad de recuperar el ballPosition
      if (recuperadores.length > 1) {
        //Obtiene las coordenadas del ballPosition en el instante donde
        //se puede recuperar el ballPosition
        double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
        for (i = 1; i < recuperadores.length; i++) {
          //Ordena a los jugadores recuperadores que se ubique
          //en la posicion de recuperacion
          comandos.add(new CommandMoveTo(recuperadores[i],
                                         new Position(posRecuperacion[0], posRecuperacion[1])));
        }
      }
    }

    //Instancia un generador aleatorio
    Random r = new Random();
    //Recorre la lista de mis jugadores que pueden rematar
    for (int p : sp.canKick()) {
      if (!tiroAPuerta(p, sp.ballPosition())) {
        posicionesPase = calculaPosicionesPase(p);
        indicePase = calculaValorPase(p, sp.ballPosition());

        if (posicionesPase[indicePase][1] == -1) {
          if (!autoPase(p, sp.ballPosition())) {
            // despejo el balón
            comandos.add(new CommandHitBall(p, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));

            //     System.out.println("******** Despejo Balon!!!!!");
          }
        } else {
          comandos.add(new CommandHitBall(p, new Position(posicionesPase[indicePase][2],
                                                          posicionesPase[indicePase][3]),
                                          posicionesPase[indicePase][5],
                                          posicionesPase[indicePase][4]));
          comandos.add(new CommandMoveTo((int) posicionesPase[indicePase][6],
                                         new Position(posicionesPase[indicePase][2],
                                                      posicionesPase[indicePase][3])));

          //   System.out.println("------- Indice Pase: " + indicePase + " + Origen: " + (p + 1) + " + Destino: " + ((int)(posicionesPase[indicePase][6]) + 1) + " + Fuerza: " + posicionesPase[indicePase][5] + " + Angulo: " + posicionesPase[indicePase][4]);
        }
      }
    }

    return comandos;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return alineacion5;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
  }

}
