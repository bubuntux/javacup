package org.dsaw.javacup.tactics.jvc2012.chr2012.accion;

import org.dsaw.javacup.model.command.Command;

/**
 * Clase que implementa el interfaz Action y que representa la acci�n de tiro del jugador.
 *
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class AccionTirar implements Accion {

  private Command comando;
  private boolean dentroDeForzado;
  private double variacion;
  private boolean aPuertaVacia;
  private boolean tiroSeguro;


  public AccionTirar(Command comando, boolean dentroDeForzado, double variacion,
                     boolean aPuertaVacia,
                     boolean tiroSeguro) {

    this.comando = comando;
    this.dentroDeForzado = dentroDeForzado;
    this.variacion = variacion;
    this.aPuertaVacia = aPuertaVacia;
    this.tiroSeguro = tiroSeguro;
  }

  @Override
  public Command comando() {
    return comando;
  }

  public boolean dentroDeForzado() {
    return dentroDeForzado;
  }

  public double variacion() {
    return variacion;
  }

  public boolean aPuertaVacia() {
    return aPuertaVacia;
  }

  public boolean tiroSeguro() {
    return tiroSeguro;
  }

}
