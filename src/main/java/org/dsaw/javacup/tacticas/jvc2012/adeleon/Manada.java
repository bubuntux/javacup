package org.dsaw.javacup.tacticas.jvc2012.adeleon;

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.render.EstiloUniforme;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Manada implements Tactic {

	

	 Position alineacion1[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-16.643356643356643,-34.68325791855204),
		        new Position(0.4755244755244755,-34.44570135746606),
		        new Position(15.692307692307693,-34.20814479638009),
		        new Position(3.804195804195804,-20.90497737556561),
		        new Position(-6.419580419580419,-20.429864253393667),
		        new Position(-24.013986013986013,0.0),
		        new Position(21.16083916083916,-0.7126696832579186),
		        new Position(-6.6573426573426575,-6.414027149321266),
		        new Position(-0.951048951048951,-9.502262443438914),
		        new Position(-13.076923076923078,-0.23755656108597287)
		    };

		    Position alineacion2[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-15.454545454545453,-37.53393665158371),
		        new Position(-2.377622377622378,-37.29638009049774),
		        new Position(11.888111888111888,-37.05882352941177),
		        new Position(5.944055944055944,-19.95475113122172),
		        new Position(-7.846153846153847,-19.479638009049776),
		        new Position(-25.202797202797203,-0.7126696832579186),
		        new Position(29.48251748251748,-1.6628959276018098),
		        new Position(-3.5664335664335667,0.0),
		        new Position(-0.7132867132867133,-9.502262443438914),
		        new Position(2.377622377622378,-0.23755656108597287)
		    };

		    Position alineacion3[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-16.405594405594407,-37.53393665158371),
		        new Position(-1.188811188811189,-36.58371040723982),
		        new Position(14.97902097902098,-37.05882352941177),
		        new Position(9.034965034965035,-16.628959276018097),
		        new Position(-9.034965034965035,-15.678733031674208),
		        new Position(-26.867132867132867,-12.59049773755656),
		        new Position(28.76923076923077,-17.104072398190045),
		        new Position(-13.314685314685315,27.556561085972852),
		        new Position(-0.4755244755244755,7.364253393665159),
		        new Position(14.265734265734267,28.269230769230766)
		    };

		    Position alineacion4[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-10.6993006993007,-41.57239819004525),
		        new Position(-1.6643356643356644,-42.28506787330317),
		        new Position(8.55944055944056,-42.28506787330317),
		        new Position(3.3286713286713288,-32.30769230769231),
		        new Position(-6.895104895104895,-31.59502262443439),
		        new Position(-25.678321678321677,51.074660633484164),
		        new Position(25.916083916083913,-1.4253393665158371),
		        new Position(-25.678321678321677,45.61085972850679),
		        new Position(-26.391608391608393,42.76018099547511),
		        new Position(-25.678321678321677,48.223981900452486)
		    };

		    Position alineacion5[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-12.839160839160838,-46.56108597285068),
		        new Position(-4.27972027972028,-45.84841628959276),
		        new Position(4.755244755244756,-46.3235294117647),
		        new Position(7.132867132867133,-42.522624434389144),
		        new Position(-12.839160839160838,-42.047511312217196),
		        new Position(-19.020979020979023,-45.84841628959276),
		        new Position(12.839160839160838,-47.2737556561086),
		        new Position(0.4755244755244755,-36.34615384615385),
		        new Position(-0.951048951048951,-42.28506787330317),
		        new Position(-7.608391608391608,-40.38461538461539)
		    };

		    Position alineacion6[]=new Position[]{
		            new Position(0.2595419847328244,-50.41044776119403),
		            new Position(-12.839160839160838,-41.334841628959275),
		            new Position(-1.902097902097902,-41.334841628959275),
		            new Position(9.510489510489512,-40.859728506787334),
		            new Position(3.804195804195804,-31.83257918552036),
		            new Position(-8.55944055944056,-32.782805429864254),
		            new Position(-8.797202797202797,40.38461538461539),
		            new Position(25.202797202797203,-1.4253393665158371),
		            new Position(-11.174825174825173,38.4841628959276),
		            new Position(-5.706293706293707,36.58371040723982),
		            new Position(-10.461538461538462,34.68325791855204)
		        };

	    class TacticDetailImpl implements TacticDetail {

	        public String getTacticName() {
	            return "La Manada";
	        }

	        public String getCountry() {
	            return "México";
	        }

	        public String getCoach() {
	            return "Andrés";
	        }

	        public Color getShirtColor() {
	            return new Color(0, 102, 51);
	        }

	        public Color getShortsColor() {
	            return new Color(255, 255, 255);
	        }

	        public Color getShirtLineColor() {
	            return new Color(204, 0, 0);
	        }

	        public Color getSocksColor() {
	            return new Color(204, 0, 0);
	        }

	        public Color getGoalKeeper() {
	            return new Color(255, 153, 255        );
	        }

	        public EstiloUniforme getStyle() {
	            return EstiloUniforme.SIN_ESTILO;
	        }

	        public Color getShirtColor2() {
	            return new Color(0, 0, 0);
	        }

	        public Color getShortsColor2() {
	            return new Color(0, 0, 0);
	        }

	        public Color getShirtLineColor2() {
	            return new Color(204, 0, 0);
	        }

	        public Color getSocksColor2() {
	            return new Color(0, 0, 0);
	        }

	        public Color getGoalKeeper2() {
	            return new Color(255, 153, 255        );
	        }

	        public EstiloUniforme getStyle2() {
	            return EstiloUniforme.SIN_ESTILO;
	        }

	        class JugadorImpl implements PlayerDetail {

	            String nombre;
	            int numero;
	            Color piel, pelo;
	            double velocidad, remate, presicion;
	            boolean portero;
	            Position Position;

	            public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
	                    double velocidad, double remate, double presicion, boolean portero) {
	                this.nombre=nombre;
	                this.numero=numero;
	                this.piel=piel;
	                this.pelo=pelo;
	                this.velocidad=velocidad;
	                this.remate=remate;
	                this.presicion=presicion;
	                this.portero=portero;
	            }

	            public String getPlayerName() {
	                return nombre;
	            }

	            public Color getSkinColor() {
	                return piel;
	            }

	            public Color getHairColor() {
	                return pelo;
	            }

	            public int getNumber() {
	                return numero;
	            }

	            public boolean isGoalKeeper() {
	                return portero;
	            }

	            public double getSpeed() {
	                return velocidad;
	            }

	            public double getPower() {
	                return remate;
	            }

	            public double getPrecision() {
	                return presicion;
	            }

	        }

	        public PlayerDetail[] getPlayers() {
	            return new PlayerDetail[]{
	                new JugadorImpl("Howard", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
	                new JugadorImpl("Errasti", 2, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
	                new JugadorImpl("Evans", 3, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
	                new JugadorImpl("Santillana", 4, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
	                new JugadorImpl("Lozano", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
	                new JugadorImpl("Marrokin", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
	                new JugadorImpl("Teran", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
	                new JugadorImpl("Luisillo", 8, new Color(102,51,0), new Color(50,0,0),1.0d,1.0d,1.0d, false),
	                new JugadorImpl("Vila", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
	                new JugadorImpl("De Leon", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, false),
	                new JugadorImpl("Cantu", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false)
	            };
	        }
	    }

	    TacticDetail detalle=new TacticDetailImpl();
	    public TacticDetail getDetail() {
	        return detalle;
	    }

	    public Position[] getStartPositions(GameSituations sp) {
	    return alineacion2;
	    }

	    public Position[] getNoStartPositions(GameSituations sp) {
	    return alineacion1;
	    }
    
    public int[] jugadoresCercanos(int num, Position p1, Position[] players){
    	double[] jugadores=new double[10];
    	int[] numeros= new int[10];
    	int cont=0;
    	
    	for(int i=0; i<players.length; i++){
    		if(i!=num){
    			jugadores[cont]=calcularDistancia(p1,players[i]);
    			numeros[cont]=i;
    			cont++;
    		}
    	}
    	
    	numeros=bubbleSort(jugadores,numeros);
    	
    	return numeros;
    }
    
    public int[] jugadoresCercanos2(Position p1, Position[] players){
    	double[] jugadores=new double[11];
    	int[] numeros= new int[11];
    	int cont=0;
    	
    	for(int i=0; i<players.length; i++){
    		
    			jugadores[cont]=calcularDistancia(p1,players[i]);
    			numeros[cont]=i;
    			cont++;
    		}
    	
    	
    	numeros=bubbleSort(jugadores,numeros);
    	
    	return numeros;
    }
    
    public int[] enemigosCercanos(Position p1, Position[] players){
    	double[] jugadores=new double[11];
    	int[] numeros= new int[11];
    	int cont=0;
    	
    	for(int i=0; i<players.length; i++){
    			jugadores[cont]=calcularDistancia(p1,players[i]);
    			numeros[cont]=i;
    			cont++;
    		
    	}
    	
    	numeros=bubbleSort(jugadores,numeros);
    	
    	return numeros;
    }
    
    public double calcularDistancia(Position p1, Position p2){
    	double distancia=0;
    	
    	distancia=Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2) + Math.pow(p1.getY()-p2.getY(), 2));
    	
    	return distancia;
    }
    
    public int[] bubbleSort(double[] jugadores, int[] numeros){
    	double temp;
    	int temp2;
    	
    	for (int contador=0; contador<jugadores.length-1; contador++){
 			for(int i=0; i<jugadores.length-1-contador; i++){
 				
 				if(jugadores[i]>jugadores[i+1]){
 					temp=jugadores[i];
 					temp2=numeros[i];
 					
 					jugadores[i]=jugadores[i+1];
 					numeros[i]=numeros[i+1];
 					
 					jugadores[i+1]=temp;
 					numeros[i+1]=temp2;
 				}
 				
 			}
    	}
    	
    	return numeros;
    }
    
    public boolean estaLibre(Position p1, Position[] rivales){
    	boolean bandera=false;
    	for(int i=0; i<rivales.length; i++){
    		if((calcularDistancia(p1,rivales[i]))>=20){
    			bandera=true;
    		}
    		else{ 
    			bandera=false;
    			break;
    		}
    	}
    	
    	if(bandera){
    		return true;
    	}
    	
    	else{
    		return false;
    	}
    	
    	
    }
    
    
    public boolean estaDesmarcado(Position p1, Position[] rivales){
    	boolean bandera=false;
    	for(int i=0; i<rivales.length; i++){
    		if((calcularDistancia(p1,rivales[i]))>=1){
    			bandera=true;
    		}
    		else{ 
    			bandera=false;
    			break;
    		}
    	}
    	
    	if(bandera){
    		return true;
    	}
    	
    	else{
    		return false;
    	}
    	
    	
    }
    
    public boolean filtrarCentro(Position p1, Position[] enemigos){
    	
    	for(int i=0; i<enemigos.length;i++){
    		if((enemigos[i].getX()>p1.getX()-6 && enemigos[i].getX()<p1.getX()+6) && (enemigos[i].getY()>p1.getY()-3 && enemigos[i].getY()<p1.getY()+20)){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
public boolean filtrarDerecha(Position p1, Position[] enemigos){
    	
    	for(int i=0; i<enemigos.length;i++){
    		if((enemigos[i].getX()>p1.getX()-3 && enemigos[i].getX()<p1.getX()+10) && (enemigos[i].getY()>p1.getY()-3 && enemigos[i].getY()<p1.getY()+20)){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
public boolean filtrarIzquierda(Position p1, Position[] enemigos){
	
	for(int i=0; i<enemigos.length;i++){
		if((enemigos[i].getX()>p1.getX()-10 && enemigos[i].getX()<p1.getX()+3) && (enemigos[i].getY()>p1.getY()-3 && enemigos[i].getY()<p1.getY()+20)){
			return false;
		}
	}
	
	return true;
}

public boolean porteriaLibre(Position[] enemigos){
	
	for(int i=0; i<enemigos.length;i++){
		if(enemigos[i].getX()>-5 && enemigos[i].getX()<5 && enemigos[i].getY()>48 && enemigos[i].getY()<53){
			
			return false;
		}
	}
	
	return true;
	
}

public boolean ultimoHombre(Position p1, Position[] enemigos){
	for(int i=1; i<enemigos.length; i++){
		if(p1.getY()<enemigos[i].getY()){
			return false;
		}
	}
	
	return true;
}

public int[] enZonaRoja(Position[] rivales){
	int cont=0;
	for(int i=0; i<rivales.length;i++){
		if(rivales[i].getX()>-21 && rivales[i].getX()<21 && rivales[i].getY()<-31){
			cont++;
		}
	}
	
	if(cont>0){
		int[] peligrosos=new int[cont];
		cont=0;
		for(int i=0; i<rivales.length;i++){
			if(rivales[i].getX()>-21 && rivales[i].getX()<21 && rivales[i].getY()<-31){
				peligrosos[cont]=i;
				cont++;
			}
		}
		
		return peligrosos;
		
	}
	
	else return null;
}
    
    List<Command> commands= new ArrayList<>();
    @Override
    public List<Command> execute(GameSituations sp) {
    	commands.clear();
    	
    	Position[] jugadores = sp.myPlayers();
    	Position[] rivales=sp.rivalPlayers();
    	int[] recuperadores=sp.getRecoveryBall();
    	int [] enemigos=sp.rivalCanKick();
    	int [] tiradores=sp.canKick();
    	int[] maloscercanos= new int[11];
    	int misgoles=sp.myGoals();
    	int susgoles=sp.rivalGoals();
    	int iteraciones=sp.iteration();
    	
    
    	
    	
    	
    	
    	for(int i=0; i<jugadores.length;i++){
    		commands.add(new CommandMoveTo(i, alineacion4[i]));
    	}
    	
    	if(sp.ballPosition().getY()>6){
        	
        		commands.add(new CommandMoveTo(6,alineacion6[6]));
        		commands.add(new CommandMoveTo(8,alineacion6[8]));
        		commands.add(new CommandMoveTo(9,alineacion6[9]));
        		commands.add(new CommandMoveTo(10,alineacion6[10]));
        	
        	}
    	
    	if(enZonaRoja(rivales)!=null){
    	int[] peligrosos=enZonaRoja(rivales);
    	int cont=1;
    	for(int i=peligrosos.length-2; i>=0;i--){
    		commands.add(new CommandMoveTo(cont,rivales[peligrosos[i]]));
    		cont++;
    	}
    	}
    	
    	
    	if(recuperadores.length>0 && tiradores.length==0){
    		double[] trayectoria=sp.getTrajectory(recuperadores[0]);
    		for(int i=1; i<recuperadores.length;i++){
    			commands.add(new CommandMoveTo(recuperadores[i], new Position(trayectoria[0],trayectoria[1])));
    		}
    	}
    	
    	if(tiradores.length>0 && tiradores[0]!=7 && sp.ballPosition().getX()>-3 && sp.ballPosition().getX()<3 && sp.ballPosition().getY()>-3 && sp.ballPosition().getY()<3){
    		for(int i=0; i<tiradores.length;i++){
    			commands.add(new CommandHitBall(tiradores[i],jugadores[0],1,true));
    		}
    	}
    	
    	else if(tiradores.length>0 && tiradores[0]!=7 && estaLibre(jugadores[7],rivales)==false && tiradores[0]!=0 && tiradores[0]!=1 && tiradores[0]!=2 && tiradores[0]!=3){
    		for(int i=0; i<tiradores.length;i++){
    			commands.add(new CommandHitBall(tiradores[i],jugadores[0],0.5,true));
    		}
    	}
    	
    	else{
    		for(int i=0; i<tiradores.length;i++){
    			commands.add(new CommandHitBall(tiradores[i],jugadores[7],1,true));
    		}
    	}
    	
    	if(tiradores.length>0 && tiradores[0]==7 && sp.ballPosition().getY()<10){
    		commands.add(new CommandMoveTo(7, new Position(0,22)));
    		commands.add(new CommandHitBall(7,new Position(0,22),0.38,false));
    	}
    	
    	else if(tiradores.length>0 && tiradores[0]==7 && sp.ballPosition().getY()>=10){
    		commands.add(new CommandMoveTo(7, new Position(8,45)));
    		commands.add(new CommandHitBall(7,new Position(8,45),0.38,false));
    	}
    	
    	
    	

    	if(tiradores.length>0 && tiradores[0]==7 && sp.ballPosition().getY()>43){
    		commands.add(new CommandHitBall(7,new Position(Constants.posteDerArcoSup.getX()-2,Constants.posteDerArcoSup.getY()),1,19));
    	}
    	
    
    	
        return commands;
    }
}