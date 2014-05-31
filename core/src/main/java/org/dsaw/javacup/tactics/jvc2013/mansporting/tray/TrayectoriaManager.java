/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.tray;

import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.tactics.jvc2013.mansporting.util.MathUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author MaN
 */
public final class TrayectoriaManager {

  /**
   * Log.
   */
  private static final Logger log = Logger.getLogger(TrayectoriaManager.class.getName());
  /**
   * Valor del redondeo para las velocidades
   */
  private static final int ROUND_VELOCIDAD = 2;
  /**
   * Precisión de los mapas con la distancia.
   */
  private static final int MAP_PRECISSION = 0;
  /**
   * La distancia de pase es cuando la probabilidad de controlar el balón es > 0.9.
   */
  private static final double PASE_FACTOR = 0.99D;
  /**
   * Porcentaje de la velocidad máxima de los disparos que son válidos para tirar.
   */
  private static final double DISPARO_FACTOR = 0.8D;
  /**
   * Instancia del manager.
   */
  private static TrayectoriaManager INSTANCE;
  /**
   * Trayectorias.
   */
  private final Trayectorias trayectorias;
  /**
   * Pases por su distancia.
   */
  private final Map<Double, TrayectoriaPunto> pasesByDistancia;
  /**
   * Disparos por su distancia.
   */
  private final Map<Double, TrayectoriaPunto> disparosByDistancia;
  /**
   * Golpeos por su distancia.
   */
  private final Map<Double, List<TrayectoriaPunto>> golpeosByDistancia;

  /**
   * Constructor.
   */
  private TrayectoriaManager() {
    long start = System.currentTimeMillis();
    try {
      this.trayectorias = loadTrayectorias();
      this.golpeosByDistancia = new HashMap<>();
      this.pasesByDistancia = new HashMap<>();
      this.disparosByDistancia = new HashMap<>();

      for (Trayectoria trayectoria : this.trayectorias.getTrayectoria()) {

        // PASE
        boolean paseAdded = false;

        // VELOCIDAD
        double maxVelocidadH = Double.MIN_VALUE;

        for (int i = 0; i < trayectoria.getPuntos().size(); i++) {
          TrayectoriaPunto desp = trayectoria.getPuntos().get(i);
          double rounded = MathUtils.round(desp.getDesplazamientoH(), MAP_PRECISSION);
          List<TrayectoriaPunto> candidatos = golpeosByDistancia.get(rounded);
          if (candidatos == null) {
            candidatos = new LinkedList<>();
            golpeosByDistancia.put(rounded, candidatos);
          }
          candidatos.add(desp);

          if (desp.getVelocidadH() > maxVelocidadH) {
            maxVelocidadH = desp.getVelocidadH();
          }

          if (!paseAdded && desp.getProbabilidadControl() >= PASE_FACTOR
              && desp.getDesplazamientoV() <= Constants.ALTURA_CONTROL_BALON) {
            paseAdded = true;
            TrayectoriaPunto candidatosPase = pasesByDistancia.get(rounded);
            if (candidatosPase == null || candidatosPase.getIteraciones() > desp.getIteraciones()) {
              pasesByDistancia.put(rounded, desp);
            }
          }
        }

        if (MathUtils.round(trayectoria.getVelocidad(), ROUND_VELOCIDAD)
            >= Constants.REMATE_VELOCIDAD_MAX && trayectoria.getAnguloVertical() >= 1.0D) {
          double velocidadTiro = DISPARO_FACTOR * maxVelocidadH;
          int iteraciones = 0;

          for (int i = trayectoria.getPuntos().size() - 1; i > 1; i--) {
            TrayectoriaPunto punto = trayectoria.getPuntos().get(i);
            if (punto.getVelocidadH() >= velocidadTiro
                && punto.getDesplazamientoV() <= Constants.ALTO_ARCO) {
              iteraciones = i;
              break;
            }
          }
          TrayectoriaPunto desp = trayectoria.getPuntos().get(iteraciones);
          double rounded = MathUtils.round(desp.getDesplazamientoH(), MAP_PRECISSION);
          TrayectoriaPunto candidatosPase = disparosByDistancia.get(rounded);
          if (candidatosPase == null || candidatosPase.getIteraciones() > desp.getIteraciones()) {
            disparosByDistancia.put(rounded, desp);
          }
        }
      }
    } finally {
      long end = System.currentTimeMillis();
      //log.log(Level.INFO, String.format("Se ha tardado [%s] ms en cargar la clase", end - start));
    }
  }

  /**
   * Recupera la instancia.
   *
   * @return instancia.
   */
  public static synchronized TrayectoriaManager getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TrayectoriaManager();
    }
    return INSTANCE;
  }

  /**
   * Carga las trayectorias.
   *
   * @return trayectorias.
   */
  private Trayectorias loadTrayectorias() {

    try {
      Trayectorias result = generateTrayectorias();
      return result;
    } catch (Throwable e) {
      throw new RuntimeException("Error al cargar los datos de las trayectorias", e);
    }
  }

  /**
   * Genera las trayectorias.
   */
  private Trayectorias generateTrayectorias() {
    Trayectorias result = new Trayectorias();
    for (double angulo = 0; angulo <= Constants.ANGULO_VERTICAL_MAX; angulo++) {
      for (double velocidad = Constants.REMATE_VELOCIDAD_MIN;
           MathUtils.round(velocidad, ROUND_VELOCIDAD) <= Constants.REMATE_VELOCIDAD_MAX;
           velocidad += 0.01D) {
        Trayectoria trayectoria = new Trayectoria();
        trayectoria.setVelocidad(velocidad);
        trayectoria.setAnguloVertical(angulo);

        result.getTrayectoria().add(trayectoria);
        double anguloReal = angulo * (Math.PI / 180);
        AirTrajectory
            trayectoriaAire =
            new AirTrajectory(Math.cos(anguloReal) * velocidad, Math.sin(anguloReal) * velocidad, 0,
                              0);
        for (int iteraciones = 0; ; iteraciones++) {
          double time = (double) (iteraciones) / 60d;
          TrayectoriaPunto desplazamiento0;
          if (iteraciones < 1) {
            desplazamiento0 = new TrayectoriaPunto();
            desplazamiento0.setDesplazamientoH(0);
            desplazamiento0.setDesplazamientoV(0);
          } else {
            desplazamiento0 = trayectoria.getPuntos().get(iteraciones - 1);
          }
          TrayectoriaPunto desplazamiento = new TrayectoriaPunto();
          desplazamiento.setIteraciones(iteraciones);
          desplazamiento.setTrayectoria(trayectoria);
          desplazamiento
              .setDesplazamientoH(trayectoriaAire.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA);
          desplazamiento.setDesplazamientoV(
              trayectoriaAire.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2);

          double
              velocidadH =
              desplazamiento.getDesplazamientoH() - desplazamiento0.getDesplazamientoH();
          double
              velocidadV =
              desplazamiento.getDesplazamientoV() - desplazamiento0.getDesplazamientoV();
          desplazamiento.setVelocidadH(velocidadH);
          desplazamiento.setVelocidadV(velocidadV);

          double vel = Math.sqrt(velocidadH * velocidadH + velocidadV * velocidadV);
          if (iteraciones > 0 && vel <= 0) {
            break;
          } else {
            double probabilidad = (7d - vel) / 7d;
            desplazamiento.setProbabilidadControl(
                iteraciones == 0 ? 0 : (probabilidad < 0 ? 0 : probabilidad));
            trayectoria.getPuntos().add(desplazamiento);
          }
        }
      }
    }
    return result;
  }

  /**
   * Devuelve las trayectorias.
   *
   * @return trayectorias.
   */
  public Trayectorias getTrayectorias() {
    return trayectorias;
  }

  public Map<Double, TrayectoriaPunto> getDisparosByDistancia() {
    return Collections.unmodifiableMap(disparosByDistancia);
  }

  public Map<Double, List<TrayectoriaPunto>> getGolpeosByDistancia() {
    return Collections.unmodifiableMap(golpeosByDistancia);
  }

  public Map<Double, TrayectoriaPunto> getPasesByDistancia() {
    return Collections.unmodifiableMap(pasesByDistancia);
  }

  /**
   * Devuelve los pases posibles para una distancia.
   *
   * @param distancia distancia.
   * @param filters   filtros.
   * @return pasos.
   */
  public TrayectoriaPunto getPasesByDistancia(double distancia) {
    return getValue(distancia, pasesByDistancia);
  }

  /**
   * Devuelve los disparos por distancia.
   *
   * @param distancia distancia,.
   * @param filters   filtros.
   * @return disparos.
   */
  public TrayectoriaPunto getDisparosByDistancia(double distancia) {
    return getValue(distancia, disparosByDistancia);
  }

  private <E> E getValue(double distancia, Map<Double, E> map) {
    double value = MathUtils.round(distancia, MAP_PRECISSION);
    if (map.containsKey(value)) {
      return map.get(value);
    }
    if (map.keySet().isEmpty()) {
      return null;
    }

    List<Double> keys = new LinkedList<>(map.keySet());
    Double currentKey = null;
    Double currentKeyDistance = null;

    for (Double key : keys) {
      if (currentKey == null) {
        currentKey = key;
        currentKeyDistance = Double.MAX_VALUE;
      } else {
        double keyDistance = Math.abs(value - key);
        if (keyDistance < currentKeyDistance) {
          currentKey = key;
          currentKeyDistance = keyDistance;
        }
      }
    }
    return map.get(currentKey);
  }
}
