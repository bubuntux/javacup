package org.dsaw.javacup.tacticas.jvc2012.hortalezablamers.utils.pases.simulador;

import java.util.Vector;

import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Position;

public class CommandHitBallSimulated implements Comparable<CommandHitBallSimulated> {

	protected CommandHitBall chb;
	protected Integer diferenciaIteraciones;
	protected Integer indiceReceptor;
	protected Position posicionRecepcionMedia;
	protected Vector<Integer> iteracionesRecuperacion;
	
	public CommandHitBallSimulated(CommandHitBall chb,
			Integer diferenciaIteraciones, Integer indiceReceptor,
			Position posicionRecepcionMedia,
			Vector<Integer> iteracionesRecuperacion) {
		super();
		this.chb = chb;
		this.diferenciaIteraciones = diferenciaIteraciones;
		this.indiceReceptor = indiceReceptor;
		this.posicionRecepcionMedia = posicionRecepcionMedia;
		this.iteracionesRecuperacion = iteracionesRecuperacion;
	}

	public CommandHitBall getChb() {
		return chb;
	}
	public Integer getDiferenciaIteraciones() {
		return diferenciaIteraciones;
	}
	public Integer getIndiceReceptor() {
		return indiceReceptor;
	}
	public Position getPosicionRecepcionMedia() {
		return posicionRecepcionMedia;
	}
	public Vector<Integer> getIteracionesRecuperacion() {
		return iteracionesRecuperacion;
	}

	@Override
	public int compareTo(CommandHitBallSimulated other) {
		return this.getDiferenciaIteraciones() - other.getDiferenciaIteraciones();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CommandHitBallSimulated))
			return false;
		
		CommandHitBallSimulated other = (CommandHitBallSimulated) o;
		return this.getDiferenciaIteraciones().equals(other.getDiferenciaIteraciones());
	}

	public String toString() {
		return "\n" +
				" - ItDiff: " + this.diferenciaIteraciones + 
				" - Ind: " + this.indiceReceptor + 
				" - Pos: " + this.posicionRecepcionMedia + 
				" - Ang: " + this.chb.getAngle() + 
				" - Pot: " + this.chb.getHitPower() + 
				" - AngV: " + this.chb.getVerticalAngle();
	}
}
