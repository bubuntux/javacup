package org.dsaw.javacup.tacticas.jvc2012.srh;

import java.awt.Color;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

public class SRHTacticDetail implements TacticDetail {

	@Override
        public String getTacticName() {
		return "Rhapsody";
	}

	@Override
        public String getCountry() {
		return "España";
	}

	@Override
        public String getCoach() {
		return "Insa";
	}

	@Override
        public Color getShirtColor() {
		return new Color(255, 0, 0);
	}

	@Override
        public Color getShortsColor() {
		return new Color(255, 255, 255);
	}

	@Override
        public Color getShirtLineColor() {
		return new Color(255, 255, 255);
	}

	@Override
        public Color getSocksColor() {
		return new Color(255, 0, 0);
	}

	@Override
        public Color getGoalKeeper() {
		return new Color(255, 255, 0        );
	}

	@Override
        public UniformStyle getStyle() {
		return UniformStyle.SIN_ESTILO;
	}

	@Override
        public Color getShirtColor2() {
		return new Color(0, 0, 204);
	}

	@Override
        public Color getShortsColor2() {
		return new Color(255, 255, 255);
	}

	@Override
        public Color getShirtLineColor2() {
		return new Color(255, 255, 255);
	}

	@Override
        public Color getSocksColor2() {
		return new Color(0, 0, 255);
	}

	@Override
        public Color getGoalKeeper2() {
		return new Color(255, 255, 0        );
	}

	@Override
        public UniformStyle getStyle2() {
		return UniformStyle.SIN_ESTILO;
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

		@Override
                public String getPlayerName() {
			return nombre;
		}

		@Override
                public Color getSkinColor() {
			return piel;
		}

		@Override
                public Color getHairColor() {
			return pelo;
		}

		@Override
                public int getNumber() {
			return numero;
		}

		@Override
                public boolean isGoalKeeper() {
			return portero;
		}

		@Override
                public double getSpeed() {
			return velocidad;
		}

		@Override
                public double getPower() {
			return remate;
		}

		@Override
                public double getPrecision() {
			return presicion;
		}

	}
	@Override
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
