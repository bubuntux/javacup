package org.dsaw.javacup.tacticas.jvc2012.evolution;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class MovimientosAtaque {

	private static double LINEA_DEFENSA_TOPE = Constants.centroArcoInf.getY()+Constants.LARGO_AREA_GRANDE;
	public static double LINEA_DEFENSA_BASE = -25;
	public static double LINEA_CENTROCAMPO_BASE = 0;
	public static double LINEA_DELANTERA_BASE = 25;
	
	private static double lineaDefensaActual=-1000;
	private static double lineaCentroActual=-1000;
	private static double lineaDelanteraActual=-1000;
	
	public static Command moverDefensa(Jugador jugador, Position posicionBalon, Jugador[] jugadores,
			Rival[] rivales, GameSituations sp, Position posicion) {
		// TODO Auto-generated method stub
		Command comando = new CommandMoveTo(jugador.getId(), posicion);
		Position posRecuperacion = TacticasUtil.puedoRecuperar(jugador, rivales, sp);
//		if(TacticasUtil.tengoBalon(jugador.getId(), sp.canKick())){
//			Pase pase = TacticasUtil.calcularPosicionPaseDefensa(jugador, jugadores, rivales, sp);
//			
//			if(pase!=null){
//				comando = new CommandHitBall(jugador.getId(), pase.getPosicionPase(), 
//        				TacticasUtil.getPotenciaPase(jugador.getPosicion(), 
//        						pase.getPosicionPase()), 10);
//			}else{
//				comando = new CommandHitBall(jugador.getId(),jugadores[9].getPosicion().movePosition(+5, 10), 1, 39);
//			}
//			
//			
//		}else 
		if(posRecuperacion!=null){

			comando = new CommandMoveTo(jugador.getId(),posRecuperacion);
		
		}else{
			Rival rival = TacticasUtil.calcularRivalMasCercano(Constants.centroArcoInf, rivales);
			if(jugador.getPosicion().getY()<rival.getPosicion().getY()){
				comando = new CommandMoveTo(jugador.getId(),jugador.getPosicion().movePosition(0, Constants.getVelocidad(sp.getMyPlayerSpeed(jugador.getId()))));
			}else{
				comando = new CommandMoveTo(jugador.getId(),jugador.getPosicion());
			}
		}
		
		return comando;
	}
	
	public static Command moverCentrocampo(Jugador jugador, Position posicionBalon, Jugador[] jugadores,
			Rival[] rivales, GameSituations sp, Position posicion) {
		// TODO Auto-generated method stub
		Command comando = new CommandMoveTo(jugador.getId(), posicion);
		Position posRecuperacion = TacticasUtil.puedoRecuperar(jugador, rivales, sp);
//		if(TacticasUtil.tengoBalon(jugador.getId(), sp.canKick())){
//			
//			Pase pase = TacticasUtil.calcularPosicionPaseCentro(jugador, jugadores, rivales, sp);
//			
//			if(pase!=null){
//				comando = new CommandHitBall(jugador.getId(), pase.getPosicionPase(), 
//        				TacticasUtil.getPotenciaPase(jugador.getPosicion(), 
//        						pase.getPosicionPase()), 10);
//				
//			}else{
//				comando = new CommandHitBall(jugador.getId(),jugadores[10].getPosicion().movePosition(0, 20),
//						TacticasUtil.getPotenciaPase(jugador.getPosicion(), jugadores[10].getPosicion().movePosition(0, 20)), 30);
//			}
//				
//		}else 
		if(posRecuperacion!=null){

			comando = new CommandMoveTo(jugador.getId(),posRecuperacion);
		
		}else{
			
			if(jugador.getPosicion().getY()<jugador.getPosicionAtaqueBase().getY()){
				comando = new CommandMoveTo(jugador.getId(),jugador.getPosicion().movePosition(0, Constants.getVelocidad(sp.getMyPlayerSpeed(jugador.getId()))));
			}
			else{
				comando = new CommandMoveTo(jugador.getId(),jugador.getPosicion());
			}
			
		}
		
		return comando;
	}
	
	public static Command moverDelantera(Jugador jugador, Position posicionBalon, Jugador[] jugadores,
			Rival[] rivales, GameSituations sp, Position posicion) {
		// TODO Auto-generated method stub
		Command comando = new CommandMoveTo(jugador.getId(), jugador.getPosicion());
		Position posRecuperacion = TacticasUtil.puedoRecuperar(jugador, rivales, sp);
//		if(TacticasUtil.tengoBalon(jugador.getId(), sp.canKick())){
//
//			double dPosteDerecho = Math.pow(Constants.posteDerArcoSup.getX()-rivales[0].getPosicion().getX(),2);
//			double dPosteIquierdo = Math.pow(Constants.posteIzqArcoSup.getX()-rivales[0].getPosicion().getX(),2);
//			double porteria = Math.pow(Constants.centroArcoSup.getY()-rivales[0].getPosicion().getY(),2);
//			
////			if(jugador.getPosicion().distance(Constants.centroArcoSup)<Constants.ANCHO_AREA_GRANDE+5){
//				if(System.currentTimeMillis()%2==0){
//					comando = new CommandHitBall(jugador.getId(),Constants.posteIzqArcoSup.movePosition(+1, 0), 1, 15);
//				}else{
//					comando = new CommandHitBall(jugador.getId(),Constants.posteDerArcoSup.movePosition(-1, 0), 1, 15);
//				}
////			}else{
////				Rival rivalMasCerca = rivales[getRivalMasCerca()];
////				double distancia = jugador.getPosicion().distance(rivalMasCerca.getPosicion());
////				
////				if(rivalMasCerca.getPosicion().getY()>jugador.getPosicion().getY() && distancia<15){
////					 if(rivalMasCerca.getPosicion().getX()<jugador.getPosicion().getX()){
////						 comando = new CommandHitBall(jugador.getId(), Constants.cornerSupDer, 0.38, false);
////					 }else if(rivalMasCerca.getPosicion().getX()>jugador.getPosicion().getX()){
////						 comando = new CommandHitBall(jugador.getId(), Constants.cornerSupIzq, 0.38, false);
////					 }else{
////						 comando = new CommandHitBall(jugador.getId(), jugador.getPosicion().movePosition(4, 0), 0.38, false);
////					 }
////				}else{
////					comando = new CommandHitBall(jugador.getId(), Constants.centroArcoSup, 0.38, false);
////				}
////			}
//			
//			
//			
//			
//		}else 
		if(posRecuperacion!=null){

			comando = new CommandMoveTo(jugador.getId(),posRecuperacion);
		
		}
		else if(jugador.getPosicion().getY()<jugador.getPosicionAtaqueBase().getY()){
			comando = new CommandMoveTo(jugador.getId(),jugador.getPosicion().movePosition(0, Constants.getVelocidad(sp.getMyPlayerSpeed(jugador.getId()))));
		}
		else{
			comando = new CommandMoveTo(jugador.getId(),jugador.getPosicionAtaqueBase());
		}
		
		return comando;
	}
	
	
	
	public static double getLineaDefensaActual() {
		return lineaDefensaActual;
	}

	public static void setLineaDefensaActual(double linea) {
		lineaDefensaActual = linea;
	}

	public static double getLineaCentroActual() {
		return lineaCentroActual;
	}

	public static void setLineaCentroActual(double linea) {
		lineaCentroActual = linea;
	}

	public static double getLineaDelanteraActual() {
		return lineaDelanteraActual;
	}

	public static void setLineaDelanteraActual(double linea) {
		lineaDelanteraActual = linea;
	}
	
	private static  void calcularLineaDefensa(Rival[] rivales, Position posicionBalon, Jugador [] jugadores){
		
		Position masAdelantado = TacticasUtil.posicionRivalMasAdelantado(rivales);
		
		if(getLineaDefensaActual()==-1000){
			setLineaDefensaActual(LINEA_DEFENSA_BASE);
		}
		
		if(!posicionBalon.isInsideGameField(0)){
			setLineaDefensaActual(LINEA_DEFENSA_BASE);
		}else if(masAdelantado.getY()<=getLineaDefensaActual()){
			
//			if(masAdelantado.getY()< LINEA_DEFENSA_TOPE){
//				setLineaDefensaActual(LINEA_DEFENSA_TOPE);
//			}else{
				setLineaDefensaActual(masAdelantado.getY()-1);
//			}
		}else{
			if(masAdelantado.getY()<LINEA_CENTROCAMPO_BASE){
				setLineaDefensaActual(masAdelantado.getY()-1);
			}
//			else{
//				setLineaDefensaActual(LINEA_DEFENSA_BASE);
//			}
			
		}
	}
	
	private static void calcularLineaCentrocampo(Rival[] rivales, Position posicionBalon, Jugador [] jugadores){
		
		if(getLineaCentroActual()==-1000){
			setLineaCentroActual(LINEA_CENTROCAMPO_BASE);
		}
		
		if(!posicionBalon.isInsideGameField(0)){
			setLineaCentroActual(LINEA_CENTROCAMPO_BASE);
		}else{
			setLineaCentroActual(posicionBalon.getY()+5);
		}

		
	}
	
	private static void calcularLineaDelantera(Rival[] rivales, Position posicionBalon, Jugador [] jugadores){
		
		if(getLineaDelanteraActual()==-1000){
			setLineaDelanteraActual(LINEA_DELANTERA_BASE);
		}
		
		if(posicionBalon.getY()<getLineaDelanteraActual()
				&& getLineaCentroActual()+30<=getLineaDelanteraActual()){
			setLineaDelanteraActual(getLineaDelanteraActual()+10);
		}else if(posicionBalon.getY()>getLineaDelanteraActual()){
			setLineaDelanteraActual(posicionBalon.getY());
		}else{
			setLineaDelanteraActual(LINEA_DELANTERA_BASE);
		}
	}
	
	public static double[] calcularLineasHorizontales(Rival[] rivales, Position posicionBalon, Jugador [] jugadores){
		
		calcularLineaDefensa(rivales, posicionBalon, jugadores);
		calcularLineaCentrocampo(rivales, posicionBalon,jugadores);
		calcularLineaDelantera(rivales, posicionBalon, jugadores);
		
		return new double []{getLineaDefensaActual(),getLineaCentroActual(),getLineaDelanteraActual()};
	}
}
