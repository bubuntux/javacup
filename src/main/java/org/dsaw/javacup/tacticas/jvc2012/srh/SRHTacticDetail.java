package org.dsaw.javacup.tacticas.jvc2012.srh;

import java.awt.Color;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.EstiloUniforme;

public class SRHTacticDetail implements TacticDetail {

	public String getTacticName() {
		return "Rhapsody";
	}

	public String getCountry() {
		return "España";
	}

	public String getCoach() {
		return "Insa";
	}

	public Color getShirtColor() {
		return new Color(255, 0, 0);
	}

	public Color getShortsColor() {
		return new Color(255, 255, 255);
	}

	public Color getShirtLineColor() {
		return new Color(255, 255, 255);
	}

	public Color getSocksColor() {
		return new Color(255, 0, 0);
	}

	public Color getGoalKeeper() {
		return new Color(255, 255, 0        );
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.SIN_ESTILO;
	}

	public Color getShirtColor2() {
		return new Color(0, 0, 204);
	}

	public Color getShortsColor2() {
		return new Color(255, 255, 255);
	}

	public Color getShirtLineColor2() {
		return new Color(255, 255, 255);
	}

	public Color getSocksColor2() {
		return new Color(0, 0, 255);
	}

	public Color getGoalKeeper2() {
		return new Color(255, 255, 0        );
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
            new JugadorImpl("Vanesa", 1, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, true),
            new JugadorImpl("Ximo", 2, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Javi", 3, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Victor", 4, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Jose", 5, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Juan", 6, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Cerdá", 7, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Corbin", 8, new Color(255,200,150), new Color(50,0,0),1d,0.625d,0.5d, false),
            new JugadorImpl("Ripoll", 9, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, false),
            new JugadorImpl("Colomer", 10, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, false),
            new JugadorImpl("Bas", 11, new Color(255,200,150), new Color(50,0,0),1d,1d,1d, false)
        };
    }
	/*
	public PlayerDetail[] getPlayers() {
		return new PlayerDetail[]{
                new JugadorImpl("Vanesa", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Ximo", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.73d,0.6d, false),
                new JugadorImpl("Javi", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.6d, false),
                new JugadorImpl("Victor", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.6d, false),
                new JugadorImpl("Jose", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.6d, false),
                new JugadorImpl("Juan", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.65d, false),
                new JugadorImpl("Cerd�", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.65d, false),
                new JugadorImpl("Corbin", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,0.65d, false),
                new JugadorImpl("Ripoll", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,1.0d, false),
                new JugadorImpl("Colomer", 10, new Color(255,200,150), new Color(50,0,0),1.0d,0.67d,1.0d, false),
                new JugadorImpl("Bas", 11, new Color(255,200,150), new Color(50,0,0),1.0d,0.7d,1.0d, false)
            };
	}*/
}
