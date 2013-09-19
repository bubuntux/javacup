package org.javahispano.javacup.tacticas.jvc2012.hortalezablamers;

import java.awt.Color;

import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class HortalezaBlamersDetalle implements TacticDetail {

	public String getTacticName() {
		return "HortalezaBlamers";
	}

	public String getCountry() {
		return "España";
	}

	public String getCoach() {
		return "Al Jabber";
	}

	public Color getShirtColor() {
		return new Color(255, 102, 0);
	}

	public Color getShortsColor() {
		return new Color(0, 0, 153);
	}

	public Color getShirtLineColor() {
		return new Color(255, 255, 255);
	}

	public Color getSocksColor() {
		return new Color(255, 102, 0);
	}

	public Color getGoalKeeper() {
		return new Color(102, 102, 102);
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.LINEAS_VERTICALES;
	}

	public Color getShirtColor2() {
		return new Color(192, 138, 142);
	}

	public Color getShortsColor2() {
		return new Color(32, 71, 66);
	}

	public Color getShirtLineColor2() {
		return new Color(85, 169, 111);
	}

	public Color getSocksColor2() {
		return new Color(215, 153, 189);
	}

	public Color getGoalKeeper2() {
		return new Color(45, 101, 149);
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
        		new JugadorImpl("Joven", 1, new Color(255,200,150), new Color(50,0,0),         1d,     1d,  1d, true),
        		new JugadorImpl("Javi Wan", 2, new Color(255,200,150), new Color(50,0,0),      1d,0.5833d,0.5d, false),
        		new JugadorImpl("Vitoxo", 3, new Color(255,200,150), new Color(50,0,0),        1d,0.5833d,0.5d, false),
        		new JugadorImpl("Van Persico", 4, new Color(255,200,150), new Color(50,0,0),   1d,0.5833d,0.5d, false),
        		new JugadorImpl("Blanco", 5, new Color(255,200,150), new Color(50,0,0),        1d,0.5833d,0.5d, false),
        		new JugadorImpl("Lerma", 6, new Color(255,200,150), new Color(50,0,0),         1d,0.5833d,0.5d, false),
        		new JugadorImpl("Schweinsteiger", 7, new Color(255,200,150), new Color(50,0,0),1d,0.5833d,0.5d, false),
        		new JugadorImpl("Santi", 8, new Color(255,200,150), new Color(50,0,0),         1d,     1d,  1d, false),
        		new JugadorImpl("�ajete", 9, new Color(255,200,150), new Color(50,0,0),        1d,     1d,  1d, false),
        		new JugadorImpl("Rober",10, new Color(255,200,150), new Color(50,0,0),         1d,     1d,  1d, false),
        		new JugadorImpl("Acio",11, new Color(255,200,150), new Color(50,0,0),          1d,     1d,  1d, false)
        };
    }
}

