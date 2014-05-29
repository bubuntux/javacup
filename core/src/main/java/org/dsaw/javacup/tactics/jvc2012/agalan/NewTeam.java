package org.dsaw.javacup.tactics.jvc2012.agalan;

import java.util.LinkedList;
import java.util.List;

import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.command.Command;
import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.model.util.Position;

public class NewTeam implements Tactic {


    TacticDetail detalle=new TacticDetailImpl();
    @Override
    public TacticDetail getDetail() {
        return detalle;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
    	return alineaciones.getAlineacionSacando();
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    	return alineaciones.getAlineacionSinBalon();
    }

    //Lista de comandos
    LinkedList<Command> comandos = new LinkedList<>();
    Alineaciones alineaciones = new Alineaciones();
    
    @Override
    public List<Command> execute(GameSituations sp) {
    	//Limpia la lista de comandos
        comandos.clear();
        TacticaImpl tactica = new TacticaImpl(sp);    
        tactica.situarEquipo(comandos);
        return comandos;
    }


}