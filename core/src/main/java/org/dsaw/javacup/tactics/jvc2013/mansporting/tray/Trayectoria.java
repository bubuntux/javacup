package org.dsaw.javacup.tactics.jvc2013.mansporting.tray;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que representa una trayectoria.
 *
 * @author MaN
 */
public class Trayectoria {

  protected double velocidad;
  protected double anguloVertical;
  protected List<TrayectoriaPunto> puntos;

  /**
   * Gets the value of the velocidad property.
   */
  public double getVelocidad() {
    return velocidad;
  }

  /**
   * Sets the value of the velocidad property.
   */
  public void setVelocidad(double value) {
    this.velocidad = value;
  }

  /**
   * Gets the value of the anguloVertical property.
   */
  public double getAnguloVertical() {
    return anguloVertical;
  }

  /**
   * Sets the value of the anguloVertical property.
   */
  public void setAnguloVertical(double value) {
    this.anguloVertical = value;
  }

  /**
   * Gets the value of the puntos property.
   *
   * <p> This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the puntos property.
   *
   * <p> For example, to add a new item, do as follows:
   * <pre>
   *    getPuntos().add(newItem);
   * </pre>
   *
   *
   * <p> Objects of the following type(s) are allowed in the list {@link TrayectoriaPunto }
   */
  public List<TrayectoriaPunto> getPuntos() {
    if (puntos == null) {
      puntos = new ArrayList<>();
    }
    return this.puntos;
  }
}
