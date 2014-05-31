package org.dsaw.javacup.tactics.jvc2013.jmv;


import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KapriFC implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(27.251908396946565,-27.94776119402985),
        new Position(-29.84732824427481,-26.902985074626866),
        new Position(27.34265734265734,12.115384615384617),
        new Position(-10.641221374045802,-7.052238805970149),
        new Position(5.944055944055944,-4.038461538461538),
        new Position(-26.391608391608393,31.357466063348415),
        new Position(-0.2595419847328244,19.067164179104477),
        new Position(-0.2595419847328244,35.78358208955224)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-19.46564885496183,-31.6044776119403),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(19.984732824427482,-31.6044776119403),
        new Position(7.526717557251908,-11.753731343283583),
        new Position(-8.564885496183205,-11.753731343283583),
        new Position(-24.65648854961832,-2.3507462686567164),
        new Position(23.099236641221374,-2.873134328358209),
        new Position(-14.274809160305344,30.559701492537314),
        new Position(-0.7786259541984732,8.097014925373134),
        new Position(12.717557251908397,29.51492537313433)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(26.732824427480914,-20.111940298507463),
        new Position(-29.32824427480916,-21.67910447761194),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(-18.946564885496183,-0.26119402985074625),
        new Position(18.946564885496183,-0.26119402985074625),
        new Position(-19.46564885496183,35.78358208955224),
        new Position(-0.2595419847328244,19.067164179104477),
        new Position(18.946564885496183,35.26119402985075)
    };

    Position alineacion4[]=new Position[]{
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

    Position alineacion5[]=new Position[]{
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

    Position alineacion6[]=new Position[]{
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

    Position cornerInferiorIzq[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-19.496503496503497,-51.549773755656105),
            new Position(-10.223776223776223,-42.76018099547511),
            new Position(-3.5664335664335667,-38.72171945701358),
            new Position(-19.25874125874126,-46.56108597285068),
            new Position(5.468531468531468,-41.57239819004525),
            new Position(-16.643356643356643,-34.44570135746606),
            new Position(-12.601398601398602,-13.065610859728507),
            new Position(1.4265734265734267,-30.407239819004527),
            new Position(0.0,-9.97737556561086),
            new Position(12.363636363636363,-38.95927601809955)
        };

        Position cornerInferiorDer[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(19.97202797202797,-51.074660633484164),
            new Position(-10.223776223776223,-42.76018099547511),
            new Position(-3.5664335664335667,-38.72171945701358),
            new Position(19.734265734265733,-45.61085972850679),
            new Position(5.468531468531468,-41.57239819004525),
            new Position(-16.643356643356643,-34.44570135746606),
            new Position(-12.601398601398602,-13.065610859728507),
            new Position(1.4265734265734267,-30.407239819004527),
            new Position(0.0,-9.97737556561086),
            new Position(12.363636363636363,-38.95927601809955)
        };

        Position cornerSuperiorDer[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(9.272727272727272,-30.407239819004527),
            new Position(-11.412587412587413,-29.457013574660635),
            new Position(-2.13986013986014,-29.694570135746606),
            new Position(-8.321678321678322,-4.98868778280543),
            new Position(-1.6643356643356644,7.126696832579185),
            new Position(1.188811188811189,27.08144796380091),
            new Position(-11.65034965034965,44.42307692307692),
            new Position(0.23776223776223776,41.334841628959275),
            new Position(32.33566433566433,50.59954751131222),
            new Position(14.74125874125874,46.08597285067873)
        };

        Position cornerSuperiorIzq[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(9.272727272727272,-30.407239819004527),
            new Position(-11.412587412587413,-29.457013574660635),
            new Position(-2.13986013986014,-29.694570135746606),
            new Position(-8.321678321678322,-4.98868778280543),
            new Position(-1.6643356643356644,7.126696832579185),
            new Position(1.188811188811189,27.08144796380091),
            new Position(-11.65034965034965,44.42307692307692),
            new Position(0.23776223776223776,41.334841628959275),
            new Position(-31.622377622377623,49.64932126696832),
            new Position(14.74125874125874,46.08597285067873)
        };


    class TacticaDetalleImpl implements TacticDetail {

        public String getTacticName() {
            return "Kapri FC";
        }

        public CountryCode getCountry() {
            return CountryCode.ES;
        }

        public String getCoach() {
            return "Luciano Hansenberger";
        }

        public Color getShirtColor() {
            return new Color(255, 204, 0);
        }

        public Color getShortsColor() {
            return new Color(27, 97, 75);
        }

        public Color getShirtLineColor() {
            return new Color(255, 255, 0);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 0);
        }

        public Color getGoalKeeper() {
            return new Color(37, 111, 220        );
        }

        public UniformStyle getStyle() {
            return UniformStyle.SIN_ESTILO;
        }

        public Color getShirtColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor2() {
            return new Color(121, 161, 110);
        }

        public Color getShirtLineColor2() {
            return new Color(152, 87, 207);
        }

        public Color getSocksColor2() {
            return new Color(38, 83, 139);
        }

        public Color getGoalKeeper2() {
            return new Color(17, 40, 189        );
        }

        public UniformStyle getStyle2() {
            return UniformStyle.FRANJA_VERTICAL;
        }

        class JugadorImpl implements PlayerDetail {

            String nombre;
            int numero;
            Color piel, pelo;
            double velocidad, remate, presicion;
            boolean portero;
            Position posicion;

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
                new JugadorImpl("Casillas", 1, new Color(255,200,150), new Color(50,0,0),1.0d,0.98d,1.0d, true),
                new JugadorImpl("Puyol", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.78d,0.75d, false),
                new JugadorImpl("Sergio Ramos", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.79d,0.79d, false),
                new JugadorImpl("Pique", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.77d,0.76d, false),
                new JugadorImpl("Busquets", 5, new Color(255,200,150), new Color(50,0,0),0.83d,0.71d,0.72d, false),
                new JugadorImpl("Iniesta", 6, new Color(255,200,150), new Color(50,0,0),0.79d,0.78d,0.67d, false),
                new JugadorImpl("Di Maria", 7, new Color(255,200,150), new Color(50,0,0),0.76d,0.64d,0.57d, false),
                new JugadorImpl("Xavi", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Cristiano Ronaldo", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.65d, false),
                new JugadorImpl("Mesi", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Villa", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticaDetalleImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion5;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
    }

    private static final int porteroIndice = 0;
    private int iteracionesParaSacar = 0;
    
    public List<Command> execute(GameSituations sp) {
    	
    	List<Command> result = new ArrayList<Command>();
    	
    	cambiarAlineacion(sp, result);
    	
    	if (sp.canKick().length > 0) {

			for (int jugador : sp.canKick()) {

				if(jugador == porteroIndice){
					CommandHitBall
                                            golpear = new CommandHitBall(jugador, 90, 1, 40);
					result.add(golpear);
				}else if(sp.isStarts()){
//					//System.out.println("SACO. iteracionesParaSacar: " + iteracionesParaSacar);
					
					if(iteracionesParaSacar == Constants.ITERACIONES_SAQUE - 10){
						int count=0;
						Random r = new Random();
						int j = -1;
						Position[] jugadores = sp.myPlayers();
		                while ((j = r.nextInt(jugadores.length)) == jugador && jugadores[jugador].getY()>jugadores[j].getY() && count<20){
		                    count++;
		                };
		                result.add(new CommandHitBall(jugador, jugadores[j], 3, r.nextInt(45)));
		                
		                iteracionesParaSacar = 0;
					}else if(iteracionesParaSacar > Constants.ITERACIONES_SAQUE){
						iteracionesParaSacar = 0;
					}else{
						
						// Desplazamos a un compañero para que le pueda sacar el balón
						int[] jugadoresParaCombinar = sp.myPlayers()[jugador].nearestIndexes(sp.myPlayers(), 0 , jugador);
						
						if(jugadoresParaCombinar.length > 0){
							
							for(int jugadorParaCombinar : jugadoresParaCombinar){
								
								if(jugadorParaCombinar != 0 && jugadorParaCombinar != jugador){
									
									double x = 0;
									if(sp.ballPosition().getX() < 0){
										x = sp.ballPosition().getX() + 10;
									}else{
										x = sp.ballPosition().getX() - 10;
									}
									
									Position newPosition = new Position(x, sp.ballPosition().getY());
									
									CommandMoveTo
                                                                            muevete = new CommandMoveTo(jugadorParaCombinar, newPosition);
									result.add(muevete);
									
									break;
								}
								
							}
							
						}
						
					}
					
				}else if (jugador==8 || jugador==9 || jugador==10 || jugador==11) {
					Random r = new Random();
	                if (r.nextBoolean()) {
	                    result.add(new CommandHitBall(jugador, Constants.centroArcoSup, 3, 12+r.nextInt(6)));
	                } else {
	                    if (r.nextBoolean()) {
	                    	result.add(new CommandHitBall(jugador, Constants.posteDerArcoSup, 3, 12+r.nextInt(6)));
	                    } else {
	                    	result.add(new CommandHitBall(jugador, Constants.posteIzqArcoSup, 3, 12+r.nextInt(6)));
	                    }
	                }
	                
	            }else if(jugador == 5 || jugador == 6) {
	                result.add(new CommandHitBall(jugador));
	                
	            }else{
	            	int count=0;
					Random r = new Random();
					int j = -1;
					Position[] jugadores = sp.myPlayers();
	                while ((j = r.nextInt(jugadores.length)) == jugador && jugadores[jugador].getY()>jugadores[j].getY() && count<20){
	                    count++;
	                };
	                result.add(new CommandHitBall(jugador, jugadores[j], 3, r.nextInt(45)));
	            }
			}
    	}
    	
    	
    	// Portero
		int[] recuperadores = sp.getRecoveryBall();
		if (recuperadores.length > 1) {
			for (int i = 1; i < 2; i++) {
				
				double[] posRecuperacion = sp.getTrajectory(iteracionMejorParaRecuperarBalonSegunAltura(sp));
				result.add(new CommandMoveTo(recuperadores[i], new Position(
						posRecuperacion[0], posRecuperacion[1])));
				
			}
		}
		
//		if(sp.myGoals() == sp.rivalGoals() && sp.ballPosition().distancia(sp.myPlayers()[0]) < 50){
//			
//			if (sp.canKick().length > 0 && sp.rivalCanKick().length == 0) {
//				
//				for(int jugador : sp.canKick()){
//					
//					if(jugador != 0){
//						// El portero no actúa aquí
//						CommandHitBall golpear = new CommandHitBall(jugador);
//						result.add(golpear);
//						
//						ComandoIrA irA = new ComandoIrA(jugador, new Posicion(40, sp.myPlayers()[0].getY()));
//						result.add(irA);
//					}
//				}
//				
//			}
//			
//		}
		
        return result;
    }
    
    
    private boolean existeJugadorRivalCercaDeBalon(GameSituations sp){
    	
    	boolean result = false;

    	for(Position rival : sp.rivalPlayers()){
    		
    		if(rival.distance(sp.ballPosition()) < 10){
    			result = true;
    			break;
    		}
    	}
    	
    	return result;
    }
    
    
    private int iteracionMejorParaRecuperarBalonSegunAltura(GameSituations sp){
    	
    	int result = -1;
    	
    	int[] recuperadores = sp.getRecoveryBall();
    	if (recuperadores.length > 1) {
    		
    		int iteracionRecuperacion = recuperadores[0];
    		
    		int iteracionesMaximas = 30;
    		int cont = 0;
    		
    		while(cont < iteracionesMaximas || result != -1){
    			
    			double[] posRecuperacion = sp.getTrajectory(iteracionRecuperacion);
    			if(posRecuperacion.length == 3 && posRecuperacion[2] <= Constants.ALTURA_CONTROL_BALON){
    				// podemos controlar el balón por alto
    				result = iteracionRecuperacion;
    				break;
    			}else{
    				cont++;
    				iteracionRecuperacion++;
    			}
    			
    		}
    		
    		if(result == -1){
    			result = iteracionRecuperacion;
    		}
    	}
    	
    	return result;
    }
    
    /**
	 * Usa una alineacion para todos los jugadores.
	 * @param comandos
	 * @param alineacion
	 * @param sp
	 */
	private void usarAlineacion(List<Command> comandos, Position[] alineacion, GameSituations sp){
		
		Position[] jugadores = sp.myPlayers();
		for (int i = 0; i < jugadores.length; i++) {
			comandos.add(new CommandMoveTo(i, alineacion[i]));
		}
		
	}
    
    private void cambiarAlineacion(GameSituations sp, List<Command> result){
    	if(sp.isRivalStarts()){
//			//System.out.println("SACA RIVAL. iteracionesParaSacar: " + iteracionesParaSacar);
			int cont = 0;
			
			
			int[] recuperadores = sp.getRecoveryBall();
			if (recuperadores.length > 1) {
				for (int i = 1; i < 2; i++) {
					double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
						result.add(new CommandMoveTo(recuperadores[i], new Position(
								posRecuperacion[0], posRecuperacion[1])));
				}
			}
			
			
			if(sp.ballPosition().equals(Constants.cornerInfDer)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerInferiorDer, sp);
				
			}else if(sp.ballPosition().equals(Constants.cornerInfIzq)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerInferiorIzq, sp);
				
			}else if(sp.ballPosition().equals(Constants.cornerSupIzq)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerSuperiorIzq, sp);
				
			}else if(sp.ballPosition().equals(Constants.cornerSupDer)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerSuperiorDer, sp);
				
			}
			
		}else if(sp.isStarts()){
			//System.out.println("SACO. iteracionesParaSacar: " + iteracionesParaSacar);
			int cont = 0;
			
			iteracionesParaSacar++;
			
			int[] recuperadores = sp.getRecoveryBall();
			if (recuperadores.length > 1) {
				for (int i = 1; i < 2; i++) {
					double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
						result.add(new CommandMoveTo(recuperadores[i], new Position(
								posRecuperacion[0], posRecuperacion[1])));
				}
			}
			
			if(sp.ballPosition().equals(Constants.cornerInfDer)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerInferiorDer, sp);
				
			}else if(sp.ballPosition().equals(Constants.cornerInfIzq)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerInferiorIzq, sp);
				
			}else if(sp.ballPosition().equals(Constants.cornerSupIzq)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerSuperiorIzq, sp);
				
			}else if(sp.ballPosition().equals(Constants.cornerSupDer)){
//				//System.out.println("SAQUE " + cont++);
				usarAlineacion(result, cornerSuperiorDer, sp);
				
			}
		}else{
			usarAlineacion(result, alineacion1, sp);
		}
    }
}