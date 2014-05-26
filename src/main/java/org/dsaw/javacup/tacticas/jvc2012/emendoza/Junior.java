package org.dsaw.javacup.tacticas.jvc2012.emendoza;

import java.awt.Color;
import org.dsaw.javacup.model.*;
import org.dsaw.javacup.model.util.*;
import org.dsaw.javacup.render.EstiloUniforme;
import org.dsaw.javacup.model.command.*;
import org.dsaw.javacup.model.engine.GameSituations;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Junior implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-19.46564885496183,-31.6044776119403),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(19.984732824427482,-31.6044776119403),
        new Position(7.526717557251908,-11.753731343283583),
        new Position(-8.564885496183205,-11.753731343283583),
        new Position(-24.65648854961832,-2.3507462686567164),
        new Position(23.099236641221374,-2.873134328358209),
        new Position(-0.951048951048951,14.490950226244346),
        new Position(-19.496503496503497,30.407239819004527),
        new Position(12.363636363636363,31.357466063348415)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(27.251908396946565,-27.94776119402985),
        new Position(-29.84732824427481,-26.902985074626866),
        new Position(8.564885496183205,-7.574626865671642),
        new Position(-31.384615384615387,-1.4253393665158371),
        new Position(2.377622377622378,-19.479638009049776),
        new Position(-9.986013986013985,-19.95475113122172),
        new Position(-6.895104895104895,-7.364253393665159),
        new Position(30.67132867132867,-0.47511312217194573)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.174825174825173,-34.68325791855204),
        new Position(9.748251748251748,-34.20814479638009),
        new Position(25.678321678321677,-33.970588235294116),
        new Position(-27.34265734265734,-33.257918552036195),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(-18.946564885496183,-0.26119402985074625),
        new Position(17.11888111888112,-0.47511312217194573),
        new Position(-2.377622377622378,-17.104072398190045),
        new Position(24.965034965034967,-17.34162895927602),
        new Position(-25.44055944055944,-17.34162895927602)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-8.797202797202797,-31.357466063348415),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(27.34265734265734,-19.004524886877828),
        new Position(-28.055944055944057,-16.391402714932127),
        new Position(7.608391608391608,0.9502262443438915),
        new Position(-6.895104895104895,0.0),
        new Position(-17.832167832167833,40.859728506787334),
        new Position(-10.461538461538462,32.30769230769231),
        new Position(3.3286713286713288,32.30769230769231),
        new Position(18.307692307692307,39.19683257918552)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(27.81818181818182,-23.993212669683256),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(12.363636363636363,-21.142533936651585),
        new Position(-14.027972027972028,-20.429864253393667),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(5.969465648854961,-5.485074626865671),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(6.6573426573426575,-36.34615384615385),
        new Position(19.97202797202797,-36.58371040723982),
        new Position(-22.58741258741259,-34.44570135746606),
        new Position(22.349650349650346,-19.479638009049776),
        new Position(-29.95804195804196,-18.766968325791854),
        new Position(-7.608391608391608,-6.889140271493213),
        new Position(3.3286713286713288,-19.717194570135746),
        new Position(-11.65034965034965,-20.667420814479637),
        new Position(5.944055944055944,-8.314479638009049)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Junior";
        }

        public String getCountry() {
            return "Colombia";
        }

        public String getCoach() {
            return "Eliecer Mendoza";
        }

        public Color getShirtColor() {
            return new Color(255, 0, 51);
        }

        public Color getShortsColor() {
            return new Color(0, 51, 255);
        }

        public Color getShirtLineColor() {
            return new Color(255, 255, 255);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper() {
            return new Color(242, 7, 42        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        public Color getShirtColor2() {
            return new Color(162, 0, 255);
        }

        public Color getShortsColor2() {
            return new Color(150, 217, 101);
        }

        public Color getShirtLineColor2() {
            return new Color(188, 76, 203);
        }

        public Color getSocksColor2() {
            return new Color(152, 204, 80);
        }

        public Color getGoalKeeper2() {
            return new Color(53, 150, 144        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
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
                new JugadorImpl("Adrián Berbia", 1, new Color(255,200,150), new Color(50,0,0),0.6d,0.5d,0.5d, true),
                new JugadorImpl("Camilo Andrés Ceballos Zapata", 2, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("Roller Cambindo Ibarra", 3, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("César Augusto Fawcett Lebolo", 4, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("César Augusto Fawcett Lebolo", 5, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("Jáider José Romero Romero", 6, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("John Alexánder Jaramillo Gómez", 7, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("Giovanni Hernández Soto", 8, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("Vladimir Javier Hernández", 9, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("Carlos Arturo Bacca Ahumada", 10, new Color(255,200,150), new Color(50,0,0),0.5d,0.5d,0.5d, false),
                new JugadorImpl("Teófilo Antonio Gutiérrez", 11, new Color(255,200,150), new Color(50,0,0),0.8d,0.8d,0.4d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion3;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
    }
  
  //Lista de comandos
    LinkedList<Command> comandos = new LinkedList<>();
    
    public List<Command> execute(GameSituations sp) {
		//Limpia la lista de comandos
		comandos.clear();
		//Obtiene las posiciones de tus jugadores
		Position[] jugadores = sp.myPlayers();
		for (int i = 0; i < jugadores.length; i++) {
		//Ordena a cada jugador que se ubique segun la alineacion1
			comandos.add(new CommandMoveTo(i, alineacion1[i]));
		}
		//Si no saca el rival
		if (!sp.isRivalStarts()) {
		//Obtiene los datos de recuperacion del balon
		int[] recuperadores = sp.getRecoveryBall();
		//Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1) {
				//Obtiene las coordenadas del balon en el instante donde
				//se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				//Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++) {
					//Ordena a los jugadores recuperadores que se ubique
					//en la posicion de recuperacion
					comandos.add(new CommandMoveTo(recuperadores[i],
					new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			}
		}
		//Instancia un generador aleatorio
		Random r = new Random();
		//Recorre la lista de mis jugadores que pueden rematar
		for (int i : sp.canKick()) {
			//Si el jugador es de indice 8 o 10
			if (i == 8 || i == 10) {
				//condicion aleatoria
				if (r.nextBoolean()) {
					//Ordena que debe rematar al centro del arco
					comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));
				} else if (r.nextBoolean()) {
					//Ordena que debe rematar al poste derecho
					comandos.add(new CommandHitBall(i, Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
				} else {
					//Ordena que debe rematar al poste izquierdo
					comandos.add(new CommandHitBall(i, Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
				}
			} else {
				//inicia contador en cero
				int count = 0;
				int jugadorDestino;
				//Repetir mientras el jugador destino sea igual al jugador que remata
				while (((jugadorDestino = r.nextInt(11)) == i
						//o mientras la coordenada y del jugador que remata
						//es mayor que la coordenada y del que recibe
						|| jugadores[i].getY() > jugadores[jugadorDestino].getY())
						//Y mientras el contador es menor a 20
						&& count < 20) {
					//incrementa el contador
						count++;
				}
				//Si el receptor del balon es el que remata
				if (i == jugadorDestino) {
					while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
					}
				}
				//Ordena que el jugador que puede rematar que de un pase
				//al jugador destino
				comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, r.nextInt(45)));
			}
		}
		//Retorna la lista de comandos
		return comandos;
    }
}