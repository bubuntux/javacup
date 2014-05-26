package org.dsaw.javacup.tacticas.jvc2012.evolution;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class JugadoresManager {

	private static double LINEA_DEFENSA_BASE = -25;
	private static double LINEA_CENTROCAMPO_BASE = -15;
	private static double LINEA_DELANTERA_BASE = 20;
	
	public static Jugador[] obtenerJugadores (Position [] posicionJugadores, int defensas, int centro, int delanteros){
		
		Jugador [] jugadores = new Jugador [posicionJugadores.length];
		
		double [] cortesVerticalesDefensa = calcularCortes(defensas,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesCentro = calcularCortes(centro,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesDelantera = calcularCortes(delanteros,Constants.ANCHO_CAMPO_JUEGO/2);
		int j=0,x=0,y=0;
		
		for(int i=0;i<posicionJugadores.length;i++){
			if(i==SanMarinoEvolution.portero){
				jugadores[i] = new Jugador(i);
				jugadores[i].setRol(Jugador.PORTERO);
				jugadores[i].setPosicionBase(new Position(0,-51.5));
			}else if(defensas>0){
				jugadores[i] = new Jugador(i);
				jugadores[i].setRol(Jugador.DEFENSA);
				jugadores[i].setPosicionBase(new Position(cortesVerticalesDefensa[j],LINEA_DEFENSA_BASE));
				jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDefensa[j++],LINEA_DEFENSA_BASE));
				defensas--;
			}else if(centro>0){
				jugadores[i] = new Jugador(i);
				jugadores[i].setRol(Jugador.CENTROCAMPISTA);
				jugadores[i].setPosicionBase(new Position(cortesVerticalesCentro[x],LINEA_CENTROCAMPO_BASE));
				jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesCentro[x++],LINEA_CENTROCAMPO_BASE+25));
				centro--;
			}else if(delanteros>0){
				jugadores[i] = new Jugador(i);
				jugadores[i].setRol(Jugador.DELANTERO);
				jugadores[i].setPosicionBase(new Position(cortesVerticalesDelantera[y],LINEA_DELANTERA_BASE));
				if(delanteros>2){
					jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDelantera[y++],LINEA_DELANTERA_BASE+5));
				}else if(delanteros>1){
					jugadores[i].setPosicionBase(new Position(cortesVerticalesDelantera[y],LINEA_DELANTERA_BASE+15));
					jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDelantera[y++],LINEA_DELANTERA_BASE+15));
				}else{
					jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDelantera[y++],LINEA_DELANTERA_BASE));
				}
				delanteros--;
			}
		}
		return jugadores;
	}
	
	public static Jugador[] cambiarTactica (Jugador [] jugadores, int defensas, int centro, int delanteros){
		
		double [] cortesVerticalesDefensa = calcularCortes(defensas,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesCentro = calcularCortes(centro,Constants.ANCHO_CAMPO_JUEGO/2);
		double [] cortesVerticalesDelantera = calcularCortes(delanteros,Constants.ANCHO_CAMPO_JUEGO/2);
		int j=0,x=0,y=0;
		
		for(int i=0;i<jugadores.length;i++){
			if(i==SanMarinoEvolution.portero){
				jugadores[i].setRol(Jugador.PORTERO);
				jugadores[i].setPosicionBase(new Position(0,-51.5));
			}else if(defensas>0){
				jugadores[i].setRol(Jugador.DEFENSA);
				jugadores[i].setPosicionBase(new Position(cortesVerticalesDefensa[j],LINEA_DEFENSA_BASE));
				jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDefensa[j++],LINEA_DEFENSA_BASE));
				defensas--;
			}else if(centro>0){
				jugadores[i].setRol(Jugador.CENTROCAMPISTA);
				jugadores[i].setPosicionBase(new Position(cortesVerticalesCentro[x],LINEA_CENTROCAMPO_BASE));
				jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesCentro[x++],LINEA_CENTROCAMPO_BASE+25));
				centro--;
			}else if(delanteros>0){
				jugadores[i].setRol(Jugador.DELANTERO);
				jugadores[i].setPosicionBase(new Position(cortesVerticalesDelantera[y],LINEA_DELANTERA_BASE));
				if(delanteros>2){
					jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDelantera[y++],LINEA_DELANTERA_BASE+5));
				}else if(delanteros>1){
					jugadores[i].setPosicionBase(new Position(cortesVerticalesDelantera[y],LINEA_DELANTERA_BASE+15));
					jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDelantera[y++],LINEA_DELANTERA_BASE+15));
				}else{
					jugadores[i].setPosicionAtaqueBase(new Position(cortesVerticalesDelantera[y++],LINEA_DELANTERA_BASE));
				}
				delanteros--;
			}
		}
		return jugadores;
	}
	
	public static void actualizarPosiciones (Jugador [] jugadores, GameSituations sp){	
		
		Position [] posicionJugadores = sp.myPlayers();
		int delanteros = 0;
		for(int i=0;i<posicionJugadores.length;i++){
			if(jugadores[i].getRol()==Jugador.DELANTERO){
				delanteros++;
			}
			jugadores[i].setPosicion(posicionJugadores[i]);
		}
		
		if(sp.iteration()%50==0){
			Position pos;
			if(delanteros==3){
				pos = jugadores[8].getPosicionBase();
				jugadores[8].setPosicionBase(jugadores[9].getPosicionBase());
				jugadores[9].setPosicionBase(jugadores[10].getPosicionBase());
				jugadores[10].setPosicionBase(pos);
				pos = jugadores[8].getPosicionAtaqueBase();
				jugadores[8].setPosicionAtaqueBase(jugadores[9].getPosicionAtaqueBase());
				jugadores[9].setPosicionAtaqueBase(jugadores[10].getPosicionAtaqueBase());
				jugadores[10].setPosicionAtaqueBase(pos);
			}else if (delanteros==2){
				pos = jugadores[9].getPosicionBase();
				jugadores[9].setPosicionBase(jugadores[10].getPosicionBase());
				jugadores[10].setPosicionBase(pos);
				pos = jugadores[9].getPosicionAtaqueBase();
				jugadores[9].setPosicionAtaqueBase(jugadores[10].getPosicionAtaqueBase());
				jugadores[10].setPosicionAtaqueBase(pos);
			}
			
		}
	}
	

	public static void actualizarRivalesEnZona (Jugador [] jugadores, GameSituations sp, Rival [] rivales){	
		
		Position [] posicionJugadores = sp.myPlayers();
		
		Rival [] rivalesZona;
		for(int i=0;i<posicionJugadores.length;i++){
			rivalesZona = TacticasUtil.obtenerRivalesZona(jugadores[i],rivales);
			jugadores[i].setRivalesZona(rivalesZona);
		}
	}
	
	
	public static double [] calcularCortes(int jugadores, double ancho){
		double [] cortes = new double [jugadores];
		
		
		double corte = ancho/jugadores;
		
		int j=1;
		
		for (int i = 0; i < cortes.length; i++) {
			if(i<jugadores/2){
				if(jugadores%2==1){
					cortes[i]= (i-(jugadores/2))*(corte);
				}else{
					cortes[i]= (i-(jugadores/2))*(corte/j++);
				}
			}else{
				if(jugadores%2==1){
					if(i==jugadores/2){
						cortes[i]=0;
					}else{
						cortes[i]= (i-(jugadores/2))*corte;
					}
				}else{
					cortes[i]= (1+i-(jugadores/2))*(corte/--j);
				}
			}
		}
		
		return cortes;
	}
	
//	public static void main(String[] args) {
//		
//		double [] cortes = calcularCortes(6, 38);
//		
//		for (int i = 0; i < cortes.length; i++) {
//			System.out.println(cortes[i]+" ");
//		}
//		
//	}

}
