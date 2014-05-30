package org.dsaw.javacup.tactics.jvc2012.hortalezablamers;

import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.PlayerDetail;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.util.Position;
import org.dsaw.javacup.render.UniformStyle;

import java.awt.*;

public class HortalezaBlamersDetalle implements TacticDetail {

	@Override
        public String getTacticName() {
		return "HortalezaBlamers";
	}

	@Override
        public CountryCode getCountry() {
		return CountryCode.ES;
	}

	@Override
        public String getCoach() {
		return "Al Jabber";
	}

	@Override
        public Color getShirtColor() {
		return new Color(255, 102, 0);
	}

	@Override
        public Color getShortsColor() {
		return new Color(0, 0, 153);
	}

	@Override
        public Color getShirtLineColor() {
		return new Color(255, 255, 255);
	}

	@Override
        public Color getSocksColor() {
		return new Color(255, 102, 0);
	}

	@Override
        public Color getGoalKeeper() {
		return new Color(102, 102, 102);
	}

	@Override
        public UniformStyle getStyle() {
		return UniformStyle.LINEAS_VERTICALES;
	}

	@Override
        public Color getShirtColor2() {
		return new Color(192, 138, 142);
	}

	@Override
        public Color getShortsColor2() {
		return new Color(32, 71, 66);
	}

	@Override
        public Color getShirtLineColor2() {
		return new Color(85, 169, 111);
	}

	@Override
        public Color getSocksColor2() {
		return new Color(215, 153, 189);
	}

	@Override
        public Color getGoalKeeper2() {
		return new Color(45, 101, 149);
	}

	@Override
        public UniformStyle getStyle2() {
		return UniformStyle.LINEAS_HORIZONTALES;
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

