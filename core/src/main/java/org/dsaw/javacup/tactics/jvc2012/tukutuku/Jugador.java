package org.dsaw.javacup.tactics.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.Arrays;

//Interface que debe implementar cualquier modalidad de jugador

public abstract class Jugador {

  protected Entrenador entrenador = null;

  protected int numero = 0;

  protected double velocidad = 0;

  protected double potencia = 0;

  protected double error = 0;

  public int marcaje = -1;

  public Position posicionJugador = null;

  //Coordenada Y m�xima y m�nima del jugador
  double yMAX = 0;
  double yMin = 0;

  //Coordenada X m�xima y m�nima del jugador
  double xMAX = 0;
  double xMin = 0;


  public ArrayList<Command> accionDefensa() {
    ArrayList<Command> accion = new ArrayList<>();

    //Si el jugador es uno liberado de marcas, va directamente a cubrir al jugador m�s cercano a puerta si estamos defendiendo en nuestro campo
    if (((numero == 8) || (numero == 9) || (numero == 10))) {
      Position
          posJugRival =
          entrenador.Gs.rivalPlayers()[Constants.centroArcoInf
              .nearestIndexes(entrenador.Gs.rivalPlayers())[10 - numero]];
      accion.add(new CommandMoveTo(numero, posJugRival.movePosition(0, -1)));
      return accion;
    }

    if (marcaje != -1) {
      Position marcajePos = entrenador.Gs.rivalPlayers()[marcaje];
      //accion.add(new CommandMoveTo(numero, marcajePos.moveAngle(marcajePos.angle(entrenador.Gs.ballPosition()), -0.75)));
      accion.add(new CommandMoveTo(numero, marcajePos.movePosition(0, -1)));
      return accion;
    }

    //Obtenemos la alineacion actual
    Position[] alineacion = entrenador.obtenerAlineacion();

    //Obtenemos la posicion del balon
    //Position posBalon = entrenador.Gs.ballPosition();

    //Si el balon est� pr�ximo a su posici�n en la alineaci�n vamos a por el o su posicion actual

    if ((entrenador.Gs.ballPosition().distance(alineacion[numero]) < 8) || (
        entrenador.Gs.ballPosition().distance(this.posicionJugador) < 5)) {
      accion.add(new CommandMoveTo(numero, entrenador.Gs.ballPosition()));
    } else {
      Position
          jugRivalCercanoPos =
          entrenador.Gs.rivalPlayers()[posicionJugador.nearestIndex(entrenador.Gs.rivalPlayers())];

			/*if ( (posicionJugador.distance( jugRivalCercanoPos) < 5 ) && (!entrenador.atacando) && numero > 5) 
			{
		
				accion.add(new CommandMoveTo(numero, jugRivalCercanoPos.movePosition(0, -1)));
			}
			else*/
      {
        //mantenemos la posicion de la alineacion
        accion.add(new CommandMoveTo(numero, alineacion[numero]));

      }
    }
    return accion;

  }

  public abstract ArrayList<Command> accionRemate();

  public Jugador(int x) {
    numero = x;
    entrenador = Entrenador.obtenerEntrenador();

    velocidad = entrenador.Gs.getMyPlayerSpeed(x);
    potencia = entrenador.Gs.getMyPlayerPower(x);
    error = entrenador.Gs.getMyPlayerError(x);

  }

  //Accion comun a todos los jugadores
  public ArrayList<Command> accionRecuperacion(Position recuperacionPos) {
    ArrayList<Command> accion = new ArrayList<>();

    accion.add(new CommandMoveTo(numero, recuperacionPos));

    return accion;
  }


  /**
   * Calcula todos los posibles pases que que se pueden dar a los destinos indicados
   *
   * @return Commando para ejecutar el disparo
   */

  protected TiroInfo seleccionarTiro() {

    //ArrayList donde guardaremos los calculos de cada uno de los pases y al final decidiremos cual es el mejor
    ArrayList<TiroInfo> pInfList = new ArrayList<>();

    //Calculamos los tiros en cinco puntos diferentes de la porteria

    for (double angulo = 15; angulo < 165; angulo += 2) {
      pInfList.addAll(obtenerPosiblesTiros(angulo * (Math.PI / 180)));
    }

    //Recorremos todos los resultados y nos quedamos con el mejor

    TiroInfo pInfMejor = null;

    for (TiroInfo pInf : pInfList) {
      if (pInfMejor == null || pInf.calidad.esMejor(pInfMejor)) {
        pInfMejor = pInf;
      }
    }

    //Devolvemos el comando con el mejor pase seleccionado
    //Si no hay ninguno devolvemos nulo
    if (pInfMejor == null) {
      return null;
    }

    return pInfMejor;

  }

  /**
   * Simula todos los posibles pases a un destino y devuelve un array con la informaci�n de los
   * pases ejecutados
   */
  private ArrayList<TiroInfo> obtenerPosiblesTiros(double angulo) {
    //Lista de PaseInfo
    ArrayList<TiroInfo> pInfList = new ArrayList<>();

    //Calculamos todos los posibles pases al punto destino
    for (double fuerza = 0.8d; fuerza < 1.01; fuerza += 0.15d) {
      //Diferentes angulos verticales
      for (double anguloVert = 0; anguloVert <= 5 * (Math.PI / 180d); anguloVert += 0.1) {

        TiroInfo pInf = new TiroInfo(numero, fuerza, anguloVert, angulo);
        pInf.simularTiro();

        //Si es un tiro con exito lo a�adimos a la lista

        if (pInf.calidad.esGol) {
          pInfList.add(pInf);
        }
      }
    }

    return pInfList;
  }

  /**
   * Calcula todos los posibles pases que que se pueden dar a los destinos indicados
   *
   * @return Commando para ejecutar el disparo
   */

  protected PaseInfo seleccionarPase(double angA, double angB, int angIncr) {

    //ArrayList donde guardaremos los calculos de cada uno de los pases y al final decidiremos cual es el mejor
    ArrayList<PaseInfo> pInfList = new ArrayList<>();

    //Calculamos todos los posibles pases

    for (double angulo = angA; angulo < angB; angulo += angIncr) {
      pInfList.addAll(obtenerPosiblesPases(angulo * (Math.PI / 180)));
    }

    //Recorremos todos los resultados y nos quedamos con el mejor

    PaseInfo pInfMejor = null;

    for (PaseInfo pInf : pInfList) {
      if (pInfMejor == null || pInf.calidad.esMejor(pInfMejor)) {
        pInfMejor = pInf;
      }
    }

    if (pInfMejor != null) {
      entrenador.jugadorDestinoPase =
          pInfMejor.calidad.posFinalBalon.nearestIndex(entrenador.Gs.myPlayers());
      entrenador.iterDestinoPase = pInfMejor.iterPase;
      entrenador.posDestinoPase = pInfMejor.calidad.posFinalBalon;
    }

    return pInfMejor;

  }

  /**
   * Simula todos los posibles pases a un destino y devuelve un array con la informaci�n de los
   * pases ejecutados
   */
  private ArrayList<PaseInfo> obtenerPosiblesPases(double anguloRad) {
    //Lista de PaseInfo
    ArrayList<PaseInfo> pInfList = new ArrayList<>();

    //Calculamos todos los posibles pases
    for (double fuerza = 0.1d; fuerza < 1.01; fuerza += 0.15d) {
      //Diferentes angulos verticales
      for (double anguloVert = 0; anguloVert <= 61 * (Math.PI / 180d); anguloVert += 0.1) {

        PaseInfo pInf = new PaseInfo(numero, fuerza, anguloVert, anguloRad);
        pInf.simularPase();

        //Si es un pase con exito lo a�adimos a la lista

        if (pInf.calidad.paseSeguro) {
          pInfList.add(pInf);
        }
      }
    }

    return pInfList;
  }

  protected Command tirarAPorteria() {
    int numJug = numero;
    GameSituations sp = entrenador.Gs;
    double[] angulosPorteria;
    posicionTiro[] puntuacionesPorteria;
    Position posDisparo = new Position(0, 53);
    double anguloVertRapido;

    angulosPorteria = this.generarArrayAngulosAnchoCampo(sp.ballPosition());
    puntuacionesPorteria =
        this.generarArrayPuntuacionesGol(angulosPorteria, sp.myPlayersDetail()[numJug],
                                         sp.ballPosition());
    int cantidadMiniTrozos = (int) Constants.ANCHO_CAMPO * 2;
    double miniTrozo = Constants.ANCHO_CAMPO / cantidadMiniTrozos;
    double mayorDistanciaPortero = -1;
    Position tiro;
    int indice = -1;
    double mayorProbabilidad = puntuacionesPorteria[0].getProbabilidad();
    double margen = 0.5;

    if (sp.ballPosition().distance(new Position(0, 52.5)) > 37) {
      margen = 0;

    }

    int porteroRival = 0;

    for (int x = 0; x < entrenador.Gs.rivalPlayersDetail().length; ++x) {
      if (entrenador.Gs.rivalPlayersDetail()[x].isGoalKeeper()) {
        porteroRival = x;
        break;
      }
    }

    for (int i = 0; i < cantidadMiniTrozos; i++) {
      tiro =
          new Position(
              (-Constants.ANCHO_CAMPO / 2) + (miniTrozo * puntuacionesPorteria[i].getIndice()), 53);
      if (puntuacionesPorteria[i].getProbabilidad() >= (mayorProbabilidad - 0.05)
          || puntuacionesPorteria[i].getProbabilidad() >= 0.9) {
        if (tiro.distance(sp.rivalPlayers()[porteroRival]) > mayorDistanciaPortero) {
          mayorDistanciaPortero = posDisparo.distance(sp.rivalPlayers()[porteroRival]);
          posDisparo = tiro;
          indice = i;

        }

      } else {
        break;
      }

    }

    anguloVertRapido =
        calcularAnguloVerticalRapido(sp.ballPosition().distance(posDisparo),
                                     sp.myPlayersDetail()[numJug].getPower(), 0, 100);

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

  private posicionTiro[] generarArrayPuntuacionesGol(double[] arrayAngulos, PlayerDetail jug,
                                                     Position posBalon) {

    double distCubiertaPorDisparo;
    int tamanio = arrayAngulos.length;
    posicionTiro[] arrayPuntuaciones = new posicionTiro[tamanio];
    Arrays.fill(arrayPuntuaciones, new posicionTiro(-1, 0));
    double anguloMin, anguloMax, posXAnguloMin, posXAnguloMax;
    double error = Constants.getErrorAngular(jug.getPrecision());
    for (int i = 0; i < tamanio; i++) {

      anguloMin = (arrayAngulos[i] + (0 * error - error / 2) * Math.PI);
      anguloMax = (arrayAngulos[i] + (1 * error - error / 2) * Math.PI);
      posXAnguloMin = this.calcularValorXLineaDeFondo(posBalon, jug, anguloMin);
      posXAnguloMax = this.calcularValorXLineaDeFondo(posBalon, jug, anguloMax);

      if (posXAnguloMin != -1000 && posXAnguloMax != -1000) {
        if (posXAnguloMin > posXAnguloMax) {
          double temp = posXAnguloMax;
          posXAnguloMax = posXAnguloMin;
          posXAnguloMin = temp;
        }
        if (posXAnguloMin > Constants.LARGO_ARCO / 2 || posXAnguloMax < -Constants.LARGO_ARCO / 2) {

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


        }
      }

    }

    Arrays.sort(arrayPuntuaciones);

    return arrayPuntuaciones;
  }

  private double calcularValorXLineaDeFondo(Position posBalon, PlayerDetail jug, double angulo) {
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


}

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
