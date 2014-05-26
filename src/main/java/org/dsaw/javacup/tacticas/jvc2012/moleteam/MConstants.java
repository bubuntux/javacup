/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tacticas.jvc2012.moleteam;

/**
 *
 * @author jlosarcos
 */
public final class MConstants {
    public enum PlayerRole 
    {
        goalKeeper,
        secondLineOfDefence,
        firstLineOfDefence,
        middleLine,
        attackLine
    }
    public enum ShootTrayectory
    {
        any,
        forward,
        backward
    }
    public enum CommandType
    {
        Go,
        Pass,
        Shoot,
        Move
    }

    //Public Constant
    public static double minPassDistance = 5;
    public static double maxPassDistance = 100;
    public static int estimatedIterationsPerHit = 12;
    public static int PassAdvanceIterationSecurity = 0;
    public static boolean ErrorCalculationEnabled = true;
}

 