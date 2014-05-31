/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

/**
 *
 * @author MaN
 */
public class JugadoresBalonData {

    private JugadorBalonData contrario;
    private JugadorBalonData propio;
    public JugadoresBalonData(JugadorBalonData contrario, JugadorBalonData propio) {
        this.contrario = contrario;
        this.propio = propio;
    }

    public JugadorBalonData getContrario() {
        return contrario;
    }

    public JugadorBalonData getPropio() {
        return propio;
    }

    public boolean isAlcanzable() {
        if(propio == null)
            return false;
        if(contrario == null )
            return true;
        return propio.getIteraciones() <= contrario.getIteraciones();
    }

    public int getIteracionesVentaja() {
        return contrario.getIteraciones() - propio.getIteraciones();
    }

    public void setContrario(JugadorBalonData contrario) {
        this.contrario = contrario;
    }

    public void setPropio(JugadorBalonData propio) {
        this.propio = propio;
    }


}
