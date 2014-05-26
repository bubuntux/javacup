package org.dsaw.javacup.tacticas.jvc2012.rchavarria;

import java.util.Random;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Position;

public class KickToPass {

    /** probabilidad de pasar a un compa�ero si tengo el balon */
    private static final int PASS_LIKELYHOOD = 50;
    /***/
    private static final int ITERATIONS_TO_FIND_GOOD_TARGET = 20;
    /***/
    private static final double DEFAULT_PASS_POWER = 0.75;
    /***/
    private static final int MIN_PASS_ANGLE = 15;
    /***/
    private static final int DELTA_PASS_ANGLE = 30;
    
    private Random random = new Random();
    
    /**
     * @return
     *      true si se decide que se va a pasar a un compa�ero o no basado en probabilidades
     */
    public boolean shouldPass() {
        int likelyhood = random.nextInt(100);
        return likelyhood <= PASS_LIKELYHOOD;
    }

    public Command pass(int kickerPlayer, Position[] players) {
        // inicia contador en cero
        int count = 0;
        int targetPlayer;
        do {
            targetPlayer = random.nextInt(players.length);
            count ++;
        } while(count < ITERATIONS_TO_FIND_GOOD_TARGET
                && !goodPlayerToPass(kickerPlayer, targetPlayer, players));
        
        // Si no hemos encontrado un buen destino para el pase, avanzamos
        return (kickerPlayer == targetPlayer || !goodPlayerToPass(kickerPlayer, targetPlayer, players)) 
                ? new CommandHitBall(kickerPlayer)
                : new CommandHitBall(kickerPlayer, 
                                     players[targetPlayer],
                                     DEFAULT_PASS_POWER,
                                     computePassAngle());
    }

    /**
     * 
     * @param kickerPlayer
     *      jugador que tiene el balon y pasara
     * @param targetPlayer
     *      posible jugador que recibir� el pase
     * @param players
     *      datos sobre los jugadores
     * @return
     *      true si targetPlayer es un buen candidato para pasarle el bal�n
     */
    private boolean goodPlayerToPass(int kickerPlayer, int targetPlayer, Position[] players) {
        return (kickerPlayer != targetPlayer)
                && kickerIsBehindTarget(kickerPlayer, targetPlayer, players);
    }

    private boolean kickerIsBehindTarget(int kickerPlayer, int targetPlayer, Position[] players) {
        return players[kickerPlayer].getY() < players[targetPlayer].getY();
    }

    private int computePassAngle() {
        return MIN_PASS_ANGLE + random.nextInt(DELTA_PASS_ANGLE);
    }
    
}
