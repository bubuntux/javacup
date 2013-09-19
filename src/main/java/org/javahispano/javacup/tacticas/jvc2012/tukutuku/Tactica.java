package org.javahispano.javacup.tacticas.jvc2012.tukutuku;

/*
 * Tactica tukutuku
 * 
 * Licencia GPL v3
 * 
 *  
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class Tactica implements Tactic {

	Entrenador entrenador = null;
	
	public Tactica()
	{
		alineaciones.add(alineacion1);
		alineaciones.add(alineacion2);
		alineaciones.add(alineacion3);
		alineaciones.add(alineacion4);
		alineaciones.add(alineacion5);
		alineaciones.add(alineacion6);
		alineaciones.add(alineacion7);
		alineaciones.add(alineacion8);
		alineaciones.add(alineacion9);
		alineaciones.add(alineacion10);
		alineaciones.add(alineacion11);
		
		entrenador = new Entrenador(this);
	}
    Position alineacion1[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-2.8531468531468533,21.855203619909503),
            new Position(15.216783216783217,-15.441176470588236),
            new Position(-21.874125874125873,-14.96606334841629),
            new Position(-1.902097902097902,0.7126696832579186),
            new Position(14.027972027972028,-32.54524886877828),
            new Position(-15.216783216783217,-32.782805429864254),
            new Position(-2.6153846153846154,-15.441176470588236),
            new Position(-0.951048951048951,-36.34615384615385),
            new Position(-7.37062937062937,-36.10859728506787),
            new Position(7.132867132867133,-36.10859728506787)
        };

        Position alineacion2[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-3.5664335664335667,35.15837104072398),
            new Position(14.97902097902098,12.59049773755656),
            new Position(-16.643356643356643,12.352941176470589),
            new Position(-3.090909090909091,21.61764705882353),
            new Position(17.11888111888112,-14.96606334841629),
            new Position(-16.167832167832167,-14.25339366515837),
            new Position(-1.188811188811189,-0.47511312217194573),
            new Position(-0.7132867132867133,-28.031674208144796),
            new Position(-12.839160839160838,-28.269230769230766),
            new Position(10.461538461538462,-28.269230769230766)
        };

        Position alineacion3[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-2.6153846153846154,35.63348416289593),
            new Position(13.076923076923078,34.920814479638004),
            new Position(-17.11888111888112,35.39592760180996),
            new Position(-1.6643356643356644,22.805429864253394),
            new Position(17.594405594405593,16.153846153846153),
            new Position(-21.3986013986014,14.96606334841629),
            new Position(-0.7132867132867133,5.701357466063349),
            new Position(0.23776223776223776,-16.391402714932127),
            new Position(-12.125874125874127,-15.203619909502263),
            new Position(11.888111888111888,-15.91628959276018)
        };

        Position alineacion4[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-4.27972027972028,40.62217194570136),
            new Position(9.034965034965035,36.82126696832579),
            new Position(-11.65034965034965,31.119909502262445),
            new Position(-0.951048951048951,13.303167420814479),
            new Position(23.776223776223777,25.418552036199095),
            new Position(-24.251748251748253,24.705882352941178),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion5[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-1.4265734265734267,37.05882352941177),
            new Position(9.272727272727272,38.24660633484163),
            new Position(-9.034965034965035,41.57239819004525),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(18.545454545454543,28.031674208144796),
            new Position(-17.594405594405593,28.744343891402718),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion6[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-0.7132867132867133,30.16968325791855),
            new Position(8.797202797202797,35.39592760180996),
            new Position(-10.223776223776223,36.58371040723982),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(21.874125874125873,21.855203619909503),
            new Position(-24.013986013986013,22.805429864253394),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion7[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-4.5174825174825175,44.89819004524887),
            new Position(10.223776223776223,44.18552036199095),
            new Position(-12.839160839160838,38.72171945701358),
            new Position(-1.188811188811189,25.893665158371043),
            new Position(21.874125874125873,34.68325791855204),
            new Position(-23.538461538461537,34.44570135746606),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion8[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-1.188811188811189,48.223981900452486),
            new Position(3.090909090909091,30.88235294117647),
            new Position(-4.27972027972028,41.334841628959275),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(12.839160839160838,21.142533936651585),
            new Position(-17.11888111888112,22.805429864253394),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion9[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(4.9930069930069925,47.2737556561086),
            new Position(1.188811188811189,38.009049773755656),
            new Position(-6.895104895104895,45.373303167420815),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(17.356643356643357,33.970588235294116),
            new Position(-7.132867132867133,31.357466063348415),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion10[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-1.188811188811189,33.02036199095023),
            new Position(8.083916083916083,45.373303167420815),
            new Position(-9.748251748251748,46.56108597285068),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(19.97202797202797,33.257918552036195),
            new Position(-22.11188811188811,33.970588235294116),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion11[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-0.7132867132867133,36.34615384615385),
            new Position(8.083916083916083,45.373303167420815),
            new Position(-9.034965034965035,45.373303167420815),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(20.923076923076923,39.43438914027149),
            new Position(-12.363636363636363,34.920814479638004),
            new Position(-0.951048951048951,-11.165158371040723),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion12[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-1.4265734265734267,-0.7126696832579186),
            new Position(3.090909090909091,-5.463800904977376),
            new Position(25.44055944055944,0.0),
            new Position(-0.4755244755244755,-11.64027149321267),
            new Position(19.25874125874126,-21.61764705882353),
            new Position(-17.389312977099234,-19.58955223880597),
            new Position(1.188811188811189,-22.330316742081447),
            new Position(0.4755244755244755,-34.920814479638004),
            new Position(-10.223776223776223,-34.44570135746606),
            new Position(9.986013986013985,-35.39592760180996)
        };

        Position alineacion13[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-0.7132867132867133,-11.165158371040723),
            new Position(-9.272727272727272,-11.402714932126697),
            new Position(8.321678321678322,-11.165158371040723),
            new Position(-5.706293706293707,-25.893665158371043),
            new Position(19.020979020979023,-28.744343891402718),
            new Position(-17.832167832167833,-28.744343891402718),
            new Position(2.6153846153846154,-25.418552036199095),
            new Position(-0.951048951048951,-36.10859728506787),
            new Position(-10.461538461538462,-36.82126696832579),
            new Position(6.6573426573426575,-36.34615384615385)
        };

        Position alineacion14[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-0.7132867132867133,41.80995475113122),
            new Position(12.839160839160838,28.744343891402718),
            new Position(-21.3986013986014,26.606334841628957),
            new Position(-1.4265734265734267,18.29185520361991),
            new Position(17.11888111888112,41.0972850678733),
            new Position(-15.454545454545453,39.19683257918552),
            new Position(-1.6643356643356644,-0.23755656108597287),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };

        Position alineacion15[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-3.804195804195804,39.43438914027149),
            new Position(13.79020979020979,35.15837104072398),
            new Position(-16.88111888111888,30.16968325791855),
            new Position(-1.188811188811189,21.142533936651585),
            new Position(26.62937062937063,20.429864253393667),
            new Position(-25.44055944055944,21.380090497737555),
            new Position(0.23776223776223776,-0.23755656108597287),
            new Position(0.0,-33.73303167420815),
            new Position(-11.412587412587413,-34.20814479638009),
            new Position(11.412587412587413,-34.920814479638004)
        };







        public ArrayList<Position[]>alineaciones = new ArrayList<Position[]>();
        



    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Tuku-Tuku";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Victor Revelationi";
        }

        public Color getShirtColor() {
            return new Color(255, 0, 0);
        }

        public Color getShortsColor() {
            return new Color(255, 0, 0);
        }

        public Color getShirtLineColor() {
            return new Color(0, 0, 0);
        }

        public Color getSocksColor() {
            return new Color(255, 0, 1);
        }

        public Color getGoalKeeper() {
            return new Color(255, 153, 51        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        public Color getShirtColor2() {
            return new Color(41, 51, 38);
        }

        public Color getShortsColor2() {
            return new Color(192, 180, 103);
        }

        public Color getShirtLineColor2() {
            return new Color(220, 123, 194);
        }

        public Color getSocksColor2() {
            return new Color(207, 157, 30);
        }

        public Color getGoalKeeper2() {
            return new Color(52, 148, 88        );
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
            		 new JugadorImpl("Bambino", 1, new Color(255,200,150), new Color(255,204,0),1.0d,1.0d,0.0d, true),
                     new JugadorImpl("Sparrowhawk", 2, new Color(255,200,150), new Color(153,102,0),1.0d,1.0d,1.0d, false),
                     new JugadorImpl("Rom�n", 3, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                     new JugadorImpl("Sergio", 4, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                     new JugadorImpl("Lolo", 5, new Color(255,200,150), new Color(50,0,0),0.54d,1.0d,1.0d, false),
                     new JugadorImpl("Harry", 6, new Color(255,200,150), new Color(50,0,0),0.51d,1.0d,1.0d, false),
                     new JugadorImpl("Fran", 7, new Color(255,200,150), new Color(50,0,0),0.78d,1.0d,1.0d, false),
                     new JugadorImpl("Chico", 8, new Color(255,200,150), new Color(50,0,0),0.74d,0.8d,0.5d, false),
                     new JugadorImpl("Rafa", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.81d,0.5d, false),
                     new JugadorImpl("Oscar", 10, new Color(255,200,150), new Color(50,0,0),1.0d,0.8d,0.5d, false),
                     new JugadorImpl("Jugador", 11, new Color(255,200,150), new Color(255,200,150),1.0d,0.52d,0.5d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion12;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion13;
    }

    
    
    public List<Command> execute(GameSituations sp) 
    {
    	
    	return entrenador.procesarIteraccion(sp);
    	
    }
}