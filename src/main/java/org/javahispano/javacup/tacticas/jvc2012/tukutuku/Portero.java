package org.javahispano.javacup.tacticas.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import java.util.ArrayList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Portero extends Jugador {

			
		public Portero(int x) 
		{
			super(x);		
		}


	@Override
	public ArrayList<Command> accionDefensa() 
	{
		ArrayList<Command> accion = new ArrayList<Command>();
		
		//Colocamos el portero segun la posicion del balon
		
		Position posBalon = entrenador.Gs.ballPosition();
		Position posPortero = new Position();
		
		//Si el balon est� en la banda izquierda nos vamos al poste izquierdo
		if ( posBalon.getX() < Constants.posteIzqArcoInf.getX())
		{
			posPortero = posPortero.setPosition(Constants.posteIzqArcoInf.getX() + 1, (posBalon.getY() < Constants.posteDerArcoInf.getY()+ Constants.ANCHO_AREA_CHICA)? posBalon.getY(): Constants.posteDerArcoInf.getY() + Constants.ANCHO_AREA_CHICA);
			accion.add(new CommandMoveTo(numero, posPortero));
		}
		else
		{
			//Si est� en la banda derecha al poste derecho
		
			if ( posBalon.getX() > Constants.posteDerArcoInf.getX())
			{				
				posPortero = posPortero.setPosition(Constants.posteDerArcoInf.getX() - 1, (posBalon.getY() < Constants.posteDerArcoInf.getY()+ Constants.ANCHO_AREA_CHICA)? posBalon.getY(): Constants.posteDerArcoInf.getY() + Constants.ANCHO_AREA_CHICA);
				accion.add(new CommandMoveTo(numero, posPortero));
			}
			else
			{
			
				//Si est� en el centro del campo nos ponemos frente a �l
								
				posPortero = posPortero.setPosition(posBalon.getX(), Constants.posteDerArcoInf.getY() + Constants.ANCHO_AREA_CHICA);
				accion.add(new CommandMoveTo(numero, posPortero));
				
			}
		}
		return accion;
	}

	@Override
	public ArrayList<Command> accionRecuperacion(Position recuperacionPos) 
	{
		
		ArrayList<Command> accion = new ArrayList<Command>();
		
		accion.add(new CommandMoveTo(numero, recuperacionPos));
		
		return accion;
	}
	
	@Override
	public ArrayList<Command> accionRemate() 
	{
		
		ArrayList<Command> accion = new ArrayList<Command>();
		accion.add(new CommandHitBall(numero, posicionJugador.angle(entrenador.Gs.myPlayers()[Constants.centroArcoSup.nearestIndex(entrenador.Gs.myPlayers())])*(180/Math.PI), 1, true));
		return accion;	
	}



}
