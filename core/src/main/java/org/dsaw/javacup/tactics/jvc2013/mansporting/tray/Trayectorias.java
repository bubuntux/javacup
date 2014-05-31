
package org.dsaw.javacup.tactics.jvc2013.mansporting.tray;

import java.util.ArrayList;
import java.util.List;



public class Trayectorias {

    protected List<Trayectoria> trayectoria;

    /**
     * Gets the value of the trayectoria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trayectoria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrayectoria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Trayectoria }
     * 
     * 
     */
    public List<Trayectoria> getTrayectoria() {
        if (trayectoria == null) {
            trayectoria = new ArrayList<Trayectoria>();
        }
        return this.trayectoria;
    }

}
