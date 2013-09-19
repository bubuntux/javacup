package org.javahispano.javacup.tacticas.jvc2012.romedal;

import java.awt.Color;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class DetalleTactica implements TacticDetail, Alineaciones {

	String	nombre	= "RomedalTeam";

	public DetalleTactica(final String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getTacticName() {
		return nombre;
	}

	@Override
	public String getCountry() {
		return "Colombia";
	}

	@Override
	public String getCoach() {
		return "Roland Cruz";
	}

	@Override
	public Color getShirtColor() {
		return new Color(255, 255, 0);
	}

	@Override
	public Color getShortsColor() {
		return new Color(0, 51, 255);
	}

	@Override
	public Color getShirtLineColor() {
		return new Color(255, 0, 51);
	}

	@Override
	public Color getSocksColor() {
		return new Color(255, 0, 0);
	}

	@Override
	public Color getGoalKeeper() {
		return new Color(0, 0, 0);
	}

	@Override
	public EstiloUniforme getStyle() {
		return EstiloUniforme.SIN_ESTILO;
	}

	@Override
	public Color getShirtColor2() {
		return new Color(57, 217, 187);
	}

	@Override
	public Color getShortsColor2() {
		return new Color(225, 56, 163);
	}

	@Override
	public Color getShirtLineColor2() {
		return new Color(92, 106, 216);
	}

	@Override
	public Color getSocksColor2() {
		return new Color(255, 0, 0);
	}

	@Override
	public Color getGoalKeeper2() {
		return new Color(0, 0, 0);
	}

	@Override
	public EstiloUniforme getStyle2() {
		return EstiloUniforme.LINEAS_VERTICALES;
	}

	class JugadorImpl implements PlayerDetail {

		String		nombre;
		int			numero;
		Color		piel, pelo;
		double		velocidad, remate, presicion;
		boolean		portero;
		Position	Position;
		double		velocidadAvance	= 0;

		public JugadorImpl(final String nombre, final int numero, final Color piel, final Color pelo, final double velocidad, final double remate, final double presicion, final boolean portero) {
			this.nombre = nombre;
			this.numero = numero;
			this.piel = piel;
			this.pelo = pelo;
			this.velocidad = velocidad;
			this.remate = remate;
			this.presicion = presicion;
			this.portero = portero;
		}

		public double getVelocidadAvance() {
			return velocidadAvance;
		}

		public void setVelocidadAvance(final double velocidadAvance) {
			this.velocidadAvance = velocidadAvance;
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
		return jugadores;
	}

	PlayerDetail[]	jugadores	= new JugadorImpl[] {//
								//
			new JugadorImpl("0", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.7d, 1.0d, true),//
			new JugadorImpl("1", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.9d, 1.0d, false),//
			new JugadorImpl("2", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
			new JugadorImpl("3", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.9d, 1.0d, false),//
			new JugadorImpl("4", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
			new JugadorImpl("5", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
			new JugadorImpl("6", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
			new JugadorImpl("7", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
			new JugadorImpl("8", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
			new JugadorImpl("9", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
			new JugadorImpl("10", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false) //
								};	//

	public TacticDetail getDetail() {
		return this;
	}

	public Position[] getStartPositions(final GameSituations sp) {
		return alineacion5;
	}

	public Position[] getNoStartPositions(final GameSituations sp) {
		return alineacion6;
	}

	public List<Command> execute(final GameSituations sp) {
		return null;
	}

}