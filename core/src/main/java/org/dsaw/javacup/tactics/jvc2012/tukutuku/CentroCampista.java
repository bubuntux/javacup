package org.dsaw.javacup.tactics.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;

public class CentroCampista extends Jugador {

		private int variacionTiro = 0;
			
		public CentroCampista(int x) 
		{
			super(x);
			yMAX = Constants.LARGO_CAMPO_JUEGO / 4;
			yMin = - Constants.LARGO_CAMPO_JUEGO / 4;
			
		}


	@Override
	public ArrayList<Command> accionRemate() 
	{
		ArrayList<Command> accion = new ArrayList<>();
		
		//Comprobamos que haya algun tiro posible
				
		//Disparamos si estamos proximos al area
		if (this.posicionJugador.distance(Constants.centroArcoSup) < Constants.ANCHO_AREA_GRANDE*1.5)
		{
			TiroInfo tInfo = seleccionarTiro();
			
			if (tInfo != null)	accion.add(new CommandHitBall(numero, tInfo.angulo * (180/Math.PI), tInfo.fuerza, tInfo.anguloVert* (180/Math.PI)));
			
			//System.out.println("Calculamos tiro CENTROCAMPISTA. Distancia: " + this.posicionJugador.distance(Constants.centroArcoSup));
		}
		
		if (accion.size() == 0)	
		{	
			//Si no hay posibilidad de tiro intentamos un pase
			
			//Comprobamos que haya algun pase posible
			//Comprobamos que haya algun pase posible
			PaseInfo paseInf = seleccionarPase( 15, 165, 3 );
			
			if (paseInf != null)
			{
				accion.add(new CommandHitBall( numero, paseInf.angulo * (180 / Math.PI),  (entrenador.Gs.isStarts())? paseInf.fuerza /0.75 : paseInf.fuerza, paseInf.anguloVert* (180 / Math.PI)));
			
		    
		    //el jugador m�s proximo al punto destino debe acercarse a por el balon
		    
		    int jugReceptorPase = paseInf.calidad.posFinalBalon.nearestIndex(entrenador.Gs.myPlayers());
		    
		     accion.add(new CommandMoveTo(jugReceptorPase, paseInf.calidad.posFinalBalon));
		     
		     entrenador.jugadoresDisponibles.remove(new Integer(jugReceptorPase));
			}
		}	
			if ((accion.size() == 0) && (this.posicionJugador.distance(Constants.centroArcoSup) < Constants.ANCHO_AREA_GRANDE))
			{
				//Si no hay pase posible chutamos si estamos proximos a la porteria
				
				if (this.posicionJugador.distance(Constants.centroArcoSup) < Constants.ANCHO_AREA_GRANDE)
				{
					switch (variacionTiro)
					{
					case 0:
						accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.centroArcoSup) * (180/Math.PI), 1, 20));
						break;
					case 1:
						accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.posteDerArcoSup.movePosition(-1, 0))* (180/Math.PI), 1, 20));
						break;
					case 2:
						accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.posteIzqArcoSup.movePosition(2, 0))* (180/Math.PI), 1, 20));
						break;
					case 3:
						accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.centroArcoSup.movePosition(-3, 0))* (180/Math.PI), 1, 15));
						break;
					case 4:
						accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.centroArcoSup.movePosition(3, 0))* (180/Math.PI), 1, 15));
						break;
					}
					//System.out.println("Variacion tiro: " + variacionTiro);
					if (++variacionTiro > 4) variacionTiro = 0;
				}
				else
				{
					//Si esta aun lejos hacemos un pase al jugador m�s pr�ximo a la porteria que no sea el mismo
					
					int jugadorCercanoNum = Constants.centroArcoSup.nearestIndex(entrenador.Gs.myPlayers(), numero);
					Position jugadorCercanoPos = entrenador.Gs.myPlayers()[jugadorCercanoNum];
										
					accion.add(new CommandHitBall(numero, this.posicionJugador.angle(jugadorCercanoPos)* (180/Math.PI), (this.posicionJugador.distance(jugadorCercanoPos) < 20) ? 0.8 : 1, 30));
					//System.out.println("Pase ultimo jugador");
				}
			}
		
			
			
	
			return accion;
	}


}
