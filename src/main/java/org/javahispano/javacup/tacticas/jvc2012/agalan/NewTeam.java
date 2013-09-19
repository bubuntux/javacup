package org.javahispano.javacup.tacticas.jvc2012.agalan;

import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

public class NewTeam implements Tactic {


    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    	return alineaciones.getAlineacionSacando();
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    	return alineaciones.getAlineacionSinBalon();
    }

    //Lista de comandos
    LinkedList<Command> comandos = new LinkedList<Command>();
    Alineaciones alineaciones = new Alineaciones();
    
    public List<Command> execute(GameSituations sp) {
    	//Limpia la lista de comandos
        comandos.clear();
        TacticaImpl tactica = new TacticaImpl(sp);    
        tactica.situarEquipo(comandos);
        return comandos;
    }


}