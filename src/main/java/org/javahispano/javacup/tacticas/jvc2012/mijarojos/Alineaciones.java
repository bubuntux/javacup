package org.javahispano.javacup.tacticas.jvc2012.mijarojos;

import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public final class Alineaciones {
    
	static Position alineacion0[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-(Constants.ANCHO_CAMPO_JUEGO*(0.25)),-(Constants.LARGO_CAMPO_JUEGO*(0.25))),
        new Position(0,-(Constants.LARGO_CAMPO_JUEGO*(0.25))),
        new Position(+(Constants.ANCHO_CAMPO_JUEGO*(0.25)),-(Constants.LARGO_CAMPO_JUEGO*(0.25))),
        new Position(-(Constants.ANCHO_CAMPO_JUEGO*(0.25)),0),
        new Position(-(Constants.ANCHO_CAMPO_JUEGO*(0.08)),-(Constants.LARGO_CAMPO_JUEGO*(0.05))),
        new Position(+(Constants.ANCHO_CAMPO_JUEGO*(0.08)),-(Constants.LARGO_CAMPO_JUEGO*(0.05))),
        new Position(+(Constants.ANCHO_CAMPO_JUEGO*(0.25)),0),
        new Position(0,+(Constants.LARGO_CAMPO_JUEGO*(0.13))),
        new Position(-(Constants.ANCHO_CAMPO_JUEGO*(0.12)),+(Constants.LARGO_CAMPO_JUEGO*(0.25))),
        new Position(+(Constants.ANCHO_CAMPO_JUEGO*(0.12)),+(Constants.LARGO_CAMPO_JUEGO*(0.25)))
    };
	
	static Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-9.272727272727272,-20.667420814479637),
        new Position(9.510489510489512,-19.95475113122172),
        new Position(20.923076923076923,-5.226244343891403),
        new Position(-24.48951048951049,-7.601809954751132),
        new Position(9.034965034965035,9.027149321266968),
        new Position(-19.020979020979023,21.61764705882353),
        new Position(16.88111888111888,22.330316742081447),
        new Position(-12.363636363636363,36.10859728506787),
        new Position(-10.461538461538462,9.502262443438914),
        new Position(9.034965034965035,35.63348416289593)
    };

	static Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-22.58741258741259,-27.794117647058822),
        new Position(-8.55944055944056,-29.93212669683258),
        new Position(20.685314685314687,-26.368778280542987),
        new Position(5.706293706293707,-30.16968325791855),
        new Position(-9.986013986013985,-13.303167420814479),
        new Position(-21.636363636363637,-1.6628959276018098),
        new Position(22.11188811188811,-1.1877828054298643),
        new Position(0.0,-0.23755656108597287),
        new Position(4.5174825174825175,-14.490950226244346),
        new Position(-4.041958041958042,-4.751131221719457)
    };

	static Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(7.846153846153847,-30.88235294117647),
        new Position(21.3986013986014,-31.59502262443439),
        new Position(-20.923076923076923,-29.694570135746606),
        new Position(-5.230769230769231,-23.28054298642534),
        new Position(-16.405594405594407,-18.054298642533936),
        new Position(14.503496503496503,-19.95475113122172),
        new Position(-6.895104895104895,-9.502262443438914),
        new Position(2.8531468531468533,-20.429864253393667),
        new Position(3.090909090909091,-10.214932126696834)
    };

	static Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(11.16030534351145,-1.3059701492537314),
        new Position(-10.641221374045802,-0.7835820895522387),
        new Position(-27.251908396946565,31.6044776119403),
        new Position(-10.641221374045802,30.559701492537314),
        new Position(9.603053435114505,28.992537313432837),
        new Position(25.69465648854962,28.992537313432837)
    };

	static Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(5.969465648854961,-5.485074626865671),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(22.580152671755727,-1.3059701492537314)
    };

	static Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(6.4885496183206115,-6.529850746268657),
        new Position(-6.4885496183206115,-6.529850746268657),
        new Position(22.580152671755727,-1.3059701492537314)
    };
    
	public static double getXPortero(GameSituations sp){
		double angle = -(sp.ballPosition().angle(sp.myPlayers()[0])) - (Math.PI / 2);
		if(angle > Math.PI){
			return 0;
		}
		return (angle / (Math.PI / 2)) * (Constants.LARGO_ARCO / 2);
	}
	
	public static Position ReubicarOutside(Position p){
		// Ordena a cada jugador que se ubique segun la alineacion0
		Position posReubicada = p.setInsideGameField();
		//si est� en el borde lo mete un poco m�s
		if(posReubicada.getX() == Constants.ANCHO_CAMPO_JUEGO / 2){ //banda derecha
			posReubicada = new Position(posReubicada.getX() - 1, posReubicada.getY());
		} else if (posReubicada.getX() == -(Constants.ANCHO_CAMPO_JUEGO / 2)){//banda izqda
			posReubicada = new Position(posReubicada.getX() - 1, posReubicada.getY());
		}
		if(posReubicada.getY() == Constants.LARGO_CAMPO_JUEGO / 2){ //fondo ataque
			posReubicada = new Position(posReubicada.getX(), posReubicada.getY() - 1);
		} else if (posReubicada.getY() == -(Constants.LARGO_CAMPO_JUEGO / 2)){//fondo defensa
			posReubicada = new Position(posReubicada.getX(), posReubicada.getY() + 1);
		}
		return posReubicada;
	}

	public static List<CommandMoveTo> execute(GameSituations sp) {
		LinkedList<CommandMoveTo> comandos = new LinkedList<CommandMoveTo>();
		// Obtiene las posiciones de tus jugadores
		Position[] jugadores = sp.myPlayers();
		Position posDespTemp;
		SituacionActual sitAct = new Situacion(sp).getActual();
		for (int i = 0; i < jugadores.length; i++) {
			posDespTemp = alineacion0[i];
			
			if (sitAct != SituacionActual.SacoPuerta && 
				sitAct != SituacionActual.SacaRivalPuerta){
				
				posDespTemp = posDespTemp.movePosition(
					i == 0 ? getXPortero(sp) : sp.ballPosition().getX() / 2, 
					sp.ballPosition().getY() / 2);
			}
			comandos.add(new CommandMoveTo(i, posDespTemp.setInsideGameField()));
			//comandos.add(new CommandMoveTo(i, Alineaciones.ReubicarOutside(posDespTemp)));
		}
		return comandos;
	}
}
