/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.Mou_team;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.trajectory.FloorTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Administrador
 */
public class Tactica_Mou_Team {

  class posicionTiro implements Comparable<posicionTiro> {

    private int indice;
    private double probabilidad;

    public posicionTiro(int indice, double probabilidad) {
      this.indice = indice;
      this.probabilidad = probabilidad;
    }

    @Override
    public int compareTo(posicionTiro o) {
      if (getProbabilidad() == o.getProbabilidad()) {
        return 0;
      } else if (getProbabilidad() < o.getProbabilidad()) {
        return 1;
      } else {
        return -1;
      }
    }

    @Override
    public String toString() {
      return getProbabilidad() + " en el indice " + getIndice();
    }

    /**
     * @return the indice
     */
    public int getIndice() {
      return indice;
    }

    /**
     * @return the probabilidad
     */
    public double getProbabilidad() {
      return probabilidad;
    }
  }

  private GameSituations sp;
  private Position alineacionDefecto[] = new Position[]{ //ALINEACION NORMAL
                                                         new Position(0, 42), //delantero,
                                                         new Position(-16, -26), //central izq
                                                         new Position(0, -26), // central
                                                         new Position(16, -26), //central derecho
                                                         new Position(12, 0),//medio derecho
                                                         new Position(-12, 0), //medio izquierdo
                                                         new Position(0, 0), // medio centro
                                                         new Position(15, 27), //extremo derecho
                                                         new Position(-15, 27), // extremo izquierdo
                                                         new Position(0, 20), //media punta
                                                         new Position(0, -52) // portero
  };
  private Position alineacion1[] = new Position[]{//CUANDO ME METEN GOL
                                                  new Position(-5, 42), //delantero,
                                                  new Position(-14, -26), //central izq
                                                  new Position(0, -23), // central
                                                  new Position(14, -26), //central derecho
                                                  new Position(12, -14),//medio derecho
                                                  new Position(-12, -14), //medio izquierdo
                                                  new Position(0, -9), // medio centro
                                                  new Position(9, 0), //extremo derecho
                                                  new Position(-9, 0), // extremo izquierdo
                                                  new Position(0, 0), //media punta
                                                  new Position(0, -52) // portero
  };
  private Position alineacion2[] = new Position[]{ //CUANDO HE METIDO GOL
                                                   new Position(0, 0), //delantero,
                                                   new Position(-13, -34), //central izq
                                                   new Position(0, -32), // central
                                                   new Position(13, -34), //central derecho
                                                   new Position(11, -20),//medio derecho
                                                   new Position(-11, -20), //medio izquierdo
                                                   new Position(0, -18), // medio centro
                                                   new Position(9, 0), //extremo derecho
                                                   new Position(-9, 0), // extremo izquierdo
                                                   new Position(0, -6), //media punta
                                                   new Position(0, -52) // portero
  };

  private Position alineacionSaque[] = new Position[]{ //SAQUE DE PUERTA
                                                       new Position(0, 20), //delantero,
                                                       new Position(-13, -34), //central izq
                                                       new Position(0, -32), // central
                                                       new Position(13, -34), //central derecho
                                                       new Position(11, -20),//medio derecho
                                                       new Position(-11, -20), //medio izquierdo
                                                       new Position(0, -18), // medio centro
                                                       new Position(9, 0), //extremo derecho
                                                       new Position(-9, 0), // extremo izquierdo
                                                       new Position(0, -6), //media punta
                                                       new Position(0, -52) // portero
  };

  private Position alineacion3[] = new Position[]{ //CUANDO EMPIEZA EL PARTIDO
                                                   new Position(0, 0), //delantero,
                                                   new Position(-13, -34), //central izq
                                                   new Position(0, -32), // central
                                                   new Position(13, -34), //central derecho
                                                   new Position(11, -20),//medio derecho
                                                   new Position(-11, -20), //medio izquierdo
                                                   new Position(0, 0), // medio centro
                                                   new Position(9, 9), //extremo derecho
                                                   new Position(-9, 0), // extremo izquierdo
                                                   new Position(0, 0), //media punta
                                                   new Position(0, -52) // portero
  };
  private int porteroContrario = -1;
  private int golesContrario;
  private int golesMios;
  private Position posAnteriorPortero;
  private Position posAnteriorBalon;
  private Position posAnteriorBalon2;
  private double[] posSiguienteBalon;
  private double alturaAnteriorBalon;
  public int golCulpaMiaDistancia;
  public int golCulpaMiaAltura;
  private double angConvert = Math.PI / 180d;

  public Position[] getStartPositions(GameSituations sp) { //he recibido gol
    return this.alineacion1;
  }

  public Position[] getNoStartPositions(GameSituations sp) { //he marcado gol o de inicio
    if (this.sp == null || this.sp.myGoals() == 0) {

      return alineacion3;


    } else {
      return alineacion2;
    }
  }

  public Tactica_Mou_Team() {
  }


  public Tactica_Mou_Team(double prob, double prob2) {

  }

  public List<Command> generarTactica(GameSituations sp) {
    this.sp = sp;
    this.buscarPorteroContrario();

        /* if (this.sp.myGoals()>this.golesMios){
        golesMios++;
        System.out.println("gooollllllllllll");
        }*/

        /*  if (this.sp.iterationsToKick()[0] != 0) {
        System.out.println("me kedan " + this.sp.iterationsToKick()[0] + " para rematar");
        }*/
    //System.out.println("IT " + (this.sp.iteration()) + ": " + this.sp.ballPosition());

    LinkedList<Command> listaComandos = new LinkedList<>();

    moverJugadoresEnFuncionBalon(sp, listaComandos);
    listaComandos = this.moverPortero(listaComandos);
    listaComandos = irAPorBalon(listaComandos);

    //listaComandos = this.golpearBalon(listaComandos);
    listaComandos = this.defensaGolpearBalon(listaComandos);
    listaComandos = this.porteroGolpearBalon(listaComandos);
    listaComandos = this.pivotesGolpearBalon(listaComandos);
    listaComandos = this.extremoIzquierdoGolpearBalon(listaComandos);
    listaComandos = this.delanteroGolpearBalon(listaComandos);
    listaComandos = this.mediaPuntaGolpearBalon(listaComandos);
    listaComandos = this.extremoDchaGolpearBalon(listaComandos);

    listaComandos = this.irAlSaqueDeCentro(listaComandos);
    listaComandos = this.sacarDeCentro(listaComandos);
    listaComandos = this.saquesBanda(listaComandos);
    listaComandos = this.moverJugadoresSaquePuerta(listaComandos);




        /*boolean encontrado;
        for (int i : this.sp.canKick()) {
        encontrado=false;
        for (Comando c:listaComandos)
        {
        if (c instanceof CommandHitBall)
        {
        if (c.getIndJugador()==i)
        {
        encontrado=true;
        }
        }
        }
        if (!encontrado)
        {
        System.out.println("El jugador "+i+" es estupido y no remata");
        }
        }*/

    //me guardo valores
    this.posAnteriorBalon = this.sp.ballPosition();
    this.alturaAnteriorBalon = this.sp.getTrajectory(0)[2];
    this.posAnteriorPortero = this.sp.myPlayers()[10];
    //System.out.println("........FIN ITERACION......");

    //System.out.println("...................");
    //eliminarComandos(listaComandos, 0, 9, 8, 7);
    return listaComandos;
  }

  public LinkedList<Command> moverJugadoresSaquePuerta(LinkedList<Command> listaComandos) {
    if (this.sp.isStarts() && this.sp.ballPosition().getY() == -47
        && Math.abs(this.sp.ballPosition().getX()) == 9.16) {

      for (int n = 0; n < 10; n++) {
        listaComandos.add(new CommandMoveTo(n, this.alineacionSaque[n]));
      }

    }

    return listaComandos;
  }

  public LinkedList<Command> saquesBanda(LinkedList<Command> listaComandos) {

    Position pasar;
    double dist;
    double angulo;
    if (!this.sp.isStarts()) {
      return listaComandos;
    }
    if (this.sp.ballPosition().getY() > 25) {
      if (this.sp.ballPosition().getX() < 0) {
        pasar = new Position(-3, 43);

      } else {
        pasar = new Position(3, 43);
      }

      dist = this.sp.ballPosition().distance(pasar);
      for (int i = 1; i < 10; i++) {
        if (this.buscarNumEnArray(this.sp.canKick(), i, 0, 100) != -1) { //el defensa puede rematar
          angulo =
              this.calcularAnguloVerticalLlegarJusto(dist, this.sp.myPlayersDetail()[i].getPower());
          listaComandos.add(new CommandHitBall(i, pasar, 1, angulo));
        }
      }
    }

    return listaComandos;
  }

  private void moverJugadoresEnFuncionBalon(GameSituations sp, List<Command> comandos) {

    double coenfAdX = sp.ballPosition().getX();
    double coenfAdY = sp.ballPosition().getY();

    Position posicionesActuales[] = {
        new Position(this.control(0 + coenfAdX, -10, 10), this.control(42 + coenfAdY, 31, 42)),
        //delantero,
        new Position(this.control(-8 + coenfAdX, -25, 5), this.control(-26 + coenfAdY, -37, -20)),
        //central izq
        new Position(this.control(0 + coenfAdX, -20, 20), this.control(-26 + coenfAdY, -41, 0)),
        // central
        new Position(this.control(8 + coenfAdX, -5, 25), this.control(-26 + coenfAdY, -37, -20)),
        //central derecho
        new Position(this.control(12 + coenfAdX, -5, 25), this.control(0 + coenfAdY, -24, 19)),
        //  pivote derecha
        new Position(this.control(-12 + coenfAdX, -25, 5), this.control(0 + coenfAdY, -24, 19)),
        //  pivote izquierda
        new Position(this.control(0 + coenfAdX, -20, 20), this.control(0 + coenfAdY, -27, 15)),
        //  pivote
        new Position(this.control(12 + coenfAdX, 0, 20), this.control(27 + coenfAdY, 0, 42)),
        //  extremo derecho
        new Position(this.control(-12 + coenfAdX, -20, 0), this.control(27 + coenfAdY, 0, 42)),
        //  extremo izquierdo
        new Position(this.control(0 + coenfAdX, -15, 15), this.control(20 + coenfAdY, -10, 34)),
        // media punta
        new Position(this.control(sp.ballPosition().getX(), -(Constants.LARGO_ARCO / 2) + 1.3,
                                  (Constants.LARGO_ARCO / 2) - 1.3), -52)}; //  PORTERO

        /*Posicion port = new Posicion(0, -52.5);
        if (port.distancia(this.sp.rivalPlayers()[port.indiceMasCercano(this.sp.rivalPlayers())]) < 30) {
        //System.out.println("entroaa");
        //posicionesActuales[2] = new Posicion(this.control(0 + coenfAdX, -20, 20), this.control(-26 + coenfAdY, -35, this.sp.rivalPlayers()[port.indiceMasCercano(this.sp.rivalPlayers())].getY() + 10)); //central
        //posicionesActuales[6] = new Posicion(this.control(0 + coenfAdX, -20, 20), this.control(0 + coenfAdY, -27, 0)); //central
        //posicionesActuales[1] = new Posicion(this.control(-8 + coenfAdX, -25, 5), this.control(-26 + coenfAdY, -37, -20)); //central
        //posicionesActuales[3] = new Posicion(this.control(8 + coenfAdX, -5, 25), this.control(-26 + coenfAdY, -37, -20)); //central
        }*/

    for (int n = 0; n < 11; n++) {
      comandos.add(new CommandMoveTo(n, posicionesActuales[n]));
    }
  }

  private LinkedList<Command> moverPortero(LinkedList<Command> listaComandos) {
    if (this.sp.rivalGoals() > this.golesContrario) {
      // System.out.println(" GOLLLL: el ballPosition estaba en: " + this.posAnteriorBalon + " " + (this.alturaAnteriorBalon <= Constants.ALTO_ARCO) + " distancia: " + this.posAnteriorBalon.distancia(this.posAnteriorPortero));
      //System.out.println("el portero estaba en: " + this.posAnteriorPortero);
      this.golesContrario++;

      if (this.posAnteriorBalon.distance(this.posAnteriorPortero)
          > Constants.DISTANCIA_CONTROL_BALON_PORTERO) {
        //System.out.println("--- culpa mia por   DISTANCIA");
        this.golCulpaMiaDistancia++;
      } else if (this.alturaAnteriorBalon >= Constants.ALTO_ARCO) {
        this.golCulpaMiaAltura++;
      }
    }

    int it;
    double[] trayectoria;

    if (this.sp.isStarts() && this.sp.ballPosition().getY() == -47
        && Math.abs(this.sp.ballPosition().getX()) == 9.16) {
      listaComandos.add(new CommandMoveTo(10, new Position(this.sp.ballPosition().getX(),
                                                           this.sp.ballPosition().getY())));
    } else if (this.sp.ballPosition().getY() > -22.5) { //ballPosition lejos
      listaComandos.add(new CommandMoveTo(10, new Position(
          this.control(this.sp.ballPosition().getX(), -(Constants.LARGO_ARCO / 2) + 1.5,
                       (Constants.LARGO_ARCO / 2) - 1.5), -48)));
    } else if ((it = this.itInteresaSalirPortero()) > -1) {
      // System.out.println("salgo: "+this.sp.iteration());
      trayectoria = this.sp.getTrajectory(it);
      listaComandos.add(new CommandMoveTo(10, new Position(trayectoria[0], trayectoria[1])));
    } else if ((it = this.itBalonCercaGol()) != -1) {
      //System.out.println("intento parar gol: "+this.sp.iteration());
      trayectoria = this.sp.getTrajectory(it);
      listaComandos.add(new CommandMoveTo(10, new Position(trayectoria[0], trayectoria[1])));
    } else {
      //System.out.println("mov standar: "+this.sp.iteration());
      listaComandos.add(new CommandMoveTo(10, new Position(
          this.control(this.sp.ballPosition().getX(), -(Constants.LARGO_ARCO / 2) + 1.5,
                       (Constants.LARGO_ARCO / 2) - 1.5), -48)));
    }
    return listaComandos;
  }

  //calcula las iteraciones que tardará el ballPosition en recorrer una distancia concreta
  private int iteracionesTardar(int fuerzaRemate, int remateJugador, double gradosVert,
                                double distancia) {
    int it;

    for (it = 1; it < 35; it++) {
      double angVer = gradosVert / (Math.PI * 180);
      double vel = fuerzaRemate * Constants.getVelocidadRemate(remateJugador);
      AbstractTrajectory
          trayectoria =
          new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
      double time = (double) it / 60d;
      double
          desplazamientoHorizontal =
          trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;

      if (desplazamientoHorizontal + 1 > distancia) {
        break;
      }
    }

    return it;
  }

  private double calcularAnguloVerticalRapido(double dist, double remateJugador,
                                              double minAlturaDestino, double maxAlturaDestino) {

    double fuerzaRemate = 1;
    double angVer, vel, time, desplazamientoHorizontal;
    AbstractTrajectory trayectoria;
    double gradosMinimos = -1;
    double gradosMinimosConAltura = -1;
    double desplazamientoAnterior;
    double desplazamientoVertical;

    fin:
    for (int iteracion = 1; iteracion < 200; iteracion++) {
      //System.out.println(iteration);
      desplazamientoAnterior = -1;
      for (double grados = 0; grados <= 60; grados += 0.5) {
        angVer = grados * Math.PI / 180;
        vel = fuerzaRemate * Constants.getVelocidadRemate(remateJugador);
        trayectoria = new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
        time = (double) iteracion / 60d;
        desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        desplazamientoVertical = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        if (desplazamientoHorizontal >= dist) {
          if (desplazamientoVertical >= minAlturaDestino
              && desplazamientoVertical <= maxAlturaDestino) {
            gradosMinimosConAltura = grados;
            //System.out.println("grados a lanzar: "+grados+" llegara a una altura de: "+desplazamientoVertical);
            break fin;
          }
          if (gradosMinimos == -1) {
            gradosMinimos = grados;
          }
        }

                /*if (desplazamientoHorizontal < desplazamientoAnterior) // a medida que sumamos mas grados el desplazamiento es inferior
                {
                break;
                } else {
                desplazamientoAnterior = desplazamientoHorizontal;
                }*/
      }
    }

    if (gradosMinimosConAltura > 0) {
      gradosMinimos = gradosMinimosConAltura;
    } else if (gradosMinimos < 0) {
      gradosMinimos = 0;
    }
    //System.out.println("grados a lanzar 2:  "+gradosMinimos);

    return gradosMinimos;
  }

  //calcula el angulo vertical perfecto para k justo le llegue el ballPosition en la altura exacta
  private double calcularAnguloVerticalLlegarJusto(double dist, double fuerzaJugador) {
    double grados = 0;
    double dif = 100;

    fuera:
    for (double i = 0; i < 60; i += 0.5) {
      for (int iteracion = 0; iteracion < 80; iteracion++) {
        double fuerzaRemate = 1;
        double angVer = i * Math.PI / 180;
        double vel = fuerzaRemate * Constants.getVelocidadRemate(fuerzaJugador);
        AbstractTrajectory
            trayectoria = new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
        double time = (double) iteracion / 60d;
        double
            desplazamientoHorizontal =
            trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double
            desplazamientoVertical =
            trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        if (Math.abs(desplazamientoHorizontal - dist) <= Constants.DISTANCIA_CONTROL_BALON) {
          if (desplazamientoVertical <= Constants.ALTURA_CONTROL_BALON) {
            if ((Constants.ALTURA_CONTROL_BALON - desplazamientoVertical) < dif) {
              dif = Constants.ALTURA_CONTROL_BALON - desplazamientoVertical;
              grados = i;
              if (Constants.ALTURA_CONTROL_BALON - desplazamientoVertical < 0.15) {
                break fuera;
              }
            }
          }
        } else if (desplazamientoHorizontal > (dist + Constants.DISTANCIA_CONTROL_BALON)) {
          break;
        }

      }
    }

    return grados;
  }

  private int itBalonCercaGol() {
    int iteracion = -1;
    for (int it = 1; it < 82 && iteracion == -1; it++) {
      if (Math.abs(this.sp.getTrajectory(it)[0]) < ((Constants.LARGO_ARCO + 1) / 2)
          && this.sp.getTrajectory(it)[1]
             <= -52.5) { //esto seria gol por lo que me tengo que quedar con la iteration anterior
        iteracion = it - 1;
      }
    }
    return iteracion;
  }

  private int itInteresaSalirPortero() {
    int it;
    int itYoLlegoElNo = -1;
    int itYoLlegoElSi = -1;
    boolean elLlega;
    boolean yoLlego;

    Position posBalon;
    double velPortero = Constants.getVelocidad(this.sp.myPlayersDetail()[10].getSpeed());
    Position posPortero = this.sp.myPlayers()[10];
    int i;
    for (i = 1; i < 100; i++) {

      if (this.sp.getTrajectory(i)[2] > Constants.ALTO_ARCO) {
        continue;
      }

      posBalon = new Position(this.sp.getTrajectory(i)[0], this.sp.getTrajectory(i)[1]);

      if (!posBalon.isInsideGameField(0)) {
        break;
      }

      elLlega = this.llegaRivalATiempoChutar(posBalon, i);
      yoLlego =
          posBalon.distance(posPortero) <= ((velPortero * i)
                                            + Constants.DISTANCIA_CONTROL_BALON_PORTERO);

      if (yoLlego && !elLlega) { //el portero puede llegar
        if (itYoLlegoElSi != -1) {
          break;
        }
        itYoLlegoElNo = i;

      } else if (yoLlego && elLlega) {
        if (itYoLlegoElNo != -1) {
          break;
        }
        itYoLlegoElSi = i;
      } else if (!yoLlego && elLlega) {
        break;
      }
    }
//System.out.println(i);
    if (itYoLlegoElNo != -1) {
      it = itYoLlegoElNo;

    } else if (itYoLlegoElSi != -1) {

      it = itYoLlegoElSi;
    } else {

      it = -1;
    }

    return it;
  }

  private LinkedList<Command> irAPorBalon(LinkedList<Command> listaComandos) {
    int[] primerMomentoChute = this.primerMomentoChutar(10);
    int tamanio = primerMomentoChute.length;
    Position posFuturaBalon;
    Position posTemp;
    boolean elLlegaAntes = false;

    if (primerMomentoChute[0] != 0) {
      posFuturaBalon =
          new Position(this.sp.getTrajectory(primerMomentoChute[0])[0],
                       this.sp.getTrajectory(primerMomentoChute[0])[1]);

      //vemos a ver si el contrario puede ir antes a por el ballPosition
      for (int it = 1; it < primerMomentoChute[0] && !elLlegaAntes; it++) {
        posTemp = new Position(this.sp.getTrajectory(it)[0], this.sp.getTrajectory(it)[1]);
        if (this.llegaRivalATiempoChutar(posTemp, it)) //mandamos solo a un jugador
        {
          elLlegaAntes = true;
        }
      }

      if (elLlegaAntes) {
        listaComandos.add(new CommandMoveTo(primerMomentoChute[1], posFuturaBalon));
      } else {
        if (this.llegaRivalATiempoChutar(posFuturaBalon, primerMomentoChute[0])) {
          if (posFuturaBalon.distance(new Position(0, 52.5)) < 18
              || posFuturaBalon.distance(new Position(0, -52.5)) < 18) {
            for (int i = 0; i < tamanio && primerMomentoChute[i] != 0; i += 2) {
              listaComandos.add(new CommandMoveTo(primerMomentoChute[i + 1], posFuturaBalon));
              //System.out.println("El jugador "+primerMomentoChute[1]+" va a ir a "+posFuturaBalon+ " donde la altura es "+(this.sp.getTrajectory(primerMomentoChute[0])[2]<=Constants.ALTURA_CONTROL_BALON));
            }
          } else {
            for (int i = 0; i < 10 && primerMomentoChute[i] != 0; i += 2) {
              if (primerMomentoChute[i + 1] == 0 && i != 1) {
                continue;
              }
              listaComandos.add(new CommandMoveTo(primerMomentoChute[i + 1], posFuturaBalon));
              //System.out.println("El jugador "+primerMomentoChute[1]+" va a ir a "+posFuturaBalon+ " donde la altura es "+(this.sp.getTrajectory(primerMomentoChute[0])[2]<=Constants.ALTURA_CONTROL_BALON));
            }
          }
        } else {
          listaComandos.add(new CommandMoveTo(primerMomentoChute[1], posFuturaBalon));
        }
      }

    }

    return listaComandos;
  }

  //devuela la iteration y el jugador que primero podrá chutar
  private int[] primerMomentoChutar(int... jugsExcluidos) {
    int[] primerMomento = new int[20];
    Arrays.fill(primerMomento, 0);
    double velJugador;
    double distanciaJugBalon;
    Position balonFuturo;
    int cont = 0;

    fin:
    for (int it = 1; it < 200 && primerMomento[0] == 0; it++) {
      if (this.sp.getTrajectory(it)[2] <= Constants.ALTURA_CONTROL_BALON) {
        for (int i = 9; i >= 0; i--) {
          if (this.buscarNumEnArray(jugsExcluidos, i, 0, 15) == -1) {
            velJugador = Constants.getVelocidad(this.sp.myPlayersDetail()[i].getSpeed());
            balonFuturo = new Position(this.sp.getTrajectory(it)[0], this.sp.getTrajectory(it)[1]);
            distanciaJugBalon = balonFuturo.distance(this.sp.myPlayers()[i]);
            if (distanciaJugBalon <= (velJugador * it + Constants.DISTANCIA_CONTROL_BALON)) {
              primerMomento[cont] = it;
              primerMomento[cont + 1] = i;
              cont += 2;
            }
          }
        }
      }

    }

    if (primerMomento[0] == 0) {
      //System.out.println("no la puedo cojer");
    } else if (primerMomento[2] != 0) {
      //System.out.println("varios la pueden cojer "+cont/2);
    }
    return primerMomento;
  }

  //devuelve si algun rival puede llegar a tiempo en una iteration a una posicion para chutar
  private boolean llegaRivalATiempoChutar(Position pos, int it) {
    boolean llega = false;

    for (int i = 0; !llega && i < 11; i++) {
      if (this.sp.rivalIterationsToKick()[i] - it > 0) // el rival no puede rematar
      {
        continue;
      }

      if (this.sp.rivalPlayersDetail()[i].isGoalKeeper()) {
        if (this.sp.getTrajectory(it)[2] <= Constants.ALTO_ARCO
            && this.sp.rivalPlayers()[i].distance(pos) <= (
            (Constants.getVelocidad(this.sp.rivalPlayersDetail()[i].getSpeed()) * it)
            + Constants.DISTANCIA_CONTROL_BALON_PORTERO)) {
          llega = true;
        }

      } else {
        if (this.sp.getTrajectory(it)[2] <= Constants.ALTURA_CONTROL_BALON
            && this.sp.rivalPlayers()[i].distance(pos) <= (
            (Constants.getVelocidad(this.sp.rivalPlayersDetail()[i].getSpeed()) * it)
            + Constants.DISTANCIA_CONTROL_BALON)) {
          llega = true;
        }
      }
    }

    return llega;
  }

  private boolean interceptariaContrarioRegate(Player jug, Position posJug,
                                               Position posDestino) {
    if (this.sp.isStarts()) //para evitar que se regatee de saque de banda
    {
      return true;
    }
    double angDireccion = posJug.angle(posDestino);
    double vel = Constants.getVelocidad(jug.getSpeed()) + .2;
    AbstractTrajectory trayectoria = new FloorTrajectory(vel * 3.5, 0);
    double time, desplazamientoHorizontal;
    double nuevaX, nuevaY;
    Position posNueva;
    boolean yoLlego = false;
    boolean elLlega = false;

    for (int it = 0; it < 150; it++) {
      time = (double) it / 60d;
      desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
      nuevaX = this.sp.ballPosition().getX() + desplazamientoHorizontal * Math.cos(angDireccion);
      nuevaY = this.sp.ballPosition().getY() + desplazamientoHorizontal * Math.sin(angDireccion);
      posNueva = new Position(nuevaX, nuevaY);

      if (posNueva.isInsideGameField(0)) {
        if (it > 10 && posJug.distance(posNueva) <= (Constants.getVelocidad(jug.getSpeed()) * it)
                                                    + Constants.DISTANCIA_CONTROL_BALON) {
          yoLlego = true;

        }

        for (int i = 0; !elLlega && i < 11; i++) {
          if (this.sp.rivalIterationsToKick()[i] - it > 0) // el rival no puede rematar
          {
            continue;
          }
          if (this.sp.rivalPlayersDetail()[i].isGoalKeeper()) {
            if (this.sp.rivalPlayers()[i].distance(posNueva) <= (
                (Constants.getVelocidad(this.sp.rivalPlayersDetail()[i].getSpeed()) * it)
                + Constants.DISTANCIA_CONTROL_BALON_PORTERO)) {
              elLlega = true;
            }

          } else {
            if (this.sp.rivalPlayers()[i].distance(posNueva) <= (
                (Constants.getVelocidad(this.sp.rivalPlayersDetail()[i].getSpeed()) * it)
                + Constants.DISTANCIA_CONTROL_BALON)) {
              elLlega = true;
            }
          }
        }

        if (elLlega || yoLlego) {
          break;
        }
      }
    }

    return elLlega;
  }

  private double control(double numero, double min, double max) {
    if (numero < min) {
      return min;
    }
    if (numero > max) {
      return max;
    }
    return numero;
  }

  private int[] jugadoresMiosMasCercanos(Position pos, int cuantosBuscar, double minX, double maxX,
                                         double minY, double maxY, double distMax,
                                         int... jugsNoIncluir) {
    int[] misJugs = new int[cuantosBuscar];
    Position posJug;
    int cont = 0;
    for (int jug = 0; jug < 11 && cont < cuantosBuscar; jug++) {
      if (this.buscarNumEnArray(jugsNoIncluir, jug, 0, 100) == -1) {
        posJug = this.sp.myPlayers()[jug];
        if (posJug.getX() > minX && posJug.getX() < maxX && posJug.getY() > minY
            && posJug.getY() < maxY && pos.distance(posJug) < distMax) {
          misJugs[cont] = jug;
          cont++;
        }
      }
    }

    return misJugs;
  }

  private int jugMasLibre(int... misJugs) {
    int jugMasLibre = -1;
    int tamanio = misJugs.length;
    int itMax = -1;
    int itMinima;
    int itTemp;
    int jugMin;
    Position posMiJugador;

    for (int i = 0; i < tamanio; i++) {
      posMiJugador = this.sp.myPlayers()[misJugs[i]];

      itMinima = 1000;
      for (int j = 0; j < 11; j++) {
        itTemp =
            (int) Math.ceil(posMiJugador.distance(this.sp.rivalPlayers()[j]) / Constants
                .getVelocidad(this.sp.rivalPlayersDetail()[j].getSpeed()));
        if (itTemp < itMinima) {
          itMinima = itTemp;
          jugMin = j;
        }
      }

      if (itMinima > itMax) {
        itMax = itMinima;
        jugMasLibre = misJugs[i];
      }

    }

    return jugMasLibre;

  }

  private LinkedList<Command> defensaGolpearBalon(LinkedList<Command> listaComandos) {
    int[] jugsAPasar;
    int jugAPasar;
    double dist;
    double angulo;
    Position posDestino = new Position(0, 53);

    for (int i = 1; i < 4; i++) {
      if (this.buscarNumEnArray(this.sp.canKick(), i, 0, 100) != -1) //el defensa puede rematar
      {
        jugsAPasar =
            this.jugadoresMiosMasCercanos(this.sp.myPlayers()[i], 6, -100, 200,
                                          this.sp.myPlayers()[i].getY() + 2, 200, 30, 1, 2, 3);
        //System.out.println(Arrays.toString(jugsAPasar));
        jugAPasar = this.jugMasLibre(jugsAPasar);
        dist = this.sp.myPlayers()[i].distance(this.sp.myPlayers()[jugAPasar]);
        dist = this.sp.ballPosition().distance(this.sp.myPlayers()[jugAPasar]);
        angulo =
            this.calcularAnguloVerticalLlegarJusto(dist, this.sp.myPlayersDetail()[i].getPower());
        listaComandos.add(new CommandHitBall(i, this.sp.myPlayers()[jugAPasar], 1, angulo));
        //System.out.println("el jugador " + (i + 1) + "le pasa a " + (jugAPasar + 1));
      }
    }

    return listaComandos;
  }

  private LinkedList<Command> pivotesGolpearBalon(LinkedList<Command> listaComandos) {
    int[] jugsAPasar;
    int jugAPasar;
    double dist;
    double angulo;
    Position posDestino = new Position(0, 53);
    for (int i = 4; i < 7; i++) {
      if (this.buscarNumEnArray(this.sp.canKick(), i, 0, 100) != -1) //el defensa puede rematar
      {
        boolean corro = false;
        if (this.sp.myPlayers()[i].getY() < 35) {

          if (i == 4) { // medio derecha
            for (int j = 6; j > -6 && !corro; j--) {
              if (!this.interceptariaContrarioRegate(this.sp.myPlayersDetail()[i],
                                                     this.sp.myPlayers()[i], new Position(j, 53))
                  && !this.sp.isStarts()) {
                listaComandos.add(new CommandMoveTo(i, new Position(j, 53)));
                listaComandos.add(new CommandHitBall(i));
                corro = true;
                //System.out.println("avanzoo a " + new Posicion(j, 53));
              }
            }
          } else {
            for (int j = -6; j < 6 && !corro; j++) {
              if (!this.interceptariaContrarioRegate(this.sp.myPlayersDetail()[i],
                                                     this.sp.myPlayers()[i], new Position(j, 53))
                  && !this.sp.isStarts()) {
                listaComandos.add(new CommandMoveTo(i, new Position(j, 53)));
                listaComandos.add(new CommandHitBall(i));

                corro = true;
                //System.out.println("avanzoo a " + new Posicion(j, 53));
              }
            }

          }
        }
        if (this.sp.myPlayers()[i].getY() >= 35 || !corro) {
          jugsAPasar =
              this.jugadoresMiosMasCercanos(this.sp.myPlayers()[i], 6, -100, 200,
                                            this.sp.myPlayers()[i].getY() + 7, 200, 30, 1, 2, 3,
                                            10);
          //System.out.println(Arrays.toString(jugsAPasar));
          jugAPasar = this.jugMasLibre(jugsAPasar);
          dist = this.sp.ballPosition().distance(this.sp.myPlayers()[jugAPasar]);
          angulo =
              this.calcularAnguloVerticalLlegarJusto(dist, this.sp.myPlayersDetail()[i].getPower());
          listaComandos.add(new CommandHitBall(i, this.sp.myPlayers()[jugAPasar], 1, angulo));
          //System.out.println("el jugador " + (i + 1) + "le pasa a " + (jugAPasar + 1));
        }

      }
    }

    return listaComandos;
  }

  private LinkedList<Command> porteroGolpearBalon(LinkedList<Command> listaComandos) {
    int[] jugsAPasar;
    int jugAPasar;
    double dist;
    double angulo;

    if (this.buscarNumEnArray(this.sp.canKick(), 10, 0, 100) != -1) //el defensa puede rematar
    {
      jugsAPasar =
          this.jugadoresMiosMasCercanos(this.sp.myPlayers()[10], 6, -100, 200,
                                        this.sp.myPlayers()[10].getY() + 10, 200, 40, 0);
      //System.out.println(Arrays.toString(jugsAPasar));
      jugAPasar = this.jugMasLibre(jugsAPasar);
      dist = this.sp.ballPosition().distance(this.sp.myPlayers()[jugAPasar]);
      angulo =
          this.calcularAnguloVerticalLlegarJusto(dist, this.sp.myPlayersDetail()[10].getPower());
      listaComandos.add(new CommandHitBall(10, this.sp.myPlayers()[jugAPasar], 1, angulo));
      //System.out.println("el portero le pasa a " + (jugAPasar + 1));
    }

    return listaComandos;
  }

  private LinkedList<Command> mediaPuntaGolpearBalon(LinkedList<Command> listaComandos) {
    if (this.buscarNumEnArray(this.sp.canKick(), 9, 0, 100) == -1) {
      return listaComandos;
    }

    double mejorAngulo;
    int jugMasLibre;
    Position yo = this.sp.myPlayers()[9];
    Position delantero = this.sp.myPlayers()[0];
    Position extremoIzq = this.sp.myPlayers()[8];
    Position extremoDcha = this.sp.myPlayers()[7];
    Position porteria = new Position(0, 52.5);
    double distanciaDelanteroYo = delantero.distance(yo);
    double
        distDelanteroDefensa =
        delantero.distance(this.sp.rivalPlayers()[delantero.nearestIndex(this.sp.rivalPlayers())]);
    double
        distExtremoIzqDefensa =
        extremoIzq
            .distance(this.sp.rivalPlayers()[extremoIzq.nearestIndex(this.sp.rivalPlayers())]);
    double
        distExtremoDchaDefensa =
        extremoDcha
            .distance(this.sp.rivalPlayers()[extremoDcha.nearestIndex(this.sp.rivalPlayers())]);
    double distDelanteroPorteria = delantero.distance(porteria);
    if (distDelanteroDefensa > 7 && distDelanteroPorteria < 16 && distanciaDelanteroYo < 32) {
      mejorAngulo =
          this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                 this.sp.myPlayersDetail()[9].getPower());
      listaComandos.add(new CommandHitBall(9, this.sp.myPlayers()[0], 1, mejorAngulo));
    } else if (this.sp.ballPosition().distance(porteria) < 17) {
      listaComandos.add(this.tirarAPorteria(9));
      //System.out.println("tira el medio punta");
    } else {
      boolean corro = false;
      for (int j = -4; j < 4 && !corro; j++) {
        if (!this.interceptariaContrarioRegate(this.sp.myPlayersDetail()[9], this.sp.myPlayers()[9],
                                               new Position(j, 53))) {
          listaComandos.add(new CommandMoveTo(9, new Position(j, 53)));
          listaComandos.add(new CommandHitBall(9));
          corro = true;
          //      System.out.println("media punta avanza a " + new Posicion(j, 53));
        }
      }

      if (!corro) {
        if (distExtremoIzqDefensa > 10 && distExtremoDchaDefensa > 10) {
          jugMasLibre = this.jugMasLibre(7, 8);
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(
                  this.sp.ballPosition().distance(this.sp.myPlayers()[jugMasLibre]),
                  this.sp.myPlayersDetail()[9].getPower());
          listaComandos
              .add(new CommandHitBall(9, this.sp.myPlayers()[jugMasLibre], 1, mejorAngulo));
        } else if (distDelanteroDefensa > 5 && distDelanteroPorteria < 20
                   && distanciaDelanteroYo < 40) {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                     this.sp.myPlayersDetail()[9].getPower());
          listaComandos.add(new CommandHitBall(9, this.sp.myPlayers()[0], 1, mejorAngulo));
        } else if (distExtremoIzqDefensa > 5 && distExtremoDchaDefensa > 5) {
          jugMasLibre = this.jugMasLibre(7, 8);
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(
                  this.sp.ballPosition().distance(this.sp.myPlayers()[jugMasLibre]),
                  this.sp.myPlayersDetail()[9].getPower());
          listaComandos
              .add(new CommandHitBall(9, this.sp.myPlayers()[jugMasLibre], 1, mejorAngulo));
        } else {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                     this.sp.myPlayersDetail()[9].getPower());
          listaComandos.add(new CommandHitBall(9, this.sp.myPlayers()[0], 1, mejorAngulo));
        }
      }
    }

    return listaComandos;
  }

  private LinkedList<Command> delanteroGolpearBalon(LinkedList<Command> listaComandos) {

    if (this.buscarNumEnArray(this.sp.canKick(), 0, 0, 30) != -1) {
      Position posPorteria = new Position(0, 52.5);
      if (this.sp.ballPosition().getY() < 30 && !this
          .interceptariaContrarioRegate(this.sp.myPlayersDetail()[0], this.sp.myPlayers()[0],
                                        posPorteria)) {
        listaComandos.add(new CommandMoveTo(0, posPorteria));
        listaComandos.add(new CommandHitBall(0));
        //System.out.println("AVANZO en la it " + this.sp.iteration());
      } else {
        listaComandos.add(this.tirarAPorteria(0));
      }
      listaComandos.add(this.tirarAPorteria(0));
    }

    return listaComandos;

  }

  private LinkedList<Command> extremoIzquierdoGolpearBalon(LinkedList<Command> listaComandos) {
    if (this.buscarNumEnArray(this.sp.canKick(), 8, 0, 100) == -1) {
      return listaComandos;
    }
    Position extIzq = this.sp.myPlayers()[8];
    Position extDcho = this.sp.myPlayers()[7];
    Position delantero = this.sp.myPlayers()[0];
    Position mediaPunta = this.sp.myPlayers()[9];
    Position porteria = new Position(0, 52.5);
    double
        distDelanteroDefensa =
        delantero.distance(this.sp.rivalPlayers()[delantero.nearestIndex(this.sp.rivalPlayers())]);
    double
        distMediaPuntaDefensa =
        mediaPunta
            .distance(this.sp.rivalPlayers()[mediaPunta.nearestIndex(this.sp.rivalPlayers())]);
    double mejorAngulo;

    if (extIzq.distance(porteria) < 26 && (this.sp.ballPosition().getY() < 49
                                           || this.sp.ballPosition().getX() > -9)) {
      listaComandos.add(this.tirarAPorteria(8));

    } else if (delantero.distance(porteria) < 17 && distDelanteroDefensa > 7) {
      mejorAngulo =
          this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                 this.sp.myPlayersDetail()[8].getPower());
      listaComandos.add(new CommandHitBall(8, delantero, 1, mejorAngulo));

    } else {
      boolean corro = false;
      for (int j = -6; j < 6 && !corro; j++) {
        if (!this.interceptariaContrarioRegate(this.sp.myPlayersDetail()[8], this.sp.myPlayers()[8],
                                               new Position(j, 53))) {
          listaComandos.add(new CommandMoveTo(8, new Position(j, 53)));
          listaComandos.add(new CommandHitBall(8));
          corro = true;
          //System.out.println("extremo izq avanza a " + new Posicion(j, 53));
        }
      }

      if (!corro) {
        if (extIzq.distance(porteria) < 29 && (this.sp.ballPosition().getY() < 49
                                               || this.sp.ballPosition().getX() > -9)) {
          listaComandos.add(this.tirarAPorteria(8));

        } else if (delantero.distance(porteria) < 20 && distDelanteroDefensa > 4) {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                     this.sp.myPlayersDetail()[8].getPower());
          listaComandos.add(new CommandHitBall(8, delantero, 1, mejorAngulo));

        } else if (distMediaPuntaDefensa > 16) {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(mediaPunta),
                                                     this.sp.myPlayersDetail()[8].getPower());
          listaComandos.add(new CommandHitBall(8, mediaPunta, 1, mejorAngulo));

        } else if (extIzq.distance(porteria) < 32 && (this.sp.ballPosition().getY() < 49
                                                      || this.sp.ballPosition().getX() > -9)) {
          listaComandos.add(this.tirarAPorteria(8));

        } else {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                     this.sp.myPlayersDetail()[8].getPower());
          listaComandos.add(new CommandHitBall(8, delantero, 1, mejorAngulo));
        }
      }
    }

    return listaComandos;
  }

  private LinkedList<Command> extremoDchaGolpearBalon(LinkedList<Command> listaComandos) {
    if (this.buscarNumEnArray(this.sp.canKick(), 7, 0, 100) == -1) {
      return listaComandos;
    }
    Position extIzq = this.sp.myPlayers()[8];
    Position extDcho = this.sp.myPlayers()[7];
    Position delantero = this.sp.myPlayers()[0];
    Position mediaPunta = this.sp.myPlayers()[9];
    Position porteria = new Position(0, 52.5);
    double
        distDelanteroDefensa =
        delantero.distance(this.sp.rivalPlayers()[delantero.nearestIndex(this.sp.rivalPlayers())]);
    double
        distMediaPuntaDefensa =
        mediaPunta
            .distance(this.sp.rivalPlayers()[mediaPunta.nearestIndex(this.sp.rivalPlayers())]);
    double mejorAngulo;

    if (extDcho.distance(porteria) < 26 && (this.sp.ballPosition().getY() < 49
                                            || this.sp.ballPosition().getX() < 9)) {
      listaComandos.add(this.tirarAPorteria(7));

    } else if (delantero.distance(porteria) < 17 && distDelanteroDefensa > 7) {
      mejorAngulo =
          this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                 this.sp.myPlayersDetail()[7].getPower());
      listaComandos.add(new CommandHitBall(7, delantero, 1, mejorAngulo));

    } else {
      boolean corro = false;
      for (int j = 6; j > -6 && !corro; j--) {
        if (!this.interceptariaContrarioRegate(this.sp.myPlayersDetail()[7], this.sp.myPlayers()[7],
                                               new Position(j, 53))) {
          listaComandos.add(new CommandMoveTo(7, new Position(j, 53)));
          listaComandos.add(new CommandHitBall(7));
          corro = true;
          //System.out.println("extremo dcha avanza a " + new Posicion(j, 53));
        }
      }
      if (!corro) {
        if (extDcho.distance(porteria) < 29 && (this.sp.ballPosition().getY() < 49
                                                || this.sp.ballPosition().getX() < 9)) {
          listaComandos.add(this.tirarAPorteria(7));

        } else if (delantero.distance(porteria) < 20 && distDelanteroDefensa > 4) {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                     this.sp.myPlayersDetail()[7].getPower());
          listaComandos.add(new CommandHitBall(7, delantero, 1, mejorAngulo));

        } else if (distMediaPuntaDefensa > 16) {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(mediaPunta),
                                                     this.sp.myPlayersDetail()[7].getPower());
          listaComandos.add(new CommandHitBall(7, mediaPunta, 1, mejorAngulo));

        } else if (extDcho.distance(porteria) < 32 && (this.sp.ballPosition().getY() < 49
                                                       || this.sp.ballPosition().getX() < 9)) {
          listaComandos.add(this.tirarAPorteria(7));

        } else {
          mejorAngulo =
              this.calcularAnguloVerticalLlegarJusto(this.sp.ballPosition().distance(delantero),
                                                     this.sp.myPlayersDetail()[7].getPower());
          listaComandos.add(new CommandHitBall(7, delantero, 1, mejorAngulo));
        }
      }
    }

    return listaComandos;
  }

  private LinkedList<Command> sacarDeCentro(LinkedList<Command> listaComandos) {
    double dist, angulo;
    if (this.sp.ballPosition().getX() == 0 && this.sp.ballPosition().getY() == 0
        && this.sp.getTrajectory(1)[0] == 0 && this.sp.getTrajectory(1)[1] == 0
        && this.sp.getTrajectory(1)[2] == 0) {
      for (int jug = 0; jug < 10; jug++) {
        if (this.buscarNumEnArray(this.sp.canKick(), jug, 0, 100) != -1) {
          if (this.sp.myGoals() == 0 && this.sp.rivalGoals() == 0) {
            //System.out.println("empieza1");
            dist = this.sp.ballPosition().distance(this.sp.myPlayers()[8]);
            angulo =
                this.calcularAnguloVerticalLlegarJusto(dist,
                                                       this.sp.myPlayersDetail()[jug].getPower());
            listaComandos.add(new CommandHitBall(jug, this.sp.myPlayers()[8], 1, angulo));
          } else {
            Position pase = this.sp.myPlayers()[6]; //le paso al medio centro
            listaComandos.add(new CommandHitBall(jug, pase, 1, false));
            //System.out.println("paso");
          }


        }
      }
    }

    return listaComandos;
  }

  private LinkedList<Command> irAlSaqueDeCentro(LinkedList<Command> listaComandos) {
    if (this.sp.ballPosition().getX() == 0 && this.sp.ballPosition().getY() == 0
        && this.sp.getTrajectory(1)[0] == 0 && this.sp.getTrajectory(1)[1] == 0
        && this.sp.getTrajectory(1)[2] == 0 && this.sp.myGoals() == 0
        && this.sp.rivalGoals() == 0) {
      listaComandos.add(new CommandMoveTo(6, this.sp.ballPosition()));
      listaComandos.add(new CommandMoveTo(9, this.sp.ballPosition()));
      //System.out.println("empieza2");
    }

    return listaComandos;
  }

  private Command tirarAPorteria(int numJug) {
    double[] angulosPorteria;
    posicionTiro[] puntuacionesPorteria;
    Position posDisparo = new Position(0, 53);
    double anguloVertRapido;

    angulosPorteria = this.generarArrayAngulosAnchoCampo(this.sp.ballPosition());
    puntuacionesPorteria =
        this.generarArrayPuntuacionesGol(angulosPorteria, this.sp.myPlayersDetail()[numJug],
                                         this.sp.ballPosition());
    int cantidadMiniTrozos = (int) Constants.ANCHO_CAMPO * 2;
    double miniTrozo = Constants.ANCHO_CAMPO / cantidadMiniTrozos;
    double mayorDistanciaPortero = -1;
    Position tiro;
    int indice = -1;
    double mayorProbabilidad = puntuacionesPorteria[0].getProbabilidad();
    double margen = 0.5;
    if (this.sp.ballPosition().distance(new Position(0, 52.5)) > 37) {
      margen = 0;
      // System.out.println("entro");
    }
    for (int i = 0; i < cantidadMiniTrozos;
         i++) { //como el array esta ordenado, no hace falta recorrerlo entero
      tiro =
          new Position(
              (-Constants.ANCHO_CAMPO / 2) + (miniTrozo * puntuacionesPorteria[i].getIndice()), 53);
      if (puntuacionesPorteria[i].getProbabilidad() >= (mayorProbabilidad - 0.05)
          || puntuacionesPorteria[i].getProbabilidad() >= 0.9) {
        if (tiro.distance(this.sp.rivalPlayers()[this.porteroContrario]) > mayorDistanciaPortero) {
          mayorDistanciaPortero =
              posDisparo.distance(this.sp.rivalPlayers()[this.porteroContrario]);
          posDisparo = tiro;
          indice = i;

        }
        //System.out.println(i + " " + mayorProbabilidad + " " + puntuacionesPorteria[i].getProbabilidad());
      } else {
        break;
      }
      //System.out.println("entro y seteo a:  "+posDisparo);
    }

    //System.out.print("Probabilidad de gol: "+(puntuacionesPorteria[indice].getProbabilidad()*100)+"%");
    //System.out.println("  DIFERENCIA "+((puntuacionesPorteria[0].getProbabilidad()*100)-(puntuacionesPorteria[indice].getProbabilidad()*100))+"%");

    if (this.tieneMasDeUnPortero()) {

      anguloVertRapido =
          calcularAnguloVerticalRapido(this.sp.ballPosition().distance(posDisparo),
                                       this.sp.myPlayersDetail()[numJug].getPower(), 3.5, 4.7);

    } else {
      anguloVertRapido =
          calcularAnguloVerticalRapido(this.sp.ballPosition().distance(posDisparo),
                                       this.sp.myPlayersDetail()[numJug].getPower(), 0, 100);
    }
    Command comando = new CommandHitBall(numJug, posDisparo, 1, anguloVertRapido);

    return comando;
  }

  private double[] generarArrayAngulosAnchoCampo(Position posBalon) {
    int cantidadMiniTrozos = (int) Constants.ANCHO_CAMPO * 2;
    double miniTrozo = Constants.ANCHO_CAMPO / cantidadMiniTrozos;
    double[] arrayAngulos = new double[cantidadMiniTrozos];
    Position posDisparo;
    for (int i = 0; i < cantidadMiniTrozos; i++) {
      posDisparo = new Position((-Constants.ANCHO_CAMPO / 2) + (miniTrozo * i), 53);
      arrayAngulos[i] = posBalon.angle(posDisparo);
    }
    return arrayAngulos;

  }

  private posicionTiro[] generarArrayPuntuacionesGol(double[] arrayAngulos, Player jug,
                                                     Position posBalon) {
    double distCubiertaPorDisparo;
    int tamanio = arrayAngulos.length;
    posicionTiro[] arrayPuntuaciones = new posicionTiro[tamanio];
    Arrays.fill(arrayPuntuaciones, new posicionTiro(-1, 0));
    double anguloMin, anguloMax, posXAnguloMin, posXAnguloMax;
    double error = Constants.getErrorAngular(jug.getPrecision());
    for (int i = 0; i < tamanio; i++) {
      // System.out.print("yo tiro a: " + new Posicion((-Constants.ANCHO_CAMPO / 2) + (miniTrozo * i), 53));
      anguloMin = (arrayAngulos[i] + (0 * error - error / 2) * Math.PI);
      anguloMax = (arrayAngulos[i] + (1 * error - error / 2) * Math.PI);
      posXAnguloMin = this.calcularValorXLineaDeFondo(posBalon, jug, anguloMin);
      posXAnguloMax = this.calcularValorXLineaDeFondo(posBalon, jug, anguloMax);
      //System.out.println("  y el ballPosition va entre "+anguloMax + "   "+ anguloMin);

      if (posXAnguloMin != -1000 && posXAnguloMax != -1000) {
        if (posXAnguloMin > posXAnguloMax) {
          double temp = posXAnguloMax;
          posXAnguloMax = posXAnguloMin;
          posXAnguloMin = temp;
        }
        if (posXAnguloMin > Constants.LARGO_ARCO / 2 || posXAnguloMax < -Constants.LARGO_ARCO / 2) {
          //System.out.println("el ballPosition llega pero no es gol");
          continue;
        } else {
          distCubiertaPorDisparo = posXAnguloMax - posXAnguloMin;
          if (posXAnguloMin < -Constants.LARGO_ARCO / 2) {
            posXAnguloMin = -Constants.LARGO_ARCO / 2;
          }
          if (posXAnguloMax > Constants.LARGO_ARCO / 2) {
            posXAnguloMax = Constants.LARGO_ARCO / 2;
          }

          arrayPuntuaciones[i] =
              new posicionTiro(i, (posXAnguloMax - posXAnguloMin) / distCubiertaPorDisparo);
          //System.out.println("en la posicion " + i + " del array meto " + posXAnguloMin + " - " + posXAnguloMax);

        }
      } else {
        //System.out.println("no llega a porteria");
      }

    }

    Arrays.sort(arrayPuntuaciones);
    // System.out.println(Arrays.toString(arrayPuntuaciones));

    return arrayPuntuaciones;
  }

  private double calcularValorXLineaDeFondo(Position posBalon, Player jug, double angulo) {
    double nuevaX = -1000;
    double remateJugador, vel, fuerzaRemate, time, desplazamientoHorizontal, nuevaY;

    for (int iteracion = 0; iteracion < 200; iteracion++) {
      remateJugador = jug.getPower();
      fuerzaRemate = 1;
      vel = fuerzaRemate * Constants.getVelocidadRemate(remateJugador);
      AbstractTrajectory
          trayectoria =
          new AirTrajectory(Math.cos(0) * vel, Math.sin(0) * vel, 0, 0);
      time = (double) iteracion / 60d;
      desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
      nuevaX = posBalon.getX() + desplazamientoHorizontal * Math.cos(angulo);
      nuevaY = posBalon.getY() + desplazamientoHorizontal * Math.sin(angulo);
      if (nuevaY >= 52.5) {
        break;
      }
    }
    return nuevaX;
  }

  public static void main(String[] args) {
    Tactica_Mou_Team t = new Tactica_Mou_Team();
    System.out.println(t.iteracionesTardar(1, 1, 60, 41.748));


  }

  private boolean tieneMasDeUnPortero() {
    boolean tieneMasDeUno = false;
    Position posPorteria = new Position(0, 52.5);
    int
        contrarioMasCercano =
        posPorteria.nearestIndex(this.sp.rivalPlayers(), this.porteroContrario);

    if (posPorteria.distance(this.sp.rivalPlayers()[contrarioMasCercano]) < 5) {
      tieneMasDeUno = true;
      //System.out.println("TIENE MAS DE UN PORTERO");
    }
    return tieneMasDeUno;
  }

  private void buscarPorteroContrario() {
    for (int i = 0; i < 11 && this.porteroContrario == -1; i++) {
      if (this.sp.rivalPlayersDetail()[i].isGoalKeeper()) {
        porteroContrario = i;
        //System.out.println("el portero es: " + porteroContrario);
      }
    }
  }

  private int buscarNumEnArray(int[] arrayNums, int num, int margenInf, int margenSup) {
    int pos = -1;
    int tamanio = arrayNums.length;
    for (int i = margenInf; i < tamanio && pos == -1 && i <= margenSup; i++) {
      if (arrayNums[i] == num) {
        pos = i;
      }
    }

    return pos;
  }

  private LinkedList<Command> eliminarComandos(LinkedList<Command> lista, int... jugs) {
    int tamanio = jugs.length;
    int jug;
    int j;
    for (int i = 0; i < tamanio; i++) {
      jug = jugs[i];
      j = 0;
      while (j < lista.size()) {
        if (lista.get(j).getPlayerIndex() == jug) {
          lista.remove(j);
        } else {
          j++;
        }
      }
    }

    lista.get(0).getPlayerIndex();
    return lista;
  }
}
