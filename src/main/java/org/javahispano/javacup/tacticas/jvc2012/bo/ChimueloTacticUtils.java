/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2012.bo;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author jsebas
 */
public class ChimueloTacticUtils {
    
    public static final int NUM_OF_PLAYERS = 11;
    
    public static int[] getLessOneIndex(int[] source, int lessIndex) {
        int[] result = new int[source.length - 1];

        int indexResult = 0;
        for (int i = 0; i < source.length; i++) {
            if (lessIndex != i) {
                result[indexResult++] = source[i];
            }
        }

        return result;
    }
    
    public static int calculateIterToBall(Position position, double z, Position[] players, PlayerDetail[] details, int[] iterToShoot) 
    {
        int best = Constants.ITERACIONES;
        
        for (int i = 0; i < players.length; i++) 
        {
            double reachHightDistance = (details[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON);
            
            if ( reachHightDistance >= z) {
                double reachWidthDistance = (details[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                int it = (int) Math.ceil(Math.max(0, players[i].distance(position) - reachWidthDistance) / Constants.getVelocidad(details[i].getSpeed()));
                
                if (iterToShoot[i] <= it && it < best) {
                    best = it;
                }
            }
        }
        return best;
    }
}
