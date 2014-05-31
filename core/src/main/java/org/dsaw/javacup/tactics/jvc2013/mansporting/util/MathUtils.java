/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.mansporting.util;

/**
 *
 * @author MaN
 */
public final class MathUtils {

    private MathUtils()
    {

    }

    public static double round(double number, int decimalDigits) {
        double factor = Math.pow(10, decimalDigits);
        int factorized = (int) (number * factor);
        return factorized / factor;
    }

}
