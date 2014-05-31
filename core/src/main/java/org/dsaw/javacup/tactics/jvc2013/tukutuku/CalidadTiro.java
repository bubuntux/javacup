package org.dsaw.javacup.tactics.jvc2013.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */


public class CalidadTiro {

  boolean esGol;


  //Indica si obtenemos los mismos resultados a pesar del error 1 error positivo, 2 error negativo, 3 ambos
  int calidad = 0;

  TiroInfo info = null;

  public CalidadTiro(TiroInfo inf) {
    info = inf;
  }


  public boolean esMejor(TiroInfo pInf) {
    //Vemos si el tiro es gol
    if (this.esGol != pInf.calidad.esGol) {
      return this.esGol;
    }

    //Vemos la calidad del tiro

    if (this.calidad != pInf.calidad.calidad) {
      return (this.calidad > pInf.calidad.calidad);
    }

    return (this.info.fuerza > pInf.fuerza);


  }
}
