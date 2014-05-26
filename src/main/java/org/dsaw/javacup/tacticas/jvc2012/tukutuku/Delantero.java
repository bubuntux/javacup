package org.dsaw.javacup.tacticas.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import java.util.ArrayList;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.util.Constants;

public class Delantero extends Jugador {
	
	//private int variacionTiro = 0;
	private int porteroRival;
	
	public Delantero(int x) 
	{
		super(x);
		yMAX = Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA;
		yMin = 0;
		
		for  (x = 0; x < entrenador.Gs.rivalPlayersDetail().length; ++x)
		{
			if (entrenador.Gs.rivalPlayersDetail()[x].isGoalKeeper())
			{
				porteroRival = x;
				break;
			}
		}
		
	}

	
	@Override
	public ArrayList<Command> accionRemate() 
	{
		ArrayList<Command> accion = new ArrayList<>();
		
			
		//Disparamos si estamos proximos al area
		//if (this.posicionJugador.distance(Constants.centroArcoSup) < Constants.ANCHO_AREA_GRANDE * 2)
		{
			TiroInfo tInfo = seleccionarTiro();
			//System.out.println("Calculamos tiro DELANTERO. Distancia: " + this.posicionJugador.distance(Constants.centroArcoSup));
			if (tInfo != null)
			{
				//System.out.println("Ejecutamos tiro: angulo: " + tInfo.angulo* (180/Math.PI));
				accion.add(new CommandMoveTo(numero, posicionJugador.moveAngle(tInfo.angulo * (180/Math.PI), 1)));
				accion.add(new CommandHitBall(numero, tInfo.angulo * (180/Math.PI), tInfo.fuerza, tInfo.anguloVert* (180/Math.PI)));
			}
			
			
		}
		
		//Si estamos en un lateral centramos al jugador m�s cercano al punto de penalty
		
		/*if ((posicionJugador.getY() > Constants.penalSup.getY()-5) && (Math.abs(posicionJugador.getX()) > Constants.dimAreaChica.getX()/2))
		{
			accion.add(new CommandHitBall(numero,entrenador.Gs.myPlayers()[ posicionJugador.nearestIndex(entrenador.Gs.myPlayers(), numero) ], 0.5, false));
			System.out.println("Pase atr�s Delantero");
		}
		*/
		//Desde el centro disparamos a puerta
		if (numero == 1 && entrenador.Gs.ballPosition().getY() < 5)
		{
			accion.add(new CommandHitBall(numero, Constants.cornerSupIzq,1,30));
		}
		
		if (accion.size() == 0 && (this.posicionJugador.distance(Constants.centroArcoSup) < Constants.ANCHO_AREA_GRANDE * 1.2))
		{
			/*Position posPorteroRival = entrenador.Gs.rivalPlayers()[porteroRival];
			int signo = (posPorteroRival.getX() > 0) ? 1 : -1;
			
			if (Math.abs(posPorteroRival.getX()) < Constants.LARGO_ARCO/2)
			{	//Si no hay pase posible chutamos
				switch (variacionTiro)
				{
				case 0:
					accion.add(new CommandHitBall(numero, Constants.centroArcoSup.movePosition(1 * signo, 0),1, false));
					break;
				case 1:
					//accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.posteDerArcoSup.movePosition(-1, 0))* (180/Math.PI), 1, 10));
					accion.add(new CommandHitBall(numero, Constants.centroArcoSup.movePosition(2 * signo, 0),1, false));
					break;
				case 2:
					accion.add(new CommandHitBall(numero, Constants.centroArcoSup.movePosition(3 * signo, 0),1, false));
					break;
				case 3:
					accion.add(new CommandHitBall(numero, Constants.centroArcoSup.movePosition(3.5 * signo, 0),1, false));
					break;
				case 4:
					accion.add(new CommandHitBall(numero, Constants.centroArcoSup.movePosition(2.5 * signo, 0),1, false));
					break;
			
				}
			}
			else
				accion.add(new CommandHitBall(numero, entrenador.Gs.myPlayers()[numero].angle(Constants.centroArcoSup) * (180/Math.PI), 1, (Constants.centroArcoSup.distance(posicionJugador) < 20 ) ? 0 : 20));
			System.out.println("Variacion tiro: " + variacionTiro);
			if (++variacionTiro > 4) variacionTiro = 0;
		*/
			accion.add(tirarAPorteria());
		}
	
		
		if ((accion.size() == 0)) 
		{
		
			//Si no hay posibilidad de tiro intentamos un pase
			//Comprobamos que haya algun pase posible
			PaseInfo paseInf = seleccionarPase( 15, 165, 3 );
			
			if (paseInf != null)
			{
				accion.add(new CommandHitBall( numero, paseInf.angulo * (180 / Math.PI),  (entrenador.Gs.isStarts())? paseInf.fuerza /0.75 : paseInf.fuerza, paseInf.anguloVert* (180 / Math.PI)));
			
		    
		    //el jugador m�s proximo al punto destino debe acercarse a por el balon
		    
		    int jugReceptorPase = paseInf.calidad.posFinalBalon.nearestIndex(entrenador.Gs.myPlayers());
		    
		     accion.add(new CommandMoveTo(jugReceptorPase, paseInf.calidad.posFinalBalon));
		     
		     entrenador.jugadoresDisponibles.remove(jugReceptorPase);
			}
		}
			
		
			
	
		return accion;
	}


}
