package org.javahispano.javacup.tacticas.jvc2012.fortega;

import java.awt.Color;
import org.javahispano.javacup.model.*;
import org.javahispano.javacup.model.util.*;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.model.command.*;
import org.javahispano.javacup.model.engine.GameSituations;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Frioleros implements Tactic {
	// Normal
    Position alineacion1[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-14.74125874125874,-29.93212669683258),
            new Position(13.79020979020979,-30.407239819004527),
            new Position(25.916083916083913,-2.6131221719457014),
            new Position(7.526717557251908,-11.753731343283583),
            new Position(-8.564885496183205,-11.753731343283583),
            new Position(-24.65648854961832,-2.3507462686567164),
            new Position(8.55944055944056,12.115384615384617),
            new Position(-9.748251748251748,11.165158371040723),
            new Position(-13.314685314685315,30.407239819004527),
            new Position(12.717557251908397,29.51492537313433)
        };



	Position alineacion2[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(-26.153846153846157, -29.21945701357466),
			new Position(-11.412587412587413, -36.58371040723982),
			new Position(7.37062937062937, -36.82126696832579),
			new Position(23.3006993006993, -28.269230769230766),
			new Position(-10.937062937062937, -7.126696832579185),
			new Position(-2.6153846153846154, -25.656108597285066),
			new Position(6.895104895104895, -10.690045248868778),
			new Position(-0.4755244755244755, -0.9502262443438915),
			new Position(-3.804195804195804, -0.47511312217194573),
			new Position(2.6153846153846154, -0.47511312217194573) };
	Position alineacion3[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(-26.153846153846157, -29.21945701357466),
			new Position(-11.412587412587413, -36.58371040723982),
			new Position(7.37062937062937, -36.82126696832579),
			new Position(23.3006993006993, -28.269230769230766),
			new Position(-10.937062937062937, -7.126696832579185),
			new Position(-2.6153846153846154, -25.656108597285066),
			new Position(6.895104895104895, -10.690045248868778),
			new Position(-0.4755244755244755, -9.97737556561086),
			new Position(-9.986013986013985, -0.23755656108597287),
			new Position(10.223776223776223, 0.0) };
	// Defensa
    Position alineacion4[]=new Position[]{
            new Position(0.2595419847328244,-50.41044776119403),
            new Position(-15.454545454545453,-44.660633484162894),
            new Position(14.503496503496503,-45.61085972850679),
            new Position(24.251748251748253,-19.004524886877828),
            new Position(7.37062937062937,-33.970588235294116),
            new Position(-7.608391608391608,-32.782805429864254),
            new Position(-24.965034965034967,-16.866515837104075),
            new Position(9.986013986013985,-9.264705882352942),
            new Position(-9.034965034965035,-9.027149321266968),
            new Position(-13.314685314685315,30.407239819004527),
            new Position(12.717557251908397,29.51492537313433)
        };








	class TacticDetailImpl implements TacticDetail {

		public String getTacticName() {
			return "Frioleros";
		}

		public String getCountry() {
			return "Groenlandia";
		}

		public String getCoach() {
			return "Sr Ortega";
		}

		public Color getShirtColor() {
			return new Color(0, 0, 0);
		}

		public Color getShortsColor() {
			return new Color(0, 0, 0);
		}

		public Color getShirtLineColor() {
			return new Color(255, 255, 0);
		}

		public Color getSocksColor() {
			return new Color(0, 0, 0);
		}

		public Color getGoalKeeper() {
			return new Color(255, 255, 0);
		}

		public EstiloUniforme getStyle() {
			return EstiloUniforme.SIN_ESTILO;
		}

		public Color getShirtColor2() {
			return new Color(255, 255, 51);
		}

		public Color getShortsColor2() {
			return new Color(0, 0, 0);
		}

		public Color getShirtLineColor2() {
			return new Color(255, 255, 0);
		}

		public Color getSocksColor2() {
			return new Color(0, 0, 0);
		}

		public Color getGoalKeeper2() {
			return new Color(0, 0, 0);
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

			public JugadorImpl(String nombre, int numero, Color piel,
					Color pelo, double velocidad, double remate,
					double presicion, boolean portero) {
				this.nombre = nombre;
				this.numero = numero;
				this.piel = piel;
				this.pelo = pelo;
				this.velocidad = velocidad;
				this.remate = remate;
				this.presicion = presicion;
				this.portero = portero;
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
			return new PlayerDetail[] {
					new JugadorImpl("Eustoquio", 1, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, true),
					new JugadorImpl("Atanasio", 2, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.5d, false),
					new JugadorImpl("Ambrosio", 3, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Gaudencio", 4, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Evaristo", 5, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Prudencio", 6, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Segismundo", 7, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Eufrasio", 8, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false),
					new JugadorImpl("Gaudencio", 9, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false),
					new JugadorImpl("Ermenegildo", 10, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false),
					new JugadorImpl("Juanjo", 11, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false) };
		}
	}

	TacticDetail detalle = new TacticDetailImpl();

	public TacticDetail getDetail() {
		return detalle;
	}

	public Position[] getStartPositions(GameSituations sp) {
		return alineacion2;
	}

	public Position[] getNoStartPositions(GameSituations sp) {
		return alineacion3;
	}

	LinkedList<Command> comandos = new LinkedList<Command>();

	@Override
	public List<Command> execute(GameSituations sp) {
		// Variables
		comandos.clear();
		Random r = new Random();
		Position[] jugadores = sp.myPlayers();
		int fuerza = 45;
		Position apunta = new Position();

		// Control de fuerza y punteria
		if (sp.ballPosition().getY() > -50 && sp.ballPosition().getY() < -10) {
			fuerza = 45;
			apunta = Constants.centroCampoJuego;
		}

		if (sp.ballPosition().getY() > -10 && sp.ballPosition().getY() < 0) {
			fuerza = 45;
			apunta = Constants.centroArcoSup;
		}

		if (sp.ballPosition().getY() == 0.0 && sp.ballPosition().getX() == 0.0) {
			fuerza = 1;
			apunta = Constants.centroArcoInf;
		}
		if (sp.ballPosition().getY() > 0 && sp.ballPosition().getY() < 10) {
			fuerza = 25;
			apunta = Constants.centroArcoSup;
		}
		if (sp.ballPosition().getY() < 20 && sp.ballPosition().getY() > 10) {
			fuerza = 20;
			apunta = Constants.centroArcoSup;
		}
		if (sp.ballPosition().getY() < 30 && sp.ballPosition().getY() > 20) {
			fuerza = 15;
			apunta = Constants.centroArcoSup;
		}
		if (sp.ballPosition().getY() < 40 && sp.ballPosition().getY() > 30) {
			fuerza = 10;
			apunta = Constants.centroArcoSup;
		}
		if (sp.ballPosition().getY() < 50 && sp.ballPosition().getY() > 40) {
			fuerza = 10;
			apunta = Constants.centroArcoSup;
		}

		// Defensa o ataque
		if (sp.ballPosition().getY() > -5) {
			for (int i = 1; i < jugadores.length; i++) {
				comandos.add(new CommandMoveTo(i, alineacion1[i]));
			}
		} else {
			for (int i = 0; i < jugadores.length; i++) {
				comandos.add(new CommandMoveTo(i, alineacion4[i]));
			}
		}

		// Defender el balon
		if (sp.rivalCanKick().length > 0) {
			for (int i = 0; i < jugadores.length; i++) {
				comandos.add(new CommandMoveTo(i, sp.ballPosition()));
			}
		}
		// Defender la posicion
		if (!sp.isRivalStarts()) {
			int[] recuperadores = sp.getRecoveryBall();
			if (recuperadores.length > 0) {
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				for (int i = 1; i < recuperadores.length; i++) {
					comandos.add(new CommandMoveTo(
							recuperadores[i],
							new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			} else {
				for (int i = 1; i < jugadores.length; i++) {
					comandos.add(new CommandMoveTo(i, alineacion4[i]));
				}
			}
		}
		// Disparo a puerta
		if (sp.canKick().length > 0) {
			//if (r.nextBoolean() || sp.ballPosition().getY() < -25
				//	|| sp.ballPosition().getY() > 30) {
				for (int i = 0; i < jugadores.length; i++) {
					comandos.add(new CommandHitBall(i, apunta, 1, fuerza));
				}
				// Regate
			//} else {
			//	for (int i = 0; i < jugadores.length; i++) {
			//		comandos.add(new CommandHitBall(i));
			//	}
		//	}
		}

		return comandos;
	}
}
