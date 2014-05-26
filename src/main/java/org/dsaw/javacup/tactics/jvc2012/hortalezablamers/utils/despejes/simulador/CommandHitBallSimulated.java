package org.dsaw.javacup.tactics.jvc2012.hortalezablamers.utils.despejes.simulador;

import org.dsaw.javacup.model.command.CommandHitBall;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

public class CommandHitBallSimulated implements Comparable<CommandHitBallSimulated> {

	protected CommandHitBall chb;
	protected Position posicionRecepcionMedia;
	
	public CommandHitBallSimulated(CommandHitBall chb,
			Position posicionRecepcionMedia) {
		super();
		this.chb = chb;
		this.posicionRecepcionMedia = posicionRecepcionMedia;
	}
	
	public CommandHitBall getChb() {
		return chb;
	}
	public Position getPosicionRecepcionMedia() {
		return posicionRecepcionMedia;
	}

	@Override
	public int compareTo(CommandHitBallSimulated other) {
		Double thisDistance = this.getPosicionRecepcionMedia().distance(Constants.centroArcoInf);
		Double otherDistance = other.getPosicionRecepcionMedia().distance(Constants.centroArcoInf);
		return otherDistance.compareTo(thisDistance);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CommandHitBallSimulated))
			return false;
		
		CommandHitBallSimulated other = (CommandHitBallSimulated) o;
		Double thisDistance = this.getPosicionRecepcionMedia().distance(Constants.centroArcoInf);
		Double otherDistance = other.getPosicionRecepcionMedia().distance(Constants.centroArcoInf);
		return thisDistance.equals(otherDistance);
	}

	public String toString() {
		return "\n" +
				" - Pos: " + this.posicionRecepcionMedia + 
				" - Dis: " + this.posicionRecepcionMedia.distance(Constants.centroArcoInf) + 
				" - Ang: " + this.chb.getAngle() + 
				" - Pot: " + this.chb.getHitPower() + 
				" - AngV: " + this.chb.getVerticalAngle();
	}
}
