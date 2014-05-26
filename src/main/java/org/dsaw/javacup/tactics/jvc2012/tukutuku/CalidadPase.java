package org.dsaw.javacup.tactics.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;


public class CalidadPase 
{
	boolean paseSeguro;
    
	boolean libreContrarios = false;
	
    //Posicion en la que nuesto jugador recibe el balon
    Position posFinalBalon = null;
    
    //Indica si obtenemos los mismos resultados a pesar del error 1 error positivo, 2 error negativo, 3 ambos
	double calidad = 0;
	
	PaseInfo info = null;

	public CalidadPase(PaseInfo inf)
	{
		info = inf;
	}


public boolean esMejor(PaseInfo pInf) 
{
   
    //El balon no debe salir del campo
    if ((this.posFinalBalon != null) && (pInf.calidad.posFinalBalon != null))
    {
    	if ((this.posFinalBalon).isInsideGameField(0) != (pInf.calidad.posFinalBalon.isInsideGameField(0)))
    	{
    		return (this.posFinalBalon.isInsideGameField(0));
    	}
    
      
    }

  //Vemos la calidad del pase
    
    if (this.calidad != pInf.calidad.calidad) 
    {
        return (this.calidad > pInf.calidad.calidad);
    }

    
	 
	//el pase m�s en profundidad y hacia el centro si estamos en nuestro campo
     if (Constants.centroArcoSup.distance(this.posFinalBalon) != Constants.centroArcoSup.distance(pInf.calidad.posFinalBalon))
	    {
	    	return (Constants.centroArcoSup.distance(this.posFinalBalon) < Constants.centroArcoSup.distance(pInf.calidad.posFinalBalon));
	    }
 
   //el pase m�s avanzado 
   	 if ((this.posFinalBalon.getY()) != pInf.calidad.posFinalBalon.getY())
   	 {
   		 return ((this.posFinalBalon.getY()) > pInf.calidad.posFinalBalon.getY());
   	 }
       
     
  if (this.info.anguloVert != pInf.anguloVert)
  {
	  return (this.info.anguloVert < pInf.anguloVert);
  }
    
    //El pase mas suave
    
    if (this.info.fuerza != pInf.fuerza)
    {
    	return (this.info.fuerza > pInf.fuerza);
    }
    
  
       
    return false;
}
}
