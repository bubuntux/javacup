package org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica;

import org.dsaw.javacup.model.Player;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.Accion;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionAvanzar;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionDespejar;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionPasar;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionPasarAlArea;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionPasarAlHueco;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionRealizada;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionRegatear;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionTirar;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.AccionTirarTrallon;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.accion.Generador;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades.Balon;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades.Entorno;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades.Jugador;
import org.dsaw.javacup.tactics.jvc2013.absolutsport.tactica.utilidades.Rival;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * Clase que implementa el interfaz Tactic con las acciones y datos requeridos por el framework para
 * desarrollar un partido.
 *
 * @author Christian Onwuzor Martín (chr -> airchris01@yahoo.es)
 */
public class TacticaAbsolutSport implements Tactic {

  TacticaAbsolutSportDetalle detalle = new TacticaAbsolutSportDetalle();
  LinkedList<Command> comandos = new LinkedList<>();

  // Variables que contendrán los indices de los jugadores atendiendo a su ubicación.
  HashSet<Jugador> conjuntoDefensas = new HashSet<>();
  HashSet<Jugador> conjuntoMedios = new HashSet<>();
  HashSet<Jugador> conjuntoDelanteros = new HashSet<>();
  HashSet<Jugador> conjuntoJugadores = new HashSet<>();

  // Variables que contendr�n los indices de los rivalPlayers atendiendo a su ubicaci�n.
  HashSet<Rival> rivalesSectorDefensa = new HashSet<>();
  HashSet<Rival> rivalesSectorMedio = new HashSet<>();
  HashSet<Rival> rivalesSectorAtaque = new HashSet<>();

  Jugador[] arrayJugadores = new Jugador[11];
  Rival[] arrayRivales = new Rival[11];

  // Objeto que nos permitir� generar las acciones a realizar en cada ciclo.
  Generador gen;

  // Lista en la que almacenaremos la trayectoria de la pelota.
  ArrayList<Balon> listaTrayectoria = null;

  int jugadorLlegaAntesBalon;
  int rivalLlegaAntesBalon;

  // Variables que indican factores de la posesi�n.
  boolean posesion = false;
  int jugadorPosesion = -1;
  int jugadorRivalPosesion = -1;
  int jugadorQuePasa = -1;
  int pasandoA = -1;


  int contAvance = 0;
  int contRegate = 0;

  Position posBola = new Position(0, 0);
  double alturaBola = 0;

  // Variables que nos permitiran conocer el sector del juego en el que se encuentra la pelota.
  char sectorActivo = ' ';

  Random rand = null;

  int iteracionesParaSacar = 0;

  double yRivalMasAdelantado;


  // Variables de optimización del proceso de elección de acción.
  AccionRealizada acciones = new AccionRealizada();

  // Variables que utilizaremos para contener las acciones efectuadas.
  String[] accionEnProceso, accionAnterior = null;

  int misGoles = 0;

  // Variables que utilzamos para obtener los datos de la táctica barrido.
  boolean ejecutarBarrido = false;
  boolean tiroPorBarrido = false;
  int iteracionUltimoBarrido = 0;


  /**
   * Constructor en el que inicializamos las variables globales de la clase.
   */
  public TacticaAbsolutSport() {

    // Inicializamos la semilla de aleatoriedad.
    rand = new Random();

    // Inicializamos el array de los jugadores
    for (int i = 0; i < arrayJugadores.length; i++) {
      arrayJugadores[i] = new Jugador();
      arrayJugadores[i].setIndice(i);
      arrayJugadores[i].setZona(Entorno.zona[i]);
      arrayJugadores[i].setMarcaje(null);

      // Método que asigna los valores del pase de cada jugador.
      cargaValoresPase(i);

      // El ángulo de tiro del jugador.
      arrayJugadores[i].setAnguloTiro(anguloTiro(arrayJugadores[i]));

      // La fuerza a aplicar para la acción avanzar.
      arrayJugadores[i].setFuerzaGolpeoAvanzar(
          ((detalle.getPlayers()[i].getPower() < 1) ? 0.5 : 0.375));
    }

    // A�adimos los �ndices de los defensas.
    conjuntoDefensas.add(arrayJugadores[1]);
    conjuntoDefensas.add(arrayJugadores[2]);
    conjuntoDefensas.add(arrayJugadores[3]);
    conjuntoDefensas.add(arrayJugadores[4]);

    // A�adimos los �ndices de los medios.
    conjuntoMedios.add(arrayJugadores[5]);
    conjuntoMedios.add(arrayJugadores[6]);
    conjuntoMedios.add(arrayJugadores[7]);
    conjuntoMedios.add(arrayJugadores[10]);

    // A�adimos los �ndices de los delanteros.
    conjuntoDelanteros.add(arrayJugadores[9]);
    conjuntoDelanteros.add(arrayJugadores[8]);

    // A�adimos todos los jugadores al conjunto general.
    conjuntoJugadores.addAll(conjuntoDefensas);
    conjuntoJugadores.addAll(conjuntoMedios);
    conjuntoJugadores.addAll(conjuntoDelanteros);

    // Inicializamos el �ndice de los jugadores que llegan m�s r�pido al bal�n.
    jugadorLlegaAntesBalon = -1;
    rivalLlegaAntesBalon = -1;

    // Inicializamos el array de los rivalPlayers
    for (int i = 0; i < arrayRivales.length; i++) {
      arrayRivales[i] = new Rival();
      arrayRivales[i].setIndice(i);
    }

    // Instanciamos el objeto en el que almacenaremos la trayectoria del bal�n.
    listaTrayectoria = new ArrayList<>();

    // Instanciamos el objeto que nos permitira generar las acciones a realizar.
    gen = new Generador();
  }

  @Override
  public Team getDetail() {
    return detalle;
  }

  @Override
  public Position[] getStartPositions(GameSituations sp) {
    return Entorno.alineacionSacando;
  }

  @Override
  public Position[] getNoStartPositions(GameSituations sp) {
    return Entorno.alineacionRecibiendo;
  }

  @Override
  public List<Command> execute(GameSituations sp) {

    //Limpiamos la lista de comandos.
    comandos.clear();

    // Generamos los tiempos de alcance del bal�n para todos los jugadores.
    generaListaTrayectoria(sp);
    generaTiempoAlcanceBalon(sp);

		/*
                if (listaTrayectoria.get(0).getX() != sp.ballPosition().getX() ||
			listaTrayectoria.get(0).getY() != sp.ballPosition().getY() ||
			listaTrayectoria.get(0).getZ() != sp.ballAltitude()) {
			System.err.println("ERROR EN LA TRAYECTORIA");
		}
		*/

    // Llamamos al m�todo que nos calcula si tenemos la posesi�n de la pelota.
    calculaPosesion(sp);

    // Calculamos los resultados de las acciones realizadas.
    calculaResultadoAcciones(sp);

    // Calculamos las acciones almacenadas.
    calculaAcciones(sp);

    // Calculamos el sector activo en el que se encuentra la bola.
    sectorActivo = calculaSector(sp.ballPosition());

    // Calculamos la posici�n en la que se encuentran los rivalPlayers.
    calculaRivales(sp);

    // Asignamos los marcajes de los jugadores.
    asignaMarcajes(sp);

    // Calculamos los movimientos de los jugadores.
    movimientosPortero(sp);
    movimientosDefensa(sp);
    movimientosMedia(sp);
    movimientosDelantera(sp);

    // Actualizamos la posición de la bola para mantener sus anterior posicion.
    posBola = sp.ballPosition();
    alturaBola = sp.ballAltitude();
    avanzarTrayectoria();

    // Actualizamos los goles para mantener los de la iteración anterior.
    misGoles = sp.myGoals();

    return comandos;
  }

  //*************************************************************************************************************
  //										M�TODOS PRIVADOS DE LA CLASE
  //*************************************************************************************************************

  //-------------------------------------------------------------------------------------------------------------
  //										M�todos generales
  //-------------------------------------------------------------------------------------------------------------

  private void cargaValoresPase(int i) {

    Hashtable<Integer, Double> t = new Hashtable<>();

    // Jugador de máxima fuerza de remate.
    if (detalle.getPlayers()[i].getPower() == 1) {

      // Asignamos la distancia máxima de golpeo bajo.
      arrayJugadores[i].setDistanciaGolpeoBajo(60);
    		
    		/*
    		t.put(60, 1d);
    		t.put(55, 0.95d);
    		t.put(50, 0.9d);
    		t.put(45, 0.85d);
    		t.put(40, 0.8d);
    		t.put(35, 0.75d);
    		t.put(30, 0.7d);
    		t.put(25, 0.65d);
    		t.put(20, 0.6d);
    		t.put(15, 0.55d);
    		t.put(10, 0.5d);
    		t.put(5,  0.45d);
    		*/

      t.put(60, 1d);
      t.put(54, 0.95d);
      t.put(48, 0.9d);
      t.put(42, 0.85d);
      t.put(36, 0.8d);
      t.put(30, 0.75d);
      t.put(24, 0.7d);
      t.put(18, 0.65d);
      t.put(12, 0.6d);
      t.put(6, 0.55d);
      //t.put(10, 0.5d);
      //t.put(5,  0.45d);

      arrayJugadores[i].setTablaGolpeoBajo(t);
    }

    // Resto de jugadores.
    else {

      // Asignamos la distancia máxima de golpeo bajo.
      arrayJugadores[i].setDistanciaGolpeoBajo(30);
    		
    		/*
    		t.put(30, 1d);
    		t.put(28, 0.95d);
    		t.put(26, 0.9d);
    		t.put(24, 0.85d);
    		t.put(22, 0.8d);
    		t.put(20, 0.75d);
    		t.put(15, 0.7d);
    		t.put(10, 0.65d);
    		t.put(9,  0.6d);
    		t.put(8,  0.55d);
    		t.put(7,  0.5d);
    		*/

      t.put(30, 1d);
      t.put(29, 0.95d);
      t.put(28, 0.9d);
      t.put(27, 0.85d);
      t.put(26, 0.8d);
      t.put(25, 0.75d);
      t.put(22, 0.7d);
      t.put(19, 0.65d);
      t.put(16, 0.6d);
      t.put(13, 0.55d);
      t.put(10, 0.5d);

      arrayJugadores[i].setTablaGolpeoBajo(t);
    }

    // La distancia máxima a la que llega el jugador realizando un golpeo alto.
    arrayJugadores[i].setDistanciaGolpeoAlto(
        (Entorno.DISTANCIA_MAX_GOLPEO_ALTO * detalle.getPlayers()[i].getPower())
        / Entorno.FUERZA_MAXIMA_GOLPEO);

  }


  // Calculamos la posesi�n teniendo en cuenta el valor anterior.
  private void calculaPosesion(GameSituations sp) {

    // Antes de calcular la posesi�n, comprobamos si se ha realizado un pase.
    if (pasandoA > -1) {
      jugadorPosesion = pasandoA;
      pasandoA = -1;
    }
    // Alg�n jugador puede rematar.
    if (sp.canKick().length > 0 || sp.isStarts()) {

      // Si la posesi�n anteriormente no era nuestra liberamos los marcajes.
      if (posesion == false) {
        contAvance = 0;
        contRegate = 0;

        // Asignamos la iteraci�n en la que obtenemos la posesi�n.
        if (sp.isStarts()) {
          iteracionesParaSacar = Constants.ITERACIONES_SAQUE;
        }
      }

      posesion = true;
      jugadorRivalPosesion = -1;
      jugadorQuePasa = -1;

      // Calculamos el jugador que tiene la posici�n en funci�n de si es un saque o no.
      if (sp.isStarts()) {

        // Decrementamos en 1 el n�mero de iteraciones que nos quedan para sacar.
        iteracionesParaSacar--;

        // Saque en la banda izquierda.
        if (sp.ballPosition().getX() == -Constants.ANCHO_CAMPO_JUEGO / 2.0) {

          if (sectorActivo == Entorno.SECTOR_DEFENSA) {
            jugadorPosesion = 2;
          } else {

            double distancia10 = sp.ballPosition().distance(sp.myPlayers()[10]);
            double distancia8 = sp.ballPosition().distance(sp.myPlayers()[8]);

            if (distancia8 < distancia10) {
              jugadorPosesion = 8;
            } else {
              jugadorPosesion = 10;
            }
          }
        }
        // Saque en la banda derecha.
        else if (sp.ballPosition().getX() == Constants.ANCHO_CAMPO_JUEGO / 2.0) {

          if (sectorActivo == Entorno.SECTOR_DEFENSA) {
            jugadorPosesion = 1;
          } else {

            double distancia9 = sp.ballPosition().distance(sp.myPlayers()[9]);
            double distancia6 = sp.ballPosition().distance(sp.myPlayers()[6]);

            if (distancia9 < distancia6) {
              jugadorPosesion = 9;
            } else {
              jugadorPosesion = 6;
            }
          }
        }
        // Saca de puerta.
        else {
          jugadorPosesion = 0;
            		
            		/*
                	ANTES DE RECALCULAR LAS ITERACIONES DEL SAQUE
                	if (iteracionesParaSacar <= sp.iteration()) {
                		jugadorPosesion = 0;
                	}
                	else {
                		iteracionesParaSacar --;
                		jugadorPosesion = -1;
                	}
                	*/
        }
      }
      // Si no es un saque la posesi�n es del jugador que puede rematar.
      else {
        jugadorPosesion = sp.canKick()[0];
      }

    } else {

      // Alg�n rival puede rematar.
      if (sp.rivalCanKick().length > 0 || sp.isRivalStarts()) {
        posesion = false;
        jugadorPosesion = -1;
        jugadorQuePasa = -1;
        pasandoA = -1;
        jugadorRivalPosesion = sp.rivalCanKick().length > 0 ? sp.rivalCanKick()[0] : -1;
        contAvance = 0;
        contRegate = 0;
      }
      // Ning�n rival puede rematar y la posesi�n es del rival teniendo alguno de ellos la posesi�n de
      // la pelota.
      else if (posesion == false && jugadorRivalPosesion >= 0) {

        // Si la distancia del rival al bal�n es mayor que la establecida estimamos que el rival ha
        // hecho un pase o un tiro.
        if (sp.rivalPlayers()[jugadorRivalPosesion].distance(sp.ballPosition())
            > Constants.DISTANCIA_CONTROL_BALON * 3) {
          jugadorRivalPosesion = -1;
        }
      }
    }
  }


  // Devuelve true si el jugador consultado esta en contacto con el bal�n
  private boolean contactoBalon(GameSituations sp, int indJugador) {

    boolean contacto = false;
    for (int i : sp.canKick()) {
      if (i == indJugador) {
        contacto = true;
        break;
      }
    }

    return contacto;
  }


  private void calculaResultadoAcciones(GameSituations sp) {

    if (!posesion) {
      tiroPorBarrido = false;
    }

    if (misGoles < sp.myGoals()) {

      if (tiroPorBarrido) {

        // Hemos marcado gol y lo desactivamos para que no penalice.
        ejecutarBarrido = false;
        tiroPorBarrido = false;
      }
    }

    if (ejecutarBarrido) {

      if (misGoles < sp.myGoals()) {

        // Hemos marcado gol y lo desactivamos para que no penalice.
        ejecutarBarrido = false;
      } else if (!posesion) {

        ejecutarBarrido = false;
      }
    }
  }


  // Calculamos las acciones.
  private void calculaAcciones(GameSituations sp) {

    // Hemos marcado gol
    if (misGoles < sp.myGoals()) {

      // Actualizamos la información de la acciones realizadas.
      if (accionEnProceso != null) {
        acciones.actualizaInformacion(accionEnProceso, 0, 1, 2);
      }
      if (accionAnterior != null) {
        acciones.actualizaInformacion(accionAnterior, 0, 0, 2);
      }

      // Inicializamos ambas variables ya que va a sacar el equipo contrario.
      accionAnterior = null;
      accionEnProceso = null;
    }
    // El balón esta fuera del campo.
    else if (!gen.posicionDentroDelCampo(sp.ballPosition())) {

      // Anulamos todas las acciones pendientes.
      accionEnProceso = null;
      accionAnterior = null;
    }
    // El balón esta en el campo.
    else {

      // Si no tenemos la posesión del balón.
      if (!posesion) {

        // Anulamos todas las acciones pendientes.
        accionEnProceso = null;
        accionAnterior = null;
      }
    }
  }


  // Calculamos el sector en el que se encuentra la posici�n indicada.
  private char calculaSector(Position posicion) {

    char sector;
    if (posicion.getY() < Entorno.LIMITE_DEFENSA) {
      sector = Entorno.SECTOR_DEFENSA;
    } else if (posicion.getY() < Entorno.LIMITE_MEDIO) {
      sector = Entorno.SECTOR_MEDIO;
    } else {
      sector = Entorno.SECTOR_ATAQUE;
    }

    return sector;
  }


  // Calculamos la posici�n en la que se encuentran los rivalPlayers
  private void calculaRivales(GameSituations sp) {

    // Limpiamos los conjuntos que nos indican donde se ubican los rivalPlayers.
    rivalesSectorDefensa.clear();
    rivalesSectorMedio.clear();
    rivalesSectorAtaque.clear();

    // Inicializamos la variable que contiene el �ndice del rival m�s adelantado.
    yRivalMasAdelantado = 0;

    Position[] rivales = sp.rivalPlayers();
    for (int i = 0; i < rivales.length; i++) {

      // Calculamos cual es el rival m�s adelantado en el eje Y.
      if (sp.rivalPlayers()[i].getY() < yRivalMasAdelantado) {
        yRivalMasAdelantado = sp.rivalPlayers()[i].getY();
      }

      arrayRivales[i].setPosicion(rivales[i]);

      if (arrayRivales[i].getMovimientos().size() > 20) {
        arrayRivales[i].getMovimientos().remove(0);
      }
      arrayRivales[i].getMovimientos().add(rivales[i]);
      arrayRivales[i].setSector(calculaSector(rivales[i]));

      // Dependiendo del sector en el que se ubique lo a�adimos a su
      // conjunto correspondiente.
      switch (arrayRivales[i].getSector()) {
        case Entorno.SECTOR_DEFENSA:
          rivalesSectorDefensa.add(arrayRivales[i]);
          break;
        case Entorno.SECTOR_MEDIO:
          rivalesSectorMedio.add(arrayRivales[i]);
          break;
        case Entorno.SECTOR_ATAQUE:
          rivalesSectorAtaque.add(arrayRivales[i]);
          break;
      }
    }
  }


  private void asignaMarcajes(GameSituations sp) {

    //NOTA: HAY QUE LIMPIAR LOS MARCAJES.
    for (Jugador jugador : arrayJugadores) {
      jugador.setMarcaje(null);
    }

    HashSet<Integer> rivalesMarcados = new HashSet<>();
    int defensasQueMarcan = 0;

    int[] arrayRivalesCercanos = Constants.centroArcoInf.nearestIndexes(sp.rivalPlayers());

    // Vamos a establecer los marcajes de la defensa.

    // Recorremos los rivalPlayers que se encuentran en mi defensa para asignarles un marcaje.
    for (int rival : arrayRivalesCercanos) {

      if (rivalesSectorDefensa.contains(arrayRivales[rival]) &&
          sp.rivalPlayers()[rival].getY() < 0) {

        int[] arrayDefensas = sp.rivalPlayers()[rival].nearestIndexes(sp.myPlayers());
        for (int defensa : arrayDefensas) {

          if (conjuntoDefensas.contains(arrayJugadores[defensa]) &&
              arrayJugadores[defensa].getMarcaje() == null) {

            arrayJugadores[defensa].setMarcaje(arrayRivales[rival]);
            rivalesMarcados.add(rival);
            defensasQueMarcan++;

            break;
          }

        }
      }
    }

    // Mandamos a marcar a los defensas que estan libres a los medios.
    if (defensasQueMarcan < conjuntoDefensas.size()) {

      for (int rival : arrayRivalesCercanos) {

        if (rivalesSectorMedio.contains(arrayRivales[rival]) &&
            sp.rivalPlayers()[rival].getY() < 0) {

          int[] arrayDefensas = sp.rivalPlayers()[rival].nearestIndexes(sp.myPlayers());
          for (int defensa : arrayDefensas) {

            if (conjuntoDefensas.contains(arrayJugadores[defensa]) &&
                arrayJugadores[defensa].getMarcaje() == null) {

              arrayJugadores[defensa].setMarcaje(arrayRivales[rival]);
              rivalesMarcados.add(rival);
              defensasQueMarcan++;

              break;
            }

          }
        }
      }
    }

    for (int rival : arrayRivalesCercanos) {

      if (!rivalesMarcados.contains(rival) && rivalesSectorDefensa.contains(arrayRivales[rival])) {

        int[] arrayMedios = sp.rivalPlayers()[rival].nearestIndexes(sp.myPlayers());
        for (int medio : arrayMedios) {

          // Solamente bajan a defender los medio centros.
          if ((medio == 5 || medio == 7) &&
              arrayJugadores[medio].getMarcaje() == null) {

            arrayJugadores[medio].setMarcaje(arrayRivales[rival]);
            rivalesMarcados.add(rival);

            break;
          }

        }
      }
    }

    if (!posesion) {

      for (int rival : arrayRivalesCercanos) {

        if (!rivalesMarcados.contains(rival) && rivalesSectorMedio.contains(arrayRivales[rival])) {

          // Solamente marcamos a los medios si estan por delante del bal�n.
          if (sp.ballPosition().getY() > sp.rivalPlayers()[rival].getY()) {

            int[] arrayMedios = sp.rivalPlayers()[rival].nearestIndexes(sp.myPlayers());
            for (int medio : arrayMedios) {

              if (conjuntoMedios.contains(arrayJugadores[medio]) &&
                  arrayJugadores[medio].getMarcaje() == null) {

                arrayJugadores[medio].setMarcaje(arrayRivales[rival]);
                rivalesMarcados.add(rival);

                break;
              }

            }
          }
        }
      }
    }

    		
    	
/*		
		AQUI HAY QUE CALCULAR SI TENEMOS QUE MANDAR ALGUN DEFENSA AL MEDIO
		// Si no tenemos la posesi�n asignamos los marcajes de los medios.
		if (!posesion) {
			
			// Creamos un array de Posiciones para ordenarlo posteriormente.
			arrayPosicionesRivales = new Posicion[rivalesSectorMedio.size()];
			i = 0;
			for (Rival rival:rivalesSectorMedio) {
				arrayPosicionesRivales[i] = sp.rivalPlayers()[rival.getIndice()];
				i++;
			}
			

			// Recorremos los medios para asignarles su marcaje.
			for (Jugador medio:conjuntoMedios) {
				
				int [] rivalPlayers = sp.myPlayers()[medio.getIndice()].indicesMasCercanos(arrayPosicionesRivales);
				for (int rival:rivalPlayers) {
					
					if (!rivalesMarcados.contains(rival)) {
						medio.setMarcaje(arrayRivales[rival]);
						rivalesMarcados.add(rival);
					}
				}
			}
		}
*/
  }


  // M�todo que calcula y devuelve el �ngulo de tiro del jugador.
  private double anguloTiro(Jugador j) {

    // Para calcular el �ngulo tomamos las siguientes referencias en funci�n a la fuerza m�xima del remate:
    //	> 1.00, el �ngulo ser� 20
    //	> 0.75, el �ngulo ser� 25
    //	> 0.50, el �ngulo ser� 30
    // Si seguimos esa progresi�n vemos que cada decena que disminuye la fuerza aumenta en 0.20 el �ngulo
    // por lo que podemos obtener la f�mrula que nos permita conocer el �ngulo para cualquier fuerza dada:
    // �ngulo = [((1 - Fuerza Remate)*100) * 0.2] + 20;
    return ((1 - detalle.getPlayers()[j.getIndice()].getPower()) * 100 * 0.2) + 20;
  }


  private boolean llegoAntes(GameSituations sp, Jugador jugador, boolean igual) {

    boolean llegoAntes = false;

    // Si hay datos de mis jugadores y somos el jugador que llega antes a por el bal�n.
    if (jugadorLlegaAntesBalon > -1 && jugador.getIndice() == jugadorLlegaAntesBalon) {

      // Si encontramos datos de mis rivalPlayers tenemos que comprobar si llegamos antes.
      if (rivalLlegaAntesBalon > -1) {

        // Con el parámetro uigual determinamos si podemos llegar en el mismo ciclo al balón.
        int comparador = igual ? 0 : 1;

        // Si el jugador llega 1 iteration antes, devolvemos true.
        if ((arrayRivales[rivalLlegaAntesBalon].getIteracionesAlcanceBalon() -
             arrayJugadores[jugadorLlegaAntesBalon].getIteracionesAlcanceBalon()) >= comparador) {

          llegoAntes = true;
        }
      }

      // Si no encontramos datos de los rivalPlayers significa que el jugador llega antes.
      else {

        // Si soy el jugador m�s r�pido devolvemos true.
        llegoAntes = true;
      }
    }

    return llegoAntes;
  }

  private boolean miJugadorLlegaAntes(GameSituations sp, boolean igual) {

    boolean llegaAntes = false;

    // Si encontrammos datos de mis jugadores y los rivalPlayers.
    if (jugadorLlegaAntesBalon > -1 && rivalLlegaAntesBalon > -1) {

      // Con el parámetro igual determinamos si podemos llegar en el mismo ciclo al balón.
      int comparador = igual ? 0 : 1;

      // Si el jugador llega 1 iteration antes, devolvemos true.
      if ((arrayRivales[rivalLlegaAntesBalon].getIteracionesAlcanceBalon() -
           arrayJugadores[jugadorLlegaAntesBalon].getIteracionesAlcanceBalon()) >= comparador) {

        llegaAntes = true;
      }
    }
    // Si encontramos datos de mis jugadores devolvemos true.
    else if (jugadorLlegaAntesBalon > -1) {
      llegaAntes = true;
    }

    return llegaAntes;
  }


  // M�todo que devuelve el �ndice del jugador m�s cercano por encima del eje Y
  private Integer rivalCercanoSobreY(GameSituations sp, int j) {

    Integer rival = null;
    double margen = 10;
    for (int r : sp.myPlayers()[j].nearestIndexes(sp.rivalPlayers())) {

      // No tenemos en cuenta al portero, para calcular si se encuentra por encima del eje Y.
      if (!sp.rivalPlayersDetail()[r].isGoalKeeper()) {

        // Comprobamos los rivalPlayers que se encuentran a menos de 15 de distancia por encima nuestra.
        if (sp.rivalPlayers()[r].getY() > sp.myPlayers()[j].getY() &&
            (sp.rivalPlayers()[r].getY() - 15) < sp.myPlayers()[j].getY() &&
            sp.rivalPlayers()[r].getX() > (sp.myPlayers()[j].getX() - margen) &&
            sp.rivalPlayers()[r].getX() < (sp.myPlayers()[j].getX() + margen)) {

          rival = r;
          break;
        }
      }
    }

    return rival;
  }


  private boolean avanzaEnYAPorteria(GameSituations sp) {
    return sp.ballPosition().getY() < posBola.getY();
  }
  //-------------------------------------------------------------------------------------------------------------

  //-------------------------------------------------------------------------------------------------------------
  //							M�todos de c�lculo del movimiento de los jugadores
  //-------------------------------------------------------------------------------------------------------------

  // M�todo que calcula los movimientos que realizar� el jugador que se encuentre
  // en posesi�n de la pelota.
  private void movimientosJugadorPosesion(GameSituations sp, Jugador jugador) {

    // Si el jugador esta en contacto con el bal�n calculamos sus movimientos.
    if (contactoBalon(sp, jugador.getIndice())) {

      // Actualizamos la información de la acciones realizadas.
      if (accionEnProceso != null) {
        acciones.actualizaInformacion(accionEnProceso, 0, 1, 1);
      }
      if (accionAnterior != null) {
        acciones.actualizaInformacion(accionAnterior, 0, 0, 1);
      }

      // La acción en proceso se convierte en la acción anterior.
      accionAnterior = accionEnProceso;
      accionEnProceso = null;

      // Deshabilitamos el tiro por barrido ya que vamos a realizar cualquier acción.
      tiroPorBarrido = false;

      AccionAvanzar avanzar = gen.avanzar(sp, jugador, arrayRivales);
      calculaEejecucionTecnicaBarrido(sp, jugador, avanzar);
      AccionPasar pasar = gen.pasar(sp, jugador, arrayJugadores, arrayRivales);
      AccionTirar tirar = sp.isStarts() ? null : gen.tirar(sp, jugador, true);

      decideAccion(sp, jugador, avanzar, pasar, tirar);
    }
    // Si no estamos en contacto con la pelota vamos a por ella porque o nos
    // han hecho un pase o estamos avanzando.
    else {
      realizaIrAPorBalon(sp, jugador);
    }
  }


  private void calculaEejecucionTecnicaBarrido(GameSituations sp, Jugador jugador,
                                               AccionAvanzar avanzar) {

    // Si no tenemos una ejecución activa cargada y tenemos acción de avanzar calculamos si ejecutarla.
    if (!ejecutarBarrido && (avanzar != null)) {

      // Si es un mediocentro o un delantero.
      if (conjuntoMedios.contains(arrayJugadores[jugador.getIndice()]) ||
          conjuntoDelanteros.contains(arrayJugadores[jugador.getIndice()])) {

        // Validamos que haya más jugadores mios sobre el eje Y para poder arrastrarlos.
        if (masJugadoresSobreY(sp, jugador)) {

          // Si no ganamos por más de 2 goles pasamos a la siguiente comprobación.
          if ((sp.myGoals() - sp.rivalGoals()) < 3) {

            // Indicamos que vamos a ejecutar la técnica.
            ejecutarBarrido = true;
            iteracionUltimoBarrido = sp.iteration();
          }
        }
      }
    }
  }


  private boolean masJugadoresSobreY(GameSituations sp, Jugador jugador) {

    // Inicializamos las variables en las que contaremos el número de jugadores y rivalPlayers sobre Y.
    int jugadoresAdelantados = 0;
    int rivalesAdelantados = 0;

    // Recorremos a todos los jugadores menos el portero.
    for (int i = 1; i < 11; i++) {

      // Si no soy yo mismo.
      if (i != jugador.getIndice()) {

        if (sp.myPlayers()[i].getY() > sp.myPlayers()[jugador.getIndice()].getY()) {
          jugadoresAdelantados++;
        }
      }
    }

    // Recorremos a todos los rivalPlayers menos el portero.
    for (int i = 1; i < 11; i++) {

      if (sp.rivalPlayers()[i].getY() > sp.myPlayers()[jugador.getIndice()].getY()) {
        rivalesAdelantados++;
      }
    }

    if ((jugadoresAdelantados > 0) || (rivalesAdelantados > 0)) {
      return (jugadoresAdelantados >= rivalesAdelantados);
    } else {
      return false;
    }

  }


  private void movimientosPortero(GameSituations sp) {

    double anguloBalon = Constants.centroArcoInf.angle(sp.ballPosition());
    Position posAngulo = Constants.centroArcoInf.moveAngle(anguloBalon, 3, 3);

    // Si tenemos la posesi�n.
    if (posesion) {
      // Si el portero est� en contacto con el bal�n realiza los movimientos del jugador con posesi�n.
      if (jugadorPosesion == 0) {
        movimientosJugadorPosesion(sp, arrayJugadores[0]);
      } else {

        // Si soy el jugador que se encuentra m�s cerca de la pelota voy a por ella.
        if (!sp.isStarts() && llegoAntes(sp, arrayJugadores[0], false)) {

          // A partir de ahora nosotros seremos el jugador que tiene la posesi�n de la pelota.
          jugadorPosesion = arrayJugadores[0].getIndice();
          realizaIrAPorBalon(sp, arrayJugadores[0]);
        }
        // Marcamos en �ngulo
        else {

          // Si el bal�n esta en mi zona y va a porter�a vamos a por el bal�n.
          boolean llegaAPorteria = balonLlegaAPorteria(sp);
          if (llegaAPorteria && arrayJugadores[0].getZona().enMiZona(sp.ballPosition())) {

            realizaIrAPorBalon(sp, arrayJugadores[0]);
          } else {

            realizaIrA(arrayJugadores[0], posAngulo);
          }
        }
      }
    }

    // Si no tenemos la posesi�n.
    else {

      if (llegoAntes(sp, arrayJugadores[0], false)) {

        realizaIrAPorBalon(sp, arrayJugadores[0]);
      }
      // En este caso no somos el jugador que llega antes.
      else {

        // Comprobamos si un jugador mio llega antes.
        if (miJugadorLlegaAntes(sp, true)) {
          realizaIrA(arrayJugadores[0], posAngulo);
        }
        // Un rival llega antes.
        else {

          boolean llegaAPorteria = balonLlegaAPorteria(sp);

          // El bal�n esta en mi zona.
          if (arrayJugadores[0].getZona().enMiZona(sp.ballPosition())) {

            // El bal�n llega a la porter�a
            if (llegaAPorteria) {
              realizaIrAPorBalon(sp, arrayJugadores[0]);
            } else {

              if (avanzaEnYAPorteria(sp)) {
                realizaIrAPorBalon(sp, arrayJugadores[0]);
              } else {
                realizaIrA(arrayJugadores[0], posAngulo);
              }
            }
          }
          // El bal�n no esta en mi zona.
          else {

            if (jugadorRivalPosesion != -1) {
              realizaIrA(arrayJugadores[0], posAngulo);
            } else {

              if (llegaAPorteria) {
                realizaIrAPorBalon(sp, arrayJugadores[0]);
              } else {
                realizaIrA(arrayJugadores[0], posAngulo);
              }
            }
          }
        }
      }
    }
  }


  private void movimientosDefensa(GameSituations sp) {

    // Establecemos los marcajes de la defensa.
    //asignaMarcajesAntiguo(sp, conjuntoDefensas, rivalesSectorDefensa, Entorno.LIMITE_DEFENSA + 8);

    for (Jugador defensa : conjuntoDefensas) {

      // El defensa que tiene la posesi�n de la pelota.
      if (defensa.getIndice() == jugadorPosesion) {
        movimientosJugadorPosesion(sp, defensa);
      }
      // El resto de defensas
      else {

        if (sp.isStarts() && jugadorPosesion == 0) {
          realizaIrA(defensa, Entorno.alineacionNormal[defensa.getIndice()]);
        } else {
          movimientoDefensivo(sp, defensa);
        }
      }
    }
  }


  private void movimientosMedia(GameSituations sp) {

    // Tenemos la posesi�n de la pelota.
    if (posesion) {

      // Vamos a calcular los movimientos de los centrocampistas.
      for (Jugador medio : conjuntoMedios) {

        // El medio que tiene la posesi�n de la pelota.
        if (medio.getIndice() == jugadorPosesion) {
          movimientosJugadorPosesion(sp, medio);
        }
        // El resto de medios
        else {

          // Si soy el medio que se encuentra m�s cerca de la pelota voy a por ella.
          if (!sp.isStarts() && llegoAntes(sp, medio, true)) {

            // A partir de ahora nosotros seremos el jugador que tiene la posesi�n de la pelota.
            jugadorPosesion = medio.getIndice();
            realizaIrAPorBalon(sp, medio);
          } else {

            if (medio.getIndice() == 6 || medio.getIndice() == 10) {
              mueveExtremoSinBalon(sp, medio);
            } else {
              mueveMedioCentrosSinBalon(sp, medio);
            }
          }
        }
      }
    }
    // El otro equipo esta en posesi�n de la pelota.
    else {

      // Establecemos los marcajes de la media.
      //asignaMarcajesAntiguo(sp, conjuntoMedios, rivalesSectorMedio, Entorno.LIMITE_MEDIO);

      for (Jugador medio : conjuntoMedios) {

        movimientoDefensivo(sp, medio);
      }
    }
  }


  private void movimientosDelantera(GameSituations sp) {

    // Si tenemos la posesi�n calculamos los movimientos.
    if (posesion) {

      for (Jugador delantero : conjuntoDelanteros) {

        // El delantero que tiene la posesi�n de la pelota.
        if (delantero.getIndice() == jugadorPosesion) {
          movimientosJugadorPosesion(sp, delantero);
        }
        // El resto de delanteros.
        else {

          // Si soy el delantero que se encuentra m�s cerca de la pelota
          // voy a por ella.
          if (!sp.isStarts() && llegoAntes(sp, delantero, true)) {

            // A partir de ahora nosotros seremos el jugador que tiene la posesi�n de la pelota.
            jugadorPosesion = delantero.getIndice();
            realizaIrAPorBalon(sp, delantero);
          } else {
            mueveDelanteroSinBalon(sp, delantero);
          }
        }
      }
    }
    // Si no tenemos la posesi�n buscamos presionar y robar la pelota.
    else {

      for (Jugador delantero : conjuntoDelanteros) {

        movimientoDefensivo(sp, delantero);
      }
    }
  }


  private void movimientoDefensivo(GameSituations sp, Jugador jugador) {

    // Comprobamos si somos el jugador que llega antes.
    if (!sp.isStarts() && llegoAntes(sp, jugador, true)) {
      realizaIrAPorBalon(sp, jugador);
    }
    // En este caso no somos el jugador que llega antes.
    else {

      // Comprobamos si un jugador mio llega antes.
      if (miJugadorLlegaAntes(sp, true)) {

        // Tengo un marcaje asignado.
        if (jugador.getMarcaje() != null) {

          realizaIrA(jugador,
                     jugador.getMarcaje().getPosicion().moveAngle(
                         jugador.getMarcaje().getPosicion().angle(sp.ballPosition()),
                         Entorno.DISTANCIA_MARCAJE_DEFENSA));
          //realizaIrA(jugador, jugador.getMarcaje().getPosicion());
        }
        // No tengo ning�n marcaje asignado.
        else {

          // Los delanteros solamente bajan hasta el medio del campo.
          if (conjuntoDelanteros.contains(jugador)) {

            double yDelantero;
            if (sp.ballPosition().getY() > Entorno.alineacionNormal[jugador.getIndice()].getY()) {
              yDelantero = Entorno.alineacionNormal[jugador.getIndice()].getY();
            } else {

              if ((sp.ballPosition().getX() < 0 && sp.myPlayers()[jugador.getIndice()].getX() < 0)
                  ||
                  (sp.ballPosition().getX() > 0
                   && sp.myPlayers()[jugador.getIndice()].getX() > 0)) {
                yDelantero = -3;
              } else {
                yDelantero = 2;
              }
            }

            realizaIrA(jugador, new Position(Entorno.alineacionNormal[jugador.getIndice()].getX(),
                                             yDelantero));
          }
          // Los extremos no bajan m�s del l�mite de la defensa.
          else if (jugador.getIndice() == 6 || jugador.getIndice() == 10) {

            double yMedio = Entorno.alineacionNormal[jugador.getIndice()].getY();
            if (sp.ballPosition().getY() < Entorno.alineacionNormal[jugador.getIndice()].getY()) {

              if (sp.ballPosition().getY() < Entorno.LIMITE_DEFENSA - 15) {
                yMedio = Entorno.LIMITE_DEFENSA - 15;
              } else {
                yMedio = sp.ballPosition().getY();
              }
            }

            if (yRivalMasAdelantado > yMedio) {
              yMedio = yRivalMasAdelantado + 10;
            }

            realizaIrA(jugador,
                       new Position(Entorno.alineacionNormal[jugador.getIndice()].getX(), yMedio));
          }
          // Los medio centros vuelven a su posici�n natural, pero modificando su Y para
          // dirigirse hacia la Y del bal�n.
          else if (conjuntoMedios.contains(jugador)) {

            double
                yMedio =
                Math.min(Entorno.alineacionNormal[jugador.getIndice()].getY(),
                         sp.ballPosition().getY());
            if (yMedio < Entorno.LIMITE_DEFENSA) {
              yMedio = yMedio + 8;
            }

            realizaIrA(jugador,
                       new Position(Entorno.alineacionNormal[jugador.getIndice()].getX(), yMedio));
          }
          // Los defensas van a su posici�n natural, pero modificando su Y para
          // dirigirse hacia la Y del rival m�s adelantado.
          else {
            //realizaIrA(jugador, new Posicion(Entorno.alineacionNormal[jugador.getIndice()].getX(),
            //							     Math.min(Entorno.alineacionNormal[jugador.getIndice()].getY(), sp.ballPosition().getY())));

            double xDefensa = Entorno.alineacionNormal[jugador.getIndice()].getX();

            if (sp.ballPosition().getY() < -20) {

              if (jugador.getIndice() == 1) {

                if (sp.ballPosition().getX() < xDefensa) {
                  xDefensa = Math.max(sp.ballPosition().getX(), 7);
                }
              } else if (jugador.getIndice() == 2) {

                if (sp.ballPosition().getX() > xDefensa) {
                  xDefensa = Math.min(sp.ballPosition().getX(), -7);
                }
              }
            }

            realizaIrA(jugador, new Position(xDefensa, Math.min(yRivalMasAdelantado, 0)));
          }
        }

      }
      // Un rival llega antes.
      else {

        // El bal�n esta en mi zona.
        if (jugador.getZona().enMiZona(sp.ballPosition())) {

          realizaIrAPorBalon(sp, jugador);
        }
        // El bal�n no esta en mi zona.
        else {

          // Tengo una marcaje asignado.
          if (jugador.getMarcaje() != null) {

            if (jugadorRivalPosesion == jugador.getMarcaje().getIndice()) {
              realizaIrAPorBalon(sp, jugador);
            } else {
              realizaIrA(jugador,
                         jugador.getMarcaje().getPosicion().moveAngle(
                             jugador.getMarcaje().getPosicion().angle(sp.ballPosition()),
                             Entorno.DISTANCIA_MARCAJE_DEFENSA));
              //realizaIrA(jugador, jugador.getMarcaje().getPosicion());
            }
          }
          // No tengo ningun marcaje asignado.
          else {

            if (sp.ballPosition().nearestIndex(sp.myPlayers()) == jugador.getIndice()) {
              realizaIrAPorBalon(sp, jugador);
            } else {

              // Los delanteros solamente bajan hasta el medio del campo.
              if (conjuntoDelanteros.contains(jugador)) {

                double yDelantero;
                if (sp.ballPosition().getY() > Entorno.alineacionNormal[jugador.getIndice()]
                    .getY()) {
                  yDelantero = Entorno.alineacionNormal[jugador.getIndice()].getY();
                } else {

                  if ((sp.ballPosition().getX() < 0
                       && sp.myPlayers()[jugador.getIndice()].getX() < 0) ||
                      (sp.ballPosition().getX() > 0
                       && sp.myPlayers()[jugador.getIndice()].getX() > 0)) {
                    yDelantero = -3;
                  } else {
                    yDelantero = 2;
                  }
                }

                realizaIrA(jugador,
                           new Position(Entorno.alineacionNormal[jugador.getIndice()].getX(),
                                        yDelantero));
              }
              // Los extremos no bajan m�s del l�mite de la defensa.
              else if (jugador.getIndice() == 6 || jugador.getIndice() == 10) {

                double yMedio = Entorno.alineacionNormal[jugador.getIndice()].getY();
                if (sp.ballPosition().getY() < Entorno.alineacionNormal[jugador.getIndice()]
                    .getY()) {

                  if (sp.ballPosition().getY() < Entorno.LIMITE_DEFENSA - 15) {
                    yMedio = Entorno.LIMITE_DEFENSA - 15;
                  } else {
                    yMedio = sp.ballPosition().getY();
                  }
                }

                if (yRivalMasAdelantado > yMedio) {
                  yMedio = yRivalMasAdelantado + 10;
                }

                realizaIrA(jugador,
                           new Position(Entorno.alineacionNormal[jugador.getIndice()].getX(),
                                        yMedio));
              }
              // Los medio centros vuelven a su posici�n natural, pero modificando su Y para
              // dirigirse hacia la Y del bal�n.
              else if (conjuntoMedios.contains(jugador)) {

                double
                    yMedio =
                    Math.min(Entorno.alineacionNormal[jugador.getIndice()].getY(),
                             sp.ballPosition().getY());
                if (yMedio < Entorno.LIMITE_DEFENSA) {
                  yMedio = yMedio + 8;
                }

                realizaIrA(jugador,
                           new Position(Entorno.alineacionNormal[jugador.getIndice()].getX(),
                                        yMedio));
              }
              // Los defensas van a su posici�n natural, pero modificando su Y para
              // dirigirse hacia la Y del rival m�s adelantado.
              else {
                //realizaIrA(jugador, new Posicion(Entorno.alineacionNormal[jugador.getIndice()].getX(),
                //							     Math.min(Entorno.alineacionNormal[jugador.getIndice()].getY(), sp.ballPosition().getY())));

                double xDefensa = Entorno.alineacionNormal[jugador.getIndice()].getX();

                if (sp.ballPosition().getY() < -20) {

                  if (jugador.getIndice() == 1) {

                    if (sp.ballPosition().getX() < xDefensa) {
                      xDefensa = Math.max(sp.ballPosition().getX(), 7);
                    }
                  } else if (jugador.getIndice() == 2) {

                    if (sp.ballPosition().getX() > xDefensa) {
                      xDefensa = Math.min(sp.ballPosition().getX(), -7);
                    }
                  }
                }

                realizaIrA(jugador, new Position(xDefensa, Math.min(yRivalMasAdelantado, 0)));
              }
            }
          }
        }
      }
    }
  }


  private void mueveMedioCentrosSinBalon(GameSituations sp, Jugador medioCentro) {

    int distJugador = 5;

    // Si se esta llevando a cabo la táctica barrido y el balón esta en el eje Y fuera del área grande
    // y detrás nuestra tenemos que apartarnos.
    if (ejecutarBarrido &&
        (sp.ballPosition().getY() < ((Constants.LARGO_CAMPO_JUEGO / 2)
                                     - Constants.ANCHO_AREA_GRANDE)) &&
        sp.ballPosition().getY() < (sp.myPlayers()[medioCentro.getIndice()].getY() + distJugador)) {
      mueveTacticaBarridoSinBalon(sp, medioCentro);
    }
    // En caso contrario nos movemos normalmente.
    else {

      boolean otraBusqueda = true;
      double incremento = 5;

      double xIzq = Entorno.zona[medioCentro.getIndice()].getXIzq();
      double xDer = Entorno.zona[medioCentro.getIndice()].getXDer();
      double y = Entorno.alineacionNormal[medioCentro.getIndice()].getY();
      if (sp.ballPosition().getY() > y) {

        double variacionY;

        // El bal�n se encuentra en el lado del medio centro.
        if ((Entorno.alineacionNormal[medioCentro.getIndice()].getX() < 0
             && sp.ballPosition().getX() < 0) ||
            (Entorno.alineacionNormal[medioCentro.getIndice()].getX() > 0
             && sp.ballPosition().getX() > 0)) {
          variacionY = 10;
        }
        // El bal�n se encuentra en el lado contrario del extremo.
        else {
          variacionY = 14;
        }

        if ((Math.abs(sp.ballPosition().getY() - sp.myPlayers()[medioCentro.getIndice()].getY())) >
            variacionY) {

          y = sp.ballPosition().getY() - variacionY;
          y = Math.min(y, Entorno.LIMITE_MEDIO - 7);
        }
      } else {
        y = Math.max(Entorno.LIMITE_DEFENSA, sp.ballPosition().getY() - 5);
      }

      // Si el bal�n esta a mi izquierda, cambiamos el signo del incremento para movernos hacia el.
      if (sp.ballPosition().getX() < sp.myPlayers()[medioCentro.getIndice()].getX()) {

        incremento = -incremento;
      }

      Position
          posInicial =
          new Position(Entorno.alineacionNormal[medioCentro.getIndice()].getX(), y);
      Position
          posDefecto =
          new Position(Entorno.alineacionNormal[medioCentro.getIndice()].getX(), y);

      realizaIrA(medioCentro, buscaHueco(sp, medioCentro.getIndice(),
                                         posInicial, posDefecto,
                                         y, xDer, xIzq, incremento, otraBusqueda));
    }
  }


  private void mueveExtremoSinBalon(GameSituations sp, Jugador extremo) {

    int distJugador = 5;

    // Si se esta llevando a cabo la táctica barrido y el balón está detrás nuestra tenemos que apartarnos.
    if (ejecutarBarrido &&
        (sp.ballPosition().getY() < ((Constants.LARGO_CAMPO_JUEGO / 2)
                                     - Constants.ANCHO_AREA_GRANDE)) &&
        sp.ballPosition().getY() < (sp.myPlayers()[extremo.getIndice()].getY() + distJugador)) {
      mueveTacticaBarridoSinBalon(sp, extremo);
    }
    // En caso contrario nos movemos normalmente.
    else {

      boolean otraBusqueda = false;
      double incremento = 2.5;

      double xIzq;
      double xDer;

      double variacionY;
      double y;

      // El bal�n se encuentra en el lado del extremo.
      if ((Entorno.alineacionNormal[extremo.getIndice()].getX() < 0 && sp.ballPosition().getX() < 0)
          ||
          (Entorno.alineacionNormal[extremo.getIndice()].getX() > 0
           && sp.ballPosition().getX() > 0)) {
        variacionY = 14;
      }
      // El bal�n se encuentra en el lado contrario del extremo.
      else {
        variacionY = 7;
      }

      if (sp.ballPosition().getY() < Entorno.LIMITE_DEFENSA) {
        y = Entorno.LIMITE_DEFENSA;
      } else {
        y = sp.ballPosition().getY() + variacionY;
      }

      // Si el jugador que tiene el bal�n no avanza, nosotros seguimos avanzando para abrir m�s huecos.
      if (Math.abs(sp.myPlayers()[extremo.getIndice()].getY() - y) < variacionY) {

        // Seguimos avnazando hasta cumplir una distancia m�xima.
        if (Math.abs(sp.myPlayers()[extremo.getIndice()].getY() - sp.ballPosition().getY()) < 21) {
          y = y + 7;
        }
      }

      if (y > Entorno.LIMITE_MEDIO + 19) {
        y = Entorno.LIMITE_MEDIO + 19;
      }

      Position posInicial = new Position(sp.myPlayers()[extremo.getIndice()].getX(), y);
      Position posDefecto;

      // El extremo esta en el lado izquierdo.
      if (Entorno.alineacionNormal[extremo.getIndice()].getX() < 0) {

        // El bal�n esta en mi lado.
        if (sp.ballPosition().getX() < 0) {

          xIzq = Entorno.zona[extremo.getIndice()].getXIzq();
          xDer = Entorno.alineacionNormal[extremo.getIndice()].getX();
          posDefecto = new Position(xIzq, y);
          incremento = -incremento;
          otraBusqueda = true;
        }
        // El bal�n esta en el otro lado.
        else {
          xIzq = Entorno.alineacionNormal[extremo.getIndice()].getX();
          xDer = Entorno.zona[extremo.getIndice()].getXDer();

          // Si el bal�n se encuentra por encima del l�mite permitido, reduciremos el rango de movimiento
          // para buscar un pase de gol.
          if (sp.ballPosition().getY() >= 40) {

            xIzq = xDer;
            xDer = xDer + 10;
            posInicial = new Position(xDer, y);
          }

          posDefecto = new Position(xDer, y);
        }
      }
      // El extremo esta en el lado derecho.
      else {

        // El bal�n esta en mi lado.
        if (sp.ballPosition().getX() > 0) {

          xIzq = Entorno.alineacionNormal[extremo.getIndice()].getX();
          xDer = Entorno.zona[extremo.getIndice()].getXDer();
          posDefecto = new Position(xDer, y);
          otraBusqueda = true;
        }
        // El bal�n esta en el otro lado.
        else {

          xIzq = Entorno.zona[extremo.getIndice()].getXIzq();
          xDer = Entorno.alineacionNormal[extremo.getIndice()].getX();
          incremento = -incremento;

          // Si el bal�n se encuentra por encima del l�mite permitido, reduciremos el rango de movimiento
          // para buscar un pase de gol.
          if (sp.ballPosition().getY() >= 40) {

            xDer = xIzq;
            xIzq = xIzq - 10;
            posInicial = new Position(xIzq, y);
            incremento = -incremento;
          }

          posDefecto = new Position(xIzq, y);
        }
      }

      realizaIrA(extremo, buscaHueco(sp, extremo.getIndice(),
                                     posInicial, posDefecto,
                                     y, xDer, xIzq, incremento, otraBusqueda));
    }
  }


  private void mueveDelanteroSinBalon(GameSituations sp, Jugador delantero) {

    boolean otraBusqueda = false;
    double incremento = 5;

    double distanciaD = (delantero.getIndice() == 8) ? 35 : 25;
    double limiteY = (delantero.getIndice() == 8) ? 47 : 44;

    double xIzq;
    double xDer;
    double y = sp.ballPosition().getY() + distanciaD;

    // Si la y supera el l�mite permitido para ubicar a los delanteros.
    if (y > limiteY) {

      // Si existe mucha distancia entre el bal�n y el delantero vamos a mandar a un delantero a recibir.
      if (limiteY - sp.ballPosition().getY() > 10) {

        // El delantero del lado contrario baja a recibir el bal�n.
        if (sp.ballPosition().getX() > 0
            && Entorno.alineacionNormal[delantero.getIndice()].getX() < 0 ||
            sp.ballPosition().getX() < 0
            && Entorno.alineacionNormal[delantero.getIndice()].getX() > 0) {

          y = sp.ballPosition().getY() + 10;
        }
        // El delantero del mismo lado, mantiene la posici�n para abrir el hueco.
        else {
          y = limiteY;
        }
      }
      // Si no existe mucha distancia mantenemos la posici�n.
      else {
        y = limiteY;
      }
    }
    // Si el bal�n no supera el medio campo, situamos la Y en el medio del campo.
    else if (y < 0) {
      y = 0;
    }

    // Vamos a calcular los valores de b�squeda de ubicaci�n del delantero en el eje X.
    Position posInicial = new Position(sp.myPlayers()[delantero.getIndice()].getX(), y);
    Position posDefecto = new Position(Entorno.alineacionNormal[delantero.getIndice()].getX(), y);

    // El delantero esta en el lado izquierdo.
    if (Entorno.alineacionNormal[delantero.getIndice()].getX() < 0) {

      // El bal�n esta en mi lado.
      if (sp.ballPosition().getX() < 0) {

        xIzq = Entorno.zona[delantero.getIndice()].getXIzq();
        xDer = Entorno.zona[delantero.getIndice()].getXDer();
        incremento = -incremento;
        otraBusqueda = true;
      }
      // El bal�n esta en el otro lado.
      else {

        xIzq = Entorno.alineacionNormal[delantero.getIndice()].getX();
        xDer = Entorno.zona[delantero.getIndice()].getXDer();

        // Si el bal�n se encuentra por encima del l�mite permitido, reduciremos el rango de movimiento
        // para buscar un pase de gol.
        if (sp.ballPosition().getY() >= limiteY) {

          // Reasignamos las variables que utilizaremos para buscar el hueco, forzando el movimiento
          // del delantero en busca del pase de gol.
          y = 45;
          xIzq = xDer + 5;
          posInicial = new Position(xDer, y);
          posDefecto = new Position(xDer, y);
        }
        // Si el bal�n se encuentra por debajo del l�mite reducimos la Y para abrir m�s el hueco
        // y poder recibir un pase.
        else {
          y = y - 6;
        }
      }
    }
    // El delantero esta en el lado derecho.
    else {

      // El bal�n esta en mi lado.
      if (sp.ballPosition().getX() > 0) {

        xIzq = Entorno.zona[delantero.getIndice()].getXIzq();
        xDer = Entorno.zona[delantero.getIndice()].getXDer();
        otraBusqueda = true;
      }
      // El bal�n esta en el otro lado.
      else {

        xIzq = Entorno.zona[delantero.getIndice()].getXIzq();
        xDer = Entorno.alineacionNormal[delantero.getIndice()].getX();
        incremento = -incremento;

        // Si el bal�n se encuentra por encima del l�mite permitido, reduciremos el rango de movimiento
        // para buscar un pase de gol.
        if (sp.ballPosition().getY() >= limiteY) {

          // Reasignamos las variables que utilizaremos para buscar el hueco, forzando el movimiento
          // del delantero en busca del pase de gol.
          y = 45;
          xDer = xIzq + 5;
          posInicial = new Position(xIzq, y);
          posDefecto = new Position(xIzq, y);
        }
        // Si el bal�n se encuentra por debajo del l�mite reducimos la Y para abrir m�s el hueco
        // y poder recibir un pase.
        else {
          y = y - 6;
        }
      }
    }

    // Mandamos al delantero a la posici�n calculada por el m�todo buscaHueco a partir de los par�metros
    // enviados.
    realizaIrA(delantero, buscaHueco(sp, delantero.getIndice(),
                                     posInicial, posDefecto,
                                     y, xDer, xIzq, incremento, otraBusqueda));
  }


  private Position buscaHueco(GameSituations sp,
                              int j,
                              Position posInicial, Position posDefecto,
                              double posY,
                              double xDer, double xIzq,
                              double incremento, boolean otraBusqueda) {

    boolean rivalInterceptaPase;
    boolean salir = false;

    Position pos = new Position(posInicial);

    do {

      rivalInterceptaPase =
          gen.rivalEnTrayectoria(sp, sp.ballPosition(), pos, Entorno.DISTANCIA_CORTE_PASE);

      if (rivalInterceptaPase) {

        if ((incremento > 0 && pos.getX() < xDer) ||
            (incremento < 0 && pos.getX() > xIzq)) {

          pos = pos.movePosition(incremento, 0);
        } else if (otraBusqueda) {

          otraBusqueda = false;
          pos = new Position(posInicial);
          incremento = -incremento;
        } else {
          salir = true;
        }
      }
    } while (rivalInterceptaPase && !salir);

    if (rivalInterceptaPase) {
      pos = posDefecto;
    }

    return pos;
  }


  private void mueveTacticaBarridoSinBalon(GameSituations sp, Jugador jugador) {

    double distancia = 10;
    double diffBalonX = sp.ballPosition().getX() - sp.myPlayers()[jugador.getIndice()].getX();

    // Si el jugador se encuentra más cerca sobre el eje de las X de lo permitido, lo alejamos.
    if (Math.abs(diffBalonX) < distancia) {

      // Si el balón esta a mi izquierda.
      if (diffBalonX < 0) {

        // Lo podemos mover hacia la derecha.
        if ((sp.ballPosition().getX() + distancia) < Constants.LARGO_CAMPO_JUEGO) {

          realizaIrA(jugador, new Position(sp.ballPosition().getX() + distancia,
                                           sp.myPlayers()[jugador.getIndice()].getY()));
        }
        // En este caso nos salimos del campo por lo que nos dirigimos hacia la izquierda pero hacia arriba tambien.
        else {
          realizaIrA(jugador, new Position(sp.ballPosition().getX() - distancia,
                                           Math.min(
                                               (sp.myPlayers()[jugador.getIndice()].getY() + 20),
                                               Constants.LARGO_CAMPO_JUEGO)));
        }
      }
      // Si el balón esta a mi derecha.
      else {

        // Lo podemos mover hacia la izquierda.
        if ((sp.ballPosition().getX() - distancia) > -Constants.LARGO_CAMPO_JUEGO) {

          realizaIrA(jugador, new Position(sp.ballPosition().getX() - distancia,
                                           sp.myPlayers()[jugador.getIndice()].getY()));
        }
        // En este caso nos salimos del campo por lo que nos dirigimos hacia la derecha pero hacia arriba tambien.
        else {
          realizaIrA(jugador, new Position(sp.ballPosition().getX() + distancia,
                                           Math.min(
                                               (sp.myPlayers()[jugador.getIndice()].getY() + 20),
                                               Constants.LARGO_CAMPO_JUEGO)));
        }
      }
    }
    // Si estamos fuera del área rival, incrementamos la Y para acompañar al jugador en el movimiento dándole opciones
    // de pase, tiro, etc.
    else if (sp.ballPosition().getY() < ((Constants.LARGO_CAMPO_JUEGO / 2)
                                         - Constants.ANCHO_AREA_GRANDE)) {

      realizaIrA(jugador, new Position(sp.myPlayers()[jugador.getIndice()].getX(),
                                       sp.ballPosition().getY() + distancia));
    }

  }
  //-------------------------------------------------------------------------------------------------------------


  //-------------------------------------------------------------------------------------------------------------
  //	M�todos empleados en la toma de deciciones de la acci�n a realizar por el jugador que tiene la posesi�n
  //-------------------------------------------------------------------------------------------------------------
  private void decideAccion(GameSituations sp,
                            Jugador jugador,
                            AccionAvanzar avanzar,
                            AccionPasar pasar,
                            AccionTirar tirar) {

    // Solamente podemos avanzar
    if (avanzar != null && pasar == null && tirar == null) {

      if (jugador.getIndice() != 0) {

        realizaAvance(avanzar, sp);
      } else {

        // No queremos que el portero avance (no es seguro). Como podemos avanzar, podemos alcanzar
        // un angulo suficiente para realizar un despeje orientado.
        realizaDespeje(sp, jugador);
      }
    }

    // Solamente podemos pasar
    else if (avanzar == null && pasar != null && tirar == null) {
      decideP(sp, jugador, pasar);
    }

    // Solamente podemos tirar
    else if (avanzar == null && pasar == null && tirar != null) {
      decideT(sp, jugador, tirar);
    }

    // Podemos avanzar y pasar
    else if (avanzar != null && pasar != null && tirar == null) {
      decideAP(sp, jugador, avanzar, pasar);
    }

    // Podemos avanzar y tirar
    else if (avanzar != null && pasar == null && tirar != null) {
      decideAT(sp, jugador, avanzar, tirar);
    }

    // Podemos pasar y tirar
    else if (avanzar == null && pasar != null && tirar != null) {
      decidePT(sp, jugador, pasar, tirar);
    }

    // Podemos pasar, avanzar y tirar
    else if (avanzar != null && pasar != null && tirar != null) {
      decideAPT(sp, jugador, avanzar, pasar, tirar);
    }

    // No podemos realizar ninguna acci�n
    else {
      decide(sp, jugador);
    }
  }


  private void decideP(GameSituations sp, Jugador jugador, AccionPasar pasar) {

    // No es el portero.
    if (jugador.getIndice() != 0) {

      // Si el pase es hacia adelante realizamos el pase.
      if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].getY() >=
          sp.myPlayers()[jugador.getIndice()].getY()) {

        // Decision
        AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
        if (regatear != null && !regatear.regateHaciaAdelante()) {
          regatear = null;
        }

        Integer eleccion = acciones.decide(new Accion[]{pasar, regatear}, sp, sectorActivo);
        if (eleccion != null && eleccion < 2) {
          if (eleccion == 0) {
            realizaPase(pasar, sp);
          } else {
            realizaRegate(regatear, sp);
          }
        }
        // Replicamos lo anterior por si no obtenemos información.
        else {
          realizaPase(pasar, sp);
        }
      }
      // El pase es hacia atras.
      else {

        // Si el destino del pase se encuentra dentro del �rea de forzado de tiro se la pasamos.
        if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].
            distance(Constants.centroArcoSup) <= Entorno.DISTANCIA_FORZAR_TIRO + 4) {
          realizaPase(pasar, sp);
        }
        // Si el destino del pase se encuentra dentro del area y tiene tiro a puerta vac�a se la pasamos.
        else if ((sp.myPlayers()[pasar.jugadorDestino().getIndice()].getY() > (
            (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE)) &&
                 (Math.abs(sp.myPlayers()[pasar.jugadorDestino().getIndice()].getX())
                  < Constants.LARGO_AREA_GRANDE / 2) &&
                 gen.puertaVacia(sp, sp.myPlayers()[pasar.jugadorDestino().getIndice()])) {
          realizaPase(pasar, sp);
        } else {

          // Nos encontramos en una situaci�n en la que tenemos que buscar la porter�a.
          if (sp.ballPosition().getY() > ((Constants.LARGO_CAMPO_JUEGO / 2)
                                          - Constants.ANCHO_AREA_GRANDE) &&
              Math.abs(sp.ballPosition().getX()) > Constants.posteDerArcoSup.getX() + 1.5) {
	        			
	        			/*
		                PROBAMOS decision
		                AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
		                if (regatear != null && regatear.regateHaciaPorteria()) {
		                	realizaRegate(regatear, sp);
		                }
		                else {
		                	
		                	AccionPasarAlHueco paseAlHueco = gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales, null);
		                	if (paseAlHueco != null && paseAlHueco.paseHaciaElArea()) {
		                		realizaPaseAlHueco(paseAlHueco, sp);
		                	}
		                	else {
		                		realizaPase(pasar, sp);
		                	}
		                }
		                */

            // Decision
            AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
            if (regatear != null && !regatear.regateHaciaPorteria()) {
              regatear = null;
            }

            AccionPasarAlHueco
                paseAlHueco =
                gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales, null);
            if (paseAlHueco != null && !paseAlHueco.paseHaciaElArea()) {
              paseAlHueco = null;
            }

            Integer
                eleccion =
                acciones.decide(new Accion[]{pasar, regatear, paseAlHueco}, sp, sectorActivo);
            if (eleccion != null && eleccion < 3) {
              if (eleccion == 0) {
                realizaPase(pasar, sp);
              } else if (eleccion == 1) {
                realizaRegate(regatear, sp);
              } else {
                realizaPaseAlHueco(paseAlHueco, sp);
              }
            }
            // Replicamos lo anterior por si no obtenemos información.
            else {
              if (regatear != null && regatear.regateHaciaPorteria()) {
                realizaRegate(regatear, sp);
              } else {
                if (paseAlHueco != null && paseAlHueco.paseHaciaElArea()) {
                  realizaPaseAlHueco(paseAlHueco, sp);
                } else {
                  realizaPase(pasar, sp);
                }
              }
            }
          }
          // En este caso nos basta con buscar el avance en el eje Y.
          else {
	        			
	        			/*
	        			PROBAMOS decision
		                AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
		                if (regatear != null && regatear.regateHaciaAdelante()) {
		                	realizaRegate(regatear, sp);
		                }
		                else {
		                	
		                	AccionPasarAlHueco paseAlHueco = gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales, null);
		                	if (paseAlHueco != null && paseAlHueco.paseHaciaAdelante()) {
		                		realizaPaseAlHueco(paseAlHueco, sp);
		                	}
		                	else {
		                		realizaPase(pasar, sp);
		                	}
		                }
		                */

            // Decision
            AccionRegatear
                regatear = gen.regatear(sp, jugador, arrayRivales);
            if (regatear != null && !regatear.regateHaciaAdelante()) {
              regatear = null;
            }

            AccionPasarAlHueco
                paseAlHueco = gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales, null);
            if (paseAlHueco != null && !paseAlHueco.paseHaciaAdelante()) {
              paseAlHueco = null;
            }

            Integer
                eleccion =
                acciones.decide(new Accion[]{pasar, regatear, paseAlHueco}, sp, sectorActivo);
            if (eleccion != null && eleccion < 3) {
              if (eleccion == 0) {
                realizaPase(pasar, sp);
              } else if (eleccion == 1) {
                realizaRegate(regatear, sp);
              } else {
                realizaPaseAlHueco(paseAlHueco, sp);
              }
            } else {
              // Replicamos lo anterior por si no obtenemos información.
              if (regatear != null && regatear.regateHaciaAdelante()) {
                realizaRegate(regatear, sp);
              } else {
                if (paseAlHueco != null && paseAlHueco.paseHaciaAdelante()) {
                  realizaPaseAlHueco(paseAlHueco, sp);
                } else {
                  realizaPase(pasar, sp);
                }
              }
            }
          }
        }
      }
    }
    // Es el portero
    else {

      // El portero directamente pasa la pelota.
      realizaPase(pasar, sp);
    }
  }


  private void decideT(GameSituations sp, Jugador jugador, AccionTirar tirar) {

    // Si es a puerta vac�a o tiro seguro.
    if (tirar.tiroSeguro()) {
      realizaTiro(tirar, sp);
    } else {

      double variacion = 15;
      double anguloTiro = Math.toDegrees(sp.ballPosition().angle(Constants.centroArcoSup));

      // Tenemos un �ngulo suficiente para realizar el tiro.
      if (anguloTiro > (0 + variacion) && (anguloTiro < (180 - variacion))) {

        if (tirar.dentroDeForzado()) {

          if (tirar.aPuertaVacia()) {
            realizaTiro(tirar, sp);
          } else if (tirar.variacion() > 3) {
            realizaTiro(tirar, sp);
          } else {
            			
            			/*
            			PROBAMOS Decision
            			AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
            			if (regatear != null && contRegate < 5) {
            				realizaRegate(regatear, sp);
            			}
            			else {
            				realizaTiro(tirar, sp);
            			}
            			*/

            // Decision
            AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
            if (contRegate > 5) {
              regatear = null;
            }

            Integer eleccion = acciones.decide(new Accion[]{tirar, regatear}, sp, sectorActivo);
            if (eleccion != null && eleccion < 2) {
              if (eleccion == 0) {
                realizaTiro(tirar, sp);
              } else {
                realizaRegate(regatear, sp);
              }
            }
            // Replicamos lo anterior por si no obtenemos información.
            else {
              if (regatear != null && contRegate < 5) {
                realizaRegate(regatear, sp);
              } else {
                realizaTiroTrallon(sp, jugador);
              }
            }
          }
        } else {

          if (tirar.variacion() > 6) {
            realizaTiro(tirar, sp);
          } else {

            // Buscamos un regate hacia la porter�a ya que el tiro no es fiable.
            AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
            if (regatear != null && regatear.regateHaciaPorteria()) {
              realizaRegate(regatear, sp);
            } else {
              realizaTiroTrallon(sp, jugador);
            }
          }
        }
      }
      // No tenemos un �ngulo suficiente para realizar el tiro.
      else {

        // Buscamos un regate hacia la porter�a ya que el tiro no es fiable.
        		/*
        		PROBAMOS Decision        		
        		AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
    			if (regatear != null && regatear.regateHaciaPorteria()) {
    				realizaRegate(regatear, sp);
    			}
    			else {
    				
    				// Buscamos el pase al hueco restringiendo la b�squeda sobre la x para que busque el pase
    				// cercano a la porteria.
    				AccionPasarAlHueco pasarAlHueco = gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales,
    																   Constants.posteDerArcoSup.getX() + 1.5);
    				if (pasarAlHueco != null && pasarAlHueco.paseHaciaElArea()) {
    					realizaPaseAlHueco(pasarAlHueco, sp);
    				}
    				else {
        				realizaTiro(tirar, sp);        					
    				}
    			}
    			*/

        // Decision
        AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
        if (regatear != null && !regatear.regateHaciaPorteria()) {
          regatear = null;
        }

        AccionPasarAlHueco
            pasarAlHueco =
            gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales,
                             Constants.posteDerArcoSup.getX() + 1.5);
        if (pasarAlHueco != null && !pasarAlHueco.paseHaciaElArea()) {
          pasarAlHueco = null;
        }

        Integer
            eleccion =
            acciones.decide(new Accion[]{tirar, regatear, pasarAlHueco}, sp, sectorActivo);
        if (eleccion != null && eleccion < 3) {
          if (eleccion == 0) {
            realizaTiro(tirar, sp);
          } else if (eleccion == 1) {
            realizaRegate(regatear, sp);
          } else {
            realizaPaseAlHueco(pasarAlHueco, sp);
          }
        }
        // Replicamos lo anterior por si no obtenemos información.
        else {
          if (regatear != null && regatear.regateHaciaPorteria()) {
            realizaRegate(regatear, sp);
          } else {

            // Buscamos el pase al hueco restringiendo la b�squeda sobre la x para que busque el pase
            // cercano a la porteria.
            if (pasarAlHueco != null && pasarAlHueco.paseHaciaElArea()) {
              realizaPaseAlHueco(pasarAlHueco, sp);
            } else {
              realizaTiro(tirar, sp);
            }
          }
        }
      }
    }
  }

  private void decideAP(GameSituations sp, Jugador jugador,
                        AccionAvanzar avanzar, AccionPasar pasar) {

    // No es el portero.
    if (jugador.getIndice() != 0) {

      char demarcacion = conjuntoDelanteros.contains(jugador) ? 'D' : 'M';

      // Si el destino del pase tiene tiro a puerta vac�a y se encuentra dentro del �rea de forzado
      // de tiro se la pasamos.
      if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].
          distance(Constants.centroArcoSup) < Entorno.DISTANCIA_FORZAR_TIRO &&
          gen.puertaVacia(sp, sp.myPlayers()[pasar.jugadorDestino().getIndice()])) {

        realizaPase(pasar, sp);
      } else if (ejecutarBarrido) {
        realizaAvance(avanzar, sp);
      }
      // Si el jugador al que vamos a pasar la pelota est� mas adelantado, calculamos la mejor opci�n
      // a realizar.
      else if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].getY() >=
               sp.myPlayers()[jugador.getIndice()].getY()) {

        // Recuperemos los �ndices de los rivalPlayers mas cercanos que esten m�s adelantados sobre el eje Y
        // tanto del jugador que tiene la posesi�n como del jugador destino del pase.
        Integer rivalCercanoDestino = rivalCercanoSobreY(sp, pasar.jugadorDestino().getIndice());
        Integer rivalCercanoOrigen = rivalCercanoSobreY(sp, jugador.getIndice());

        // Si tenemos rivalPlayers por encima del eje Y.
        if (rivalCercanoDestino != null && rivalCercanoOrigen != null) {

          double
              distOrigen =
              sp.rivalPlayers()[rivalCercanoOrigen].distance(sp.myPlayers()[jugador.getIndice()]);
          double
              distDestino =
              sp.rivalPlayers()[rivalCercanoDestino]
                  .distance(sp.myPlayers()[pasar.jugadorDestino().getIndice()]);

          // Si el rival del destino esta algo m�s alejado del jugador origen.
          if ((distDestino - distOrigen) > 10) {
            realizaPase(pasar, sp);
          } else {

            // Movimiento de avance de los delanteros.
            if (demarcacion == 'D') {

              // Calculamos aleatoriamente si realizamos un pase o avanzamos, siendo m�s probable
              // realizar la acci�n de avance.
              if (rand.nextInt(10) > 2) {
                realizaAvance(avanzar, sp);
              } else {
                realizaPase(pasar, sp);
              }
            }
            // Movimiento de avance de los medios.
            else {
              // Calculamos aleatoriamente si realizamos un pase o seguimos avanzando, teniendo en
              // cuenta que cuantas m�s veces hayamos realizado avances, menos probable es volver
              // a avanzar y que es m�s probable avanzar cuanto m�s alejados estemos del arco rival.
              int progresion = (sp.myPlayers()[jugador.getIndice()].getY()) < 8 ? 2 : 1;
              if (rand.nextInt(contAvance + progresion) <= 1) {
                realizaAvance(avanzar, sp);
              } else {
                realizaPase(pasar, sp);
              }
            }

						/* ANULAMOS LA DECISION PORQUE EN ESTE CASO NO NOS OFRECE BUENOS RESULTADOS
						// Decision
						Integer eleccion = acciones.decide(new Accion[]{avanzar, pasar}, sp, sectorActivo);
						if (eleccion != null && eleccion < 2) {
							if (eleccion == 0) {
								realizaAvance(avanzar, sp);
				            }
				            else {
				              realizaPase(pasar, sp);
				            }
						}
						// Replicamos lo anterior por si no obtenemos información.
				        else {
				        	
							// Movimiento de avance de los delanteros.
							if (demarcacion == 'D') {

								// Calculamos aleatoriamente si realizamos un pase o avanzamos, siendo m�s probable
								// realizar la acci�n de avance.
								if (rand.nextInt(10) > 2) {
									realizaAvance(avanzar, sp);
								} else {
									realizaPase(pasar, sp);
								}
							}
							// Movimiento de avance de los medios.
							else {
								// Calculamos aleatoriamente si realizamos un pase o seguimos avanzando, teniendo en
								// cuenta que cuantas m�s veces hayamos realizado avances, menos probable es volver
								// a avanzar y que es m�s probable avanzar cuanto m�s alejados estemos del arco rival.
								int progresion = (sp.myPlayers()[jugador.getIndice()].getY()) < 8 ? 2 :1;
								if (rand.nextInt(contAvance + progresion) <= 1) {
									realizaAvance(avanzar, sp);
								}
								else {
									realizaPase(pasar, sp);
								}
							}
				        }
				        */
          }
        }
        // Si el jugador con la posesi�n no tiene rivalPlayers por encima del eje Y.
        else if (rivalCercanoOrigen == null) {

          // Si es el delantero avanza directamente.
          if (demarcacion == 'D') {
            realizaAvance(avanzar, sp);
          } else {

            // Si estamos por debajo del límite de nuestra zona avanzamos.
            if (sp.ballPosition().getY() < jugador.getZona().getYSup()) {
              realizaAvance(avanzar, sp);
            } else {
              realizaPase(pasar, sp);
            }

          }
        }
        // Si el jugador destino no tiene rivalPlayers por encima del eje Y le pasamos la pelota.
        else {
          realizaPase(pasar, sp);
        }
      } else {

        // Si el jugador destino esta dentro del área de la pasamos.
        if (sp.ballPosition().getY() > ((Constants.LARGO_CAMPO_JUEGO / 2)
                                        - Constants.ANCHO_AREA_GRANDE) &&
            Math.abs(sp.ballPosition().getX()) > Constants.posteDerArcoSup.getX() + 1.5 &&

            sp.myPlayers()[pasar.jugadorDestino().getIndice()].getY() >=
            ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE) &&
            Math.abs(sp.myPlayers()[pasar.jugadorDestino().getIndice()].getX()) <
            Constants.posteDerArcoSup.getX() + 1.5) {

          realizaPase(pasar, sp);
        } else {
          realizaAvance(avanzar, sp);
        }
      }
    }

    // Es el portero
    else {

      // El portero directamente pasa la pelota.
      realizaPase(pasar, sp);
    }
  }


  private void decideAT(GameSituations sp, Jugador jugador,
                        AccionAvanzar avanzar, AccionTirar tirar) {

    // No es el portero.
    if (jugador.getIndice() != 0) {

      // Si es a puerta vac�a o tiro seguro.
      if (tirar.tiroSeguro()) {
        realizaTiro(tirar, sp);
      } else if (ejecutarBarrido) {

        // Si nos encontraoms a una distancia inferior a la del punto de penaltie tiraremos con
        // mayor probabilidad que avanzamos.
        if (sp.ballPosition().distance(Constants.centroArcoSup) <= Constants.DISTANCIA_PENAL) {

          if (rand.nextDouble() < 0.7) {
            realizaTiroTrallon(sp, jugador);
          } else {
            realizaAvance(avanzar, sp);
          }
        } else {
          realizaAvance(avanzar, sp);
        }
      } else {

        double variacion = 15;
        double anguloTiro = Math.toDegrees(sp.ballPosition().angle(Constants.centroArcoSup));

        // Tenemos un �ngulo suficiente para realizar el tiro.
        if (anguloTiro > (0 + variacion) && (anguloTiro < (180 - variacion))) {

          if (tirar.dentroDeForzado()) {

            if (tirar.aPuertaVacia()) {
              realizaTiro(tirar, sp);
            } else if (tirar.variacion() > 3) {
              realizaTiro(tirar, sp);
            } else {
              realizaAvance(avanzar, sp);
            }
          } else {
	        			
	        			/*
	        			PROBAMOS Decision
	        			// Si tenemos una variacion muy considerable (ya que podemos avanzar) realizamos el tiro.
	        			if (tirar.variacion() > 5) {
	        				realizaTiro(tirar, sp);
	        			}
	        			else {
		            		realizaAvance(avanzar, sp);
	        			}
	        			*/

            // Decision
            Integer eleccion = acciones.decide(new Accion[]{tirar, avanzar}, sp, sectorActivo);
            if (eleccion != null && eleccion < 2) {
              if (eleccion == 0) {
                realizaTiro(tirar, sp);
              } else {
                realizaAvance(avanzar, sp);
              }
            }
            // Replicamos lo anterior por si no obtenemos información.
            else {
              // Si tenemos una variacion muy considerable (ya que podemos avanzar) realizamos el tiro.
              if (tirar.variacion() > 6) {
                realizaTiroTrallon(sp, jugador);
              } else {
                realizaAvance(avanzar, sp);
              }
            }
          }
        }
        // No tenemos un �ngulo suficiente para realizar el tiro.
        else {
          realizaAvance(avanzar, sp);
        }
      }
    }

    // Es el portero
    else {
      realizaTiro(tirar, sp);
    }
  }


  private void decidePT(GameSituations sp, Jugador jugador,
                        AccionPasar pasar, AccionTirar tirar) {

    // No es el portero.
    if (jugador.getIndice() != 0) {

      // Si es un tiro seguro o a puerta vacia tiramos
      if (tirar.tiroSeguro()) {
        realizaTiro(tirar, sp);
      }

      // Si no es un tiro a puerta vacia ni seguro
      else {

        // Si el destino del pase tiene tiro a puerta vac�a y se encuentra dentro del �rea de forzado
        // de tiro se la pasamos.
        if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].
            distance(Constants.centroArcoSup) < Entorno.DISTANCIA_FORZAR_TIRO &&
            gen.puertaVacia(sp, sp.myPlayers()[pasar.jugadorDestino().getIndice()])) {

          realizaPase(pasar, sp);
        }
        // Dentro del �rea de forzado de tiro tiramos
        else if (tirar.dentroDeForzado()) {

          if (tirar.aPuertaVacia()) {
            realizaTiro(tirar, sp);
          } else if (tirar.variacion() > 3) {
            realizaTiro(tirar, sp);
          } else {
            AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
            if (regatear != null && contRegate < 5) {
              realizaRegate(regatear, sp);
            } else {
              realizaTiroTrallon(sp, jugador);
            }
          }
        } else {

          Accion tirarDestino = gen.tirar(sp, pasar.jugadorDestino(), false);

          // Si el jugador destino del pase est� m�s adelantado y puede tirar se la pasamos.
          if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].getY() >
              sp.myPlayers()[jugador.getIndice()].getY() &&
              tirarDestino != null) {

            realizaPase(pasar, sp);
          }
          // Si podemos regatear cuanto m�s grande sea la distancia de tiro m�s regatearemos.
          else {

            if (tirar.variacion() > 6) {
              realizaTiroTrallon(sp, jugador);
            } else {

              AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
              if (regatear != null && contRegate < 5) {
                realizaRegate(regatear, sp);
              } else {
                realizaPase(pasar, sp);
              }
            }
          }
        }
      }
    }

    // Es el portero
    else {

      // Si estamos dentro del l�mite de forzado de tiro y tenemos un tiro con �ngulo, tiramos, en caso
      // contrario pasamos
      if (tirar.dentroDeForzado()) {

        if (tirar.variacion() > 3) {
          realizaTiro(tirar, sp);
        } else {
          AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
          if (regatear != null && contRegate < 5) {
            realizaRegate(regatear, sp);
          } else {
            realizaTiro(tirar, sp);
          }
        }
      } else {
        realizaPase(pasar, sp);
      }
    }
  }


  private void decideAPT(GameSituations sp, Jugador jugador,
                         AccionAvanzar avanzar, AccionPasar pasar, AccionTirar tirar) {

    // No es el portero.
    if (jugador.getIndice() != 0) {

      // Si es un tiro seguro o a puerta vacia tiramos.
      if (tirar.tiroSeguro()) {
        realizaTiro(tirar, sp);
      }
      // Si el destino del pase tiene tiro a puerta vac�a y se encuentra dentro del �rea de forzado
      // de tiro se la pasamos.
      else if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].
          distance(Constants.centroArcoSup) < Entorno.DISTANCIA_FORZAR_TIRO &&
               gen.puertaVacia(sp, sp.myPlayers()[pasar.jugadorDestino().getIndice()])) {

        realizaPase(pasar, sp);
      } else if (ejecutarBarrido) {

        // Si nos encontraoms a una distancia inferior a la del punto de penaltie tiraremos con
        // mayor probabilidad que avanzamos.
        if (sp.ballPosition().distance(Constants.centroArcoSup) <= Constants.DISTANCIA_PENAL) {

          if (rand.nextDouble() < 0.7) {
            realizaTiroTrallon(sp, jugador);
          } else {
            realizaAvance(avanzar, sp);
          }
        } else {
          realizaAvance(avanzar, sp);
        }
      }
      // Dentro del l�mite de forzado de tiro.
      else if (tirar.dentroDeForzado()) {

        if (tirar.aPuertaVacia()) {
          realizaTiro(tirar, sp);
        } else if (tirar.variacion() > 3) {
          realizaTiro(tirar, sp);
        } else {
          realizaAvance(avanzar, sp);
        }
      }

      // Fuera del l�mite de forzado de tiro.
      else {

        // Si el jugador al que vamos a pasar la pelota est� mas adelantado.
        if (sp.myPlayers()[pasar.jugadorDestino().getIndice()].getY() >=
            sp.myPlayers()[jugador.getIndice()].getY()) {

          // Calculamos la mejor opci�n a realizar ignorando el tiro.
          decideAP(sp, jugador, avanzar, pasar);
        }

        // Si el destino del pase no se encuentra m�s adelantado.
        else {
          // Calculamos la mejor opci�n a realizar ignorando el pase.
          decideAT(sp, jugador, avanzar, tirar);
        }
      }
    }

    // Es el portero
    else {
      realizaPase(pasar, sp);
    }
  }

  private void decide(GameSituations sp, Jugador jugador) {

    if (sp.isStarts() && iteracionesParaSacar > 0) {
      //System.out.println("Esperamos para obtener una acci�n");
    } else {

      // No es el portero.
      if (jugador.getIndice() != 0) {

        // Nos encontramos en una situaci�n en la que tenemos que buscar la porter�a.
        if (sp.ballPosition().getY() > ((Constants.LARGO_CAMPO_JUEGO / 2) - 8) &&
            Math.abs(sp.ballPosition().getX()) > Constants.posteDerArcoSup.getX() + 1.5) {

          AccionPasarAlHueco
              paseAlHueco =
              gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales, null);
          if (paseAlHueco != null && paseAlHueco.paseHaciaElArea()) {
            realizaPaseAlHueco(paseAlHueco, sp);
          } else {

            if (conjuntoDelanteros.contains(jugador) ||
                conjuntoMedios.contains(jugador)) {

              AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
              if (regatear != null && regatear.regateHaciaPorteria()) {
                realizaRegate(regatear, sp);
              } else {
                realizaPaseAlArea(sp, jugador);
              }
            }

            // El portero y los defensas realizan un despeje directamente.
            else {
              realizaDespeje(sp, jugador);
            }
          }
        }
        // Situaci�n en la que no es necesario buscar la porter�a.
        else {

          double distanciaPorteria = sp.ballPosition().distance(Constants.centroArcoSup);
          if (distanciaPorteria <= Constants.DISTANCIA_PENAL) {
            realizaTiroTrallon(sp, jugador);
          } else {
            AccionPasarAlHueco
                paseAlHueco =
                gen.pasarAlHueco(sp, jugador, arrayJugadores, arrayRivales, null);
            if (paseAlHueco != null) {
              realizaPaseAlHueco(paseAlHueco, sp);
            } else {

              if (conjuntoDelanteros.contains(jugador) ||
                  conjuntoMedios.contains(jugador)) {

                AccionRegatear regatear = gen.regatear(sp, jugador, arrayRivales);
                if (regatear != null && contRegate < 5) {
                  realizaRegate(regatear, sp);
                } else {
                  realizaPaseAlArea(sp, jugador);
                }
              }

              // El portero y los defensas realizan un despeje directamente.
              else {
                realizaDespeje(sp, jugador);
              }
            }
          }
        }
      }
      // Es el portero
      else {
        realizaDespeje(sp, jugador);
      }
    }
  }
  //-------------------------------------------------------------------------------------------------------------


  //-------------------------------------------------------------------------------------------------------------
  //				Métodos empleados en el calculo de la trayectoria del balón
  //-------------------------------------------------------------------------------------------------------------
  private boolean calcularListaTrayectoria(GameSituations sp) {

    boolean calcular = false;

    // Si la lista esta vacia la generamos ya que nos encontramos en el caso inicial.
    if (listaTrayectoria.size() == 0) {

      calcular = true;
    }

    // Si solamente queda un elemento en la lista comprobamos si tenemos que volver a generar la trayectoria,
    // ya que ese caso se da cuando el balón no se mueve.
    else if (listaTrayectoria.size() == 1) {

      // Si el balón se ha movido de la situación anterior, regeneramos la trayectoria.
      if (posBola.getX() != sp.ballPosition().getX() ||
          posBola.getY() != sp.ballPosition().getY()) {

        calcular = true;
      }
    }

    // Si la lista no esta vacia, comprobamos si se ha variado la trayectoria.
    else {

      // Si la primera posición de la lista no se corresponde con la que tenemos en la trayectoria
      // volvemos a generarla porque se ha producido un golpeo o rebote en el poste.
      Balon balon = listaTrayectoria.get(0);
      if (sp.ballPosition().getX() != balon.getX() ||
          sp.ballPosition().getY() != balon.getY()) {

        calcular = true;
      }
    }

    return calcular;
  }


  private void generaListaTrayectoria(GameSituations sp) {

    // Generamos la lista de la trayectoria.
    if (calcularListaTrayectoria(sp)) {

      // Eliminamos los elementos que pudiera tener la lista ya que vamos a volver a calcularla.
      listaTrayectoria.clear();

      boolean salir = false;
      int contador = 0;
      while (gen.posicionDentroDelCampo(sp.ballPosition()) && (!salir && contador < 500)) {

        double[] trayectoria = sp.getTrajectory(contador);

        // Añadimos la posición a la lista.
        listaTrayectoria.add(new Balon(trayectoria[0], trayectoria[1], trayectoria[2]));

        if (listaTrayectoria.size() >= 2) {
          Balon bPen = listaTrayectoria.get(listaTrayectoria.size() - 2);
          Balon bUlt = listaTrayectoria.get(listaTrayectoria.size() - 1);

          if (bPen.getX() == bUlt.getX() &&
              bPen.getY() == bUlt.getY() &&
              bPen.getZ() == bUlt.getZ()) {

            salir = true;
          }
        }

        contador++;
      }
    }

    // Si la lista esta vacía entendemos que es porque el balón no se mueve (y hemos podido eliminar
    // todos sus elementos al consumirlos en cada vuelta de bucle) o porque se encuentra
    // fuera del campo por lo que devolvemos la ubicación actual de la bola.
    if (listaTrayectoria.size() == 0) {
      listaTrayectoria
          .add(new Balon(sp.ballPosition().getX(), sp.ballPosition().getY(), sp.ballAltitude()));
    }
  }


  // Cuando queda un solo elemento no lo eliminamos, ya que este indicar�a que el bal�n esta parado.
  private void avanzarTrayectoria() {

    if (listaTrayectoria.size() > 1) {
      listaTrayectoria.remove(0);
    }
  }


  private void generaTiempoAlcanceBalon(GameSituations sp) {

    // Generamos los tiempos de mis jugadores. Para ello inicializamos primero toda la información, ya
    // que lo vamos a calcular todo en cada iteración.
    for (Jugador j : arrayJugadores) {
      j.setInformacionAlcanceBalon(null);
      j.setIteracionesAlcanceBalon(999);
    }
    for (Jugador j : arrayJugadores) {
      generaTiempoAlcanceBalonJugador(sp, j);
    }

    // Generamos los tiempos de los rivalPlayers. Para ello inicializamos primero toda la información, ya
    // que lo vamos a calcular todo en cada iteración.
    for (Rival r : arrayRivales) {
      r.setInformacionAlcanceBalon(null);
      r.setIteracionesAlcanceBalon(999);
    }
    for (Rival r : arrayRivales) {
      generaTiempoAlcanceBalonRival(sp, r);
    }
  }


  private void generaTiempoAlcanceBalonJugador(GameSituations sp, Jugador jugador) {

    Balon informacionAlcance = null;
    int iteraciones = -1;

    // Si encontramos iteraciones en la trayectoria vamos a calcular hacia donde dirigirnos.
    if (listaTrayectoria.size() > 0) {

      Player jd = sp.myPlayersDetail()[jugador.getIndice()];
      double
          alcanceXY =
          jd.isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO
                            : Constants.DISTANCIA_CONTROL_BALON;
      double alcanceZ = jd.isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;

      while (informacionAlcance == null && iteraciones < 500) {

        iteraciones++;

        // Si en el elemento de la iteración encontramos una ubicación para el balón, buscamos esa, en
        // caso contrario buscamos la última ya que significa que el balón esta parado.
        Balon balon = (listaTrayectoria.size() > iteraciones) ? listaTrayectoria.get(iteraciones) :
                      listaTrayectoria.get(listaTrayectoria.size() - 1);

        // Si en la iteración actual el jugador puede rematar y alcanzamos el balón en Z calculamos si llegamos.
        if ((sp.iterationsToKick()[jugador.getIndice()] <= iteraciones) &&
            (balon.getZ() <= alcanceZ)) {

          Position posicionJugador = sp.myPlayers()[jugador.getIndice()];
          Position irA = new Position(balon.getX(), balon.getY());

          double dist = posicionJugador.distance(irA);
          double vel = Constants.getVelocidad(jd.getSpeed()) * iteraciones;
          if (dist < vel) {
            vel = dist;
          }
          posicionJugador = posicionJugador.movePosition(irA.getX() - posicionJugador.getX(),
                                                         irA.getY() - posicionJugador.getY(), vel);

          if (posicionJugador.distance(irA) < alcanceXY) {
            informacionAlcance = balon;
            break;
          }
        }
      }
    }

    // Si encontramos un resultado.
    if (informacionAlcance != null) {
      jugador.setInformacionAlcanceBalon(informacionAlcance);
      jugador.setIteracionesAlcanceBalon(iteraciones);

      // Comprobamos si el jugador actual tarda menos en llegar al balón que el que teniamos asignado.
      if (jugadorLlegaAntesBalon > -1) {

        if (arrayJugadores[jugadorLlegaAntesBalon].getIteracionesAlcanceBalon() > iteraciones) {
          jugadorLlegaAntesBalon = jugador.getIndice();
        }
      } else {
        jugadorLlegaAntesBalon = jugador.getIndice();
      }
    }
    // Si no encontramos nada no le asignamos nada.
    else {

      jugador.setInformacionAlcanceBalon(null);
      jugador.setIteracionesAlcanceBalon(-1);
    }
  }


  private void generaTiempoAlcanceBalonRival(GameSituations sp, Rival rival) {

    Balon informacionAlcance = null;
    int iteraciones = -1;

    // Si encontramos iteraciones en la trayectoria vamos a calcular hacia donde dirigirnos.
    if (listaTrayectoria.size() > 0) {

      Player rd = sp.rivalPlayersDetail()[rival.getIndice()];
      double alcanceXY = Constants.DISTANCIA_CONTROL_BALON;
      double alcanceZ = rd.isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;

      while (informacionAlcance == null && iteraciones < 500) {

        iteraciones++;

        // Si en el elemento de la iteraci�n encontramos una ubicaci�n para el bal�n, buscamos esa, en
        // caso contrario buscamos la �ltima ya que significa que el bal�n esta parado.
        Balon balon = (listaTrayectoria.size() > iteraciones) ? listaTrayectoria.get(iteraciones) :
                      listaTrayectoria.get(listaTrayectoria.size() - 1);

        // Si en la iteración actual el rival puede rematar y alcanzamos el balón en Z calculamos si llegamos.
        if ((sp.rivalIterationsToKick()[rival.getIndice()] <= iteraciones) &&
            (balon.getZ() <= alcanceZ)) {

          Position posicionRival = sp.rivalPlayers()[rival.getIndice()];
          Position irA = new Position(balon.getX(), balon.getY());

          double dist = posicionRival.distance(irA);
          double vel = Constants.getVelocidad(rd.getSpeed()) * iteraciones;
          if (dist < vel) {
            vel = dist;
          }
          posicionRival = posicionRival.movePosition(irA.getX() - posicionRival.getX(),
                                                     irA.getY() - posicionRival.getY(), vel);

          if (posicionRival.distance(irA) < alcanceXY) {

            informacionAlcance = balon;
            break;
          }
        }
      }
    }

    // Si encontramos un resultado.
    if (informacionAlcance != null) {

      rival.setInformacionAlcanceBalon(informacionAlcance);
      rival.setIteracionesAlcanceBalon(iteraciones);

      // Comprobamos si el rival actual tarda menos en llegar al bal�n que el que teniamos asignado.
      if (rivalLlegaAntesBalon > -1) {

        if (arrayRivales[rivalLlegaAntesBalon].getIteracionesAlcanceBalon() > iteraciones) {
          rivalLlegaAntesBalon = rival.getIndice();
        }
      } else {
        rivalLlegaAntesBalon = rival.getIndice();
      }
    }
    // Si no encontramos nada no le asignamos nada.
    else {
      rival.setInformacionAlcanceBalon(null);
      rival.setIteracionesAlcanceBalon(-1);
    }
  }


  // M�todo que devuelve la posici�n a la que llegar� la pelota a la porter�a.
  // Si no llega devuelve null.
  private boolean balonLlegaAPorteria(GameSituations sp) {

    boolean llega = false;

    // Si el bal�n avanza en el eje Y hacia la porter�a.
    if (avanzaEnYAPorteria(sp)) {

      if (listaTrayectoria.size() > 0) {

        Balon ultimaPosicion = listaTrayectoria.get(listaTrayectoria.size() - 1);

        // El valor Y del punto de corte se encuentra en la linea de gol.
        if (ultimaPosicion.getY()
            <= Constants.centroArcoInf.getY() + Constants.DISTANCIA_CONTROL_BALON_PORTERO) {
          if (ultimaPosicion.getX() >= Constants.posteIzqArcoInf.getX() &&
              ultimaPosicion.getX() <= Constants.posteDerArcoInf.getX()) {

            llega = true;
          }
        }
      }
    }

    return llega;
  }
  //-------------------------------------------------------------------------------------------------------------


  //-------------------------------------------------------------------------------------------------------------
  //				M�todos que a�aden a la lista de comandos las acciones a realizar por los jugadores
  //-------------------------------------------------------------------------------------------------------------
  private void realizaIrA(Jugador jugador, Position irA) {

    comandos.add(new CommandMoveTo(jugador.getIndice(), irA));
  }

  private void realizaIrAPorBalon(GameSituations sp, Jugador jugador) {

    // Generamos el alcance del jugador.
    generaTiempoAlcanceBalonJugador(sp, jugador);

    // Si tenemos informaci�n de alcance vamos a la posici�n indicada.
    if (jugador.getInformacionAlcanceBalon() != null) {

      comandos.add(new CommandMoveTo(jugador.getIndice(),
                                     new Position(jugador.getInformacionAlcanceBalon().getX(),
                                                  jugador.getInformacionAlcanceBalon().getY())));

      // Actualizamos las iteraciones del jugador indicando que hemos ido hacia el bal�n siguiendo
      // la trayectoria calculada.
      if (jugador.getIteracionesAlcanceBalon() >= 0) {

        jugador.setIteracionesAlcanceBalon(jugador.getIteracionesAlcanceBalon() - 1);

        if (jugador.getIteracionesAlcanceBalon() == 0) {
          jugador.setInformacionAlcanceBalon(null);
        }
      }
    }

    // En caso contrario vamos directamente a por el bal�n.
    else {

      // Si el bal�n se encuentra dentro del campo.
      if (gen.posicionDentroDelCampo(sp.ballPosition())) {
        realizaIrA(jugador, sp.ballPosition());
      } else {
        realizaIrA(jugador, Entorno.alineacionNormal[jugador.getIndice()]);
      }
    }
  }


  private void realizaPase(AccionPasar pasar, GameSituations sp) {

    comandos.add(pasar.comando());
    jugadorQuePasa = pasar.comando().getPlayerIndex();
    pasandoA = pasar.jugadorDestino().getIndice();

    // Cuando realizamos un pase corremos hacia adelante para no perder un ciclo.
    comandos.add(new CommandMoveTo(jugadorQuePasa,
                                   new Position(sp.myPlayers()[jugadorQuePasa].getX(),
                                                sp.myPlayers()[jugadorQuePasa].getY() + 10)));

    contAvance = 0;
    contRegate = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(pasar, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    //System.out.println("Pase");
  }

  private void realizaPaseAlHueco(AccionPasarAlHueco paseAlHueco, GameSituations sp) {

    comandos.add(paseAlHueco.comando());

    jugadorQuePasa = paseAlHueco.comando().getPlayerIndex();
    pasandoA = paseAlHueco.indiceJugadorCercano();

    // Cuando realizamos un pase al hueco corremos hacia el �ngulo del pase para no perder un ciclo ya que
    // hay ocasiones en las se utiliza para hacer un autopase.
    comandos.add(new CommandMoveTo(pasandoA, paseAlHueco.posicionAlcancePase()));

    contAvance = 0;
    contRegate = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(paseAlHueco, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    //System.out.println("Pase al hueco");
  }

  private void realizaPaseAlArea(GameSituations sp, Jugador jugador) {

    AccionPasarAlArea pasarAlArea = gen.pasarAlArea(sp, jugador, arrayJugadores, arrayRivales);
    comandos.add(pasarAlArea.comando());

    jugadorQuePasa = pasarAlArea.comando().getPlayerIndex();
    pasandoA = pasarAlArea.jugadorDestino().getIndice();

    contAvance = 0;
    contRegate = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(pasarAlArea, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    //System.out.println("Pase al area");
  }

  private void realizaTiro(AccionTirar tirar, GameSituations sp) {

    comandos.add(tirar.comando());
    jugadorPosesion = -1;

    // Cuando realizamos un tiro corremos hacia la porteria para no perder un ciclo y buscar un posible rechace.
    comandos.add(new CommandMoveTo(tirar.comando().getPlayerIndex(), Constants.centroArcoSup));

    contAvance = 0;
    contRegate = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(tirar, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    // Hemos tirado a gol y lo desactivamos para que no penalice ya que hemos realizado la acción completa.
    ejecutarBarrido = false;
    tiroPorBarrido = true;

    //System.out.println("Tiro");
  }


  private void realizaTiroTrallon(GameSituations sp, Jugador jugador) {

    AccionTirarTrallon tiroTrallon = gen.tirarATrallon(sp, jugador);
    comandos.add(tiroTrallon.comando());
    jugadorPosesion = -1;

    // Cuando realizamos un tiro a trllon corremos hacia la porteria para no perder un ciclo y buscar un posible rechace.
    comandos
        .add(new CommandMoveTo(tiroTrallon.comando().getPlayerIndex(), Constants.centroArcoSup));

    contAvance = 0;
    contRegate = 0;
  }

  private void realizaDespeje(GameSituations sp, Jugador jugador) {

    AccionDespejar despeje = gen.despejar(sp, jugador, arrayRivales);
    comandos.add(despeje.comando());

    jugadorPosesion = -1;

        /*
        Actualizacion de la posici�n para que los delanteros no se queden parados.
        posesion = false;
         */

    contAvance = 0;
    contRegate = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(despeje, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    //System.out.println("Despeje");
  }


  private void realizaAvance(AccionAvanzar avanzar, GameSituations sp) {

    comandos.add(avanzar.comando());
    comandos.add(avanzar.comandoIrA());

    contAvance = contAvance + 1;
    contRegate = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(avanzar, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    //System.out.println("Avanza");
  }


  private void realizaRegate(AccionRegatear regatear, GameSituations sp) {

    comandos.add(regatear.comando());
    comandos.add(regatear.comandoIrA());

    contRegate = contRegate + 1;
    contAvance = 0;

    // Añadimos la acción realizada a la acción pendiente.
    accionEnProceso = acciones.traduceAccion(regatear, sp.ballPosition(), sectorActivo);
    acciones.actualizaInformacion(accionEnProceso, 1, 0, 0);

    //System.out.println("Regatea");
  }
  //-------------------------------------------------------------------------------------------------------------
}