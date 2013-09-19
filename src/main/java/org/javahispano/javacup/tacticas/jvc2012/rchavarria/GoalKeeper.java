package org.javahispano.javacup.tacticas.jvc2012.rchavarria;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.util.Constants;

public class GoalKeeper {

    private static final double MAX_POWER = 1.0;
    private static final int GOAL_KEEPER_INDEX = 0;

    public boolean isGoalKeeper(int i) {
        return i == GOAL_KEEPER_INDEX;
    }

    /**
     * @return
     *      comando para indicar que el portero despeja
     */
    public Command clear() {
        return new CommandHitBall(GOAL_KEEPER_INDEX, 
                                  Constants.centroCampoJuego, 
                                  MAX_POWER, 
                                  true);
    }
}
