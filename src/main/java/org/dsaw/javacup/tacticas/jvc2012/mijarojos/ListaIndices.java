package org.dsaw.javacup.tacticas.jvc2012.mijarojos;

import java.util.ArrayList;
import java.util.LinkedList;

import org.dsaw.javacup.model.util.Position;

public class ListaIndices extends LinkedList<Integer> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListaIndices(boolean llena){
		super();
		if(llena){
			for(int i = 0; i < 11; i++){
				super.add(i);
			}
		}
	}
	
	public boolean sacar(int indice){
		for(Integer i : this){
			if(i.intValue() == indice){
				super.remove(i);
				return true;
			}				
		}
		return false;
	}
	
	public boolean sacar(int[] indices, int n){
		int cont = 0;
		for(int i : indices){
			if(cont == n){
				break;
			}
			if(this.sacar(i)){
				cont++;
			}
		}
		return cont > 0;
	}
	
	public boolean sacar(int[] indices){
		return sacar(indices, -1);
	}
	
	public boolean meter(int indice){
		for(Integer i : this){
			if(i.intValue() == indice){
				return false;
			}				
		}
		super.add(new Integer(indice));
		return true;
	}

	public boolean meter(int[] indices, int n){
		int cont = 0;
		for(int i : indices){
			if(cont == n){
				break;
			}
			if(this.meter(i)){
				cont++;
			}
		}
		return cont > 0;
	}
	
	public boolean meter(int[] indices){
		return meter(indices, -1);
	}
	
	public Position[] toPositionArray(Position[] jugadores){
		ArrayList<Position> lst = new ArrayList<Position>();
		for(Integer i : this){
			if(jugadores.length > (i.intValue() + 1)){
				lst.add(new Position(jugadores[i.intValue()]));
			}
		}
		return lst.toArray(new Position[0]);
	}
	
	public int[] toIntArray(){
		int[] array = new int[this.size()];
		int cont = 0;
		for(Integer i : this){
			array[cont++] = i.intValue();
		}
		return array;
	}
}
