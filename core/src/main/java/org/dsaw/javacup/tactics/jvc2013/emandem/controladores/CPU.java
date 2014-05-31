package org.dsaw.javacup.tactics.jvc2013.emandem.controladores;

import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

import java.util.LinkedList;
import java.util.List;

public class CPU {
	private LinkedList<Command> comandos = new LinkedList<Command>();
	
	public List<Command> getComandosEjecutar(GameSituations situacionPartido) {
		
		this.comandos.clear();
		
		ControladorEstadoPartido.getInstance().analizarYCargarInformacion(situacionPartido);
		
		Position[] alineacionActual = ControladorAlineaciones.getInstance().getAlineacionActual(situacionPartido);
		
		this.comandos.addAll(ControladorPortero.getInstance().getComandos(situacionPartido, alineacionActual));
		
		this.comandos.addAll(ControladorJugadores.getInstance().getComandos(situacionPartido, alineacionActual));
	        
	    return comandos;
	}   
}