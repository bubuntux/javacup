package org.dsaw.javacup.tacticas.jvc2012.agalan;

import org.dsaw.javacup.model.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Alineaciones {
	
	Position inicial[]=new Position[]{
		  new Position(-10,45),
    	  new Position(-15,-40),
          new Position(0,-40),
          new Position(15,-40),
          new Position(10,0),
          new Position(-10,0),
          new Position(-25,10),
          new Position(25,10),
          new Position(-15,20),
          new Position(10,40),                
          new Position(0,-50)
    };

	Position ultraDefensivo[]=new Position[]{
    		new Position(-15,-40),
            new Position(-10,-40),
            new Position(-5,-40),
            new Position(0,-40),
            new Position(5,-40),
            new Position(10,-40),
            new Position(15,-40),
            new Position(-5,20),
            new Position(5,-10),
            new Position(0,35),
            new Position(0,-50)
    };

    Position[] alineaciones[] = new Position[][]{inicial,ultraDefensivo};
    List<Integer> defensores = new ArrayList<>();
    List<Integer> medios = new ArrayList<>();
    List<Integer> extremos = new ArrayList<>();
    List<Integer> delanteros = new ArrayList<>();
    int portero = 10;

    public Position[] getAlineacion(int pos){
    	defensores.clear();
        medios.clear();
        delanteros.clear();
    	switch (pos) {
    	case Constantes.ALINEACION_INICIAL:
    		defensores.add(1);    		defensores.add(2);
    		defensores.add(3);    		medios.add(4);
	    	medios.add(5);	  		  	medios.add(6);
	    	medios.add(7);	    		extremos.add(6);			extremos.add(7);	    		
	    	delanteros.add(8);	    	
	    	delanteros.add(9);	    	delanteros.add(0);
			break;
    	case Constantes.ALINEACION_ULTRADEFENSIVO:
    		defensores.add(0);    		defensores.add(1);
    		defensores.add(2);    		defensores.add(3);
    		defensores.add(4);	  		defensores.add(5);
    		defensores.add(6);	    	delanteros.add(7);	    	
	    	delanteros.add(8);	    	delanteros.add(9);
			break;
		default:
    		defensores.add(0);    		defensores.add(1);
    		defensores.add(2);    		medios.add(3);
	    	medios.add(4);	  		  	medios.add(5);
	    	medios.add(6);	    		extremos.add(5);			extremos.add(6);	    		
	    	delanteros.add(7);	    	
	    	delanteros.add(8);	    	delanteros.add(9);
			break;
		}
    	return alineaciones[pos];
    }

	public Position[] getAlineacionSacando() {
		return inicial;
	}

	public Position[] getAlineacionSinBalon() {
		return inicial;
	}

	public List getDefensores() {
		return defensores;
	}

	public void setDefensores(List defensores) {
		this.defensores = defensores;
	}

	public List getMedios() {
		return medios;
	}

	public void setMedios(List medios) {
		this.medios = medios;
	}

	public List getDelanteros() {
		return delanteros;
	}

	public void setDelanteros(List delanteros) {
		this.delanteros = delanteros;
	}

	public List getExtremos() {
		return extremos;
	}

	public void setExtremos(List extremos) {
		this.extremos = extremos;
	}

	public int getPortero() {
		return portero;
	}

	public void setPortero(int portero) {
		this.portero = portero;
	}
	
	
	
	
}