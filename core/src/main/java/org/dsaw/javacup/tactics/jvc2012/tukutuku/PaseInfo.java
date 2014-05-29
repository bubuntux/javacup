package org.dsaw.javacup.tactics.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;




public class PaseInfo 
{
	Entrenador entrenador = null;
    
    CalidadPase calidad = null;
    int tirador;
    double fuerza;
    double angulo;
    double anguloVert;
    int iterPase = 0;

  //Numero de posiciones de la trayectoria del balon que se calcularan
  	private	final int N_POSICIONES = 100;  		
    PosicionBalon[] posicionesBalon;
    PosicionBalon [] errAngularPositivo;
    PosicionBalon [] errAngularNegativo;
    int [] iterDisparo;
       
    public PaseInfo(PaseInfo pInf) 
    {
    	this.calidad = pInf.calidad;    	
        this.tirador = pInf.tirador;
        this.fuerza = pInf.fuerza;
        this.angulo = pInf.angulo;
        this.anguloVert = pInf.anguloVert;
        this.posicionesBalon = pInf.posicionesBalon.clone();
        crearArrays();
    }
    
    public PaseInfo(int tirador, double fuerza, double angVert, double angHor) 
    {
    	entrenador = Entrenador.obtenerEntrenador();
        calidad = new CalidadPase(this);
        this.tirador = tirador;
        this.fuerza = fuerza;
        angulo = angHor;
        anguloVert = angVert;
        crearArrays();
    }
    
    public PaseInfo() 
    {
    	entrenador = Entrenador.obtenerEntrenador();
    
        calidad = new CalidadPase(this);
        tirador = -1;
        fuerza = 0;
        angulo = 0;
        anguloVert = 0;
        crearArrays();
   	
    }
    
    private void crearArrays()
    {
    	posicionesBalon = new PosicionBalon[N_POSICIONES];
    	errAngularNegativo = new PosicionBalon[N_POSICIONES];
    	errAngularPositivo = new PosicionBalon[N_POSICIONES];
    }
       
    //Rellena los datos para el analisis de la calidad del pase una vez simulado
    private void calcularCalidad()
    {
    	calidad.libreContrarios = true;
    	   	
		int [] canKick = entrenador.Gs.canKick();
		iterDisparo = entrenador.Gs.iterationsToKick();
		
			for (int i = 0; i < canKick.length; i++) 
			{
				iterDisparo[canKick[i]] = Constants.ITERACIONES_GOLPEAR_BALON;
			}
    	
    	    	
    	//Comprobamos si el contrario se hace con el balon o nosotros
    	
    	for (int iter = 1; iter < N_POSICIONES; ++iter)
    	{
    		if ( posicionesBalon[iter].distancia(entrenador.Gs.rivalPlayers()[posicionesBalon[iter].getPosicion().nearestIndex(entrenador.Gs.rivalPlayers())]) < 2) calidad.libreContrarios = false;
    		    		    		
    		//Si alguno de los rivales puede alcanzar el balon entonces el pase no es seguro
    		if ( puedeInterceptarBalon(posicionesBalon[iter], true, iter))
    		{
    			calidad.paseSeguro = false;
    					
    			break;
    		}
    		
    		//Vemos si el pase lo coge algun jugador nuestro en esta iteraccion
    		if ( puedeInterceptarBalon(posicionesBalon[iter], false, iter))
    		{
    			calidad.paseSeguro = true;    			
    			calidad.posFinalBalon = posicionesBalon[iter].getPosicion();
    			iterPase = iter;
    			break;
    		}
    	}
    	
    	if (calidad.libreContrarios) calidad.calidad += 10; 
    	
    	//Calculamos lo mismo pero con el errorPositivo
    	for (int iter = 0; iter < N_POSICIONES; ++iter)
    	{
    		
    		//Si alguno de los rivales puede alcanzar el balon entonces el pase no es seguro
    		if ( puedeInterceptarBalon(errAngularPositivo[iter], true, iter)  )
    		{
    			break;
    		}
    		//Vemos si alguno de nuestros 2 jugadores m�s cercano al punto destino alcanza el balon
    		if ( puedeInterceptarBalon(errAngularPositivo[iter], false, iter) )
    		{
    			calidad.calidad += 1;
    			break;
    		}
    	
    		
    	}
    	
    	
    	//Calculamos los mismo pero con el errorNegativo
    	for (int iter = 0; iter < N_POSICIONES; ++iter)
    	{
    		//Si alguno de los rivales puede alcanzar el balon entonces el pase no es seguro
    		if ( puedeInterceptarBalon(errAngularNegativo[iter], true, iter) )
    		{
    			break;
    		}
    		
    		//Vemos si alguno de nuestros 2 jugadores m�s cercano al punto destino alcanza el balon
    		if ( puedeInterceptarBalon(errAngularNegativo[iter], false, iter)  )
    		{
    			calidad.calidad += 1;
    			break;
    		}
    	
    	}
    	
    }
      
    /**
	 * Rellena un objeto paseInfo con los datos del pase simulado
	 * @param pInf
	 */
	protected void simularPase()
	{
		
		
		//Introducimos la posicion inicial
		
		posicionesBalon[0] = new PosicionBalon(entrenador.Gs.ballPosition());
		errAngularPositivo[0] = new PosicionBalon(entrenador.Gs.ballPosition());
		errAngularNegativo[0] = new PosicionBalon(entrenador.Gs.ballPosition());
		
		//Calculamos la velocidad del balon que depende de la fuerza con la que se golpee el balon y la potencia del jugador
		
		double vel = this.fuerza * Constants.getVelocidadRemate(entrenador.Gs.getMyPlayerPower(tirador)); 
		
		//La descomponemos en sus componentes vertical y horizontal
		
	    double velVert = vel * Math.sin(anguloVert);
        double velHor = vel * Math.cos(anguloVert);
		
		//Creamos una trayectoria con estos datos
		
		AbstractTrajectory trayectoria= new AirTrajectory(velHor, velVert, 0, 0);
		
		//Coordenadas 
		
		double r; //radial
		double x;
		double y;
		double z;

		//Error Angular
		double errorAngular = Constants.getErrorAngular(entrenador.Gs.getMyPlayerError(tirador));

		
		//Calculamos las diferentes posiciones del balon

		for (int i = 1; i < N_POSICIONES; i++) 
		{
             r = trayectoria.getX((double) i / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
             z = trayectoria.getY((double) i / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA*2;
             
             //Convertimos las coordenadas radiales en coordenadas normales de x,y
                             
	          x = (r * Math.cos(this.angulo)) + posicionesBalon[0].getX();
	          y = (r * Math.sin(this.angulo)) + posicionesBalon[0].getY();
	         	            
	         posicionesBalon[i] = new PosicionBalon(x,y,z);
	         
	         x = (r * Math.cos(this.angulo + errorAngular))  + posicionesBalon[0].getX() ;
	         y = (r * Math.sin(this.angulo + errorAngular))  + posicionesBalon[0].getY();
	         
	         errAngularPositivo[i] = new PosicionBalon(x,y,z);
	         
	         x = (r * Math.cos(this.angulo - errorAngular))  + posicionesBalon[0].getX();
	         y = (r * Math.sin(this.angulo - errorAngular))  + posicionesBalon[0].getY();
	         	         
	         errAngularNegativo[i] = new PosicionBalon(x,y,z);


		}
		
		calcularCalidad();
	}

	public boolean puedeInterceptarBalon(PosicionBalon posBalon, boolean esRival, int iter)
	{
		    
	        GameSituations Gs = entrenador.Gs;
	        
	        //Analizamos el jugador m�s cercano al punto actual del balon
	        int jugador = (esRival)? posBalon.getPosicion().nearestIndex(Gs.rivalPlayers()): posBalon.getPosicion().nearestIndex(Gs.myPlayers());
	        	        
	        int iteraccionesDisp = (esRival)? Gs.rivalIterationsToKick()[jugador]: this.iterDisparo[jugador];
	        
	        //Si el jugador no puede rematar en esta iteraccion no puede recuperar el balon
	        
	        if (iteraccionesDisp > iter) return false;
	        
	        //En caso contrario calculamos a ver si puede alcanzar el balon dependiendo de su velocidad
	        
	        PlayerDetail detJugador = (esRival) ? Gs.rivalPlayersDetail()[jugador] : Gs.myPlayersDetail()[jugador];
	                
	        Position posJugador = (esRival) ? Gs.rivalPlayers()[jugador] : Gs.myPlayers()[jugador];
	        
	        //Distancias para controlar el  balon
	        double maxAltura = Constants.ALTURA_CONTROL_BALON;
	        double maxDistancia = Constants.DISTANCIA_CONTROL_BALON;
	                
	        //Si es portero y esta dentro del area entonces tiene un alcance mayor de control de balon
	        if (detJugador.isGoalKeeper() && Math.abs(posJugador.getX()) <= Constants.LARGO_AREA_GRANDE / 2 && Math.abs(posJugador.getY()) > Constants.ANCHO_AREA_GRANDE)
	        {
	            maxDistancia = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
	            maxAltura = Constants.ALTO_ARCO;
	        }
	        
	        if (posBalon.z > maxAltura) 
	        {	        	
	            return false;
	        }
	
	        double dist = posBalon.distancia(posJugador);
	        return dist - maxDistancia <= iter * Constants.getVelocidad(detJugador.getSpeed());
	    
	}
	 
}