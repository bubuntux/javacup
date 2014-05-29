package org.dsaw.javacup.tactics.jvc2012.rchavarria;

import java.util.Random;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class GoalKickers {

    private static final double CLOSE_DISTANCE_FACTOR = 1.15;

    private static final int KICK_MAX_POWER = 1;
    
    private Random random = new Random();
    
    /**
     * @param i
     *      numero de jugador
     * @return
     *      true si se trata de un jugador para rematar a puerta
     */
    public boolean isGoalKicker(int i) {
        return i == 8 || i == 10;
    }

    /**
     * @param sp
     *      contexto del juego
     * @param player
     *      jugador al cual asignar el comando
     * @return
     *      un comando de remate a puerta o un comando de avance
     */
    public Command kickToGoalOrAdvance(GameSituations sp, int player) {
        if(isCloseEnoughToKick(sp, player)){
            //System.out.println("player [id='" + player + "'] kick to goal");
            return kickToGoal(player);
            
        } else {
            //System.out.println("player [id='" + player + "'] advance to goal");
            return new CommandHitBall(player);
        }
    }
    
    private boolean isCloseEnoughToKick(GameSituations sp, int player){
        Position playerPosition = sp.myPlayers()[player];
        double distanceToGoal = playerPosition.distance(Constants.centroArcoSup);
        return (distanceToGoal < Constants.ANCHO_AREA_GRANDE * CLOSE_DISTANCE_FACTOR);
    }

    /**
     * Crea un comando de remate a puerta
     * 
     * @param player 
     * @return
     *      comando de remate a puerta
     */
    private Command kickToGoal(int player) {
        Command command = null;
        if (random.nextBoolean()) {
            // Ordena que debe rematar al centro del arco
            command = new CommandHitBall(player,
                                         Constants.centroArcoSup,
                                         KICK_MAX_POWER,
                                         computeRandomAngle());
            
        } else if (random.nextBoolean()) {
            // Ordena que debe rematar al poste derecho
            command = new CommandHitBall(player,
                                         Constants.posteDerArcoSup, 
                                         KICK_MAX_POWER, 
                                         computeRandomAngle());
            
        } else {
            // Ordena que debe rematar al poste izquierdo
            command = new CommandHitBall(player,
                                         Constants.posteIzqArcoSup, 
                                         KICK_MAX_POWER, 
                                         computeRandomAngle());
        }
        
        return command;
    }

    private double computeRandomAngle() {
        double angle = Constants.ANGULO_VERTICAL / 2;
        double delta = Constants.ANGULO_VERTICAL / 4;
        return angle + random.nextDouble() * delta;
    }
}
