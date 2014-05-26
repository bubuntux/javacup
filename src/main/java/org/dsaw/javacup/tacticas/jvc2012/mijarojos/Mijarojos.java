package org.dsaw.javacup.tacticas.jvc2012.mijarojos;

import java.awt.Color;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.EstiloUniforme;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;

import java.util.LinkedList;
import java.util.List;

public class Mijarojos implements Tactic {

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "mijarojos";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Gus";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 0);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        public Color getShirtLineColor() {
            return new Color(0, 0, 0);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 0);
        }

        public Color getGoalKeeper() {
            return new Color(132, 24, 188        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.SIN_ESTILO;
        }

        public Color getShirtColor2() {
            return new Color(143, 253, 190);
        }

        public Color getShortsColor2() {
            return new Color(110, 154, 138);
        }

        public Color getShirtLineColor2() {
            return new Color(170, 16, 34);
        }

        public Color getSocksColor2() {
            return new Color(242, 33, 152);
        }

        public Color getGoalKeeper2() {
            return new Color(212, 12, 149        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.LINEAS_HORIZONTALES;
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
                    new JugadorImpl("Jugador", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, true),
                    new JugadorImpl("Jugador", 2, new Color(255,200,150), new Color(50,0,0),0.76d,1.0d,0.63d, false),
                    new JugadorImpl("Jugador", 3, new Color(255,200,150), new Color(50,0,0),0.76d,1.0d,0.65d, false),
                    new JugadorImpl("Jugador", 4, new Color(255,200,150), new Color(50,0,0),0.76d,1.0d,0.65d, false),
                    new JugadorImpl("Jugador", 5, new Color(255,200,150), new Color(50,0,0),0.76d,1.0d,0.65d, false),
                    new JugadorImpl("Jugador", 6, new Color(255,200,150), new Color(50,0,0),0.92d,0.76d,0.74d, false),
                    new JugadorImpl("Jugador", 7, new Color(255,200,150), new Color(50,0,0),0.92d,0.76d,0.74d, false),
                    new JugadorImpl("Jugador", 8, new Color(255,200,150), new Color(50,0,0),0.92d,0.76d,0.74d, false),
                    new JugadorImpl("Jugador", 9, new Color(255,200,150), new Color(50,0,0),0.97d,0.75d,0.95d, false),
                    new JugadorImpl("Jugador", 10, new Color(255,200,150), new Color(50,0,0),0.98d,0.84d,0.95d, false),
                    new JugadorImpl("Jugador", 11, new Color(255,200,150), new Color(50,0,0),0.97d,0.75d,0.95d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    	return Alineaciones.alineacion2;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    	return Alineaciones.alineacion3;
    }
    
    private String getDescripcionComandosHit(List<CommandHitBall> lstCommands){
    	String cadDescripcionString = "";
    	for(CommandHitBall c : lstCommands){
    		cadDescripcionString += c.getPlayerIndex() + ": " + c.getDestiny().toString() + "\r\n";
    	}
    	return cadDescripcionString;
    }

    private String getDescripcionComandosMove(List<CommandMoveTo> lstCommands){
    	String cadDescripcionString = "";
    	for(CommandMoveTo c : lstCommands){
    		cadDescripcionString += c.getPlayerIndex() + ": " + c.getMoveTo().toString() + "\r\n";
    	}
    	return cadDescripcionString;
    }

	LinkedList<Command> comandos = new LinkedList<Command>();

	@Override
	public List<Command> execute(GameSituations sp) {
		// Limpia la lista de comandos
		comandos.clear();
		
		List<CommandMoveTo> lstTmpMoveTo = null;
		List<CommandHitBall> lstTmpHitBall = null;
		lstTmpMoveTo = Alineaciones.execute(sp);
		comandos.addAll(lstTmpMoveTo);
		/*System.out.println("ALINEACION\r\n" + this.
				getDescripcionComandosMove(lstTmpMoveTo));*/
		lstTmpMoveTo = new Anticipacion(sp).execute();
		comandos.addAll(lstTmpMoveTo);
		//System.out.println("ANTICIPACION\r\n" + this.
		//		getDescripcionComandosMove(lstTmpMoveTo));
		lstTmpHitBall = new Chuts(sp).execute();
		comandos.addAll(lstTmpHitBall);
		//System.out.println("CHUTS\r\n" + this.
		//		getDescripcionComandosHit(lstTmpHitBall));
		
		// Retorna la lista de comandos
		return comandos;
	}
    
}
