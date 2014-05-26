package org.dsaw.javacup.tactics.jvc2012.masia2012;

public enum Mentality{
	Normal(5, 2, 1),
	Offensive(4, 3, 1),
	Defensive(6, 1, 2);
	
	public int defenseCount;
	public int centralDefenseCount;
	public int attackerCount;
	

	private Mentality(int defenseCount, int attackerCount, int centralDefenseCount) {
		this.defenseCount = defenseCount;
		this.attackerCount = attackerCount;
		this.centralDefenseCount = centralDefenseCount;
	}
}
