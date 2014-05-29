package org.dsaw.javacup.tactics.jvc2012.rchavarria;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

public class RChavarriaTactic implements Tactic {
    
    private TacticDetail detail = new TacticDetailImpl();
    private LineUps lineups = new LineUps();
    private GoalKickers goalKickers = new GoalKickers();
    private KickToPass kickToPass = new KickToPass();
    private GoalKeeper goalKeeper = new GoalKeeper();
    private Recoveries recoveries = new Recoveries();
    
    @Override
    public TacticDetail getDetail() {
        return detail;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return lineups.getStartPositions();
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return lineups.getNoStartPositions();
    }

    @Override
    public List<Command> execute(GameSituations sp) {
        List<Command> commands = new LinkedList<>();

        arrangePlayers(sp, commands);
        tryToRecoverBall(sp, commands);

        // Recorre la lista de mis jugadores que pueden rematar
        for (int i : sp.canKick()) {
            if(goalKeeper.isGoalKeeper(i)){
                //System.out.println("goalkeeper clear");
                Command c = goalKeeper.clear();
                commands.add(c);
                
            } else if(kickToPass.shouldPass()) {
                //System.out.println("player [id='"+i+"'] pass or advance");
                Command c = kickToPass.pass(i, sp.myPlayers());
                commands.add(c);
                
            } else if(goalKickers.isGoalKicker(i)) {
                Command c = goalKickers.kickToGoalOrAdvance(sp, i);
                commands.add(c);
                
            } else {
                //System.out.println("player [id='"+i+"'] advance");
                commands.add(advancePlayer(i));
            }
        }
        
        return commands;
    }

    /**
     * move closest player to ball trying to recover it
     * 
     * @param sp
     * @param commands
     */
    private void tryToRecoverBall(GameSituations sp, List<Command> commands) {
        if (!sp.isRivalStarts()) {
            recoveries.moveClosestPlayerToBall(sp, commands);
        }
    }

    private void arrangePlayers(GameSituations sp, List<Command> commands) {
        Position[] players = sp.myPlayers();
        int lineup = lineups.selectAccordingToBallPosition(sp.ballPosition());
        for (int i = 0; i < players.length; i++) {
            commands.add(new CommandMoveTo(i, lineups.get(lineup)[i]));
        }
    }

    private CommandHitBall advancePlayer(int i) {
        return new CommandHitBall(i);
    }
}
