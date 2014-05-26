/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tacticas.jvc2012.moleteam;

import java.util.Collections;
import java.util.LinkedList;
import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.command.CommandMoveTo;
import org.dsaw.javacup.model.trajectory.AbstractTrajectory;
import org.dsaw.javacup.model.trajectory.AirTrajectory;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jlosarcos
 */
public class Player {
        Position alineacionNormal[]=new Position[]{
        new Position(0,-52),
        new Position(-6,-32),
        new Position(6,-32),
        new Position(-12,-18),
        new Position(12,-18),
        new Position(12,6),
        new Position(0,-10),
        new Position(-12,6),
        new Position(-12,25),
        new Position(0,40),
        new Position(12,25)
        
        };
        int playerIndex;
        boolean isProcessed;
        boolean canKick;
        MConstants.PlayerRole playerRole;
        Position3D playerPosition;
        int goalProximityIndex = 0;
        int rivalToDefenceIndex = -1;
        boolean isRival;
        public Player(int playerIndex, Position position, int rivalToDefenceIndex, boolean isRival) {
                this.playerIndex = playerIndex;
                this.isProcessed = false;
                this.canKick = false;
                this.playerPosition = new Position3D(position);
                this.rivalToDefenceIndex = rivalToDefenceIndex;
                this.isRival = isRival;
                switch(playerIndex)
                {
                    case 0:
                    {
                        this.playerRole = MConstants.PlayerRole.goalKeeper;
                        this.goalProximityIndex = 0;
                        break;
                    }
                    case 2:
                    case 3:
                    {
                        this.playerRole = MConstants.PlayerRole.secondLineOfDefence;
                        this.goalProximityIndex = 1;
                        break;
                    }
                    case 1:
                    case 4:
                    {
                        this.playerRole = MConstants.PlayerRole.firstLineOfDefence;
                        this.goalProximityIndex = 2;
                        break;
                    }
                    case 5:
                    case 6:
                    case 7:
                    {
                        this.playerRole = MConstants.PlayerRole.middleLine;
                        this.goalProximityIndex = 3;
                        break;
                    }
                    case 8:
                    case 9:
                    case 10:
                    {
                        this.playerRole = MConstants.PlayerRole.attackLine;
                        this.goalProximityIndex = 4;
                        break;
                    }
                }
            }
        public boolean CanGo = true;
        public boolean CanPass = true;
        public boolean CanShoot = true;
        public boolean SimplePass = false;
         public void getCommands(Variables vars)
         {
            Position3D recoveryPosition = this.getRecoveryBallPosition3D(vars);
            if(recoveryPosition != null && this.canKick == false)
            {
                vars.AddCommands(new Move(this.playerIndex, recoveryPosition.to2D()));
            }
            else
            {
                if(Variables.ballIsOurs)
                {
                    if(this.playerIndex == Variables.ballInPlayerIndex && vars.gs.canKick().length > 0)
                    {
                        Position3D ballPosition3D = new Position3D(vars.gs.ballPosition(), vars.gs.ballAltitude());
                        vars.AddCommands(new Shoot(this.playerIndex, 
                                Constants.centroArcoSup,
                                1,
                                25));
                        if(this.playerIndex != 0)
                        {
                            if(ballPosition3D.y > 47 && Math.abs(ballPosition3D.x) > Constants.ANCHO_AREA_GRANDE / 2 )
                            {
                                CheckForPassAdvance(vars, MConstants.ShootTrayectory.backward);
                            }
                            else if(CountPlayersGreaterThanY(vars.myPlayers, this.playerPosition.y, this.playerIndex) == 0)
                            {
                                CheckForGo(vars, MConstants.ShootTrayectory.forward);
                                CheckForShoot(vars);
                            }
                            else if(ballPosition3D.y > 15)
                            {
                                CheckForGo(vars, MConstants.ShootTrayectory.forward);
                                CheckForShoot(vars);
                                CheckForPassAdvance(vars, MConstants.ShootTrayectory.forward);
                            }
                            else if(ballPosition3D.y > 0)
                            {
                                CheckForGo(vars, MConstants.ShootTrayectory.backward);
                                CheckForGo(vars, MConstants.ShootTrayectory.forward);
                                CheckForPassAdvance(vars, MConstants.ShootTrayectory.forward);
                             }
                            else
                            {
                                CheckForGo(vars, MConstants.ShootTrayectory.forward);
                                CheckForPassAdvance(vars, MConstants.ShootTrayectory.forward);
                             }
                        }
                        else
                        {
                           CheckForPassAdvance(vars, MConstants.ShootTrayectory.forward);
                        }   
                        CheckForKickOff(vars);
                    }
                    else
                    {

                        if(vars.gs.ballPosition().nearestIndex(vars.myPlayers.positions) == this.playerIndex)
                        {
                            vars.AddCommands(new Move(this.playerIndex, vars.gs.ballPosition()));
                        }
                        else
                        {
                            vars.AddCommands(new Move(this.playerIndex, this.getAttackPosition(vars)));
                        }

                    }
                }
                else
                {

                    if(this.playerPosition.y > vars.gs.ballPosition().getY())
                    {
                        vars.AddCommands(new Move(this.playerIndex, this.getAttackPosition(vars)));
                    }
                    else
                    {
                        vars.AddCommands(new Move(this.playerIndex, this.getDefencePosition(vars)));
                    }
                }
            }
         }
        public double getRealPlayerPower(Variables vars)
        {
            if(this.isRival)
            {
                return Constants.getVelocidadRemate(vars.gs.getRivalPlayerPower(this.playerIndex));
            }
            else
            {
                return Constants.getVelocidadRemate(vars.gs.getMyPlayerPower(this.playerIndex));
            }
        }
        public double getRealPlayerSpeed(Variables vars)
        {
            if(this.isRival)
            {
                return Constants.getVelocidad(vars.gs.getRivalPlayerSpeed(this.playerIndex));
            }
            else
            {
                return Constants.getVelocidad(vars.gs.getMyPlayerSpeed(this.playerIndex));
            }
        }
        public double getRealPlayerError(Variables vars)
        {
            if(this.isRival)
            {
                return Constants.getErrorAngular(vars.gs.getRivalPlayerError(this.playerIndex));
            }
            else
            {
                return Constants.getErrorAngular(vars.gs.getMyPlayerError(this.playerIndex));
            }
        }
        public double getGoAheadHitPower(Variables vars)
        {
            return (this.getRealPlayerSpeed(vars) * 2 / this.getRealPlayerPower(vars)) - 0.05;
        }
        public Position getGoalKeeperPosition(Variables vars)
        {
            Position3D recoveryBallPosition = this.getRecoveryBallPosition3D(vars);
            Position goalKeeperPosition = null;
            if(recoveryBallPosition != null)
            {
                goalKeeperPosition = recoveryBallPosition.to2D();
            }
            if(goalKeeperPosition == null)
            {
                goalKeeperPosition = Helpers.IntersectionOfTwoSegments(Constants.posteIzqArcoInf.movePosition(0,4), Constants.posteDerArcoInf.movePosition(0,4), Constants.centroArcoInf, vars.gs.ballPosition());
            }
            if(goalKeeperPosition == null)
            {
                goalKeeperPosition = Helpers.IntersectionOfTwoSegments(Constants.posteDerArcoInf, Constants.posteDerArcoInf.movePosition(0,4), Constants.centroArcoInf, vars.gs.ballPosition());
            }
            if(goalKeeperPosition == null)
            {
                goalKeeperPosition = Helpers.IntersectionOfTwoSegments(Constants.posteIzqArcoInf, Constants.posteIzqArcoInf.movePosition(0,4), Constants.centroArcoInf, vars.gs.ballPosition());
            }
            if(goalKeeperPosition == null || !vars.gs.ballPosition().isInsideGameField(0))
            {
                goalKeeperPosition = Constants.centroArcoInf.moveAngle(Constants.centroArcoInf.angle(vars.gs.ballPosition()), 5);
            }
            return goalKeeperPosition.setInsideGameField();
        }         
        Random r = new Random();
        public Position getBasicDefencePosition(Variables vars)
        {
            if(this.rivalToDefenceIndex == -1)
            {
                return this.playerPosition.to2D();
            }
            Position rivalPosition =  vars.rivalPlayers.GetPlayerByIndex(this.rivalToDefenceIndex).playerPosition.to2D();
            return rivalPosition.moveAngle(rivalPosition.angle(Constants.centroArcoInf), Constants.JUGADORES_SEPARACION);
            //return rivalPosition.movePosition(0, -Constants.DISTANCIA_CONTROL_BALON /2);
            //return rivalPosition.moveAngle(rivalPosition.angle(vars.gs.ballPosition()),Constants.DISTANCIA_CONTROL_BALON);
        }
        public Position getBasicAttackPosition(Variables vars)
        {
            if(this.playerIndex == Variables.ballToPlayerIndex && Variables.ballToEstimatedPosition3D != null)
            {
                return Variables.ballToEstimatedPosition3D.to2D();
            }
            if(this.rivalToDefenceIndex == -1)
            {
                return this.playerPosition.to2D();
            }
            Position ballPosition = vars.gs.ballPosition();
            Position rivalPosition =  vars.rivalPlayers.GetPlayerByIndex(this.rivalToDefenceIndex).playerPosition.to2D();
            
            Position p1 = rivalPosition.moveAngle(Math.toRadians(-20), this.goalProximityIndex + 5);
            Position p2 = rivalPosition.moveAngle(Math.toRadians(-160), this.goalProximityIndex + 5);
            if(p1.distance(ballPosition) < p2.distance(ballPosition))
            {
                return p1;
            }
            else
            {
                return p2;
            }
        }
        public Position getAttackPosition(Variables vars)
        {
            Position basicDefencePosition = getBasicDefencePosition(vars);  
            Position basicAttackPosition = getBasicAttackPosition(vars);  
            Position finalPlayerPosition = basicAttackPosition; 
            switch(this.playerRole)
            {
                case goalKeeper:
                {
                    finalPlayerPosition = getGoalKeeperPosition(vars);
                    break;
                }
                case secondLineOfDefence:
                {
                    finalPlayerPosition = basicDefencePosition; 
                    break;
                }
                case firstLineOfDefence:
                {
                    finalPlayerPosition = basicDefencePosition; 
                    break;
                }
                case middleLine:
                {
                   break;
                }
                case attackLine:
                {
                    if(Variables.playerAloneEnabled)
                    {
                        Position defaultPosition = alineacionNormal[this.playerIndex];
                        Player nearestRival = vars.rivalPlayers.GetPlayerByIndex(this.playerPosition.to2D().nearestIndex(vars.rivalPlayers.positions));
                        if(this.playerPosition.to2D().distance(nearestRival.playerPosition.to2D()) < 3 && this.playerPosition.to2D().distance(defaultPosition) < 1)
                        {
                            Variables.playerAloneEnabled = false;
                            break;
                        }
                        if(this.playerIndex == 9 || playerPosition.y < 25)
                        {
                            finalPlayerPosition = defaultPosition;
                        }
                    }
                   break;
                }
            }
            return finalPlayerPosition;
        }
        public Position getDefencePosition(Variables vars)
        {
            Position basicDefencePosition = getBasicDefencePosition(vars);  
            Position basicAttackPosition = getBasicAttackPosition(vars);  
            Position finalPlayerPosition = basicDefencePosition; 
            switch(this.playerRole)
            {
                case goalKeeper:
                {
                   finalPlayerPosition = getGoalKeeperPosition(vars);
                   break;
                }
                case secondLineOfDefence:
                {
                    break;
                }
                case firstLineOfDefence:
                {
                    break;
                }
                case middleLine:
                {
                    break;
                }
                case attackLine:
                {
                    if(Variables.playerAloneEnabled)
                    {
                        Position defaultPosition = alineacionNormal[this.playerIndex];
                        if(this.playerIndex == 9 || playerPosition.y < 25)
                        {
                            finalPlayerPosition = defaultPosition;
                        }
                    }
                   break;
                }
            }
            return finalPlayerPosition;
        }
         public boolean rivalsAreInGoAheadTrayectory(Variables vars,  double angle, double power, double angleZ)
         {
            ArrayList<Position3D> expectedTrayectory = getTrayectoryExpectedFirstTimeInFloor(vars, angle, power, angleZ, false);
            
            for(int iteration=0; iteration < expectedTrayectory.size(); iteration++)
            {
                Position3D estimated3DBallPosition = expectedTrayectory.get(iteration);
                if(estimated3DBallPosition.z <= Constants.ALTURA_CONTROL_BALON)
                {
                    Position estimatedBallPosition = estimated3DBallPosition.to2D();
                    if(estimatedBallPosition.isInsideGameField(1) == false)
                    {
                        return true;
                    }
                    int[] nearestRivalIndexes = estimatedBallPosition.nearestIndexes(vars.rivalPlayers.positions);
                    int[] nearestIndexes = estimatedBallPosition.nearestIndexes(vars.myPlayers.positions);
                    for(int i=0; i<nearestIndexes.length; i++)
                    {
                        Player myPlayer = vars.myPlayers.get(nearestIndexes[i]);
                        if(this.playerIndex == myPlayer.playerIndex)
                        {
                            continue;
                        }
                        if(this.playerPosition.to2D().distance(myPlayer.playerPosition.to2D()) < Constants.JUGADORES_SEPARACION + Constants.DISTANCIA_CONTROL_BALON
                                &&
                                Math.toDegrees(this.playerPosition.to2D().angle(myPlayer.playerPosition.to2D())) + 5  > angle
                                &&
                                angle > Math.toDegrees(this.playerPosition.to2D().angle(myPlayer.playerPosition.to2D())) - 5 
                                )
                        {
                            return true;
                        }
                        Player rivalPlayer = vars.rivalPlayers.get(nearestRivalIndexes[i]);
                        double iterationsToEstimatedBallPosition = Math.round(((this.playerPosition.to2D().distance(estimatedBallPosition) - Constants.DISTANCIA_CONTROL_BALON) / (this.getRealPlayerSpeed(vars) * (iteration))));
                        double rivalIterationsToEstimatedBallPosition = Math.round(((rivalPlayer.playerPosition.to2D().distance(estimatedBallPosition)- Constants.DISTANCIA_CONTROL_BALON) / (rivalPlayer.getRealPlayerSpeed(vars) * (iteration))));
                        if(iterationsToEstimatedBallPosition != 0 
                            && Math.abs(iterationsToEstimatedBallPosition) >= Math.abs(rivalIterationsToEstimatedBallPosition)
                            && Math.abs(rivalPlayer.playerPosition.to2D().distance(estimatedBallPosition)) < Math.abs(this.playerPosition.to2D().distance(estimatedBallPosition)))
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
         }
         public Position3D getTrayectoryExpectedPosition(Variables vars, double shootAngle, double shootPower,  double shootAngleZ, boolean returnNullIfOutOfPlayField)
         {
             ArrayList<Position3D> coords = getTrayectoryExpected(vars, shootAngle, shootPower, shootAngleZ, returnNullIfOutOfPlayField);
             return coords.get(coords.size() -1);
         }
         public Position3D getTrayectoryExpectedPositionFirstTimeInFloor(Variables vars, double shootAngle, double shootPower,  double shootAngleZ, boolean returnNullIfOutOfPlayField)
         {
             ArrayList<Position3D> coords = getTrayectoryExpected(vars, shootAngle, shootPower, shootAngleZ, returnNullIfOutOfPlayField);
             for(int i=1;i<coords.size();i++)
             {
                 Position3D c3d = coords.get(i);
                 if(c3d.z == 0)
                 {
                     c3d.iteration = i;
                     return c3d;
                 }
             }
             return coords.get(coords.size() -1);
         }
         public ArrayList<Position3D> getTrayectoryExpectedFirstTimeInFloor(Variables vars, double shootAngle, double shootPower,  double shootAngleZ, boolean returnNullIfOutOfPlayField)
         {
             ArrayList<Position3D> coords = getTrayectoryExpected(vars, shootAngle, shootPower, shootAngleZ, returnNullIfOutOfPlayField);
             if(coords == null)
             {
                 return null;
             }
             if(shootAngleZ > 0)
             {
                ArrayList<Position3D> result = new ArrayList<Position3D>();
                for(int i=0;i<coords.size();i++)
                {
                    Position3D c3d = coords.get(i);
                    result.add(c3d);
                    if(c3d.z == 0 && i > 0)
                    {
                        return result;
                    }
                }
             }
             return coords;
         }
         public ArrayList<Position3D> getTrayectoryExpected(Variables vars, double shootAngle, double shootPower,  double shootAngleZ, boolean returnNullIfOutOfPlayField)
         {
            double angle = shootAngle * (Math.PI / 180d);
            double speed = shootPower * this.getRealPlayerPower(vars);
            double angleZ = Math.max(0, Math.min(shootAngleZ, Constants.ANGULO_VERTICAL_MAX)) * (Math.PI / 180d);
            int maxIterations = 1000;
           
            AbstractTrajectory trajectory = new AirTrajectory(Math.cos(angleZ) * speed, Math.sin(angleZ) * speed, 0, 0);
            Position3D newBall = new Position3D(vars.gs.ballPosition(), vars.gs.ballAltitude());
            ArrayList<Position3D> result = new ArrayList<Position3D>();
            result.add(newBall);
            double oldtrX = 1000;
            double oldtrY = 1000;
            for (int step = 1; step < maxIterations; ++step) {
                double trX = trajectory.getX(step / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                double trY = trajectory.getY(step / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                Position3D nextBall = new Position3D(vars.gs.ballPosition().getX() + trX * Math.cos(angle), vars.gs.ballPosition().getY() + trX * Math.sin(angle), trY*2,step, trY > oldtrY);
                if(nextBall.to2D().isInsideGameField(0) == false && returnNullIfOutOfPlayField)
                {
                    return null;
                }
                result.add(nextBall);
                if (trX == oldtrX && trY == oldtrY) {
                    break;
                }
                oldtrX = trX;
                oldtrY = trY;            
            }
            return result;
         }
         public boolean rivalsAreInPassTrayectory(Variables vars, Player destinationPlayer, double shootAngle, double shootPower,  double shootAngleZ)
         {
            double passDistance = vars.gs.ballPosition().distance(destinationPlayer.playerPosition.to2D()) - Constants.DISTANCIA_CONTROL_BALON;
            ArrayList<Position3D> expectedTrayectory = getTrayectoryExpected(vars, shootAngle, shootPower, shootAngleZ, false);
            for(int iteration=1; iteration < expectedTrayectory.size(); iteration++)
            {
                Position3D estimated3DBallPosition = expectedTrayectory.get(iteration);
                if(estimated3DBallPosition.to2D().isInsideGameField(1) == false)
                {
                    return true;
                }
                
                if(estimated3DBallPosition.z <= Constants.ALTURA_CONTROL_BALON)
                {
                    double iterationDistance = vars.gs.ballPosition().distance(estimated3DBallPosition.to2D());
                    if(passDistance <= iterationDistance)
                    {
                        return false;
                    }
                    
                    int[] nearestRivalIndexes = estimated3DBallPosition.to2D().nearestIndexes(vars.rivalPlayers.positions);
                    for(int i=0; i<nearestRivalIndexes.length; i++)
                    {
                        Player rivalPlayer = vars.rivalPlayers.get(nearestRivalIndexes[i]);
                        double iterationsToEstimatedBallPosition = Math.abs(Math.round((destinationPlayer.playerPosition.to2D().distance(estimated3DBallPosition.to2D())) / (destinationPlayer.getRealPlayerSpeed(vars) * (iteration))));
                        double rivalIterationsToEstimatedBallPosition = Math.abs(Math.round((rivalPlayer.playerPosition.to2D().distance(estimated3DBallPosition.to2D())) / (rivalPlayer.getRealPlayerSpeed(vars) * (iteration))));
                        if(iterationsToEstimatedBallPosition != 0 
                            && this.playerIndex != destinationPlayer.playerIndex
                            && rivalIterationsToEstimatedBallPosition == 1
                            && iterationsToEstimatedBallPosition > 1
                            )
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
         }
        public int CountPlayersGreaterThanY(Players players, double y, int... excludeIndex)
        {
            int result = 0;
            playerloop: for(Player p : players)
            {
                for(int ix: excludeIndex)
                {
                    if(ix == p.playerIndex)
                    {
                        continue playerloop;
                    }
                }
                if(p.playerPosition.y>=y)
                {
                    result++;
                }
            }
            return result;
        }
        public int CountPlayersLesserThanY(Players players, int y)
        {
            int result = 0;
            for(Player p : players)
            {
                if(p.playerPosition.y<=y)
                {
                    result++;
                }
            }
            return result;
        }
         public Position3D getRecoveryBallPosition3D(Variables vars)
         {
                          
            int[] recoveryBallProvability = vars.gs.getRecoveryBall();
            if(recoveryBallProvability.length > 0)
            {
                if(Variables.ballIsOurs)
                {
                    if(recoveryBallProvability[1] == this.playerIndex)
                    {
                        return new Position3D(vars.gs.getTrajectory(recoveryBallProvability[0]));
                    }
                }
                else
                {
                    for(int i=1;i<recoveryBallProvability.length;i++)
                    {
                        if(i>3)//only return recovery for 2 players
                        {
                            return null;
                        }
                        if(recoveryBallProvability.length != 2 
                                && this.playerIndex == 0 
                                && vars.gs.ballPosition().distance(vars.myPlayers.GetPlayerByIndex(0).playerPosition.to2D()) > 10)
                        {
                            return null;
                        }
                        if(recoveryBallProvability[i] == this.playerIndex)
                        {
                            return new Position3D(vars.gs.getTrajectory(recoveryBallProvability[0]));
                        }
                    }
                }
            }
            return null;
         }
      
         public void CheckForGo(Variables vars, MConstants.ShootTrayectory trayectory)
         {
                if(!CanGo)
                {
                    return;
                }
               if(this.playerIndex==0)
               {
                   return;
               }
         
            
               double hitAngle = -1;
               double hitPower = this.getGoAheadHitPower(vars);
               double hitAngleZ = 0;
               if(trayectory != MConstants.ShootTrayectory.backward)
               {
                double angleToGoal = Math.toDegrees(vars.gs.ballPosition().angle(Constants.centroArcoSup));
                if(!this.rivalsAreInGoAheadTrayectory(vars, angleToGoal, hitPower, hitAngleZ))
                {
                    hitAngle = angleToGoal;
                }
                else
                {
                       if(CountPlayersLesserThanY(vars.rivalPlayers, -20)>3)
                       {
                             return;
                        }
                        LinkedList<Integer> l = new LinkedList<Integer>();
                        for (int i = 0; i < 32; l.add(++i));
                        Collections.shuffle(l);
                        Integer[] randomAngles = l.toArray(new Integer[0]);
                        for(int i=0;i<32;i++)
                        {
                            int angle = randomAngles[i] * 5;
                            if(!this.rivalsAreInGoAheadTrayectory(vars, angle, hitPower, hitAngleZ))
                            {
                                hitAngle = angle;
                                break;
                            }
                        }
                }
               }
               if(hitAngle == -1 && trayectory == MConstants.ShootTrayectory.backward)
               {
                    LinkedList<Integer> l = new LinkedList<Integer>();
                    for (int i = 0; i < 32; l.add(++i));
                    Collections.shuffle(l);
                    Integer[] randomAngles = l.toArray(new Integer[0]);
                    for(int i=0;i<32;i++)
                    {
                        int angle = randomAngles[i] * -5;
                        if(!this.rivalsAreInGoAheadTrayectory(vars, angle, hitPower, hitAngleZ))
                        {
                            hitAngle = angle;
                            break;
                        }
                    }
               }
                if(hitAngle != -1)
                {
                    //commands.add(new CommandMoveTo(this.playerIndex, vars.gs.ballPosition().moveAngle(hitAngle, 5)));
                    //commands.add(new CommandHitBall(this.playerIndex));  
                    vars.AddCommands(new Go(this.playerIndex, hitAngle, hitPower, hitAngleZ));
                }
                return;
         }
          public void CheckForKickOff(Variables vars)
            {
                if(vars.gs.isStarts())
                {
                    if(vars.gs.ballPosition().getX() == (-Constants.ANCHO_CAMPO_JUEGO / 2.0))
                    {
                        vars.AddCommands(new Shoot(this.playerIndex, 
                            Constants.centroArcoSup,
                            1,
                            45));
                    }
                    else if(vars.gs.ballPosition().getX() == (Constants.ANCHO_CAMPO_JUEGO / 2.0))
                    {
                        vars.AddCommands(new Shoot(this.playerIndex, 
                            Constants.centroArcoSup,
                            1,
                            45));
                    }    
                    else
                    {
                        vars.AddCommands(new Shoot(this.playerIndex, 
                            Constants.centroArcoSup,
                            1,
                            45));
                    }
                }
                else if(vars.gs.ballPosition().getX() == 0 && vars.gs.ballPosition().getY() == 0 )
                {
                    vars.AddCommands(new Shoot(this.playerIndex, 
                        Constants.centroArcoSup,
                        1,
                        25 + r.nextInt(15)));
                }
                else if(CountPlayersLesserThanY(vars.myPlayers,0) > 9)
                {
                 vars.AddCommands(new Shoot(this.playerIndex, 
                            Constants.centroArcoSup,
                            1,
                            45));
                }
                return;
            }
            public void CheckForShoot(Variables vars)
            {
                if(!CanShoot)
                {
                    return;
                }
                Position p = Constants.centroArcoSup;
                if(vars.rivalPlayers.positions[0].distance(Constants.centroArcoSup)< 0)
                {
                    if(vars.rivalPlayers.positions[0].distance(Constants.posteIzqArcoSup) < vars.rivalPlayers.positions[0].distance(Constants.posteDerArcoSup))
                    {
                        p = Constants.posteDerArcoSup.movePosition(-1,0);

                    }
                    else
                    {   
                        p = Constants.posteIzqArcoSup.movePosition(1,0);
                    }
                    if((!rivalsAreInPassTrayectory(vars, this, this.playerPosition.to2D().angle(p), 1, 0)
                        &&
                        !vars.currentPlayerCommandIsForGo(this.playerIndex)
                        && 
                        !vars.currentPlayerCommandIsForPass(this.playerIndex)
                    )
                    ||
                    vars.gs.ballPosition().distance(Constants.centroArcoSup) < 30
                    ||
                    CountPlayersGreaterThanY(vars.myPlayers, 30) >3
                    )
                {
                    vars.AddCommands(new Shoot(this.playerIndex, 
                        p,
                        1,
                        5 + r.nextInt(5)));
                }
                }
                else
                {
                    double angle = Math.toDegrees(vars.gs.ballPosition().angle(Constants.centroArcoSup));
                    boolean isGoal = false;
                    angleZloop: for(double angleZ=0; angleZ<=8; angleZ = angleZ+1)
                    {
                        powerloop: for(double power=1;power>0; power = power-0.1)
                        {
                            ArrayList<Position3D> expectedTrayectory = getTrayectoryExpected(vars, angle, power, angleZ, false);
                            if(expectedTrayectory == null)
                            {
                                break powerloop;
                            }
                            coordsloop: for(Position3D c3d : expectedTrayectory)
                            {
                                if(c3d.z > Constants.ALTO_ARCO)
                                {
                                    if(c3d.y > Constants.LARGO_CAMPO /2)
                                    {
                                        break coordsloop;
                                    }
                                    else
                                    {
                                        continue;
                                    }
                                }
                                
                                Players nearestRivalPlayersToExpectedTrayectory = c3d.nearestPlayers(vars, vars.rivalPlayers);
                                for(Player nearestRivalPlayerToExpectedTrayectory : nearestRivalPlayersToExpectedTrayectory)
                                {
                                    boolean nearestRivalPlayerCanHandleBall = (nearestRivalPlayerToExpectedTrayectory.getRealPlayerSpeed(vars) * c3d.iteration) + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= Math.abs(nearestRivalPlayerToExpectedTrayectory.playerPosition.to2D().distance(c3d.to2D()));
                                    if(nearestRivalPlayerCanHandleBall)
                                    {
                                        break coordsloop;
                                    }
                                }
                                if(c3d.y >= Constants.LARGO_CAMPO /2)
                                {
                                    if(c3d.z <= Constants.ALTO_ARCO && c3d.inUpTrayectory == false)
                                    {
                                        isGoal = true;
                                    }
                                    break coordsloop;
                                }
                            }
                            if(isGoal)
                            {
                                vars.AddCommands(new Shoot(this.playerIndex, 
                                    angle,
                                    power,
                                    angleZ));
                                break angleZloop;
                            }
                        }
                    }
                    if(!isGoal)
                    {
                        vars.AddCommands(new Shoot(this.playerIndex, 
                            angle,
                            1,
                            0));
                    }
                }
//               
            }
            public void CheckForPassAdvance(Variables vars,
                    MConstants.ShootTrayectory shootTrayectory)
            {
                Position ballPosition = vars.gs.ballPosition();
                if(!CanPass)
                {
                    return;
                }
                if(SimplePass)
                {
                    CheckForPass(vars, shootTrayectory, 0);
                    return;
                }
                if(shootTrayectory == MConstants.ShootTrayectory.backward && this.playerIndex == 0)
                {
                    return;
                }
                if(shootTrayectory == MConstants.ShootTrayectory.forward)
                {
                    if(
                    CountPlayersGreaterThanY(vars.myPlayers, this.playerPosition.y) == 0
                    ||        
                    ballPosition.getY() > 35  
                      )
                    {
                        return;
                    }
                }
                Position currentPlayerPosition = vars.myPlayers.positions[this.playerIndex];
                MCommand bestCommand = null;
                int maxAngle = 180;
                int minAngle = 0;
                if(ballPosition.getY() > -10)
                {
                    if(ballPosition.getX()> 24)
                    {
                        minAngle = 90;
                    }
                    if(ballPosition.getX()< -24)
                    {
                        maxAngle = 90;
                    }
                }
                Player nearestRival = this.playerPosition.nearestPlayer(vars, vars.rivalPlayers);
                angleloop: for(int angle=minAngle;angle<maxAngle;angle=angle+5)
                {
                    boolean isGoodPass = false;
                    Position3D estimatedPosition3D = null;
                    double angleErrorMin = angle - (Math.toDegrees(this.getRealPlayerError(vars)) /2);
                    double angleErrorMax = angle + (Math.toDegrees(this.getRealPlayerError(vars)) /2);
                    double finalAngle = 0;
                    double finalPower = 0;
                    double finalAngleZ = 0;
                    Player nearestPlayerToExpectedTrayectory = null;
                    
                    angleZloop: for(double angleZ=0;angleZ<=45;angleZ=angleZ+5)
                    {
                        powerloop: for(double power=1;power>0.4;power=power-0.1)
                        {
                            ArrayList<Position3D> expectedTrayectory = getTrayectoryExpected(vars, angle, power, angleZ, false);
                            if(expectedTrayectory == null)
                            {
                                isGoodPass = false;
                                break powerloop;
                            }
                            ArrayList<Position3D> expectedTrayectoryErrorMin = getTrayectoryExpected(vars, angleErrorMin, power, angleZ, false);
                            ArrayList<Position3D> expectedTrayectoryErrorMax = getTrayectoryExpected(vars, angleErrorMax, power, angleZ, false);
                            coordsloop: for(Position3D c3d : expectedTrayectory)
                            {
                                if(!c3d.to2D().isInsideGameField(4))
                                {
                                    break coordsloop;
                                }
                               
                                if(c3d.z > Constants.ALTURA_CONTROL_BALON)
                                {
                                    continue;
                                }
                                
                                Players nearestRivalPlayersToExpectedTrayectory = c3d.nearestPlayers(vars, vars.rivalPlayers);
                                for(int rp=0;rp<Math.min(1, nearestRivalPlayersToExpectedTrayectory.size());rp++)
                                {
                                    Player nearestRivalPlayerToExpectedTrayectory = nearestRivalPlayersToExpectedTrayectory.get(rp);
                                    double nearestRivalMettersInC3DIteration = nearestRivalPlayerToExpectedTrayectory.getRealPlayerSpeed(vars) * c3d.iteration;
                                    double nearestRivalDistanceToTrayectory = Math.abs(nearestRivalPlayerToExpectedTrayectory.playerPosition.to2D().distance(c3d.to2D()));
                                    boolean nearestRivalPlayerCanHandleBallErrorMin = false;
                                    boolean nearestRivalPlayerCanHandleBallErrorMax = false;
                                    boolean nearestRivalPlayerCanHandleBall = nearestRivalMettersInC3DIteration + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= nearestRivalDistanceToTrayectory;
                                    if(MConstants.ErrorCalculationEnabled && expectedTrayectoryErrorMin.size() <= c3d.iteration)
                                    {
                                        double nearestRivalDistanceToTrayectoryErrorMin = Math.abs(nearestPlayerToExpectedTrayectory.playerPosition.to2D().distance(expectedTrayectoryErrorMin.get(c3d.iteration).to2D()));
                                        nearestRivalPlayerCanHandleBallErrorMin = nearestRivalMettersInC3DIteration + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= nearestRivalDistanceToTrayectoryErrorMin;
                                    }
                                    if(MConstants.ErrorCalculationEnabled && expectedTrayectoryErrorMax.size() <= c3d.iteration)
                                    {
                                        double nearestRivalDistanceToTrayectoryErrorMax = Math.abs(nearestPlayerToExpectedTrayectory.playerPosition.to2D().distance(expectedTrayectoryErrorMax.get(c3d.iteration).to2D()));
                                        nearestRivalPlayerCanHandleBallErrorMax = nearestRivalMettersInC3DIteration + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= nearestRivalDistanceToTrayectoryErrorMax;
                                    }
                                    if(nearestRivalPlayerCanHandleBall || (MConstants.ErrorCalculationEnabled &&  (nearestRivalPlayerCanHandleBallErrorMin || nearestRivalPlayerCanHandleBallErrorMax)))
                                    {
                                        break coordsloop;
                                    }
                                }
                                nearestPlayerToExpectedTrayectory = c3d.nearestPlayer(vars, vars.myPlayers, this.playerIndex);
                                if(nearestPlayerToExpectedTrayectory != null)
                                {
                                    double nearestPlayerMettersInC3DIteration = nearestPlayerToExpectedTrayectory.getRealPlayerSpeed(vars) * c3d.iteration;
                                    double nearestPlayerDistanceToTrayectory = Math.abs(nearestPlayerToExpectedTrayectory.playerPosition.to2D().distance(c3d.to2D()));
                                    boolean nearestPlayerCanHandleBallErrorMin = false;
                                    boolean nearestPlayerCanHandleBallErrorMax = false;
                                    boolean nearestPlayerCanHandleBall = nearestPlayerMettersInC3DIteration + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= nearestPlayerDistanceToTrayectory;
                                    if(MConstants.ErrorCalculationEnabled && expectedTrayectoryErrorMin.size() <= c3d.iteration)
                                    {
                                        double nearestPlayerDistanceToTrayectoryErrorMin = Math.abs(nearestPlayerToExpectedTrayectory.playerPosition.to2D().distance(expectedTrayectoryErrorMin.get(c3d.iteration).to2D()));
                                        nearestPlayerCanHandleBallErrorMin = nearestPlayerMettersInC3DIteration + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= nearestPlayerDistanceToTrayectoryErrorMin;
                                    }
                                    if(MConstants.ErrorCalculationEnabled && expectedTrayectoryErrorMax.size() <= c3d.iteration)
                                    {
                                        double nearestPlayerDistanceToTrayectoryErrorMax = Math.abs(nearestPlayerToExpectedTrayectory.playerPosition.to2D().distance(expectedTrayectoryErrorMax.get(c3d.iteration).to2D()));
                                        nearestPlayerCanHandleBallErrorMax = nearestPlayerMettersInC3DIteration + Constants.DISTANCIA_CONTROL_BALON_PORTERO >= nearestPlayerDistanceToTrayectoryErrorMax;
                                    }
                                    if(nearestPlayerCanHandleBall || (nearestPlayerCanHandleBall && MConstants.ErrorCalculationEnabled && (nearestPlayerCanHandleBallErrorMin || nearestPlayerCanHandleBallErrorMax)))
                                    {
                                        isGoodPass = true;
                                        estimatedPosition3D = c3d;
                                        finalAngle = angle;
                                        finalPower = power;
                                        finalAngleZ = angleZ;
                                        break angleZloop;
                                    }
                                }
                            }
                           
                        }
                     
                    }
                    if(isGoodPass && nearestPlayerToExpectedTrayectory != null)
                    {
                        if(bestCommand == null 
                                || 
                                (vars.myPlayers.GetPlayerByIndex(bestCommand.destinationPlayerIndex).playerPosition.y < nearestPlayerToExpectedTrayectory.playerPosition.y))
                        {
                            bestCommand = new Pass(this.playerIndex, nearestPlayerToExpectedTrayectory.playerIndex, finalAngle, finalPower, finalAngleZ);
                            vars.ballToPlayerIndex = nearestPlayerToExpectedTrayectory.playerIndex;                                    
                            vars.ballToEstimatedPosition3D = estimatedPosition3D;
                        }
                    }
                }
                if(bestCommand != null)
                {
                     vars.AddCommands(bestCommand);
                }
            }
            public void CheckForPass(Variables vars,
                    MConstants.ShootTrayectory shootTrayectory,
                    double angleZ
                    )
            {
                if(!CanPass)
                {
                    return;
                }
                if(shootTrayectory == MConstants.ShootTrayectory.backward && this.playerIndex == 0)
                {
                    return;
                }
              
                Player bestPass = null;
                double bestPassDistance = 0;
                double bestPassAngle =0;
                Position currentPlayerPosition = vars.myPlayers.positions[this.playerIndex];
                int[] nearestIndexes = currentPlayerPosition.nearestIndexes(vars.myPlayers.positions);
                for(int i=0;i<nearestIndexes.length;i++)
                {
                    int nearestIndex = nearestIndexes[i]; 
                    if(nearestIndex == this.playerIndex || nearestIndex == 0)
                    {
                        continue;
                    }
                    Player nearestPlayer = vars.myPlayers.GetPlayerByIndex(nearestIndex);
                    if(this.playerIndex == 0 && nearestPlayer.playerPosition.y < 0)
                    {
                        continue;
                    }
                    double hitPower = 1;
                
                    double angleToNearestPosition = Math.toDegrees(currentPlayerPosition.angle(nearestPlayer.playerPosition.to2D()));
                    double distanceToNearestPosition = currentPlayerPosition.distance(nearestPlayer.playerPosition.to2D());
                    
                    if(shootTrayectory == MConstants.ShootTrayectory.forward && angleToNearestPosition < 0)
                    {
                        continue;
                    }
                    if(shootTrayectory == MConstants.ShootTrayectory.backward && angleToNearestPosition > 0)
                    {
                        continue;
                    }
                    
                    if((!this.rivalsAreInPassTrayectory(vars, nearestPlayer, angleToNearestPosition, hitPower, angleZ)
                        && nearestIndex != 0
                            && distanceToNearestPosition > MConstants.minPassDistance
                            ))
                    {
                       
                        if(bestPass == null)
                        {
                            bestPass = nearestPlayer;
                            bestPassDistance = distanceToNearestPosition;
                            bestPassAngle = angleToNearestPosition;
                        }
                        else
                        {
                            if(nearestPlayer.playerPosition.y > bestPass.playerPosition.y)
                            {
                               bestPass = nearestPlayer;
                               bestPassDistance = distanceToNearestPosition;
                               bestPassAngle = angleToNearestPosition;
                            }
                        }
                    }
                    
                }
                if(bestPass != null)
                {
                    vars.AddCommands(new Pass(this.playerIndex,
                            bestPass.playerIndex,
                            bestPassAngle,
                            1,
                            angleZ));
                    vars.ballToPlayerIndex = bestPass.playerIndex;
                }
                return;
            }
    }

class Go extends MCommand
{
    public Go(int playerIndex, double angle, double power, double angleZ)
    {
        this.playerIndex = playerIndex;
        this.commandType = MConstants.CommandType.Go;
        this.destinationPlayerIndex = playerIndex;
        this.angle = angle;
        this.power = power;
        this.angleZ = angleZ;
        this.command = new CommandHitBall(playerIndex, angle, power, angleZ);
    }
}
class Pass extends MCommand
{
    public Pass(int playerIndex, int destinationPlayerIndex, double angle, double power, double angleZ)
    {
        this.playerIndex = playerIndex;
        this.commandType = MConstants.CommandType.Pass;
        this.destinationPlayerIndex = destinationPlayerIndex;
        this.angle = angle;
        this.power = power;
        this.angleZ = angleZ;
        this.command = new CommandHitBall(playerIndex, angle, power, angleZ);
    }
}
class Shoot extends MCommand
{
    public Shoot(int playerIndex, double angle, double power, double angleZ)
    {
        this.playerIndex = playerIndex;
        this.commandType = MConstants.CommandType.Shoot;
        this.destinationPlayerIndex = -1;
        this.angle = angle;
        this.power = power;
        this.angleZ = angleZ;
        this.command = new CommandHitBall(playerIndex, angle, power, angleZ);
    }
    public Shoot(int playerIndex, Position position, double power, double angleZ)
    {
        this.playerIndex = playerIndex;
        this.commandType = MConstants.CommandType.Shoot;
        this.destinationPlayerIndex = -1;
        this.destinationPosition = position;
        this.power = power;
        this.angleZ = angleZ;
        this.command = new CommandHitBall(playerIndex, position, power, angleZ);
    }
}
class Move extends MCommand
{
    public Move(int playerIndex, Position position)
    {
        this.playerIndex = playerIndex;
        this.commandType = MConstants.CommandType.Move;
        this.destinationPosition = position;
        this.command = new CommandMoveTo(playerIndex, position);
    }
}