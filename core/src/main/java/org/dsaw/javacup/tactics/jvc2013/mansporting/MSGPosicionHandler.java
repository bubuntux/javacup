/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.engine.GameSituations;

import java.util.Map;

/**
 *
 * @author MaN
 */
public interface MSGPosicionHandler {

    Map<Integer, MSGAlineacionPosicion> getPosicion(MSGTactica tactica, GameSituations sp, Boolean saco);
}
