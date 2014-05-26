/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tacticas.jvc2012.moleteam;

import java.util.ArrayList;
import org.dsaw.javacup.model.util.Position;

/**
 *
 * @author jlosarcos
 */
 public final class Players extends ArrayList<Player> {
        Position[] positions;
        public Players()
        {
        
        }
        public Players(Position[] positions, int[] rivalsToDefence, boolean isRivals)
        {
           this.positions = positions;
           for(int i=0;i<positions.length;i++)
           {
               this.add(new Player(i, positions[i], rivalsToDefence[i], isRivals));
           }
        }
        public Players(Position[] positions, boolean isRivals)
        {
           this.positions = positions;
           for(int i=0;i<positions.length;i++)
           {
               this.add(new Player(i, positions[i], -1, isRivals));
           }
        }
        public Player GetPlayerByIndex(int index)
        {
           return this.get(index);
        }
        
    }
