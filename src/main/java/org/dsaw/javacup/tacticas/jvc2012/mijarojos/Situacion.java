package org.dsaw.javacup.tacticas.jvc2012.mijarojos;

import java.util.LinkedList;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class Situacion {
	private GameSituations sp;
	
	private int[] recoveryBall;
	private int[] rivalRecoveryBall;
	
	private int[] chutadores;
	private int[] chutadoresRival;
	
	public Situacion(GameSituations sp){
		this.sp = sp;	
	}
	
	public int getIterRecoveryBall(){
		int[] recup = getRecoveryBall();
		if(recup.length > 0){
			return recup[0];
		}
		return Integer.MAX_VALUE;
	}

	public int getIterRivalRecoveryBall(){
		int[] recup = getRivalRecoveryBall();
		if(recup.length > 0){
			return recup[0];
		}
		return Integer.MAX_VALUE;
	}

	public int[] getChutadores(){
		if(this.chutadores != null){
			return this.chutadores;
		}
		this.chutadores = sp.canKick();
		return this.chutadores;
	}

	public int[] getChutadoresRival(){
		if(this.chutadoresRival != null){
			return this.chutadoresRival;
		}
		this.chutadoresRival = sp.rivalCanKick();
		return this.chutadoresRival;
	}
	
	public int[] getRecoveryBall(){
		if(this.recoveryBall != null){
			return this.recoveryBall;
		}
		this.recoveryBall = sp.getRecoveryBall();
		return this.recoveryBall;
	}
	
	public int[] getRivalRecoveryBall(){
		if(this.rivalRecoveryBall != null){
			return this.rivalRecoveryBall;
		}
		this.rivalRecoveryBall = this.getRivalRecoveryBallPrivado();
		return this.rivalRecoveryBall;
	}
	
	/**retorna un array donde el primer elemento es la iteration donde se puede recuperar el ballPosition,
    los siguientes números corresponden a los indices de los jugadores que pueden recuperar el ballPosition,
    ordenados desde el mas cercano al mas lejano del punto de recuperación.*/
    private int[] getRivalRecoveryBallPrivado() {    	
    	Position[] jugadoresRivales = sp.rivalPlayers();
        int it = 0;
        boolean found = false;
        Position pJug;
        double dist0, dist;
        int idxFound = -1;
        LinkedList<Double> founds = new LinkedList<Double>();
        PlayerDetail detalles[] = sp.myPlayersDetail();
        while (!found) {
            double[] posBalon = sp.getTrajectory(it);
            if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
                return new int[]{};
            }
            if (posBalon[2] <= Constants.ALTO_ARCO) {
                for (int i = 0; i < jugadoresRivales.length; i++) {
                    if (posBalon[2] <= (detalles[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                        pJug = jugadoresRivales[i];
                        dist0 = (double) it * Constants.getVelocidad(sp.rivalPlayersDetail()[i].getSpeed());
                        dist = pJug.distance(new Position(posBalon[0], posBalon[1]));
                        if (dist0 >= dist) {
                            found = true;
                            founds.add(dist);
                            founds.add((double) i);
                            idxFound = it;
                        }
                    }
                }
            }
            it++;
        }
        for (int i = 2; i < founds.size(); i = i + 2) {
            for (int j = 0; j < i; j = j + 2) {
                if (founds.get(i) < founds.get(j)) {
                    dist0 = founds.get(i);
                    dist = founds.get(i + 1);
                    founds.set(i, founds.get(j));
                    founds.set(i + 1, founds.get(j + 1));
                    founds.set(j, dist0);
                    founds.set(j + 1, dist);
                }
            }
        }
        for (int i = founds.size() - 1; i >= 0; i = i - 2) {
            founds.remove(i - 1);
        }
        founds.add(0, (double) idxFound);
        int[] result = new int[founds.size()];
        for (int i = 0; i < founds.size(); i++) {
            result[i] = (int) founds.get(i).doubleValue();
        }
        return result;
    }
    

	private boolean sacoBandaCorner() {
		return sp.isStarts()
				&& (Math.abs(sp.ballPosition().getX()) * 2d == Constants.ANCHO_CAMPO_JUEGO);
	}
	
	private boolean sacaRivalBandaCorner() {
		return sp.isRivalStarts()
				&& (Math.abs(sp.ballPosition().getX()) * 2d == Constants.ANCHO_CAMPO_JUEGO);
	}
	
	public SituacionActual getActual(){
		//SACO
		if(sp.isStarts()){
			if(sacoBandaCorner()){
				return SituacionActual.SacoBandaCorner;
			}
			//if(sp.ballPosition() == Constants.centroCampoJuego){
			//	return SituacionActual.SacoCentro;
			//}
			return SituacionActual.SacoPuerta;
		}
		//SACA RIVAL
		if(sp.isRivalStarts()){
			if(sacaRivalBandaCorner()){
				return SituacionActual.SacaRivalBandaCorner;
			}			
			//if(sp.ballPosition() == Constants.centroCampoJuego){
			//	return SituacionActual.SacaRivalCentro;
			//}
			return SituacionActual.SacaRivalPuerta;
		}
		//
		int[] misChutadores = getChutadores();
		int[] chutadoresRival = getChutadoresRival();
		if(misChutadores.length > 0){
			if(chutadoresRival.length > 0){
				return SituacionActual.PodemosChutarLosDos;
			} else {
				return SituacionActual.PuedoChutar;
			}
		} else {
			if(chutadoresRival.length > 0){
				return SituacionActual.PuedeRivalChutar;
			}
		}
		//QUIEN LLEGA ANTES
		int misIteraciones = getIterRecoveryBall();
		int susIteraciones = getIterRivalRecoveryBall();
		if(misIteraciones < susIteraciones)
		{
			return SituacionActual.LlegoAntes;
		} else if(misIteraciones > susIteraciones){
			return SituacionActual.LlegaRivalAntes;
		} else if(misIteraciones == susIteraciones &&
				misIteraciones < Integer.MAX_VALUE){
			return SituacionActual.LlegamosALaVez;
		}
		return SituacionActual.Indefinida;
	}
}

