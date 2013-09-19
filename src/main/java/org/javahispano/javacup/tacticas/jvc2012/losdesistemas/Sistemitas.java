package org.javahispano.javacup.tacticas.jvc2012.losdesistemas;

import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;

public class Sistemitas implements Tactic{

	private List<Command> comandos = new LinkedList<Command>();
	private GameSituations sp;
	private Datines data = new Datines(); 
	private MessiLaSacaHastaConLaCho goalkeeper = new MessiLaSacaHastaConLaCho(comandos, data);
	private SapitoEncina pass = new SapitoEncina(comandos, data);
	private Defensa defense = new Defensa(comandos, data);
	private AtaquePapa attack = new AtaquePapa(comandos, data);	
	
	@Override
	public List<Command> execute(GameSituations sp) {
		init(sp);
		boolean attacking = attacking();
		Mentalidad mentality = getMentality();
		attack.execute(sp, mentality);
		defense.execute(sp, attacking);
		pass.execute(sp, mentality);
		goalkeeper.execute(sp);		
		return comandos;
	}

	
	private Mentalidad getMentality() {
		int iter = sp.iteration();
		if(sp.myGoals() >= sp.rivalGoals() + 5){
			defense.setCountDefense(4);
			return Mentalidad.Offensive;
		}
		if(sp.myGoals() > sp.rivalGoals()){
			defense.setCountDefense(5);
			return Mentalidad.Normal;
		}
		if(sp.myGoals() == sp.rivalGoals()){
			if(iter < Constants.ITERACIONES/3){
				defense.setCountDefense(4);
				return Mentalidad.Normal;
			}
			if(iter < Constants.ITERACIONES*2/3){
				defense.setCountDefense(4);
				return Mentalidad.Offensive;
			}
			defense.setCountDefense(4);
			return Mentalidad.Aggressive;
		}
		if(iter < Constants.ITERACIONES/3){
			defense.setCountDefense(4);
			return Mentalidad.Offensive;
		}
		if(iter < Constants.ITERACIONES*2/3){
			defense.setCountDefense(4);
			return Mentalidad.Aggressive;
		}
		defense.setCountDefense(4);
		return Mentalidad.Aggressive;
	}


	private boolean attacking() {
		int iterToBall = data.getIterToBall();
		int opponentIterToBall = data.getOpponentIterToBall();
		return sp.isStarts() || (!sp.isRivalStarts() && iterToBall >= 0 && (opponentIterToBall < 0 || iterToBall < opponentIterToBall));
	}


	private void init(GameSituations sp) {
		this.sp = sp;
		comandos.clear();
		data.update(sp);
	}

	@Override
	public TacticDetail getDetail() {
		return new LosDetalles();
	}

	@Override
	public Position[] getNoStartPositions(GameSituations sp) {
		return new Position[]{
				new Position(0.23776223776223776,-51.31221719457013),
		        new Position(-11.174825174825173,-31.357466063348415),
		        new Position(9.510489510489512,-32.07013574660634),
		        new Position(24.251748251748253,-30.88235294117647),
		        new Position(-26.153846153846157,-31.59502262443439),
		        new Position(-0.7132867132867133,-19.479638009049776),
		        new Position(19.734265734265733,-9.502262443438914),
		        new Position(-16.88111888111888,-11.402714932126697),
		        new Position(9.748251748251748,-0.7126696832579186),
		        new Position(-0.951048951048951,-9.264705882352942),
		        new Position(-9.986013986013985,-0.23755656108597287)
		    };
	}

	@Override
	public Position[] getStartPositions(GameSituations sp) {
		return new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-8.55944055944056,-31.59502262443439),
		        new Position(6.6573426573426575,-32.54524886877828),
		        new Position(21.16083916083916,-31.83257918552036),
		        new Position(-20.923076923076923,-31.357466063348415),
		        new Position(-0.7132867132867133,-19.479638009049776),
		        new Position(17.11888111888112,-10.927601809954751),
		        new Position(-16.88111888111888,-11.402714932126697),
		        new Position(24.251748251748253,-0.7126696832579186),
		        new Position(-0.23776223776223776,0.0),
		        new Position(-23.062937062937063,-0.23755656108597287)
		    };
	}

}
