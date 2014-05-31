package org.dsaw.javacup.tactics.jvc2013.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 *  Clase encargada de realizar calculos generales de apoyo a los jugadores y de pensar
 *  en el partido de forma general.
 * @author Victor Gavil�n
 *
 */
public class Entrenador 
{
	
	//******** VARIABLES ***************
	
	/**
	 * Lista de comandos a ejecutar en la iteracion
	 */
	private ArrayList<Command>comandos = null;
	
	private int variacionAtaque = 0;
	
	/**
	 * Indica si estamos atacando o defendiendo
	 */

	public boolean atacando = false;
	
	public int jugadorDestinoPase = -1;
	public int iterDestinoPase = 0;
	public Position posDestinoPase = null;
	
	/**
	 * Referencia a todos los jugadores del equipo
	 */
	private ArrayList<Jugador>misJugadores = null;
	
	/**
	 * Jugadores sin asignar acci�n
	 */
	public ArrayList<Integer>jugadoresDisponibles = null;
	/**
	 * Referencia a la clase GameSituations
	 */
	GameSituations Gs = null;
	
	/**
	 * Referencia singleton 
	 */
	
	private static Entrenador entrenador = null;
	
	
	public Tactica tactica = null;
	
	//***** METODOS ********
		
	public Entrenador(Tactica tactic) 
	{
		tactica = tactic;
		entrenador = this;		
		comandos = new ArrayList<Command>();
		misJugadores = new ArrayList<Jugador>();
		
	}

	
	private void rellenarArrayJugadores()
	{
		misJugadores.add(0, new Portero(0));		
		
		for (int x = 1; x < 4; ++x)
		{
			misJugadores.add(x, new Delantero(x));			
		}
			for (int x = 4; x < 8; ++x)
		{
			misJugadores.add(x, new CentroCampista(x));			
		}
		
		for (int x = 8; x < 11; ++x)
		{
			misJugadores.add(x, new Defensa(x));		
		}

	}

	/**
	 * Devuelve el objeto singleton
	 */
	
		public static Entrenador obtenerEntrenador()
		{
			return entrenador;
		}
	
	//Indica que alineacion utilizar en cada momento
	public Position[] obtenerAlineacion()
	{
		Position[] alineacion = tactica.alineacion1;
		
		if (variacionAtaque <= 3)
		{
			if (Gs.ballPosition().getY() > -Constants.LARGO_CAMPO_JUEGO/4) alineacion = tactica.alineacion2;
			if (Gs.ballPosition().getY() > 0) alineacion = tactica.alineacion3;
			if (Gs.ballPosition().getY() > (Constants.LARGO_CAMPO_JUEGO/6))	alineacion = tactica.alineaciones.get(3 + variacionAtaque);
			if (Gs.ballPosition().getY() > (Constants.LARGO_CAMPO_JUEGO/3))   alineacion = tactica.alineaciones.get(4 + (2 * variacionAtaque));
		}
		else
		{
			if (Gs.ballPosition().getY() > -Constants.LARGO_CAMPO_JUEGO/4) 
			{
				alineacion = tactica.alineacion2;
			}
			
			if (Gs.ballPosition().getY() > 0)
			{
				Position posiciones[];
						
				posiciones = (variacionAtaque == 4) ? calcularPosiiones(tactica.alineacion14) : calcularPosiiones(tactica.alineacion15); 
				
				for (int x = 1; x < 9; ++x)
				{
					alineacion[x] = posiciones[x];
				}
			}
		}
		
			if (Gs.isRivalStarts() && Gs.ballPosition().getY() == 0) alineacion = tactica.alineacion13;
			if (Gs.isStarts() && Gs.ballPosition().getY() == 0) alineacion = tactica.alineacion12;
				
			return alineacion;
	}
	
	
	private Position[] calcularPosiiones(Position[] positions)
	{
		double height = Math.max(Constants.LARGO_CAMPO_JUEGO/3, 2*Math.min(Math.abs(Constants.centroArcoInf.getY()-Gs.ballPosition().getY()), Math.abs(Constants.centroArcoSup.getY()-Gs.ballPosition().getY())));
		double width = Math.max(Constants.ANCHO_CAMPO_JUEGO*2/3, 2*Math.min(Math.abs(Constants.cornerInfIzq.getX()-Gs.ballPosition().getX()), Math.abs(Constants.cornerInfDer.getX()-Gs.ballPosition().getX())));
		
		Position center = new Position(Gs.ballPosition().getX(), Gs.ballPosition().getY());
		
		if(center.getX() + width/2 > Constants.cornerInfDer.getX())
		{
			center = center.movePosition(-(center.getX() + width/2 - Constants.cornerInfDer.getX()), 0);
		}
		
		if(center.getX() - width/2 < Constants.cornerInfIzq.getX())
		{
			center = center.movePosition(Constants.cornerInfIzq.getX() - (center.getX()-width/2), 0);
		}
		
		if(center.getY() + height/2 > Constants.centroArcoSup.getY())
		{
			center = center.movePosition(0, -(center.getY() + height/2 - Constants.centroArcoSup.getY()));
		}
		
		if(center.getY() - height/2 < Constants.centroArcoInf.getY())
		{
			center = center.movePosition(0, Constants.centroArcoInf.getY() - (center.getY() - height/2));
		}
		
		Position[] newPositions = new Position[11];
		
		for (int i = 0; i < newPositions.length; i++) 
		{
			newPositions[i] = new Position(positions[i].getX()*width/Constants.ANCHO_CAMPO_JUEGO + center.getX(), positions[i].getY()*height/Constants.LARGO_CAMPO_JUEGO + center.getY());
		}
		return newPositions;
	}
	
	/**
	 * Genera los comandos para la iteraccion actual
	 * @param Gs Objeto GameSituations
	 * @return Comandos para ejecutar en la iteracci�n actual
	 */
	public ArrayList<Command> procesarIteraccion(GameSituations Gs)
	{
		
		//Obtenemos el objeto Gs actual
		this.Gs = Gs;
		
		double distMisJugadores = Gs.ballPosition().distance( Gs.myPlayers()[(Gs.ballPosition().nearestIndex(Gs.myPlayers()))] ); 
		double distRivales = Gs.ballPosition().distance( Gs.rivalPlayers()[(Gs.ballPosition().nearestIndex(Gs.rivalPlayers()))] );
				
		atacando = Gs.isStarts() || (!Gs.isRivalStarts() && distMisJugadores < distRivales);
		
		if (Gs.ballPosition().getY() < 0)
		{
			if (Gs.iteration() % (Constants.ITERACIONES/10) == 0)
			{
				++variacionAtaque;
				//System.out.println("Variacion de ataque a: " + variacionAtaque);
			}
			if (variacionAtaque > 5) variacionAtaque = 0;
		}
			
		//Rellenamos el array con nuestros jugadores si todavia no se ha hecho
		if (misJugadores.size() == 0)  rellenarArrayJugadores();
		
		
		//Limpiamos los marcajes
		for (Jugador jugador:misJugadores) 
    	{
    		jugador.marcaje = -1;
    	}
		
		//Actualizamos las posiciones
		
		Position posJugadores[] = Gs.myPlayers();
				
		for (Jugador jugador : misJugadores)
		{
			jugador.posicionJugador = posJugadores[jugador.numero];
		}
		
		//vaciamos la lista de comandos
		comandos.clear();
		
		//Rellenamos la lista de jugadores sin asignar accion
		if (jugadoresDisponibles == null)
			jugadoresDisponibles = new ArrayList<Integer>();
		else
			jugadoresDisponibles.clear();
		
				
		for (int i=0; i < 11; ++i)
		{
			jugadoresDisponibles.add(new Integer(i));
		}
		
		//asignamos marcajes si estamos defendiendo
		if (!atacando && Gs.ballPosition().getY() < 20) asignaMarcajes();
		
		//Si se ha hecho un pase el jugador debe desplazarse al destino
		
		if (--iterDestinoPase >= 0)
		{
			comandos.add(new CommandMoveTo(jugadorDestinoPase, posDestinoPase));
			jugadoresDisponibles.remove(new Integer(jugadorDestinoPase));
		}
		
	  	//Ejecutamos la accion de disparo
        
		int jugadoresCanKick[] = Gs.canKick();
		
        if (jugadoresCanKick.length > 0) 
        {
        	
        	for (int jugador:jugadoresCanKick)
        	{
        		
        		comandos.addAll(misJugadores.get(jugador).accionRemate());
        		
        		    		
        		jugadoresDisponibles.remove(new Integer(jugador));        		
        	}
            
        }
        
		
      //Accion recuperacion
        
      //Si no saca el rival
        if (!Gs.isRivalStarts()) 
        {
            //Intentamos recuperar el balon
            int[] recuperadores = Gs.getRecoveryBall();
            
      
            if (recuperadores.length > 1) 
            {
                double[] doubRecuperacion = Gs.getTrajectory(recuperadores[0]);
                Position posRecuperacion = new Position().setPosition(doubRecuperacion[0], doubRecuperacion[1]);
               
                //Hacemos que los dos jugadores m�s cercanos a la pelota intenten recuperarla
                for (int i = 1; i < (recuperadores.length < 3 ? recuperadores.length : 3) ; i++) 
                {
                	//Si es el portero solo dejamos que vaya si no hay otro jugador m�s que pueda ir
                	//if ((recuperadores[i] == 0) && recuperadores.length > 1) continue;
                	
                	if ((recuperadores[i] == 0) && posRecuperacion.getY() <= Constants.posteDerArcoInf.getY())
                	{
                		posRecuperacion = new Position(posRecuperacion.getX(), Constants.posteDerArcoInf.getY());
                	}
                    comandos.addAll(misJugadores.get( recuperadores[i] ).accionRecuperacion(posRecuperacion));
                    jugadoresDisponibles.remove( new Integer( recuperadores[i] ) );
                }
            }
        }						
	
	      
        	//Accion defensa       
        	for (Integer jugadorNum:jugadoresDisponibles)
        
        	{
        		comandos.addAll(misJugadores.get(jugadorNum).accionDefensa());
        	}
        
       /* int jugCercanosNum [] =  Constants.centroArcoInf.nearestIndexes(Gs.rivalPlayers());
        //Organizamos la defensa yendo cada uno a por un jugador de los m�s pr�ximos a la porteria
        	for (int x = 10; x > 0; --x)
        	{
        		if (!jugadoresDisponibles.contains(x)) continue;
        		if (Gs.rivalPlayers()[jugCercanosNum[10-x]].getY() < - 10)
        		{
        			comandos.add( new CommandMoveTo(x, Gs.rivalPlayers()[jugCercanosNum[10-x]].moveAngle(-90, 1)) );
        			
        		}
        		else
        			{        				
        				comandos.addAll(misJugadores.get(x).accionDefensa());
        			}
        	}
        	
        	//Accion defensa del portero
        	comandos.addAll(misJugadores.get(0).accionDefensa());
*/
      	return comandos;
	}


	private void asignaMarcajes() 
	{
	    	
	      	
	    	HashSet<Integer> rivalesMarcados = new HashSet<Integer>();
	    	int defensasQueMarcan = 0;
	    	
	    	int[] arrayRivalesCercanos = Constants.centroArcoInf.nearestIndexes(Gs.rivalPlayers());
	    	
	    	// Vamos a establecer los marcajes de la defensa.
	
			// Recorremos los rivalPlayers que se encuentran en mi defensa para asignarles un marcaje.
			for (int rival:arrayRivalesCercanos) 
			{
				
				int jugMiosCercanos[] = Gs.rivalPlayers()[rival].nearestIndexes(Gs.myPlayers()); 
				
				for (int x=0; x < jugMiosCercanos.length; ++x)
				{
					//El portero no marca
					if (jugMiosCercanos[x] == 0) continue;
					
					//El libero tampoco
					if (jugMiosCercanos[x] == 8) continue;
					
					if (misJugadores.get(jugMiosCercanos[x]).marcaje == -1)
					{
						misJugadores.get(jugMiosCercanos[x]).marcaje = rival;
						break;
					}
				}
			}
				
									
		}
}
