package org.dsaw.javacup.tactics.jvc2013.absolutsport.accion;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.Hashtable;
import java.util.Random;

public class AccionRealizada {


  //--------------------------------------------------------------------------------------------------------------------------------------------
  // Declaraci�n de �ndices de traducción.
  //--------------------------------------------------------------------------------------------------------------------------------------------
  public final int NUM_INDICES_TRAD = 2;
  public final int IND_TRAD_SECTOR = 0;
  public final int IND_TRAD_ACCION = 1;
  //--------------------------------------------------------------------------------------------------------------------------------------------

  //--------------------------------------------------------------------------------------------------------------------------------------------
  // Declaraci�n de sectores de valoraci�n.
  //--------------------------------------------------------------------------------------------------------------------------------------------
        /*
	private final String DEF_IZQ = "DEF_IZQ";
	private final String DEF_CENT_IZQ = "DEF_CENT_IZQ";
	private final String DEF_CENT_DER = "DEF_CENT_DER";
	private final String DEF_DER = "DEF_DER";
	
	private final String MED_IZQ = "MED_IZQ";
	private final String MED_CENT_IZQ = "MED_CENT_IZQ";
	private final String MED_CENT_DER = "MED_CENT_DER";
	private final String MED_DER = "MED_DER";
	
	private final String DEL_IZQ = "DEL_IZQ";
	private final String DEL_CENT_IZQ = "DEL_CENT_IZQ";
	private final String DEL_CENT_DER = "DEL_CENT_DER";
	private final String DEL_DER = "DEL_DER";
	*/
  //--------------------------------------------------------------------------------------------------------------------------------------------


  //--------------------------------------------------------------------------------------------------------------------------------------------
  // Declaraci�n de constantes que nos definen las acciones realizadas.
  //--------------------------------------------------------------------------------------------------------------------------------------------
  private final String AVANZAR = "AVANZAR";

  private final String REGATEAR_PORTERIA = "REGATEAR_PORTERIA";
  private final String REGATEAR_ADELANTE = "REGATEAR_ADELANTE";
  private final String REGATEAR_ATRAS = "REGATEAR_ATRAS";

  private final String PASAR_ADELANTE = "PASAR_ADELANTE";
  private final String PASAR_ATRAS = "PASAR_ADELANTE";

  private final String TIRAR_SEGURO = "TIRAR_SEGURO";
  private final String TIRAR_NO_SEGURO = "TIRAR_NO_SEGURO";

  private final String TIRAR_TRALLON = "TIRAR_TRALLON";

  private final String HUECO_AREA = "HUECO_AREA";
  private final String HUECO_ADELANTE = "HUECO_ADELANTE";
  private final String HUECO_ATRAS = "HUECO_ATRAS";

  private final String AREA = "AREA";

  private final String DESPEJAR = "DESPEJAR";
  //--------------------------------------------------------------------------------------------------------------------------------------------


  //--------------------------------------------------------------------------------------------------------------------------------------------
  // Declaraci�n de �ndices de valores.
  //--------------------------------------------------------------------------------------------------------------------------------------------
  private final int NUM_INDICES = 3;
  private final int IND_CONTADOR = 0;
  private final int IND_ACIERTOS = 1;
  private final int IND_PESO = 2;
  //--------------------------------------------------------------------------------------------------------------------------------------------


  private Hashtable<String, Hashtable<String, Integer[]>> tablaSectores;
  Random rand;


  public AccionRealizada() {

    // Instanciamos la tabla.
    tablaSectores = new Hashtable<>();

    // Inicializamos la semilla de aleatoriedad.
    rand = new Random();
  }


  public void actualizaInformacion(String[] informacion, int contador, int acierto, int peso) {

    if (informacion != null && informacion.length == NUM_INDICES_TRAD) {

      String sector = informacion[this.IND_TRAD_SECTOR];
      String accion = informacion[this.IND_TRAD_ACCION];

      if (sector != null && accion != null) {

        // Si no existe informaci�n previa para dicho sector la creamos.
        if (!tablaSectores.containsKey(sector)) {
          tablaSectores.put(sector, new Hashtable<String, Integer[]>());
        }

        // Si no existe informaci�n previa para dicha acci�n la creamos.
        if (!tablaSectores.get(sector).containsKey(accion)) {
          tablaSectores.get(sector).put(accion, new Integer[NUM_INDICES]);
          tablaSectores.get(sector).get(accion)[IND_CONTADOR] = 0;
          tablaSectores.get(sector).get(accion)[IND_ACIERTOS] = 0;
          tablaSectores.get(sector).get(accion)[IND_PESO] = 0;
        }

        tablaSectores.get(sector).get(accion)[IND_CONTADOR] =
            tablaSectores.get(sector).get(accion)[IND_CONTADOR] + contador;
        tablaSectores.get(sector).get(accion)[IND_ACIERTOS] =
            tablaSectores.get(sector).get(accion)[IND_ACIERTOS] + acierto;
        tablaSectores.get(sector).get(accion)[IND_PESO] =
            tablaSectores.get(sector).get(accion)[IND_PESO] + peso;
      } else {
        System.err.println("Encontrada acción con valores a null");
      }
    } else {
      System.err.println("Encontrada acción mal formada");
    }
  }


  public Integer[] dameInformacion(String[] informacion) {

    Integer[] informacionDevuelta = null;

    if (informacion != null && informacion.length == NUM_INDICES_TRAD) {

      String sector = informacion[this.IND_TRAD_SECTOR];
      String accion = informacion[this.IND_TRAD_ACCION];

      if (sector != null && accion != null) {

        if (tablaSectores.containsKey(sector)) {

          if (tablaSectores.get(sector).containsKey(accion)) {
            informacionDevuelta = tablaSectores.get(sector).get(accion);
          }
        }
      }
    }
    return informacionDevuelta;
  }


  public String[] traduceAccion(Accion accion, Position balon, char sectorActivo) {

    // Variable en la que devolveremos el resultado.
    String[] accionTraducida = new String[2];

    try {

      accionTraducida[IND_TRAD_SECTOR] = formateaSector(balon.getX(), sectorActivo);

      if (accion instanceof AccionAvanzar) {

        accionTraducida[IND_TRAD_ACCION] = this.AVANZAR;
      } else if (accion instanceof AccionRegatear) {

        if (((AccionRegatear) accion).regateHaciaAdelante()) {
          accionTraducida[IND_TRAD_ACCION] = this.REGATEAR_ADELANTE;
        } else if (((AccionRegatear) accion).regateHaciaPorteria()) {
          accionTraducida[IND_TRAD_ACCION] = this.REGATEAR_PORTERIA;
        } else {
          accionTraducida[IND_TRAD_ACCION] = this.REGATEAR_ATRAS;
        }
      } else if (accion instanceof AccionPasar) {

        double posY_Destino = ((AccionPasar) accion).posJugadorDestino().getY();
        if (posY_Destino > balon.getY()) {
          accionTraducida[IND_TRAD_ACCION] = this.PASAR_ADELANTE;
        } else {
          accionTraducida[IND_TRAD_ACCION] = this.PASAR_ATRAS;
        }
      } else if (accion instanceof AccionTirar) {

        if (((AccionTirar) accion).tiroSeguro()) {
          accionTraducida[IND_TRAD_ACCION] = this.TIRAR_SEGURO;
        } else {
          accionTraducida[IND_TRAD_ACCION] = this.TIRAR_NO_SEGURO;
        }
      } else if (accion instanceof AccionTirarTrallon) {
        accionTraducida[IND_TRAD_ACCION] = this.TIRAR_TRALLON;
      } else if (accion instanceof AccionPasarAlHueco) {

        if (((AccionPasarAlHueco) accion).paseHaciaElArea()) {
          accionTraducida[IND_TRAD_ACCION] = this.HUECO_AREA;
        } else if (((AccionPasarAlHueco) accion).paseHaciaAdelante()) {
          accionTraducida[IND_TRAD_ACCION] = this.HUECO_ADELANTE;
        } else {
          accionTraducida[IND_TRAD_ACCION] = this.HUECO_ATRAS;
        }
      } else if (accion instanceof AccionPasarAlArea) {
        accionTraducida[IND_TRAD_ACCION] = this.AREA;
      } else if (accion instanceof AccionDespejar) {
        accionTraducida[IND_TRAD_ACCION] = this.DESPEJAR;
      } else {
        accionTraducida = null;
      }
    } catch (Exception e) {

      System.err.println("Error en traduceAccion: " + e);
      accionTraducida = null;
    }

    return accionTraducida;
  }


  public Integer decide(Accion[] acciones, GameSituations sp, char sectorActivo) {

    // Variable en la que devolvemos el resultado.
    Integer decisionFinal = null;

    try {
      if (acciones == null) {
      } else if (acciones.length == 1) {
        decisionFinal = 0;
      } else if (acciones.length > 1) {

        // Variable en la que almacenaremos el valor de cada iteraci�n.
        Integer decisionAux;

        // Nos quedamos con la primera por defecto y comenzamos a iterar.
        Integer[]
            informacionFinal =
            dameInformacion(traduceAccion(acciones[0], sp.ballPosition(), sectorActivo));
        if (acciones[0] != null) {
          if (informacionFinal == null) {
            informacionFinal = new Integer[]{1, 1, 1};
          }
          decisionFinal = 0;
        }

        for (int i = 1; i < acciones.length; i++) {

          decisionAux = null;

          // Cogemos la informaci�n de la actual iteraci�n.
          Integer[]
              informacionAux =
              dameInformacion(traduceAccion(acciones[i], sp.ballPosition(), sectorActivo));
          if (acciones[i] != null && informacionAux == null) {

            // Lo inicializamos para poder aplicarle una probabilidad.
            informacionAux = new Integer[]{1, 1, 1};
          }

          if (informacionFinal == null && informacionAux != null) {

            informacionFinal = informacionAux;
            decisionFinal = i;
            continue;
          } else if (informacionFinal != null && informacionAux != null) {

            //------------------------------------------------------------------------------------------------------------------------------
            // PENDIENTE AJUSTAR ESTOS VALORES.
            // Establecemos en 5 la diferencia de ejecuciones.
            int diferenciaEjecuciones = 5;
            int diferenciaGoles = sp.myGoals() - sp.rivalGoals();
            int ganamosAmpliamente = 3;
            //------------------------------------------------------------------------------------------------------------------------------

            // Nos quedamos con los valores de las acciones.
            int iFinal_Contador = informacionFinal[IND_CONTADOR];
            int iFinal_Aciertos = informacionFinal[IND_ACIERTOS];
            int iFinal_Peso = informacionFinal[IND_PESO];

            int iAux_Contador = informacionAux[IND_CONTADOR];
            int iAux_Aciertos = informacionAux[IND_ACIERTOS];
            int iAux_Peso = informacionAux[IND_PESO];

            // No existen diferencias apreciables en cuanto a las ejecuciones de ambas acciones.
            if (Math.abs(iFinal_Contador - iAux_Contador) < diferenciaEjecuciones) {

              // Si la diferencia de goles es +2, significa que ganamos ampliamente.
              if (diferenciaGoles >= ganamosAmpliamente) {

                // Dado que ganamos ampliamente y no hay diferencia entre las ejecuciones vamos a dar m�s prioridad a las que tengan mayor
                // porcentaje en relaci�n a aciertos y peso.

                // Obtenemos el porcentaje de aciertos en relaci�n al peso.
                double probabilidadFinal = (iFinal_Aciertos * 100) / iFinal_Peso;
                double probabilidadAux = (iAux_Aciertos * 100) / iAux_Peso;

                // Calculamos la acci�n a ejecutar si tenemos distintas probabilidades.
                if (probabilidadFinal != probabilidadAux) {
                  if (rand.nextDouble() < (probabilidadFinal / 100)) {
                    decisionAux = 1;
                  } else {
                    decisionAux = 2;
                  }
                }
                // En caso de empate lanzamos una al azar.
                else {
                  decisionAux = (rand.nextDouble() < 0.5) ? 1 : 2;
                }
              }
              // Caso en el que no ganamos ampliamente.
              else {

                // Dado que no ganamos ampliamente y no hay diferencia entre las ejecuciones vamos a ejecutar la que m�s peso tenga si tiene
                // un amplio porcentaje de aciertos ya que no podemos arriesgar.

                double porcentajeAciertos = 70;
                double porcentAciertos1 = (iFinal_Aciertos * 100) / iFinal_Contador;
                double porcentAciertos2 = (iAux_Aciertos * 100) / iAux_Contador;

                // Las 2 acciones tienen un amplio porcentaje de aciertos por lo que nos quedamos con la de mayor peso.
                if ((porcentAciertos1 >= porcentajeAciertos) && (porcentAciertos2
                                                                 >= porcentajeAciertos)) {

                  // La acci�n 1 tiene mayor peso.
                  if (iFinal_Peso > iAux_Peso) {
                    decisionAux = 1;
                  }
                  // La acci�n 2 tiene mayor peso.
                  else if (iAux_Peso > iFinal_Peso) {
                    decisionAux = 2;
                  }
                  // Si tienen igual peso devolvemos uno aleatoriamente.
                  else {
                    decisionAux = (rand.nextDouble() < 0.5) ? 1 : 2;
                  }
                }
                // La acción 1 tienen un amplio porcentaje de aciertos.
                else if ((porcentAciertos1 >= porcentajeAciertos)) {
                  decisionAux = 1;
                }
                // La acción 2 tienen un amplio porcentaje de aciertos.
                else if ((porcentAciertos2 >= porcentajeAciertos)) {
                  decisionAux = 2;
                }
                // Ninguna de las 2 acciones tiene un amplio porcentaje por lo que nos quedamos con la de mayor acierto.
                else {
                  // La accion 1 tiene mayor acierto.
                  if (iFinal_Aciertos > iAux_Aciertos) {
                    decisionAux = 1;
                  }
                  // La accion 2 tiene mayor acierto.
                  else if (iAux_Aciertos > iFinal_Aciertos) {
                    decisionAux = 2;
                  }
                  // Si tienen igual aciertos devolvemos uno aleatoriamente.
                  else {
                    decisionAux = (rand.nextDouble() < 0.5) ? 1 : 2;
                  }
                }
              }
            }
            // Existen diferencias apreciables en cuanto a las ejecuciones de ambas acciones.
            else {

              // Si la diferencia de goles es +2, significa que ganamos ampliamente.
              if (diferenciaGoles >= ganamosAmpliamente) {

                // Este es el caso al que tenemos que intentar llegar continuamente ya que nos indica que hemos encontrado la forma de romper
                // al equipo contrario.

                // Vamos a obtener cual es la relaci�n entre el que m�s ejecuciones tiene y el que menos para obtener la probabilidad.
                if (iFinal_Contador > iAux_Contador) {

                  // Obtenemos el porcentaje del de menos ejecuciones en relaci�n al de m�s ejecuciones.
                  double pr2 = (iAux_Contador * 100) / iFinal_Contador;
                  if (rand.nextDouble() < pr2) {
                    decisionAux = 2;
                  } else {
                    decisionAux = 1;
                  }
                } else {

                  // Obtenemos el porcentaje del de menos ejecuciones en relaci�n al de m�s ejecuciones.
                  double pr1 = (iFinal_Contador * 100) / iAux_Contador;
                  if (rand.nextDouble() < pr1) {
                    decisionAux = 1;
                  } else {
                    decisionAux = 2;
                  }
                }
              }
              // En este caso no ganamos ampliamente.
              else {

                // En este caso tenemos muchas diferencias de ejecuci�n pero no ganamos ampliamente, por lo que tenemos que buscar otras opciones
                // menos utilizadas.
                // Para ello vamos a quedarnos con la que su relaci�n peso-error sea menor.
                double prError1 = ((iFinal_Contador - iFinal_Aciertos) * 100) / iFinal_Peso;
                double prError2 = ((iAux_Contador - iAux_Aciertos) * 100) / iAux_Peso;

                if (prError1 < prError2) {
                  decisionAux = 1;
                } else if (prError2 < prError1) {
                  decisionAux = 2;
                } else {
                  decisionAux = (rand.nextDouble() < 0.5) ? 1 : 2;
                }
              }
            }
          }

          // Si nos quedamos con la segunda, la actualizamos para compararla con la siguiente.
          if (decisionAux != null && decisionAux == 2) {
            informacionFinal = informacionAux;
            decisionFinal = i;
          }

        }

      }
    } catch (Exception e) {
      //System.err.println("Error en decide: " + e);
      decisionFinal = null;
    }

    return decisionFinal;
  }


  private String formateaSector(double posX, char sectorActivo) {

    String sector;
/*		
		// En el lado izquierdo.
		if (posX < -Constantes.ANCHO_CAMPO_JUEGO/4) {

			switch (sectorActivo) {
				case Entorno.SECTOR_DEFENSA: 
					sector = this.DEF_IZQ;
			    break;
				case  Entorno.SECTOR_MEDIO: 
					sector = this.MED_IZQ;
			     break;
				case  Entorno.SECTOR_ATAQUE: 
					sector = this.DEL_IZQ;
			     break;
			}
		}
		// En el lado medio izquierdo.
		else if (posX < 0) {
			
			switch (sectorActivo) {
				case Entorno.SECTOR_DEFENSA: 
					sector = this.DEF_CENT_IZQ;
			    break;
				case  Entorno.SECTOR_MEDIO: 
					sector = this.MED_CENT_IZQ;
			     break;
				case  Entorno.SECTOR_ATAQUE: 
					sector = this.DEL_CENT_IZQ;
			     break;
			}
		}
		// En el lado medio derecho
		else if (posX < Constantes.ANCHO_CAMPO_JUEGO - (Constantes.ANCHO_CAMPO_JUEGO/4)) {
			
			switch (sectorActivo) {
				case Entorno.SECTOR_DEFENSA: 
					sector = this.DEF_CENT_DER;
			    break;
				case  Entorno.SECTOR_MEDIO: 
					sector = this.MED_CENT_DER;
			     break;
				case  Entorno.SECTOR_ATAQUE: 
					sector = this.DEL_CENT_DER;
			     break;
			}
		}
		// En el lado derecho
		else {
			
			switch (sectorActivo) {
				case Entorno.SECTOR_DEFENSA: 
					sector = this.DEF_DER;
			    break;
				case  Entorno.SECTOR_MEDIO: 
					sector = this.MED_DER;
			     break;
				case  Entorno.SECTOR_ATAQUE: 
					sector = this.DEL_DER;
			     break;
			}
		}
*/
    sector = (new StringBuffer().append(sectorActivo)).toString();
    return sector;
  }
}