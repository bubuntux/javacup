package org.dsaw.javacup.tactics.jvc2012.srh;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SRH implements Tactic {

	//Lista de comandos
	LinkedList<Command> comandos = new LinkedList<>();

	// Variables de la clase
	GameSituations sitAct;

	@Override
        public TacticDetail getDetail() {
		return new SRHTacticDetail();
	}


	// Posiciones de alineaciones de tácticas...
	// alineacion1 = Formación de ataque 1 (4-3-3 con el delantero centro atrasado)
	// alineacion2 = Formación de ataque 2 (defensa en medio campo y acoso)
	// alineacion3 = Formacion de defensa 1 (centrales y delanteros retrasados)
	// alineacion4 = Formación de defensa 2 (catenazio + central + 2 palomeros)
	// alineacion5 = Saque de centro, sacamos nosotros.
	// alineacion6 = Saque de centro, sacan ellos.

	Position alineacion1[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-26.867132867132867,-15.441176470588236),
			new Position(-9.986013986013985,-22.56787330316742),
			new Position(8.321678321678322,-23.042986425339365),
			new Position(24.251748251748253,-15.91628959276018),
			new Position(-19.25874125874126,9.97737556561086),
			new Position(0.23776223776223776,3.800904977375566),
			new Position(17.356643356643357,9.027149321266968),
			new Position(-14.503496503496503,38.72171945701358),
			new Position(-0.23776223776223776,24.943438914027148),
			new Position(15.93006993006993,40.147058823529406)
	};

	Position alineacion2[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-24.013986013986013,0.0),
			new Position(-8.321678321678322,-4.513574660633484),
			new Position(7.608391608391608,-4.513574660633484),
			new Position(25.678321678321677,0.7126696832579186),
			new Position(-12.363636363636363,22.092760180995477),
			new Position(0.0,11.877828054298643),
			new Position(12.601398601398602,22.092760180995477),
			new Position(-13.076923076923078,40.38461538461539),
			new Position(0.23776223776223776,36.82126696832579),
			new Position(11.412587412587413,39.90950226244344)
	};

	Position alineacion3[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-19.020979020979023,-26.84389140271493),
			new Position(-8.083916083916083,-32.30769230769231),
			new Position(7.37062937062937,-32.30769230769231),
			new Position(19.496503496503497,-28.744343891402718),
			new Position(-18.06993006993007,-11.877828054298643),
			new Position(-2.377622377622378,-17.104072398190045),
			new Position(14.027972027972028,-11.877828054298643),
			new Position(-20.20979020979021,10.214932126696834),
			new Position(-0.7132867132867133,-1.900452488687783),
			new Position(19.496503496503497,12.115384615384617)
	};

	Position alineacion4[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-17.356643356643357,-43.23529411764706),
			new Position(-7.608391608391608,-40.62217194570136),
			new Position(7.846153846153847,-41.334841628959275),
			new Position(17.594405594405593,-43.94796380090498),
			new Position(-14.503496503496503,-33.49547511312217),
			new Position(0.0,-31.59502262443439),
			new Position(13.076923076923078,-33.73303167420815),
			new Position(-12.125874125874127,35.63348416289593),
			new Position(-0.4755244755244755,-6.414027149321266),
			new Position(13.314685314685315,36.10859728506787)
	};

	Position alineacion5[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-28.293706293706293,-28.031674208144796),
			new Position(-11.888111888111888,-35.63348416289593),
			new Position(11.412587412587413,-35.63348416289593),
			new Position(27.104895104895103,-27.556561085972852),
			new Position(-17.11888111888112,-15.203619909502263),
			new Position(0.0,-10.927601809954751),
			new Position(18.307692307692307,-14.96606334841629),
			new Position(-24.727272727272727,-1.1877828054298643),
			new Position(0.2595419847328244,-0.26119402985074625),
			new Position(22.580152671755727,-1.3059701492537314)
	};

	Position alineacion6[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-27.58041958041958,-27.31900452488688),
			new Position(-13.076923076923078,-34.44570135746606),
			new Position(12.125874125874127,-34.44570135746606),
			new Position(27.104895104895103,-27.31900452488688),
			new Position(-20.447552447552447,-13.778280542986426),
			new Position(2.377622377622378,-9.97737556561086),
			new Position(20.447552447552447,-13.540723981900454),
			new Position(-27.34265734265734,-0.47511312217194573),
			new Position(-6.4885496183206115,-6.529850746268657),
			new Position(27.34265734265734,-0.23755656108597287)
	};

	Position[] estrategiaActual;
	Command[] cmdEstrategiaActual;
	Position[] jugadores;
	Position[] rivales;
	Position balon;

	@Override
        public Position[] getStartPositions(GameSituations sp) {
		return alineacion5;
	}

	@Override
        public Position[] getNoStartPositions(GameSituations sp) {
		return alineacion6;
	}

	@Override
	public List<Command> execute(GameSituations sp) {
		// Actualizamos la variable de clase con la situación actual
		sitAct = sp;

		// Replanteamos la estrategiaActual
		setEstrategiaActual();

		// Actualiza las posiciones de tus jugadores y de los rivales
		jugadores = sitAct.myPlayers();
		rivales = sitAct.rivalPlayers();
		//Actualizo posicion balon
		balon = sitAct.ballPosition();

		//Limpia la lista de comandos
		comandos.clear();

		//Ordena a cada jugador que se ubique segun la estrategia actual
		for (int i = 0; i < jugadores.length; i++) {
			comandos.add(new CommandMoveTo(i, estrategiaActual[i]));
		}

		// Comandos de recuperación del balón
		if(!sp.isRivalStarts())
			no_saca_el_rival();
		// Comandos para pase o disparo
		jugamos_el_balon();

		//Retorna la lista de comandos
		return comandos;
	}


	// Cuando no estamos jugando el balon
	private void no_saca_el_rival() {

		//Array de booleans para saber si el jugador esta ya marcando
		Boolean[] ocupadoJug = new Boolean[11];
		for(int i = 0; i < ocupadoJug.length; i++)
			ocupadoJug[i] = (i < 8 ? false : true);

		// Cubrir a los delanteros contrarios que estén en mi zona
		for(int delantero : getRivalDelanteros()){
			//Busco que jugadores estan mas cerca del rival
			int[] indicesDefensaMasCercano = rivales[delantero].nearestIndexes(jugadores, 0);
			//Recorro los jugadores mas cerca hasta que encuentre un jugador que no este marcando
			int i = 0;
			while(i < ocupadoJug.length && ocupadoJug[indicesDefensaMasCercano[i]] == true){
				i++;
			}
			//Si no es delantero
			if(i < 8){
				//Ordeno al jugador que marque al rival y digo que esta ocupado.
				int indiceDefensaMasCerca = indicesDefensaMasCercano[i];
				ocupadoJug[indicesDefensaMasCercano[i]] = true;
				comandos.add(new CommandMoveTo(indiceDefensaMasCerca, getPositionDondeCubrirAlRival(rivales[delantero])));
			}
		}

		// Mueve el portero
		// Posicionamos el portero en funcion de la posicion del balon
		double posXFinal = sitAct.ballPosition().getX() / (Constants.ANCHO_CAMPO_JUEGO / Constants.LARGO_ARCO);
		comandos.add(new CommandMoveTo(0, new Position(posXFinal, Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_CHICA/2)));

		// Intentar recuperar el balón donde caiga
		int[] recuperadores = sitAct.getRecoveryBall();
		//Si existe posibilidad de recuperar el balon
		if (recuperadores.length > 1) {
			//Obtiene las coordenadas del balon en el instante donde
			//se puede recuperar el balon
			double[] doubleRecuperacion = sitAct.getTrajectory(recuperadores[0]);
			int jugRecuperador = recuperadores[1];
			Position posRecuperacion = new Position(doubleRecuperacion[0],doubleRecuperacion[1]);
			//Ordena a los jugadores que vayan a la posicion de recuperacion
			//Si es el portero, solo ira si esta en el area
			if(jugRecuperador != 0 || (jugRecuperador == 0 && enElArea(posRecuperacion))){
				comandos.add(new CommandMoveTo(jugRecuperador,posRecuperacion));
			}		
		}
	}

	private void jugamos_el_balon() {
		// Cada jugador hace lo que tiene programado
		for(int jugador : sitAct.canKick()) {
			comandos.add(getComandoSegunEstrategia(jugador));
		}
	}

	// Usamos una estrategia u otra en función de la situación del partido
	private void setEstrategiaActual() {
		// Por defecto usamos la primera
		estrategiaActual = alineacion1;

		// Cambiamos en función de la diferencia de goles
		int diferenciaGoles = sitAct.myGoals()-sitAct.rivalGoals();

		// Si el balón está en nuestro campo por debajo del crículo central... defensa
		//if(situacion_actual.ballPosition().getY() < -Constants.RADIO_CIRCULO_CENTRAL) {
		if(sitAct.ballPosition().getY() < 0){
			// Vamos ganando
			if (diferenciaGoles > 0) {
				// Defensa normal
				estrategiaActual = alineacion3;
				// Vamos ganando de mucho
				if (diferenciaGoles > 3)
					// Defensa italiana
					estrategiaActual = alineacion4;
			}
			// Vamos perdiendo de mucho
			if (diferenciaGoles < -3)
				// Bansaiiiiii... al ataqueeeee
				estrategiaActual = alineacion2;
		}
	}

	//Comandos segun tipo de jugador
	private Command getComandoSegunEstrategia(int jugador) {
		Command comandoSalida = null;
		int jugadorMasSolo;
		double potenciaPase;
		double anguloPaseDelantero;
		switch(jugador) {
		case 0:
			//Pase al defensa mas solo
			comandoSalida = new CommandHitBall(jugador, jugadores[getJugadorSolo(jugador)], 1, 45.0);
			break;
		case 1: case 2: case 3: case 4:
			jugadorMasSolo = getJugadorSolo(jugador);
			potenciaPase = calcularPotencia(jugador, jugadorMasSolo);
			double angulo = 45.0;
			//Pase
			comandoSalida = new CommandHitBall(jugador, jugadores[jugadorMasSolo], potenciaPase, angulo);
			break;
		case 5: case 6: case 7:
			jugadorMasSolo = getJugadorSolo(jugador);
			potenciaPase = calcularPotencia(jugador, jugadorMasSolo);
			anguloPaseDelantero = 45.0;
			//Pase
			comandoSalida = new CommandHitBall(jugador, jugadores[jugadorMasSolo], potenciaPase, anguloPaseDelantero);
			break;
		case 8: case 9: case 10:
			if(jugadores[jugador].getY() > Constants.LARGO_CAMPO_JUEGO/4)
				// Si estamos cerca de la portería
				comandoSalida = new CommandHitBall(jugador, Constants.centroArcoSup, 1, 15.0);
			else {
				// Pasamos el balón
				jugadorMasSolo = getJugadorSolo(jugador);
//				potenciaPase = calcularPotencia(jugador, jugadorMasSolo);
//				anguloPaseDelantero = 45.0;
//				comandoSalida = new CommandHitBall(jugador, jugadores[jugadorMasSolo], potenciaPase, anguloPaseDelantero);
				double anguloPase = calcularAngulo(jugador, jugadorMasSolo);
				comandoSalida = new CommandHitBall(jugador, jugadores[jugadorMasSolo], 1, anguloPase);
			}
			break;
		default:
			comandoSalida = new CommandHitBall(jugador, Constants.centroArcoSup, 1, true);
		}
	
		return comandoSalida;
	}

	// Este método devuelve una posición para cubrir a un contrario en función
	// de la posición del balón. 
	private Position getPositionDondeCubrirAlRival(Position rival) {
		Position pos_final = rival;
		Position pelota = sitAct.ballPosition();

		//Si el balon esta en el suelo adelantarse
		if(sitAct.ballAltitude() == 0){
			pos_final = pos_final.moveAngle(pos_final.angle(pelota), Constants.DISTANCIA_CONTROL_BALON - Constants.VELOCIDAD_MAX);
		}
		else{
			pos_final = pos_final.moveAngle(Constants.centroArcoInf.angle(), -Constants.DISTANCIA_CONTROL_BALON + Constants.VELOCIDAD_MAX);
		}
		return pos_final;
	}

	// Lista índices de rivales que están por debajo de la mitad de mi campo
	private ArrayList<Integer> getRivalDelanteros() {
		// Calculamos los índices de jugadores rivales que están en mi área
		ArrayList<Integer> salida = new ArrayList<>();

		// Recorremos los jugadores contrarios
		for(int indexRival = 0; indexRival < rivales.length; indexRival++) {
			// Si el rival está en mi campo lo añado a la lista de delanteros
			if(rivales[indexRival].getY() < 0)
				salida.add(indexRival);
		}

		return salida;
	}

	//Jugador que tiene al defensa mas lejos excepto el mismo
	private int getJugadorSolo(int n){
		//Por defecto el jugador solo es el delantero y la distancia maximo es 0
		Integer jugSolo = null;
		double distAlRival = 0;

		// Jugadores a quien pasar en función de quien pasa
		List<Integer> jugadoresARevisar = new ArrayList<>();;
		switch(n) {
		case 0: case 1: case 2: case 3: case 4:
			jugadoresARevisar.add(1);
			jugadoresARevisar.add(2);
			jugadoresARevisar.add(3);
			jugadoresARevisar.add(4);
			jugadoresARevisar.add(5);
			jugadoresARevisar.add(6);
			jugadoresARevisar.add(7);
			break;
		case 5: case 6: case 7:
			jugadoresARevisar.add(1);
			jugadoresARevisar.add(2);
			jugadoresARevisar.add(3);
			jugadoresARevisar.add(4);
			jugadoresARevisar.add(8);
			jugadoresARevisar.add(9);
			jugadoresARevisar.add(10);
			break;
		case 8: case 9: case 10:
			jugadoresARevisar.add(5);
			jugadoresARevisar.add(6);
			jugadoresARevisar.add(7);
			jugadoresARevisar.add(8);
			jugadoresARevisar.add(9);
			jugadoresARevisar.add(10);
			break;
		}
		
		//Por cada jugador mio, a excepcion del portero, en orden de cercanía al balón
		for(int indexMio : jugadores[n].nearestIndexes(jugadores, n, 0)) {
			if(jugadoresARevisar.contains(indexMio) && indexMio != n && indexMio != 0) {
				//Obtengo el indice del jugador rival mas cercano
				int indRivalMasCercano = jugadores[indexMio].nearestIndex(rivales,0);
				//Calculo la distancia de mi jugador al rival
				double tempDistanciaAlRival = rivales[indRivalMasCercano].distance(jugadores[indexMio]);
				// Si la distancia al rival es mayor y la posición está por encima
				if(tempDistanciaAlRival > distAlRival){
					distAlRival = tempDistanciaAlRival;
					jugSolo = indexMio;
				}
			}
		}
		// No hay ningún jugador por encima del indicado elegimos al mas cercano
		if(jugSolo == null)
			jugSolo = jugadores[n].nearestIndexes(jugadores)[1];

		return jugSolo;

	}

	// Calcula la potencia con la que realizar un pase
	private double calcularPotencia(int jugador, int jugadorDestino){
		double distanciaAlCompi = jugadores[jugador].distance(jugadores[jugadorDestino]);
		double potenciaJugador = sitAct.getMyPlayerPower(jugador) * Constants.REMATE_VELOCIDAD_MAX;
		double alcanceMax = Math.pow(potenciaJugador, 2) / 10;
		double potencia = distanciaAlCompi / alcanceMax;
		return potencia;
	}
	
	// Calcula el ángulo para usar la potencia máxima
	private double calcularAngulo(int jugador, int jugadorDestino){
		double distanciaAlCompi = jugadores[jugador].distance(jugadores[jugadorDestino]);
		double potenciaJugador = sitAct.getMyPlayerPower(jugador) * Constants.REMATE_VELOCIDAD_MAX * 20;
		double alcanceMax = Math.pow(potenciaJugador, 2) / 9.8 * Math.sin(2 * 45) ;
		return (45*distanciaAlCompi / alcanceMax);
	}

	//Determina si una posicion esta en el area mia
	private boolean enElArea(Position pos) {
		if(Math.abs(pos.getX()) <= Constants.LARGO_AREA_GRANDE/2 && pos.getY() <= Constants.centroArcoInf.getY()+Constants.ANCHO_AREA_GRANDE){
			return true;
		}
		else{
			return false;
		}
	}

	//	//Calcula el indice del jugador mio de campo mas proximo al balon
	//	private int jugadorProximoBalon(){
	//		return sitAct.ballPosition().nearestIndex(jugadores, 0);
	//	}
	//
	//	//Dado un jugador calculo la distancia al rival mas proximo
	//	private double distanciaAlRival(int jug){
	//		int rivalMasProximo = jugadores[jug].nearestIndex(rivales);
	//		return jugadores[jug].distance(rivales[rivalMasProximo]);
	//	}
	//
	//	//Calculo si estoy atacando
	//	private boolean atacando(){
	//		return (balon.getY() > 0 ? true : false);
	//	}
	//
	//	//Calculo si estoy defendiendo
	//	private boolean defendiendo(){
	//		return (balon.getY() <= 0 ? true : false);
	//	}
}