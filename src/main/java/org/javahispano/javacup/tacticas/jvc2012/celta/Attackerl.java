package org.javahispano.javacup.tacticas.jvc2012.celta;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 * Generador de commandos en situaciones de ataque.
 */
public class Attackerl extends BasicCommandGenerator {

    /** No se chutará a puerta a no ser que la distancia sea menor o igual */
    protected static final int MAX_DISTANCE_TO_SHOOT = 15;
    /**
     * Los pases cuya línea de pase tenga alǵun rival a menos de esta distancia
     * serán considerados arriesgados
     */
    protected static final int PASS_SECURITY_MARGIN = 3;
    /**
     * Correr con el balón si hay algún rival a esta distancia, será considerado
     * una mala idea
     */
    protected static final int KEEP_BALL_SECURITY_MARGIN = 3;

    /**
     * Devuelve la lista de commandos en función de la situación del partido
     *
     * @param sp	Situación del partido
     * @return Lista de comandos
     */
    @Override
    public List<Command> getCommandList(GameSituations sp) {
        super.getCommandList(sp);
        List<Command> commands = new ArrayList<Command>();

        for (int i : sp.canKick()) {
            Command command = null;
            // comprobamos si podemos tirar a puerta
			/*if (sp.ballPosition().getY() > Constants.LARGO_CAMPO / 2
            - MAX_DISTANCE_TO_SHOOT && !(isCorner(sp.ballPosition()))) {
            command = shoot(i);
            System.out.println("disparo (attackerl!)");*/
            if (isBallInsideRivalLargeArea()) {
                command = shoot(i);
                //System.out.println("disparo (attackerl!)");

                // comprobamos si es corner
            } else if (isCorner(sp.ballPosition())) {
                Position destino = getRandomPlayerInsideLargeArea();
                command = new CommandHitBall(i, destino, getPower(i, destino), true);

                //comprobamos si podemos centrar
            } else if (sp.ballPosition().getY() > Constants.LARGO_CAMPO / 2
                    - MAX_DISTANCE_TO_SHOOT && !isBallInsideRivalLargeArea()) {
                Position destino = getRandomPlayerInsideLargeArea();
                if (destino != null) {//Agregado para evitar java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
                    command = new CommandHitBall(i, destino, getPower(i, destino), true);
                }
            }
            // si se ha decidido no tirar
            if (command == null) {
                // si es el portero, como por ejemplo por un balon alto, patapum
                if (i == GOAL_KEEPER) {
                    command = longPass(i);
                } // si es un saque de puerta
                else if (isBallStopped() && sp.ballPosition().getY() < (Constants.LARGO_CAMPO / -2 + Constants.LARGO_AREA_GRANDE)) // patapúm p'arriba al jugador más adelantado
                {
                    command = longPass(i);
                } else {
                    if (!isBallStopped()) {
                        // corre con el balón
                        commands.addAll(run(i));
                    } else {
                        // si la cosa está malita, mejor pasamos
                        if (command == null) {
                            command = pass(i);
                        }
                    }
                }
            }
            if (command != null) {
                commands.add(command);
            }
        }

        return commands;
    }

    /**
     * Calcula el mejor tiro posible. Si no hay un tiro claro, devuelve null.
     * @param playerIdx		Jugador que realiza el tiro
     * @return Comando de tiro
     */
    protected Command shoot(int playerIdx) {
        Command shoot;
        Position portero = null;
        Position shoot_to;

        //Obtenemos la position del portero contrario
        for (int i = 0; sp.rivalPlayersDetail().length > i; i++) {
            if (sp.rivalPlayersDetail()[i].isGoalKeeper()) {
                portero = sp.rivalPlayers()[i];
            }
        }

        double proba_poste_Der = 0;
        double proba_poste_Izq = 0;
        //Comprobamos que poste está peor defendido
        if (portero.distance(Constants.posteDerArcoSup) < portero.distance(Constants.posteIzqArcoSup)) {
            proba_poste_Izq = 1;
        } else {
            proba_poste_Der = 1;
        }
        //comprobamos si está limpio el camino a cada uno de los palos.
        proba_poste_Der = proba_poste_Der * probabilityToBeIntercepted(sp.myPlayers()[playerIdx], Constants.posteDerArcoSup);
        proba_poste_Izq = proba_poste_Izq * probabilityToBeIntercepted(sp.myPlayers()[playerIdx], Constants.posteIzqArcoSup);
        //seleccionamos el poste al cual disparar.
        if (proba_poste_Der == proba_poste_Izq && proba_poste_Der == 1) {
            return shoot = null;
        } else if (proba_poste_Der > proba_poste_Izq) {
            shoot_to = Constants.posteIzqArcoSup;
        } else {
            shoot_to = Constants.posteDerArcoSup;
        }

        shoot = new CommandHitBall(playerIdx, shoot_to, 1, false);
        return shoot;
    }

    /**
     * Devuelve el mejor pase posible para el jugador indicado.
     * @param playerIdx	Jugador que realiza el pase
     * @return Comando de pase
     */
    protected CommandHitBall pass(int playerIdx) {
        Position[] players = sp.myPlayers();
        // número del jugador mejor posicionado para recibir el pase
        int bestCandidate = 10;
        // valor inicial disparatadamente bajo, para que al compararlo nunca sea mayor
        int bestPassValue = -1000;

        // si estamos sacando de centro, pasamos a Oubiña o a Alex
        if (sp.ballPosition().equals(Constants.centroCampoJuego)) {
            if (Celta.DEBUG_PASS) {
                System.out.println("SACAMOS DE CENTRO");
            }
            if (Math.random() > .5) {
                bestCandidate = 4;
            } else {
                bestCandidate = 5;
            }
        } else {
            // si es portero patapúm p'arriba
            if (sp.myPlayersDetail()[playerIdx].isGoalKeeper()) {
                int[] nearestPlayers = players[1].nearestIndexes(players);
                // enviamos el balón hacia nuestro jugador más alejado
                bestCandidate = nearestPlayers[nearestPlayers.length - 1];
            } else {
                // recorremos la lista de nuestros jugadores para ver cuál es el
                // mejor candidato para recibir el pase
                for (int candidate = 0; candidate < players.length; candidate++) {
                    if (candidate != playerIdx) { // nada de autopases
                        int value = valuePass(playerIdx, candidate);
                        if (value > bestPassValue) {
                            bestPassValue = value;
                            bestCandidate = candidate;
                        }
                    }
                }
            }
        }

        double passDistance = players[playerIdx].distance(players[bestCandidate]);
        double power = passDistance * .035;
        boolean highKick = (passDistance > 25); // los pases largos van por alto

        if (Celta.DEBUG_PASS) {
            System.out.println("pase de "
                    + sp.myPlayersDetail()[playerIdx].getPlayerName() + " ("
                    + sp.myPlayersDetail()[playerIdx].getNumber() + ") para "
                    + sp.myPlayersDetail()[bestCandidate].getPlayerName()
                    + " (" + sp.myPlayersDetail()[bestCandidate].getNumber()
                    + ")    power = " + power + ", highKick = " + highKick
                    + "    [ valor del pase = " + bestPassValue + "]");
        }

        return new CommandHitBall(playerIdx, players[bestCandidate], power,
                highKick);
    }

    /**
     * Valora si un pase es bueno no malo. Mayor que 0 es un pase bueno y menor
     * que 0 es malo.
     * @param fromPlayerIdx	Número del jugador que realiza el pase
     * @param toPlayerIdx   Número del jugador hacia el que va el pase
     * @return Valoración del pase
     */
    protected int valuePass(int fromPlayerIdx, int toPlayerIdx) {
        double value = 0;
        Position from = sp.myPlayers()[fromPlayerIdx];
        Position to = sp.myPlayers()[toPlayerIdx];

        // al portero no le pasamos a menudo el balón que es un poco torpe
        if (sp.myPlayersDetail()[toPlayerIdx].isGoalKeeper()) {
            value -= 40;
        }

        // cuánto mayor sea la probabilidad que nos intercepten, menor será el valor
        // esto restará de 0 a 50 puntos
        double interception = probabilityToBeIntercepted(from, to);
        value -= interception * 50;

        // si con el pase se avanza (eje Y) se suman puntos y si se retrocede se restan
        double moveForward = to.getY() - from.getY();
        value += moveForward * .50;

        if (Celta.DEBUG_PASS) {
            System.out.println("   valoración del pase a "
                    + sp.myPlayersDetail()[toPlayerIdx].getPlayerName() + " ("
                    + sp.myPlayersDetail()[toPlayerIdx].getNumber() + ") = "
                    + value + " -->  probabilidad de ser interceptado = "
                    + interception + "   puntos prob. interc. = "
                    + interception * -50 + "   puntos por avance = "
                    + moveForward * .5);
        }

        return (int) Math.round(value);
    }

    /**
     * Calcula la probabilidad (de 0 a 1) de que un pase sea interceptado por el
     * equipo rival.
     * @param from	Posición del jugador que efectúa el pase
     * @param to    Posición del jugador que recibirá el balón
     * @return Probabilidad de que el el balón sea interceptado por el rival
     */
    protected double probabilityToBeIntercepted(Position from, Position to) {
        // valor inicial disparatado, para que al compararlo nunca sea menor
        double minDistance = 1000;
        // cantidad de rivales cerca de la línea de pase
        int amountOfRivalsNearPassLine = 0;

        // comprobamos a qué distancia de la línea de pase se encuentran los
        // rivales
        Position[] rivals = sp.rivalPlayers();
        for (int i = 0; i < rivals.length; i++) {
            double distance = Line2D.ptSegDist(from.getX(), from.getY(),
                    to.getX(), to.getY(), rivals[i].getX(), rivals[i].getY());
            if (distance < minDistance) {
                minDistance = distance;
            }

            if (distance < PASS_SECURITY_MARGIN) {
                amountOfRivalsNearPassLine++;
                if (Celta.DEBUG_PASS) {
                    System.out.println("    distancia del rival "
                            + sp.rivalPlayersDetail()[i].getNumber()
                            + " a la trayectoria del pase: " + distance);
                }

            }
        }

        // calculamos la probabilidad
        double probability = amountOfRivalsNearPassLine * .2;
        if (minDistance <= 2) {
            probability = 1;
        }

        return (probability > 1) ? 1 : probability;
    }

    /**
     * Calcula la probabilidad de perder el balón si se corre hacia una determinada posición
     * @param from	Posición de origen
     * @param to	Posición hacia la que se avanza
     * @return		Probabilidad de que perdamos el balón (de 0 a 1, siendo 1 que nos van a quitar el balón seguro)
     */
    protected double probabilityToLooseTheBall(Position from, Position to) {
        double probability = 0;
        // comprobamos cuántos rivales hay entre la posición actual y la final
        Position[] rivals = sp.rivalPlayers();
        for (int i = 0; i < rivals.length; i++) {
            double distanceToTrajectory = Line2D.ptSegDist(from.getX(), from.getY(),
                    to.getX(), to.getY(), rivals[i].getX(), rivals[i].getY());
            double distanceToPlayer = from.distance(rivals[i]);

            if (distanceToTrajectory < KEEP_BALL_SECURITY_MARGIN) {
                probability += 0.2;
            }
            if (distanceToPlayer < KEEP_BALL_SECURITY_MARGIN) {
                probability += 0.3;
            }
        }
        return (probability > 1) ? 1 : probability;
    }

    /**
     * Indica al jugador que corra con el balón. Devuelve null si hay demasiados rivales cerca.
     * @param playerIdx		Índice del jugador
     * @return Comando de movimiento
     */
    protected List<Command> run(int playerIdx) {
        Position destination = Alignment.INITIAL[playerIdx].movePosition(0, 15);
        List<Command> commands = new LinkedList<Command>();
        if (probabilityToLooseTheBall(sp.myPlayers()[playerIdx], destination) < .7) {
            commands.add(new CommandHitBall(playerIdx));
            commands.add(new CommandMoveTo(playerIdx, destination));
        }
        return commands;
    }

    /**
     * Indica si el balón está en posición de saque de corner
     * @param ballPosition Posición del balón.
     * @return true o false
     */
    protected boolean isCorner(Position ballPosition) {

        if (isBallStopped() && ballPosition == Constants.cornerSupDer) {
            return true;
        } else if (isBallStopped() && ballPosition == Constants.cornerSupIzq) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Devuelve un jugador al azar que esté dentro del area constraria
     * @return Posición jugador
     */
    protected Position getRandomPlayerInsideLargeArea() {
        Position[] players = sp.myPlayers();
        List<Position> posiblePlayers = new LinkedList<Position>();
        for (int i = 0; i < players.length; i++) {
            if (isPlayerInsideLargeArea(i)) {
                posiblePlayers.add(players[i]);
            }
        }
        if (posiblePlayers.size() > 0) {//Agregado para evitar java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
            int idx=-1;
            do {
                idx=(int) Math.round(Math.random() * posiblePlayers.size());
            } while (idx>=posiblePlayers.size());//While para evitar otra excepcion
            return posiblePlayers.get(idx);
        } else {
            return null;
        }
    }
}
